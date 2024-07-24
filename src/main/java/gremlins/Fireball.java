package gremlins;

import processing.core.PImage;

import java.util.ArrayList;

public class Fireball extends MovingObject {

    //needs access to slime
    private ArrayList<Slime> slimes;

    public Fireball(int x, int y, PImage sprite){
        super(x,y,sprite);

        this.xVel = 4;
        this.yVel = 4;

        this.slimes = null;
    }

    /**
     * Executes logic that invovlves fireball movement, and collision with tiles and slime
     */
    public void tick(){

        //Moving

        //Checks if collides with bricks
        checkBrickCollision(this.bricks, this.direction);
        if(this.collide){
            this.alive = false;
        }
        //Check if collides with stones
        checkCollision(this.stones, this.direction);
        if(this.collide){
            this.alive = false;
        }
        //If not collided, continue moving in same direction
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
        
        //If collided with slime, is dead
        checkAlive(this.slimes);
    }

    //setter
    public void setSlimes(ArrayList<Slime> slimes){
        this.slimes = slimes;
    }


    /**
     * Fireball has its own collision method as if it collides with a brick, the brick destruction animation needs to begin
     * @param bricks: Arraylist of type brick
     * @param direction: Direction fireball will move
     */
    public void checkBrickCollision(ArrayList<Brick> bricks, String direction){ 
        for(Brick brick: bricks){

            int brickLeft = brick.getX();
            int brickRight = brick.getX() + brick.getWidth();
            int brickTop = brick.getY();
            int brickBottom = brick.getY() + brick.getHeight();

            if(direction.equals("left")){
                int fireballLeft = this.x - this.xVel;
                int fireballRight = this.x + this.sprite.width - this.xVel;
                int fireballBottom = this.y + this.sprite.height;
                int fireballTop = this.y;
                if(fireballRight > brickLeft &&fireballLeft < brickRight &&fireballBottom > brickTop &&fireballTop < brickBottom){
                    this.collide = true;
                    brick.setAlive();
                    return;
                }
            }
            else if(direction.equals("right")){
                int fireballLeft = this.x + this.xVel;
                int fireballRight = this.x + this.sprite.width + this.xVel;
                int fireballBottom = this.y + this.sprite.height;
                int fireballTop = this.y;
                if(fireballRight > brickLeft &&fireballLeft < brickRight &&fireballBottom > brickTop &&fireballTop < brickBottom){
                    this.collide = true;
                    brick.setAlive();
                    return;
                }
            }
            else if(direction.equals("down")){
                int fireballLeft = this.x;
                int fireballRight = this.x + this.sprite.width;
                int fireballBottom = this.y + this.sprite.height + this.yVel;
                int fireballTop = this.y + this.yVel;
                if(fireballRight > brickLeft &&fireballLeft < brickRight &&fireballBottom > brickTop &&fireballTop < brickBottom){
                    this.collide = true;
                    brick.setAlive();
                    return;
                }
            }
            else if(direction.equals("up")){
                int fireballLeft = this.x;
                int fireballRight = this.x + this.sprite.width;
                int fireballBottom = this.y + this.sprite.height - this.yVel;
                int fireballTop = this.y - this.yVel;
                if(fireballRight > brickLeft && fireballLeft < brickRight && fireballBottom > brickTop && fireballTop < brickBottom){
                    this.collide = true;
                    brick.setAlive();
                    return;
                }
            }
        }

    }
}
