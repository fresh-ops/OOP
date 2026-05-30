package ru.nsu.g.solovev5.m.task212.actors.slave;

import java.io.IOException;
import java.net.Socket;
import ru.nsu.g.solovev5.m.task212.actors.Actor;
import ru.nsu.g.solovev5.m.task212.actors.Role;
import ru.nsu.g.solovev5.m.task212.models.DiscoveredNode;
import ru.nsu.g.solovev5.m.task212.models.messages.ConnectionApprovedMessage;
import ru.nsu.g.solovev5.m.task212.models.messages.ConnectionRefusedMessage;
import ru.nsu.g.solovev5.m.task212.models.messages.ConnectionRequestMessage;
import ru.nsu.g.solovev5.m.task212.models.messages.TcpMessage;
import ru.nsu.g.solovev5.m.task212.models.messages.io.MessageChannel;
import ru.nsu.g.solovev5.m.task212.models.messages.io.TcpMessageInputStream;
import ru.nsu.g.solovev5.m.task212.models.messages.io.TcpMessageOutputStream;

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
            System.out.println("Connecting to master...");
            channel.send(new ConnectionRequestMessage(master.uuid()));

            TcpMessage response = channel.receive();
            if (response instanceof ConnectionApprovedMessage) {
                System.out.println("Connected to " + master.ip() + ":" + master.port());
            } else if (response instanceof ConnectionRefusedMessage refused) {
                System.err.println("Connection refused: " + refused.reason());
            } else {
                System.err.println("Unexpected connection response: " + response.getClass().getName());
            }
        } catch (IOException e) {
            System.err.println("Can't connect to master.");
        } catch (ClassNotFoundException e) {
            System.err.println("Can't read connection response from master.");
        } finally {
            System.out.println("Disconnected");
        }
    }

    @Override
    public void stop() {

    }
}
