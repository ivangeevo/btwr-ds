package org.btwr.data_suite.block.entity;

import net.minecraft.block.entity.BeaconBlockEntity;

public class BeaconBlockEntityMagneticTracker {
    private static final BeaconBlockEntityMagneticTracker INSTANCE = new BeaconBlockEntityMagneticTracker();

    private BeaconBlockEntityMagneticTracker() {}

    public static BeaconBlockEntityMagneticTracker getInstance() {
        return INSTANCE;
    }

    public void onPowerChange(int newPowerLevel, int oldPowerLevel, BeaconBlockEntity beacon) {
        assert beacon.getWorld() != null;
        if (!beacon.getWorld().isClient) {
            // check for changes in state which will affect the magnetic point list
            updateGlobalMagneticFieldListForStateChange(newPowerLevel, oldPowerLevel, beacon);
        }
    }

    // The power level multiplication here is usually done per beacon effect,
    // so I'm not sure if * 2 is actually proper for each case.
    private void updateGlobalMagneticFieldListForStateChange(int newPowerLevel, int oldPowerLevel, BeaconBlockEntity beacon) {
        assert beacon.getWorld() != null;
        if (newPowerLevel <= 0) {
            beacon.getWorld().btwr$magneticPoints().removePointAt(
                    beacon.getPos().getX(), beacon.getPos().getY(), beacon.getPos().getZ()
            );
        }
        else if (oldPowerLevel <= 0) {
            beacon.getWorld().btwr$magneticPoints().addPoint(
                    beacon.getPos().getX(), beacon.getPos().getY(), beacon.getPos().getZ(), newPowerLevel * 2
            );
        }
        else if (oldPowerLevel != newPowerLevel) {
            beacon.getWorld().btwr$magneticPoints().changePowerLevelOfPointAt(
                    beacon.getPos().getX(), beacon.getPos().getY(), beacon.getPos().getZ(), newPowerLevel * 2
            );
        }
    }
}