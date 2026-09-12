package org.btwr.data_suite.datagen;

import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.btwr.animageddon.tag.ModTags;
import org.btwr.core.tag.BTWRTags;
import org.btwr.self_sustainable.item.ModItems;
import org.btwr.shared_library.api.tag.BTWRConventionalTags;
import com.bwt.items.BwtItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

import static org.btwr.animageddon.item.ModItems.BURNED_MEAT;
import static org.btwr.core.item.ModItems.*;
import static org.btwr.self_sustainable.item.ModItems.*;
import static org.btwr.vegehenna.item.ModItems.*;

public class DS_ItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public DS_ItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        addToVanillaTags();
        addToModTags();
        addToOtherModTags();
        addToConventionalTags();
        addToBTWRConventionalTags();
    }

    private void addToVanillaTags() {
        getOrCreateTagBuilder(ItemTags.WOLF_FOOD)
                .add(SANDWICH)
                .add(HAM_AND_EGGS)
                .add(STEAK_AND_POTATOES)
                .add(RAW_KEBAB)
                .add(COOKED_KEBAB)
                .add(STEAK_DINNER)
                .add(PORK_DINNER)
                .add(WOLF_DINNER)
                .add(BEAST_LIVER_RAW)
                .add(BEAST_LIVER_COOKED)
                .add(BURNED_MEAT);
    }

    private void addToModTags() {
        getOrCreateTagBuilder(ModTags.Items.PIG_BREEDING_ITEMS)
                .add(CHOCOLATE);
    }

    private void addToOtherModTags() {
        getOrCreateTagBuilder(BTWRTags.Items.TANNED_LEATHERS)
                .add(BwtItems.tannedLeatherItem);
    }

    private void addToConventionalTags() {
        getOrCreateTagBuilder(ConventionalItemTags.STRINGS)
                .add(BwtItems.hempFiberItem);

        // Replacing because Self-Sustainable adds scrambled eggs for compat if BWT isn't present, and we don't want that in the modpack
        getOrCreateTagBuilder(BTWRConventionalTags.Items.COOKED_EGG_FOODS)
                .setReplace(true)
                .addOptional(Identifier.of("bwt", "fried_egg"))
                .addOptional(Identifier.of("bwt", "poached_egg"));
    }

    private void addToBTWRConventionalTags() {
        getOrCreateTagBuilder(BTWRConventionalTags.Items.CHICKEN_TEMPT_ITEMS)
                .add(BwtItems.hempSeedsItem)
                .add(CARROT_SEEDS);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.MODERN_AXES)
                .setReplace(true)
                .add(Items.IRON_AXE)
                .add(Items.DIAMOND_AXE);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.DO_KNOCKBACK_ITEMS)
                .add(BwtItems.netheriteMattockItem)
                .add(BwtItems.netheriteBattleAxeItem);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.AXES_MAKE_PLANKS)
                .add(BwtItems.netheriteBattleAxeItem);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.ON_CRAFT_SHEARS_CUT_SOUND)
                .add(BwtItems.strapItem);

        // Replace the cooked egg foods because "Self Sustainable" adds scrambled eggs for compat, but we don't need that in BTWR
        getOrCreateTagBuilder(BTWRConventionalTags.Items.COOKED_EGG_FOODS)
                .setReplace(true)
                .addOptional(Identifier.of("bwt", "fried_egg"))
                .addOptional(Identifier.of("bwt", "poached_egg"));
    }
}
