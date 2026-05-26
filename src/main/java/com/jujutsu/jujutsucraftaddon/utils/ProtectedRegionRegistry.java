//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.jujutsu.jujutsucraftaddon.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jujutsu.jujutsucraftaddon.JujutsucraftaddonMod;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraftforge.server.ServerLifecycleHooks;

public final class ProtectedRegionRegistry {
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private static final Type LIST_TYPE = (new TypeToken<List<ProtectedRegion>>() {
    }).getType();
    private static final String FILE_NAME = "jjkur_protected_regions.json";
    private static final List<ProtectedRegion> REGIONS = new ArrayList();
    private static boolean loaded = false;

    private ProtectedRegionRegistry() {
    }

    public static synchronized List<ProtectedRegion> all() {
        return new ArrayList(REGIONS);
    }

    public static synchronized boolean add(ProtectedRegion region) {
        if (region != null && region.name != null && !region.name.isEmpty()) {
            REGIONS.removeIf((r) -> r.name.equalsIgnoreCase(region.name));
            REGIONS.add(region);
            save();
            return true;
        } else {
            return false;
        }
    }

    public static synchronized boolean remove(String name) {
        if (name == null) {
            return false;
        } else {
            boolean removed = REGIONS.removeIf((r) -> r.name.equalsIgnoreCase(name));
            if (removed) {
                save();
            }

            return removed;
        }
    }

    public static synchronized void clear() {
        REGIONS.clear();
        save();
    }

    public static synchronized boolean isProtected(Level level, double x, double y, double z) {
        if (level != null && !REGIONS.isEmpty()) {
            ResourceLocation dim = level.dimension().location();

            for(ProtectedRegion r : REGIONS) {
                if (r.contains(dim, x, y, z)) {
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }

    public static synchronized boolean isProtected(Level level, BlockPos pos) {
        return isProtected(level, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ());
    }

    private static Path filePath() {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        return server == null ? null : server.getWorldPath(LevelResource.ROOT).resolve("jjkur_protected_regions.json");
    }

    public static synchronized void load() {
        REGIONS.clear();
        loaded = true;
        Path path = filePath();
        if (path != null && Files.exists(path, new LinkOption[0])) {
            try {
                String json = new String(Files.readAllBytes(path));
                List<ProtectedRegion> list = (List)GSON.fromJson(json, LIST_TYPE);
                if (list != null) {
                    for(ProtectedRegion r : list) {
                        if (r != null && r.name != null && r.dimension != null) {
                            REGIONS.add(r);
                        }
                    }
                }

                JujutsucraftaddonMod.LOGGER.info("[JJKUR] Loaded {} protected region(s)", REGIONS.size());
            } catch (IOException e) {
                JujutsucraftaddonMod.LOGGER.error("[JJKUR] Failed to load protected regions", e);
            }

        }
    }

    public static synchronized void save() {
        if (loaded) {
            Path path = filePath();
            if (path != null) {
                try {
                    Files.createDirectories(path.getParent());
                    String json = GSON.toJson(REGIONS, LIST_TYPE);
                    Files.write(path, json.getBytes(), new OpenOption[0]);
                } catch (IOException e) {
                    JujutsucraftaddonMod.LOGGER.error("[JJKUR] Failed to save protected regions", e);
                }

            }
        }
    }

    public static final class ProtectedRegion {
        public String name;
        public String dimension;
        public int x1;
        public int y1;
        public int z1;
        public int x2;
        public int y2;
        public int z2;

        public ProtectedRegion() {
        }

        public ProtectedRegion(String name, ResourceLocation dim, BlockPos a, BlockPos b) {
            this.name = name;
            this.dimension = dim.toString();
            this.x1 = Math.min(a.getX(), b.getX());
            this.y1 = Math.min(a.getY(), b.getY());
            this.z1 = Math.min(a.getZ(), b.getZ());
            this.x2 = Math.max(a.getX(), b.getX());
            this.y2 = Math.max(a.getY(), b.getY());
            this.z2 = Math.max(a.getZ(), b.getZ());
        }

        public boolean contains(ResourceLocation dim, double x, double y, double z) {
            if (!this.dimension.equals(dim.toString())) {
                return false;
            } else {
                return x >= (double)this.x1 && x <= (double)(this.x2 + 1) && y >= (double)this.y1 && y <= (double)(this.y2 + 1) && z >= (double)this.z1 && z <= (double)(this.z2 + 1);
            }
        }

        public String toString() {
            return String.format(Locale.ROOT, "%s [%s] (%d,%d,%d) -> (%d,%d,%d)", this.name, this.dimension, this.x1, this.y1, this.z1, this.x2, this.y2, this.z2);
        }
    }
}
