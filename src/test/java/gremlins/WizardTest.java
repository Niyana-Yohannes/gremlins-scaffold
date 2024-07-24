package gremlins;

import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import processing.core.PApplet;
import processing.core.PImage;
import processing.event.KeyEvent;

import java.util.ArrayList;

public class WizardTest {

    private static App app;

    private int initX = 40;
    private int initY = 20;

    @BeforeAll
    public static void setWizard(){
        app = new App();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(6000);
        app.loop();
    }

    @Test
    public void LeftMovement() {
        app.wizard.stopMoveUp();
        app.wizard.stopMoveDown();
        app.wizard.stopMoveRight();
        int i = 0;
        while(i<19){
            app.wizard.tick();
            i++;
        }
        app.wizard.setX(initX);
        app.wizard.setY(initY);
        app.wizard.tick();
        app.wizard.setMoveLeft();
        app.wizard.tick();
        assertTrue(app.wizard.getX() < initX);
        assertTrue(app.wizard.getY() == initY);


    }
    @Test
    public void RightMovement(){
        app.wizard.stopMoveUp();
        app.wizard.stopMoveDown();
        app.wizard.stopMoveLeft();
        int i = 0;
        while(i<19){
            app.wizard.tick();
            i++;
        }
        app.wizard.setX(initX);
        app.wizard.setY(initY);
        app.wizard.tick();
        app.wizard.setMoveRight();
        app.wizard.tick();
        assertTrue(app.wizard.getX() > initX);
        assertTrue(app.wizard.getY() == initY);
    }
    @Test
    public void UpMovement(){
        app.wizard.stopMoveDown();
        app.wizard.stopMoveRight();
        app.wizard.stopMoveLeft();
        int i = 0;
        while(i<19){
            app.wizard.tick();
            i++;
        }
        app.wizard.setX(20);
        app.wizard.setY(40);
        app.wizard.tick();
        app.wizard.setMoveUp();
        app.wizard.tick();
        assertTrue(app.wizard.getX() == 20);
        assertTrue(app.wizard.getY() < 40);
    }
    @Test
    public void DownMovement(){
        app.wizard.stopMoveUp();
        app.wizard.stopMoveRight();
        app.wizard.stopMoveLeft();
        int i = 0;
        while(i<19){
            app.wizard.tick();
            i++;
        }
        app.wizard.setX(60);
        app.wizard.setY(initY);
        app.wizard.tick();
        app.wizard.setMoveDown();
        app.wizard.tick();
        assertTrue(app.wizard.getX() == 60);
        assertTrue(app.wizard.getY() > initY);
    }
    @Test
    public void collideNoNmore(){
        app.wizard.stopMoveUp();
        app.wizard.stopMoveRight();
        app.wizard.stopMoveLeft();
        app.wizard.stopMoveDown();
        int i = 0;
        while(i<19){
            app.wizard.tick();
            i++;
        }
        app.wizard.setX(initX);
        app.wizard.setY(initY);
        app.wizard.tick();
        app.wizard.setMoveUp();
        app.wizard.tick();
        //Does not change when collide
        assertTrue(app.wizard.getX() == initX);
        assertTrue(app.wizard.getY() == initY);
    }
    @Test
    public void shootFireball(){
        app.wizard.stopShootFreezeball();
        app.wizard.setMoveRight();
        app.wizard.setShootFireball();
        app.wizard.tick();
        for(Fireball f: app.fireballs) {
            f.collide = false;
            f.tick();
        }
        for(Fireball f: app.fireballs) {
            f.collide = false;
        }
        for (Fireball f: app.fireballs) {
            f.tick();
        }
        assertEquals(1, app.fireballs.size());
        assertEquals(true, app.wizard.getdrawRectFireball());

    }
    @Test
    public void shootFreezeball(){
        app.wizard.stopShootFireball();
        app.wizard.setMoveRight();
        app.wizard.setShootFreezeball();
        app.wizard.tick();
        for(Freeze f: app.freezeballs) {
            f.collide = false;
        }
        for (Freeze f: app.freezeballs) {
            f.tick();
        }
        assertEquals(1, app.freezeballs.size());
        assertEquals(true, app.wizard.getdrawRectFreezeball());

    }
    @Test
    public void powerUp(){
        app.wizard.stopMoveUp();
        app.wizard.stopMoveRight();
        app.wizard.stopMoveLeft();
        app.wizard.stopMoveDown();
        int i = 0;
        while(i <= 10*60){
            app.wizard.tick();
            i++;
        }
        //Go to powerup
        app.wizard.setX(160);
        app.wizard.setY(40);
        app.wizard.tick();
        assertEquals(true, app.wizard.getdrawPowerUp());

        int j = 0;
        while(j <= 10*60){
            app.wizard.tick();
            j++;
        }
        assertEquals(10, app.wizard.getPowerUpTimer());
    }
    @Test
    public void powerUpWhileMoving(){
        app.wizard.stopMoveUp();
        app.wizard.stopMoveRight();
        app.wizard.stopMoveLeft();
        app.wizard.stopMoveDown();
        ArrayList<PowerUp> powerUps = new ArrayList<PowerUp>();
        powerUps.add(new PowerUp(160, 40, app.powerupImage));
        app.wizard.setPowerUps(powerUps);
        int i = 0;
        while(i <= 20*60){
            app.wizard.tick();
            i++;
        }
        //Go to powerup
        app.wizard.setX(160);
        app.wizard.setY(40);
        app.wizard.tick();
        assertEquals(true, app.wizard.getdrawPowerUp());

        int j = 0;
        while(j <= 10*60){
            app.wizard.setIsMoving();
            app.wizard.tick();
            j++;
        }
        assertNotEquals(10, app.wizard.getPowerUpTimer());
    }

}