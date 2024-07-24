package gremlins;

import processing.core.PImage;

public class Air extends Tiles {
    
    public Air(int x, int y, PImage sprite) {
        super(x, y, sprite);
    }
    
    /**
     * Does nothing as air tiles are set
     */
    public void tick() {
        //Do nothing for Air, will always remain the same
    }

}
