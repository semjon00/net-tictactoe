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
    static Stage stage;

    public static void createNotification(String str)
    {
        Label label = new Label(str);
        label.setPadding(new Insets(0, 0, 0, 20));
        Scene secondScene = new Scene(label, 200, 50);

        Stage newWindow = new Stage();
        newWindow.setScene(secondScene);
        newWindow.initModality(Modality.APPLICATION_MODAL);
        newWindow.showAndWait();
    }

    private static void askIp()
    {
        Stage newWindow = new Stage();

        VBox root = new VBox();
        Label label = new Label("Please input the IP:port:room");
        root.getChildren().add(label);
        TextField userText = new TextField();
        root.getChildren().add(userText);
        Button button = new Button("Ok");
        button.setOnAction(e -> {
            if (label.getText().equals("SERVER"))
            {
                new Server();
            }

            newWindow.close();
            new Game(stage, label.getText());
        });
        root.getChildren().add(button);

        Scene secondScene = new Scene(root, 300, 100);

        newWindow.setScene(secondScene);
        newWindow.initModality(Modality.APPLICATION_MODAL);
        newWindow.showAndWait();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        askIp();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
