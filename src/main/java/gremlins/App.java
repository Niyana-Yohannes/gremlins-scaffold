package gremlins;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PFont;

import java.util.ArrayList;

public class App extends PApplet {

    public static final int WIDTH = 720;
    public static final int HEIGHT = 720;

    public static final int FPS = 60;

    public String configPath;
    
    //PImage for game objects (air, fireball, slime, powerup, extension:freeze gremlins )
    public PImage brickwall;
    public PImage stonewall;
    public PImage exitwall;
    public PImage air;
    public PImage wizard0;
    public PImage wizard1;
    public PImage wizard2;
    public PImage wizard3;
    public PImage gremlin;
    public PImage fireball;
    public PImage slime; 
    public PImage freeze;
    public PImage brickwall_destroyed0;
    public PImage brickwall_destroyed1;
    public PImage brickwall_destroyed2;
    public PImage brickwall_destroyed3;
    public PImage powerupImage;
    public PImage powerupImage1;
    public PImage powerupImage2;
    public PImage powerupImage3;


    //write on map
    private PFont f;

    //load each level
    public LevelLoad levelOne;
    public LevelLoad levelTwo;

    //game states
    public GameState levelOneState;
    public GameState levelTwoState;
    public GameState loseGameState;
    public GameState winGameState;
    public GameState currGameState;
    public int nextState;

    //storing game objects
    public ArrayList<Stone> stones;
    public ArrayList<Brick> bricks; 
    public ExitDoor exitTile;
    public ArrayList<Air> airTiles;
    public ArrayList<Gremlin> gremlins;
    public Wizard wizard;
    public ArrayList<Wizard> livesDispWizards;
    public ArrayList<Fireball> fireballs;
    public ArrayList<Slime> slimes;
    public ArrayList<Freeze> freezeballs;
    public ArrayList<PowerUp> powerups;


    public App() {
        this.configPath = "config.json";
    }

    /**
     * Initialise the setting of the window size.
    */
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player, enemies and map elements.
    */
    public void setup() {
        frameRate(FPS);

        //Load images during setup
        this.stonewall = loadImage(this.getClass().getResource("stonewall.png").getPath().replace("%20", " "));
        this.brickwall = loadImage(this.getClass().getResource("brickwall.png").getPath().replace("%20", " "));
        this.brickwall_destroyed0 = loadImage(this.getClass().getResource("brickwall_destroyed0.png").getPath().replace("%20", " "));
        this.brickwall_destroyed1 = loadImage(this.getClass().getResource("brickwall_destroyed1.png").getPath().replace("%20", " "));
        this.brickwall_destroyed2 = loadImage(this.getClass().getResource("brickwall_destroyed2.png").getPath().replace("%20", " "));
        this.brickwall_destroyed3 = loadImage(this.getClass().getResource("brickwall_destroyed3.png").getPath().replace("%20", " "));
        this.exitwall = loadImage(this.getClass().getResource("Door.png").getPath().replace("%20", " "));
        this.air = loadImage(this.getClass().getResource("airTile.png").getPath().replace("%20", " "));
        this.gremlin = loadImage(this.getClass().getResource("gremlin.png").getPath().replace("%20", " "));
        this.wizard0 = loadImage(this.getClass().getResource("wizard0.png").getPath().replace("%20", " "));
        this.wizard1 = loadImage(this.getClass().getResource("wizard1.png").getPath().replace("%20", " "));
        this.wizard2 = loadImage(this.getClass().getResource("wizard2.png").getPath().replace("%20", " "));
        this.wizard3 = loadImage(this.getClass().getResource("wizard3.png").getPath().replace("%20", " "));
        this.slime = loadImage(this.getClass().getResource("slime.png").getPath().replace("%20", " "));
        this.fireball = loadImage(this.getClass().getResource("fireball.png").getPath().replace("%20", " "));
        this.freeze = loadImage(this.getClass().getResource("freezeball.png").getPath().replace("%20", " "));
        this.powerupImage = loadImage(this.getClass().getResource("lightning.png").getPath().replace("%20", " "));
        this.powerupImage1 = loadImage(this.getClass().getResource("lightning1.png").getPath().replace("%20", " "));
        this.powerupImage2 = loadImage(this.getClass().getResource("lightning2.png").getPath().replace("%20", " "));
        this.powerupImage3 = loadImage(this.getClass().getResource("lightning3.png").getPath().replace("%20", " "));
        
        
        f = createFont("Arial",16);

        levelOneState = GameState.LevelOneState;
        levelTwoState = GameState.LevelTwoState;
        winGameState = GameState.WinGameState;
        loseGameState = GameState.LoseGameState;

        currGameState = levelOneState;
    
    }

