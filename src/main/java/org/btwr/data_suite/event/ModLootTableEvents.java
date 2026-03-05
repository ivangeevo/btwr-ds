package org.btwr.data_suite.event;

import com.bwt.items.BwtItems;
import net.fabricmc.fabric.api.loot.v3.LootTableSource;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.AnyOfLootCondition;
import net.minecraft.loot.condition.InvertedLootCondition;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.entry.AlternativeEntry;
import net.minecraft.registry.RegistryWrapper;
import org.btwr.sturdy_trees.item.SturdyTreesItems;
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
import org.btwr.shared_library.api.tag.BTWRConventionalTags;
import org.btwr.shared_library.util.utils.IdUtils;
import org.btwr.data_suite.mixin.vanilla.entity.ItemEntryAccessor;
import org.btwr.data_suite.mixin.vanilla.LootPoolBuilderAccessor;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

public class ModLootTableEvents {

    private static final Identifier ZOMBIE_LOOT_TABLE_ID = IdUtils.ofMC("entities/zombie");
    private static final Identifier ZOMBIE_VILLAGER_LOOT_TABLE_ID = IdUtils.ofMC("entities/zombie_villager");
    private static final RegistryKey<LootTable> ZOMBIE_LOOT_TABLE = RegistryKey.of(RegistryKeys.LOOT_TABLE, ZOMBIE_LOOT_TABLE_ID);
    private static final RegistryKey<LootTable> ZOMBIE_VILLAGER_LOOT_TABLE = RegistryKey.of(RegistryKeys.LOOT_TABLE, ZOMBIE_VILLAGER_LOOT_TABLE_ID);

    private static final String[] OVERWORLD_TOUGH_WOOD_TYPES = {
            "oak", "birch", "spruce", "jungle", "acacia", "dark_oak", "mangrove", "cherry"
    };

    private static final String[] STRIPPED_LOG_VARIANTS = { "stripped", "chewed", "spike" };

    private static final String[] PLANK_LIKE_BLOCKS = { "planks", "slab", "stairs", "fence", "trapdoor" };

    // Condition for MODERN_AXES or ADVANCED_AXES tag check
    /**
    private static final LootCondition.Builder WITH_STRONG_AXE = AnyOfLootCondition.builder(
            MatchToolLootCondition.builder(
                    ItemPredicate.Builder.create().tag(BTWRConventionalTags.Items.MODERN_AXES)
            ),
            MatchToolLootCondition.builder(
                    ItemPredicate.Builder.create().tag(BTWRConventionalTags.Items.ADVANCED_AXES)
            )
    );
     **/
    private static final LootCondition.Builder WITH_STRONG_AXE = MatchToolLootCondition.builder(
            ItemPredicate.Builder.create().items(Items.DIAMOND_AXE)
    );

    private static final LootCondition.Builder WITHOUT_STRONG_AXE = InvertedLootCondition.builder(WITH_STRONG_AXE);

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
                    RegistryKey<LootTable> logKey = RegistryKey.of(RegistryKeys.LOOT_TABLE, IdUtils.ofST("blocks/log_" + wood + "_" + logType));
                    if (logKey.equals(key)) {
                        replaceItemsInPools(tableBuilder, SturdyTreesItems.DUST_SAW, BwtItems.sawDustItem);
                    }
                }

                // Replace stripped log plank drops with sticks
                RegistryKey<LootTable> strippedLogKey = RegistryKey.of(RegistryKeys.LOOT_TABLE, IdUtils.ofST("blocks/log_" + wood + "_stripped"));
                if (strippedLogKey.equals(key)) {
                    Item planksItem = Registries.ITEM.get(IdUtils.ofMC(wood + "_planks"));
                    replaceItemsInPools(tableBuilder, planksItem, Items.STICK);
                }

                // Replace plank-like blocks drops with saw dust
                for (String blockType : PLANK_LIKE_BLOCKS) {
                    RegistryKey<LootTable> plankKey = RegistryKey.of(RegistryKeys.LOOT_TABLE, IdUtils.ofMC("blocks/" + wood + "_" + blockType));
                    if (plankKey.equals(key)) {
                        modifyBreaksToSawdustDrops(tableBuilder, Registries.ITEM.get(IdUtils.ofMC(wood + "_" + blockType)), 2);
                    }
                }

                // Replace door drops with saw dust
                RegistryKey<LootTable> doorKey = RegistryKey.of(RegistryKeys.LOOT_TABLE, IdUtils.ofMC("blocks/" + wood + "_door"));
                if (doorKey.equals(key)) {
                    modifyDoorBreaksToSawdustDrops(tableBuilder, Registries.ITEM.get(IdUtils.ofMC(wood + "_door")), 2);
                }
            }
        });

        //LootTableEvents.REPLACE.register(ModLootTableEvents::replaceChestLootTable);
    }

    // Doesn't seem to work properly
    private static LootTable replaceChestLootTable(RegistryKey<LootTable> key, LootTable original, LootTableSource source, RegistryWrapper.WrapperLookup registries) {
        if (source.isBuiltin()) {
            if (Blocks.CHEST.getLootTableKey().equals(key)) {
                LootTable.Builder newTable = LootTable.builder();

                newTable.pool(LootPool.builder()
                        .with(ItemEntry.builder(Items.CHEST))
                        .conditionally(withStrongAxe())
                );

                newTable.pool(LootPool.builder()
                        .with(ItemEntry.builder(BwtItems.sawDustItem)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(6))))
                        .conditionally(withoutStrongAxe())
                );

                newTable.pool(LootPool.builder()
                        .with(ItemEntry.builder(Items.STICK)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2))))
                        .conditionally(withoutStrongAxe())
                );

                return newTable.build();
            }
        }
        return null;
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

    private static LootCondition.Builder withStrongAxe() {
        return AnyOfLootCondition.builder(
                MatchToolLootCondition.builder(
                        ItemPredicate.Builder.create().tag(BTWRConventionalTags.Items.MODERN_AXES)
                ),
                MatchToolLootCondition.builder(
                        ItemPredicate.Builder.create().tag(BTWRConventionalTags.Items.ADVANCED_AXES)
                )
        );
    }

    private static LootCondition.Builder withoutStrongAxe() {
        return InvertedLootCondition.builder(withStrongAxe());
    }
}