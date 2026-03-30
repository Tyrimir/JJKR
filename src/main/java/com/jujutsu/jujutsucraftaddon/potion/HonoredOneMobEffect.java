package com.jujutsu.jujutsucraftaddon.potion;

import com.jujutsu.jujutsucraftaddon.procedures.HonoredOneEffectExpiresProcedure;
import com.jujutsu.jujutsucraftaddon.procedures.HonoredOneEffectStartedProcedure;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;



public class HonoredOneMobEffect extends MobEffect {
    public HonoredOneMobEffect() {
        super(MobEffectCategory.BENEFICIAL, -16777216);
        INSTANCE = this; // assign when constructed
    }
    public static HonoredOneMobEffect INSTANCE;
    @Override
    public void addAttributeModifiers(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
        super.addAttributeModifiers(entity, attributeMap, amplifier);
        HonoredOneEffectStartedProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity);
    }

    @Override
    public void removeAttributeModifiers(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
        super.removeAttributeModifiers(entity, attributeMap, amplifier);
        HonoredOneEffectExpiresProcedure.execute(entity.level(), entity);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
