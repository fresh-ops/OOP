package ru.nsu.g.solovev5.m.task212.models.messages;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;
import org.junit.jupiter.api.Test;

class AdvertisementMessageTest {
    @Test
    void serialization_shouldNot_changeFields() {
        var advertisement = new AdvertisementMessage(UUID.randomUUID(), 0, 0);
        var bytes = advertisement.toBytes();
        var restored = AdvertisementMessage.fromBytes(bytes);

        assertEquals(advertisement, restored);
    }
}