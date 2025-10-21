package org.ivangeevo.btwr_ds.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import java.util.Optional;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.poi.PointOfInterestTypes;

public record MagneticPointTrackerComponent(Optional<GlobalPos> target, boolean tracked) {

	public static final Codec<MagneticPointTrackerComponent> CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
					GlobalPos.CODEC
							.optionalFieldOf("target")
							.forGetter(MagneticPointTrackerComponent::target),
					Codec.BOOL
							.optionalFieldOf("tracked", Boolean.TRUE)
							.forGetter(MagneticPointTrackerComponent::tracked)
				)
				.apply(instance, MagneticPointTrackerComponent::new)
	);

	public static final PacketCodec<ByteBuf, MagneticPointTrackerComponent> PACKET_CODEC = PacketCodec.tuple(
		GlobalPos.PACKET_CODEC.collect(PacketCodecs::optional),
		MagneticPointTrackerComponent::target,
		PacketCodecs.BOOL,
		MagneticPointTrackerComponent::tracked,
		MagneticPointTrackerComponent::new
	);

	public MagneticPointTrackerComponent forWorld(ServerWorld world) {
		if (this.tracked && this.target.isPresent()) {
			if (this.target.get().dimension() != world.getRegistryKey()) {
				return this;
			} else {
				BlockPos blockPos = this.target.get().pos();
				return world.isInBuildLimit(blockPos) /**&& world.getPointOfInterestStorage().hasTypeAt(PointOfInterestTypes.LODESTONE, blockPos)**/
					? this
					: new MagneticPointTrackerComponent(Optional.empty(), true);
			}
		} else {
			return this;
		}
	}
}
