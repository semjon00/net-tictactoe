package tictactoe;

public class Human implements Player {
    public int gotTurn = -1;

    public void init(int symbol) {}

    public int getTurn()
    {
        // Read turn from interface
        while (ClientAndUI.latestHit == -1) {Main.sleep(25);}
        return ClientAndUI.latestHit;
    }

    public void deltaUpdate(int pos, int value)
    {
        ClientAndUI.placeEvent(pos, value);
    }

    public void gameOver(int reason)
    {
        // Game over window
        ClientAndUI.gameOverNotification(reason);
    }
}
