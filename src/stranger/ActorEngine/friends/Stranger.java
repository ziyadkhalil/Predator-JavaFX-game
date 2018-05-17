package stranger.ActorEngine.friends;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import stranger.ActorEngine.Actor;
import stranger.ActorEngine.Friend;
import stranger.game_engine.GameManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Stranger extends Actor implements Friend {
    private static final int SUPER_FPS = 10*12;
    private static final int INITIAL_HEALTH=5;
    private int runningFPSCount=0;
    private int health =INITIAL_HEALTH;
    private boolean isUp;
    private boolean isLeft;
    private boolean isDown;
    private boolean isRight;
    private boolean shooting;
    private boolean jumping;
    private int jumpFPSCount=0;
    private static final int JUMP_FPS = 32;
    private static final int HURT_FPS = 32;
    private static final int DYING_FPS = 120;
    private int dieFPSCount=0;
    private int hurtFPSCount=0;
    private int idleFPSCount=0;
    private Image[] idleSpriteSheet;
    private String[] idleSVGPaths;
    private double[] idleOffsets;
    private Image[] runSpriteSheet;
    private Image[] jumpSpriteSheet;
    private Image[] shootingSpriteSheet;
    private Image[] superHitSpriteSheet;
    private Image[] hurtSpriteSheet;
    private Image[] dyingSpriteSheet;
    private Image[] healthBar;
    private int superFPSCount=0;
    private int shootingFPSCount=0;
    private boolean isShot;
    private boolean lookingLeft=false;
    double bulletOffsetX=130;
    double bulletOffsetY=50;
    private boolean superShooting;
    private Laser laser;
    private boolean superShootingAnimation;
    private boolean shootingAnimation;
    private boolean hurtAnimation;
    private  int score=0;
    private  int laserCounter=0;
    private boolean canShootLaser=true;
    private boolean dyingAnimation;

    public Stranger(double iX, double iY, GameManager gameManager) {
        super(iX,iY);
        try {
            setSpriteSheets();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.gameManager=gameManager;
        currentSprite = new ImageView(idleSpriteSheet[0]);
        setOffsetSVG(27.266);
        currentSprite.setTranslateX(dX);
        currentSprite.setTranslateY(dY);
        setCurrentSVGPath("M111.8,48.1c-2.71-10.43-5.63-21.68-11.64-30.77a26.68,26.68,0,0,0-3.94-4.78c-3.53-4.81-8.08-9-13.88-10.81A1.13,1.13,0,0,0,81.5,2l-2,2.17L79,4.65A37.15,37.15,0,0,0,68.42,1.84,30.77,30.77,0,0,0,55.74,3l-.95-.75C54.19,1.76,52.66,0,51.86,0a14,14,0,0,0-5.19,1.13,15.71,15.71,0,0,0-7.19,5.78l-.1,0a33.07,33.07,0,0,0-7.55,3c-4.94,2.81-8.69,7.35-11.95,11.91a73.09,73.09,0,0,0-9,17.59c-.24.66-.47,1.33-.69,2a91.25,91.25,0,0,0-4,9.51A116.31,116.31,0,0,0,.63,74.28c-2.4,16.94,2.81,33.36,2.88,50.24,0,.45,1,.25,1-.11,0-10,0-20.23,2.15-30.07q.23,1.2.5,2.39a99.46,99.46,0,0,0,.12,10.74c.22,3.06.61,6.1,1.11,9.12-2.37,7.18-2.43,15.67-1.6,22.61.23,1.69.38,7.12,3.32,6.11.95-.32,1.57-1.4,2.07-2.18.89-1.38,1.62-2.86,2.37-4.32,0,.11,0,.21,0,.32.07.85.36,3.82,1.54,4.1.79.19,1.07-.53,1.28-1.12a50.6,50.6,0,0,1,2.25-5.3,47.63,47.63,0,0,0,1.95,8.45A45.32,45.32,0,0,0,26.07,155a62.51,62.51,0,0,0,2.85,19.33,27,27,0,0,0-1.08,14.93,8.45,8.45,0,0,0,.82,2.12,24.68,24.68,0,0,0-2.56,4.45c-1.91,5.1,27.09,4.79,30.82,2.84,2.58-1.35,0-4.66-1.14-6.09a21.57,21.57,0,0,0-2.12-2.2,20.27,20.27,0,0,0-.21-5.48,65.41,65.41,0,0,0-1.78-8.75,20.51,20.51,0,0,0,2.74-5.79,23.66,23.66,0,0,0,10.65-2.05c.37-.17.74-.36,1.11-.54-1.18,3-1.64,6.1-.68,8.87a18.22,18.22,0,0,0-2.62,14.47,12.64,12.64,0,0,0,.65,2l0,0a.32.32,0,0,0,0,0,4.13,4.13,0,0,0,.65.91c-.31.58-.61,1.16-.88,1.77-1.91,5.1,27.09,4.79,30.82,2.84,2.58-1.35,0-4.66-1.14-6.09a23.54,23.54,0,0,0-7.81-6c.18-1.55.27-3.09.39-4.45.35-4.06.25-8.29,1.14-12.28a11.62,11.62,0,0,0,.42-2.85,75.52,75.52,0,0,0,1.16-10.76,65,65,0,0,0-.28-9.85,11.78,11.78,0,0,0-.45-2.66,39.64,39.64,0,0,0,.71-6.63c.48.64,1,1.28,1.49,1.88.66.76,2.09,2.34,3.16,1.27a2.64,2.64,0,0,0,.37-2.21c.51.75,1,1.51,1.44,2.32a1.75,1.75,0,0,0,1.63,1c2.25-.19,3.25-3.19,3.87-5a30.73,30.73,0,0,0,1.19-14c0-.17-.05-.34-.08-.52a32.2,32.2,0,0,0,1.5-4.63c.53-2.66,1.11-5.28,1.74-7.92,1-4.34,1.91-8.73,2.75-13.12.21,8.77,1,17.57-.07,26.29-.06.46.88.23,1-.1,3.74-12.11,6.85-24.19,7.13-36.92C115.69,72.95,115,60.31,111.8,48.1Zm-18,58.66c-.29-.39-.61-.79-.94-1.16.31-2.27.55-4.54.76-6.81A71,71,0,0,1,93.85,106.76ZM97.4,112l1-2.5c2.35-6.1,4.61-12.39,6.42-18.79-.1,1-.19,2.08-.31,3.11-.89,8.13-3,16.09-4.11,24.18A32.59,32.59,0,0,0,97.4,112Z");
        laser = new Laser(dX,dY,this);
        currentSprite.setSmooth(true);
        System.out.println(healthBar);
        this.gameManager.setHealthBar(this,healthBar);
    }



    @Override
    public void update() {
//        currentSprite.setFitWidth(currentSprite.getImage().getWidth());
//        currentSprite.setFitHeight(currentSprite.getImage().getHeight());
        currentSprite.setTranslateX(dX);
        currentSprite.setTranslateY(dY);
        spriteBounds.setTranslateX(dX+offsetSVG);
        spriteBounds.setTranslateY(dY);
        if(dyingAnimation)
            die();
        else {
            if ((hurtAnimation || isShot) && !superShootingAnimation) {
                hurt();
            } else if (((superShooting && !jumping && !shootingAnimation) || superShootingAnimation) && canShootLaser)
                superLaser();
            else if ((shooting && !jumping && !isUp() && !superShootingAnimation) || shootingAnimation)
                shoot();

            else if ((isLeft || isRight) && !superShootingAnimation)
                run();
            else if (!shootingAnimation && !superShootingAnimation && !jumping)
                idle();

            if ((this.isUp() || jumping) && !superShootingAnimation && !shootingAnimation)
                jump();
        }



    }

    private void superLaser() {
           if(superFPSCount==0) {
               laserCounter=0;
               superShootingAnimation = true;
               AudioClip audioClip = new AudioClip(getClass().getResource("/resources/LaserBeam.wav").toString());
               audioClip.setVolume(audioClip.getVolume()/2);
           //    System.out.println(audioClip.getVolume());
               audioClip.play();
               laser.setCoordinates(dX+bulletOffsetX-20,dY+bulletOffsetY);
           }
        System.out.println("X: " + (dX + bulletOffsetX - 20) + "\nY:" + (dY + bulletOffsetY));
         if(superFPSCount==SUPER_FPS) {
             canShootLaser=false;
             superFPSCount=-1;
            superShooting = false;
            superShootingAnimation=false;
        }
        if(superFPSCount==19)
            gameManager.shoot(laser);
        if(superFPSCount<10)
            this.currentSprite.setImage(superHitSpriteSheet[0]);
        else if(10<superFPSCount&&superFPSCount<20)
            this.currentSprite.setImage(superHitSpriteSheet[1]);
        else if(20<superFPSCount&&superFPSCount<30)
            this.currentSprite.setImage(superHitSpriteSheet[2]);
        else if(30<superFPSCount&&superFPSCount<120)
            this.currentSprite.setImage(superHitSpriteSheet[3]);
        superFPSCount++;
    }


    private void shoot() {
        Bullet bullet = new Bullet(dX+bulletOffsetX,dY+bulletOffsetY,this);
        if(shootingFPSCount==0){
            shootingAnimation=true;
        }
            if(shootingFPSCount==40) {
                shootingFPSCount=-1;
                shooting = false;
                shootingAnimation=false;
            }
        shootingFPSCount++;
        this.setCurrentSprite(shootingSpriteSheet[shootingFPSCount/4]);

       if(shootingFPSCount==20) {
           gameManager.shoot(bullet);}
    }

    private void setSpriteSheets() throws FileNotFoundException {
        runSpriteSheet = new Image[6];
        runSpriteSheet[0]= new Image("/resources/run1.png");
        runSpriteSheet[1]= new Image("/resources/run2.png");
        runSpriteSheet[2]= new Image("/resources/run3.png");
        runSpriteSheet[3]= new Image("/resources/run4.png");
        runSpriteSheet[4]= new Image("/resources/run5.png");
        runSpriteSheet[5]= new Image("/resources/run6.png");

        jumpSpriteSheet = new Image[4];
        jumpSpriteSheet[0]= new Image("/resources/jump1.png");
        jumpSpriteSheet[1]= new Image("/resources/jump2.png");
        jumpSpriteSheet[2]= new Image("/resources/jump3.png");
        jumpSpriteSheet[3]= new Image("/resources/jump4.png");



        idleSpriteSheet = new Image[5];
//        idleSVGPaths = new String[5];
        idleOffsets = new double[5];
        idleSpriteSheet[0]= new Image("/resources/idle1.png");
        idleSpriteSheet[1]= new Image("/resources/idle2.png");
        idleSpriteSheet[2]= new Image("/resources/idle3.png");
        idleSpriteSheet[3]=idleSpriteSheet[1];
        idleSpriteSheet[4]=idleSpriteSheet[0];
//        idleSVGPaths[0] = new Scanner(new File("src/resources/idle1.txt")).useDelimiter("\\Z").next();
//        idleSVGPaths[1] = new Scanner(new File("src/resources/idle2.txt")).useDelimiter("\\Z").next();
//        idleSVGPaths[2] = new Scanner(new File("src/resources/idle3.txt")).useDelimiter("\\Z").next();
//        idleSVGPaths[3]=idleSVGPaths[1];
//        idleSVGPaths[4]=idleSVGPaths[0];
        idleOffsets[0] = 27.266;
//        idleOffsets[1] = 26.266;
//        idleOffsets[2] = 29.266;
//        idleOffsets[3] = idleOffsets[1];
//        idleOffsets[4] = idleOffsets[0];


        shootingSpriteSheet = new Image[11];
        shootingSpriteSheet[0]=new Image("/resources/shoot1.png");
        shootingSpriteSheet[1]=new Image("/resources/shoot2.png");
        shootingSpriteSheet[2]=new Image("/resources/shoot3.png");
        shootingSpriteSheet[3]=new Image("/resources/shoot4.png");
        shootingSpriteSheet[4]=new Image("/resources/shoot5.png");
        shootingSpriteSheet[5]=new Image("/resources/shoot6.png");
        shootingSpriteSheet[6]=new Image("/resources/shoot7.png");
        shootingSpriteSheet[7]=new Image("/resources/shoot8.png");
        shootingSpriteSheet[8]=new Image("/resources/shoot9.png");
        shootingSpriteSheet[9]=shootingSpriteSheet[8];
        shootingSpriteSheet[10]=shootingSpriteSheet[8];


        superHitSpriteSheet = new Image[4];
        superHitSpriteSheet[0] = new Image("/resources/super1.png");
        superHitSpriteSheet[1] = new Image("/resources/super2.png");
        superHitSpriteSheet[2] = new Image("/resources/super3.png");
        superHitSpriteSheet[3] = new Image("/resources/super4.png");

        hurtSpriteSheet = new Image[4];
        hurtSpriteSheet[0] = new Image("/resources/Hurt1.png");
        hurtSpriteSheet[1] = new Image("/resources/Hurt2.png");
        hurtSpriteSheet[2] = new Image("/resources/Hurt3.png");
        hurtSpriteSheet[3] = new Image("/resources/Hurt4.png");

        dyingSpriteSheet = new Image[5];
        dyingSpriteSheet[0] = new Image("/resources/Dying1.png");
        dyingSpriteSheet[1] = new Image("/resources/Dying2.png");
        dyingSpriteSheet[2] = new Image("/resources/Dying3.png");
        dyingSpriteSheet[3] = new Image("/resources/Dying4.png");
        dyingSpriteSheet[4] = new Image("/resources/Dying5.png");

        healthBar = new Image[INITIAL_HEALTH];
        healthBar[0] = new Image("/resources/HealthBar.png");
        for(int i=0;i<INITIAL_HEALTH-1;i++) {
            healthBar[i+1]= healthBar[0];
        }
    }
    private void idle() {
        if(idleFPSCount==40) idleFPSCount=0;
            this.setCurrentSprite(idleSpriteSheet[idleFPSCount/8]);
//            this.setCurrentSVGPath(idleSVGPaths[idleFPSCount/8]);
//            this.setOffsetSVG(idleOffsets[idleFPSCount/8]);
        idleFPSCount++;
    }

    private void jump() {
        if(jumpFPSCount==1)
            setJumping(true);
        if(jumpFPSCount== JUMP_FPS) {
            jumpFPSCount=0;
            setJumping(false);
            //vX=5;
        }
        if(jumpFPSCount< JUMP_FPS /2 - 5)
            this.currentSprite.setTranslateY(dY-=15);

        if(jumpFPSCount>= JUMP_FPS /2 + 5)
            this.currentSprite.setTranslateY(dY+=15);

        if (!hurtAnimation) {
            this.setCurrentSprite(jumpSpriteSheet[jumpFPSCount / (JUMP_FPS / 4)]);
        }
        jumpFPSCount++;

    }

    private void run() {
        if(isLeft) {
            this.currentSprite.setScaleX(-1);
            this.spriteBounds.setScaleX(-1);
            this.lookingLeft=true;
            bulletOffsetX = -17.884140014648438;
            dX-=vX;
        } else {
            this.currentSprite.setScaleX(1);
            this.spriteBounds.setScaleX(1);
            this.lookingLeft=false;
            bulletOffsetX=130;
            dX+=vX;
        }

        if (!jumping) { //Stops Running Animation in case of Jumping
            if(runningFPSCount==24) runningFPSCount=0;
            this.setCurrentSprite(runSpriteSheet[runningFPSCount/4]);
            runningFPSCount++;
        }
   // hurt();

    }
    
    public void hurt(){
        hurtAnimation = true;
        shootingAnimation=false;
        shootingFPSCount=0;
        if(hurtFPSCount==HURT_FPS) {
            hurtFPSCount=0;
            health--;
            gameManager.decreaseHealthBar(this);
            hurtAnimation = false;
            setShot(false);
        }
        if(health==0)
            die();
        this.setCurrentSprite(hurtSpriteSheet[hurtFPSCount/(HURT_FPS/hurtSpriteSheet.length)]);
        hurtFPSCount++;
    }

    private void die() {
        dyingAnimation = true;
        shootingAnimation = false;
        shootingFPSCount = 0;
        if(dieFPSCount==0){
            if(isLookingLeft())
                dX-=42;
            else {
                dX-=68;

            }

        }
        if(dieFPSCount==DYING_FPS){
            dieFPSCount=0;
            gameManager.removeActor(this);
            dyingAnimation=false;
            return;
        }
        this.setCurrentSprite(dyingSpriteSheet[dieFPSCount/(DYING_FPS/dyingSpriteSheet.length)]);
//        this.getCurrentSprite().setTranslateX(dX);
//        this.currentSprite.setFitWidth(280);




        dieFPSCount++;
    }

    public boolean isUp() {
        return isUp;
    }

    public void setUp(boolean up) {
        isUp = up;
    }

    public boolean isLeft() {
        return isLeft;
    }

    public void setLeft(boolean left) {
        isLeft = left;
    }

    public boolean isDown() {
        return isDown;
    }

    public void setDown(boolean down) {
        isDown = down;
    }

    public boolean isRight() {
        return isRight;
    }

    public void setRight(boolean right) {
        isRight = right;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public void setShooting(boolean shooting) {
        this.shooting = shooting;
    }

    public void setCurrentSVGPath(String currentSVGPath) {
        this.spriteBounds.setContent(currentSVGPath);
    }

    public boolean isShot() {
        return isShot;
    }

    public void setShot(boolean shot) {
        isShot = shot;
    }

    @Override
    public void increaseScore(int increaseAmount) {
        this.score+=increaseAmount;
    }

    @Override
    public void increaseLaserCounter() {
        if(laserCounter==1)
            return;
        laserCounter++;
        if(laserCounter==1)
            canShootLaser=true;
    }

    @Override
    public int getScore() {
        return score;
    }

    public boolean isLookingLeft() {
        return lookingLeft;
    }

    public void setLookingLeft(boolean lookingLeft) {
        this.lookingLeft = lookingLeft;
    }

    public void setLaserOn(boolean laserOn) {
        this.superShooting = laserOn;
    }

    public Laser getLaser(){
        return laser;
    }
}
