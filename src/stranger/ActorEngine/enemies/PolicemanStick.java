package stranger.ActorEngine.enemies;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import stranger.ActorEngine.Actor;
import stranger.ActorEngine.EnemyFire;
import stranger.collision_engine.CollisionManager;

public class PolicemanStick extends Actor implements EnemyFire {
    double dX;
    double dY;
    Policeman policeman;
    public Rectangle stick;
    public PolicemanStick(double dX, double dY,Policeman policeman) {
        super(dX,dY);
        this.currentSprite= new ImageView();
        stick=new Rectangle();
        stick.setX(dX+115);
        stick.setY(dY+85);
        stick.setWidth(16);
        stick.setHeight(67);
        stick.setRotate(62.7);
        this.policeman=policeman;
        stick.setFill(Paint.valueOf("red"));

    }

    @Override
    public void update() {
        CollisionManager.collision(this,gameManager.getFriends());
    }

    @Override
    public Bounds getBounds() {
        return stick.getBoundsInParent();
    }
}
