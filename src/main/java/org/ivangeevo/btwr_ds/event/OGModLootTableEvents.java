package org.ivangeevo.btwr_ds.event;

import btwr.btwr_sl.tag.BTWRConventionalTags;
import com.bwt.items.BwtItems;
import com.google.common.collect.ImmutableList;
import ivangeevo.sturdy_trees.item.SturdyTreesItems;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.ivangeevo.btwr_ds.mixin.entity.ItemEntryAccessor;
import org.ivangeevo.btwr_ds.mixin.LootPoolBuilderAccessor;

import java.util.ArrayList;
import java.util.List;

public class OGModLootTableEvents
{

    private static final Identifier ZOMBIE_LOOT_TABLE_ID = ID.ofMC("entities/zombie");
    private static final RegistryKey<LootTable> ZOMBIE_LOOT_TABLE = RegistryKey.of(RegistryKeys.LOOT_TABLE, ZOMBIE_LOOT_TABLE_ID);

    private static final String[] vanillaWoodTypes = new String[]
            {"oak", "birch", "spruce", "jungle", "acacia", "dark_oak", "mangrove", "cherry", "bamboo", "crimson", "warped"};

    // tough wood types require an axe to break fully
    private static final String[] overworldToughWoodTypes = new String[]
            {"oak", "birch", "spruce", "jungle", "acacia", "dark_oak", "mangrove", "cherry"};


    private static final String[] plankLikeBlocks = new String[]
            {"planks", "slab", "stairs", "fence", "trapdoor"};

    // Register loot table changes
    public static void initialize() {
        // Improper axe break drops
        replacePlankLikeItemDrops();
        replaceDoorItemDrops();

        replacePlankItemDrops();
        replaceSawDustItemDrops();
        removeZombieDrops();
    }

    private static void removeZombieDrops() {
        modifySpecificItem(ZOMBIE_LOOT_TABLE, Items.POTATO, Items.AIR);
        modifySpecificItem(ZOMBIE_LOOT_TABLE, Items.CARROT, Items.AIR);
        modifySpecificItem(ZOMBIE_LOOT_TABLE, Items.IRON_INGOT, Items.AIR);
    }

    // Replacing all Sturdy Trees Saw Dust with Better With Time ones
    private static void replaceSawDustItemDrops() {
        for (String woodType : overworldToughWoodTypes) {
            String[] logTypes = new String[]{"stripped", "chewed", "spike"};

            for (String logType : logTypes) {
                RegistryKey<LootTable> key = RegistryKey.of(
                        RegistryKeys.LOOT_TABLE,
                        ID.ofST("blocks/log_" + woodType + "_" + logType)
                );

                modifySpecificItem(key, SturdyTreesItems.DUST_SAW, BwtItems.sawDustItem);
            }
        }
    }

    // Replacing all Sturdy Trees Stripped blocks planks drop with a stick
    private static void replacePlankItemDrops() {
        for (String woodType : overworldToughWoodTypes) {
            Item planksItem = Registries.ITEM.get(ID.ofMC(woodType + "_planks"));
            RegistryKey<LootTable> key = RegistryKey.of(
                    RegistryKeys.LOOT_TABLE,
                    ID.ofST("blocks/log_" + woodType + "_stripped")
            );

            modifySpecificItem(key, planksItem, Items.STICK);
        }
    }

    // Replacing all plank like blocks
    // (wooden slab, fence, stairs, doors, trapdoor, etc.) with saw dust
    private static void replacePlankLikeItemDrops() {
        for (String woodType : overworldToughWoodTypes) {
            for (String plankBlockType : plankLikeBlocks) {
                Item originalDrop = Registries.ITEM.get(ID.ofMC(woodType + "_" + plankBlockType));
                RegistryKey<LootTable> key = RegistryKey.of(
                        RegistryKeys.LOOT_TABLE,
                        ID.ofMC("blocks/" + woodType + "_" + plankBlockType)
                );
                modifyBreaksToSawdustDrops(key, originalDrop);
            }
        }
    }

    // Replacing all door drops with saw dust
    private static void replaceDoorItemDrops() {
        for (String woodType : overworldToughWoodTypes) {
            Item originalDrop = Registries.ITEM.get(ID.ofMC(woodType + "_door"));
            RegistryKey<LootTable> key = RegistryKey.of(
                    RegistryKeys.LOOT_TABLE,
                    ID.ofMC("blocks/" + woodType + "_door")
            );
            modifyBreaksToSawdustDrops(key, originalDrop);
        }
    }

