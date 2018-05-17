package stranger.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HighScoresController implements Initializable {
    @FXML private Text backText;
    @FXML private ListView scoresListView;
    private GameMainMenuController gameMainMenuController;
    private Scene mainScene;
    private ArrayList scores;
    private ObservableList<Integer> observableScores;

    public void clickBack(MouseEvent event) {
        ((Stage) backText.getParent().getScene().getWindow()).setScene(mainScene);
    }
    @FXML
    public void menuItemMouseEntered(MouseEvent event){
        Rectangle rectangle = ((Rectangle) event.getSource());
        rectangle.setFill(Color.web("#455059"));
        backText.setFill(Color.web("#eaeaea"));
    }
    @FXML  public void menuItemMouseExited(MouseEvent event){
        Rectangle rectangle = ((Rectangle) event.getSource());
        rectangle.setFill(Color.web("#95a1ac"));
        backText.setFill(Color.web("#5c5c5c"));
    }
    public void injectMainControllerandMainScene(GameMainMenuController gameMainMenuController, Scene scene){
        this.gameMainMenuController=gameMainMenuController;
        this.mainScene=scene;
        System.out.println(scene);
    }
    public void injectScoresList(ArrayList scores){
        observableScores= FXCollections.observableArrayList(scores);
        scoresListView.setItems(observableScores);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
