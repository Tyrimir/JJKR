package com.jujutsu.jujutsucraftaddon.mixins;

import com.jujutsu.jujutsucraftaddon.procedures.CustomCursedTechniqueChangerRightclickedProcedureProcedure;
import com.jujutsu.jujutsucraftaddon.procedures.SelectWukongProcedure;
import net.mcreator.jujutsucraft.network.SelectTechnique2ButtonMessage;
import net.mcreator.jujutsucraft.world.inventory.SelectTechnique2Menu;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;

@Mixin(value = SelectTechnique2ButtonMessage.class, priority = -10000)
public class SelectTechnique2ButtonMessageMixin {
    @Inject(at = @At("HEAD"), method = "handleButtonAction", remap = false, cancellable = true)
    private static void handleButtonAction(Player entity, int buttonID, int x, int y, int z, HashMap<String, String> textstate, CallbackInfo ci) {
        if (buttonID != 100 && buttonID != 101 && buttonID != 1000) {
            return;
        }
        // Custom buttons execute once, on the server, using the player's active menu.
        ci.cancel();
        if (!(entity instanceof ServerPlayer player)
                || !(player.containerMenu instanceof SelectTechnique2Menu menu)
                || !menu.stillValid(player)) {
            return;
        }
        // Button 101 has no enabled UI and its unfinished procedure selects Wukong.
        if (buttonID == 101) {
            return;
        }
        if (buttonID == 1000) {
            CustomCursedTechniqueChangerRightclickedProcedureProcedure.execute(
                    player.level(), player.getX(), player.getY(), player.getZ(), player);
        } else {
            SelectWukongProcedure.execute(player.level(), player.getX(), player.getY(), player.getZ(),
                    player, new HashMap<>());
        }
    }
}