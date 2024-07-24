package gremlins;

import org.junit.jupiter.api.Test;

import processing.core.PApplet;
import processing.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    // General overall test of the App
    @Test
    public void testForFree() {
        App app = new App();
        app.noLoop();
        // Tell PApplet to create the worker threads for the program
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(500);
    }
    
}
