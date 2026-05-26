package com.jujutsu.jujutsucraftaddon.mixins;

import net.mcreator.jujutsucraft.entity.ZeninOgiEntity;
import net.mcreator.jujutsucraft.init.JujutsucraftModMobEffects;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.mcreator.jujutsucraft.procedures.LogicStartPassiveProcedure;
import net.mcreator.jujutsucraft.procedures.OgiZeninPassiveSkillProcedure;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = OgiZeninPassiveSkillProcedure.class)
public class OgiZeninPassiveSkillProcedureMixin {
 //Fixes base jjc ogi ticking crash
    @Inject(method = "execute", at = @At("HEAD"), cancellable = true, remap = false)
    private static void preventNullCrash(Entity entity, CallbackInfo ci) {
        if (!(entity instanceof Player)) return;
        var cap = entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null);
        if (!cap.isPresent()) {
            ci.cancel();
        }
    }
}