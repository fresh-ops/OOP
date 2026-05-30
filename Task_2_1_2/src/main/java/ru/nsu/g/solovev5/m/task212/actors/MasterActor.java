package ru.nsu.g.solovev5.m.task212.actors;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.UUID;
import ru.nsu.g.solovev5.m.task212.models.messages.AdvertisementMessage;

/**
 * An actor that accepts tasks and distributes them between slaves.
 */
public class MasterActor implements Actor {
    private final ServerSocket serverSocket = new ServerSocket(0);
    private final UUID uuid = UUID.randomUUID();

    /**
     * Creates a new MasterActor.
     *
     * @throws IOException if an I/O error occurred
     */
    public MasterActor() throws IOException {
    }

    /**
     * Creates an advertisement message with information about server.
     *
     * @return a new advertisement message
     */
    public AdvertisementMessage getAdvertisementMessage() {
        return new AdvertisementMessage(
            uuid,
            serverSocket.getLocalPort()
        );
    }

    @Override
    public Role getRole() {
        return Role.MASTER;
    }

    @Override
    public void run() {
        System.out.println("Nothing to do");
    }

    @Override
    public void stop() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.err.println("Could not close the server socket");
        }
    }
}
