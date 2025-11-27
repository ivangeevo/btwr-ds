package org.btwr.data_suite.util;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;

import java.util.ArrayList;
import java.util.List;

public class MagneticPointList {

	public List<MagneticPoint> magneticPoints;
	
	public MagneticPointList()
	{
		magneticPoints = new ArrayList<>();
	}
	
    public void load(NbtList list) {
    	magneticPoints.clear();

        for (NbtElement nbtElement : list) {
            NbtCompound tempCompound = (NbtCompound) nbtElement;

            MagneticPoint newPoint = new MagneticPoint(tempCompound);

            magneticPoints.add(newPoint);
        }
    }

	public NbtList write() {
		NbtList list = new NbtList();

		for (MagneticPoint tempPoint : magneticPoints) {
			NbtCompound tempCompound = new NbtCompound();
			tempPoint.write(tempCompound);
			list.add(tempCompound);
		}

		return list;
	}
    
    public void removePointAt(int posX, int posY, int posZ) {
		for (MagneticPoint tempPoint : magneticPoints) {
			if (tempPoint.posX == posX && tempPoint.posY == posY && tempPoint.posZ == posZ) {
				magneticPoints.remove(tempPoint);
				return;
			}
		}
    }
    
    public void addPoint(int posX, int posY, int posZ, int powerLevel) {
        MagneticPoint newPoint = new MagneticPoint(posX, posY, posZ, powerLevel);
        magneticPoints.add(newPoint);
    }
    
    public void changePowerLevelOfPointAt(int posX, int posY, int posZ, int powerLevel) {
		MagneticPoint point = getMagneticPointAtLocation(posX, posY, posZ);
		
		if (point != null) {
			point.fieldLevel = powerLevel;
		}    	
    }
    
    public MagneticPoint getMagneticPointAtLocation(int posX, int posY, int posZ) {
    	for (MagneticPoint tempPoint : magneticPoints) {
			if (tempPoint.posX == posX && tempPoint.posZ == posZ && tempPoint.posY == posY) {
				return tempPoint;
			}
		}
		return null;
    }

}