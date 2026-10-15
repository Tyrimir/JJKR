package com.jujutsu.jujutsucraftaddon.mixins;

import com.jujutsu.jujutsucraftaddon.init.JujutsucraftaddonModMobEffects;
import com.jujutsu.jujutsucraftaddon.network.JujutsucraftaddonModVariables;
import com.jujutsu.jujutsucraftaddon.procedures.Animations2Procedure;
import com.jujutsu.jujutsucraftaddon.procedures.HRAttack1Procedure;
import com.jujutsu.jujutsucraftaddon.procedures.NueSummonProcedure;
import com.jujutsu.jujutsucraftaddon.procedures.SpawnCloneProcedure;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.mcreator.jujutsucraft.procedures.KeyStartTechniqueOnKeyPressedProcedure;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = KeyStartTechniqueOnKeyPressedProcedure.class, priority = -10000)
public abstract class KeyStartTechniqueOnKeyPressedProcedureMixin {

    /**
     * @author Satushi
     * @reason Adds some logics to the KeyStartTechniqueOnKeyPressedProcedure for display animations and new attacks
     */


    @Inject(method = "execute", at = @At("TAIL"), remap = false)
    private static void execute(LevelAccessor world, double x, double y, double z, Entity entity, CallbackInfo cir) {
        NueSummonProcedure.execute(world, x, y, z, entity);

        if ((entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftaddonModVariables.PlayerVariables())).Output > 0) {
            if (entity.getPersistentData().getDouble("cnt6") <= (entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftaddonModVariables.PlayerVariables())).Output) {
                entity.getPersistentData().putDouble("cnt6",
                        (entity.getPersistentData().getDouble("cnt6") + (entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftaddonModVariables.PlayerVariables())).Output));
            }
        }


        if ((entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftModVariables.PlayerVariables())).PlayerCurseTechnique2 == -1) {
            HRAttack1Procedure.execute(world, x, y, z, entity);
        }

        if ((entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftModVariables.PlayerVariables())).PlayerCursePower > 1500) {
            if (((entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftModVariables.PlayerVariables())).PlayerSelectCurseTechniqueName).contains("Clone")) {
                if (!(entity instanceof LivingEntity _livEnt4 && _livEnt4.hasEffect(JujutsucraftaddonModMobEffects.CLONE_TICKED.get()))) {
                    if (!(entity.isShiftKeyDown())) {
                        SpawnCloneProcedure.execute(world, x, y, z, entity);
                        if (((entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftaddonModVariables.PlayerVariables())).Clans).equals("Wukong") && (Math.random() < (1) / ((float) 3))) {
                            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
                                _entity.addEffect(new MobEffectInstance(JujutsucraftaddonModMobEffects.CLONE_TICKED.get(), -1, 1, false, false));
                        } else if (!(((entity.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftaddonModVariables.PlayerVariables())).Clans).equals("Wukong"))) {
                            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
                                _entity.addEffect(new MobEffectInstance(JujutsucraftaddonModMobEffects.CLONE_TICKED.get(), -1, 1, false, false));
                        }
                        if (entity instanceof Player) {
                            double _setval = entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftModVariables.PlayerVariables()).PlayerCursePower - 1500.0;
                            entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent((capability) -> {
                                capability.PlayerCursePower = _setval;
                                capability.syncPlayerVariables(entity);
                            });
                        }
                        if (entity instanceof Player _player && !_player.level().isClientSide())
                            _player.displayClientMessage(Component.literal("Clone Spawned"), true);
                    }
                }
            }
        }


        if ((entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftModVariables.PlayerVariables())).PlayerCurseTechnique2 == 2
                || (entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftModVariables.PlayerVariables())).PlayerCurseTechnique2 == 1) {
            Animations2Procedure.execute(world, x, y, z, entity);
        }


    }
    @Inject(method = "execute", at = @At("HEAD"), remap = false, cancellable = true)
    private static void cancelDomainWithBarrierCleanup(LevelAccessor world, double x, double y, double z, Entity entity, CallbackInfo ci) {
        if (!(entity instanceof LivingEntity living) || world.isClientSide()
                || !living.hasEffect(net.mcreator.jujutsucraft.init.JujutsucraftModMobEffects.DOMAIN_EXPANSION.get())) {
            return;
        }
        var vars = entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                .orElse(new JujutsucraftModVariables.PlayerVariables());
        if (!Component.translatable("jujutsu.technique.cancel_domain").getString().equals(vars.PlayerSelectCurseTechniqueName)) {
            return;
        }
        // Capture the center before effect removal resets any domain state.
        var data = entity.getPersistentData();
        if (!data.contains("x_pos_doma") || !data.contains("y_pos_doma") || !data.contains("z_pos_doma")) {
            return;
        }
        var center = new net.minecraft.world.phys.Vec3(data.getDouble("x_pos_doma"),
                data.getDouble("y_pos_doma"), data.getDouble("z_pos_doma"));
        living.removeEffect(net.mcreator.jujutsucraft.init.JujutsucraftModMobEffects.DOMAIN_EXPANSION.get());
        for (var barrier : world.getEntitiesOfClass(net.mcreator.jujutsucraft.entity.DomainExpansionEntityEntity.class,
                net.minecraft.world.phys.AABB.ofSize(center, 4.0, 4.0, 4.0), e -> true)) {
            barrier.getPersistentData().putBoolean("Break", true);
            barrier.getPersistentData().putDouble("cnt_break", 0.0);
            barrier.removeEffect(net.mcreator.jujutsucraft.init.JujutsucraftModMobEffects.NEUTRALIZATION.get());
        }
        ci.cancel();
    }
}
