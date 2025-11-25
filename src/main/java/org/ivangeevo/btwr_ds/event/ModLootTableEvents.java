package org.ivangeevo.btwr_ds.event;

import com.bwt.items.BwtItems;
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
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.btwr.shared_library.tag.BTWRConventionalTags;
import org.ivangeevo.btwr_ds.mixin.vanilla.entity.ItemEntryAccessor;
import org.ivangeevo.btwr_ds.mixin.vanilla.LootPoolBuilderAccessor;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

public class ModLootTableEvents {

    private static final Identifier ZOMBIE_LOOT_TABLE_ID = ID.ofMC("entities/zombie");
    private static final Identifier ZOMBIE_VILLAGER_LOOT_TABLE_ID = ID.ofMC("entities/zombie_villager");
    private static final RegistryKey<LootTable> ZOMBIE_LOOT_TABLE = RegistryKey.of(RegistryKeys.LOOT_TABLE, ZOMBIE_LOOT_TABLE_ID);
    private static final RegistryKey<LootTable> ZOMBIE_VILLAGER_LOOT_TABLE = RegistryKey.of(RegistryKeys.LOOT_TABLE, ZOMBIE_VILLAGER_LOOT_TABLE_ID);

    private static final String[] OVERWORLD_TOUGH_WOOD_TYPES = {
            "oak", "birch", "spruce", "jungle", "acacia", "dark_oak", "mangrove", "cherry"
    };

    private static final String[] STRIPPED_LOG_VARIANTS = { "stripped", "chewed", "spike" };

    private static final String[] PLANK_LIKE_BLOCKS = { "planks", "slab", "stairs", "fence", "trapdoor" };

