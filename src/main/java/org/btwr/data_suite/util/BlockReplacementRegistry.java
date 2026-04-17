package org.btwr.data_suite.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

import java.util.HashMap;
import java.util.Map;

/**
 * Registry for replacing blocks during world generation or other bulk block placement.
 * Allows registering a replacement block and a custom {@link StateTransformer}
 * that defines how to derive the final {@link BlockState}.
 */
public class BlockReplacementRegistry {
    /**
     * Functional interface for transforming the original blockstate into the
     * final blockstate used for the replacement.
     *
     * @param original The original state being replaced.
     * @param replacementDefault The default state of the replacement block.
     * @return The blockstate that should actually be placed.
     */
    @FunctionalInterface
    public interface StateTransformer {
        BlockState transform(BlockState original, BlockState replacementDefault);
    }

    /**
     * Internal record storing the replacement block and its associated transformer.
     */
    private record ReplacementInfo(Block block, StateTransformer transformer) {}

    private static final Map<Block, ReplacementInfo> REPLACEMENTS = new HashMap<>();

    /**
     * Registers a block replacement that uses the replacement's default state.
     * No properties are copied from the original state.
     *
     * @param original The block being replaced.
     * @param replacement The block to place instead.
     */
    public static void registerReplacement(Block original, Block replacement) {
        registerReplacement(original, replacement, (oldState, newState) -> newState);
    }

    /**
     * Registers a block replacement with a custom transformation rule.
     * The transformer decides how the final state should be created.
     *
     * @param original The block being replaced.
     * @param replacement The block to place instead.
     * @param transformer Logic for converting the original state into the final replacement state.
     */
    public static void registerReplacement(Block original, Block replacement, StateTransformer transformer) {
        REPLACEMENTS.put(original, new ReplacementInfo(replacement, transformer));
    }

    /**
     * Retrieves the replacement info for a block.
     * Falls back to "no replacement" behavior: original block + identity transformer.
     */
    private static ReplacementInfo getInfo(Block original) {
        return REPLACEMENTS.getOrDefault(
                original,
                new ReplacementInfo(original, (old, rep) -> old)
        );
    }

    /**
     * Returns the replacement block for the given block.
     * If no replacement is registered, returns the original block.
     *
     * @param original The original block.
     * @return The replacement block or the original block.
     */
    public static Block getReplacementFor(Block original) {
        return getInfo(original).block();
    }

    /**
     * Returns the transformer that should be used for this block.
     * If no replacement is registered, returns an identity transformer
     * that produces the original state unchanged.
     *
     * @param original The original block.
     * @return The transformer associated with this block.
     */
    public static StateTransformer getTransformer(Block original) {
        return getInfo(original).transformer();
    }
}
