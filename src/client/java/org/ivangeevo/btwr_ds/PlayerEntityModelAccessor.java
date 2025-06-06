package org.ivangeevo.btwr_ds;

// This interface is used to pass saturation info from renderer -> model
public interface PlayerEntityModelAccessor {
    void setSaturation(float saturation);
    float getSaturation();
}