    public static void register() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {

            if (ZOMBIE_LOOT_TABLE.equals(key) || ZOMBIE_VILLAGER_LOOT_TABLE.equals(key)) {
                replaceItemsInPools(tableBuilder, Items.POTATO, Items.AIR);
                replaceItemsInPools(tableBuilder, Items.CARROT, Items.AIR);
                replaceItemsInPools(tableBuilder, Items.IRON_INGOT, Items.AIR);
            }

            for (String wood : OVERWORLD_TOUGH_WOOD_TYPES) {
                // Replace saw dust from sturdy trees logs
                for (String logType : STRIPPED_LOG_VARIANTS) {
                    RegistryKey<LootTable> logKey = RegistryKey.of(RegistryKeys.LOOT_TABLE, ID.ofST("blocks/log_" + wood + "_" + logType));
                    if (logKey.equals(key)) {
                        replaceItemsInPools(tableBuilder, SturdyTreesItems.DUST_SAW, BwtItems.sawDustItem);
                    }
                }

                // Replace stripped log plank drops with sticks
                RegistryKey<LootTable> strippedLogKey = RegistryKey.of(RegistryKeys.LOOT_TABLE, ID.ofST("blocks/log_" + wood + "_stripped"));
                if (strippedLogKey.equals(key)) {
                    Item planksItem = Registries.ITEM.get(ID.ofMC(wood + "_planks"));
                    replaceItemsInPools(tableBuilder, planksItem, Items.STICK);
                }

                // Replace plank-like blocks drops with saw dust
                for (String blockType : PLANK_LIKE_BLOCKS) {
                    RegistryKey<LootTable> plankKey = RegistryKey.of(RegistryKeys.LOOT_TABLE, ID.ofMC("blocks/" + wood + "_" + blockType));
                    if (plankKey.equals(key)) {
                        modifyBreaksToSawdustDrops(tableBuilder, Registries.ITEM.get(ID.ofMC(wood + "_" + blockType)), 2);
                    }
                }

                // Replace door drops with saw dust
                RegistryKey<LootTable> doorKey = RegistryKey.of(RegistryKeys.LOOT_TABLE, ID.ofMC("blocks/" + wood + "_door"));
                if (doorKey.equals(key)) {
                    modifyDoorBreaksToSawdustDrops(tableBuilder, Registries.ITEM.get(ID.ofMC(wood + "_door")), 2);
                }
            }
        });
    }

    private static void replaceItemsInPools(LootTable.Builder tableBuilder, Item target, Item replacement) {
        tableBuilder.modifyPools(poolBuilder -> {
            List<LootPoolEntry> entries = new ArrayList<>(((LootPoolBuilderAccessor) poolBuilder).getEntries().build());
            entries.replaceAll(entry -> {
                if (!(entry instanceof ItemEntry itemEntry)) return entry;
                if (((ItemEntryAccessor) itemEntry).getItem().value() != target) return entry;
                ((ItemEntryAccessor) entry).setItem(Registries.ITEM.getEntry(replacement));
                return entry;
            });
            ((LootPoolBuilderAccessor) poolBuilder).setEntries(ImmutableList.<LootPoolEntry>builder().addAll(entries));
        });
    }

    private static void replaceItemWithToolCheck(LootTable.Builder tableBuilder, Item target, Item replacement, int count, TagKey<Item> toolTag) {
        tableBuilder.modifyPools(poolBuilder -> {
            List<LootPoolEntry> entries = new ArrayList<>(((LootPoolBuilderAccessor) poolBuilder).getEntries().build());
            entries.replaceAll(entry -> {
                if (!(entry instanceof ItemEntry itemEntry)) return entry;
                if (((ItemEntryAccessor) itemEntry).getItem().value() != target) return entry;
                return ItemEntry.builder(replacement)
                        .conditionally(MatchToolLootCondition.builder(ItemPredicate.Builder.create().tag(toolTag)))
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(count)))
                        .build();
            });
            ((LootPoolBuilderAccessor) poolBuilder).setEntries(ImmutableList.<LootPoolEntry>builder().addAll(entries));
        });
    }

    private static void modifyBreaksToSawdustDrops(LootTable.Builder tableBuilder, Item target, int count) {
        tableBuilder.modifyPools(poolBuilder -> {
            List<LootPoolEntry> entries = new ArrayList<>(((LootPoolBuilderAccessor) poolBuilder).getEntries().build());
            entries.replaceAll(entry -> {
                if (!(entry instanceof ItemEntry itemEntry)) return entry;
                if (((ItemEntryAccessor) itemEntry).getItem().value() != target) return entry;

                return ItemEntry.builder(BwtItems.sawDustItem)
                        .conditionally(MatchToolLootCondition.builder(ItemPredicate.Builder.create().tag(BTWRConventionalTags.Items.AXES_MAKE_PLANKS)).invert())
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(count)))
                        .alternatively(ItemEntry.builder(target).conditionally(MatchToolLootCondition.builder(ItemPredicate.Builder.create().tag(BTWRConventionalTags.Items.AXES_MAKE_PLANKS))))
                        .build();
            });
            ((LootPoolBuilderAccessor) poolBuilder).setEntries(ImmutableList.<LootPoolEntry>builder().addAll(entries));
        });
    }

    private static void modifyDoorBreaksToSawdustDrops(LootTable.Builder tableBuilder, Item target, int count) {
        tableBuilder.modifyPools(poolBuilder -> {
            List<LootPoolEntry> newEntries = new ArrayList<>();
            for (LootPoolEntry entry : ((LootPoolBuilderAccessor) poolBuilder).getEntries().build()) {
                if (entry instanceof ItemEntry itemEntry &&
                        ((ItemEntryAccessor) itemEntry).getItem().value() == target) {

                    newEntries.add(ItemEntry.builder(BwtItems.sawDustItem)
                            .conditionally(MatchToolLootCondition.builder(
                                    ItemPredicate.Builder.create().tag(BTWRConventionalTags.Items.AXES_MAKE_PLANKS)).invert())
                            .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(count)))
                            .build());

                    newEntries.add(ItemEntry.builder(target)
                            .conditionally(MatchToolLootCondition.builder(
                                    ItemPredicate.Builder.create().tag(BTWRConventionalTags.Items.AXES_MAKE_PLANKS)))
                            .build());

                } else {
                    newEntries.add(entry);
                }
            }

            ((LootPoolBuilderAccessor) poolBuilder).setEntries(ImmutableList.<LootPoolEntry>builder().addAll(newEntries));
        });
    }



    private static class ID {
        static Identifier ofMC(String item) { return Identifier.ofVanilla(item); }
        static Identifier ofDS(String item) { return Identifier.of("btwr_ds", item); }
        static Identifier ofBTWR(String item) { return Identifier.of("btwr", item); }
        static Identifier ofBWT(String item) { return Identifier.of("bwt", item); }
        static Identifier ofTE(String item) { return Identifier.of("tough_environment", item); }
        static Identifier ofST(String item) { return Identifier.of("sturdy_trees", item); }
        static Identifier ofSS(String item) { return Identifier.of("self_sustainable", item); }
        static Identifier ofVG(String item) { return Identifier.of("vegehenna", item); }
    }
}
