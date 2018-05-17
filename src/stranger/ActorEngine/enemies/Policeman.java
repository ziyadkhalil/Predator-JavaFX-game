package stranger.ActorEngine.enemies;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import stranger.ActorEngine.Actor;
import stranger.ActorEngine.Enemy;

public class Policeman extends Actor implements Enemy {
    private static final int DIE_FPS = 15;
    private static final int RUN_FPS = 36 ;
    private static final int SHOOTING_FPS = 84 ;
    private Image[] runSpriteSheet;
    private Image[] idleSpriteSheet;
    private Image[] hurtSpriteSheet;
    private Image[] shootSpriteSheet;
    private Image[] dieSpriteSheet;
    private Image[] hitStickSpriteSheet;

    int jumpFPSCount=0;
    final int JUMP_FPS=60;
    int idleFPSCount=0;
    final int IDLE_FPS=112;
    final int HIT_STICK_FPS=60;
    private int hitStickCount=0;
    private boolean isColliding = false;
    private boolean isShot = false;
    private boolean hurtAnimation;
    private int hurtFPSCount=0;
    private final int HURT_FPS = 30;
    private int health=3;
    private int dieFPSCount=0;
    private boolean dying=false;
    private int runningFPSCount = 0;
    private boolean running=true;
    private int shootingFPSCount;
    private boolean shootingAnimation;
    private boolean shooting;
    private boolean lookingLeft;
    private double bulletOffsetX=50;
    private double bulletOffsetY=0;
    PolicemanBullet bullet;
    private boolean hitStickAnimation;
    PolicemanStick policemanStick;



