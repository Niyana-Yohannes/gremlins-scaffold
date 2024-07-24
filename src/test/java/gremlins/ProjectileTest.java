package gremlins;

import org.junit.jupiter.api.*;

import processing.core.PApplet;
import processing.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;


public class ProjectileTest {

    private static App app;

    @BeforeAll
    public static void setWizard(){
        app = new App();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(6000);
        app.loop();
    }
    @Test
    public void shootFreezeballCollide(){
        app.wizard.setShootFreezeball();
        app.wizard.tick();
        for(Freeze f: app.freezeballs) {
            f.collide = true;
            f.tick();
            assertEquals(true, f.collide);
        }
    }
    @Test
    public void shootFireballCollide(){
        app.wizard.direction = "down";
        app.wizard.tick();
        app.wizard.setShootFireball();
        app.wizard.tick();
        for(Fireball f: app.fireballs) {
            f.tick();
            assertEquals(true, f.collide);
        }
    }
    @Test
    public void brickDestructionAnimation(){
        ArrayList<Brick> bricks = new ArrayList<Brick>();
        bricks.add(new Brick(40,40,app.brickwall,app.brickwall_destroyed0,app.brickwall_destroyed1,app.brickwall_destroyed2,app.brickwall_destroyed3));
        app.wizard.setBricks(bricks);
        app.wizard.tick();
        for(Brick b: app.bricks){
            b.setAlive();
            b.tick();
        }
        for(Brick b: app.bricks){
            assertEquals(b.sprite, app.brickwall_destroyed0);
        }
        int i = 0;
        while (i <= 16){
            for(Brick b: app.bricks){
                b.tick();
                if(i==4){
                    assertEquals(app.brickwall_destroyed1,b.sprite);
                }
                if(i==8){
                    assertEquals(app.brickwall_destroyed2,b.sprite);
                }
                if(i==12){
                    assertEquals(app.brickwall_destroyed3,b.sprite);
                }
            }
            i++;
        }
    }

}