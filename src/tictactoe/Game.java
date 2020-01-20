package tictactoe;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

import static java.lang.Integer.max;

public class Game {
    GridPane root;

    int turn = 0;
    int[] board = new int[9];
    Button[] buttons = new Button[9];
    int myFigure = -1;


    private void restart()
    {
        turn = 0;
        board = new int[9];
        for (int i=0; i<9; i++)
        {
            buttons[i].setMouseTransparent(false);
            buttons[i].setGraphic(new ImageView());
        }
    }

    private void placeEvent(int buttonI)
    {
        // Ignore wrong turns
        if (board[buttonI] != 0)
            return;

        // Change button state
        Image img = new Image((turn % 2 == 0 ?  "file:img/cross.png" : "file:img/circle.png"),
                60, 60, true, true);
        ImageView imgV = new ImageView(img);
        buttons[buttonI].setMouseTransparent(true);
        buttons[buttonI].setGraphic(imgV);

        board[buttonI] = (turn % 2) + 1;

        // Check if it is a game over
        int win = checkWinCondition();
        if (turn == 8 && win == 0)
            win = 3;
        if (win != 0)
        {
            // Notification
            String text;
            if (win == 3)
                text = "It is a draw";
            else
                text = (win == 1 ? "Crosses win!" : "Circles win!");
            tictactoe.Main.createNotification(text);

            restart();
        }
        else
            turn++;
    }

    private int checkWinCondition()
    {
        int flag = 0;

        for(int i=0; i<3; i++)
        {
            flag = max(flag, checkWinConditionUtil(i,3));
            flag = max(flag, checkWinConditionUtil(i*3,1));
        }
        flag = max(flag, checkWinConditionUtil(0,4));
        flag = max(flag, checkWinConditionUtil(2,2));

        return flag;
    }

    private int checkWinConditionUtil(int f, int d)
    {
        if(board[f] == board[f+d] && board[f+d] == board[f+2*d])
        {
            return board[f];
        }
        return 0;
    }

    public Game(Stage primaryStage, String netString) {
        //String[] netStringSplit = netString.split(":");
        //Socket socket = new Socket(netStringSplit[0], Integer.parseInt(netStringSplit[1]));

        root = new GridPane();
        root.setPadding(new Insets(20));
        root.setHgap(10);
        root.setVgap(10);

        for(int i=0; i<9; i++)
        {
            Button b = new Button();
            buttons[i] = b;

            final int fi = i;
            b.setOnAction(e -> placeEvent(fi));
            b.setPrefSize(90,90);
            b.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
            root.add(b, i/3, i%3);
        }

        primaryStage.setTitle("Net TicTacToe");
        primaryStage.setScene(new Scene(root, 300, 300));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