    /**
     * Receive key pressed signal from the keyboard.
    */
    public void keyPressed(){
        if(this.keyCode == 37 ){ 
            wizard.setMoveLeft();
        }
        if(this.keyCode == 38 ){
            wizard.setMoveUp();
        }
        if(this.keyCode == 39 ){
            wizard.setMoveRight();
        }
        if(this.keyCode == 40 ){
            wizard.setMoveDown();
        }
        if(this.keyCode == 32){
            wizard.setShootFireball();
        }
        if(this.keyCode == 16){
            wizard.setShootFreezeball();
        }
    }
    
    /**
     * Receive key released signal from the keyboard.
    */
    public void keyReleased(){
        if(this.keyCode ==32){
            wizard.stopShootFireball();
        }
        if(this.keyCode == 16){
            wizard.stopShootFreezeball();
        }
        if(this.keyCode == 37 ){ 
            wizard.stopMoveLeft();
        }
        if(this.keyCode == 38 ){
            wizard.stopMoveUp();
        }
        if(this.keyCode == 39 ){
            wizard.stopMoveRight();
        }
        if(this.keyCode == 40 ){
            wizard.stopMoveDown();
        }
    }


    /**
     * Draw all elements in the game by current frame. 
	 */
    public void draw() {
        background(200,157,124);
        textFont(f,24);  
        fill(255,255,255);
        text("Lives: ", 1*20, 35*20);
        text("Level: " + currGameState.getlevel() + "/2", 9*20, 35*20);
        
        if(currGameState == levelOneState){
            //Initializing game objects
            if(currGameState.getreset() == 1){
                resetLevel1();
            }
            //Ticking and drawing
            tickdraw();
            //Cooldown Bars
            fill(255,255,255);
            rect(23*20, 45*15, 11*20, 10);
            if(wizard.getdrawRectFireball()){ 
                fill(255,192,203);
                rect(23*20, 45*15, wizard.getTimerRectFireball(), 10);
            }
            fill(255,255,255);
            rect(23*20, 46*15, 11*20, 10);
            if(wizard.getdrawRectFreezeball()){
                fill(173,216,230);
                rect(23*20, 46*15, wizard.getTimerRectFreezeball(), 10); 
            }
            //PowerUp Timer
            if(wizard.getdrawPowerUp()){
                textFont(f,24); 
                fill(0,0,0);
                text("SPEED: " + (wizard.getPowerUpTimer()), 16*20, 35*20);
            }
            //Updating game state
            nextState = levelOneState.GameStateUpdate();
            getCurrGameState(nextState);  
        }


        if(currGameState == levelTwoState){
            //Initializing game objects
            if(currGameState.getreset() == 1){
                resetLevel2();
            }
            //Ticking and drawing
            tickdraw();
            //Cooldown Bars
            fill(255,255,255);
            rect(23*20, 45*15, 12*20, 10);
            if(wizard.getdrawRectFireball()){ //
                fill(255,192,203);
                rect(23*20, 45*15, wizard.getTimerRectFireball(), 10);
            }
            fill(255,255,255);
            rect(23*20, 46*15, 12*20, 10);
            if(wizard.getdrawRectFreezeball()){
                fill(173,216,230);
                rect(23*20, 46*15, wizard.getTimerRectFreezeball(), 10); 
            }
            //PowerUp Timer
            if(wizard.getdrawPowerUp()){
                textFont(f,24); 
                fill(0,0,0); 
                text("SPEED: " + (wizard.getPowerUpTimer()), 16*20, 35*20); 
            }
            //Updating game state
            nextState = levelTwoState.GameStateUpdate();
            getCurrGameState(nextState); 
        }

        if(currGameState == winGameState){
            background(200,157,124);
            textFont(f,60);  
            text("You Win", 11*20, 18*20); 
            if(keyPressed){
                currGameState.setkeyPressed();
            }
            levelOneState.setfirstload(0);
            nextState = winGameState.GameStateUpdate();
            getCurrGameState(nextState); 
        }

        if(currGameState == loseGameState){
            background(200,157,124);
            textFont(f,60);  
            text("Game Over", 10*20, 18*20); 
            if(keyPressed){
                currGameState.setkeyPressed();
            }
            levelOneState.setfirstload(0);
            nextState = loseGameState.GameStateUpdate();
            getCurrGameState(nextState); 
        }
    }

