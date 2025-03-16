package org.ivangeevo.btwr_ds.mixin;

import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemDispenserBehavior.class)
public abstract class ItemDispenserBehaviorMixin {

    // Change the position of the spawned item without Y variation(no going upwards)
    //@Inject(method = "spawnItem", at = @At("HEAD"), cancellable = true)
    private static void changeSpawnItemYPos(World world, ItemStack stack, int speed, Direction side, Position pos, CallbackInfo ci) {
        double d = pos.getX();
        double e = pos.getY();
        double f = pos.getZ();
        e = side.getAxis() == Direction.Axis.Y ? (e -= 0.125) : (e -= 0.15625);
        ItemEntity itemEntity = new ItemEntity(world, d, e, f, stack);
        double g = world.random.nextDouble() * 0.1 + 0.2;
        //double g = 0.3;
        itemEntity.setVelocity(
                world.random.nextTriangular((double)side.getOffsetX() * g, 0.0172275 * (double)speed),
                0,
                world.random.nextTriangular((double)side.getOffsetZ() * g, 0.0172275 * (double)speed)
        );
        world.spawnEntity(itemEntity);
        ci.cancel();
    }
}
