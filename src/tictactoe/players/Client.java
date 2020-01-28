package tictactoe.players;

import java.io.*;
import java.net.*;

public class Client implements Player {
    Socket socket;
    PrintWriter out;
    BufferedReader in;

    public int mySymbol = -1;

    public Client(Socket socket, PrintWriter out, BufferedReader in, int mySymbol) {
        this.socket = socket;
        this.out = out;
        this.in = in;
        this.mySymbol = mySymbol;

        out.println(mySymbol);
    }

    public void init(int symbol) {

    }

    public int getTurn() {
        // Waits the client to send its turn
        return 0;
    }

    public void deltaUpdate(int pos, int value) {
        // Sends the data to the client
    }

    public void gameOver(int reason) {

    }
}
