package org.ivangeevo.btwr_ds.data;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.util.Identifier;
import org.ivangeevo.btwr_ds.BTWRDSMod;

import java.util.UUID;

public class ModAttachments {
    private static final Identifier ITEM_DESPAWN_ID = Identifier.of(BTWRDSMod.MOD_ID, "item_despawn");
    private static final Identifier DROP_OWNER_ID = Identifier.of(BTWRDSMod.MOD_ID, "drop_owner");

    public static final AttachmentType<ItemDespawnData> ITEM_DESPAWN =
            AttachmentRegistry.createPersistent(ITEM_DESPAWN_ID, ItemDespawnData.CODEC);

    public static final AttachmentType<UUID> DROP_OWNER =
            AttachmentRegistry.createPersistent(DROP_OWNER_ID, Codec.STRING.xmap(UUID::fromString, UUID::toString));

    public static void register() {}
}
