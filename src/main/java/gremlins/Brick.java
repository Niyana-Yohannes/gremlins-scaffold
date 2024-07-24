package gremlins;

import processing.core.PImage;

public class Brick extends Tiles {

    //has access to all sprites for brick
    private PImage brickwall_destroyed0;
    private PImage brickwall_destroyed1;
    private PImage brickwall_destroyed2;
    private PImage brickwall_destroyed3;

    //needs alive
    private boolean alive;

    //animation
    private int timer;
    private int prevTimer;
    private boolean switchtoAirTile;

    public Brick(int x, int y, PImage sprite, PImage brickwall_destroyed0, PImage brickwall_destroyed1, PImage brickwall_destroyed2, PImage brickwall_destroyed3) {
        super(x, y, sprite);
        this.brickwall_destroyed0 = brickwall_destroyed0;
        this.brickwall_destroyed1 = brickwall_destroyed1;
        this.brickwall_destroyed2 = brickwall_destroyed2;
        this.brickwall_destroyed3 = brickwall_destroyed3;

        this.alive = true;
        this.timer = 0;
        this.prevTimer = 0;
        this.switchtoAirTile = false;

    }
    
    /**
     * Checks if object is alive, and if not, begins and performs destruction animation
     */
    public void tick() {
        //If hit by wizard fireball projectile, destroy
        if(!this.alive){
            if(this.timer == this.prevTimer){           
                this.setSprite(brickwall_destroyed0);
            }
            else if(this.timer == this.prevTimer + 4){
                this.setSprite(brickwall_destroyed1);
            }
            else if(this.timer == this.prevTimer + 8){
                this.setSprite(brickwall_destroyed2);
            }
            else if(this.timer == this.prevTimer + 12){
                this.setSprite(brickwall_destroyed3);
            }
            else if(this.timer == this.prevTimer + 16){
                this.switchtoAirTile = true;
            }
            this.timer++;
        }
    }

    //getter
    public boolean getSwitchToAirTiles(){
        return this.switchtoAirTile;
    }
    public void setAlive(){
        this.alive = false;
    }
           
}
