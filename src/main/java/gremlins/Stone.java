package gremlins;

import processing.core.PImage;

public class Stone extends Tiles{

    public Stone(int x, int y, PImage sprite) {
        super(x, y, sprite);
    }
    
    /**
     * Does nothing as stones are  set
     */
    public void tick() {
        //Do nothing for stones, will always remain the same
    }
}
