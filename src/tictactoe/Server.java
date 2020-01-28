/*  Packet format:
A string formatted with spaces
*/

package tictactoe;

import tictactoe.players.Client;
import tictactoe.players.Player;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class Server {
    public Server() {
        System.out.println("The SERVER was born to serve lost souls and bring happiness!");

        try {
            int port = 61003; // Net TicTacToe default port
            HashMap<String, Player> waiting = new HashMap<>();

            ServerSocket ss;

            ss = new ServerSocket(port);

            while (true)
            {
                Socket socket = ss.accept();

                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String helloLine = in.readLine();
                if (!helloLine.startsWith("connect"))
                    continue;
                String room = helloLine.split(" ")[1];

                System.out.println(String.format("Put client %s to room %s", socket.getInetAddress(), room));
                if (waiting.get(room) == null)
                {
                    waiting.put(room, new Client(socket, out, in, 1));
                }
                else
                {
                    new Thread(() -> new Game(new Player[]
                            {waiting.get(room), new Client(socket, out, in, 2)}
                            ));
                    waiting.remove(room);
                }
            }
        } catch (IOException e) {
            System.out.println("The one we had our hopes on, the SERVER, was destroyed due to unknown error!");
        }
    }
}
