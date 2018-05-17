package stranger.ActorEngine.enemies;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import stranger.ActorEngine.Actor;
import stranger.ActorEngine.EnemyFire;
import stranger.collision_engine.CollisionManager;

public class PolicemanBullet extends Actor implements EnemyFire {
    private static final int BULLET_ANIMATION_FPS = 12;
    Policeman policeman;
    Image[] bulletSpriteSheet;
    private boolean isColliding;
    private double offsetX=75;
    private double offsetY=95;
    private boolean bulletAnimation;
    private int bulletAnimationFPSCounter=0;

    public PolicemanBullet(double dX, double dY, Policeman policeman) {
        super(dX, dY);
        this.vX=20;
        this.policeman=policeman;
        spriteBounds.setContent("M3,0C1.3,0,0,1.2,0,2.9C0,4.7,1.2,6,3,6c1.7,0,3-1.3,3-3C6,1.3,4.8,0,3,0z");
        setSpriteSheets();
        currentSprite = new ImageView(bulletSpriteSheet[0]);
        currentSprite.setCache(true);
        currentSprite.setTranslateX(this.dX+=offsetX);
        currentSprite.setTranslateY(this.dY+=offsetY);
        if(this.policeman.isLookingLeft())
            currentSprite.setScaleX(-1);


    }

    @Override
    public void update() {
        isColliding= CollisionManager.collision(this,gameManager.getFriends());
        currentSprite.setImage(bulletSpriteSheet[0]);
        currentSprite.setTranslateY(dY);
        if(this.currentSprite.getScaleX()==-1) {
            currentSprite.setTranslateX(dX-=vX);
        } else
            currentSprite.setTranslateX(dX+=vX);
        spriteBounds.setTranslateX(dX);
        spriteBounds.setTranslateY(dY);

        if(isColliding||bulletAnimation){
          //  this.spriteBounds.setContent("");
            this.vX=0;
            bulletAnimation=true;
            bulletAnimationFPSCounter++;
                if(bulletAnimationFPSCounter==BULLET_ANIMATION_FPS) {
                bulletAnimationFPSCounter=0;
                bulletAnimation=false;
                gameManager.removeActor(this);

            }
            this.currentSprite.setImage(bulletSpriteSheet[bulletAnimationFPSCounter /(BULLET_ANIMATION_FPS/3) + 1]);
        }
        if(dX>1920||dX<-100)
            gameManager.removeActor(this);
    }

    private void setSpriteSheets() {
        bulletSpriteSheet = new Image[4];
        bulletSpriteSheet[0] = new Image("/resources/PoliceMan1/Bullet1.png");
        bulletSpriteSheet[1] = new Image("/resources/PoliceMan1/Bullet2.png");
        bulletSpriteSheet[2] = new Image("/resources/PoliceMan1/Bullet3.png");
        bulletSpriteSheet[3] = new Image("/resources/PoliceMan1/Bullet4.png");
    }


}
