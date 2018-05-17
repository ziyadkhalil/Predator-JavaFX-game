package stranger.game_engine;

import javafx.animation.AnimationTimer;
import stranger.ActorEngine.Actor;

import java.util.ArrayList;

public class GameLoop extends AnimationTimer {
    private boolean loopOnOffToggle=true;
    ArrayList<Actor> actors;
    GameManager manager;

    @Override
    public void handle(long now) {
        for (Actor actor:
             actors) {
            actor.update();
        }
        manager.update();

    }
    @Override
    public void start(){
        super.start();

    }
    @Override
    public void stop(){
        super.stop();
    }
    public void setActors(ArrayList<Actor> actors ){
        this.actors=actors;
    }
    public void setManager(GameManager manager) {
        this.manager = manager;
    }
    public void toggleOnOff(){
        if(loopOnOffToggle){
            this.stop();
            loopOnOffToggle=false;
        }
        else {
            this.start();
            loopOnOffToggle=true;
        }
    }
}
