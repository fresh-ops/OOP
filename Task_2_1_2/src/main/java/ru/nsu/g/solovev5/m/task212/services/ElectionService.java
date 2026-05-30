package ru.nsu.g.solovev5.m.task212.services;

import ru.nsu.g.solovev5.m.task212.models.DiscoveredNode;
import ru.nsu.g.solovev5.m.task212.models.messages.AdvertisementMessage;

/**
 * A service for electing a new master node.
 */
public class ElectionService {
    private static final int ELECTION_THRESHOLD = 5;
    private AdvertisementMessage localMessage;

    private DiscoveredNode candidate;
    private int candidateRounds = 0;

    /**
     * Resets this service.
     */
    public void reset(AdvertisementMessage localMessage) {
        candidateRounds = 0;
        candidate = null;
        this.localMessage = localMessage;
    }

    /**
     * Checks if the node can become a new master.
     *
     * @param node a node to check
     * @return {@code true} if the node can become a new master, {@code false} otherwise
     */
    public boolean elect(DiscoveredNode node) {
        if (node == null) {
            return false;
        }

        if (node.uuid().compareTo(localMessage.uuid()) >= 0
            || node.priority() >= localMessage.priority()) {
            return false;
        }

        if (candidate == null) {
            candidate = node;
            candidateRounds = 1;
            return false;
        }

        var compare = node.uuid().compareTo(candidate.uuid());
        if (node.priority() < candidate.priority()
            || (node.priority() == candidate.priority() && compare < 0)) {
            candidateRounds = 1;
            candidate = node;
        } else if (node.priority() == candidate.priority() && compare == 0) {
            candidateRounds++;
        }
        System.out.println(candidate);
        return candidateRounds >= ELECTION_THRESHOLD;
    }
}
