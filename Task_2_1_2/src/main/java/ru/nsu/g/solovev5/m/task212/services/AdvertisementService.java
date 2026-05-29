package ru.nsu.g.solovev5.m.task212.services;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.StandardProtocolFamily;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;

/**
 * A service that translates multicast messages.
 */
public class AdvertisementService implements Runnable {
    private final String ip;
    private final int port;

    private volatile byte[] message = new byte[0];
    private boolean alive = false;
    private DatagramChannel channel;

    /**
     * Creates a new AdvertisementService.
     *
     * @param ip a multicast address to send messages.
     * @param port a to send messages.
     */
    public AdvertisementService(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    /**
     * Sets the message to send.
     *
     * @param message an advertisement message
     */
    public void setMessage(byte[] message) {
        this.message = Arrays.copyOf(message, message.length);
    }

    /**
     * Tests if this service is alive.
     *
     * @return {@code true} if this service is alive, {@code false} otherwise
     */
    public synchronized boolean isAlive() {
        return alive;
    }

    /**
     * Opens the message channel.
     *
     * @throws IOException if an I/O error occurred
     */
    public synchronized void open() throws IOException {
        if (alive) {
            return;
        }

        channel = DatagramChannel.open(StandardProtocolFamily.INET);
        alive = true;
    }

    /**
     * Closes the message channel.
     *
     * @throws IOException if an I/O error occurred
     */
    public synchronized void close() throws IOException {
        if (!alive) {
            return;
        }

        try {
            channel.close();
        } finally {
            channel = null;
            alive = false;
        }
    }

    @Override
    public void run() {
        send();
    }

    /**
     * Sends a message to the channel.
     */
    public void send() {
        final DatagramChannel localChannel;
        final byte[] localMessage;

        synchronized (this) {
            if (!alive || channel == null) {
                return;
            }
            localChannel = channel;
            localMessage = message;
        }

        try  {
            var discoveryAddress = new InetSocketAddress(InetAddress.getByName(ip), port);

            var buffer = ByteBuffer.wrap(localMessage);
            localChannel.send(buffer, discoveryAddress);

        } catch (IOException e) {
            System.err.println("Advertisement service failed: " + e.getMessage());
        }
    }
}