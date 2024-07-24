package gremlins;

import processing.core.PImage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Random;


public class Wizard extends MovingObject{

    private int lives;
    private double cooldown;

    //Wizard also needs access to gremlins, slime, fireball, and freezeball, airTiles and powerups
    private ArrayList<Gremlin> gremlins;
    private ArrayList<Slime> slimes;
    private ArrayList<Fireball> fireballs;
    private ArrayList<Freeze> freezeballs;
    private ArrayList<Air> airTiles;
    private ArrayList<PowerUp> powerups;
    //Wizard also needs access to PImages
    private PImage wizard0;
    private PImage wizard1;
    private PImage wizard2;
    private PImage wizard3;
    private PImage fireballPimage; 
    private PImage freezeballPimage; 
    private PImage air;
    private PImage powerUp;
    private PImage powerUp1;
    private PImage powerUp2;
    private PImage powerUp3;
    //For moving
    private boolean isMoving;
    private int pixelCounter;
    private boolean moveLeft;
    private boolean moveRight;
    private boolean moveUp;
    private boolean moveDown;
    private boolean moveLeftWholeTile;
    private boolean moveRightWholeTile;
    private boolean moveUpWholeTile;
    private boolean moveDownWholeTile;
    //For shooting
    private int timerFireball; 
    private int timerFreezeball;
    private boolean shootFireball;
    private boolean shootFreezeball;
    //For powerup
    private boolean changeVel;
    private int changeVelTimer;
    //For cooldown bar
    private boolean drawRectFireball;
    private boolean drawRectFreezeball;
    private int timerRectFireball; 
    private int timerRectFreezeball;
    //For powerup
    private PowerUp powerUpFirst;
    private boolean firstPowerUp;
    private boolean removefirst;
    private int initialTimer;
    private final Random randomGenerator = new Random();
    private boolean powerUpReached;
    private boolean drawPowerUp;
    private int remainingTime;
    private int powerUpTimer;


    public Wizard(int x, int y, PImage sprite, PImage fireballPimage, PImage freezeballPimage, PImage air, PImage powerUp, PImage wizard0, PImage wizard2, PImage wizard3, PImage powerup1, PImage powerup2, PImage powerup3){
        super(x,y,sprite);

        this.fireballPimage = fireballPimage;
        this.freezeballPimage = freezeballPimage;
        this.air = air;
        this.powerUp = powerUp;
        this.wizard0 = wizard0;
        this.wizard1 = sprite;
        this.wizard2 = wizard2;
        this.wizard3 = wizard3;
        this.powerUp1 = powerup1;
        this.powerUp2 = powerup2;
        this.powerUp3 = powerup3;

        this.direction = "right";

        this.gremlins = null;
        this.slimes = null;
        this.fireballs = null;
        this.freezeballs = null;
        this.airTiles = null;
        this.powerups = null;
    
        this.xVel = 2;
        this.yVel = 2;

        this.lives = 0; 
        this.cooldown = 0; 

        this.isMoving = false;
        this.pixelCounter = 0;
        this.moveLeft = false;
        this.moveRight = false;
        this.moveUp = false;
        this.moveDown = false;
        this.moveLeftWholeTile = false;
        this.moveRightWholeTile = false;
        this.moveUpWholeTile = false;
        this.moveDownWholeTile = false;

        this.timerFireball = 0;
        this.timerFreezeball = 0;
        this.shootFireball = false;
        this.shootFreezeball = false;

        this.changeVel = false;
        changeVelTimer = 0;

        this.drawRectFireball = false;
        this.drawRectFreezeball = false;
        this.timerRectFireball = 0;
        this.timerRectFreezeball = 0;

        this.powerUpTimer = 0;
        this.initialTimer = 0;
        this.powerUpReached = false;
        this.firstPowerUp = true;
        this.removefirst = true;
        this.drawPowerUp = false;
        this.remainingTime = 10;      
    }

