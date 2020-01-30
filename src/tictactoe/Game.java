package tictactoe;

import tictactoe.players.Player;

import static java.lang.Integer.max;

class Game {
    private int turn = 0;

    private int[] board = new int[9];

    private int getState()
    {
        int state = 0;

        for(int i=0; i<3; i++)
        {
            state = max(state, getStateUtil(i,3));
            state = max(state, getStateUtil(i*3,1));
        }
        state = max(state, getStateUtil(0,4));
        state = max(state, getStateUtil(2,2));

        if (turn == 9 && state == 0)
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

    private int place(int pos, int who)
    {
        if (pos < 0 || pos > 8 || board[pos] != 0)
            return 1;
        board[pos] = who + 1;
        turn++;
        return 0;
    }

    Game(Player[] players) {
        System.out.println(String.format("A new game with %s and %s is spawned",
                players[0].getClass(), players[1].getClass()));

        int playersN = players.length;

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

            UI.placeEvent(turn, currentPlayer);

            for (Player player : players)
                player.deltaUpdate(turn, currentPlayer+1);

            latestState = getState();
            if (latestState != 0)
            {
                for (Player player : players)
                    player.gameOver(latestState);
            } else {
                currentPlayer = (currentPlayer + 1) % playersN;
            }
        }

        UI.gameOverNotification(latestState);

        System.out.println(String.format("A game ends with state %s", latestState));
    }
}
