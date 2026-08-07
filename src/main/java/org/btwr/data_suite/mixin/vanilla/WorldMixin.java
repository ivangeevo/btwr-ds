package org.btwr.data_suite.mixin.vanilla;

import net.minecraft.world.World;
import org.btwr.data_suite.util.MagneticPointList;
import org.btwr.data_suite.world.interfaces.WorldAdded;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(World.class)
public abstract class WorldMixin implements WorldAdded {
    @Unique private MagneticPointList magneticPointList = new MagneticPointList();

    @Override
    public MagneticPointList btwr$magneticPoints() {
        return magneticPointList;
    }
}