package org.ivangeevo.btwr_ds.mixin.vanilla;

import net.minecraft.world.World;
import org.ivangeevo.btwr_ds.util.MagneticPointList;
import org.ivangeevo.btwr_ds.world.interfaces.WorldAdded;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(World.class)
public abstract class WorldMixin implements WorldAdded {

    @Unique
    private MagneticPointList magneticPointList = new MagneticPointList();

    @Override
    public MagneticPointList getMagneticPointList() {
        return magneticPointList;
    }

}
