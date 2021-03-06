package de.teamlapen.vampirism;

import cpw.mods.fml.common.registry.GameRegistry;
import de.teamlapen.vampirism.block.BlockCastleSlab;
import de.teamlapen.vampirism.item.*;
import de.teamlapen.vampirism.util.REFERENCE;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class ModItems {

	public static ItemSword leechSword = new ItemLeechSword();

	public static ItemBloodBottle bloodBottle = new ItemBloodBottle();

	public static ItemVampireFang vampireFang = new ItemVampireFang();
	public static ItemWeakVampireFang weakVampireFang = new ItemWeakVampireFang();
	public static ItemPitchfork pitchfork = new ItemPitchfork();

	public static ItemTorch torch = new ItemTorch();
	public static ItemHumanHeart humanHeart = new ItemHumanHeart();
	public static ItemWeakHumanHeart weakHumanHeart = new ItemWeakHumanHeart();
	public static ItemPureBlood pureBlood = new ItemPureBlood();
	public static ItemSunscreen sunscreen = new ItemSunscreen();
	public static ItemCoffin coffin = new ItemCoffin();
	public static ItemVampireArmor vampireHelmet = new ItemVampireArmor(0);
	public static ItemVampireArmor vampireChestplate = new ItemVampireArmor(1);
	public static ItemVampireArmor vampireLeggings = new ItemVampireArmor(2);
	public static ItemVampireArmor vampireBoots = new ItemVampireArmor(3);
	public static ItemGemOfBinding gemOfBinding = new ItemGemOfBinding();
	public static ItemMinionNameTag minionNameTag = new ItemMinionNameTag();
	public static ItemSlab castleSlabItem;
	public static ItemBloodEye bloodEye=new ItemBloodEye();
	public static ItemTent tent = new ItemTent();
	public static ItemGarlic garlic;
	public static ItemGarlicBomb garlicBomb = new ItemGarlicBomb();
	public static ItemCheatLevelUp cheatLevelUp = new ItemCheatLevelUp();
	public static ItemBloodBottleBundle bloodBottleBundle = new ItemBloodBottleBundle();

	/**
	 * Concentrated form of garlic
	 */
	public static Item concentratedGarlic = new Item().setCreativeTab(VampirismMod.tabVampirism).setUnlocalizedName(REFERENCE.MODID + ".concentratedGarlic").setTextureName(REFERENCE.MODID + ":concentratedGarlic");

	public static void init() {
		GameRegistry.registerItem(leechSword, ItemLeechSword.name);
		GameRegistry.registerItem(bloodBottle, ItemBloodBottle.name);
		GameRegistry.registerItem(vampireFang, ItemVampireFang.NAME);
		GameRegistry.registerItem(pitchfork, ItemPitchfork.name);
		// GameRegistry.registerItem(torch, ItemTorch.name);
		GameRegistry.registerItem(humanHeart, ItemHumanHeart.name);
		GameRegistry.registerItem(pureBlood, ItemPureBlood.name);
		GameRegistry.registerItem(sunscreen, ItemSunscreen.name);
		GameRegistry.registerItem(coffin, ItemCoffin.name);
		GameRegistry.registerItem(vampireHelmet, vampireHelmet.getRegisterItemName());
		GameRegistry.registerItem(vampireChestplate, vampireChestplate.getRegisterItemName());
		GameRegistry.registerItem(vampireLeggings, vampireLeggings.getRegisterItemName());
		GameRegistry.registerItem(vampireBoots, vampireBoots.getRegisterItemName());
		GameRegistry.registerItem(gemOfBinding, ItemGemOfBinding.name);
		GameRegistry.registerItem(minionNameTag, ItemMinionNameTag.name);
		GameRegistry.registerItem((castleSlabItem=new ItemSlab(ModBlocks.castleSlab,ModBlocks.castleSlab,ModBlocks.doubleCastleSlab,false)), BlockCastleSlab.name);
		GameRegistry.registerItem(bloodEye,ItemBloodEye.name);
		GameRegistry.registerItem(tent, ItemTent.name);
		GameRegistry.registerItem(weakHumanHeart, ItemWeakHumanHeart.name);
		GameRegistry.registerItem(weakVampireFang, ItemWeakVampireFang.name);
		garlic = new ItemGarlic(ModBlocks.garlicPlant);
		GameRegistry.registerItem(garlic, ItemGarlic.name);
		GameRegistry.registerItem(concentratedGarlic, "item_concentratedGarlic");
		GameRegistry.registerItem(garlicBomb, ItemGarlicBomb.name);
		GameRegistry.registerItem(cheatLevelUp, ItemCheatLevelUp.name);
		GameRegistry.registerItem(bloodBottleBundle, ItemBloodBottleBundle.name);
	}
	public static void registerRecipes() {
		GameRegistry.addRecipe(new ItemStack(bloodBottle, 1, 0), "   ", "XYX", " X ", 'X', Blocks.glass, 'Y', Items.rotten_flesh);
		GameRegistry.addRecipe(new ItemStack(leechSword, 1), "XYX", "XYX", " Z ", 'X', vampireFang, 'Y', Items.iron_ingot, 'Z', Items.stick);
		GameRegistry.addRecipe(new ItemStack(sunscreen, 1), "XYX", "YZY", "XYX", 'X', ModBlocks.vampireFlower, 'Y', Items.gold_nugget, 'Z', humanHeart);
		GameRegistry.addRecipe(new ItemStack(coffin, 1), "XXX", "Y Y", "XXX", 'X', Blocks.planks, 'Y', Blocks.wool);
		GameRegistry
				.addRecipe(new ItemStack(vampireHelmet, 1), "XXX", "YYY", "YZY", 'X', ModBlocks.vampireFlower, 'Y', Items.iron_ingot, 'Z', new ItemStack(bloodBottle, 1, ItemBloodBottle.MAX_BLOOD));
		GameRegistry.addRecipe(new ItemStack(vampireBoots, 1), "YZY", "Y Y", 'Y', Items.iron_ingot, 'Z', new ItemStack(bloodBottle, 1, ItemBloodBottle.MAX_BLOOD));
		GameRegistry.addRecipe(new ItemStack(vampireLeggings, 1), "YYY", "YZY", "Y Y", 'Y', Items.iron_ingot, 'Z', new ItemStack(bloodBottle, 1, ItemBloodBottle.MAX_BLOOD));
		GameRegistry.addRecipe(new ItemStack(vampireChestplate, 1), "YZY", "YYY", "YYY", 'Y', Items.iron_ingot, 'Z', new ItemStack(bloodBottle, 1, ItemBloodBottle.MAX_BLOOD));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.glass_bottle), new ItemStack(bloodBottle, 1, 0));
		GameRegistry.addRecipe(new ItemStack(gemOfBinding, 1), " X ", "YZY", " V ", 'X', ModItems.humanHeart, 'Y', new ItemStack(bloodBottle, 1, ItemBloodBottle.MAX_BLOOD), 'Z', Items.diamond, 'V',
				ModBlocks.vampireFlower);
		GameRegistry.addShapelessRecipe(new ItemStack(minionNameTag), Items.paper, ModBlocks.vampireFlower);
		GameRegistry.addShapelessRecipe(new ItemStack(bloodEye),Items.ender_eye,new ItemStack(bloodBottle,1,ItemBloodBottle.MAX_BLOOD));
		GameRegistry.addShapelessRecipe(new ItemStack(humanHeart), ModItems.weakHumanHeart, ModItems.weakHumanHeart, ModItems.weakHumanHeart, ModItems.weakHumanHeart, ModItems.weakHumanHeart, ModItems.weakHumanHeart);
		GameRegistry.addShapelessRecipe(new ItemStack(vampireFang), ModItems.weakVampireFang, ModItems.weakVampireFang, ModItems.weakVampireFang, ModItems.weakVampireFang, ModItems.weakVampireFang, ModItems.weakVampireFang);
		GameRegistry.addShapelessRecipe(new ItemStack(concentratedGarlic), ModItems.garlic, ModItems.garlic, ModItems.garlic, ModItems.garlic, ModItems.garlic, ModItems.garlic, ModItems.garlic, ModItems.garlic, ModItems.garlic);
		GameRegistry.addShapelessRecipe(new ItemStack(garlicBomb), ModItems.concentratedGarlic, Items.glass_bottle);
		GameRegistry.addShapelessRecipe(new ItemStack(bloodBottleBundle), new ItemStack(bloodBottle, 1, ItemBloodBottle.MAX_BLOOD), new ItemStack(bloodBottle, 1, ItemBloodBottle.MAX_BLOOD), new ItemStack(bloodBottle, 1, ItemBloodBottle.MAX_BLOOD), new ItemStack(bloodBottle, 1, ItemBloodBottle.MAX_BLOOD));

		//Add garlic version of swords
		GameRegistry.addRecipe(GarlicHelper.createGarlicRecipe(Items.iron_sword));
		GameRegistry.addRecipe(GarlicHelper.createGarlicRecipe(Items.golden_sword));
		GameRegistry.addRecipe(GarlicHelper.createGarlicRecipe(Items.wooden_sword));
		GameRegistry.addRecipe(GarlicHelper.createGarlicRecipe(Items.diamond_sword));
		GameRegistry.addRecipe(GarlicHelper.createGarlicRecipe(Items.stone_sword));
	}
}
