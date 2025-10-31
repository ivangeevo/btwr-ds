package org.ivangeevo.btwr_ds.datagen;

import com.bwt.items.BwtItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.CopyComponentsLootFunction;
import net.minecraft.loot.function.ExplosionDecayLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class DS_BlockLootTableProvider extends FabricBlockLootTableProvider
{

    public DS_BlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        this.overrideVanilla();
        //this.forMod();
    }

    private void overrideVanilla() {

        // override bed loot tables
        addDrop(Blocks.WHITE_BED, this.btwBedDrops());
        addDrop(Blocks.ORANGE_BED, this.btwBedDrops());
        addDrop(Blocks.MAGENTA_BED, this.btwBedDrops());
        addDrop(Blocks.LIGHT_BLUE_BED, this.btwBedDrops());
        addDrop(Blocks.YELLOW_BED, this.btwBedDrops());
        addDrop(Blocks.LIME_BED, this.btwBedDrops());
        addDrop(Blocks.PINK_BED, this.btwBedDrops());
        addDrop(Blocks.GRAY_BED, this.btwBedDrops());
        addDrop(Blocks.LIGHT_GRAY_BED, this.btwBedDrops());
        addDrop(Blocks.CYAN_BED, this.btwBedDrops());
        addDrop(Blocks.PURPLE_BED, this.btwBedDrops());
        addDrop(Blocks.BLUE_BED, this.btwBedDrops());
        addDrop(Blocks.BROWN_BED, this.btwBedDrops());
        addDrop(Blocks.GREEN_BED, this.btwBedDrops());
        addDrop(Blocks.RED_BED, this.btwBedDrops());
        addDrop(Blocks.BLACK_BED, this.btwBedDrops());

        // override other loot tables
        this.addDrop(Blocks.BONE_BLOCK, this::boneBlockDrops);

    }

    public LootTable.Builder boneBlockDrops(Block drop) {
        return this.dropsWithSilkTouch(
                drop,
                this.applyExplosionDecay(
                        drop,
                        ItemEntry.builder(Items.BONE_MEAL)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0F, 4.0F)))
                )
        );
    }

    public LootTable.Builder btwBedDrops() {
        return LootTable.builder()
                .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F))
                        .with(ItemEntry.builder(BwtItems.sawDustItem).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(3.0f)))))
                .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F))
                        .with(ItemEntry.builder(BwtItems.paddingItem).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0f)))))
                .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F))
                        .with(ItemEntry.builder(Items.STICK).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)))));
    }

    private void forMod() {

    }

}
