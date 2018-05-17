package stranger.controllers;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import stranger.game_engine.GameManager;
import stranger.scoring_engine.ScoringManager;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ResourceBundle;

public class GameMainMenuController implements Initializable {

    private static final double SCALING_Y = .7;
    private static final double SCALING_X = .7;
    @FXML private Rectangle startRectangle;
   @FXML private Text startText;
   @FXML   private Rectangle scoreBoardRectangle;
   @FXML private Text scoreBoardText;
   @FXML private Rectangle exitRectangle;
   @FXML  private Text exitText;
   private Rectangle focusedRectangle;
   private Rectangle menuRectangles[];
    private int focusedRectangleIndex;
    private ArrayList<Integer> scores;
    private Scene scene;
    private Stage stage;
    private GameManager gameManager;
   @FXML private Rectangle start1Rectangle;
   @FXML private  Text start1Text;


    @FXML  public void menuItemMouseEntered(MouseEvent event){
      Rectangle rectangle = ((Rectangle) event.getSource());
      rectangle.setFill(Color.web("#455059"));

      if(rectangle.equals(startRectangle))
          startText.setFill(Color.web("#eaeaea"));
      if(rectangle.equals(scoreBoardRectangle))
          scoreBoardText.setFill(Color.web("#eaeaea"));
      if(rectangle.equals(exitRectangle))
          exitText.setFill(Color.web("#eaeaea"));
      if(rectangle.equals(start1Rectangle))
          start1Text.setFill(Color.web("#eaeaea"));
    }
    @FXML  public void menuItemMouseExited(MouseEvent event){
        Rectangle rectangle = ((Rectangle) event.getSource());
        rectangle.setFill(Color.web("#95a1ac"));

        if(rectangle.equals(startRectangle))
            startText.setFill(Color.web("#5c5c5c"));
        if(rectangle.equals(scoreBoardRectangle))
            scoreBoardText.setFill(Color.web("#5c5c5c"));
        if(rectangle.equals(exitRectangle))
            exitText.setFill(Color.web("#5c5c5c"));
    }

    @FXML public void menuRectanglesAction(KeyEvent event){
        KeyCode keyCode = event.getCode();
        switch(keyCode){
            case W:
                previousRectangle();
                System.out.println("pls");
                break;
            case UP:
                previousRectangle();
                break;
            case  S:
                nextRectangle();
                break;
            case DOWN:
                nextRectangle();
        }
    }

    private void nextRectangle() {
        switch (focusedRectangleIndex){
            case 0:
                focusedRectangle = menuRectangles[1];
                focusedRectangleIndex = 1;
                submitChanges(focusedRectangle,menuRectangles[1]);
                break;
            case 1:
                focusedRectangle = menuRectangles[2];
                focusedRectangleIndex = 2;
                submitChanges(focusedRectangle,menuRectangles[2]);
                break;
            case 2:
                focusedRectangle = menuRectangles[0];
                focusedRectangleIndex = 0;
                submitChanges(focusedRectangle,menuRectangles[0]);
                break;
        }
    }

    private void previousRectangle() {
        switch (focusedRectangleIndex){
            case 0:
                focusedRectangle = menuRectangles[2];
                focusedRectangleIndex = 2;
                submitChanges(focusedRectangle,menuRectangles[0]);
                break;
            case 1:
                focusedRectangle = menuRectangles[0];
                focusedRectangleIndex = 0;
                submitChanges(focusedRectangle,menuRectangles[1]);
                break;
            case 2:
                focusedRectangle = menuRectangles[1];
                focusedRectangleIndex = 1;
                submitChanges(focusedRectangle,menuRectangles[2]);
                break;
        }
    }

