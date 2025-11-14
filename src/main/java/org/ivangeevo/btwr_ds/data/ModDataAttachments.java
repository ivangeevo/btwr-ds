package org.ivangeevo.btwr_ds.data;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.util.Identifier;
import org.ivangeevo.animageddon.AnimageddonMod;
import org.ivangeevo.btwr_ds.BTWRDSMod;

public class ModDataAttachments {

    /**
    public static final AttachmentType<Boolean> HAS_BAIT = AttachmentRegistry.createPersistent(
            Identifier.of(AnimageddonMod.MOD_ID, "has_bait"),
            Codec.BOOL
    );
     **/

    public static final AttachmentType<CrawlingToggleData> CRAWLING_TOGGLE = AttachmentRegistry.create(
            Identifier.of(AnimageddonMod.MOD_ID, "crawling_toggle_data"),
            builder -> builder
                    .initializer(() -> new CrawlingToggleData(false))
                    .persistent(CrawlingToggleData.CODEC)
                    .syncWith(CrawlingToggleData.PACKET_CODEC, AttachmentSyncPredicate.all())
    );

    public static void register() {
        BTWRDSMod.LOGGER.info("Registering {} attachments", BTWRDSMod.MOD_ID);
        // Technically this method can stay empty, but some developers like to notify
        // the console, that certain parts of the mod have been successfully initialized
    }
}
