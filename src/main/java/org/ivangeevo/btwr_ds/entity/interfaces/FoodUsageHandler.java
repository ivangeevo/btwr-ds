package org.ivangeevo.btwr_ds.entity.interfaces;

public interface FoodUsageHandler
{
    // Expose getter/setter so your ItemMixin can check this
    boolean canUseFoodAgain();
    void setCanUseFoodAgain(boolean val);
    
}
