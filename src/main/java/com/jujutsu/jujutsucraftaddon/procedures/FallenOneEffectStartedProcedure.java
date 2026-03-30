
package com.jujutsu.jujutsucraftaddon.procedures;

import com.jujutsu.jujutsucraftaddon.init.JujutsucraftaddonModMobEffects;
import com.jujutsu.jujutsucraftaddon.network.JujutsucraftaddonModVariables;
import net.mcreator.jujutsucraft.init.JujutsucraftModMobEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import software.bernie.geckolib.animatable.GeoEntity;

import java.util.Comparator;
import java.util.List;

public class FallenOneEffectStartedProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null)
            return;

        // Only apply if the entity is a Player and has "The Fallen One" trait
        if (!(entity instanceof Player player)) return;

        String trait = player.getCapability(JujutsucraftaddonModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                .orElse(new JujutsucraftaddonModVariables.PlayerVariables())
                .Trait;

        if (!"The Fallen One".equals(trait)) return;

        // Play sound if needed
        if (!(entity instanceof GeoEntity)) {

            // Apply Damage Boost
            if (world instanceof ServerLevel && entity instanceof LivingEntity _livingEntity) {
                MobEffectInstance currentEffect = _livingEntity.getEffect(MobEffects.DAMAGE_BOOST);
                int newAmplifier = 1; // base boost

                if (currentEffect != null) {
                    newAmplifier += currentEffect.getAmplifier(); // stack if already present
                }

                _livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, -1, newAmplifier, false, false));
                _livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, -1, 0, false, false));
                _livingEntity.addEffect(new MobEffectInstance(JujutsucraftModMobEffects.REVERSE_CURSED_TECHNIQUE.get(), 30, (entity instanceof LivingEntity && ((LivingEntity) entity).hasEffect(JujutsucraftModMobEffects.REVERSE_CURSED_TECHNIQUE.get()) ? ((LivingEntity) entity).getEffect(JujutsucraftModMobEffects.REVERSE_CURSED_TECHNIQUE.get()).getAmplifier() : 0) + 25, false, false));
                _livingEntity.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, -1, 4, false, false));
                _livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, -1, 1, false, false));
                _livingEntity.addEffect(new MobEffectInstance(JujutsucraftModMobEffects.DOUBLE_JUMP_EFFECT.get(), -1, 4, false, false));

                //_livingEntity.removeEffect(new MobEffectInstance(JujutsucraftaddonModMobEffects.BROKEN_BRAIN.get(), -1, 0, false, false).getEffect());
                //_livingEntity.removeEffect(new MobEffectInstance(JujutsucraftModMobEffects.COOLDOWN_TIME_COMBAT.get(), -1, 0, false, false).getEffect());
                //_livingEntity.removeEffect(new MobEffectInstance(JujutsucraftModMobEffects.COOLDOWN_TIME.get(), -1, 0, false, false).getEffect());
                //_livingEntity.removeEffect(new MobEffectInstance(JujutsucraftaddonModMobEffects.COOLDOWN_TP.get(), -1, 0, false, false).getEffect());



                /**
                if (entity == null)
                    return;
                if (entity instanceof LivingEntity && ((LivingEntity) entity).hasEffect(JujutsucraftModMobEffects.COOLDOWN_TIME.get())) {
                    if (entity instanceof LivingEntity _entity)
                        _entity.removeEffect(JujutsucraftModMobEffects.COOLDOWN_TIME.get());
                } else if (entity instanceof LivingEntity && ((LivingEntity) entity).hasEffect(JujutsucraftModMobEffects.COOLDOWN_TIME_COMBAT.get())) {
                    if (entity instanceof LivingEntity _entity)
                        _entity.removeEffect(JujutsucraftModMobEffects.COOLDOWN_TIME_COMBAT.get());
                } else if (entity instanceof LivingEntity && ((LivingEntity) entity).hasEffect(JujutsucraftModMobEffects.COOLDOWN_TIME_SIMPLE_DOMAIN.get())) {
                    if (entity instanceof LivingEntity _entity)
                        _entity.removeEffect(JujutsucraftModMobEffects.COOLDOWN_TIME_SIMPLE_DOMAIN.get());
                } else if (entity instanceof LivingEntity && ((LivingEntity) entity).hasEffect(JujutsucraftModMobEffects.UNSTABLE.get())) {
                    if (entity instanceof LivingEntity _entity)
                        _entity.removeEffect(JujutsucraftModMobEffects.UNSTABLE.get());
                } else if (entity instanceof LivingEntity && ((LivingEntity) entity).hasEffect(JujutsucraftModMobEffects.COOLDOWN_TIME_BACK_STEP.get())) {
                    if (entity instanceof LivingEntity _entity)
                        _entity.removeEffect(JujutsucraftModMobEffects.COOLDOWN_TIME_BACK_STEP.get());


                    if (entity instanceof LivingEntity && ((LivingEntity) entity).hasEffect(JujutsucraftaddonModMobEffects.BROKEN_BRAIN.get())) {
                        if (entity instanceof LivingEntity _entity)
                            _entity.removeEffect(JujutsucraftaddonModMobEffects.BROKEN_BRAIN.get());
                    }

                    if (entity instanceof LivingEntity && ((LivingEntity) entity).hasEffect(JujutsucraftaddonModMobEffects.COOLDOWN_TP.get())) {
                        if (entity instanceof LivingEntity _entity)
                            _entity.removeEffect(JujutsucraftaddonModMobEffects.COOLDOWN_TP.get());
                    }

                    **/


                    final Vec3 _center = new Vec3(x, y, z);
                    List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(40 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                    for (Entity entityiterator : _entfound) {
                        if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
                            _entity.removeEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 0, 254, false, false).getEffect());

                        if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
                            _entity.removeEffect(new MobEffectInstance(JujutsucraftModMobEffects.UNSTABLE.get(), 0,
                                    (entityiterator instanceof LivingEntity _livEnt && _livEnt.hasEffect(JujutsucraftModMobEffects.UNSTABLE.get()) ? _livEnt.getEffect(JujutsucraftModMobEffects.UNSTABLE.get()).getAmplifier() : 0) + 0, false, false).getEffect());

                        if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
                            _entity.removeEffect(new MobEffectInstance(JujutsucraftModMobEffects.COOLDOWN_TIME.get(), 60,
                                    (entityiterator instanceof LivingEntity _livEnt && _livEnt.hasEffect(JujutsucraftModMobEffects.COOLDOWN_TIME.get()) ? _livEnt.getEffect(JujutsucraftModMobEffects.COOLDOWN_TIME.get()).getAmplifier() : 0) + 0, false, false).getEffect());

                        if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
                            _entity.removeEffect(new MobEffectInstance(JujutsucraftModMobEffects.COOLDOWN_TIME_COMBAT.get(), 0,
                                    (entityiterator instanceof LivingEntity _livEnt && _livEnt.hasEffect(JujutsucraftModMobEffects.COOLDOWN_TIME_COMBAT.get())
                                            ? _livEnt.getEffect(JujutsucraftModMobEffects.COOLDOWN_TIME_COMBAT.get()).getAmplifier()
                                            : 0) + 1,
                                    false, false).getEffect());

                    }

























                }
            }
        }
    }














