package gremlins;

import org.junit.jupiter.api.Test;

import processing.core.PApplet;
import processing.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

public class GameStateTest {

    private GameState currGameState;
    private Wizard wizard = new Wizard(20, 20, null, null, null, null, null, null, null, null);
    private ExitDoor exitTile =  new ExitDoor(40, 40, null);

    private GameState currGameState2;
    private Wizard wizard2 = new Wizard(20, 20, null, null, null, null, null, null, null, null);
    private ExitDoor exitTile2 =  new ExitDoor(40, 40, null);

    private GameState currGameState3;
    private Wizard wizard3 = new Wizard(20, 20, null, null, null, null, null, null, null, null);
    private ExitDoor exitTile3 =  new ExitDoor(40, 40, null);

    @BeforeEach
    public void init(){
        this.wizard.setX(20);
        this.wizard.setY(20);
        this.wizard.setLives(3);
        this.exitTile = new ExitDoor(40, 40, null);
        this.exitTile.setWizard(this.wizard);
        this.currGameState = GameState.LevelOneState;
        this.currGameState.setGameObjects(this.wizard, this.exitTile);
    }

    @Test
    public void moveToLevelTwo(){
        //Setting wizard on exit
        wizard.setX(40);
        wizard.setY(40);
        exitTile.tick();
        int newGameState = currGameState.GameStateUpdate();     
        assertEquals(2, newGameState);
    }
    @Test
    public void moveToLose(){
        //Setting wizard on exit
        this.wizard.setLives(0);
        exitTile.tick();
        int newGameState = currGameState.GameStateUpdate();
        assertEquals(4, newGameState);
    }
    @Test
    public void moveToWin(){
        //Setting level to 2
        this.currGameState = GameState.LevelTwoState;
        this.currGameState.setGameObjects(this.wizard, this.exitTile);
        //Setting wizard on exit
        this.wizard.setX(40);
        this.wizard.setY(40);
        this.exitTile.tick();
        int newGameState = currGameState.GameStateUpdate();
        assertEquals(3, newGameState);
    }
    @Test
    public void loseALife(){
        //Life lost but still have more than 3 lives
        this.wizard.setNotAlive();
        int newGameState = currGameState.GameStateUpdate();
        assertEquals(1, newGameState);
    }

    @BeforeEach
    public void init2(){
        this.wizard2.setX(20);
        this.wizard2.setY(20);
        this.wizard2.setLives(3);
        this.exitTile2 = new ExitDoor(40, 40, null);
        this.exitTile2.setWizard(this.wizard2);
        this.currGameState2 = GameState.LevelTwoState;
        this.currGameState2.setGameObjects(this.wizard2, this.exitTile2);
    }
    @Test
    public void moveToLose2(){
        //Setting wizard on exit
        this.wizard2.setLives(0);
        exitTile2.tick();
        int newGameState = currGameState2.GameStateUpdate();
        assertEquals(4, newGameState);
    }
    @Test
    public void loseALife2(){
        //Life lost but still have more than 3 lives
        this.wizard2.setNotAlive();
        int newGameState = currGameState2.GameStateUpdate();
        assertEquals(2, newGameState);
    }

    @BeforeEach
    public void init3(){
        this.wizard3.setX(20);
        this.wizard3.setY(20);
        this.wizard3.setLives(3);
        this.exitTile3 = new ExitDoor(40, 40, null);
        this.exitTile3.setWizard(this.wizard3);
        this.currGameState3 = GameState.WinGameState;
        this.currGameState3.setGameObjects(this.wizard3, this.exitTile3);
    }
    @Test
    public void winToOne(){
        //Setting wizard on exit
        this.currGameState3.setkeyPressed();
        int newGameState = currGameState3.GameStateUpdate();
        assertEquals(1, newGameState);
    }
    @Test
    public void loseToOne(){
        //Setting wizard on exit
        this.currGameState3 = GameState.LoseGameState;
        this.currGameState3.setGameObjects(this.wizard3, this.exitTile3);

        this.currGameState3.setkeyPressed();
        int newGameState = currGameState3.GameStateUpdate();
        assertEquals(1, newGameState);
    }

    
}