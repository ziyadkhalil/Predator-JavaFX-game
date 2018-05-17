package stranger.game_engine;

import javafx.scene.Scene;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import stranger.ActorEngine.*;
import stranger.ActorEngine.enemies.Policeman;
import stranger.ActorEngine.enemies.PolicemanBullet;
import stranger.ActorEngine.enemies.PolicemanStick;
import stranger.ActorEngine.friends.Bullet;
import stranger.ActorEngine.friends.Laser;
import stranger.ActorEngine.friends.Stranger;
import stranger.controllers.GameMainMenuController;

import java.util.ArrayList;
import java.util.Random;

public class GameManager {
    GameMainMenuController gameMainMenuController;
    Stage gameStage;
    Scene gameScene;
    AnchorPane root;
    Stranger stranger;
    public GameLoop gameLoop;
    ArrayList<Actor> actors;
    ArrayList<Actor> removedActors;
    ArrayList<Actor> addedActors;
    AudioClip audioClip;
    ArrayList<EnemyFire> enemyFires;
    ArrayList<FriendlyFire> friendlyFires;
    private ArrayList<Friend> friends;
    private ArrayList<Enemy> enemies;
    Random random;
    private Text scoreText;
    private int score=0;
    private ArrayList<ImageView> healthImageViews;
    private Text gameOverText;
    private boolean gameOver;
    private int enemyGeneratorCounter;
    private boolean multiplayer;
    private Stranger stranger_2;
    private int playersCount=0;
    private ArrayList<ImageView> healthImage2Views;

    public GameManager(Scene gameScene, Stage stage, GameMainMenuController gameMainMenuController,boolean multiplayer) {
        this.multiplayer=multiplayer;
        this.gameMainMenuController = gameMainMenuController;
        this.gameScene = gameScene;
        this.gameStage = stage;
        root = ((AnchorPane) (gameScene.getRoot()));
        actors = new ArrayList<>();
        addedActors = new ArrayList<>();
        removedActors = new ArrayList<>();
        random=new Random();
        enemies = new ArrayList<>();
        enemyFires = new ArrayList<>();
        friends = new ArrayList<>();
        friendlyFires = new ArrayList<>();
        stranger = new Stranger(200, 600,this);
        Policeman policeman = new Policeman(1500, 600);
        policeman.setLookingLeft(true);
        policeman.setManager(this);
        addActor(policeman);
        addActor(stranger);
        if(multiplayer) {
            stranger_2 = new Stranger(400,600,this);
            addActor(stranger_2);
        }
        gameLoop = new GameLoop();
        gameLoop.setManager(this);
        gameLoop.setActors(actors);
        hookUpActorsListeners();
        gameLoop.start();
        audioClip = new AudioClip(getClass().getResource("/resources/song.MP3").toString());
        audioClip.play();
        this.scoreText=new Text(953,115,"0");
        Font font = new Font("Berlin Sans FB Demi",100);
        scoreText.setFont(font);
        Reflection reflection = new Reflection();
        scoreText.setEffect(reflection);
        reflection.setTopOffset(-40);
        scoreText.setFill(Color.web("#dd00b8"));
        scoreText.setStroke(Color.web("#840073"));
        scoreText.setStrokeWidth(4);
        scoreText.setSmooth(true);
        root.getChildren().add(scoreText);
        gameOverText = new Text(700,540,"GAME OVER\nPRESS ENTER ");
        gameOverText.setFont(font);
        gameOverText.setFill(Color.web("#dd00b8"));
        gameOverText.setStroke(Color.web("#840073"));
    }
    public void shoot(Bullet bullet){
        bullet.setManager(this);
        addActor(bullet);
        root.getChildren().add(bullet.getSpriteBounds());
    }

