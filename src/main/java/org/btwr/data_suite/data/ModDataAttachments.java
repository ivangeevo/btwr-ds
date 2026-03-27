package org.btwr.data_suite.data;

import org.btwr.data_suite.BTWRDSMod;

public class ModDataAttachments {

    /**
    public static final AttachmentType<Boolean> HAS_BAIT = AttachmentRegistry.createPersistent(
            Identifier.of(AnimageddonMod.MOD_ID, "has_bait"),
            Codec.BOOL
    );
     **/

    public static void register() {
        BTWRDSMod.LOGGER.info("Registering {} attachments", BTWRDSMod.MOD_ID);
        // Technically this method can stay empty, but some developers like to notify
        // the console, that certain parts of the mod have been successfully initialized
    }

}