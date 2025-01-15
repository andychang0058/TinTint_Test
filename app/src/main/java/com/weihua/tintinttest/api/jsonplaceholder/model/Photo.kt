package com.weihua.tintinttest.api.jsonplaceholder.model

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
data class Photo(
    @SerialName("albumId") val albumId: Int,
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("url") val url: String,
    @Serializable(with = HexColorSerializer::class)
    @SerialName("thumbnailUrl") val hex: String,
)

object HexColorSerializer : KSerializer<String> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("HexColor", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: String) {
        encoder.encodeString(value)
    }

    override fun deserialize(decoder: Decoder): String {
        val url = decoder.decodeString()
        return runCatching {
            url.split("/")
                .lastOrNull { it.matches(Regex("[0-9a-fA-F]{6}")) }
                ?: throw IllegalArgumentException("Invalid hex color URL: $url")
        }.fold(
            onSuccess = { return it },
            onFailure = { return "000000" }
        )
    }
}