    private void hookUpActorsListeners() {
        gameScene.setOnKeyPressed(keyPressed -> {
            switch(keyPressed.getCode()){
                case W: stranger.setUp(true); break;
                case A: stranger.setLeft(true); break;
                case S: stranger.setDown(true); break;
                case D: stranger.setRight(true); break;
                case SPACE: stranger.setShooting(true); break;
                case F: stranger.setLaserOn(true); break;
                case ESCAPE: gameLoop.toggleOnOff(); break;
                case ENTER: if(gameOver) returnToMainMenu();
            }
        });
        gameScene.setOnKeyReleased(keyPressed -> {
            switch(keyPressed.getCode()){
                case W: stranger.setUp(false); break;
                case A: stranger.setLeft(false); break;
                case S: stranger.setDown(false); break;
                case D: stranger.setRight(false); break;
                case F: stranger.setLaserOn(false);break;
                case SPACE: stranger.setShooting(false);break;
            }
        });
        if(multiplayer){
            gameScene.setOnKeyPressed(keyPressed -> {
                switch(keyPressed.getCode()){
                    case W: stranger.setUp(true); break;
                    case A: stranger.setLeft(true); break;
                    case S: stranger.setDown(true); break;
                    case D: stranger.setRight(true); break;
                    case SPACE: stranger.setShooting(true); break;
                    case F: stranger.setLaserOn(true); break;
                    case ESCAPE: gameLoop.toggleOnOff(); break;
                    case ENTER: if(gameOver) returnToMainMenu(); break;
                    case UP: stranger_2.setUp(true); break;
                    case LEFT: stranger_2.setLeft(true); break;
                    case DOWN: stranger_2.setDown(true); break;
                    case RIGHT: stranger_2.setRight(true); break;
                    case L: stranger_2.setShooting(true); break;
                    case K: stranger_2.setLaserOn(true); break;
                }
            });
            gameScene.setOnKeyReleased(keyPressed -> {
                switch(keyPressed.getCode()){
                    case UP: stranger_2.setUp(false); break;
                    case LEFT: stranger_2.setLeft(false); break;
                    case DOWN: stranger_2.setDown(false); break;
                    case RIGHT: stranger_2.setRight(false); break;
                    case K: stranger_2.setLaserOn(false);break;
                    case L: stranger_2.setShooting(false);break;
                    case W: stranger.setUp(false); break;
                    case A: stranger.setLeft(false); break;
                    case S: stranger.setDown(false); break;
                    case D: stranger.setRight(false); break;
                    case F: stranger.setLaserOn(false);break;
                    case SPACE: stranger.setShooting(false);break;
                }
            });
        }
    }

    private void returnToMainMenu() {
        System.out.println("heereere");
        System.out.println(gameStage);
        audioClip.stop();
        System.out.println(gameMainMenuController.getScene());
        gameStage.setHeight(570);
        gameStage.setWidth(960);
        gameStage.setScene(gameMainMenuController.getScene());
    }

    public void update() {
        for (Actor actor:
                removedActors) {
            root.getChildren().remove(actor.getCurrentSprite());
            actors.remove(actor);
            actor=null;
        }
        removedActors.clear();
        for (Actor actor:
                addedActors) {
          //  System.out.println(actor);
            root.getChildren().add(actor.getCurrentSprite());
            actors.add(actor);
        }
        addedActors.clear();
        if(enemies.size()==0&&enemyGeneratorCounter>60) {
            if(Integer.parseInt(scoreText.getText()) <100) addPolice();
            else if(Integer.parseInt(scoreText.getText()) <200) {
                addPolice();
                addPolice();
            }
            else if (Integer.parseInt(scoreText.getText()) <300)
            {
                addPolice();
                addPolice();
                addPolice();

            }
            enemyGeneratorCounter=0;
        }
        enemyGeneratorCounter++;
        for (Friend friend:
             friends) {
            score+=friend.getScore();
        }
        scoreText.setText(String.valueOf(score));
        score=0;

    }


    public ArrayList<Actor> getActors() {
        return actors;
    }

    public void setActors(ArrayList<Actor> actors) {
        this.actors = actors;
    }

