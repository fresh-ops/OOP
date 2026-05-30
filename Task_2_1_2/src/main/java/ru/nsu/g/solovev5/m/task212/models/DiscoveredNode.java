package ru.nsu.g.solovev5.m.task212.models;

import java.net.InetAddress;
import java.util.UUID;
import ru.nsu.g.solovev5.m.task212.models.messages.AdvertisementMessage;

/**
 * Represents discovered node with necessary information for connection.
 *
 * @param uuid     the service uuid
 * @param priority the node priority
 * @param ip       the node ip
 * @param port     the node port
 */
public record DiscoveredNode(
    UUID uuid,
    int priority,
    InetAddress ip,
    int port
) {
    /**
     * Creates a new DiscoveredNode from AdvertisementMessage.
     *
     * @param message the advertisement
     * @param ip      the advertisement sender
     */
    public DiscoveredNode(AdvertisementMessage message, InetAddress ip) {
        this(message.uuid(), message.priority(), ip, message.port());
    }
}
