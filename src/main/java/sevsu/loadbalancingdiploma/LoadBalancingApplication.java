package sevsu.loadbalancingdiploma;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoadBalancingApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("loadbalancing-view.fxml")));
        stage.getIcons().add(new Image("/icon.png"));
        stage.setTitle("Load Balancing v1.0");
        stage.setScene(new Scene(root, 1200, 780));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}