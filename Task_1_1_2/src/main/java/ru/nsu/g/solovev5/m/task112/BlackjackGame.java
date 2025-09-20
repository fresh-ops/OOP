package ru.nsu.g.solovev5.m.task112;

import ru.nsu.g.solovev5.m.task112.game.Game;
import ru.nsu.g.solovev5.m.task112.game.TextDrawer;
import ru.nsu.g.solovev5.m.task112.game.TextInput;
import ru.nsu.g.solovev5.m.task112.participants.Dealer;
import ru.nsu.g.solovev5.m.task112.participants.Participant;
import ru.nsu.g.solovev5.m.task112.participants.Player;

/**
 * A main game class.
 */
public class BlackjackGame {
    /**
     * The programs entry point.
     *
     * @param args command line args
     */
    public static void main(String[] args) {
        var drawer = new TextDrawer(System.out);
        var input = new TextInput(System.in, System.out);

        drawer.welcome();
        var name = input.nextLine("Пожалуйста, введите своё имя");

        var participants = new Participant[]{
            new Player(name, input),
            new Dealer()
        };

        new Game(participants, drawer, input).run();

        input.close();
        drawer.thanks();
    }

}
