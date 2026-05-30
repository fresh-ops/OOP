package ru.nsu.g.solovev5.m.task212.actors.slave;

import java.io.IOException;
import java.net.Socket;
import ru.nsu.g.solovev5.m.task212.actors.Actor;
import ru.nsu.g.solovev5.m.task212.actors.Role;
import ru.nsu.g.solovev5.m.task212.models.DiscoveredNode;

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
        try (var socket = new Socket(master.ip(), master.port())) {
            System.out.println("Connected to " + master.ip() + ":" + master.port());
        } catch (IOException e) {
            System.err.println("Can't connect to master.");
        } finally {
            System.out.println("Disconnected");
        }
    }

    @Override
    public void stop() {

    }
}
