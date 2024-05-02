package dev.hawk0f.itutor.core.data.utils

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object TimeSerializer : KSerializer<LocalTime>
{
    private val timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss")

    override val descriptor = PrimitiveSerialDescriptor("Time", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder) = LocalTime.parse(decoder.decodeString(), timeFormat)

    override fun serialize(encoder: Encoder, value: LocalTime) = encoder.encodeString(value.toString())
}