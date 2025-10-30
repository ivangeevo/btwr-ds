package org.ivangeevo.btwr_ds.mixin.vanilla;

import com.google.common.collect.ImmutableList;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.LootPoolEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LootPool.Builder.class)
public interface LootPoolBuilderAccessor {

    @Accessor("entries")
    ImmutableList.Builder<LootPoolEntry> getEntries(); // Или `List<LootPoolEntry.Builder<?>>` в зависимост от версията

    @Accessor("entries")
    void setEntries(ImmutableList.Builder<LootPoolEntry> entries);
}
