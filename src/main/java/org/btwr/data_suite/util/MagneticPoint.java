package org.btwr.data_suite.util;


import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class MagneticPoint {
	static private double[] fieldStrengthMultipliersByLevel =
			new double[] { 0D, 1D, 8D, 27D, 64D, 125D, 216D, 343D, 4096D };

	static private double[] maxRangeSquaredForLevelWithNoise =
			new double[] { 0D, 100D, 400D, 1600D, 6400, 25600, 102400, 409600, Double.POSITIVE_INFINITY };
		
	public int posX;
	public int posY;
	public int posZ;
	public int fieldLevel;
	
	public MagneticPoint() {
		posX = 0;
		posY = 0;
		posZ = 0;
		fieldLevel = 0;
	}
	
	public MagneticPoint(int posX, int posY, int posZ, int fieldLevel) {
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.fieldLevel = fieldLevel;
	}

	public static MagneticPoint fromBlockPos(BlockPos pos, int fieldLevel) {
		return new MagneticPoint(pos.getX(), pos.getY(), pos.getZ(), fieldLevel);
	}
	
	public MagneticPoint(NbtCompound nbt)
	{
		read(nbt);
	}
	
	public void read(NbtCompound nbt) {
		posX = nbt.getInt("PosX");
		posY = nbt.getShort("PosY");
		posZ = nbt.getInt("PosZ");
		fieldLevel = nbt.getByte("Lvl");
	}
	
    public NbtCompound write(NbtCompound nbt) {
    	nbt.putInt("PosX", posX);
    	nbt.putShort( "PosY", (short) posY);
    	nbt.putInt("PosZ", posZ);
		nbt.putByte( "Lvl", (byte) fieldLevel);

        return nbt;
    }
    
    public double getFieldStrengthRelativeToPosition(double relativeX, double relativeZ) {
    	double deltaX = (double) posX - relativeX;
    	double deltaZ = (double) posZ - relativeZ;
    	
    	double distSq = deltaX * deltaX + deltaZ * deltaZ;
    	
    	return fieldStrengthMultipliersByLevel[fieldLevel] / distSq;
    }
    
    public double getFieldStrengthRelativeToPositionWithBackgroundNoise(double relativeX, double relativeZ) {
    	double deltaX = (double) posX - relativeX;
    	double deltaZ = (double) posZ - relativeZ;
    	
    	double distSq = deltaX * deltaX + deltaZ * deltaZ;
    	
    	if (distSq <= maxRangeSquaredForLevelWithNoise[fieldLevel]) {
    		return fieldStrengthMultipliersByLevel[fieldLevel] / distSq;
    	} else {
    		return -1D;
    	}
    }
}