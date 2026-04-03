package org.btwr.data_suite.data;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import org.btwr.data_suite.BTWRDSMod;
import org.btwr.data_suite.data.attachment.BeastTimerAttachedData;
import org.btwr.data_suite.data.attachment.ItemDespawnAttachedData;
import org.btwr.data_suite.data.attachment.MagneticPointAttachedData;
import org.btwr.shared_library.api.data.EntityAttachmentBase;
import org.btwr.shared_library.api.event.BTWREvents;
import org.btwr.shared_library.util.utils.IdUtils;

import java.util.UUID;

public class ModDataAttachments {

    public static final AttachmentType<MagneticPointAttachedData> MAGNETIC_POINT = AttachmentRegistry.create(
            Identifier.of(BTWRDSMod.MOD_ID, "magnetic_point"),
            builder -> builder
                    .initializer(() -> MagneticPointAttachedData.DEFAULT)
                    .persistent(MagneticPointAttachedData.CODEC)
                    .syncWith(MagneticPointAttachedData.PACKET_CODEC, AttachmentSyncPredicate.all())
    );

    public static final AttachmentType<ItemDespawnAttachedData> ITEM_DESPAWN =
            AttachmentRegistry.createPersistent(Identifier.of(BTWRDSMod.MOD_ID, "item_despawn"), ItemDespawnAttachedData.CODEC);

    public static final AttachmentType<UUID> DROP_OWNER =
            AttachmentRegistry.createPersistent(Identifier.of(BTWRDSMod.MOD_ID, "drop_owner"), Codec.STRING.xmap(UUID::fromString, UUID::toString));


    public static final AttachmentType<BeastTimerAttachedData> BEAST_TIMER = AttachmentRegistry.create(
            IdUtils.ofDS("beast_timer"),
            builder -> builder
                    .initializer(() -> new BeastTimerAttachedData(false, BeastTimerAttachedData.CONVERSION_TIME))
                    .persistent(BeastTimerAttachedData.CODEC)
                    .syncWith(BeastTimerAttachedData.PACKET_CODEC, AttachmentSyncPredicate.all())
    );

    public static void register() {
        BTWRDSMod.LOGGER.info("Registering {} attachments", BTWRDSMod.MOD_ID);
        // Technically this method can stay empty, but some developers like to notify
        // the console, that certain parts of the mod have been successfully initialized

        BTWREvents.LIVING_TICK.add(living -> {
            if (living.getType() == EntityType.WOLF) {
                tickAndSync(BEAST_TIMER, living);
            }
        });
    }

    private static <T extends Entity, A extends EntityAttachmentBase<T>> void tickAndSync(AttachmentType<A> type, LivingEntity entity) {
        A attachment = entity.getAttachedOrCreate(type);
        attachment.tick((T) entity);
        if (attachment.isDirty()) {
            entity.setAttached(type, attachment);
        }
    }

}