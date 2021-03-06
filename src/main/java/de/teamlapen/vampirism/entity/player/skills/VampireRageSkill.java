package de.teamlapen.vampirism.entity.player.skills;

import de.teamlapen.vampirism.entity.player.VampirePlayer;
import de.teamlapen.vampirism.util.BALANCE;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class VampireRageSkill extends DefaultSkill implements ILastingSkill {

	@Override
	public boolean canBeUsedBy(VampirePlayer vampire, EntityPlayer player) {
		return !vampire.isSkillActive(Skills.batMode);
	}

	@Override
	public int getCooldown() {
		return BALANCE.VP_SKILLS.RAGE_COOLDOWN * 20;
	}

	@Override
	public int getDuration(int level) {
		return BALANCE.VP_SKILLS.getVampireLordDuration(level);
	}

	@Override
	public int getMinLevel() {
		return BALANCE.VP_SKILLS.RAGE_MIN_LEVEL;
	}

	@Override
	public int getMinU() {
		return 32;
	}

	@Override
	public int getMinV() {
		return 0;
	}

	@Override
	public String getUnlocalizedName() {
		return "skill.vampirism.vampire_rage";
	}

	@Override
	public boolean onActivated(VampirePlayer vampire, EntityPlayer player) {
		player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, getDuration(vampire.getLevel()), 2));
		return true;
	}

	@Override
	public void onDeactivated(VampirePlayer vampire, EntityPlayer player) {
		player.removePotionEffect(Potion.moveSpeed.id);

	}

	@Override
	public void onReActivated(VampirePlayer vampire, EntityPlayer player) {

	}

	@Override
	public boolean onUpdate(VampirePlayer vampire, EntityPlayer player) {
		return false;
	}

}
