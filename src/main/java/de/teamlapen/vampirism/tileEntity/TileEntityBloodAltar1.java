package de.teamlapen.vampirism.tileEntity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.teamlapen.vampirism.ModItems;
import de.teamlapen.vampirism.ModPotion;
import de.teamlapen.vampirism.entity.player.VampirePlayer;
import de.teamlapen.vampirism.item.ItemLeechSword;
import de.teamlapen.vampirism.util.Helper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

/**
 * Beacon style blood altar
 * REWRITE RECOMMEND to make the infinite option more clean
 *
 * @author maxanier, Mistadon
 */
public class TileEntityBloodAltar1 extends TileEntity {
	private boolean occupied = false;
	private int bloodAmount;
	public final String OCCUPIED_NBTKEY = "occupied";
	public final String BLOOD_NBTKEY = "blood";
	public final String TICK_NBTKEY = "tick";
	public final String INFINITE_NBTKEY = "infinite";
	private final String TAG = "TEBloodAltar";
	public int distance = 25;
	private int tickCounter = 0;
	private TileEntityBeacon fakeBeacon;
	private final int max_blood;

	public boolean isInfinite() {
		return infinite;
	}


	/**
	 * If true the altar does not consume any blood from the sword.
	 * Reset when the sword is taken out.
	 */
	private boolean infinite;

	public TileEntityBloodAltar1() {
		super();
		infinite=false;
		max_blood = ItemLeechSword.MAX_BLOOD;
	}

	public void dropSword() {
		if (this.isOccupied()) {
			EntityItem sword = new EntityItem(this.worldObj, this.xCoord, this.yCoord + 1, this.zCoord, getSwordToEject());
			this.worldObj.spawnEntityInWorld(sword);
			//Infinite is alread disabled in {@link #getSwordToEject()}
		}
	}

	public int getBloodLeft() {
		return infinite ? max_blood : bloodAmount;
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbtTag = new NBTTagCompound();
		this.writeToNBT(nbtTag);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbtTag);
	}

	@SideOnly(Side.CLIENT)
	public TileEntityBeacon getFakeBeacon() {
		return fakeBeacon;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return INFINITE_EXTENT_AABB;
	}

	private ItemStack getSwordToEject() {
		ItemStack s = new ItemStack(ModItems.leechSword, 1);
		ItemLeechSword.setBlood(s, bloodAmount);
		bloodAmount = 0;
		infinite = false;
		return s;
	}

	public boolean isActive() {
		return bloodAmount > 0 || infinite;
	}

	public boolean isOccupied() {
		return occupied;
	}

	public void makeInfinite(){
		this.infinite=true;
		this.bloodAmount = 0;
		this.occupied=true;
		this.markDirty();
	}

	/**
	 * Marks the block dirty and ready for update
	 */
	@Override
	public void markDirty() {
		super.markDirty();
		this.worldObj.markBlockForUpdate(this.xCoord, yCoord, zCoord);
	}

	public void onActivated(EntityPlayer player, ItemStack itemStack) {
		if (occupied) {
			if (itemStack == null) {
				player.inventory.setInventorySlotContents(player.inventory.currentItem, getSwordToEject());
			} else {
				dropSword();
			}
			occupied = false;
			markDirty();
		} else if (itemStack != null) {
			if (ModItems.leechSword.equals(itemStack.getItem())) {
				this.startRitual(player, itemStack);
			}
		}
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		readFromNBT(packet.func_148857_g());
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.occupied = nbt.getBoolean(OCCUPIED_NBTKEY);
		this.bloodAmount = nbt.getInteger(BLOOD_NBTKEY);
		this.tickCounter = nbt.getInteger(TICK_NBTKEY);
		if(nbt.hasKey(INFINITE_NBTKEY)){
			this.infinite = nbt.getBoolean(INFINITE_NBTKEY);
		}
	}

	@Override
	public void setWorldObj(World world) {
		super.setWorldObj(world);
		if (world.isRemote) {
			fakeBeacon = new TileEntityBeacon();
			Helper.Reflection.setPrivateField(TileEntityBeacon.class, fakeBeacon, true, Helper.Obfuscation.getPosNames("TileEntityBeacon/field_146015_k"));
			fakeBeacon.setWorldObj(world);
		}
	}

	public void startRitual(EntityPlayer player, ItemStack itemStack) {
		if (VampirePlayer.get(player).getLevel() == 0) {
			player.addChatMessage(new ChatComponentTranslation("text.vampirism.ritual_no_vampire"));
			return;
		}
		// Put sword into altar
		occupied = true;
		this.bloodAmount = ItemLeechSword.getBlood(itemStack);
		itemStack.stackSize--;
		infinite = false;
		markDirty();
	}

	@Override
	public void updateEntity() {
		if (this.worldObj.getTotalWorldTime() % 100L == 0L && !this.worldObj.isRemote) {
			if (bloodAmount > 0 || infinite) {
				if(!infinite){
					bloodAmount--;
				}
				if (bloodAmount == 0) {
					this.markDirty();
				}
				AxisAlignedBB axisalignedbb = AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord, this.zCoord, this.xCoord + 1, this.yCoord + 1, this.zCoord + 1).expand(distance, distance,
						distance);
				axisalignedbb.maxY = this.worldObj.getHeight();
				List list = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, axisalignedbb);
				Iterator iterator = list.iterator();
				EntityPlayer entityplayer;

				while (iterator.hasNext()) {
					entityplayer = (EntityPlayer) iterator.next();
					VampirePlayer vampire = VampirePlayer.get(entityplayer);
					if (vampire.getLevel() > 0) {
						entityplayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 120, 0, true));
						entityplayer.addPotionEffect(new PotionEffect(ModPotion.saturation.id, 120, 1, true));
					}
				}
			}

		}

	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean(OCCUPIED_NBTKEY, occupied);
		nbt.setInteger(BLOOD_NBTKEY, bloodAmount);
		nbt.setInteger(TICK_NBTKEY, tickCounter);
		nbt.setBoolean(INFINITE_NBTKEY,infinite);
	}
}
