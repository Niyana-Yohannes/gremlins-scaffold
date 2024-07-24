package gremlins;

import processing.core.PImage;


public class PowerUp extends GameObject{

    private int timer;
    private PImage sprite1;
    private PImage sprite2;
    private PImage sprite3;
    
    public PowerUp(int x, int y, PImage sprite, PImage sprite1, PImage sprite2, PImage sprite3){
        super(x,y,sprite);
        this.sprite1 = sprite1;
        this.sprite2 = sprite2;
        this.sprite3 = sprite3;

    }

    public void tick(){

        if(this.timer == 10){           
            this.setSprite(sprite1);
        }
        else if(this.timer == 20){
            this.setSprite(sprite2);
        }
        else if(this.timer == 30){
            this.setSprite(sprite3);
            this.timer = 0;
        }
        this.timer++;
    }

}
