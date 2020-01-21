package tictactoe;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static Boolean dispatcher(String str)
    {
        if (str.equals(""))
            str = "human:human";
        str = str.toLowerCase();

        String[] ss = str.split(":");

        boolean ok = true;
        Player[] players = new Player[2];
        if (ss.length == 2) {
            for(int i = 0; i < 2; i++)
            {
                if (ss[i].equals("human"))
                    players[i] = new Human;
                if (ss[i].equals("ai"))
                    players[i] = new AI;
                else
                    ok = false;
            }
        } else if (ss.length == 3) {

        }
        else
            ok = false;

        new Game(players);

        return !ok;
    }

    private void mainMenu(Stage primaryStage)
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
            Boolean isCorrectString = dispatcher(userText.getText());
            if (!isCorrectString)
                ClientAndUI.createNotification("Seems wrong to me...");
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
        mainMenu(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
