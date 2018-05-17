package stranger.ActorEngine;

import javafx.geometry.Bounds;
import javafx.scene.shape.SVGPath;

public interface Enemy {
    Bounds getBounds();
    void setShot(boolean isShot);
    boolean isDead();
    void setDead();
    int getHealth();
    SVGPath getSpriteBounds();
}
