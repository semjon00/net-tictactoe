package tictactoe;

import java.io.*;
import java.net.Socket;

public class Net implements Player {
    int mySymbol = -1;
    int[] board = new int[9];

    public void init(int symbol){
        mySymbol = symbol;

        // Connecting to the server
        //String[] netStringSplit = netString.split(":");
        //Socket socket = new Socket(netStringSplit[0], Integer.parseInt(netStringSplit[1]));

        //InputStream sin = socket.getInputStream();
        //OutputStream sout = socket.getOutputStream();

        //DataInputStream in = new DataInputStream(sin);
        //DataOutputStream out = new DataOutputStream(sout);
    }

    public int getTurn()
    {
        return -1;
    }

    public void deltaUpdate(int pos, int value)
    {
        board[pos] = value;
    }

    public void gameOver(int reason)
    {

    }
}