    public Policeman(double dX, double dY) {
        super(dX, dY);
        this.spriteBounds.setContent("M84.2,139.1c-1.5-0.8-4-1-4.7-2.1c-0.7-1.1,1.7-1.4,2.6-1.5c0.9-0.1,1.9-0.3,1.9-1.1c0-0.2-6.1-1.4-6.8-1.4\n" +
                "\tc-0.3,0-0.5,0-0.8,0.1c-1.3-3.2-9.8-9-13.8-11.4c0-0.1,0-0.3,0-0.4c-0.3-2.1-1.4-6.9-2.8-12.1c0.6-0.4,0.9-0.7,0.8-0.9\n" +
                "\tc0,0,0-0.1,0-0.1c0-0.5-0.6-1.1-0.8-1c0.1-0.2-0.1-1-0.2-1.1c0,0,0,0,0,0c0.6,0.1,0.6-0.8,0.2-1.3c-0.1-0.1-0.2-0.2-0.5-0.2\n" +
                "\tc-1.6-4.6-3.1-8.3-4.3-10.2c0,0,0,0,0,0c0,0,0-0.1,0-0.1c0,0,0,0,0,0c0,0,0,0,0,0c0.1-0.1-0.2-0.5-0.7-1c-0.1-0.1-0.2-0.2-0.4-0.4\n" +
                "\tc-0.2-0.2-0.4-0.4-0.6-0.6c-0.2-0.2-0.5-0.4-0.7-0.6c-0.1-0.1-0.1-0.1-0.2-0.2l0,0l0,0c-0.1,0-0.1-0.1-0.2-0.1c0,0,0,0-0.1,0\n" +
                "\tc0,0,0,0,0,0c0,0-0.1-0.1-0.1-0.1c0,0,0,0-0.1-0.1c0,0-0.1-0.1-0.1-0.1c0,0-0.1,0-0.1-0.1c0,0,0,0,0,0c0,0-0.1,0-0.1-0.1\n" +
                "\tc0,0-0.1,0-0.1-0.1c0,0-0.1-0.1-0.1-0.1c0,0-0.1,0-0.1-0.1c0,0-0.1,0-0.1-0.1c0,0-0.1,0-0.1-0.1c0,0,0,0,0,0c0,0,0,0-0.1,0\n" +
                "\tc0,0-0.1,0-0.1,0c0,0-0.1,0-0.1,0c0,0-0.1,0-0.1,0c0,0,0,0,0,0c0,0,0,0,0,0c0,0,0,0-0.1,0c0,0,0,0,0,0c0,0,0,0,0,0c0,0,0,0-0.1,0\n" +
                "\tc0,0,0,0-0.1,0c0,0,0,0,0,0c0,0,0,0-0.1,0c0,0,0,0-0.1,0c0,0,0,0,0,0c0,0,0,0,0,0c0,0,0,0,0,0c0,0,0,0,0,0c0,0,0,0,0,0c0,0,0,0,0,0\n" +
                "\tc0,0,0,0,0,0c0,0,0,0,0,0c0,0,0,0,0,0c0,0,0,0,0,0c0,0,0,0,0,0c0,0,0,0,0,0c0,0,0,0.1,0,0.1c0,0,0,0.1,0,0.2c-0.7-0.1-1.3-0.3-2-0.4\n" +
                "\tc0.6-0.9,1-1.7,1.3-2.4c0,0,0,0,0.1,0c0.1-0.3,0.2-0.7,0.3-1.1c2.4,0.3,4.8,0.4,7.1,0.2c7.6,0.3,20.4-18.3,21-25.2\n" +
                "\tc0.2-2.3,0.2-3.7,0-4.7c0.8-1.3,1-2.6,1.2-3.4c0,0,0,0,0,0c0.5-1.8,1.4-6,1.4-6c0,0,0,0,0,0c-0.1-0.4-1.1-0.7-2.8-1\n" +
                "\tc0.6-2.6,1.3-5.3,1.1-9.3c-0.1-1.4-0.2-2.9-0.4-4.4c0,0,0,0,0,0c0,0,0.1,0.1,0.1,0.1c0,0,0,0,0,0c0,0,0,0,0,0c0,0,0,0,0,0\n" +
                "\tc0,0,0,0,0.1,0c0,0,0,0,0,0c0,0,0,0,0,0c0,0,0,0,0,0c0,0,0,0,0,0c0,0,0,0,0,0c0,0,0.1,0,0.1,0.1c0,0,0,0,0,0c0-0.1,0-0.2,0-0.4\n" +
                "\tc0,0,0,0,0,0c0-0.1,0-0.2,0-0.3c0,0,0,0,0,0c0-0.1,0-0.3-0.1-0.4c0-0.1,0-0.2-0.1-0.3c0-0.1-0.1-0.3-0.1-0.4\n" +
                "\tc0.1-0.2,0.1-0.4,0.1-0.6c0.1,0.1,0.2,0.1,0.2,0.2c0,0,0,0,0,0c0.1,0,0.1,0,0.2,0c0,0,0,0,0,0c0,0,0,0,0,0c0,0,0.1,0,0.1,0\n" +
                "\tc0,0,0,0,0,0c0,0,0,0,0,0c0,0,0,0,0,0c0.1,0,0.1,0,0.1,0c0,0,0,0,0,0c0,0,0,0,0,0c0,0,0.1,0,0.1,0c0,0,0,0,0,0c0,0,0,0,0,0\n" +
                "\tc0,0,0,0,0,0c0,0,0.1,0,0.1,0c0,0,0,0,0,0c0,0,0,0,0,0c0,0,0.1,0,0.1,0c0,0,0,0,0,0c0,0,0,0,0,0c0,0,0,0,0,0c0,0,0.1,0,0.1-0.1\n" +
                "\tc0,0,0,0,0,0c0,0,0,0,0,0c0,0,0,0,0.1-0.1c0,0,0,0,0,0c0,0,0,0,0-0.1c0,0,0,0,0.1-0.1c0,0,0,0,0,0c0,0,0,0,0-0.1c0,0,0,0,0-0.1\n" +
                "\tc0,0,0,0,0-0.1c0,0,0-0.1,0.1-0.1c0,0,0,0,0,0c0,0,0,0,0-0.1c0,0,0,0,0,0c0,0,0-0.1,0-0.1c0,0,0,0,0-0.1c0,0,0-0.1,0-0.1\n" +
                "\tc0,0,0-0.1,0.1-0.1c0,0,0,0,0,0c0,0,0-0.1,0-0.1c0,0,0,0,0,0c0,0,0-0.1,0-0.1c0,0,0,0,0-0.1c0,0,0-0.1,0-0.1c0-0.1,0-0.1,0-0.2\n" +
                "\tc0,0,0-0.1,0-0.1c0,0,0,0,0-0.1c0-0.1,0-0.1,0-0.2c0,0,0,0,0,0c0,0,0-0.1,0-0.1c0-0.1,0-0.1,0-0.2c0,0,0-0.1,0-0.1c0,0,0,0,0-0.1\n" +
                "\tc0-0.1,0-0.1,0-0.2c0-0.1,0-0.1,0-0.2c0-0.1,0-0.2,0-0.3c0,0,0-0.1,0-0.1c0,0,0-0.1,0-0.1c0-0.1,0-0.1,0-0.2c0-0.1,0-0.1,0-0.2\n" +
                "\tc0,0,0,0,0,0c0-0.1,0-0.2,0-0.3c0-0.1,0-0.1,0-0.2c0,0,0-0.1,0-0.1c0-0.1,0-0.2,0-0.2c0-0.1,0-0.1,0-0.2c0-0.1,0-0.3,0-0.4\n" +
                "\tc0-0.1,0-0.1,0-0.2c0-0.1,0-0.3,0-0.4c0,0,0-0.1,0-0.2c0-0.2,0-0.3,0-0.5c0,0,0-0.1,0-0.1c0-0.2,0-0.4,0-0.5c0,0,0,0,0,0\n" +
                "\tc-0.1-1.3-0.3-2.9-0.5-4.7c0,0,0,0,0,0h0c0-0.3-0.1-0.7-0.2-1C78.7,7.3,69.8-3.4,41.9,1.1C27,3.5,20.7,8.5,17.1,12.9c0,0,0,0,0,0\n" +
                "\tc-0.1,0.1-0.2,0.3-0.3,0.4c0,0,0,0,0,0c-1.2,1.6-2.1,3.1-3,4.4c0,0,0,0,0,0c-0.1,0.1-0.1,0.2-0.2,0.3c0,0,0,0,0,0.1\n" +
                "\tc-0.1,0.1-0.1,0.1-0.2,0.2c0,0,0,0.1-0.1,0.1c0,0.1-0.1,0.1-0.1,0.2c0,0,0,0.1-0.1,0.1c0,0.1-0.1,0.1-0.1,0.2c0,0-0.1,0.1-0.1,0.1\n" +
                "\tc-0.1,0.1-0.1,0.2-0.2,0.3c0,0-0.1,0.1-0.1,0.1c0,0-0.1,0.1-0.1,0.1c0,0-0.1,0.1-0.1,0.1c0,0-0.1,0.1-0.1,0.1c0,0-0.1,0.1-0.1,0.1\n" +
                "\tc0,0-0.1,0.1-0.1,0.1c0,0-0.1,0.1-0.1,0.1c0,0-0.1,0.1-0.1,0.1c0,0-0.1,0.1-0.1,0.1c0,0-0.1,0.1-0.1,0.1c0,0-0.1,0.1-0.1,0.1\n" +
                "\tc0,0-0.1,0-0.1,0.1c0,0-0.1,0.1-0.1,0.1c0,0-0.1,0-0.1,0.1c-0.1,0-0.1,0.1-0.2,0.1c0,0-0.1,0-0.1,0.1c-0.1,0-0.1,0.1-0.2,0.1\n" +
                "\tc0,0-0.1,0-0.1,0c-0.1,0-0.2,0.1-0.3,0.1c0,0,0,0,0,0c-1.5,0.5-2.9,1.1-4.2,1.9c-0.2,0.1-0.3,0.2-0.5,0.3c-0.4,0.2-0.7,0.5-1.1,0.8\n" +
                "\tc0,0-0.1,0.1-0.1,0.1c0,0-0.1,0.1-0.1,0.1c0,0-0.1,0.1-0.1,0.1c0,0,0,0-0.1,0.1c-0.1,0.1-0.2,0.1-0.2,0.2c-0.1,0-0.1,0.1-0.2,0.1\n" +
                "\tc0,0-0.1,0.1-0.1,0.1c-0.1,0.1-0.1,0.1-0.2,0.2c0,0-0.1,0.1-0.1,0.1C3.1,25,3.1,25.1,3,25.2c-0.1,0.1-0.1,0.1-0.1,0.2\n" +
                "\tc0,0-0.1,0.1-0.1,0.1c-0.1,0.1-0.1,0.1-0.2,0.2c0,0-0.1,0.1-0.1,0.1c-0.1,0.1-0.2,0.2-0.2,0.3c0,0.1-0.1,0.1-0.1,0.2\n" +
                "\tc0,0.1-0.1,0.1-0.1,0.2c0,0.1-0.1,0.1-0.1,0.2c-0.1,0.1-0.1,0.1-0.2,0.2c-0.1,0.1-0.1,0.1-0.2,0.2c0,0.1-0.1,0.1-0.1,0.2\n" +
                "\tc0,0.1-0.1,0.1-0.1,0.2c0,0-0.1,0.1-0.1,0.1C1.1,27.6,1,27.7,1,27.8c0,0,0,0.1-0.1,0.1c0,0.1-0.1,0.2-0.1,0.2c0,0,0,0.1-0.1,0.1\n" +
                "\tc-0.1,0.1-0.1,0.2-0.1,0.3c0,0,0,0.1,0,0.1c0,0.1-0.1,0.2-0.1,0.3c0,0,0,0.1,0,0.1c0,0.1-0.1,0.2-0.1,0.3c0,0,0,0.1,0,0.1\n" +
                "\tc0,0.1-0.1,0.2-0.1,0.3c0,0,0,0.1,0,0.1c0,0.1,0,0.2-0.1,0.3c0,0,0,0,0,0.1c0,0.1,0,0.2,0,0.3c0,0,0,0.1,0,0.1c0,0.1,0,0.2,0,0.3\n" +
                "\tc0,0,0,0,0,0c0,0.1,0,0.2,0,0.3c0,0,0,0.1,0,0.1c0,0.1,0,0.2,0,0.3c0.2,1.9,1.2,3.5,2.6,4.9c0.2,0.2,0.4,0.4,0.6,0.6\n" +
                "\tc0.3,0.3,0.6,0.6,0.9,0.8c0.4,0.4,0.9,0.7,1.4,1.1c1,0.7,2,1.4,3,2.1c0,0,0.1,1.3,0.5,2.5c0.1,2.5,0.4,5,1,7.3\n" +
                "\tc0.1,0.4,0.1,0.9,0.2,1.3c0,0.1,0,0.2,0.1,0.3c-0.8,0.3-1.6,0.8-1.9,1.6c-0.1,0.2-0.2,0.5-0.2,0.7c0,0,0,0,0,0\n" +
                "\tc0,0.2-0.1,0.4-0.1,0.6c0,0,0,0,0,0.1c0,0.1,0,0.2,0,0.3c-0.1,2.1-0.3,4.1,0.7,6.4c0,0,0,0,0,0c0.2,0.4,0.4,0.7,0.6,1.1\n" +
                "\tc1,1.7,2.4,2.9,3.2,3.3c1.3,0.6,2.8,1,4.3,1c1,1.3,2.3,2.8,3.8,4.8c0.5,0.7,1.1,1.4,1.8,2c0,0,0,0,0,0c0.3,0.3,0.5,0.5,0.8,0.8\n" +
                "\tc0,0,0,0,0,0c0.2,0.2,0.4,0.3,0.5,0.5c0.4,0.3,0.7,0.6,1.1,0.9c0.5,0.4,1.1,0.9,1.6,1.3c0.3,0.2,0.5,0.4,0.8,0.6\n" +
                "\tc0.1,0.1,0.2,0.2,0.3,0.2c0.1,0.1,0.2,0.2,0.4,0.3c0.1,0.1,0.2,0.2,0.3,0.2c1.1,0.7,2.1,1.4,3,1.9c2,1.2,3.5,2,4.1,2.3\n" +
                "\tc0.1,0,0.1,0.1,0.1,0.1c0,0,0,0,0,0c0,0,0,0,0,0c0.3,0.1,0.5,0.2,0.8,0.2c-0.4,1.2-0.6,3-0.5,4.4c0,0.2,0,0.4,0,0.5c0,0,0,0,0,0\n" +
                "\tc0,0,0,0,0,0c-0.3,0-0.6,0-0.8,0c-0.1,0-0.1-0.1-0.2-0.1h0v0c0-0.1-0.1-0.1-0.3-0.1c0,0-0.1,0-0.1,0c0,0,0,0-0.1,0\n" +
                "\tc-1.1-0.1-3,0.3-4.2,0.7c0,0,0,0,0,0c-0.1,0-0.1,0-0.2,0.1c0,0,0,0,0,0c0,0-0.1,0-0.1,0c0,0,0,0,0,0c0,0-0.1,0-0.1,0.1c0,0,0,0,0,0\n" +
                "\tc0,0-0.1,0-0.1,0c0,0,0,0,0,0c0,0-0.1,0-0.1,0.1c0,0,0,0,0,0c0,0-0.1,0-0.1,0c0,0,0,0,0,0c0,0,0,0-0.1,0c0,0,0,0,0,0c0,0,0,0,0,0\n" +
                "\tc0,0,0,0,0,0c0,0,0,0,0,0c0,0,0,0,0,0c0,0,0,0,0,0c0,0,0,0,0,0c0,0,0,0,0,0c0,0,0,0,0,0.1c0,0,0.1,0,0.1,0.1c-0.1,0-0.2,0.1-0.3,0.1\n" +
                "\tc-0.7-0.1-1.4-0.1-2.3,0c-0.2,0-0.4,0.1-0.6,0.1c-2.7,0.5-6.6,2.7-11.1,5.9c-0.2-0.1-0.4-0.2-0.5-0.1c-0.7,0.3-0.8,0.9-1.2,1.2\n" +
                "\tc-0.5,0.4-1.4,0.4-1.4,0.8c0,0,0,0,0,0c-0.1,0-0.1,0.1-0.2,0.1c-0.5,0.3-0.5,1.1-0.1,2.2c-5.4,5-10,10.5-10.6,12.6c0,0,0,0,0,0\n" +
                "\tc0,0,0,0.1,0,0.1c-0.2,0.4-0.3,0.9-0.3,1.5c0,0.1,0,0.3,0,0.4c0,0.1,0,0.2,0.1,0.3c0,0,0,0,0,0c0,0.2,0.1,0.4,0.1,0.6c0,0,0,0,0,0.1\n" +
                "\tc0.1,0.2,0.1,0.3,0.2,0.4c1.9,4.1,8.9,13.4,12.1,15.8c0.1,0,0.1,0.1,0.2,0.1c0,0.4,0.1,0.8,0.2,1.2c0,0,0,0.1,0,0.1\n" +
                "\tc0,0.1,0,0.1,0.1,0.2c0,0,0,0.1,0,0.1c0,0,0,0.1,0.1,0.1c0,0.1,0,0.1,0.1,0.2c0,0,0,0.1,0,0.1c0,0.1,0.1,0.2,0.1,0.3c0,0,0,0,0,0\n" +
                "\tc0.3,0.6,0.8,1.4,1.3,2.2c0.1,0.2,0.2,0.3,0.3,0.5c1,1.6,2.2,3.2,2.5,3.7c0,0,0,0.1,0,0.1c0,0,0,0,0,0c0,0,0,0.1,0.1,0.1\n" +
                "\tc0,0,0,0,0,0c0,0,0.1,0.1,0.1,0.1c0,0,0,0,0,0c0,0,0.1,0.1,0.1,0.1c0,0,0,0,0,0c0.1,0.1,0.2,0.2,0.3,0.3c0,0,0,0,0,0\n" +
                "\tc0.1,0.1,0.2,0.2,0.4,0.2c0,3.1,0.3,6.5,0.7,9.7c0.5,5.2,1.2,9.9,1.4,11.4c0,0.2,0,0.3,0,0.5c0,0,0,0,0,0c0,0,0,0,0,0\n" +
                "\tc-0.1,0.1-0.2,0.1-0.2,0.2c-5.6,4.9-4.7,15.7-4.5,21.5c0,0.4,0,0.7,0,1.1c0,0.1,0,0.2,0,0.3c-0.1,0-0.2,0-0.2,0h-0.3\n" +
                "\tc0,0.2,0,0.4,0,0.6c-1.3,3.8-2.3,7.9-1.8,10.2c0,0,0,0,0,0.1c0,0.2,0.1,0.4,0.2,0.5c0,0,0,0.1,0,0.1c0,0,0,0.1,0.1,0.1\n" +
                "\tc0,0.1,0.1,0.1,0.1,0.2c0,0,0,0.1,0.1,0.1c0,0,0,0.1,0.1,0.1c0,0,0,0.1,0.1,0.1c0,0.1,0.1,0.1,0.1,0.2c0,0,0,0,0,0\n" +
                "\tc0,0,0.1,0.1,0.1,0.1c0,0,0,0,0.1,0c0.1,0,0.1,0.1,0.2,0.1c0,0,0,0,0,0c0.1,0,0.1,0.1,0.2,0.1c0,0,0,0,0.1,0c0.1,0,0.2,0.1,0.2,0.1\n" +
                "\tc1.6,0.4,5.8,0.5,9.9,0.5c3.7,0,7.3-0.1,9.2-0.4c0,0,0,0,0,0c0.1,0,0.2,0,0.3,0c0,0,0,0,0,0c0,0,0,0,0,0c0,0,0.1,0,0.1,0\n" +
                "\tc0.1,0,0.2,0,0.2-0.1c0,0,0.1,0,0.1,0c0.1,0,0.2-0.1,0.3-0.1c0,0,0,0,0,0c0.1,0,0.2-0.1,0.3-0.1c0,0,0.1,0,0.1-0.1\n" +
                "\tc0.1,0,0.1-0.1,0.2-0.1c0,0,0.1,0,0.1-0.1c0.1-0.1,0.1-0.1,0.2-0.2c0,0,0.1,0,0.1-0.1c0.1,0,0.1-0.1,0.2-0.1c0,0,0.1-0.1,0.1-0.1\n" +
                "\tc0,0,0.1-0.1,0.1-0.1c0,0,0.1-0.1,0.1-0.1c0,0,0.1-0.1,0.1-0.1c0.1-0.1,0.1-0.1,0.2-0.2c0,0,0-0.1,0.1-0.1c0-0.1,0.1-0.1,0.1-0.2\n" +
                "\tc0,0,0-0.1,0-0.1c1.2-2.1,0.8-5.2-1.9-6.3c-3.5-1.4-6.1-1.2-9.6-3c0-0.1,0.1-0.1,0.1-0.1s0-0.2,0.1-0.5c0-0.1,0.1-0.3,0.1-0.4\n" +
                "\tc-0.1,0-0.1,0-0.2,0c-0.1,0-0.2,0-0.3,0.1c0.2-0.4,0.3-0.8,0.4-1.2c1.3-6.1,3.8-16.2,4.2-20.1c0-0.3,0-0.7,0-1\n" +
                "\tc1.7-3.9,4.3-14,5.1-19.7c0,0,0,0,0,0c0,0,0,0,0,0c0.7,0,1.4,0,2.2,0c1.2,0,2.5-0.1,3.5-0.2c0,0,0,0,0,0.1c0,0,0,0,0,0.1\n" +
                "\tc0,0,0,0,0,0c0,0,0,0.1,0,0.1c0,0.1,0,0.2,0.1,0.3c0,0,0,0.1,0,0.1c0,0.1,0.1,0.2,0.1,0.3c0,0,0,0,0,0.1c0,0.1,0.1,0.2,0.1,0.2\n" +
                "\tc0,0,0,0.1,0,0.1c0.1,0.1,0.1,0.2,0.2,0.3c0.1,0.1,0.2,0.3,0.3,0.4c0,0,0.1,0.1,0.1,0.1c0,0,0.1,0.1,0.1,0.1c0,0,0,0,0,0.1\n" +
                "\tc0,0,0.1,0.1,0.1,0.1c0,0.1,0.1,0.1,0.1,0.2c0,0,0.1,0.1,0.1,0.1c0,0.1,0.1,0.1,0.1,0.2c0,0,0.1,0.1,0.1,0.1c0,0,0,0,0.1,0.1\n" +
                "\tc0.1,0.1,0.1,0.1,0.2,0.2c0,0,0,0.1,0.1,0.1c0,0,0,0.1,0.1,0.1c0,0,0,0,0,0c0.1,0.1,0.1,0.1,0.2,0.2c0.1,0.1,0.1,0.1,0.2,0.2\n" +
                "\tc0,0,0,0,0,0c0,0,0.1,0.1,0.1,0.1c0.1,0.1,0.1,0.2,0.2,0.3c0,0,0.1,0.1,0.1,0.1c0.1,0.1,0.1,0.1,0.2,0.2c0.1,0.1,0.1,0.1,0.2,0.2\n" +
                "\tc0,0,0.1,0.1,0.1,0.1c0,0,0,0,0,0c0.1,0.1,0.1,0.1,0.2,0.2c0,0,0.1,0.1,0.1,0.1c0,0,0.1,0.1,0.1,0.1c0,0.1,0.1,0.1,0.1,0.2\n" +
                "\tc0,0,0.1,0.1,0.1,0.1c0,0,0,0,0,0c0,0,0.1,0.1,0.1,0.1c0.1,0.1,0.2,0.2,0.3,0.3c0,0,0,0,0,0c0,0,0.1,0.1,0.1,0.1c0,0,0,0,0,0\n" +
                "\tc0.1,0.1,0.2,0.2,0.3,0.4c0,0,0,0,0.1,0.1c0.1,0.1,0.1,0.1,0.2,0.2c0.1,0.1,0.1,0.1,0.2,0.2c0,0,0,0,0,0.1c0,0,0.1,0.1,0.1,0.1\n" +
                "\tc0.1,0.1,0.1,0.1,0.2,0.2c0,0,0.1,0.1,0.1,0.1c0,0,0,0,0,0c0.1,0.1,0.1,0.1,0.2,0.2c0.1,0.1,0.1,0.1,0.2,0.2c0,0,0.1,0.1,0.1,0.1\n" +
                "\tc0,0,0,0,0,0c0.1,0.2,0.3,0.3,0.4,0.5c0,0,0,0,0,0c0,0,0,0,0,0c0,0,0,0,0,0c0.2,0.2,0.3,0.3,0.5,0.5c0.1,0.1,0.1,0.1,0.2,0.2\n" +
                "\tc0.1,0.1,0.2,0.2,0.4,0.4c0.1,0.1,0.1,0.1,0.2,0.2c0.1,0.1,0.2,0.2,0.4,0.4c0.1,0.1,0.1,0.1,0.2,0.2c0.1,0.1,0.3,0.3,0.4,0.4\n" +
                "\tc0,0,0.1,0.1,0.1,0.1c0.2,0.2,0.4,0.3,0.5,0.5c0.1,0.1,0.1,0.1,0.2,0.2c0.1,0.1,0.3,0.2,0.4,0.4c0.1,0.1,0.1,0.1,0.2,0.2\n" +
                "\tc0.1,0.1,0.2,0.2,0.4,0.3c0.1,0.1,0.1,0.1,0.2,0.2c0.1,0.1,0.3,0.2,0.4,0.4c0,0,0.1,0.1,0.1,0.1c0.2,0.2,0.3,0.3,0.5,0.5\n" +
                "\tc0,0,0.1,0.1,0.1,0.1c0.1,0.1,0.3,0.2,0.4,0.4c0.1,0.1,0.1,0.1,0.2,0.2c0.1,0.1,0.2,0.2,0.3,0.3c0.1,0.1,0.1,0.1,0.2,0.2\n" +
                "\tc0.1,0.1,0.2,0.2,0.3,0.3c0.1,0,0.1,0.1,0.2,0.1c0.2,0.1,0.3,0.3,0.5,0.4c0,0,0.1,0,0.1,0.1c-4.3,5.5-3.2,15-2.8,20.2\n" +
                "\tc-0.1,0-0.2,0-0.4-0.1h-0.3c0,0.2,0,0.4,0,0.6c-1.3,3.8-2.3,7.9-1.8,10.2c0,0,0,0,0,0.1c0,0.2,0.1,0.4,0.2,0.5c0,0,0,0.1,0,0.1\n" +
                "\tc0,0,0,0.1,0.1,0.1c0,0.1,0.1,0.1,0.1,0.2c0,0,0,0.1,0.1,0.1c0,0,0,0.1,0.1,0.1c0,0,0,0.1,0.1,0.1c0,0.1,0.1,0.1,0.1,0.2\n" +
                "\tc0,0,0,0,0,0c0,0,0.1,0.1,0.1,0.1c0,0,0,0,0.1,0c0.1,0,0.1,0.1,0.2,0.1c0,0,0,0,0,0c0.1,0,0.1,0.1,0.2,0.1c0,0,0,0,0.1,0\n" +
                "\tc0.1,0,0.2,0.1,0.2,0.1c1.6,0.4,5.8,0.5,9.9,0.5c3.7,0,7.3-0.1,9.2-0.4c0,0,0,0,0,0c0.1,0,0.2,0,0.3,0c0,0,0,0,0,0c0,0,0,0,0,0\n" +
                "\tc0,0,0.1,0,0.1,0c0.1,0,0.2,0,0.2-0.1c0,0,0.1,0,0.1,0c0.1,0,0.2-0.1,0.3-0.1c0,0,0,0,0,0c0.1,0,0.2-0.1,0.3-0.1c0,0,0.1,0,0.1-0.1\n" +
                "\tc0.1,0,0.1-0.1,0.2-0.1c0,0,0.1,0,0.1-0.1c0.1-0.1,0.1-0.1,0.2-0.2c0,0,0.1,0,0.1-0.1c0.1,0,0.1-0.1,0.2-0.1c0,0,0.1-0.1,0.1-0.1\n" +
                "\tc0,0,0.1-0.1,0.1-0.1c0,0,0.1-0.1,0.1-0.1c0,0,0.1-0.1,0.1-0.1c0.1-0.1,0.1-0.1,0.2-0.2c0,0,0-0.1,0.1-0.1c0-0.1,0.1-0.1,0.1-0.2\n" +
                "\tc0,0,0-0.1,0-0.1c1.2-2.1,0.8-5.2-1.9-6.3c-3.5-1.4-6.1-1.2-9.6-3c0.1-0.2,0.2-0.5,0.2-0.8c1.2-5.9,3.3-16.2,3.6-20.4\n" +
                "\tc0.1-1.2-0.1-2.4-0.6-3.3c-0.1-0.4-0.2-0.9-0.4-1.3c-1.5-2.9-4.3-10-7.5-16.3c0,0,0,0,0,0c-0.1-0.3-0.3-0.5-0.4-0.8c0,0,0,0,0,0\n" +
                "\tc-0.1-0.3-0.3-0.5-0.4-0.8c0,0,0,0,0,0c-0.1-0.3-0.3-0.5-0.4-0.8c0,0,0,0,0-0.1c-0.1-0.3-0.3-0.5-0.4-0.8c0,0,0,0,0-0.1\n" +
                "\tc-0.1-0.2-0.3-0.5-0.4-0.7c0,0,0,0,0-0.1c-0.1-0.2-0.3-0.5-0.4-0.7c0,0,0,0,0,0c-0.1-0.2-0.3-0.5-0.4-0.7c0,0,0,0,0,0\n" +
                "\tc-0.1-0.2-0.3-0.5-0.4-0.7c0,0,0,0,0,0c-0.2-0.2-0.3-0.5-0.5-0.7c0,0,0,0,0,0c-0.3-0.5-0.7-0.8-1.1-1.1c0-0.1-0.1-0.1-0.1-0.2\n" +
                "\tc0,0,0.1-0.1,0.1-0.1c0,0,0-0.1,0.1-0.1c0,0,0,0,0,0c0,0,0-0.1,0.1-0.1c0,0,0,0,0,0c0-0.1,0.1-0.2,0.1-0.4c0,0,0,0,0,0l0,0\n" +
                "\tc0.2-0.8,0.3-1.8-0.2-1.9c0,0,0,0-0.1,0c0-0.1,0.1-0.2,0.1-0.3c0.1-0.2,0.1-0.5,0.2-0.8c0.1-0.3,0.1-0.5,0.2-0.8\n" +
                "\tc0-0.1,0.1-0.3,0.1-0.4c0.1-0.3,0.1-0.6,0.2-0.9c0-0.1,0.1-0.2,0.1-0.3c0.5-0.2,0.8-0.4,0.8-0.6c0-0.1,0-0.1-0.1-0.2\n" +
                "\tc0.1-0.4,0.3-1.2,0.4-2.3c1.1,1.5,2.1,2.6,2.8,3c3.3,4.5,11.8,12.9,14.4,13.6c0.1,0.5,0.2,0.9,0.4,1.4c0,0,0,0,0,0.1\n" +
                "\tc0,0.1,0,0.1,0.1,0.2c0,0,0,0.1,0.1,0.1c0,0,0,0.1,0.1,0.1c0,0.1,0.1,0.1,0.1,0.2c0,0,0,0.1,0.1,0.1c0.1,0.1,0.1,0.2,0.2,0.3\n" +
                "\tc0.1,0.2,0.3,0.5,0.5,0.7c0.9,1.1,2.2,2.6,3.2,3.8c0.1,0.1,0.1,0.1,0.2,0.2c0.1,0.1,0.2,0.2,0.3,0.4c0.2,0.3,0.4,0.5,0.6,0.7\n" +
                "\tc0.1,0.1,0.1,0.2,0.2,0.2c0,0,0,0,0,0c0,0,0.1,0.1,0.1,0.1c0,0,0,0,0,0c0,0,0.1,0.1,0.1,0.1c0,0,0,0,0,0c0,0,0.1,0.1,0.1,0.1\n" +
                "\tc0,0,0,0,0,0c0.1,0.1,0.2,0.2,0.3,0.2c0,0,0,0,0,0c0.4,0.2,0.7,0.3,0.9,0c0.3-0.5-1.4-2.7-1-3c0.4-0.3,2.8,2,3.3,1.6\n" +
                "\tc0.5-0.4-0.2-1.6-0.7-2.2c-0.5-0.6-1.5-1.5-1-2.1c0.4-0.5,1.8,0.7,2.5,1.1c0.6,0.3,1,0.7,1.4,0.1c0.4-0.5-1.6-2.1-2.1-2.4\n" +
                "\tc-0.3-0.2-0.6-0.6-0.4-0.9c0.1-0.1,0.2-0.2,0.4-0.2c0.6-0.1,1.2,0.2,1.8,0.4s1.4,0.1,1.7-0.5C85.9,139.7,84.9,139.5,84.2,139.1z\n" +
                "\t M19.1,138.9c0,0,0.1,0,0.1,0c0,0.3,0,0.7,0,1C19,139.4,18.9,139,19.1,138.9z M18.9,126.3c-0.1,0-0.2,0-0.3,0\n" +
                "\tc-1.4-3-6.6-8-10.2-11.1c2.5-1.3,6.2-3.4,10-5.8c1.1,1,2,1.6,2.4,1.4c0,0,0.1,0,0.1-0.1c0.1,0,0.1,0,0.1-0.1c0,0.2,0,0.3,0,0.5\n" +
                "\tc0,0.5-0.1,0.9-0.1,1.4c-0.8,1.1-1,3-0.3,4.4c0,0,0.1,0,0.1,0c0,0.3,0,0.6,0,0.8l0,0l0,0c0,0.4,0,0.8,0,1.1c-0.5,1.1-0.6,2.7,0,3.9\n" +
                "\tc0,0,0,0,0,0c0,0,0,0,0,0l0,0c0,0,0,0,0,0c0,0.9,0.1,1.5,0.2,1.7c0,0,0,0.1,0,0.1c0,0.2,0.3,0.4,0.8,0.7c0,0.6,0.1,1.2,0.1,1.8\n" +
                "\tC20.3,126.7,19.2,126.4,18.9,126.3z M21.9,130c-0.2,0-0.3,0.2-0.3,0.4c0,0,0,0,0,0c0,0.1,0,0.2,0,0.3c0,0,0,0,0,0c0,0,0,0.1,0,0.1\n" +
                "\tc0,0,0,0,0,0c0,0,0,0,0,0c0,0,0,0,0,0c0,0.1,0,0.1,0,0.2c0,0,0,0,0,0c0,0,0,0,0,0c0,0,0,0.1,0,0.1c0,0,0,0,0,0c0,0,0,0,0,0\n" +
                "\tc0,0,0,0.1,0,0.1c-0.4-0.2-0.7-0.5-0.8-0.8c-0.3-0.7,0.3-1,1-1.1C21.8,129.7,21.8,129.9,21.9,130z");
        setSpriteSheets();
        this.offsetSVG=37;
        this.spriteBounds.setFill(Paint.valueOf("black"));
        this.currentSprite=new ImageView(this.idleSpriteSheet[0]);
        currentSprite.setCache(true);
        this.currentSprite.setTranslateX(dX);
        this.currentSprite.setTranslateY(dY);
        this.setCurrentSprite(this.idleSpriteSheet[0]);
    }

