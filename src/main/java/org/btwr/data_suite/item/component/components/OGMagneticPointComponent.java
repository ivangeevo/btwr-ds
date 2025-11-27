package org.btwr.data_suite.item.component.components;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;

public class OGMagneticPointComponent {

    private BlockPos pos;

    public OGMagneticPointComponent(BlockPos pos) {
        this.pos = pos;
    }

    public BlockPos getPos() {
        return pos;
    }

    public void setPos(BlockPos pos) {
        this.pos = pos;
    }

    public static final Codec<OGMagneticPointComponent> CODEC = BlockPos.CODEC.xmap(
            OGMagneticPointComponent::new,
            OGMagneticPointComponent::getPos
    );

}