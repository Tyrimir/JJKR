package com.jujutsu.jujutsucraftaddon.procedures;

import net.mcreator.jujutsucraft.entity.EightHandledSwordDivergentSilaDivineGeneralMahoragaEntity;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;

public class MahoEffectoEffectStartedappliedProcedure {
    public static void execute(Entity entity) {
        if (entity == null)
            return;
        {
            Entity _ent = entity;
            if (!_ent.level().isClientSide() && _ent.getServer() != null) {
                _ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
                        _ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "execute as @e[distance=..30] run effect clear @e[distance=..30] jujutsucraft:domain_expansion");
            }
        }
        ((EightHandledSwordDivergentSilaDivineGeneralMahoragaEntity) entity).setAnimation("sword_overhead");
    }
}