    private void setSpriteSheets() {
        idleSpriteSheet = new Image[16];
        idleSpriteSheet[0]= new Image("/resources/PoliceMan1/Idle1.png");
        idleSpriteSheet[1]= new Image("/resources/PoliceMan1/Idle2.png");
        idleSpriteSheet[2]= new Image("/resources/PoliceMan1/Idle3.png");
        idleSpriteSheet[3]= new Image("/resources/PoliceMan1/Idle4.png");
        idleSpriteSheet[4]= new Image("/resources/PoliceMan1/Idle5.png");
        idleSpriteSheet[5]= new Image("/resources/PoliceMan1/Idle6.png");
        idleSpriteSheet[6]= new Image("/resources/PoliceMan1/Idle7.png");
        idleSpriteSheet[7]= new Image("/resources/PoliceMan1/Idle8.png");
        idleSpriteSheet[8]= idleSpriteSheet[7];
        idleSpriteSheet[9]=idleSpriteSheet[6];
        idleSpriteSheet[10]=idleSpriteSheet[5];
        idleSpriteSheet[11]=idleSpriteSheet[4];
        idleSpriteSheet[12]=idleSpriteSheet[3];
        idleSpriteSheet[13]=idleSpriteSheet[2];
        idleSpriteSheet[14]=idleSpriteSheet[1];
        idleSpriteSheet[15]=idleSpriteSheet[0];


        hurtSpriteSheet = new Image[6];
        hurtSpriteSheet[0] = new Image("/resources/PoliceMan1/Hurt1.png");
        hurtSpriteSheet[1] = new Image("/resources/PoliceMan1/Hurt2.png");
        hurtSpriteSheet[2] = new Image("/resources/PoliceMan1/Hurt3.png");
        hurtSpriteSheet[3] = new Image("/resources/PoliceMan1/Hurt4.png");
        hurtSpriteSheet[4] = new Image("/resources/PoliceMan1/Hurt5.png");
        hurtSpriteSheet[5] = new Image("/resources/PoliceMan1/Hurt6.png");

        dieSpriteSheet = new Image[3];
        dieSpriteSheet[0] = new Image("/resources/PoliceMan1/Die1.png");
        dieSpriteSheet[1] = new Image("/resources/PoliceMan1/Die2.png");
        dieSpriteSheet[2] = new Image("/resources/PoliceMan1/Die3.png");


        runSpriteSheet = new Image[6];
        runSpriteSheet[0] = new Image("/resources/PoliceMan1/Run1.png");
        runSpriteSheet[1] = new Image("/resources/PoliceMan1/Run2.png");
        runSpriteSheet[2] = new Image("/resources/PoliceMan1/Run3.png");
        runSpriteSheet[3] = new Image("/resources/PoliceMan1/Run4.png");
        runSpriteSheet[4] = new Image("/resources/PoliceMan1/Run5.png");
        runSpriteSheet[5] = new Image("/resources/PoliceMan1/Run6.png");


        shootSpriteSheet = new Image[12];
    //    System.out.println(this.getClass().getResource("/stranger/recourses/PoliceMan1/Shoot1.png"));
        System.out.println(this.getClass().getResource("PoliceMan1/Shoot1.png"));
        shootSpriteSheet[0] = new Image("/resources/PoliceMan1/Shoot1.png");
        shootSpriteSheet[1] = new Image("/resources/PoliceMan1/Shoot2.png");
        shootSpriteSheet[2] = new Image("/resources/PoliceMan1/Shoot3.png");
        shootSpriteSheet[3] = new Image("/resources/PoliceMan1/Shoot4.png");
        shootSpriteSheet[4] = new Image("/resources/PoliceMan1/Shoot5.png");
        shootSpriteSheet[5] = new Image("/resources/PoliceMan1/Shoot6.png");
        shootSpriteSheet[6] = shootSpriteSheet[5];
        shootSpriteSheet[7] = shootSpriteSheet[3];
        shootSpriteSheet[8] = shootSpriteSheet[2];
        shootSpriteSheet[9] = shootSpriteSheet[1];
        shootSpriteSheet[10] = shootSpriteSheet[0];
        shootSpriteSheet[11] = shootSpriteSheet[0];

        hitStickSpriteSheet = new Image[10];
//        hitStickSpriteSheet[0] = new Image("file:src/PoliceMan1/HitStick1.png");
//        hitStickSpriteSheet[1] = new Image("file:src/PoliceMan1/HitStick2.png");
//        hitStickSpriteSheet[2] = new Image("file:src/PoliceMan1/HitStick3.png");
//        hitStickSpriteSheet[3] = new Image("file:src/PoliceMan1/HitStick4.png");
//        hitStickSpriteSheet[4] = new Image("file:src/PoliceMan1/HitStick5.png");
//        hitStickSpriteSheet[5] = new Image("file:src/PoliceMan1/HitStick6.png");
//        hitStickSpriteSheet[6] = new Image("file:src/PoliceMan1/HitStick7.png");
//        hitStickSpriteSheet[7] = new Image("file:src/PoliceMan1/HitStick8.png");
//        hitStickSpriteSheet[8] = new Image("file:src/PoliceMan1/HitStick9.png");
//        hitStickSpriteSheet[9] = new Image("file:src/PoliceMan1/HitStick10.png");
//        hitStickSpriteSheet[0] =
        hitStickSpriteSheet[1] = new Image("/resources/PoliceMan1/HitStick2.png");
        hitStickSpriteSheet[2] = new Image("/resources/PoliceMan1/HitStick3.png");
        hitStickSpriteSheet[3] = new Image("/resources/PoliceMan1/HitStick4.png");
        hitStickSpriteSheet[4] = new Image("/resources/PoliceMan1/HitStick5.png");
        hitStickSpriteSheet[5] = new Image("/resources/PoliceMan1/HitStick6.png");
        hitStickSpriteSheet[6] = new Image("/resources/PoliceMan1/HitStick7.png");
        hitStickSpriteSheet[7] = new Image("/resources/PoliceMan1/HitStick8.png");
        hitStickSpriteSheet[8] = new Image("/resources/PoliceMan1/HitStick9.png");
        hitStickSpriteSheet[9] = new Image("/resources/PoliceMan1/HitStick10.png");
    }

