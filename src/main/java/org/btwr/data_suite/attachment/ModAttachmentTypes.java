package org.btwr.data_suite.attachment;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.util.Identifier;
import org.btwr.data_suite.BTWRDSMod;

public class ModAttachmentTypes {

    public static final AttachmentType<MagneticPointAttachedData> MAGNETIC_POINT = AttachmentRegistry.create(
            Identifier.of(BTWRDSMod.MOD_ID, "magnetic_point"),
            builder -> builder
                    .initializer(() -> MagneticPointAttachedData.DEFAULT)
                    .persistent(MagneticPointAttachedData.CODEC)
                    .syncWith(
                            MagneticPointAttachedData.PACKET_CODEC,
                            AttachmentSyncPredicate.all()
                    )
    );

}