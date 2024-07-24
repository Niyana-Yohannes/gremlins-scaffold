package gremlins;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

//Test config class
public class ConfigTest {
    
    private Config config;

    @BeforeEach
    public void init(){
        config = new Config("config.json");
    }
    
    @Test
    public void configFileNotNull(){
        Config config = new Config("String");
        assertNotNull(config.getConfigPath());
    }

    @Test
    public void numberofMap(){
        ArrayList<String[][]> Map = config.getMap();
        assertEquals(2, Map.size());
    }

    @Test
    public void numberofWizCoolDown(){
        String[] wizCoolDown = config.getWizardCooldown();
        assertEquals(2, wizCoolDown.length);
    }


    @Test
    public void numberoGremCooldown(){
        String[] gremCoolDown = config.getEnemyCooldown();
        assertEquals(2, gremCoolDown.length);
    }
}
