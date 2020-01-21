package tictactoe;

import java.io.*;
import java.net.*;

public class Server {
    public class ConnectionRunnable implements Runnable {
        Socket socket;
        DataInputStream in;
        DataOutputStream out;

        private void message(String msg) throws IOException {
            out.writeUTF(msg);
            out.flush();
        }

        public ConnectionRunnable(Socket _socket) {
            socket = _socket;
        }

        public void run()
        {
            try {
                InputStream sin = socket.getInputStream();
                OutputStream sout = socket.getOutputStream();

                in = new DataInputStream(sin);
                out = new DataOutputStream(sout);

                String got = null;
                while(true) {
                    got = in.readUTF();


                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Server() throws IOException {
        int port = 52391; // Net TicTacToe default port
        ServerSocket ss = new ServerSocket(port);

        while(true)
        {
            Socket socket = ss.accept();
            Runnable r = new ConnectionRunnable(socket);
            new Thread(r).start();
        }
    }
}
