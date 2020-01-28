package tictactoe.players;

import java.io.*;
import java.net.Socket;

public class Retranslator implements Player {
    public int mySymbol = -1;

    Socket socket;
    PrintWriter out;
    BufferedReader in;

    public Retranslator(String netString) throws IOException {
        // Open connection, notify room, assign symbol

        String[] netStringSplit = netString.split(":");
        socket = new Socket(netStringSplit[0], Integer.parseInt(netStringSplit[1]));

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        out.println("connect " + netStringSplit[2]);
        mySymbol = Integer.parseInt(in.readLine());
    }

    public void init(int symbol){ /* Does nothing */ }

    public int getTurn()
    {
        // Get from the server
        try {
            String line = in.readLine();
            String[] splitLine = line.split("/");

        } catch (IOException e) {
            // Continue and return -1
        }
        return -1;
    }

    public void deltaUpdate(int pos, int value)
    {
        // Send to the server
    }

    public void gameOver(int reason)
    {
        // Close connection
    }
}
