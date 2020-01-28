package tictactoe;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tictactoe.players.AI;
import tictactoe.players.Human;
import tictactoe.players.Player;
import tictactoe.players.Retranslator;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

public class Main extends Application {
    private static Stage primaryStage;

    private static Integer dispatcher(String str)
    {
       int errorCode = 0;

        if (str.equals("SERVER"))
        {
            primaryStage.close();
            new Server();
        }

        if (str.equals(""))
            str = "human:human";
        str = str.toLowerCase();

        String[] ss = str.split(":");

        Player[] players = new Player[2];
        if (ss.length == 2) {
            for(int i = 0; i < 2; i++)
            {
                if (ss[i].equals("human"))
                    players[i] = new Human();
                else if (ss[i].equals("ai"))
                    players[i] = new AI();
                else
                    errorCode = 3;
            }
        } else if (ss.length == 3) {
            players[0] = new Human();
            try {
                Retranslator player1 = new Retranslator(str);
                players[1] = player1;

                // Making players to be in a correct order
                if (player1.mySymbol == 2)
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

        Label label0 = new Label("For singleplayer - player:player");
        Label label1 = new Label("For multiplayer - IP:port:room");
        root.getChildren().add(label0);
        root.getChildren().add(label1);

        TextField userText = new TextField();
        root.getChildren().add(userText);

        Button button = new Button("Ok");
        button.setOnAction(e -> {
            Integer errorCode = dispatcher(userText.getText());
            if (errorCode != 0)
            {
                System.out.println();
                UI.createNotification("Seems wrong to me...\nError code: " + errorCode.toString());
            }


        });
        root.getChildren().add(button);

        primaryStage.setTitle("Net TicTacToe");
        primaryStage.setScene(new Scene(root, 300, 100));
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
