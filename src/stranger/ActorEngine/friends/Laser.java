package stranger.ActorEngine.friends;

import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import stranger.ActorEngine.Actor;
import stranger.ActorEngine.Friend;
import stranger.ActorEngine.FriendlyFire;
import stranger.collision_engine.CollisionManager;


public class Laser extends Actor implements FriendlyFire {
    private static final int LASER_FPS = 1  * 90;
    private Stranger stranger;
    private Image[] laserSpriteSheet;
    private int laserFPSCount=0;
    private boolean laserOn;
    public Rectangle laserPath;
    private double tempX;

    public Laser(double dX, double dY, Stranger stranger) {
        super(dX, dY);
        setSpriteSheets();
        this.currentSprite = new ImageView();
        currentSprite.setX(dX);
        currentSprite.setY(dY);
        currentSprite.setSmooth(true);
        laserOn=true;
        this.stranger=stranger;
        if(stranger.isLookingLeft())
            currentSprite.setScaleX(-1);
        laserPath=new Rectangle(0,0,-1,-1);
        laserPath.setFill(Paint.valueOf("red"));
    }

    private void setSpriteSheets() {
        laserSpriteSheet = new Image[6];
        laserSpriteSheet[0] = new Image("/resources/lazer1z.png");
        laserSpriteSheet[1] = new Image("/resources/lazer2z.png");
        laserSpriteSheet[2] = new Image("/resources/lazer3z.png");
        laserSpriteSheet[3] = new Image("/resources/lazer4z.png");
        laserSpriteSheet[4] = new Image("/resources/lazer5z.png");
        laserSpriteSheet[5] = new Image("/resources/lazer6z.png");
    }

    @Override
    public void update() {
        if(laserOn)
            laserAnimation();
        System.out.println(gameManager);
        CollisionManager.collision(this,gameManager.getEnemies());
    }

    private void laserAnimation() {
        if (laserFPSCount==0) {
            tempX = this.currentSprite.getX();
        }
        if(laserFPSCount==LASER_FPS){
            laserFPSCount=0;
            System.out.println("hai");
            this.currentSprite.setImage(null);
            this.currentSprite.setViewport(null);
            gameManager.removeActor(this);
            setLaserOff();
        }


        if(stranger.isLookingLeft())
            leftLaserAnimation();
        else
            rightLaserAnimation();

        if(2<laserFPSCount&&laserFPSCount<5)
            this.currentSprite.setImage(laserSpriteSheet[0]);
        else if(5<laserFPSCount&&laserFPSCount<=10)
            this.currentSprite.setImage(laserSpriteSheet[1]);
        else if(laserFPSCount>10&&laserFPSCount<90) {
            this.currentSprite.setViewport(new Rectangle2D(0,0, 50 * (laserFPSCount - 10),120));
            this.laserPath.setWidth(50 * (laserFPSCount - 10));
            if(stranger.isLookingLeft()){
                this.currentSprite.setX(stranger.getdX()+40-laserPath.getWidth());
                this.laserPath.setX(stranger.getdX()+40-laserPath.getWidth());
                System.out.println(laserPath.getX());
          //      this.laserPath.setX(tempX-currentSprite.getBoundsInParent().getWidth()+100);
            }
            this.currentSprite.setImage(laserSpriteSheet[((laserFPSCount / 5) % 4) +2 ]);

        }
        System.out.println(laserFPSCount+"ظظظظظظظظظظ");
        laserFPSCount++;
    }

    private void rightLaserAnimation() {
        this.currentSprite.setScaleX(1);
        this.laserPath.setScaleX(1);
        this.currentSprite.setX(stranger.getdX()+140);
        this.laserPath.setX(stranger.getdX()+140);

    }

    private void leftLaserAnimation() {
        this.currentSprite.setScaleX(-1);
        this.laserPath.setScaleX(-1);

        this.currentSprite.setX(stranger.getdX()+40);
        this.laserPath.setX(stranger.getdX()+40);

    }

    private void setLaserOff() {
        this.laserOn=false;
        this.laserPath.setWidth(-1);
        this.laserPath.setHeight(-1);
    }


    public void setLaserOn() {
        this.laserPath.setHeight(10);
        this.laserOn=true;
    }

    public void setCoordinates(double dX, double dY) {
        this.dX=dX;
        this.dY=dY;
        this.currentSprite.setX(dX);
        this.currentSprite.setY(dY);
        this.laserPath.setX(dX);
        this.laserPath.setY(dY+50);
    }

    @Override
    public Bounds getBounds(){
        System.out.println("MIN X:" +laserPath.getBoundsInParent().getMinX());
        System.out.println("MAX X:" +laserPath.getBoundsInParent().getMaxX());
        System.out.println("X X:" +laserPath.getX());
        System.out.println("WIDTH :" +laserPath.getWidth());
        System.out.println("BOUND:" + (laserPath.getBoundsInParent().getMaxX() - laserPath.getBoundsInParent().getMinX()));
        return this.laserPath.getBoundsInParent();
    }

    public Friend getShooter(){
        return this.stranger;
    }
}
