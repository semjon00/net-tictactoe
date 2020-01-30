package tictactoe.players;

import tictactoe.Server;

import java.io.*;
import java.net.Socket;

public class Retranslator implements Player {
    public int mySymbol = -1;

    Socket socket;
    PrintWriter out;
    BufferedReader in;

    public Retranslator(String netString) throws IOException {
        // Open connection, notify room, assign symbol

        String[] netStringSplit = netString.split(":", -1);
        String address = netStringSplit[0];
        if (address.equals(""))
            address = "localhost";
        int port;
        try {
            port = Integer.parseInt(netStringSplit[1]);
        } catch (NumberFormatException e) {
            port = Server.defaultPort;
        }
        String room = netStringSplit[2];
        if (room.equals(""))
            room = "default";

        socket = new Socket(address, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        out.println("connect " + room);
        mySymbol = Integer.parseInt(Server.recieve(in));
    }

    public void init(int symbol){ /* Does nothing */ }

    public int getTurn()
    {
        // Get from the server
        String got = Server.recieve(in);
        if (got.startsWith("turned ")) {
            return Integer.parseInt(got.split(" ")[1]);
        }
        return -1;
    }

    public void deltaUpdate(int pos, int value)
    {
        // Send to the server
        if (value == mySymbol)
            Server.send(out, "turn " + pos);
    }

    public void gameOver(int reason)
    {
        // Close connection
    }
}