    /**
     * Executes logic involving wizard movement, powerup, collision with slimes and gremlins, and wizard shooting
     */
    public void tick(){
        //For powerup   
        //If powerup change velocity     
        if(this.changeVel){
            this.xVel = 4;
            this.yVel = 4;
            changeVelTimer++;
            if(changeVelTimer % 60 == 0){this.remainingTime--;}
        }
        //Return to normal velocity after powerup timer reached and wizard on whole tile
        if(changeVelTimer >= 10*60){ 
            if(this.isMoving){
                ReachWholeTileWithPowerUp();
            }
            else{
                this.xVel = 2;
                this.yVel = 2;
                changeVel = false;
                changeVelTimer = 0;
                this.remainingTime = 10; 
            }
        }

        //Moving
        //Moves only once in whole tile
        if(!this.isMoving){
            this.collide = false;

            //Set sprite for each direction
            if(this.moveLeft){
                this.direction = "left";
                this.sprite = this.wizard0;
            }
            else if(this.moveRight){
                this.direction = "right";
                this.sprite = this.wizard1;
            }
            else if(this.moveUp){
                this.direction = "up";
                this.sprite = wizard2;
            }
            else if(this.moveDown){
                this.direction = "down";
                this.sprite = wizard3;
            }

            checkCollision(this.bricks, this.direction);
            checkCollision(this.stones, this.direction);

            //If moving in direction does not result in collision, move in direction and set wizard moving boolean to true
            if(!this.collide){
                if(this.moveLeft){
                    this.x -= xVel;
                    this.setIsMoving();
                    this.moveLeftWholeTile = true;
                    this.moveRightWholeTile = false;
                    this.moveUpWholeTile = false;
                    this.moveDownWholeTile = false;
                }
                else if(this.moveRight){
                    this.x += xVel;
                    this.setIsMoving();
                    this.moveLeftWholeTile = false;
                    this.moveRightWholeTile = true;
                    this.moveUpWholeTile = false;
                    this.moveDownWholeTile = false;
                }
                else if(this.moveUp){
                    this.y -= yVel;
                    this.setIsMoving();
                    this.moveLeftWholeTile = false;
                    this.moveRightWholeTile = false;
                    this.moveUpWholeTile = true;
                    this.moveDownWholeTile = false;
                }
                else if(this.moveDown){
                    this.y += yVel;
                    this.setIsMoving();
                    this.moveLeftWholeTile = false;
                    this.moveRightWholeTile = false;
                    this.moveUpWholeTile = false;
                    this.moveDownWholeTile = true;}
            }
        }

        //If wizard is already moving, cannot change direction until whole tile is reached
        if(this.isMoving){
            if(!this.changeVel){
                ReachWholeTileNormal();
            }
            if(this.changeVel){
                ReachWholeTileWithPowerUp();
            }
        }

        // Collision with Gremlins and Slimes
        checkAlive(this.gremlins);
        checkAlive(this.slimes);

        // Shooting fireball and freezeball
        timerFireball++;
        timerFreezeball++;
        //Shoot fireball and freezeball only after cooldown
        if(this.shootFireball && timerFireball >= cooldown*60){
            this.drawRectFireball = true;
            addFireball();
            timerFireball = 0;
            timerRectFireball = 0;
        }
        else if(this.shootFreezeball && timerFreezeball >= cooldown*60){
            this.drawRectFreezeball = true;
            addFreezeball();                                                      
            timerFreezeball = 0;
            timerRectFreezeball = 0;
        }
        //Remove collided fireballs and freezeballs
        checkDeadFireball();
        checkDeadFreezeball();
        
        //For cooldown bar
        timerRectFireball += Math.ceil(220.0 / Math.round(cooldown*60));           
        timerRectFreezeball += Math.ceil(220.0 / Math.round(cooldown*60));          
        if(timerFireball >= cooldown*60 && !this.shootFireball){
            this.drawRectFireball = false;
            timerRectFireball = 0;
        }
        if(timerFreezeball >= cooldown*60 && !this.shootFreezeball){
            this.drawRectFreezeball = false;
            timerRectFreezeball = 0;
        } 

        //Adding removed bricks to airTiles
        checkDeadBrick();

        //For powerup
        //First powerup needs delay
        if(this.firstPowerUp){
            if(this.removefirst){
                int firstpowerUpx = powerups.get(0).getX();
                int firstpowerUpy = powerups.get(0).getY();
                this.powerUpFirst = new PowerUp(firstpowerUpx, firstpowerUpy, this.powerUp, this.powerUp1, this.powerUp2, this.powerUp3);
                firstpowerupRemove();
                this.removefirst = false;
            }
            if(initialTimer == 10*60){
                firstpowerupAdd();
                this.firstPowerUp = false;
            }
            initialTimer++;
        }
        if(!this.firstPowerUp){
            reachedPowerUp();
            if(this.powerUpReached){
                this.drawPowerUp = true;
                int randtime = randomGenerator.nextInt(5) + 10; //Between 10 - 15 secs
                if(this.powerUpTimer >= randtime*60){
                    powerUpAdd();
                    this.powerUpReached = false;
                    this.drawPowerUp = false;
                    powerUpTimer = 0;
                }
                powerUpTimer++;
            }
        }   
    }


