package org.ivangeevo.btwr_ds.util;

import net.minecraft.entity.EntityPose;
import net.minecraft.entity.player.PlayerEntity;
import org.ivangeevo.btwr_ds.data.ModDataAttachments;

public class CrawlHandler {

    public static void toggleCrawl(PlayerEntity player) {
        var data = player.getAttachedOrCreate(ModDataAttachments.CRAWLING_TOGGLE);
        boolean now = !data.getCrawling();
        data.setCrawling(now);

        // Update pose
        player.setPose(now ? EntityPose.SWIMMING : EntityPose.STANDING);
        player.calculateDimensions();
    }
}
