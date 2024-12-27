package org.ivangeevo.btwr_ds.datagen;

import btwr.btwrsl.tag.BTWRConventionalTags;
import btwr.core.tag.BTWRTags;
import com.bwt.items.BwtItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class DS_ItemTagProvider extends FabricTagProvider.ItemTagProvider
{
    public DS_ItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }


    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup)
    {
        // Conventional tags
        getOrCreateTagBuilder(ConventionalItemTags.STRINGS)
                .add(BwtItems.hempFiberItem);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.COOKED_EGG_FOODS)
                .add(BwtItems.friedEggItem)
                .add(BwtItems.poachedEggItem);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.DO_KNOCKBACK_ITEMS)
                .add(BwtItems.netheriteMattockItem)
                .add(BwtItems.netheriteBattleAxeItem);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.AXES_MAKE_PLANKS)
                .add(BwtItems.netheriteBattleAxeItem);

        // BTWR-SL tags
        getOrCreateTagBuilder(BTWRTags.Items.TANNED_LEATHERS)
                .add(BwtItems.tannedLeatherItem);




    }
}
