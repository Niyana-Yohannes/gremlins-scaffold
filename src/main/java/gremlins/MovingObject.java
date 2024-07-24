package gremlins;

import processing.core.PImage;

import java.util.ArrayList;


public abstract class MovingObject extends GameObject {

    //all moving objects need access to bricks, stones
    /**
     * all moving objects need access to stones
     */
    protected ArrayList<Stone> stones;
    /**
     * all moving objects need access to bricks
     */
    protected ArrayList<Brick> bricks;

    //every moving object has xVel and yVel
    /**
     * every moving object has an x velocity
     */
    protected int xVel;
    /**
     * every moving object has a y velocity
     */
    protected int yVel;
    /**
     * every moving object has an direction
     */
    protected String direction;

    //all moving objects can collide with other objects
    /**
     * Boolean that determines if object has collided with another object 
     */
    protected boolean collide;

    //all moving objects can cease to exit
    /**
     * Boolean that determines if object is alive as all moving objects can die
     */
    protected boolean alive;

    public MovingObject(int x, int y, PImage sprite){
        super(x, y, sprite);
        this.stones = null;
        this.bricks = null;
        this.collide = false;
        this.alive = true;
        this.direction = null;
    }

    //getter
    public boolean getAlive(){
        return this.alive;
    }

    //setter
    public void setStones(ArrayList<Stone> stones){
        this.stones = stones;
    }
    public void setBricks(ArrayList<Brick> bricks){
        this.bricks = bricks;
    }
    public void setDirection(String direction){
        this.direction = direction;
    }
    public void setNotAlive(){
        this.alive = false;
    }


    /**
     * Determines if this object moving in a certain direction results in a collision with an object specified in parameters, and if so sets attribute collide to false.
     * Uses rectangle collision detection.  
     * @param gameObjects: An arraylist of types that extend game objects 
     * @param direction: A string which indicates direction moving object will move
     */
    public void checkCollision(ArrayList<? extends GameObject> gameObjects, String direction){ 
        for(GameObject gameObject: gameObjects){

            int GameObjectLeft = gameObject.getX();
            int GameObjectRight = gameObject.getX() + gameObject.getWidth();
            int GameObjectTop = gameObject.getY();
            int GameObjectBottom = gameObject.getY() + gameObject.getHeight();

            if(direction.equals("left")){
                int movingObjectLeft = this.x - this.xVel;
                int movingObjectRight = this.x + this.sprite.width - this.xVel;
                int movingObjectBottom = this.y + this.sprite.height;
                int movingObjectTop = this.y;
                if(movingObjectRight > GameObjectLeft && movingObjectLeft < GameObjectRight && movingObjectBottom > GameObjectTop && movingObjectTop < GameObjectBottom){
                    this.collide = true;
                    return;
                }
            }
            else if(direction.equals("right")){
                int movingObjectLeft = this.x + this.xVel;
                int movingObjectRight = this.x + this.sprite.width + this.xVel;
                int movingObjectBottom = this.y + this.sprite.height;
                int movingObjectTop = this.y;
                if(movingObjectRight > GameObjectLeft && movingObjectLeft < GameObjectRight && movingObjectBottom > GameObjectTop && movingObjectTop < GameObjectBottom){
                    this.collide = true;
                    return;
                }
            }
            else if(direction.equals("down")){
                int movingObjectLeft = this.x;
                int movingObjectRight = this.x + this.sprite.width;
                int movingObjectBottom = this.y + this.sprite.height + this.yVel;
                int movingObjectTop = this.y + this.yVel;
                if(movingObjectRight > GameObjectLeft && movingObjectLeft < GameObjectRight && movingObjectBottom > GameObjectTop && movingObjectTop < GameObjectBottom){
                    this.collide = true;
                    return;
                }
            }
            else if(direction.equals("up")){
                int movingObjectLeft = this.x;
                int movingObjectRight = this.x + this.sprite.width;
                int movingObjectBottom = this.y + this.sprite.height - this.yVel;
                int movingObjectTop = this.y - this.yVel;
                if(movingObjectRight > GameObjectLeft && movingObjectLeft < GameObjectRight && movingObjectBottom > GameObjectTop && movingObjectTop < GameObjectBottom){
                    this.collide = true;
                    return;
                }
            }
        }

    }


    /**
     * Determines if object has already collided with a moving object, and if so, set alive attribute to false.
     * @param movingObjects: An arraylist of types that extend moving objects
     */
    public void checkAlive(ArrayList<? extends MovingObject> movingObjects){
        for (MovingObject movingObject: movingObjects){
            int thisRight = this.x + this.sprite.width;
            int thisBottom = this.y + this.sprite.height;

            int movingObjectLeft = movingObject.getX();
            int movingObjectRight = movingObject.getX() + movingObject.getWidth();
            int movingObjectTop = movingObject.getY();
            int movingObjectBottom = movingObject.getY() + movingObject.getHeight();

            if(thisRight > movingObjectLeft && this.x < movingObjectRight && thisBottom > movingObjectTop && this.y < movingObjectBottom){
                this.alive = false;
                return;
            }
        }
    }

    // Collision Method to see if already collided (Sets both alive to false)
    /**
     * Determines if object has already collided with a moving object, and if so, set alive attribute of both object and moving object to false.
     * @param movingObjects: An arraylist of types that extend moving objects
     */
    public void checkAliveBoth(ArrayList<? extends MovingObject> movingObjects){
        for (MovingObject movingObject: movingObjects){
            int thisRight = this.x + this.sprite.width;
            int thisBottom = this.y + this.sprite.height;

            int movingObjectLeft = movingObject.getX();
            int movingObjectRight = movingObject.getX() + movingObject.getWidth();
            int movingObjectTop = movingObject.getY();
            int movingObjectBottom = movingObject.getY() + movingObject.getHeight();

            if(thisRight > movingObjectLeft && this.x < movingObjectRight && thisBottom > movingObjectTop && this.y < movingObjectBottom){
                this.alive = false;
                movingObject.setNotAlive();
                return;
            }
        }
    }   
}