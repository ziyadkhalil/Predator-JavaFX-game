package stranger.ActorEngine;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.SVGPath;
import stranger.ActorEngine.friends.Laser;
import stranger.game_engine.GameManager;

import java.util.ArrayList;

public abstract class Actor {
    protected ArrayList<Image> runSpriteSheet = new ArrayList<>();
    protected ArrayList<Image> jumpSpriteSheet= new ArrayList<>();
    protected ArrayList<Image> idleSpriteSheet = new ArrayList<>();
    protected ImageView currentSprite;
    protected SVGPath spriteBounds;
    protected double dX=0;
    protected double vX=5;
    protected double dY;
    protected boolean isAlive;
    protected boolean isMoving;
    protected double offsetSVG;
    protected GameManager gameManager;

    public Actor(double dX, double dY){
        spriteBounds = new SVGPath();
        this.dX = dX;
        this.dY = dY;
        offsetSVG=0;
    }
    public abstract void update();
    public boolean collide(Actor actor) {
        if(!(actor instanceof Laser))
        if (this.spriteBounds.getBoundsInParent().intersects(actor.spriteBounds.getBoundsInParent()))
            return true;
        if(actor instanceof Laser){
            if (this.spriteBounds.getBoundsInParent().intersects(((Laser) actor).laserPath.getBoundsInParent()))
                return true;
        }
        return false;
    }

    public void setManager(GameManager gameManager){
        this.gameManager=gameManager;
    }

    public ArrayList<Image> getRunSpriteSheet() {
        return runSpriteSheet;
    }

    public void setRunSpriteSheet(ArrayList<Image> runSpriteSheet) {
        this.runSpriteSheet = runSpriteSheet;
    }

    public ImageView getCurrentSprite() {
        return currentSprite;
    }

    public void setCurrentSprite(Image sprite) {
        currentSprite.setImage(sprite);
    }

    public SVGPath getSpriteBounds() {
        return spriteBounds;
    }

    public void setSpriteBounds(SVGPath spriteBounds) {
        this.spriteBounds = spriteBounds;
    }

    public double getdX() {
        return dX;
    }

    public void setdX(double dX) {
        this.dX = dX;
    }

    public double getdY() {
        return dY;
    }

    public void setdY(double dY) {
        this.dY = dY;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public double getOffsetSVG() {
        return offsetSVG;
    }

    public void setOffsetSVG(double offsetSVG) {
        this.offsetSVG = offsetSVG;
    }

    public Bounds getBounds(){
     return this.spriteBounds.getBoundsInParent();
    }
}
