package MAP;



import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import Model.Map;
import Model.Resource;
import Model.Revealed;
import Model.River;
import Model.Terrain;
import Model.User;
import Model.Terrains.TerrainTypes;
import Model.Units.CombatUnit;
import Model.Units.NonCombatUnit;

@ExtendWith(MockitoExtension.class)
public class MapGeneratorTest {

    @Mock
    River river;
    @Mock
    Terrain terrainOne;
    @Mock
    Terrain terrainTwo;
    @Mock
     ArrayList<River> rivers;
    
    @Test
    public void hasRiverTest() {
        Map map = new Map();
        ArrayList<River> rivers = new ArrayList<River>();
        rivers.add(river);
        map.setRiver(rivers);
        when(river.getFirstTerrain()).thenReturn(terrainOne);
        when(river.getSecondTerrain()).thenReturn(terrainTwo);
        River checkRiver = map.hasRiver(terrainOne, terrainTwo);
        Assertions.assertNotNull(checkRiver);
    }

    @Test
    public void hasNotRiverTest() {
        Map map = new Map();
        ArrayList<River> rivers = new ArrayList<River>();
        rivers.add(river);
        map.setRiver(rivers);
        when(river.getFirstTerrain()).thenReturn(null);
        when(river.getSecondTerrain()).thenReturn(terrainOne);
        River checkRiver = map.hasRiver(terrainOne, terrainTwo);
        Assertions.assertNull(checkRiver);
    }

    @Test
    public void hasRiverSecondTest(){
        Map map = new Map();
        rivers = new ArrayList<River>();
        river = new River(terrainOne, terrainTwo);
        rivers.add(river);
        map.setRiver(rivers);
        River checkRiver = map.hasRiver(terrainOne);
        Assertions.assertNotNull(checkRiver);
    }

    @Test
    public void hasNotRiverSecondTest(){
        Map map = new Map();
        rivers = new ArrayList<River>();
        river = new River(null, terrainOne);
        rivers.add(river);
        map.setRiver(rivers);
        River checkRiver = map.hasRiver(terrainTwo);
        Assertions.assertNull(checkRiver);
    }

    @Mock
    Terrain terrainRevealed;
    @Mock
    User user;
    @Mock
    Revealed reveal;
    Revealed revealTwo;
    @Mock
    ArrayList<Revealed> reveals;
    @Mock
    CombatUnit combatunit;
    @Mock
    NonCombatUnit noncombatunit;
    @Mock
    Resource resource;
    @Test
    public void setRevealedTest(){
        terrainRevealed = new Terrain(3, 4, null, null, null, combatunit, noncombatunit, null, resource,new ArrayList<Revealed>() );
        reveals = new ArrayList<Revealed>();
        reveal.setUser(user);
        reveals.add(reveal);
        reveals.add(revealTwo);
        Map map = new Map();
        terrainRevealed.setRevealedTest(reveals);
        map.setTerrainTest(terrainRevealed, 3, 4);
        when(reveal.getUser()).thenReturn(user);
        map.setRevealed(user, 3, 4);
       Assertions.assertFalse(terrainRevealed.getReveals().contains(reveal));
    }

    @Test
    public void setRevealedTestHasAddUser(){
        terrainRevealed = new Terrain(3, 4, null, null, null, combatunit, noncombatunit, null, resource,new ArrayList<Revealed>() );
        reveals = new ArrayList<Revealed>();
        reveal.setUser(user);
        reveals.add(reveal);
        reveals.add(revealTwo);
        Map map = new Map();
        terrainRevealed.setRevealedTest(reveals);
        map.setTerrainTest(terrainRevealed, 3, 4);
        when(reveal.getUser()).thenReturn(user);
        map.setRevealed(user, 3, 4);
       Assertions.assertTrue(terrainRevealed.getReveals().get(1).getUser() == user);
    }


    @Mock
    Terrain terrainInitialize;
   
    Terrain terrains[][];
   
    @Test 
    public void InitializeMapTestPlains(){
        Map map = new Map();
         terrains = new Terrain[map.getROW()][map.getCOL()];
        for(int i = 0;i< map.getROW();i++){
            for(int j = 0; j < map.getCOL();j++){
                terrains[i][j]  =  new Terrain(i,j, null, null, null, combatunit, noncombatunit, null, resource,new ArrayList<Revealed>() );
            }
        }
        map.setTerrains(terrains);
        map.Initializemap();  ;
       Assertions.assertTrue(terrains[3][4].getTerrainTypes() == TerrainTypes.PLAINS);
    }

    @Test 
    public void InitializeMapTestOceans(){
        Map map = new Map();
         terrains = new Terrain[map.getROW()][map.getCOL()];
        for(int i = 0;i< map.getROW();i++){
            for(int j = 0; j < map.getCOL();j++){
                terrains[i][j]  =  new Terrain(i,j, null, null, null, combatunit, noncombatunit, null, resource,new ArrayList<Revealed>() );
            }
        }
        map.setTerrains(terrains);
        map.Initializemap();  ;
       Assertions.assertTrue(terrains[0][0].getTerrainTypes() == TerrainTypes.OCEAN);
    }


    @Test 
    public void InitializeMapTestGrassland(){
        Map map = new Map();
         terrains = new Terrain[map.getROW()][map.getCOL()];
        for(int i = 0;i< map.getROW();i++){
            for(int j = 0; j < map.getCOL();j++){
                terrains[i][j]  =  new Terrain(i,j, null, null, null, combatunit, noncombatunit, null, resource,new ArrayList<Revealed>() );
            }
        }
        map.setTerrains(terrains);
        map.Initializemap();  ;
       Assertions.assertTrue(terrains[12][7].getTerrainTypes() == TerrainTypes.GRASSLLAND);
    }

    @Test 
    public void InitializeMapTestSnow(){
        Map map = new Map();
         terrains = new Terrain[map.getROW()][map.getCOL()];
        for(int i = 0;i< map.getROW();i++){
            for(int j = 0; j < map.getCOL();j++){
                terrains[i][j]  =  new Terrain(i,j, null, null, null, combatunit, noncombatunit, null, resource,new ArrayList<Revealed>() );
            }
        }
        map.setTerrains(terrains);
        map.Initializemap();  ;
       Assertions.assertTrue(terrains[27][4].getTerrainTypes() == TerrainTypes.SNOW);
       Assertions.assertTrue(terrains[27][4].getTerrainTypes() == TerrainTypes.SNOW);
    }

    @Test

    public void randomTerrainAdd(){
        
    }


  







}