    //Helper Methods
    private void tickdraw(){
        //Tick
        wizard.tick();
        for(Gremlin g: this.gremlins){
            g.tick();
        }
        exitTile.tick();
        for(Slime slime : this.slimes){
            slime.tick();
        }
        for(Fireball fireball : this.fireballs){
            fireball.tick();
        }
        for(Brick brick : this.bricks){
            brick.tick();
        }
        for(Freeze freezeball : this.freezeballs){
            freezeball.tick();
        }
        for(PowerUp powerup : this.powerups){
            powerup.tick();
        }
        //Draw
        for(Stone stone: this.stones){ 
            stone.draw(this);
        }
        for(Brick brick: this.bricks){
            brick.draw(this);
        }
        exitTile.draw(this); 
        for(Gremlin grem: this.gremlins){ 
            grem.draw(this);
        }
        wizard.draw(this);  
        for(Wizard wiz: this.livesDispWizards){ 
            wiz.draw(this);
        } 
        for(Slime slime : this.slimes){
            slime.draw(this);
        }
        for(Fireball fireball : this.fireballs){
            fireball.draw(this);
        }
        for(Freeze freezeball : this.freezeballs){
            freezeball.draw(this);
        }
        for(PowerUp powerup: this.powerups){
            powerup.draw(this);
        }
        
    }

 
    private void resetLevel1(){
        levelOne = new LevelLoad(1, this.configPath, this.stonewall, this.brickwall, this.gremlin, this.wizard1, this.exitwall, this.fireball, this.slime, this.freeze, this.brickwall_destroyed0, this.brickwall_destroyed1, this.brickwall_destroyed2, this.brickwall_destroyed3, this.powerupImage, this.air, this.wizard0, this.wizard2, this.wizard3, this.powerupImage1, this.powerupImage2, this.powerupImage3);
        levelOne.Load();
        this.stones = levelOne.getStones();
        this.bricks = levelOne.getBricks();
        this.exitTile = levelOne.getExitTile();
        this.airTiles = levelOne.getAirTiles();
        this.gremlins = levelOne.getGremlins();
        this.wizard = levelOne.getWizard(); 
        this.livesDispWizards = levelOne.getWizLives(); 
        this.fireballs = levelOne.getFireballs();
        this.slimes = levelOne.getSlimes();
        this.freezeballs = levelOne.getFreezeballs();
        this.powerups = levelOne.getPowerUps();
        //If not first load of level, decrementing lives and lives display
        if(currGameState.getfirstload() != 0){
            for(int i=0; i<currGameState.getfirstload(); i++){
                wizard.setLives(wizard.getLives() - 1);
                livesDispWizards.remove(livesDispWizards.size()-1); 
            } 
        }
        currGameState.setfirstload(currGameState.getfirstload()+1);
        currGameState.setreset(0);
        //Setting game objects to game class
        currGameState.setGameObjects(wizard, exitTile);
    }

    private void resetLevel2(){
        levelTwo = new LevelLoad(2, this.configPath, this.stonewall, this.brickwall, this.gremlin, this.wizard1, this.exitwall, this.fireball, this.slime, this.freeze, this.brickwall_destroyed0, this.brickwall_destroyed1, this.brickwall_destroyed2, this.brickwall_destroyed3, this.powerupImage, this.air, this.wizard0, this.wizard2, this.wizard3, this.powerupImage1, this.powerupImage2, this.powerupImage3);
        levelTwo.Load();
        this.stones = levelTwo.getStones();
        this.bricks = levelTwo.getBricks();
        this.exitTile = levelTwo.getExitTile();
        this.airTiles = levelTwo.getAirTiles();
        this.gremlins = levelTwo.getGremlins();
        this.wizard = levelTwo.getWizard(); 
        this.livesDispWizards = levelTwo.getWizLives();
        this.fireballs = levelTwo.getFireballs();
        this.slimes = levelTwo.getSlimes();
        this.freezeballs = levelTwo.getFreezeballs();
        this.powerups = levelTwo.getPowerUps();
        //Used to keep wizard life from level1
        if(currGameState.getfirstload() == 0){
            currGameState.setfirstload(levelOneState.getfirstload()-1);
        }
        //If not first load of level, decrementing lives and lives display
        if(currGameState.getfirstload() != 0){
            for(int i=0; i<currGameState.getfirstload(); i++){
                wizard.setLives(wizard.getLives() - 1); 
                livesDispWizards.remove(livesDispWizards.size()-1); 
            } 
        }
        currGameState.setfirstload(currGameState.getfirstload()+1);
        currGameState.setreset(0);
        //Setting game objects to game class
        currGameState.setGameObjects(wizard, exitTile);
    }

    //Used to determine nextgame state
    private void getCurrGameState(int nextState){
        if(nextState == 1){currGameState = levelOneState;}
        else if(nextState == 2){currGameState = levelTwoState;}
        else if(nextState == 3){currGameState = winGameState;}
        else if(nextState == 4){currGameState = loseGameState;}
        else{}//error
    }

    public static void main(String[] args) {
        PApplet.main("gremlins.App");
    }
}
