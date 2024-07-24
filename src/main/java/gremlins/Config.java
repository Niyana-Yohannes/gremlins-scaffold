package gremlins;

import processing.core.PApplet;
import processing.data.JSONArray;
import processing.data.JSONObject;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Config {

    private String configPath;
    
    public Config(String configPath){
        this.configPath = configPath;
    }

    /**
     * Retruns name of config path
     * @return String: name of config path
     */
    public String getConfigPath(){
        return this.configPath;
    }

    /**
     * Stores a map into a 2d array
     * @return ArrayList of type String[][]: ArrayList of 2d arrays. Stores multiple maps
     */
    //Currently 3 name:value pairs so map at index 0 & 0+3 
    public ArrayList<String[][]> getMap(){

        ArrayList<String> configArrayList = getLevel(this.configPath);
        String[] mapFiles = {configArrayList.get(0), configArrayList.get(3)};
        ArrayList<String[][]> retMap = new ArrayList<String[][]>();

        //Store map in 2d array 
        for (String str : mapFiles){
            String filename = str;
            File f = new File(filename);
            String[][] storemapchar = new String[33][36];
            Scanner inputStream = null;
            try{
                inputStream = new Scanner(f);
            }
            catch(FileNotFoundException e){ 
                System.out.println("Error opening the file " + filename); 
            }
            int i = 0;
            while (inputStream.hasNextLine()){
                String line = inputStream.nextLine();
                String[] intermedschar = line.split("");
                for(int j=0; j<storemapchar[i].length; j++){
                    storemapchar[i][j] = intermedschar[j];

                }         
                i++;
            }
            //Add 2d array of map for each level into retMap
            retMap.add(storemapchar); 
        }
        return retMap;  
    }
    
    /**
     * Reads congif file to return the wizard cooldown 
     * @return double: wizard cooldown
     */
    public String[] getWizardCooldown(){
        ArrayList<String> configArrayList = getLevel(this.configPath);
        String[] wizcooldown = {configArrayList.get(1), configArrayList.get(4)}; //CHANGE IF EXTENSION
        return wizcooldown;
    }

    /**
     * Reads confif file to return the enemy cooldown
     * @return double: enemy cooldown
     */
    public String[] getEnemyCooldown(){
        ArrayList<String> configArrayList = getLevel(this.configPath);
        String[] enemycooldown = {configArrayList.get(2), configArrayList.get(5)}; //CHANGE IF EXTENSION
        return enemycooldown;
    }

    /**
     * Reads config file to return wizard lives
     * @return lives: wizard lives
     */
    public int getLives(){
        int Lives = getLives(this.configPath);
        return Lives;
    }

    //private helper methods
    private ArrayList<String> getLevel(String path){
        JSONObject conf = PApplet.loadJSONObject(new File(path));
        JSONArray levels = conf.getJSONArray("levels");
        ArrayList<String> levelsdata = new ArrayList<String>();
        for (int i = 0; i < levels.size(); i++) {
            JSONObject levelsx = levels.getJSONObject(i);
            levelsdata.add(levelsx.getString("layout"));
            levelsdata.add(String.valueOf(levelsx.getDouble("wizard_cooldown")));
            levelsdata.add(String.valueOf(levelsx.getDouble("enemy_cooldown")));
        }
        return levelsdata;
    }
    //Gets wizard lives
    private int getLives(String path){
        JSONObject conf = PApplet.loadJSONObject(new File(path));
        int Lives = conf.getInt("lives");
        return Lives;
    }
    
}

