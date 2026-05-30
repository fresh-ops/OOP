package ru.nsu.g.solovev5.m.task212.messages;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * A message with data for server connection.
 *
 * @param uuid    a service uuid
 * @param address a service ip address
 * @param port    a service connection port
 */
public record AdvertisementMessage(
    UUID uuid,
    InetAddress address,
    int port
) {
    private static final int BYTES_LENGTH = Long.BYTES * 2 + // UUID
        1 + // IP version
        16 + // Aligned for IPv6 address
        Integer.BYTES // Port
        ;

    /**
     * Serialize this object into a byte array.
     *
     * @return a byte array
     */
    public byte[] toBytes() {
        var buffer = ByteBuffer.allocate(BYTES_LENGTH);

        buffer.putLong(uuid.getMostSignificantBits());
        buffer.putLong(uuid.getLeastSignificantBits());

        var addressBytes = address.getAddress();
        if (addressBytes.length == 4) {
            buffer.put((byte) 4);
            buffer.put(addressBytes);
        } else if (addressBytes.length == 16) {
            buffer.put((byte) 6);
            buffer.put(addressBytes);
        }
        buffer.putInt(BYTES_LENGTH - Integer.BYTES, port);
        return buffer.array();
    }

    /**
     * Restores a serialized message.
     *
     * @param bytes a serialized message
     * @return a deserialized message
     */
    public static AdvertisementMessage fromBytes(byte[] bytes) throws UnknownHostException {
        if (bytes.length != BYTES_LENGTH) {
            throw new IllegalArgumentException("Invalid byte array length");
        }
        var buffer = ByteBuffer.wrap(bytes);
        var uuid = new UUID(buffer.getLong(), buffer.getLong());
        var ipVersion = buffer.get();
        byte[] addressBytes;
        if (ipVersion == 4) {
            addressBytes = new byte[4];
            buffer.get(addressBytes);
        } else if (ipVersion == 6) {
            addressBytes = new byte[16];
            buffer.get(addressBytes);
        } else {
            throw new UnknownHostException("Unknown IP version: " + ipVersion);
        }
        var address = InetAddress.getByAddress(addressBytes);
        var port = buffer.getInt(BYTES_LENGTH - Integer.BYTES);

        return new AdvertisementMessage(uuid, address, port);
    }
}
