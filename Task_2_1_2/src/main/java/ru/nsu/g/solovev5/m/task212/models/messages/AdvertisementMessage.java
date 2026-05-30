package ru.nsu.g.solovev5.m.task212.models.messages;

import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * A message with data for server connection.
 *
 * @param uuid a service uuid
 * @param port a service connection port
 */
public record AdvertisementMessage(
    UUID uuid,
    int port
) {
    // UUID + Port
    private static final int BYTES_LENGTH = Long.BYTES * 2 + Integer.BYTES;

    /**
     * Serialize this object into a byte array.
     *
     * @return a byte array
     */
    public byte[] toBytes() {
        var buffer = ByteBuffer.allocate(BYTES_LENGTH);

        buffer.putLong(uuid.getMostSignificantBits());
        buffer.putLong(uuid.getLeastSignificantBits());
        buffer.putInt(BYTES_LENGTH - Integer.BYTES, port);
        return buffer.array();
    }

    /**
     * Restores a serialized message.
     *
     * @param bytes a serialized message
     * @return a deserialized message
     */
    public static AdvertisementMessage fromBytes(byte[] bytes) {
        if (bytes.length != BYTES_LENGTH) {
            throw new IllegalArgumentException("Invalid byte array length");
        }
        var buffer = ByteBuffer.wrap(bytes);
        var uuid = new UUID(buffer.getLong(), buffer.getLong());
        var port = buffer.getInt(BYTES_LENGTH - Integer.BYTES);

        return new AdvertisementMessage(uuid, port);
    }
}
