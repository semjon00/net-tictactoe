package tictactoe;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;

public class UI {
    static GridPane root;
    static Button[] buttons = new Button[9];

    static int latestHit = -1;

    private void restart()
    {
        for (int i=0; i<9; i++)
        {
            buttons[i].setMouseTransparent(false);
            buttons[i].setGraphic(new ImageView());
        }
    }

    public static void createNotification(String str)
    {
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                Label label = new Label(str);
                label.setPadding(new Insets(0, 0, 0, 20));
                Scene secondScene = new Scene(label, 200, 50);

                Stage newWindow = new Stage();
                newWindow.setScene(secondScene);
                newWindow.initModality(Modality.APPLICATION_MODAL);
                newWindow.show();
            }
        });
    }

    static public void placeEvent(int pos, int value) {
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                // Change button state
                Image img = new Image((value == 1 ? "file:img/cross.png" : "file:img/circle.png"),
                        60, 60, true, true);
                ImageView imgV = new ImageView(img);
                UI.buttons[pos].setMouseTransparent(true);
                UI.buttons[pos].setGraphic(imgV);
            }
        });
    }

    public static void gameOverNotification(int lineI) {
        String text;
        if (lineI == 3)
            text = "It is a draw";
        else
            text = (lineI == 1 ? "Circles win!" : "Crosses win!");
        createNotification(text);
    }

    static void init() {
        root = new GridPane();
        root.setPadding(new Insets(20));
        root.setHgap(10);
        root.setVgap(10);
        for(int i=0; i<9; i++)
        {
            Button b = new Button();
            buttons[i] = b;

            final int fi = i;
            b.setOnAction(e -> latestHit = fi);
            b.setPrefSize(90,90);
            b.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
            root.add(b, i/3, i%3);
        }

        Scene secondScene = new Scene(root, 300, 300);

        Stage newWindow = new Stage();
        newWindow.setTitle("TicTacToe game");
        newWindow.setScene(secondScene);
        //newWindow.setResizable(false);
        // Breaks cross button
        //window.initModality(Modality.APPLICATION_MODAL);
        newWindow.show();
    }
}
