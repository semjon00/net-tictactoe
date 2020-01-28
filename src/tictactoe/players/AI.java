package tictactoe.players;

import tictactoe.Main;

import java.util.Random;

public class AI implements Player {
    private int mySymbol = -1;
    private int[] board = new int[9];

    public void init(int symbol)
    {
        mySymbol = symbol;
    }

    public int getTurn()
    {
        Main.sleep(new Random().nextInt(300) + 300);

        // Very dumb AI
        int free = 9;
        for (int i = 0; i < 9; i++)
        {
            if (board[i] != 0)
                free--;
        }
        int place = new Random().nextInt(free);
        for(int i = 0; i < 9; i++)
        {
            if(board[i] == 0)
                place--;
            if (place == -1)
                return i;
        }
        return -1;
    }

    public void deltaUpdate(int pos, int value)
    {
        board[pos] = value;
    }

    public void gameOver(int reason) {}
}
