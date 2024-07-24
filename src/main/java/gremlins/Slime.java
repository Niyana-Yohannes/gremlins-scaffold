package gremlins;

import processing.core.PImage;

import java.util.ArrayList;

public class Slime extends MovingObject {

    //needs fireball and freezeball
    private ArrayList<Fireball> fireballs;
    private ArrayList<Freeze> freezeballs;

    public Slime(int x, int y, PImage sprite){
        super(x,y,sprite);

        this.xVel = 4;
        this.yVel = 4;

        this.fireballs = null;
        this.freezeballs = null;

    }

    /**
     * Executes logic that invovlves slime movement, and collision with tiles, fireball and freezeball
     */
    public void tick(){

        //Moving
        checkCollision(this.bricks, this.direction);
        checkCollision(this.stones, this.direction);
        if(this.collide){
            this.alive = false;
        }
        if(!this.collide){
            if(this.direction.equals("left")){
                this.x -= xVel;
            }
            else if(this.direction.equals("up")){
                this.y -= yVel;
            }
            else if(this.direction.equals("right")){
                this.x += xVel;
            }
            else if(this.direction.equals("down")){
                this.y += yVel;
            }
        }

        //Collision with Fireball
        checkAlive(this.fireballs);
        //Collision with Freezeball
        checkAlive(this.freezeballs);


    }

    //setter
    public void setFireball(ArrayList<Fireball> fireballs){
        this.fireballs = fireballs;
    }
    public void setFreezeball(ArrayList<Freeze> freezeballs){
        this.freezeballs = freezeballs;
    }

}