    public void removeActor(EnemyFire enemyFire){
        enemies.remove(enemyFire);
        removedActors.add((Actor) enemyFire);
        ((Actor) enemyFire).getSpriteBounds().setContent("");
    }
int dist = 10;
    public void addPolice(){
        Policeman policeman =new Policeman(random.nextDouble()*1720,600);
        policeman.setManager(this);
      //  actors.add(policeman);
       // root.getChildren().add(policeman.getCurrentSprite());
        addActor(policeman);
    }


    public void shoot(Laser laser) {
        laser.setLaserOn();
        addActor(laser);
    }




    public void addActor(FriendlyFire friendlyFire){
        this.friendlyFires.add(friendlyFire);
        addedActors.add((Actor) friendlyFire);
        ((Actor) friendlyFire).setManager(this);
    }
    public void addActor(EnemyFire enemyFire){
        this.enemyFires.add(enemyFire);
        addedActors.add((Actor) enemyFire);
        ((Actor) enemyFire).setManager(this);
    }
    public void addActor(Enemy enemy){
        this.enemies.add(enemy);
        addedActors.add((Actor) enemy);
        ((Actor) enemy).setManager(this);
    }
    public void addActor(Friend friend){
        this.friends.add(friend);
        addedActors.add((Actor) friend);
        ((Actor) friend).setManager(this);
    }
    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public ArrayList<FriendlyFire> getFriendlyFires() {
        return friendlyFires;
    }

    public void removeActor(FriendlyFire friendlyFire) {
        friendlyFires.remove(friendlyFire);
        removedActors.add((Actor) friendlyFire);
        ((Actor) friendlyFire).getSpriteBounds().setContent("");
    }
    public void removeActor(Friend friend) {
        if(friends.size()==1){
            root.getChildren().add(gameOverText);
            gameOver=true;
            gameMainMenuController.setTop5Scores(Integer.parseInt(scoreText.getText()));
            gameLoop.stop();
            return;
        }
        friends.remove(friend);
        ((Actor) friend).getSpriteBounds().setContent("");

        removedActors.add((Actor) friend);

    }
    public void removeActor(Enemy enemy) {
        enemies.remove(enemy);
        removedActors.add((Actor) enemy);
        ((Actor) enemy).getSpriteBounds().setContent("");
    }

    public void shoot(PolicemanBullet bullet) {
        bullet.setManager(this);
        addActor(bullet);
       // bullet.getSpriteBounds().setFill(Paint.valueOf("red"));
      //  root.getChildren().add(bullet.getSpriteBounds());
    }

    public ArrayList<Friend> getFriends() {
        return friends;
    }

    public void shoot(PolicemanStick policemanStick) {
        addActor(policemanStick);
       // root.getChildren().add(policemanStick.stick);
    }

    public void setHealthBar(Stranger stranger, Image[] healthBar) {
        if(playersCount==0){
            healthImageViews = new ArrayList<ImageView>();
            for (int i = 0; i < healthBar.length; i++) {
                healthImageViews.add(new ImageView(healthBar[i]));
                healthImageViews.get(i).setX(1900-100*(i+1));
                healthImageViews.get(i).setY(20);
                root.getChildren().add(healthImageViews.get(i));

            }
            playersCount++;
        }
        if(playersCount==1){
            healthImage2Views = new ArrayList<>();
            for (int i = 0; i < healthBar.length; i++) {
                healthImage2Views.add(new ImageView(healthBar[i]));
                healthImage2Views.get(i).setX(800-100*(i+1));
                healthImage2Views.get(i).setY(20);
                root.getChildren().add(healthImage2Views.get(i));
                System.out.println("yaraaab");
            }
        }

    }

    public void decreaseHealthBar(Stranger stranger) {
       if(stranger.equals(this.stranger)) {
           if (healthImageViews.size()!=0) {
                       root.getChildren().remove(healthImageViews.get(healthImageViews.size()-1));
                       healthImageViews.remove(healthImageViews.size()-1);
                   }
       }
       else if(stranger.equals(stranger_2)){
           if (healthImage2Views.size()!=0) {
               root.getChildren().remove(healthImage2Views.get(healthImage2Views.size()-1));
               healthImage2Views.remove(healthImage2Views.size()-1);
           }
       }
    }
}
