package org.ivangeevo.btwr_ds.util;

import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;

public class MagneticPointsState extends PersistentState {

    private final MagneticPointList list = new MagneticPointList();

    public MagneticPointsState() {}

    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        nbt.put("MagneticPoints", list.write());
        return nbt;
    }

    private static MagneticPointsState fromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        MagneticPointsState state = new MagneticPointsState();
        if (nbt.contains("MagneticPoints") && nbt.get("MagneticPoints") instanceof NbtList l) {
            state.list.load(l);
        }
        return state;
    }

    public MagneticPointList getList() {
        return list;
    }

    public static final PersistentState.Type<MagneticPointsState> TYPE =
            new PersistentState.Type<>(
                    MagneticPointsState::new,
                    MagneticPointsState::fromNbt,
                    DataFixTypes.LEVEL
            );

    public static MagneticPointsState get(ServerWorld world) {
        return world.getPersistentStateManager().getOrCreate(TYPE, "magnetic_points");
    }
}
