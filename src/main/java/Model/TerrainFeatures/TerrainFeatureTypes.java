package Model.TerrainFeatures;

import java.util.ArrayList;

import Model.Resources.ResourceTypes;

public enum TerrainFeatureTypes {
    FLOODPLAINS(2, 0, 0, -33, 1, new ArrayList<ResourceTypes>() {
        {
            add(ResourceTypes.WHEAT);
            add(ResourceTypes.SUGAR);
        }
    },"FLOOD"),
    FOREST(1, 1, 0, 25, 2, new ArrayList<ResourceTypes>() {
        {
            add(ResourceTypes.DEER);
            add(ResourceTypes.FURS);
            add(ResourceTypes.DYES);
            add(ResourceTypes.SILK);
        }
    },"FOREST"),
    ICE(0, 0, 0, 0, 99999, null,"ICE"),
    JUNGLE(1, -1, 0, 25, 2, new ArrayList<ResourceTypes>() {
        {
            add(ResourceTypes.BANANAS);
            add(ResourceTypes.GEMS);
            add(ResourceTypes.DYES);
        }
    },"JUNGLE"),
    MARSH(-1, 0, 0, -33, 2, new ArrayList<ResourceTypes>() {
        {
            add(ResourceTypes.SUGAR);
        }
    },"MARSH"),
    OASIS(3, 0, 1, -33, 1, null,"OASIS"),
    RIVER(0, 0, 1, 0, -1, null,"RIVER");

    TerrainFeatureTypes(int food, int product, int gold, int combatModifier, int movementCost,
            ArrayList<ResourceTypes> possibleResourcesFound,String ShowFeatures) {
        this.food = food;
        this.product = product;
        this.gold = gold;
        this.combatModifier = combatModifier;
        this.movementCost = movementCost;
        this.possibleResourcesFound = possibleResourcesFound;
        this.ShowFeatures = ShowFeatures;
    }

    int food;
    int product;
    int gold;
    int combatModifier;
    int movementCost;
    ArrayList<ResourceTypes> possibleResourcesFound;
    String ShowFeatures;
    public String getShowFeatures(){
        return this.ShowFeatures;
    }
    public ArrayList<ResourceTypes> getResourceType(){
        return this.possibleResourcesFound; 
    }
    public int getGold(){
        return this.gold;
    }
    public int getFood(){
        return this.food;
    }
}