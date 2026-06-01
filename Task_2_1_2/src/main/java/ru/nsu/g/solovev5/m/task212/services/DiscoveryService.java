package ru.nsu.g.solovev5.m.task212.services;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.StandardProtocolFamily;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.MembershipKey;
import ru.nsu.g.solovev5.m.task212.models.DiscoveredNode;
import ru.nsu.g.solovev5.m.task212.models.messages.AdvertisementMessage;

/**
 * A service that receives multicast discovery messages.
 */
public class DiscoveryService {
    private static final int MESSAGE_BUFFER_CAPACITY = 2048;

    private final String ip;
    private final int port;

    private volatile boolean alive = false;

    private DatagramChannel channel;
    private MembershipKey membershipKey;

    /**
     * Creates a new DiscoveryService.
     *
     * @param ip   multicast group address
     * @param port multicast port
     */
    public DiscoveryService(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    /**
     * Tests if this service is alive.
     *
     * @return true if service is running
     */
    public synchronized boolean isAlive() {
        return alive;
    }

    /**
     * Opens multicast channel and joins group.
     *
     * @throws IOException if an I/O error occurred
     */
    public synchronized void open() throws IOException {
        if (alive) {
            return;
        }

        channel = DatagramChannel.open(StandardProtocolFamily.INET);
        channel.configureBlocking(true);
        channel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
        channel.bind(new InetSocketAddress(port));

        var networkInterface = NetworkInterface.getNetworkInterfaces()
            .asIterator()
            .next();

        membershipKey = channel.join(InetAddress.getByName(ip), networkInterface);
        alive = true;
    }

    /**
     * Closes the multicast channel.
     *
     * @throws IOException if an I/O error occurred
     */
    public synchronized void close() throws IOException {
        if (!alive) {
            return;
        }

        try {
            if (membershipKey != null) {
                membershipKey.drop();
                membershipKey = null;
            }

            if (channel != null) {
                channel.close();
            }
        } finally {
            channel = null;
            alive = false;
        }
    }

    /**
     * Receives next multicast message.
     *
     * @return discovered node, or {@code null} on error
     */
    public DiscoveredNode discover() {
        final DatagramChannel localChannel;

        synchronized (this) {
            if (!alive || channel == null) {
                return null;
            }
            localChannel = channel;
        }

        try {
            var buffer = ByteBuffer.allocate(MESSAGE_BUFFER_CAPACITY);
            buffer.clear();
            var address = localChannel.receive(buffer);

            buffer.flip();

            byte[] data = new byte[buffer.remaining()];
            buffer.get(data);

            var message = AdvertisementMessage.fromBytes(data);
            if (address instanceof InetSocketAddress inetSocketAddress) {
                return new DiscoveredNode(message, inetSocketAddress.getAddress());
            }
        } catch (IOException e) {
            System.err.println("Discovery service failed: " + e.getMessage());
        }

        return null;
    }
}
