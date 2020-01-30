package tictactoe.players;

import tictactoe.Main;
import tictactoe.UI;

public class Human implements Player {
    public int gotTurn = -1;

    public void init(int symbol) {}

    public int getTurn()
    {
        // Read turn from interface
        UI.latestHit = -1;
        while (UI.latestHit == -1) {
            Main.sleep(25);
        }
        return UI.latestHit;
    }

    public void deltaUpdate(int pos, int value) {}

    public void gameOver(int reason) {}
}
