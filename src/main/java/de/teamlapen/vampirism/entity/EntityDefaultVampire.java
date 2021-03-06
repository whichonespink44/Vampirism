package de.teamlapen.vampirism.entity;

import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.vampirism.entity.ai.VampireAIBiteNearbyEntity2;
import de.teamlapen.vampirism.util.REFERENCE;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBreakDoor;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

/**
 * Base class for all 'human' based vampires
 */
public class EntityDefaultVampire extends EntityVampireBase {
    public EntityDefaultVampire(World world) {
        super(world);

        this.setSize(0.6F, 1.8F);

        if (world.provider.dimensionId == VampirismMod.castleDimensionId) {
            this.tasks.addTask(1, new EntityAIOpenDoor(this, true));
            this.getNavigator().setBreakDoors(true);
        } else if (world.difficultySetting == EnumDifficulty.HARD) {
            //Only break doors on hard difficulty
            this.tasks.addTask(1, new EntityAIBreakDoor(this));
            this.getNavigator().setBreakDoors(true);
        }
        this.tasks.addTask(5, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.1F, false));
        this.tasks.addTask(5, new EntityAIAttackOnCollide(this, EntityHunterBase.class, 1.0F, false));
        this.tasks.addTask(6, new EntityAIAttackOnCollide(this, EntityVampireBase.class, 1.0F, false));
        this.tasks.addTask(7, new VampireAIBiteNearbyEntity2(this));

    }

    @Override
    protected String getLivingSound() {
        return REFERENCE.MODID + ":entity.vampire.scream";
    }

    @Override
    public int getTalkInterval() {
        return 400;
    }
}
