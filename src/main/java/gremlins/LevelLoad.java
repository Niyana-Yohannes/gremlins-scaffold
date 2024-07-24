package gremlins;

import processing.core.PImage;

import java.util.ArrayList;

public class LevelLoad {

    private int Level;

    private String configPath;

    private ArrayList<Stone> stones;
    private ArrayList<Brick> bricks; 
    private ExitDoor exitTile;
    private ArrayList<Air> airTiles;
    private ArrayList<Gremlin> gremlins;
    private Wizard wizard;
    private ArrayList<PowerUp> powerups;
    private ArrayList<Fireball> fireballs;
    private ArrayList<Slime> slimes;
    private ArrayList<Freeze> freezeballs;
    private ArrayList<Wizard> wizLives;

    private PImage stonewall;
    private PImage brickwall;
    private PImage brickwall_destroyed0;
    private PImage brickwall_destroyed1;
    private PImage brickwall_destroyed2;
    private PImage brickwall_destroyed3;
    private PImage exitDoor;
    private PImage air;
    private PImage wizard0;
    private PImage wizard1; 
    private PImage wizard2;
    private PImage wizard3;
    private PImage gremlin;
    private PImage fireball;
    private PImage slime;
    private PImage freezeball;
    private PImage powerupImage;
    private PImage powerupImage1;
    private PImage powerupImage2;
    private PImage powerupImage3;

    public LevelLoad(int Level, String configPath, PImage stonewall, PImage brickwall, PImage gremlin, PImage wizard1, PImage exitDoor, PImage fireball, PImage slime, PImage freezeball, PImage brickwall_destroyed0, PImage brickwall_destroyed1, PImage brickwall_destroyed2, PImage brickwall_destroyed3, PImage powerupImage, PImage air, PImage wizard0, PImage wizard2, PImage wizard3, PImage powerupImage1, PImage powerupImage2, PImage powerupImage3){
        this.Level = Level;
        this.configPath = configPath;

        this.stones = new ArrayList<Stone>();
        this.bricks = new ArrayList<Brick>();
        this.airTiles = new ArrayList<Air>();
        this.gremlins = new ArrayList<Gremlin>();
        this.wizLives = new ArrayList<Wizard>(); 
        this.fireballs = new ArrayList<Fireball>();
        this.freezeballs = new ArrayList<Freeze>();
        this.slimes = new ArrayList<Slime>();
        this.powerups = new ArrayList<PowerUp>();
        this.exitTile = null;
        this.wizard = null;

        this.stonewall = stonewall;
        this.brickwall = brickwall;
        this.brickwall_destroyed0 = brickwall_destroyed0;
        this.brickwall_destroyed1 = brickwall_destroyed1;
        this.brickwall_destroyed2 = brickwall_destroyed2;
        this.brickwall_destroyed3 = brickwall_destroyed3;
        this.exitDoor = exitDoor;
        this.air = air;
        this.gremlin = gremlin;
        this.wizard0 = wizard0;
        this.wizard1 = wizard1;
        this.wizard2 = wizard2;
        this.wizard3 = wizard3;
        this.fireball = fireball;
        this.freezeball = freezeball;
        this.slime = slime;
        this.powerupImage = powerupImage;
        this.powerupImage1 = powerupImage1;
        this.powerupImage2 = powerupImage2;
        this.powerupImage3 = powerupImage3;
    }

    //Getter Methods
    /**
     * Returns its reference to an arraylist of stones
     * @return ArrayList of type Stone
     */
    public ArrayList<Stone> getStones(){
        return this.stones;
    }
    /**
     * Returns its reference to an arraylist of bricks
     * @return ArrayList of type Brick
     */
    public ArrayList<Brick> getBricks(){
        return this.bricks;

    }
    /**
     * Returns its reference to an ExitDoor type
     * @return exitTile of type ExitDoor 
     */
    public ExitDoor getExitTile(){
        return this.exitTile;
    }
    /**
     * Returns its reference to an arraylist of airTiles
     * @return ArrayList of type Air
     */
    public ArrayList<Air> getAirTiles(){
        return this.airTiles;
    }
    /**
     * Returns its reference to an arraylist of gremlins
     * @return ArrayList of type Gremlin
     */
    public ArrayList<Gremlin> getGremlins(){
        return this.gremlins;

    }
    /**
     * Returns its reference to a Wizard type
     * @return object of type Wizard
     */
    public Wizard getWizard(){
        return this.wizard;
    }
    /**
     * Returns its reference to an arraylist of wizard used to display wizard lives
     * @return ArrayList of type Wizard
     */
    public ArrayList<Wizard> getWizLives(){
        return this.wizLives;

    }
    /**
     * Returns its reference to an arraylist of fireball
     * @return ArrayList of type Fireball
     */
    public ArrayList<Fireball> getFireballs(){
        return this.fireballs;
    }
    /**
     * Returns its reference to an arraylist of freezeballs
     * @return ArrayList of type Freeze
     */
    public ArrayList<Freeze> getFreezeballs(){
        return this.freezeballs;
    }
    /**
     * Returns its reference to an arraylist of slimes
     * @return ArrayList of type Slime
     */
    public ArrayList<Slime> getSlimes(){
        return this.slimes;
    }
    /**
     * Returns its reference to an arraylist of powerups
     * @return ArrayList of type PowerUp
     */
    public ArrayList<PowerUp> getPowerUps(){
        return this.powerups;
    }

