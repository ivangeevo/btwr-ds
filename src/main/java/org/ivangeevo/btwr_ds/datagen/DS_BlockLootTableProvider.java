package org.ivangeevo.btwr_ds.datagen;

import btwr.core.item.BTWR_Items;
import btwr.core.tag.BTWRConventionalTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.condition.SurvivesExplosionLootCondition;
import net.minecraft.loot.entry.AlternativeEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import org.tough_environment.block.ModBlocks;
import org.tough_environment.datagen.TELootTableProvider;
import org.tough_environment.item.ModItems;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class DS_BlockLootTableProvider extends FabricBlockLootTableProvider
{
    public static final LootCondition.Builder WITH_SHOVEL_FULLY_HARVESTS = MatchToolLootCondition.builder(ItemPredicate.Builder.create().tag(BTWRConventionalTags.Items.SHOVELS_HARVEST_FULL_BLOCK));
    public static final LootCondition.Builder WITHOUT_HOE = MatchToolLootCondition.builder(ItemPredicate.Builder.create().tag(ItemTags.HOES)).invert();


    public DS_BlockLootTableProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate()
    {
        // change grass block to drop hemp seeds
        addDrop(Blocks.GRASS_BLOCK, dropsForLooseAggregate(Blocks.GRASS_BLOCK, ModBlocks.DIRT_LOOSE, WITH_SHOVEL_FULLY_HARVESTS, ModItems.PILE_DIRT, 6,
                null, Map.of(Items.IRON_HOE, 0.03f, Items.GOLDEN_HOE, 0.04f, Items.DIAMOND_HOE, 0.06f, Items.NETHERITE_HOE, 0.11f), BTWR_Items.HEMP_SEEDS));

    }

    public LootTable.Builder dropsForLooseAggregate(Block dropWithSilkTouch, Block looseDrop, LootCondition.Builder toolCondition, Item pileDrop, int pileDropCount, List<AdditionalDrop> additionalDrops, Map<Item, Float> hoeDrops, Item hoeDroppedItem) {
        LootTable.Builder builder = dropsForLooseAggregate(dropWithSilkTouch, looseDrop, toolCondition, pileDrop, pileDropCount, additionalDrops); // Call the previous method

        // Add hoe-specific drops
        if (hoeDrops != null) {
            for (Map.Entry<Item, Float> entry : hoeDrops.entrySet()) {
                Item hoe = entry.getKey();
                float chance = entry.getValue();

                LootPool.Builder hoePool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0f))
                        .with(ItemEntry.builder(hoeDroppedItem)
                                .conditionally(MatchToolLootCondition.builder(ItemPredicate.Builder.create().items(hoe)))
                                .conditionally(RandomChanceLootCondition.builder(chance)));

                builder.pool(hoePool);
            }
        }

        return builder;
    }

    // Overloaded method: Adds additional drops
    // Only gravel from flint for now (could be abstracted further to be suitable for different cases as well)
    public LootTable.Builder dropsForLooseAggregate(Block dropWithSilkTouch, Block looseDrop, LootCondition.Builder toolCondition, Item pileDrop, int pileDropCount, List<AdditionalDrop> additionalDrops) {
        LootTable.Builder builder = dropsForLooseAggregate(dropWithSilkTouch, looseDrop, toolCondition, pileDrop, pileDropCount); // Call the previous method

        // Add additional drops
        if (additionalDrops != null) {
            for (AdditionalDrop additionalDrop : additionalDrops) {
                LootPool.Builder additionalPool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0f))
                        .with(ItemEntry.builder(additionalDrop.item)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(additionalDrop.count)))
                                .conditionally(RandomChanceLootCondition.builder(additionalDrop.chance)))
                        .conditionally(SurvivesExplosionLootCondition.builder());

                builder.pool(additionalPool);
            }
        }

        return builder;
    }


    // Handles only basic loose aggregate drops
    public LootTable.Builder dropsForLooseAggregate(Block dropWithSilkTouch, Block looseDrop, LootCondition.Builder toolCondition, Item pileDrop, int pileDropCount) {
        // Define the main loot pool with conditions
        AlternativeEntry.Builder alternativeEntry = AlternativeEntry.builder(
                ItemEntry.builder(dropWithSilkTouch).conditionally(createSilkTouchCondition()),
                ItemEntry.builder(looseDrop).conditionally(toolCondition),
                ItemEntry.builder(pileDrop).conditionally(WITHOUT_HOE)
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(pileDropCount)))
                        .conditionally(dropWithSilkTouch == Blocks.GRASS_BLOCK ? WITHOUT_HOE : SurvivesExplosionLootCondition.builder())
        );

        LootPool.Builder mainPool = LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1.0f))
                .with(alternativeEntry);


        return LootTable.builder().pool(mainPool);
    }

    public record AdditionalDrop(Item item, int count, float chance) {}


}
