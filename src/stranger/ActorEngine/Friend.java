package stranger.ActorEngine;

import javafx.geometry.Bounds;

public interface Friend {
    Bounds getBounds();
    void setShot(boolean shot);
    void increaseScore(int increaseAmount);
    void increaseLaserCounter();
    int getScore();
}
