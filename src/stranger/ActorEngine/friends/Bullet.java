package stranger.ActorEngine.friends;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import stranger.ActorEngine.Actor;
import stranger.ActorEngine.Friend;
import stranger.ActorEngine.FriendlyFire;
import stranger.ActorEngine.friends.Stranger;
import stranger.collision_engine.CollisionManager;

public class Bullet extends Actor implements FriendlyFire {
    private Stranger stranger;
    private double offsetX = 21;
    private double offsetY = 45;
    private Image[] bulletSpriteSheet;
    private boolean isColliding;
    private boolean bulletAnimation;
    private int bulletAnimationFPSCounter;
    private final int BULLET_ANIMATION_FPS = 12;

    public Bullet( double dX, double dY,Stranger stranger) {
        super(dX, dY);
        this.vX=20;
        this.stranger=stranger;
        spriteBounds.setContent("M38.7,9.6c-1.7-7.4-6-7.7-8.7-8.7c-2.1-0.8-4.2-1.4-6.4-0.2c-2,1.1-4.1-0.5-6.1,1.4c0.5,0.2,0.9,0.6,1.3,1.3\n" +
                "\tc-1.7,1.4-3.6,0.5-5.3,0.3c0.9,1.9,2.8,1.2,3.5,3.8c-2.8,0.6-5.5,0-8.4,0.9l1,1.8c-1.2-0.1-2.5,0.1-3.4,1c0.2,0,0.5,0.3,0.7,0.4\n" +
                "\tC6,12.3,5.1,12,4.3,12.3c-1.3,0.5-3.2-0.1-4.3,2.3c1-0.2,1.5,0.9,2.3,2.1c0.9,1.4,2.2,0.6,3.2,0.2c-0.1,0.4-0.1,0.8-0.2,1.2\n" +
                "\tc0.9,0.5,2.2-0.1,3.2-0.3c0.8-0.2,1.6-0.2,2.4-0.2c0.9,0.2,1.9,0.6,2.7,1.6c-0.3,0.6-0.6,1.3-0.9,1.5c1.5-0.1,3.3-0.5,4.8,0.7\n" +
                "\tc-0.7,1.6-1.4,2.6-2.2,3.4c4.6,1.4,9.9-1.5,14.8-3.5c2.1-0.9,4.3-1.3,6.5-3.9C37.8,15.7,39.8,14.2,38.7,9.6z");
        setSpriteSheets();
        currentSprite = new ImageView(bulletSpriteSheet[0]);
        currentSprite.setTranslateX(dX);
        currentSprite.setTranslateY(dY);
        spriteBounds.setTranslateX(this.dX+offsetX);
        spriteBounds.setTranslateY(this.dY+offsetY);
        if(stranger.isLookingLeft()) {
            currentSprite.setScaleX(-1);
            spriteBounds.setScaleX(-1);
        }
    }

    private void setSpriteSheets() {
        bulletSpriteSheet = new Image[4];
        bulletSpriteSheet[0] = new Image("/resources/bullet1.png");
        bulletSpriteSheet[1] = new Image("/resources/bullet2.png");
        bulletSpriteSheet[2] = new Image("/resources/bullet3.png");
        bulletSpriteSheet[3] = new Image("/resources/bullet4.png");
    }

    @Override
    public void update() {
        isColliding= CollisionManager.collision(this,gameManager.getEnemies());
        currentSprite.setImage(bulletSpriteSheet[0]);
        currentSprite.setTranslateY(dY);
        if(this.currentSprite.getScaleX()==-1) {
            currentSprite.setTranslateX(dX-=vX);
            } else
            currentSprite.setTranslateX(dX+=vX);
        spriteBounds.setTranslateX(dX+offsetX);
        spriteBounds.setTranslateY(dY+offsetY);
        if(isColliding||bulletAnimation){
           // this.spriteBounds.setContent("");
            this.vX=0;
            bulletAnimation=true;
            if(bulletAnimationFPSCounter==BULLET_ANIMATION_FPS) {
                bulletAnimationFPSCounter=0;
                bulletAnimation=false;
                gameManager.removeActor(this);

            }
            this.currentSprite.setImage(bulletSpriteSheet[bulletAnimationFPSCounter /(BULLET_ANIMATION_FPS/3) + 1]);
            bulletAnimationFPSCounter++;
        }
        if(dX>1920||dX<-100)
            gameManager.removeActor(this);

    }


    @Override
    public Friend getShooter() {
        return this.stranger;
    }
}

