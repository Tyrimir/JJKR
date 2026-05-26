//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.jujutsu.jujutsucraftaddon.events;

import com.jujutsu.jujutsucraftaddon.JujutsucraftaddonMod;
import com.jujutsu.jujutsucraftaddon.utils.ProtectedRegionRegistry;
import net.mcreator.jujutsucraft.init.JujutsucraftModMobEffects;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent.ImpactResult;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber(modid = JujutsucraftaddonMod.MODID, bus = EventBusSubscriber.Bus.FORGE)
public class ProtectedRegionEvents {
    private static final String INSIDE_TAG = "jjkur_safespot_inside";

    public ProtectedRegionEvents() {
    }

    @SubscribeEvent(
            priority = EventPriority.HIGHEST
    )
    public static void onEntitySpawn(EntityJoinLevelEvent event) {
        Entity e = event.getEntity();
        if (e != null) {
            Level level = event.getLevel();
            if (!level.isClientSide()) {
                if (!(e instanceof Player)) {
                    if (isJujutsuCraftEntity(e)) {
                        if (ProtectedRegionRegistry.isProtected(level, e.getX(), e.getY(), e.getZ())) {
                            event.setCanceled(true);
                        } else {
                            if (e instanceof Projectile) {
                                Projectile proj = (Projectile)e;
                                Entity owner = proj.getOwner();
                                if (owner != null && ProtectedRegionRegistry.isProtected(level, owner.getX(), owner.getY(), owner.getZ())) {
                                    event.setCanceled(true);
                                }
                            }

                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent(
            priority = EventPriority.HIGHEST
    )
    public static void onMobNaturalSpawn(MobSpawnEvent.FinalizeSpawn event) {
        Mob mob = event.getEntity();
        if (mob != null) {
            if (isJujutsuCraftEntity(mob)) {
                Level level = mob.level();
                if (!level.isClientSide()) {
                    if (ProtectedRegionRegistry.isProtected(level, mob.getX(), mob.getY(), mob.getZ())) {
                        event.setSpawnCancelled(true);
                    }

                }
            }
        }
    }

    @SubscribeEvent(
            priority = EventPriority.HIGHEST
    )
    public static void onLivingAttack(LivingAttackEvent event) {
        if (shouldBlockDamage(event.getEntity(), event.getSource())) {
            event.setCanceled(true);
            Entity var2 = event.getSource().getEntity();
            if (var2 instanceof Player) {
                Player p = (Player)var2;
                notifyPlayer(p);
            }
        }

    }

    @SubscribeEvent(
            priority = EventPriority.HIGHEST
    )
    public static void onLivingHurt(LivingHurtEvent event) {
        if (shouldBlockDamage(event.getEntity(), event.getSource())) {
            event.setCanceled(true);
            Entity var2 = event.getSource().getEntity();
            if (var2 instanceof Player) {
                Player p = (Player)var2;
                notifyPlayer(p);
            }
        }

    }

    private static boolean shouldBlockDamage(LivingEntity victim, DamageSource src) {
        if (victim != null && src != null) {
            Level level = victim.level();
            if (level.isClientSide()) {
                return false;
            } else {
                Entity direct = src.getDirectEntity();
                Entity owner = src.getEntity();
                boolean victimInside = ProtectedRegionRegistry.isProtected(level, victim.getX(), victim.getY(), victim.getZ());
                boolean directInside = direct != null && ProtectedRegionRegistry.isProtected(level, direct.getX(), direct.getY(), direct.getZ());
                boolean ownerInside = owner != null && ProtectedRegionRegistry.isProtected(level, owner.getX(), owner.getY(), owner.getZ());
                return !victimInside && !directInside && !ownerInside ? false : isJujkDamage(direct, owner);
            }
        } else {
            return false;
        }
    }

    @SubscribeEvent(
            priority = EventPriority.HIGHEST
    )
    public static void onProjectileImpact(ProjectileImpactEvent event) {
        Projectile proj = event.getProjectile();
        if (proj != null) {
            if (isJujutsuCraftEntity(proj)) {
                Level level = proj.level();
                if (!level.isClientSide()) {
                    if (ProtectedRegionRegistry.isProtected(level, proj.getX(), proj.getY(), proj.getZ())) {
                        event.setImpactResult(ImpactResult.STOP_AT_CURRENT_NO_DAMAGE);
                        proj.discard();
                    }

                }
            }
        }
    }

    @SubscribeEvent(
            priority = EventPriority.HIGHEST
    )
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Player p = event.getPlayer();
        if (p != null) {
            Level level = p.level();
            if (!level.isClientSide()) {
                BlockPos pos = event.getPos();
                if (ProtectedRegionRegistry.isProtected(level, pos)) {
                    if (!isAdmin(p)) {
                        event.setCanceled(true);
                        notifyPlayer(p);
                    }
                }
            }
        }
    }

    @SubscribeEvent(
            priority = EventPriority.HIGHEST
    )
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
        LevelAccessor levelAccessor = event.getLevel();

        if (levelAccessor instanceof Level level) {
            if (!level.isClientSide()) {
                BlockPos pos = event.getPos();

                if (ProtectedRegionRegistry.isProtected(level, pos)) {
                    Entity placer = event.getEntity();
                    if (placer != null) {
                        if (isJujutsuCraftEntity(placer)) {
                            event.setCanceled(true);
                        } else if (placer instanceof Player p) {
                            if (isAdmin(p)) {
                                return;
                            }

                            event.setCanceled(true);
                            notifyPlayer(p);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent(
            priority = EventPriority.HIGHEST
    )
    public static void onExplosionDetonate(ExplosionEvent.Detonate event) {
        Level level = event.getLevel();
        if (level != null && !level.isClientSide()) {
            Entity source = event.getExplosion().getIndirectSourceEntity();
            if (source == null) {
                source = event.getExplosion().getDirectSourceEntity();
            }

            boolean var10000;
            label29: {
                label28: {
                    if (source != null) {
                        if (isJujutsuCraftEntity(source)) {
                            break label28;
                        }

                        if (source instanceof Player) {
                            Player p = (Player)source;
                            if (hasActiveCt(p)) {
                                break label28;
                            }
                        }
                    }

                    var10000 = false;
                    break label29;
                }

                var10000 = true;
            }

            boolean jjkSource = var10000;
            if (jjkSource) {
                event.getAffectedBlocks().removeIf((bp) -> ProtectedRegionRegistry.isProtected(level, bp));
                event.getAffectedEntities().removeIf((en) -> ProtectedRegionRegistry.isProtected(level, en.getX(), en.getY(), en.getZ()));
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == Phase.END) {
            Player p = event.player;
            if (p != null && !p.level().isClientSide()) {
                boolean inside = ProtectedRegionRegistry.isProtected(p.level(), p.getX(), p.getY(), p.getZ());
                if (inside) {
                    p.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, (Direction)null).ifPresent((vars) -> {
                        boolean dirty = false;
                        if (vars.PlayerCursePower > (double)0.0F) {
                            vars.PlayerCursePower = (double)0.0F;
                            dirty = true;
                        }

                        if (vars.PlayerCharge > (double)0.0F) {
                            vars.PlayerCharge = (double)0.0F;
                            dirty = true;
                        }

                        if (dirty) {
                            vars.syncPlayerVariables(p);
                        }

                    });
                    if (p.hasEffect((MobEffect)JujutsucraftModMobEffects.DOMAIN_EXPANSION.get())) {
                        p.removeEffect((MobEffect)JujutsucraftModMobEffects.DOMAIN_EXPANSION.get());
                    }
                }

                if (p.level().getGameTime() % 10L == 0L) {
                    boolean wasInside = p.getPersistentData().getBoolean("jjkur_safespot_inside");
                    if (inside) {
                        p.addEffect(new MobEffectInstance((MobEffect)JujutsucraftModMobEffects.COOLDOWN_TIME.get(), 40, 0, false, false, false));
                        p.addEffect(new MobEffectInstance((MobEffect)JujutsucraftModMobEffects.COOLDOWN_TIME_COMBAT.get(), 40, 0, false, false, false));
                        if (!wasInside) {
                            p.getPersistentData().putBoolean("jjkur_safespot_inside", true);
                            p.displayClientMessage(Component.literal("§c⚠ You entered a JJK Safe Spot — abilities disabled."), true);
                        }
                    } else if (wasInside) {
                        p.removeEffect((MobEffect)JujutsucraftModMobEffects.COOLDOWN_TIME.get());
                        p.removeEffect((MobEffect)JujutsucraftModMobEffects.COOLDOWN_TIME_COMBAT.get());
                        p.removeEffect((MobEffect)JujutsucraftModMobEffects.UNSTABLE.get());
                        p.getPersistentData().putBoolean("jjkur_safespot_inside", false);
                        p.displayClientMessage(Component.literal("§a✔ You left the Safe Spot — abilities re-enabled."), true);
                    }

                }
            }
        }
    }

    private static boolean isAdmin(Player p) {
        if (!(p instanceof ServerPlayer sp)) {
            return false;
        } else {
            return sp.hasPermissions(2) && sp.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
        }
    }

    private static boolean hasActiveCt(Player p) {
        JujutsucraftModVariables.PlayerVariables vars = (JujutsucraftModVariables.PlayerVariables)p.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, (Direction)null).orElse(new JujutsucraftModVariables.PlayerVariables());
        return vars.PlayerCurseTechnique > (double)0.0F || vars.PlayerCurseTechnique2 > (double)0.0F;
    }

    private static void notifyPlayer(Player p) {
        if (!p.level().isClientSide()) {
            long now = p.level().getGameTime();
            long last = p.getPersistentData().getLong("jjkur_safespot_msg_tick");
            if (now - last > 60L) {
                p.getPersistentData().putLong("jjkur_safespot_msg_tick", now);
                p.displayClientMessage(Component.literal("§c⚠ JJK abilities are disabled inside this safe spot."), true);
            }

        }
    }

    private static boolean isJujutsuCraftEntity(Entity e) {
        if (e == null) {
            return false;
        } else {
            ResourceLocation key = ForgeRegistries.ENTITY_TYPES.getKey(e.getType());
            return key == null ? false : key.getNamespace().equals("jujutsucraft");
        }
    }

    private static boolean isJujkDamage(Entity direct, Entity owner) {
        if (direct == null && owner == null) {
            return false;
        } else if (direct != null && isJujutsuCraftEntity(direct)) {
            return true;
        } else if (owner != null && isJujutsuCraftEntity(owner)) {
            return true;
        } else if (direct != null && direct.getPersistentData().getString("jjkur_technique_dmg_key").length() > 0) {
            return true;
        } else if (owner != null && owner.getPersistentData().getString("jjkur_technique_dmg_key").length() > 0) {
            return true;
        } else {
            if (owner instanceof Player) {
                Player p = (Player)owner;
                JujutsucraftModVariables.PlayerVariables vars = (JujutsucraftModVariables.PlayerVariables)p.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, (Direction)null).orElse(new JujutsucraftModVariables.PlayerVariables());
                if ((vars.PlayerCurseTechnique > (double)0.0F || vars.PlayerCurseTechnique2 > (double)0.0F) && direct != null && direct != owner) {
                    return true;
                }
            }

            return false;
        }
    }
}
