package gremlins;

public enum  GameState {
    LevelOneState(1, 1, 0, false),
    LevelTwoState(2, 1, 0, false),
    WinGameState(2, 0, 0, false),
    LoseGameState(1, 0, 0, false);

    /**
     * Level of gamestate
     */
    protected int level;
    /**
     * Determines if game state needs to be reset
     */
    protected int reset;
    /**
     * Determines if game state is loaded once or more
     */
    protected int firstload;
    /**
     * Determines if a key is pressed
     */
    protected boolean keypressed;

    //storing game objects
    private ExitDoor exitTile;
    private Wizard wizard;

    GameState(int level, int reset, int firstload, boolean keypreseed){
        this.level = level;
        this.reset = reset;
        this.firstload = firstload;
        this.keypressed = keypressed;
    }

    public int getlevel(){
        return this.level;
    }
    public void setlevel(int level){
         this.level = level;
    }
    public int getreset(){
        return this.reset;
    }
    public void setreset(int reset){
         this.reset = reset;
    }
    public int getfirstload(){
        return this.firstload;
    }
    public void setfirstload(int firstload1){
         this.firstload = firstload1;
    }
    public void setkeyPressed(){
        this.keypressed = true;
    }
    public void setGameObjects(Wizard wizard, ExitDoor exitTile){
        this.wizard = wizard;
        this.exitTile = exitTile;
    }
    
    /**
     * Determines the next game state
     * @return int: 1 = LevelOneState, 2 = LevelTwoState, 3 = WinGameState, 4 = LoseGameState
     */
    public int GameStateUpdate(){
        switch(this){
            case LevelOneState: //Level1
                //ExitTile reached (while wizard has at least 1 life --> won't reach here though) Level2
                if(exitTile.isReached()){
                    this.reset = 1;
                    //this.firstload = 0;
                    return 2;
                }
                //Wizard lives reaches 0
                else if(wizard.getLives() == 0){
                    this.reset = 1;
                    this.firstload = 0;
                    return 4;
                }
                //You lose a life (at 0 will do above first)
                else if(!wizard.isAlive()){
                    this.reset = 1;
                    return 1;
                }
            case LevelTwoState: //Level2
                //Level 2 and ExitTile reached (while wizard has at least 1 life --> won't reach here though) Wingame
                if(exitTile.isReached()){
                    this.reset = 1;
                    this.firstload = 0;
                    return 3;
                }
                //Wizard lives reaches 0
                else if(wizard.getLives() == 0){
                    this.reset = 1;
                    this.firstload = 0;
                    return 4;
                }
                //You lose a life (at 0 will do above first)
                else if(!wizard.isAlive()){
                    this.reset = 1;
                    return 2;
    
                }
            case WinGameState: //Win game
                //When keypreseed return to main game (reset game)
                if(this.keypressed){
                    this.keypressed = false;
                    return 1;
                }
            case LoseGameState: //Lose game
            //When keypreseed return to main game (reset game)
                if(this.keypressed){
                    this.keypressed = false;
                    return 1;
                }
        }
        return 0; //If reaches here, error
    }
}



