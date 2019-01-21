package bsr.client;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class AlertWindow {


    public static void show(String message, String title){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);

        VBox vBox = new VBox();
        vBox.setPrefSize(200, 120);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        vBox.setPadding(new Insets(5));

        Label label = new Label(message);
        label.setFont(Font.font("verdana", FontWeight.BOLD, 18));
        Button button = new Button("ok");
        button.setMaxWidth(Double.MAX_VALUE);
        button.setOnAction(e -> window.close());

        vBox.getChildren().addAll(label, button);

        Scene scene = new Scene(vBox);
        window.setTitle(title);
        window.setResizable(false);
        window.setScene(scene);
        window.showAndWait();
    }


}
