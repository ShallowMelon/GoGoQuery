package pack;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class NavigationUtil {
    public static void switchScene(Stage stage, Scene scene, String title) {
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
}