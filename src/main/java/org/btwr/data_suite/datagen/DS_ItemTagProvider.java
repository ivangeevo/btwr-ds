package org.btwr.data_suite.datagen;

import org.btwr.animageddon.tag.ModTags;
import org.btwr.core.item.BTWR_Items;
import org.btwr.core.tag.BTWRTags;
import org.btwr.shared_library.api.tag.BTWRConventionalTags;
import com.bwt.items.BwtItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

import static org.btwr.animageddon.item.ModItems.BURNED_MEAT;
import static org.btwr.vegehenna.item.ModItems.*;

public class DS_ItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public DS_ItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        // Vanilla tags
        getOrCreateTagBuilder(ItemTags.WOLF_FOOD)
                .add(BTWR_Items.SANDWICH)
                .add(BTWR_Items.HAM_AND_EGGS)
                .add(BTWR_Items.STEAK_AND_POTATOES)
                .add(BTWR_Items.RAW_KEBAB)
                .add(BTWR_Items.COOKED_KEBAB)
                .add(BTWR_Items.STEAK_DINNER)
                .add(BTWR_Items.PORK_DINNER)
                .add(BTWR_Items.WOLF_DINNER)
                .add(BTWR_Items.BEAST_LIVER_RAW)
                .add(BTWR_Items.BEAST_LIVER_COOKED)
                .add(BURNED_MEAT);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.CHICKEN_TEMPT_ITEMS)
                .add(BwtItems.hempSeedsItem)
                .add(CARROT_SEEDS);

        // Conventional tags
        getOrCreateTagBuilder(ConventionalItemTags.STRINGS)
                .add(BwtItems.hempFiberItem);

        // BTWR Conventional tags
        getOrCreateTagBuilder(BTWRConventionalTags.Items.COOKED_EGG_FOODS)
                .add(BwtItems.friedEggItem)
                .add(BwtItems.poachedEggItem);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.DO_KNOCKBACK_ITEMS)
                .add(BwtItems.netheriteMattockItem)
                .add(BwtItems.netheriteBattleAxeItem);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.AXES_MAKE_PLANKS)
                .add(BwtItems.netheriteBattleAxeItem);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.ON_CRAFT_SHEARS_CUT_SOUND)
                .add(BwtItems.strapItem);

        // Misc tags
        getOrCreateTagBuilder(BTWRTags.Items.TANNED_LEATHERS)
                .add(BwtItems.tannedLeatherItem);

        getOrCreateTagBuilder(ModTags.Items.PIG_BREEDING_ITEMS)
                .add(CHOCOLATE);

    }
}