    //getter methods
    public int getLives(){
        return this.lives;
    }
    public boolean isAlive(){
        return this.alive;
    }
    public boolean getIsMoving(){
        return this.isMoving;
    }
    public double getCooldown(){
        return this.cooldown;
    }
    public void changeVel(){
        this.changeVel = true;
    }
    public boolean getdrawRectFireball(){
        return this.drawRectFireball;
    }
    public boolean getdrawRectFreezeball(){
        return this.drawRectFreezeball;
    }
    public int getTimerRectFireball(){
        return this.timerRectFireball;
    }
    public int getTimerRectFreezeball(){
        return this.timerRectFreezeball;
    }
    public boolean getdrawPowerUp(){
        return this.drawPowerUp;
    }
    public int getPowerUpTimer(){
        return this.remainingTime;
    }

    //setter methods
    public void setLives(int lives){
        this.lives = lives;
    }
    public void setCooldown(double cooldown){
        this.cooldown = cooldown;
    }
    public void setIsMoving(){
        this.isMoving = true;
    }
    public void setShootFireball(){
        this.shootFireball = true;
    }
    public void stopShootFireball(){
        this.shootFireball = false;
    }
    public void setShootFreezeball(){
        this.shootFreezeball = true;
    }
    public void stopShootFreezeball(){
        this.shootFreezeball = false;
    }
    public void setGremlins(ArrayList<Gremlin> gremlins){
        this.gremlins = gremlins;
    }
    public void setSlimes(ArrayList<Slime> slimes){
        this.slimes = slimes;
    }
    public void setFireballs(ArrayList<Fireball> fireballs){
        this.fireballs = fireballs;
    }
    public void setFreezeballs(ArrayList<Freeze> freezeballs){
        this.freezeballs = freezeballs;
    }
    public void setAirTiles(ArrayList<Air> airTiles){
        this.airTiles = airTiles;
    }
    public void setPowerUps(ArrayList<PowerUp> powerups){
        this.powerups = powerups;
    }
    public void setMoveLeft(){
        this.moveLeft = true;
    }
    public void setMoveRight(){
        this.moveRight = true;
    }
    public void setMoveUp(){
        this.moveUp = true;
    }
    public void setMoveDown(){
        this.moveDown = true;
    }
    public void stopMoveLeft(){
        this.moveLeft = false;
    }
    public void stopMoveRight(){
        this.moveRight = false;
    }
    public void stopMoveUp(){
        this.moveUp = false;
    }
    public void stopMoveDown(){
        this.moveDown = false;
    }
    

