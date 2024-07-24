package gremlins;

import processing.core.PImage;

public class ExitDoor extends Tiles {

    //needs wizard
    private Wizard wizard;
    private boolean reached;

    public ExitDoor(int x, int y, PImage sprite) {
        super(x, y, sprite);
        this.reached = false;
    }
    
    /**
     * Checks if wizard game object has reached object, and if signals begins next level state or win game state
     */
    public void tick() {
        int wizardx = wizard.getX();
        int wizardy = wizard.getY();

        if(wizardx == this.x && wizardy == this.y){
            this.reached = true;
        }
    }

    /**
     * Sets reference to wizard object
     * @param wizard: wizard object;
     */
    public void setWizard(Wizard wizard){
        this.wizard = wizard;
    }
    /**
     * Checks if object has reached this object (x and y values are the same)
     * @return boolean: reached
     */
    public boolean isReached(){
        return this.reached;
    }
}