    @Override
    public void update() {
        this.currentSprite.setTranslateX(dX);
        this.spriteBounds.setTranslateX(dX+offsetSVG);
        this.currentSprite.setTranslateY(dY);
        this.spriteBounds.setTranslateY(dY);
        if(dying)
            die();
        else {
//            for (Actor actor:
//                    gameManager.getBullets()) {
//                if(actor!=this)
//                    if(!isShot)
//                        setShot(collide(actor));
//            }
//            for (Laser laser:
//                 gameManager.getLasers()) {
//                setShot(collide(laser));
//            }


//            idle();
            if(isShot||hurtAnimation)
            {
                hurt();
                spriteBounds.setFill(Paint.valueOf("red"));
            }
//            else if(running){
//                run();
//            }
//            else
//                spriteBounds.setFill(Paint.valueOf("black"));
            shoot();
//            hitStick();

        }

    }

    private void run() {
        if(runningFPSCount==RUN_FPS) runningFPSCount=0;
        this.setCurrentSprite(runSpriteSheet[runningFPSCount/(RUN_FPS/runSpriteSheet.length)]);
        runningFPSCount++;
        dX+=vX;
    }
    private void hurt() {
        hurtAnimation = true;
        if(hurtFPSCount==HURT_FPS) {
            hurtFPSCount=0;
            health--;
            hurtAnimation = false;
            setShot(false);
        }
        if(health==0)
            die();
        this.setCurrentSprite(hurtSpriteSheet[hurtFPSCount/(HURT_FPS/hurtSpriteSheet.length)]);
        hurtFPSCount++;
    }
    private void die() {
        dying=true;
        if(dieFPSCount==DIE_FPS) {
            dieFPSCount=0;
            gameManager.removeActor(this);
        }
        this.setCurrentSprite(dieSpriteSheet[dieFPSCount/ (DIE_FPS/dieSpriteSheet.length)]);
        dieFPSCount++;
    }
    private void idle() {
        if(idleFPSCount==IDLE_FPS) idleFPSCount=0;
        this.setCurrentSprite(idleSpriteSheet[idleFPSCount/(IDLE_FPS/idleSpriteSheet.length)]);
        idleFPSCount++;
    }
    private void shoot(){
        if(shootingFPSCount==0){
            shootingAnimation=true;
            bullet = new PolicemanBullet(dX+bulletOffsetX,dY+bulletOffsetY,this);
        }
        if(shootingFPSCount==SHOOTING_FPS) {
            shootingFPSCount=-1;
            shooting = false;
            shootingAnimation=false;
            this.setLookingLeft(!lookingLeft);
        }
        this.setCurrentSprite(shootSpriteSheet[shootingFPSCount/7]);
        shootingFPSCount++;


        if(shootingFPSCount==28) {
            gameManager.shoot(bullet);}
    }
    private void hitStick(){
        if(hitStickCount==0){
            hitStickAnimation=true;
            policemanStick = new PolicemanStick(currentSprite.getTranslateX(),currentSprite.getTranslateY(),this);
        }
        if(hitStickCount==HIT_STICK_FPS){
            hitStickAnimation=false;
            hitStickCount=0;
            gameManager.removeActor(policemanStick);
            return;
        }
        this.setCurrentSprite(hitStickSpriteSheet[hitStickCount/6]);
        if(hitStickCount==50)
            gameManager.shoot(policemanStick);
        hitStickCount++;

    }

    public void setShot(boolean shot) {
        this.isShot = shot;
    }

    @Override
    public boolean isDead() {
        return dying;
    }

    @Override
    public void setDead() {
        this.health=1;
        this.hurt();
    }

    @Override
    public int getHealth() {
        return health;
    }

    public boolean isLookingLeft() {
        return lookingLeft;
    }
    public void setLookingLeft(boolean lookingLeft){
        this.lookingLeft=lookingLeft;
        if(lookingLeft){
            this.spriteBounds.setScaleX(-1);
            this.currentSprite.setScaleX(-1);
            this.bulletOffsetX=-40;
        }
        if(!lookingLeft){
            this.spriteBounds.setScaleX(1);
            this.currentSprite.setScaleX(1);
            this.bulletOffsetX=50;
        }
    }
}
