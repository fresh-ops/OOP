package ru.nsu.g.solovev5.m.task212.actors.slave;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import ru.nsu.g.solovev5.m.task212.actors.Actor;
import ru.nsu.g.solovev5.m.task212.actors.Role;
import ru.nsu.g.solovev5.m.task212.models.DiscoveredNode;
import ru.nsu.g.solovev5.m.task212.models.messages.ConnectionApprovedMessage;
import ru.nsu.g.solovev5.m.task212.models.messages.ConnectionRequestMessage;
import ru.nsu.g.solovev5.m.task212.models.messages.PingMessage;
import ru.nsu.g.solovev5.m.task212.models.messages.io.MessageChannel;

/**
 * An actor that accepts tasks from master.
 */
public class SlaveActor implements Actor {
    private final DiscoveredNode master;

    /**
     * Creates a new SlaveActor.
     *
     * @param master information about master
     */
    public SlaveActor(DiscoveredNode master) {
        this.master = master;
    }

    @Override
    public Role getRole() {
        return Role.SLAVE;
    }

    @Override
    public void run() {
        try (
            var socket = new Socket(master.ip(), master.port());
            var channel = new MessageChannel(socket);
        ) {
            if (authorizeConnection(channel)) {
                System.out.println("Connected to " + master.ip() + ":" + master.port());
            } else {
                System.err.println("Failed to connect to " + master.ip() + ":" + master.port());
                return;
            }
            while (!Thread.interrupted() && !channel.isClosed()) {
                try {
                    var request = channel.receive(5_000);
                    if (request instanceof PingMessage) {
                        channel.send(new PingMessage());
                    }
                } catch (ClassNotFoundException | SocketTimeoutException e) {
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Connection interrupted: " + e.getMessage());
            e.printStackTrace();
        } finally {
            System.out.println("Disconnected");
        }
    }

    @Override
    public void stop() {

    }

    private boolean authorizeConnection(MessageChannel channel) throws IOException {
        try {
            channel.send(new ConnectionRequestMessage(master.uuid()));

            var response = channel.receive(5_0000);
            return response instanceof ConnectionApprovedMessage;
        } catch (ClassNotFoundException e) {
            System.err.println("Can't read connection response from master.");
            return false;
        }
    }
}
