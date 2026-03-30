

package com.jujutsu.jujutsucraftaddon.mixins;
import net.mcreator.jujutsucraft.procedures.SetupAnimationsProcedure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SetupAnimationsProcedure.JujutsucraftModAnimationMessage.class)
public interface PacketAccessorMixin {
/**
    @Accessor("animation")
    String getAnimation();

    @Accessor("animation")
    void setAnimation(String animation);

    @Accessor("target")
    int getTarget();

    @Accessor("target")
    void setTarget(int target);

    @Accessor("override")
    boolean isOverride();

    @Accessor("override")
    void setOverride(boolean override);

**/
}