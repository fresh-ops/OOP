package ru.nsu.g.solovev5.m.task212.actors.slave;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import ru.nsu.g.solovev5.m.task212.actors.Actor;
import ru.nsu.g.solovev5.m.task212.models.DiscoveredNode;
import ru.nsu.g.solovev5.m.task212.models.messages.ConnectionApprovedMessage;
import ru.nsu.g.solovev5.m.task212.models.messages.ConnectionRequestMessage;
import ru.nsu.g.solovev5.m.task212.models.messages.PingMessage;
import ru.nsu.g.solovev5.m.task212.models.messages.ProcessMessage;
import ru.nsu.g.solovev5.m.task212.models.messages.ProcessResultMessage;
import ru.nsu.g.solovev5.m.task212.models.messages.TcpMessage;
import ru.nsu.g.solovev5.m.task212.models.messages.io.MessageChannel;
import ru.nsu.g.solovev5.m.task212.services.PrimeChecker;

/**
 * An actor that accepts tasks from master.
 */
public class SlaveActor implements Actor {
    private final DiscoveredNode master;

    private boolean alive = false;

    /**
     * Creates a new SlaveActor.
     *
     * @param master information about master
     */
    public SlaveActor(DiscoveredNode master) {
        this.master = master;
    }

    @Override
    public void run() {
        alive = true;
        try (
            var socket = new Socket(master.ip(), master.port());
            var channel = new MessageChannel(socket)
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
                    var response = generateResponse(request);
                    if (response == null) {
                        break;
                    }
                    channel.send(response);
                } catch (ClassNotFoundException | SocketTimeoutException e) {
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Connection interrupted: " + e.getMessage());
        } finally {
            System.out.println("Disconnected");
            alive = false;
        }
    }

    @Override
    public boolean isAlive() {
        return alive;
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

    private TcpMessage generateResponse(TcpMessage request) {
        if (request instanceof PingMessage) {
            return new PingMessage();
        } else if (request instanceof ProcessMessage processMessage) {
            return processNumbers(processMessage.numbers());
        }
        return null;
    }

    private TcpMessage processNumbers(int[] numbers) {
        var checker = new PrimeChecker();
        return new ProcessResultMessage(
            checker.hasNonPrime(numbers)
        );
    }
}
