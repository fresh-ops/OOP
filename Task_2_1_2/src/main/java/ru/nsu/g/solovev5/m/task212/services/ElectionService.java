package ru.nsu.g.solovev5.m.task212.services;

import ru.nsu.g.solovev5.m.task212.messages.AdvertisementMessage;

/**
 * A service for electing a new master node.
 */
public class ElectionService {
    private static final int ELECTION_THRESHOLD = 5;
    private AdvertisementMessage localMessage;

    private AdvertisementMessage candidateMessage;
    private int candidateRounds = 0;

    /**
     * Resets this service.
     */
    public void reset(AdvertisementMessage localMessage) {
        candidateRounds = 0;
        candidateMessage = localMessage;
        this.localMessage = localMessage;
    }

    /**
     * Checks if the sender of advertisement can become a new master node.
     *
     * @param message a message to check
     * @return {@code true} if the sender can become a new master node, {@code false} otherwise
     */
    public boolean elect(AdvertisementMessage message) {
        if (message == null) {
            return false;
        }

        if (message.uuid().equals(localMessage.uuid())) {
            return false;
        }

        var compare = message.uuid().compareTo(candidateMessage.uuid());
        if (compare < 0) {
            candidateRounds = 1;
            candidateMessage = message;
        } else if (compare == 0) {
            candidateRounds++;
        }

        return candidateRounds >= ELECTION_THRESHOLD;
    }
}
