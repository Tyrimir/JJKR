package com.jujutsu.jujutsucraftaddon.procedures;

import com.jujutsu.jujutsucraftaddon.configuration.JogoatConfiguration;
import com.jujutsu.jujutsucraftaddon.init.JujutsucraftaddonModItems;
import net.mcreator.jujutsucraft.procedures.*;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestProcedureeProcedure {
    private static class CTRange {
        String CT;
        double startRange;
        double endRange;

        public CTRange(String CT, double startRange, double endRange) {
            this.CT = CT;
            this.startRange = startRange;
            this.endRange = endRange;
        }
    }


    private static List<CTRange> calculateCTRanges() {
        List<CTRange> CTs = new ArrayList<>();

        addCTRange(CTs, "SUKUNA", JogoatConfiguration.SUKUNA.get());
        addCTRange(CTs, "GOJO", JogoatConfiguration.GOJO.get());
        addCTRange(CTs, "OGI", JogoatConfiguration.OGI.get());
        addCTRange(CTs, "MAKI", JogoatConfiguration.MAKI.get());
        addCTRange(CTs, "DHRUV", JogoatConfiguration.DHRUV.get());
        addCTRange(CTs, "MAHORAGA", JogoatConfiguration.MAHORAGA.get());
        addCTRange(CTs, "TSUKUMO", JogoatConfiguration.TSUKUMO.get());
        addCTRange(CTs, "YAGA", JogoatConfiguration.YAGA.get());
        addCTRange(CTs, "KUROURUSHI", JogoatConfiguration.KUROURUSHI.get());
        addCTRange(CTs, "ISHIGORI", JogoatConfiguration.ISHIGORI.get());
        addCTRange(CTs, "NISHIMIYA", JogoatConfiguration.NISHIMIYA.get());
        addCTRange(CTs, "TODO", JogoatConfiguration.TODO.get());
        addCTRange(CTs, "DAGON", JogoatConfiguration.DAGON.get());
        addCTRange(CTs, "MIGUEL", JogoatConfiguration.MIGUEL.get());
        addCTRange(CTs, "OKKOTSU", JogoatConfiguration.OKKOTSU.get());
        addCTRange(CTs, "MEIMEI", JogoatConfiguration.MEIMEI.get());
        addCTRange(CTs, "YOROZU", JogoatConfiguration.YOROZU.get());
        addCTRange(CTs, "URAUME", JogoatConfiguration.URAUME.get());
        addCTRange(CTs, "GETO", JogoatConfiguration.GETO.get());
        addCTRange(CTs, "CHOSO", JogoatConfiguration.CHOSO.get());
        addCTRange(CTs, "TAKAKOURO", JogoatConfiguration.TAKAKOURO.get());
        addCTRange(CTs, "ITADORI", JogoatConfiguration.ITADORI.get());
        addCTRange(CTs, "KUSAKABE", JogoatConfiguration.KUSAKABE.get());
        addCTRange(CTs, "JUNPE", JogoatConfiguration.JUNPE.get());
        addCTRange(CTs, "JOGO", JogoatConfiguration.JOGO.get());
        addCTRange(CTs, "TAKABA", JogoatConfiguration.TAKABA.get());
        addCTRange(CTs, "HIGURUMA", JogoatConfiguration.HIGURUMA.get());
        addCTRange(CTs, "ANGEL", JogoatConfiguration.ANGEL.get());
        addCTRange(CTs, "HAKARI", JogoatConfiguration.HAKARI.get());
        addCTRange(CTs, "MAHITO", JogoatConfiguration.MAHITO.get());
        addCTRange(CTs, "CHOJURO", JogoatConfiguration.CHOJURO.get());
        addCTRange(CTs, "NANAMI", JogoatConfiguration.NANAMI.get());
        addCTRange(CTs, "FUSHIGURO", JogoatConfiguration.FUSHIGURO.get());
        addCTRange(CTs, "SMALLPOXDEITY", JogoatConfiguration.SMALLPOXDEITY.get());
        addCTRange(CTs, "INUMAKI", JogoatConfiguration.INUMAKI.get());
        addCTRange(CTs, "KUGISAKI", JogoatConfiguration.KUGISAKI.get());
        addCTRange(CTs, "HANAMI", JogoatConfiguration.HANAMI.get());
        addCTRange(CTs, "KASHIMO", JogoatConfiguration.KASHIMO.get());
        addCTRange(CTs, "JINICHI", JogoatConfiguration.JINICHI.get());
        addCTRange(CTs, "NAOYA", JogoatConfiguration.NAOYA.get());
        return CTs;
    }

    private static String selectRandomCT(List<CTRange> CTs) {
        if (CTs.isEmpty()) {
            return null;
        }
        double totalSum = CTs.get(CTs.size() - 1).endRange;
        RandomSource random = RandomSource.create();
        double randomValue = random.nextDouble() * totalSum;
        for (CTRange ctRange : CTs) {
            if (randomValue >= ctRange.startRange && randomValue < ctRange.endRange) {
                return ctRange.CT;
            }
        }
        return null;
    }

    private static void addCTRange(List<CTRange> CTs, String CT, double value) {
        if (!Double.isFinite(value) || value <= 0.0) {
            return;
        }
        double totalSum = CTs.isEmpty() ? 0.0 : CTs.get(CTs.size() - 1).endRange;
        CTs.add(new CTRange(CT, totalSum, totalSum + value));

    }

    public static void execute(LevelAccessor world, double x, double y, double z, Player entity, HashMap guistate) {
        if (entity != null && !entity.level().isClientSide() && guistate != null) {
            String selectedCT = selectRandomCT(calculateCTRanges());

            if (selectedCT != null) {
                switch (selectedCT) {
                    case "SUKUNA":
                        SelectSukunaProcedure.execute(world, x, y, z, entity);
                        break;
                    case "GOJO":
                        SelectGojoProcedure.execute(world, x, y, z, entity);
                        break;
                    case "OGI":
                        SelectOgiProcedure.execute(world, x, y, z, entity);
                        break;
                    case "MAKI":
                        SelectMakiProcedure.execute(world, x, y, z, entity);
                        break;
                    case "DHRUV":
                        SelectDhruvLakdawallaProcedure.execute(world, x, y, z, entity);
                        break;
                    case "MAHORAGA":
                        SelectMahoragaProcedure.execute(world, x, y, z, entity);
                        break;
                    case "TSUKUMO":
                        SelectTsukumoProcedure.execute(world, x, y, z, entity);
                        break;
                    case "YAGA":
                        SelectYagaProcedure.execute(world, x, y, z, entity);
                        break;
                    case "KUROURUSHI":
                        SelectKurourushiProcedure.execute(world, x, y, z, entity);
                        break;
                    case "ISHIGORI":
                        SelectIshigoriProcedure.execute(world, x, y, z, entity);
                        break;
                    case "NISHIMIYA":
                        SelectNishimiyaProcedure.execute(world, x, y, z, entity);
                        break;
                    case "TODO":
                        SelectTodoProcedure.execute(world, x, y, z, entity);
                        break;
                    case "DAGON":
                        SelectDagonProcedure.execute(world, x, y, z, entity);
                        break;
                    case "MIGUEL":
                        SelectMiguelProcedure.execute(world, x, y, z, entity);
                        break;
                    case "OKKOTSU":
                        SelectOkkotsuProcedure.execute(world, x, y, z, entity);
                        break;
                    case "MEIMEI":
                        SelectMeiMeiProcedure.execute(world, x, y, z, entity);
                        break;
                    case "YOROZU":
                        SelectYorozuProcedure.execute(world, x, y, z, entity);
                        break;
                    case "URAUME":
                        SelectUraumeProcedure.execute(world, x, y, z, entity);
                        break;
                    case "GETO":
                        SelectGetoProcedure.execute(world, x, y, z, entity);
                        break;
                    case "CHOSO":
                        SelectChosoProcedure.execute(world, x, y, z, entity);
                        break;
                    case "TAKAKOURO":
                        SelectTakakoUroProcedure.execute(world, x, y, z, entity);
                        break;
                    case "ITADORI":
                        SelectItadoriProcedure.execute(world, x, y, z, entity);
                        break;
                    case "KUSAKABE":
                        SelectKusakabeProcedure.execute(world, x, y, z, entity);
                        break;
                    case "JUNPE":
                        SelectJunpeProcedure.execute(world, x, y, z, entity);
                        break;
                    case "JOGO":
                        SelectJogoProcedure.execute(world, x, y, z, entity);
                        break;
                    case "TAKABA":
                        SelectTakabaProcedure.execute(world, x, y, z, entity);
                        break;
                    case "HIGURUMA":
                        SelectHigurumaProcedure.execute(world, x, y, z, entity);
                        break;
                    case "ANGEL":
                        SelectAngelProcedure.execute(world, x, y, z, entity);
                        break;
                    case "HAKARI":
                        SelectHakariProcedure.execute(world, x, y, z, entity);
                        break;
                    case "MAHITO":
                        SelectMahitoProcedure.execute(world, x, y, z, entity);
                        break;
                    case "CHOJURO":
                        SelectChojuroProcedure.execute(world, x, y, z, entity);
                        break;
                    case "NANAMI":
                        SelectNanamiProcedure.execute(world, x, y, z, entity);
                        break;
                    case "FUSHIGURO":
                        SelectFushiguroProcedure.execute(world, x, y, z, entity);
                        break;
                    case "SMALLPOXDEITY":
                        SelectSmallpoxDeityProcedure.execute(world, x, y, z, entity);
                        break;
                    case "INUMAKI":
                        SelectInumakiProcedure.execute(world, x, y, z, entity);
                        break;
                    case "KUGISAKI":
                        SelectKugisakiProcedure.execute(world, x, y, z, entity);
                        break;
                    case "HANAMI":
                        SelectHanamiProcedure.execute(world, x, y, z, entity);
                        break;
                    case "KASHIMO":
                        SelectKashimoProcedure.execute(world, x, y, z, entity);
                        break;
                    case "JINICHI":
                        SelectJinichiProcedure.execute(world, x, y, z, entity);
                        break;
                    case "NAOYA":
                        SelectNaoyaProcedure.execute(world, x, y, z, entity);
                        break;
                    default:
                        System.err.println("CT Don't Exists: " + selectedCT);
                        break;
                }

                RandomizedSelectionProcedure.execute(entity);

                if (entity instanceof Player) {
                    ItemStack stackToRemove = new ItemStack(JujutsucraftaddonModItems.RANDOM_CT_CHANGER.get());
                    entity.getInventory().clearOrCountMatchingItems(p -> stackToRemove.getItem() == p.getItem(), 1, entity.inventoryMenu.getCraftSlots());
                }
            }
        }
    }
}