package Model;

import Model.City.City;
import Model.Terrains.TerrainTypes;
import Model.TerrainFeatures.TerrainFeatureTypes;
import Model.Units.CombatUnit;
import Model.Units.NonCombatUnit;
import Model.Units.Unit;

public class Terrain {

    private int x;
    private int y;
    private String Type;
    private TerrainTypes terrainTypes;
    private TerrainFeatureTypes terrainFeatureTypes;
    private CombatUnit combatUnit;
    private NonCombatUnit nonCombatUnit;
    private Resource resource;
    private City city;
    private boolean isBeingWorkedOn;


    Terrain(int x, int y, String Type, TerrainTypes terrainTypes, TerrainFeatureTypes terrainFeatureTypes, CombatUnit combatUnit,NonCombatUnit nonCombatUnit) {
        this.x = x;
        this.y = y;
        this.Type = Type;
        this.terrainTypes = terrainTypes;
        this.terrainFeatureTypes = terrainFeatureTypes;
        this.combatUnit = combatUnit;
        this.nonCombatUnit = nonCombatUnit;
        this.city = null;
        this.isBeingWorkedOn = false;
    }


    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getType() {
        return this.Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public TerrainTypes getTerrainTypes() {
        return this.terrainTypes;
    }

    public void setTerrainTypes(TerrainTypes terrainTypes) {
        this.terrainTypes = terrainTypes;
    }

    public TerrainFeatureTypes getTerrainFeatureTypes() {
        return this.terrainFeatureTypes;
    }

    public void setTerrainFeatureTypes(TerrainFeatureTypes terrainFeatureTypes) {
        this.terrainFeatureTypes = terrainFeatureTypes;
    }

    public CombatUnit getCombatUnit() {
        return this.combatUnit;
    }

    public void setCombatUnit(CombatUnit combatUnit) {
        this.combatUnit = combatUnit;
    }

    public NonCombatUnit getNonCombatUnit() {
        return this.nonCombatUnit;
    }

    public void setNonCombatUnit(NonCombatUnit nonCombatUnit) {
        this.nonCombatUnit = nonCombatUnit;
    }


    public Resource getResource()
    {
        return resource;
    }

    public void setResource(Resource resource)
    {
        this.resource = resource;
    }

    public City getCity()
    {
        return city;
    }

    public void setCity(City city)
    {
        this.city = city;
    }
    public boolean containsUnit(Unit unit2)
    {
        if(unit2.equals((Unit) combatUnit))
        {
            return true;
        }
        else if(unit2.equals((Unit) nonCombatUnit))
        {
            return true;
        }
        return false;
    }

    public boolean isBeingWorkedOn()
    {
        return isBeingWorkedOn;
    }

    public void setBeingWorkedOn(boolean beingWorkedOn)
    {
        isBeingWorkedOn = beingWorkedOn;
    }

    public int getGold ()
    {
        if (this.isBeingWorkedOn == false)
        {
            return 0;
        }
        else
        {

        }
    }
}