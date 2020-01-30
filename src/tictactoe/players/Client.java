package tictactoe.players;

import tictactoe.Server;

import java.io.*;
import java.net.*;

public class Client implements Player {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    private int mySymbol = -1;

    public Client(Socket socket, PrintWriter out, BufferedReader in, int mySymbol) {
        this.socket = socket;
        this.out = out;
        this.in = in;
        this.mySymbol = mySymbol;

        out.println(mySymbol);
    }

    public void init(int symbol){ /* Does nothing */ }

    public int getTurn() {
        // Get from the client
        String got = Server.recieve(in);
        if (got.startsWith("turn ")) {
            return Integer.parseInt(got.split(" ")[1]);
        }
        return -1;
    }

    public void deltaUpdate(int pos, int value) {
        // Send to the client
        if (value != mySymbol)
            Server.send(out, "turned " + pos);
    }

    public void gameOver(int reason) {
        // Close connection
    }
}
