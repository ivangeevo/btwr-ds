package org.ivangeevo.btwr_ds.event;

import com.bwt.items.BwtItems;
import ivangeevo.sturdy_trees.item.SturdyTreesItems;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import org.btwr.core.item.BTWR_Items;
import org.ivangeevo.vegehenna.item.ModItems;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;

/** Handles modification of items max allowed count**/
public class ItemCountModificationEvents
{

    public static void register() {
        DefaultItemComponentEvents.MODIFY.register(context -> {
            modifyTo16(context);
            modifyTo8(context);
        });
    }

    private static void modifyTo16(DefaultItemComponentEvents.ModifyContext context) {
        for (Item item : rawFoods()) {
            context.modify(item, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 16));
        }

        for (Item item : cookedFoods()) {
            context.modify(item, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 16));
        }

        for (Item item : miscFoods()) {
            context.modify(item, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 16));
        }

        for (Item item : miscItems()) {
            context.modify(item, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 16));
        }
    }

    private static void modifyTo8(DefaultItemComponentEvents.ModifyContext context) {
        context.modify(Items.POTION, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 8));
        context.modify(Items.SPLASH_POTION, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 8));
        context.modify(Items.LINGERING_POTION, builder -> builder.add(DataComponentTypes.MAX_STACK_SIZE, 8));
    }

    private static ArrayList<Item> rawFoods() {
        ArrayList<Item> list = new ArrayList<>();

        list.add(Items.APPLE);
        list.add(Items.MUSHROOM_STEW);
        list.add(Items.CARROT);
        list.add(Items.POTATO);
        list.add(Items.SWEET_BERRIES);
        list.add(Items.GLOW_BERRIES);
        list.add(Items.HONEY_BOTTLE);

        list.add(Items.PORKCHOP);
        list.add(Items.COD);
        list.add(Items.SALMON);
        list.add(Items.TROPICAL_FISH);
        list.add(Items.PUFFERFISH);
        list.add(Items.BEEF);
        list.add(Items.CHICKEN);
        list.add(Items.RABBIT);
        list.add(Items.MUTTON);
        list.add(BwtItems.rawEggItem);
        list.add(BwtItems.wolfChopItem);

        return list;
    }

    @Unique
    private static ArrayList<Item> cookedFoods() {
        ArrayList<Item> list = new ArrayList<>();

        // vanilla non-meats
        list.add(Items.BREAD);
        list.add(Items.COOKIE);
        list.add(Items.BAKED_POTATO);
        list.add(Items.GOLDEN_CARROT);
        list.add(Items.CHORUS_FRUIT);
        list.add(Items.BEETROOT);
        list.add(Items.BEETROOT_SOUP);

        // vanilla meats
        list.add(Items.COOKED_PORKCHOP);
        list.add(Items.COOKED_COD);
        list.add(Items.COOKED_SALMON);
        list.add(Items.COOKED_BEEF);
        list.add(Items.COOKED_CHICKEN);
        list.add(Items.COOKED_RABBIT);
        list.add(Items.RABBIT_STEW);
        list.add(Items.COOKED_MUTTON);

        // btwr: core
        list.add(BTWR_Items.EGG_SCRAMBLED_COOKED);
        list.add(BTWR_Items.MUSHROOM_OMELETTE_COOKED);
        list.add(BTWR_Items.SANDWICH);
        list.add(BTWR_Items.HAM_AND_EGGS);
        list.add(BTWR_Items.CHOWDER);
        list.add(BTWR_Items.STEAK_AND_POTATOES);
        list.add(BTWR_Items.COOKED_KEBAB);
        list.add(BTWR_Items.STEAK_DINNER);
        list.add(BTWR_Items.PORK_DINNER);
        list.add(BTWR_Items.WOLF_DINNER);
        list.add(BTWR_Items.CHICKEN_SOUP);
        list.add(BTWR_Items.HEARTY_STEW);
        list.add(BTWR_Items.BEAST_LIVER_COOKED);


        // vegehenna
        list.add(ModItems.BOILED_POTATO);
        list.add(ModItems.COOKED_CARROT);

        // bwt
        list.add(BwtItems.friedEggItem);
        list.add(BwtItems.poachedEggItem);
        list.add(BwtItems.cookedWolfChopItem);
        list.add(BwtItems.donutItem);

        return list;
    }

    @Unique
    private static ArrayList<Item> miscFoods() {
        ArrayList<Item> list = new ArrayList<>();

        list.add(Items.MELON);
        list.add(Items.PUMPKIN);
        list.add(Items.MELON_SLICE);
        list.add(Items.COCOA_BEANS);
        list.add(Items.GOLDEN_APPLE);
        list.add(Items.ENCHANTED_GOLDEN_APPLE);
        list.add(Items.DRIED_KELP);
        list.add(Items.POISONOUS_POTATO);
        list.add(Items.BROWN_MUSHROOM);
        list.add(Items.RED_MUSHROOM);

        list.add(BTWR_Items.CREEPER_OYSTERS);

        list.add(ModItems.CHOCOLATE);
        list.add(ModItems.CHOCOLATE_MILK);
        list.add(ModItems.PASTRY_UNCOOKED_CAKE);
        list.add(ModItems.PASTRY_UNCOOKED_PUMPKIN_PIE);
        list.add(ModItems.PASTRY_UNCOOKED_COOKIES);

        return list;
    }

    @Unique
    private static ArrayList<Item> miscItems() {
        ArrayList<Item> list = new ArrayList<>();

        list.add(Items.BONE);
        list.add(Items.EGG);
        list.add(Items.ROTTEN_FLESH);
        list.add(Items.SPIDER_EYE);
        list.add(Items.SUSPICIOUS_STEW);
        list.add(Items.OMINOUS_BOTTLE);

        list.add(BwtItems.soulUrnItem);
        list.add(SturdyTreesItems.STUMP_REMOVER);

        return list;
    }

}
