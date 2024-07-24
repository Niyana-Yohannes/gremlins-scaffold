package gremlins;

import processing.core.PImage;

import java.util.ArrayList;


public class Freeze extends MovingObject {

    //needs slime
    private ArrayList<Slime> slimes; 

    public Freeze(int x, int y, PImage sprite){
        super(x,y,sprite);

        this.xVel = 4;
        this.yVel = 4;

        this.collide = false;
        this.alive = true;
    }

    //setter
    public void setSlimes(ArrayList<Slime> slimes){
        this.slimes = slimes;
    }
    
    /**
     *  Executes logic that invovlves freezeball movement, and collision with tiles and slime
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
        
        //Checking if alive
        checkAlive(this.slimes);
    }

}

