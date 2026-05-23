package ru.nsu.g.solovev5.m.task212.master.eventloop;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import ru.nsu.g.solovev5.m.task212.master.network.SlaveSession;
import ru.nsu.g.solovev5.m.task212.models.requests.PingRequest;
import ru.nsu.g.solovev5.m.task212.models.responses.ResponseType;

public class SlaveEventLoop implements Runnable {
    private final SlaveSession session;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private final Queue<Event> events = new ConcurrentLinkedQueue<>();

    public SlaveEventLoop(SlaveSession session) {
        this.session = session;
    }

    @Override
    public void run() {
        running.set(true);
        try {
            while (running.get() && !Thread.interrupted()) {
                if (events.isEmpty()) {
                    ping();
                }
            }
        } catch (IOException e) {
            System.err.println("IOException occurred: " + e.getMessage());
        }
        running.set(false);
    }

    public boolean isRunning() {
        return running.get();
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public void close() throws IOException {
        running.set(false);
        session.close();
    }

    private void ping() throws IOException {
        session.requests.writeRequest(new PingRequest());
        session.requests.flush();
        try {
            var response = session.responses.readResponse();
            if (response.getType() != ResponseType.PING) {
                throw new IOException("Invalid response");
            }
        } catch (ClassNotFoundException e) {
            throw new IOException(e);
        }
    }
}
