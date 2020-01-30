/*  Packet format:
A string formatted with spaces
*/

package tictactoe;

import tictactoe.players.Client;
import tictactoe.players.Player;

import java.io.*;
import java.net.*;
import java.util.HashMap;

public class Server {
    public static final int defaultPort = 61003;

    public static void send(PrintWriter out, String msg)
    {
        System.out.println("Sent : " + msg);
        out.println(msg);
    }

    public static String recieve(BufferedReader in)
    {
        try{
            String got = in.readLine();
            System.out.println("Recieved: " + got);
            return got;
        } catch (IOException ignored) {}
        return "";
    }

    Server(int port) {
        System.out.println("The SERVER was born to serve lost souls and bring happiness!");

        try {

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

                if (waiting.get(room) == null)
                {
                    waiting.put(room, new Client(socket, out, in, 1));
                    System.out.println(String.format("Put client %s to room %s", socket.getInetAddress(), room));
                }
                else
                {
                    Client our = new Client(socket, out, in, 2);
                    Player[] players = new Player[] {waiting.get(room), our};
                    System.out.println(String.format("Starting game with %s, from room %s", socket.getInetAddress(), room));
                    new Thread(() -> new Game(players)).start();
                    waiting.remove(room);
                }
            }
        } catch (IOException e) {
            System.out.println("The one we had our hopes on, the SERVER, was destroyed due to unknown error!");
        }
    }
}
