package com.jujutsu.jujutsucraftaddon.network;

import com.jujutsu.jujutsucraftaddon.init.JujutsucraftaddonModMobEffects;
import com.jujutsu.jujutsucraftaddon.network.JujutsucraftaddonModVariables;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class HonoredOneEffectHandler {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        // Only process at the end of the tick
        if (event.phase != TickEvent.Phase.END) return;

        Player player = event.player;
        if (player == null || player.level().isClientSide()) return;

        // Check if player has "The Honored One" trait
        String trait = player.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                .orElse(new JujutsucraftaddonModVariables.PlayerVariables())
                .Trait;

        // Apply the effect only if they have the trait
        if ("The Honored One".equals(trait)) {
            // Apply the effect if the player doesn't already have it
            if (!player.hasEffect(JujutsucraftaddonModMobEffects.HONORED_ONE.get())) {
                player.addEffect(new MobEffectInstance(
                        JujutsucraftaddonModMobEffects.HONORED_ONE.get(), // RegistryObject.get()
                        -1, // Duration: permanent until removed
                        0, // Amplifier
                        false, // Show particles
                        false  // Show icon
                ));
            }
        } else {
            // Remove the effect if the player no longer has the trait
            if (player.hasEffect(JujutsucraftaddonModMobEffects.HONORED_ONE.get())) {
                player.removeEffect(JujutsucraftaddonModMobEffects.HONORED_ONE.get());
            }
        }
    }
}
