package de.teamlapen.vampirism;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;
import de.teamlapen.vampirism.entity.player.VampirePlayer;
import de.teamlapen.vampirism.item.GarlicHelper;
import de.teamlapen.vampirism.tileEntity.TileEntityCoffin;
import de.teamlapen.vampirism.util.REFERENCE;
import de.teamlapen.vampirism.util.VampireLordData;
import de.teamlapen.vampirism.util.VersionChecker;
import de.teamlapen.vampirism.villages.VillageVampireData;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.world.WorldEvent;

public class VampirismEventHandler {
	@SubscribeEvent
	public void playerInteract(PlayerInteractEvent e) {
		if (e.world.isRemote)
			return;
		ItemStack i = null;
		if (e.action == Action.RIGHT_CLICK_BLOCK ){
			if(e.entityPlayer.isSneaking() &&e.world.getBlock(e.x, e.y, e.z).equals(ModBlocks.coffin)
					&& (i = (e.entityPlayer).inventory.getCurrentItem()) != null && i.getItem() instanceof ItemDye) {
				int color = i.getItemDamage();
				TileEntityCoffin t = (TileEntityCoffin) e.world.getTileEntity(e.x, e.y, e.z);
				if (t == null)
					return;
				t = t.getPrimaryTileEntity();
				if (t == null)
					return;
				t.changeColor(color);
				e.useBlock = Result.DENY;
				e.useItem = Result.DENY;
				if (!e.entityPlayer.capabilities.isCreativeMode) {
					i.stackSize--;
				}
			}
		}
	}

	@SubscribeEvent
	public void onBonemeal(BonemealEvent event) {
		if (Configs.disable_vampire_biome) {
			if (Blocks.grass.equals(event.block)) {
				if (event.world.rand.nextInt(9) == 0) {
					EntityItem flower = new EntityItem(event.world, event.x, event.y + 1, event.z, new ItemStack(ModBlocks.vampireFlower, 1));
					event.world.spawnEntityInWorld(flower);
				}
			}
		}
	}

	@SubscribeEvent
	public void onTick(TickEvent event) {
		VampirismMod.proxy.onTick(event);
		if(event instanceof ServerTickEvent){
			World w=DimensionManager.getWorld(0);
			if(w!=null){
				VampireLordData.get(w).tick((ServerTickEvent) event);
			}

		}
	}

	@SubscribeEvent
	public void onPlayerLoggedOut(PlayerLoggedOutEvent e) {
		if (VampirePlayer.get(e.player).sleepingCoffin) {
			VampirePlayer.get(e.player).wakeUpPlayer(true, true, false, false);
		}
	}

	@SubscribeEvent
	public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent e) {
		if (VampirismMod.vampireCastleFail) {
			e.player.addChatComponentMessage(new ChatComponentTranslation("text.vampirism.castle_fail"));
		}
		if (Configs.updateNotification && VersionChecker.newVersion != null) {
			if (e.player.getRNG().nextInt(5) == 0) {

				//Inspired by @Vazikii's useful message
				e.player.addChatComponentMessage(new ChatComponentTranslation("text.vampirism.outdated", REFERENCE.VERSION, VersionChecker.newVersion.getModVersion()));
				IChatComponent component = IChatComponent.Serializer.func_150699_a(VersionChecker.addVersionInfo(StatCollector.translateToLocal("text.vampirism.update_message")));
				e.player.addChatComponentMessage(component);
			}
		}
	}

	@SubscribeEvent
	public void onWorldTick(TickEvent.WorldTickEvent event){
		VillageVampireData.get(event.world).onWorldTick(event);
	}


	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event) {
		if (!event.world.isRemote && event.world.provider.dimensionId == 0) {
			//Reset the castle fail notice
			VampirismMod.vampireCastleFail = false;
		}
	}

	@SubscribeEvent
	public void onItemTooltip(ItemTooltipEvent event) {
		int i = GarlicHelper.getGarlicValue(event.itemStack);
		if (i > 0) {
			event.toolTip.add(EnumChatFormatting.YELLOW + StatCollector.translateToLocal("block.vampirism.garlic.name") + " " + i);
//			if(Minecraft.getMinecraft().gameSettings.keyBindSneak.getIsKeyPressed()){
//				String[] parts=StatCollector.translateToLocal("text.vampirism.garlic_weapons").split("/n");
//				for(String p:parts){
//					event.toolTip.add(p);
//				}
//			}



		}


	}
}