    private void submitChanges(Rectangle focusedRectangle, Rectangle menuRectangle) {
        focusedRectangle.setFill(Color.web("#455059"));
        if(focusedRectangle.equals(startRectangle))
            startText.setFill(Color.web("#eaeaea"));
        if(focusedRectangle.equals(scoreBoardRectangle))
            scoreBoardText.setFill(Color.web("#eaeaea"));
        if(focusedRectangle.equals(exitRectangle))
            exitText.setFill(Color.web("#eaeaea"));


        menuRectangle.setFill(Color.web("#95a1ac"));

        if(menuRectangle.equals(startRectangle))
            startText.setFill(Color.web("#5c5c5c"));
        if(menuRectangle.equals(scoreBoardRectangle))
            scoreBoardText.setFill(Color.web("#5c5c5c"));
        if(menuRectangle.equals(exitRectangle))
            exitText.setFill(Color.web("#5c5c5c"));
    }
    @FXML  public void clickRectangle(MouseEvent event){
        Rectangle rectangle = ((Rectangle) event.getSource());
        System.out.println("here");
        if(rectangle.equals(startRectangle))
            startRectangleAction();
        if(rectangle.equals(scoreBoardRectangle))
            scoreBoardAction();
        if(rectangle.equals(exitRectangle))
            ((Stage) exitRectangle.getParent().getParent().getScene().getWindow()).close();
        if(rectangle.equals(start1Rectangle)){
            start1RectangleAction();
        }
    }

    private void start1RectangleAction() {
        AnchorPane anchorPane = new AnchorPane();
        Image background0 = new Image("/resources/background.png");
        ImageView imageView = new ImageView(background0);
        anchorPane.setScaleX(SCALING_X);
        anchorPane.setScaleY(SCALING_Y);
        anchorPane.setLayoutX(-(1-SCALING_X)*1920/2);
        anchorPane.setLayoutY(-(1-SCALING_Y)*1080/2+18);
        anchorPane.getChildren().add(0,imageView);
        Image background1 = new Image("/resources/foreground.png");
        ImageView imageView1 = new ImageView(background1);
        anchorPane.getChildren().add(1,imageView1);
        Scene scene = new Scene(anchorPane,SCALING_X*1920-50,SCALING_Y*1080-25);


        stage=  ((Stage) exitRectangle.getParent().getParent().getScene().getWindow());
        stage.setWidth(SCALING_X*1920-50);
        stage.setHeight(SCALING_Y*1080-25);
        stage.setScene(scene);
        gameManager = new GameManager(scene,stage,this,true);
    }

    private void startRectangleAction() {
        AnchorPane anchorPane = new AnchorPane();
        Image background0 = new Image("/resources/background.png");
        ImageView imageView = new ImageView(background0);
        anchorPane.setScaleX(SCALING_X);
        anchorPane.setScaleY(SCALING_Y);
        anchorPane.setLayoutX(-(1-SCALING_X)*1920/2);
        anchorPane.setLayoutY(-(1-SCALING_Y)*1080/2+18);
        anchorPane.getChildren().add(0,imageView);
        Image background1 = new Image("/resources/foreground.png");
        ImageView imageView1 = new ImageView(background1);
        anchorPane.getChildren().add(1,imageView1);
        Scene scene = new Scene(anchorPane,SCALING_X*1920-50,SCALING_Y*1080-25);


        stage=  ((Stage) exitRectangle.getParent().getParent().getScene().getWindow());
        stage.setWidth(SCALING_X*1920-50);
        stage.setHeight(SCALING_Y*1080-25);
              stage.setScene(scene);
         gameManager = new GameManager(scene,stage,this,false);
    }

    private void scoreBoardAction() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/stranger/scenes/HighScores.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        HighScoresController highScoresController = fxmlLoader.getController();
        highScoresController.injectMainControllerandMainScene(this,exitRectangle.getParent().getParent().getScene());
        highScoresController.injectScoresList(scores);
        Scene scene = new Scene(root);
        ((Stage) exitRectangle.getParent().getParent().getScene().getWindow()).setScene(scene);
    }

    public void setTop5Scores(int score){
        scores.add(score);
        Collections.sort(scores);
        Collections.reverse(scores);
        if(scores.size()>5)
            scores =  new ArrayList<>(scores.subList(0,5));
        ScoringManager.saveScores(scores);

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("lggggggggggggg");
        scene = exitRectangle.getParent().getParent().getScene();
            menuRectangles  = new Rectangle[3];
            menuRectangles[0] = startRectangle;
            menuRectangles[1] = scoreBoardRectangle;
            menuRectangles[2] = exitRectangle;
            focusedRectangle = menuRectangles[0];
            focusedRectangleIndex = 0;
            scores = ScoringManager.loadScores();
        System.out.println( exitRectangle.getScene());
        }

    public Scene getScene() {
        return exitRectangle.getParent().getParent().getScene();
    }

    public void returnToMe() {

        stage.setScene(getScene());
    }
}
