package org.btwr.data_suite.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.util.math.BlockPos;

public record MagneticPointGlobalPos(BlockPos pos) {
	public static final MapCodec<MagneticPointGlobalPos> MAP_CODEC = RecordCodecBuilder.mapCodec(
		instance -> instance.group(
				 BlockPos.CODEC.fieldOf("pos").forGetter(MagneticPointGlobalPos::pos))
				.apply(instance, MagneticPointGlobalPos::create)
	);
	public static final Codec<MagneticPointGlobalPos> CODEC = MAP_CODEC.codec();
	public static final PacketCodec<ByteBuf, MagneticPointGlobalPos> PACKET_CODEC = PacketCodec.tuple(
			BlockPos.PACKET_CODEC, MagneticPointGlobalPos::pos, MagneticPointGlobalPos::create
	);

	public static MagneticPointGlobalPos create(BlockPos pos) {
		return new MagneticPointGlobalPos( pos);
	}

	public String toString() {
		return " " + this.pos;
	}
}