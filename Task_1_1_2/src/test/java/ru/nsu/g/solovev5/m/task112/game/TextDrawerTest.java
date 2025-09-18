package ru.nsu.g.solovev5.m.task112.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.g.solovev5.m.task112.cards.Card;
import ru.nsu.g.solovev5.m.task112.cards.CardRank;
import ru.nsu.g.solovev5.m.task112.cards.CardSuit;
import ru.nsu.g.solovev5.m.task112.participants.Participant;
import ru.nsu.g.solovev5.m.task112.participants.Player;

class TextDrawerTest {
    ByteArrayOutputStream bytes;
    PrintStream stream;
    TextDrawer drawer;
    Participant participant;
    Participant[] participants;

    @BeforeEach
    void setUp() {
        bytes = new ByteArrayOutputStream();
        stream = new PrintStream(bytes, true, StandardCharsets.UTF_8);
        drawer = new TextDrawer(stream);

        participant = new Player("test", null);
        participant.takeCardFrom(() -> new Card(CardSuit.SPADES, CardRank.ACE));
        participant.takeCardFrom(() -> new Card(CardSuit.HEARTS, CardRank.THREE));

        participants = new Participant[]{
                participant,
                new Player("player", null),
        };
    }

    @AfterEach
    void tearDown() {
        stream.close();
    }

    @Test
    void welcome() {
        drawer.welcome();
        var output = bytes.toString();
        assertEquals("Добро пожаловать в Блэкджек\n", output);
    }

    @Test
    void startRound() {
        drawer.startRound(4);
        var output = bytes.toString();
        assertEquals("Раунд 4\n", output);
    }

    @Test
    void dealingCards() {
        drawer.dealingCards();
        var output = bytes.toString();
        assertEquals("Раздача карт...\n", output);
    }

    @Test
    void startTurn() {
        drawer.startTurn(participant);
        var output = bytes.toString();
        assertEquals("Сейчас ходит test\n", output);
    }

    @Test
    void takingCard() {
        drawer.takingCard(participant);
        var output = bytes.toString();
        assertEquals("test берёт карту\n", output);
    }

    @Test
    void endTurn() {
        drawer.endTurn(participant);
        var output = bytes.toString();
        assertEquals("test закончил свой ход\n", output);
    }

    @Test
    void participantsCards() {
        drawer.participantsCards(participants);
        var output = bytes.toString();
        var expected = String.format("Карты участников:\n\t%s: %s\n\t%s: %s\n",
                participants[0], participants[0].listCards(),
                participants[1], participants[1].listCards()
        );
        assertEquals(expected, output);
    }

    @Test
    void blackjackMessage() {
        drawer.blackjackMessage(participant);
        var output = bytes.toString();
        assertEquals("test собрал блэкджек. Поздравляем!\n", output);
    }

    @Test
    void bustedMessage() {
        drawer.bustedMessage(participant);
        var output = bytes.toString();
        assertEquals("Похоже, что test перебрал\n", output);
    }

    @Test
    void winMessage() {
        drawer.winMessage(participant);
        var output = bytes.toString();
        assertEquals("Побеждает test\n", output);
    }

    @Test
    void draw() {
        drawer.draw();
        var output = bytes.toString();
        assertEquals("В этом раунде никто не выиграл\n", output);
    }

    @Test
    void scoreTable() {
        drawer.scoreTable(participants);
        var output = bytes.toString();
        var expected = String.format("Посмотрим на таблицу:\n\t%s: %d\n\t%s: %d\n",
                participants[0], participants[0].getScore(),
                participants[1], participants[1].getScore()
        );
        assertEquals(expected, output);
    }

    @Test
    void emptyDeck() {
        drawer.emptyDeck();
        var output = bytes.toString();
        assertEquals("Похоже, что колода опустела. Достаём новую\n", output);
    }

    @Test
    void thanks() {
        drawer.thanks();
        var output = bytes.toString();
        assertEquals("Спасибо за игру\n", output);
    }

    @Test
    void separator() {
        drawer.separator();
        var output = bytes.toString();
        assertEquals("------------------------------------------\n", output);
    }
}