    //helper methods
    //Method for reaching whole tile if normal velocity
    private void ReachWholeTileNormal(){
        pixelCounter += xVel;
        if(this.moveLeftWholeTile){this.x -= xVel;}
        else if(this.moveRightWholeTile){this.x += xVel;}
        else if(this.moveUpWholeTile){this.y -= yVel;}
        else if(this.moveDownWholeTile){this.y += yVel;}
        if(pixelCounter == 18){ 
            this.isMoving = false;
            pixelCounter = 0;
        }
    }
    //Method for reaching whole tile if powerup velocity
    private void ReachWholeTileWithPowerUp(){
        pixelCounter += xVel;
        if(this.moveLeftWholeTile){this.x -= xVel;}
        else if(this.moveRightWholeTile){this.x += xVel;}
        else if(this.moveUpWholeTile){this.y -= yVel;}
        else if(this.moveDownWholeTile){this.y += yVel;}
        if(pixelCounter == 16){ 
            this.isMoving = false;
            pixelCounter = 0;
        }
    }
    //Method for adding fireballs and storing necessary game objects into fireball 
    private void addFireball(){
        if(this.shootFireball){
            this.fireballs.add(new Fireball(this.x, this.y, this.fireballPimage));
            this.fireballs.get(fireballs.size() - 1).setDirection(this.direction);
            this.fireballs.get(fireballs.size() - 1).setBricks(this.bricks);
            this.fireballs.get(fireballs.size() - 1).setStones(this.stones);
            this.fireballs.get(fireballs.size() - 1).setSlimes(this.slimes);
        }
    }
    //Method for adding freezeballs and storing necessary game objects into freezeball 
    private void addFreezeball(){
        if(this.shootFreezeball){
            this.freezeballs.add(new Freeze(this.x, this.y, this.freezeballPimage));
            this.freezeballs.get(freezeballs.size() - 1).setDirection(this.direction);
            this.freezeballs.get(freezeballs.size() - 1).setBricks(this.bricks);
            this.freezeballs.get(freezeballs.size() - 1).setStones(this.stones);
            this.freezeballs.get(freezeballs.size() - 1).setSlimes(this.slimes);
        }
    }
    //Removing fireballs that have collided with something
    private void checkDeadFireball(){
        Iterator<Fireball> itr = this.fireballs.iterator();
        while(itr.hasNext()){
            Fireball fireball = itr.next();
            if(!fireball.getAlive()){
                itr.remove();
            }
        }
    }
    //Removing freezeballs that have collided with something
    private void checkDeadFreezeball(){
        Iterator<Freeze> itr = this.freezeballs.iterator();
        while(itr.hasNext()){
            Freeze freezeball = itr.next();
            if(!freezeball.getAlive()){
                itr.remove();
            }
        }
    }   
    //Removing bricks that have been destroyed by fireball and adding coordinates to airTiles
    private void checkDeadBrick(){
        Iterator<Brick> itr = this.bricks.iterator();
        ListIterator<Air> itr2 = this.airTiles.listIterator();
        while(itr.hasNext()){
            Brick brick = itr.next();
            int brickx = brick.getX();
            int bricky = brick.getY();
            if(brick.getSwitchToAirTiles()){
                itr2.add(new Air(brickx, bricky, this.air)); 
                itr.remove();
            }
        }
    }
    //Used to determine if wizard moves on powerup 
    private void reachedPowerUp(){
        Iterator<PowerUp> itr = powerups.iterator();
        while(itr.hasNext()){
            PowerUp powerup = itr.next();
            if(this.x == powerup.getX() && this.y == powerup.getY()){
                itr.remove();
                this.changeVel = true;
                this.powerUpReached = true;
            }
        }
    }
    //Adds powerup
    private void powerUpAdd(){
        int chooserand = randomGenerator.nextInt(airTiles.size());
        Air nextPowerUpPos = airTiles.get(chooserand);
        int nextPowerUpx = nextPowerUpPos.getX();
        int nextPowerUpy = nextPowerUpPos.getY();

        ListIterator<PowerUp> itr2 = this.powerups.listIterator();
        itr2.add(new PowerUp(nextPowerUpx, nextPowerUpy, this.powerUp, this.powerUp1, this.powerUp2, this.powerUp3)); 
    }
    //Below two methods used to ensure first powerup does not load immediately with map but after 10 sec delay
    private void firstpowerupRemove(){
        Iterator<PowerUp> itr = powerups.iterator();
        while(itr.hasNext()){
            PowerUp powerup = itr.next();
            itr.remove();
        }
    }
    private void firstpowerupAdd(){
        ListIterator<PowerUp> itr2 = this.powerups.listIterator();
        itr2.add(this.powerUpFirst); 
    }
}