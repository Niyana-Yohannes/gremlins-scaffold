package gremlins;

import processing.core.PImage;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.Random;
import java.util.Iterator;

public class Gremlin extends MovingObject{
    
    private double cooldown;

    //Gremlin also needs acces to wizard, fireball, and freezeball, simes, and airTiles
    private Wizard wizard;
    private ArrayList<Air> airTiles;
    private ArrayList<Fireball> fireballs;
    private ArrayList<Freeze> freezeballs;
    private ArrayList<Slime> slimes;
    //For moving
    private final Random randomGenerator = new Random();
    private int intDirection = randomGenerator.nextInt(4);
    private boolean hasMoved;
    private int prevDir;     
    private HashSet<Integer> checkPrevDirs;     
    //For Shooting
    private int timer;
    private boolean shoot;
    private PImage slimePimage; 
    //For respawning
    private ArrayList<Air> respawnTiles;
    //For frozen
    private boolean frozen;
    private int frozenTimer;

    public Gremlin(int x, int y, PImage sprite, PImage slime){
        super(x,y,sprite);
        this.slimePimage = slime;
        this.wizard = null;
        this.fireballs = null;
        this.freezeballs = null;

        this.xVel = 1;
        this.yVel = 1;
        
        this.collide = false;
        this.alive = true;

        this.cooldown = 0; 
        this.hasMoved = false;

        this.checkPrevDirs = new HashSet<Integer>();

        this.timer = 0;
        this.shoot = true;

        this.respawnTiles = new ArrayList<Air>();

        this.frozen = false;
        this.frozenTimer = 0;

        this.prevDir = 0;
        this.direction = null;

    }

    /**
     * Executes logic that involves gremlin movement, collision with fireball and freezeball, and gremlin shooting
     */
    public void tick(){
        
        //If frozen, cannot move for 3sec
        checkFrozenBoth();

        //Move only if not frozen
        if(!this.frozen){
            //Moving
            prevDir = intDirection; //Storing current move as previous move
            intDirtoStringDir(); 
            //Change direction only if collision 
            while(!this.hasMoved){
                checkCollision(this.bricks, this.direction);
                checkCollision(this.stones, this.direction);
                if(this.collide){
                    intDirection = randomGenerator.nextInt(4);
                    //Ensuring it doesn't move in the same direction it came from
                    while((prevDir == 0 && intDirection == 2) || (prevDir == 2 && intDirection == 0) || (prevDir == 1 && intDirection == 3) || (prevDir == 3 && intDirection == 1)){
                        intDirection = randomGenerator.nextInt(4); 
                        checkPrevDirs.add(intDirection);
                        //If its checked all moves, it means only direction it came from is possible option
                        if(checkPrevDirs.size() == 4){
                            break;
                        }
                    }
                    //If its checked all moves, it means only direction it came from is possible option
                    if(checkPrevDirs.size() == 4){
                        if(prevDir == 0){intDirection = 2;}
                        else if(prevDir == 2){intDirection = 0;}
                        else if(prevDir == 1){intDirection = 3;}
                        else if(prevDir == 3){intDirection = 1;}
                    }
                    intDirtoStringDir();
                }
                //If there is no collision, move in same direction
                else if(this.direction.equals("left")){
                    this.x -= xVel;
                    this.hasMoved = true;
                }
                else if(this.direction.equals("up")){
                    this.y -= yVel;
                    this.hasMoved = true;
                }
                else if(this.direction.equals("right")){
                    this.x += xVel;
                    this.hasMoved = true;
                }
                else if(this.direction.equals("down")){
                    this.y += yVel;
                    this.hasMoved = true;
                }
                this.collide = false;
            }
            checkPrevDirs.clear();
            this.hasMoved = false;
        }
        //Frozen for 3 seconds
        if(this.frozen){
            frozenTimer++;
            if(frozenTimer >= 3*60){ 
                this.frozen = false;
                frozenTimer = 0;
            }
        }


        //Collision with fireball
        checkAliveBoth(this.fireballs); 

        //If  dead, start in new direction 10 tiles away from wizard;
        if(!this.alive){
            int wizx = this.wizard.getX();
            int wizy = this.wizard.getY();
            for(Air airTile : airTiles){
                if(airTile.getX() >= wizx + 10*20  || airTile.getX() <= wizx -10*20 && airTile.getY() >= wizy + 10*20  || airTile.getY() <= wizy -10*20){
                    this.respawnTiles.add(airTile);
                }
            }
            int chooserand = randomGenerator.nextInt(respawnTiles.size());
            Air nextGremPos = respawnTiles.get(chooserand);
            this.x = nextGremPos.getX();
            this.y = nextGremPos.getY();
            respawnTiles.clear(); 

            this.frozen = false;
        }

        //Shooting slime
        if(this.alive){
            addSlime();
            if(!this.frozen){
                timer++; //Only increment timer if not frozen
            }
            if(timer==cooldown*60){ //60FPS and cooldown of 3sec --> Shoot after 180 frames
                this.shoot = true;
                timer = 0;
            }
            checkDeadSlime();
        }

        //Return to default condition
        this.alive = true;
    }

    //setter methods
    public void setCooldown(double cooldown){
        this.cooldown = cooldown;
    }
    public void setWizard(Wizard wizard){
        this.wizard = wizard;
    }
    public void setAirTiles(ArrayList<Air> airTiles){
        this.airTiles = airTiles;
    }
    public void setFireballs(ArrayList<Fireball> fireballs){
        this.fireballs = fireballs;
    }
    public void setFreezeballs(ArrayList<Freeze> freezeballs){
        this.freezeballs = freezeballs;
    }
    public void setSlimes(ArrayList<Slime> slimes){
        this.slimes = slimes;
    }

    //Change direction based on integers
    private void intDirtoStringDir(){
        if(this.intDirection == 0){
            this.direction = "left";
        }
        else if(this.intDirection == 1){
            this.direction = "up";
        }
        else if(this.intDirection == 2){
            this.direction = "right";
        }
        else if(this.intDirection == 3){
            this.direction = "down";
        }
    }

    //Add slime
    private void addSlime(){
        if(this.shoot){
            this.slimes.add(new Slime(this.x, this.y, this.slimePimage));
            this.slimes.get(slimes.size() - 1).setDirection(this.direction);
            this.slimes.get(slimes.size() - 1).setBricks(this.bricks);
            this.slimes.get(slimes.size() - 1).setStones(this.stones);
            this.slimes.get(slimes.size() - 1).setFireball(this.fireballs);
            this.slimes.get(slimes.size() - 1).setFreezeball(this.freezeballs);
        }
        this.shoot = false;
    }
    //Remove slime that has collided with something
    private void checkDeadSlime(){
        Iterator<Slime> itr = this.slimes.iterator();
        while(itr.hasNext()){
            Slime slime = itr.next();
            if(!slime.getAlive()){
                itr.remove();
            }
        }
    }
    //Checks if freezeball hits gremlin, and if so sets frozen attribute to true
    private void checkFrozenBoth(){
        for (Freeze freezeball: this.freezeballs){
            int thisRight = this.x + this.sprite.width;
            int thisBottom = this.y + this.sprite.height;

            int freezeballLeft = freezeball.getX();
            int freezeballRight = freezeball.getX() + freezeball.getWidth();
            int freezeballTop = freezeball.getY();
            int freezeballBottom = freezeball.getY() + freezeball.getHeight();

            if(thisRight > freezeballLeft && this.x < freezeballRight && thisBottom > freezeballTop && this.y < freezeballBottom){
                this.frozen = true;
                freezeball.setNotAlive();
                return;
            }
        }
    }
}
