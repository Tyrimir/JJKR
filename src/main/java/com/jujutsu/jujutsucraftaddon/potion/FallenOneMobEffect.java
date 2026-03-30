package com.jujutsu.jujutsucraftaddon.potion;

import com.jujutsu.jujutsucraftaddon.procedures.FallenOneEffectExpiresProcedure;
import com.jujutsu.jujutsucraftaddon.procedures.FallenOneEffectStartedProcedure;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;



public class FallenOneMobEffect extends MobEffect {
    public FallenOneMobEffect() {
        super(MobEffectCategory.BENEFICIAL, -16777216);
        INSTANCE = this; // assign when constructed
    }
    public static FallenOneMobEffect INSTANCE;
    @Override
    public void addAttributeModifiers(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
        super.addAttributeModifiers(entity, attributeMap, amplifier);
        FallenOneEffectStartedProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity);
    }

    @Override
    public void removeAttributeModifiers(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
        super.removeAttributeModifiers(entity, attributeMap, amplifier);
        FallenOneEffectExpiresProcedure.execute(entity.level(), entity);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