    /**
     * Reads the config file and stores game objects for specified level
     */
    public void Load(){
        //Read Config file
        Config newConfig = new Config(this.configPath);
        
        //Store levels & lives
        ArrayList<String[][]> Maps = newConfig.getMap();
        String[] wiz_cooldowns = newConfig.getWizardCooldown(); 
        String[] enemy_cooldowns = newConfig.getEnemyCooldown();
        int wizardLives = newConfig.getLives();

        //stores map for specified level
        storeMap(Maps);
        //sets wizard cooldown for specified level
        wizard.setCooldown(Double.parseDouble(wiz_cooldowns[this.Level-1]));
        //sets enemy cooldown for specified level
        for(Gremlin g: gremlins){
            g.setCooldown(Double.parseDouble(enemy_cooldowns[this.Level-1]));
        } 
        //sets lives for wizard (same for both levels)
        wizard.setLives(wizardLives);
        //sets wizard lives display for specified level, Wizards that show how many lives (have 1 life since just display)
        for(int i = 0; i < wizardLives; i++){ 
            wizLives.add(new Wizard((i+5)*20, 34*20, wizard1, fireball, freezeball, air, powerupImage, wizard0, wizard2, wizard3, this.powerupImage1, this.powerupImage2, this.powerupImage3)); 
        }

        //Adding references of game objects to required game objects
        wizard.setBricks(this.bricks);
        wizard.setStones(this.stones);
        wizard.setGremlins(this.gremlins);
        wizard.setSlimes(this.slimes);
        wizard.setFireballs(this.fireballs);
        wizard.setFreezeballs(this.freezeballs);
        wizard.setAirTiles(this.airTiles);
        wizard.setPowerUps(this.powerups);
        for(Gremlin g : this.gremlins){
            g.setBricks(this.bricks);
            g.setStones(this.stones);
            g.setAirTiles(this.airTiles);
            g.setWizard(this.wizard);
            g.setFireballs(this.fireballs);
            g.setFreezeballs(this.freezeballs);
            g.setSlimes(this.slimes);
        }
        exitTile.setWizard(this.wizard);
    }

    //Helper methods
    //Using the level, chooses the map and loops through contents, creating game object of respective types
    //Raises an exception if file is invalid
    private void storeMap(ArrayList<String[][]> Maps){
        String[][] mapcontents = Maps.get(this.Level-1); // level 1 = index 0, level 2 = index 1, etc...
        try{
            for (int i = 0; i < mapcontents.length; i++){
                for(int j = 0; j < mapcontents[i].length; j++){
                    if(mapcontents[i][j].equals("X")){stones.add(new Stone(j*20, i*20, stonewall));}
                    else if(mapcontents[i][j].equals("B")){bricks.add(new Brick(j*20, i*20, brickwall, brickwall_destroyed0, brickwall_destroyed1, brickwall_destroyed2, brickwall_destroyed3));}
                    else if(mapcontents[i][j].equals("E")){exitTile = new ExitDoor(j*20, i*20, exitDoor);}
                    else if(mapcontents[i][j].equals(" ")){airTiles.add(new Air(j*20, i*20, air));} 
                    else if(mapcontents[i][j].equals("W")){wizard = new Wizard(j*20, i*20, wizard1, fireball, freezeball, air, powerupImage, wizard0, wizard2, wizard3, this.powerupImage1, this.powerupImage2, this.powerupImage3);} 
                    else if(mapcontents[i][j].equals("P")){powerups.add(new PowerUp(j*20, i*20, powerupImage, powerupImage1, powerupImage2, powerupImage3 ));}
                    else if(mapcontents[i][j].equals("G")){gremlins.add(new Gremlin(j*20, i*20, gremlin, slime));}
                    else{throw new Exception("File has unknown character");} //Unknown character in map, raise an error
                }
            }
            if(this.exitTile == null || this.wizard == null){
                throw new Exception("File missing starting point or exit");
            }
        }
        catch (Exception e){
            e.getMessage();
            e.printStackTrace();
        }
    }
}

