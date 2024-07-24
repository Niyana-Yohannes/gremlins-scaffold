package gremlins;

import processing.core.PApplet;
import processing.core.PImage;

public abstract class GameObject {

    //all game objects have x,y and sprite
    /**
     * x axis of moving object
     */
    protected int x;
    /**
     * y axis of moving object
     */
    protected int y;
    /**
     * sprite of moving object
     */
    protected PImage sprite;


    public GameObject(int x, int y, PImage sprite) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
    }

    /**
     * all game objects have a tick method
     */
    public abstract void tick();
    
    /**
     * Draws game object
     * @param app: Main game class
     */
    public void draw(PApplet app) {
        app.image(this.sprite, this.x, this.y);
    }

    //Getter methods for game objects
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public int getWidth(){
        return this.sprite.width;
    }
    public int getHeight(){
        return this.sprite.height;
    }
    //get&set sprite methods 
    public PImage getPImage(){ //getSprite
        return this.sprite;
    }
    public void setSprite(PImage image){//setSprite
        this.sprite = image;
    }

    //Setter methods (mainly used for testing)
    public void setX(int X){
        this.x = X;
    }
    public void setY(int Y){
        this.y = Y;
    }
}
