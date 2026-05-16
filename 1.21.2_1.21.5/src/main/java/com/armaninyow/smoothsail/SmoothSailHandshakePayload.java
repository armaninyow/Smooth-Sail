package com.armaninyow.smoothsail;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SmoothSailHandshakePayload() implements CustomPacketPayload {

	public static final CustomPacketPayload.Type<SmoothSailHandshakePayload> TYPE =
		new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(SmoothSail.MOD_ID, "hello"));

	public static final StreamCodec<FriendlyByteBuf, SmoothSailHandshakePayload> CODEC =
		StreamCodec.unit(new SmoothSailHandshakePayload());

	@Override
	public CustomPacketPayload.Type<SmoothSailHandshakePayload> type() {
		return TYPE;
	}
}