    private static void modifySpecificItem(RegistryKey<LootTable> registryKey, Item target, Item toReplace) {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {

            // Check if the key is for target's loot table
            if (registryKey != key) return;

            tableBuilder.modifyPools(builder -> {
                List<LootPoolEntry> l = new ArrayList<>(((LootPoolBuilderAccessor) builder).getEntries().build());
                l.replaceAll(entry -> {
                    if (!(entry instanceof ItemEntry itemEntry)) return entry;
                    if (((ItemEntryAccessor) itemEntry).getItem().value() != target) return entry;
                    ((ItemEntryAccessor) entry).setItem(Registries.ITEM.getEntry(toReplace));
                    return entry;
                });

                ((LootPoolBuilderAccessor) builder).setEntries(ImmutableList.<LootPoolEntry>builder().addAll(l));
            });
        });
    }

    private static void modifySpecificItemWithCount(RegistryKey<LootTable> registryKey, Item target, Item toReplace, int count) {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {

            // Check if the key is for target's loot table
            if (registryKey != key) return;

            tableBuilder.modifyPools(builder -> {
                List<LootPoolEntry> l = new ArrayList<>(((org.ivangeevo.animageddon.mixin.LootPoolBuilderAccessor) builder).getEntries().build());
                l.replaceAll(entry -> {
                    if (!(entry instanceof ItemEntry itemEntry))
                        return entry;
                    if (((org.ivangeevo.animageddon.mixin.ItemEntryAccessor) itemEntry).getItem().value() != target)
                        return entry;

                    // Replace the item and add a SetCount function to modify the count
                    return ItemEntry.builder(toReplace)
                            .apply(() -> SetCountLootFunction.builder(ConstantLootNumberProvider.create(count)).build())
                            .build();
                });

                ((org.ivangeevo.animageddon.mixin.LootPoolBuilderAccessor) builder).setEntries(ImmutableList.<LootPoolEntry>builder().addAll(l));
            });
        });
    }

    private static void modifyBreaksToSawdustDrops(RegistryKey<LootTable> registryKey, Item target) {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {

            // Check if the key is for target's loot table
            if (registryKey != key) return;

            tableBuilder.modifyPools(builder -> {
                List<LootPoolEntry> l = new ArrayList<>(((LootPoolBuilderAccessor) builder).getEntries().build());
                l.replaceAll(entry -> {
                    if (!(entry instanceof ItemEntry itemEntry))
                        return entry;
                    if (((ItemEntryAccessor) itemEntry).getItem().value() != target)
                        return entry;

                    return ItemEntry.builder(BwtItems.sawDustItem)
                            .conditionally(MatchToolLootCondition.builder(ItemPredicate.Builder.create().tag(BTWRConventionalTags.Items.AXES_MAKE_PLANKS)).invert())
                            .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2)))
                            .alternatively(
                                    ItemEntry.builder(target)
                                            .conditionally(MatchToolLootCondition.builder(ItemPredicate.Builder.create().tag(BTWRConventionalTags.Items.AXES_MAKE_PLANKS))))
                            .build();

                });

                ((LootPoolBuilderAccessor) builder).setEntries(ImmutableList.<LootPoolEntry>builder().addAll(l));
            });
        });
    }

    private static class ID {

        static Identifier ofMC(String item) {
            return Identifier.ofVanilla(item);
        }

        /**
         * DS - Datapack Suite
         **/
        static Identifier ofDS(String item) {
            return Identifier.of("btwr_ds", item);
        }

        /**
         * BTWR: Core
         **/
        static Identifier ofBTWR(String item) {
            return Identifier.of("btwr", item);
        }

        /**
         * BWT - Better With Time
         **/
        static Identifier ofBWT(String item) {
            return Identifier.of("bwt", item);
        }

        static Identifier ofTE(String item) {
            return Identifier.of("tough_environment", item);
        }

        static Identifier ofST(String item) {
            return Identifier.of("sturdy_trees", item);
        }

        static Identifier ofSS(String item) {
            return Identifier.of("self_sustainable", item);
        }

        static Identifier ofVG(String item) {
            return Identifier.of("vegehenna", item);
        }

    }
}


