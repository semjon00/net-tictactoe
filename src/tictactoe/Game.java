package tictactoe;

import static java.lang.Integer.max;

public class Game {
    int turn = 0;

    int[] board = new int[9];

    int getState()
    {
        int state = 0;

        for(int i=0; i<3; i++)
        {
            state = max(state, getStateUtil(i,3));
            state = max(state, getStateUtil(i*3,1));
        }
        state = max(state, getStateUtil(0,4));
        state = max(state, getStateUtil(2,2));

        if (turn == 8 && state == 0)
            state = 3;
        return state;
    }

    private int getStateUtil(int first, int delta)
    {
        if(board[first] == board[first+delta] && board[first+delta] == board[first+2*delta])
        {
            return board[first];
        }
        return 0;
    }

    void reset()
    {
        turn = 0;
        board = new int[9];
    }

    int place(int pos, int who)
    {
        if (pos < 0 || pos > 8 || board[pos] != 0)
            return 1;
        board[pos] = who + 1;
        turn++;
        return 0;
    }

    public Game(Player[] players) {
        int playersN = players.length;
        // Make players

        // Play
        for (int i=0; i<playersN; i++)
            players[i].init(i+1);
        int currentPlayer = 0;
        int latestState = 0;
        while(latestState == 0)
        {
            int turn = -1;
            while(turn == -1)
            {
                turn = players[currentPlayer].getTurn();
                if (place(turn, currentPlayer) != 0)
                    turn = -1;
            }

            for (int i=0; i<playersN; i++)
                players[i].deltaUpdate(turn, currentPlayer);

            latestState = getState();
            if (latestState != 0)
            {
                for (int i=0; i<playersN; i++)
                    players[i].gameOver(latestState);
            }

            currentPlayer = (currentPlayer + 1) % playersN;
        }
    }
}
