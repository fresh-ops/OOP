package ru.nsu.g.solovev5.m.task112.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.g.solovev5.m.task112.cards.Card;
import ru.nsu.g.solovev5.m.task112.cards.CardRank;
import ru.nsu.g.solovev5.m.task112.cards.CardSuit;
import ru.nsu.g.solovev5.m.task112.participants.Participant;
import ru.nsu.g.solovev5.m.task112.participants.Player;

class RoundTest {
    Participant[] participants;
    TextDrawer drawer;
    ArrayList<Card> cards;
    Card ace = new Card(CardSuit.HEARTS, CardRank.ACE);
    Card jack = new Card(CardSuit.CLUBS, CardRank.JACK);
    Card three = new Card(CardSuit.DIAMONDS, CardRank.THREE);

    @BeforeEach
    void setUp() {
        participants = new Participant[2];

        var outputBytes = new ByteArrayOutputStream();
        var printStream = new PrintStream(outputBytes);
        drawer = new TextDrawer(printStream);

        cards = new ArrayList<>();
    }

    TextInput setUpTextInput(String input) {
        var inputBytes = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        var outputBytes = new ByteArrayOutputStream();
        var printStream = new PrintStream(outputBytes);

        return new TextInput(inputBytes, printStream);
    }

    @Test
    void checkDefaultRound() {
        var textInput = setUpTextInput("1 1 0 1 0");

        participants[0] = new Player("player A", textInput);
        participants[1] = new Player("player B", textInput);

        cards.add(jack);
        cards.add(three);
        cards.add(ace);

        new Round(participants, drawer, () -> cards.remove(0)).run();

        assertEquals(1, participants[0].getScore());
        assertEquals(13, participants[0].getCost());

        assertEquals(0, participants[1].getScore());
        assertEquals(11, participants[1].getCost());
    }

    @Test
    void checkBlackjack() {
        var textInput = setUpTextInput("1 0 1 1 0");

        participants[0] = new Player("player A", textInput);
        participants[1] = new Player("player B", textInput);

        cards.add(three);
        cards.add(jack);
        cards.add(ace);

        new Round(participants, drawer, () -> cards.remove(0)).run();

        assertEquals(0, participants[0].getScore());
        assertEquals(3, participants[0].getCost());
        assertFalse(participants[0].hasBlackjack());

        assertEquals(1, participants[1].getScore());
        assertEquals(21, participants[1].getCost());
        assertTrue(participants[1].hasBlackjack());
    }

    @Test
    void checkFalseBlackjack() {
        var textInput = setUpTextInput("1 1 1 0 1 0");

        participants[0] = new Player("player A", textInput);
        participants[1] = new Player("player B", textInput);

        cards.add(jack);
        cards.add(jack);
        cards.add(ace);
        cards.add(three);

        new Round(participants, drawer, () -> cards.remove(0)).run();

        assertEquals(1, participants[0].getScore());
        assertEquals(21, participants[0].getCost());
        assertFalse(participants[0].hasBlackjack());

        assertEquals(0, participants[1].getScore());
        assertEquals(3, participants[1].getCost());
        assertFalse(participants[1].hasBlackjack());
    }

    @Test
    void checkDoubleBlackjack() {
        var textInput = setUpTextInput("1 1 0 1 1 0");

        participants[0] = new Player("player A", textInput);
        participants[1] = new Player("player B", textInput);

        cards.add(jack);
        cards.add(ace);
        cards.add(jack);
        cards.add(ace);

        new Round(participants, drawer, () -> cards.remove(0)).run();

        assertEquals(1, participants[0].getScore());
        assertEquals(21, participants[0].getCost());
        assertTrue(participants[0].hasBlackjack());

        assertEquals(1, participants[1].getScore());
        assertEquals(21, participants[1].getCost());
        assertTrue(participants[1].hasBlackjack());
    }

    @Test
    void checkBust() {
        var textInput = setUpTextInput("1 1 1 1 0");

        participants[0] = new Player("player A", textInput);
        participants[1] = new Player("player B", textInput);

        cards.add(three);
        cards.add(jack);
        cards.add(jack);
        cards.add(ace);

        new Round(participants, drawer, () -> cards.remove(0)).run();

        assertEquals(0, participants[0].getScore());
        assertEquals(23, participants[0].getCost());
        assertTrue(participants[0].hasBust());

        assertEquals(1, participants[1].getScore());
        assertEquals(11, participants[1].getCost());
        assertFalse(participants[1].hasBust());
    }

    @Test
    void checkDoubleBust() {
        var textInput = setUpTextInput("1 1 1 1 1 1");

        participants[0] = new Player("player A", textInput);
        participants[1] = new Player("player B", textInput);

        cards.add(three);
        cards.add(jack);
        cards.add(jack);
        cards.add(three);
        cards.add(jack);
        cards.add(jack);

        new Round(participants, drawer, () -> cards.remove(0)).run();

        assertEquals(0, participants[0].getScore());
        assertEquals(23, participants[0].getCost());
        assertTrue(participants[0].hasBust());

        assertEquals(0, participants[1].getScore());
        assertEquals(23, participants[1].getCost());
        assertTrue(participants[1].hasBust());
    }

    @Test
    void checkDraw() {
        var textInput = setUpTextInput("1 0 1 0");

        participants[0] = new Player("player A", textInput);
        participants[1] = new Player("player B", textInput);

        cards.add(jack);
        cards.add(jack);

        new Round(participants, drawer, () -> cards.remove(0)).run();

        assertEquals(1, participants[0].getScore());
        assertEquals(10, participants[0].getCost());

        assertEquals(1, participants[1].getScore());
        assertEquals(10, participants[1].getCost());
    }
}