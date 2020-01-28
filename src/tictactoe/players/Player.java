package tictactoe.players;

public interface Player {
    void init(int symbol);
    int getTurn(); // Receive player's turn
    void deltaUpdate(int pos, int value);
    void gameOver(int reason);
}
