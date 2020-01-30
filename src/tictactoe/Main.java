package tictactoe;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import tictactoe.players.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

public class Main extends Application {
    private static Stage primaryStage;

    private static Integer dispatcher(String str)
    {
       int errorCode = 0;

        if (str.startsWith("SERVER"))
        {
            String[] ss = str.split(":");
            int port = Server.defaultPort;
            try {
                port = Integer.parseInt(ss[1]);
            } catch (Exception ignored) {}

            primaryStage.close();
            new Server(port);
        }

        if (str.equals(""))
            str = ":";
        str = str.toLowerCase();

        String[] ss = str.split(":", -1);

        Player[] players = new Player[2];
        if (ss.length == 2) {
            for(int i = 0; i < 2; i++)
            {
                if (ss[i].equals("human") || ss[i].equals(""))
                    players[i] = new Human();
                else if (ss[i].equals("ai"))
                    players[i] = new AI();
                else
                    errorCode = 3;
            }
        } else if (ss.length == 3) {
            try {
                players[0] = new Human();
                players[1] = new Retranslator(str);

                // Making players to be in a correct order
                if (((Retranslator)players[1]).mySymbol == 2)
                    Collections.swap(Arrays.asList(players), 0, 1);
            } catch (IOException e) {
                errorCode = 2;
            }
        }
        else
            errorCode = 1;

        if (errorCode == 0)
        {
            UI.init();
            new Thread(() -> new Game(players)).start();
        }

        return errorCode;
    }

    private void mainMenu()
    {
        VBox root = new VBox();
        root.setPadding(new Insets(7, 10, 0, 10));

        // Text
        Label label0 = new Label("For singleplayer - [player]:[player]");
        Label label1 = new Label("For multiplayer - [IP]:[port]:[room]");
        root.getChildren().add(label0);
        root.getChildren().add(label1);

        // Create wrap
        HBox wrap = new HBox(15);
        wrap.setPadding(new Insets(10,0,0,0));

        // Field
        TextField userText = new TextField();
        userText.setMinWidth(200);
        wrap.getChildren().add(userText);

        // Button
        Button button = new Button("Play");
        button.setOnAction(e -> {
            Integer errorCode = dispatcher(userText.getText());
            if (errorCode != 0)
            {
                System.out.println();
                UI.createNotification("Seems wrong to me...\nError code: " + errorCode.toString());
            }
        });
        wrap.getChildren().add(button);

        root.getChildren().add(wrap);

        primaryStage.setTitle("Net TicTacToe");
        primaryStage.setScene(new Scene(root, 280, 90));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void sleep(int ms)
    {
        try {
            Thread.sleep(ms);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void start(Stage primaryStage) {
        Main.primaryStage = primaryStage;
        mainMenu();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
