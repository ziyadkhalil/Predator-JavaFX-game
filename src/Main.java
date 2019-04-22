import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import stranger.ActorEngine.friends.Stranger;
import stranger.game_engine.GameManager;
import stranger.scoring_engine.ScoringManager;

import java.io.File;
import java.net.URISyntaxException;

public class Main extends Application{

    public static void main(String[] args) {
    launch(args);
        try {
          File file=   new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            System.out.println(file.getPath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    Scene scene;
    Stranger stranger;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/stranger/scenes/gameMainMenu.fxml"));
        primaryStage.setTitle("Predator");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("/resources/HealthBar.png"));
        primaryStage.setResizable(false);
        primaryStage.setWidth(960);
        primaryStage.setHeight(570);
        primaryStage.show();

    }


}
