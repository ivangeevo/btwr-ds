package org.btwr.data_suite.mixin.vanilla.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.world.World;
import org.btwr.data_suite.data.attachment.ItemDespawnAttachedData;
import org.btwr.data_suite.data.ModDataAttachments;
import org.spongepowered.asm.mixin.Mixin;

import java.util.UUID;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {
    public ItemEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    //@ModifyConstant(method = "tick", constant = @Constant(intValue = 6000))
    private int modifyDespawnTime(int original) {
        ItemEntity self = (ItemEntity) (Object) this;
        UUID ownerId = self.getAttached(ModDataAttachments.DROP_OWNER);
        if (ownerId == null) return original;

        var world = self.getWorld();
        if (world.getServer() == null) return original;

        var owner = world.getServer().getPlayerManager().getPlayer(ownerId);
        if (owner == null) return original;

        var data = owner.getAttachedOrElse(ModDataAttachments.ITEM_DESPAWN, ItemDespawnAttachedData.DEFAULT);
        if (data == null) return original;

        return switch (data.type()) {
            case NEVER, PERSIST_UNTIL_PLAYER_REDEATH -> Integer.MAX_VALUE;
            case FULL_DAY -> 24000;
            default -> original;
        };
    }
}