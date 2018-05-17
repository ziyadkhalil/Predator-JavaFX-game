package stranger.collision_engine;

import stranger.ActorEngine.*;
import stranger.ActorEngine.friends.Bullet;
import stranger.ActorEngine.friends.Laser;

import java.util.ArrayList;

public class CollisionManager {

    public static boolean collision (EnemyFire enemyFire, ArrayList<Friend>friends){
        for (Friend friend :
             friends) {
            if(friend.getBounds().intersects(enemyFire.getBounds())) {
                friend.setShot(true);
                return true;
            }
        }
        return false;
    }

    public static boolean collision (FriendlyFire friendlyFire, ArrayList<Enemy>enemies){
        for (Enemy enemy :
                enemies) {
            if(friendlyFire.getBounds().intersects(enemy.getBounds())) {
                if(friendlyFire instanceof Laser){
                    ((Laser) friendlyFire).getSpriteBounds().setContent("");
                    enemy.getSpriteBounds().setContent("");
                   friendlyFire.getShooter().increaseScore(20);
                    enemy.setDead();
                    return true;

                }
                else if(friendlyFire instanceof Bullet){
                    ((Bullet) friendlyFire).getSpriteBounds().setContent("");
                    ((Bullet) friendlyFire).getSpriteBounds().setLayoutY(5000);
                    int enemyHealth = enemy.getHealth();
                    enemy.setShot(true);
                    if (enemyHealth==1) {
                        enemy.getSpriteBounds().setContent("");
                        friendlyFire.getShooter().increaseScore(10);
                        friendlyFire.getShooter().increaseLaserCounter();
                        System.out.println("INCREASE");
                    } else if(enemyHealth>1) {
                        friendlyFire.getShooter().increaseScore(1);
                        System.out.println("INCREASE AWI");
                        System.out.println(((Bullet) friendlyFire).getSpriteBounds().getContent());

                    }

                    return true;
                }

            }
        }
        return false;
    }

}
