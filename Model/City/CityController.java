package Model.City;

import Model.Civilization;
import Model.Resources.ResourceTypes;
import Model.Terrain;
import Model.Units.*;
import Model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;

public class CityController
{
    public void garrison(City city, CombatUnit combatUnit)
    {
        if (city.getCombatUnit() == null)
        {
            city.setCombatUnit(combatUnit);
            city.setCombatStrength(100); // adding combatStrength

        }
        else
        {
            // this city already contains a combat unit
        }

    }

    public void foundCity (Civilization civilization, NonCombatUnit unit, Terrain tile)
    {
        if ( unit.getUnitType().equals(UnitTypes.SETTLER))
        {
            if (tile.getCity() != null )
            {

                return;
            }
            City newCity = new City(civilization, civilization,  tile, 0, "none", 0, 0 );
            tile.setCity(newCity);
            civilization.addCity(newCity);
            civilization.removeUnit( (Unit) unit);
        }
    }

    public void playTurn( City city)
    {
        // update food
        int foodIncrease = 0;
        int goldIncrease = 0;
        int productionIncrease = 0;
        for ( Terrain tile : city.getNeighbors() )
        {
            foodIncrease += tile.getResource().getFood();
            goldIncrease += tile.getResource().getGold();
            goldIncrease += tile.getGold();
            productionIncrease += tile.getResource().getProduction();
        }
        for ( Citizen citizen : city.getCitizens())
        {
            productionIncrease += citizen.getProduction();
        }
        foodIncrease -= city.getPopulation() * 2;
        if ( foodIncrease > 0) // creating Citizens
        {
            city.setFoodStorage(city.getFoodStorage() + foodIncrease);
            if ( city.getFoodStorage() > 100)
            {
                Citizen newCitizen = new Citizen(city);
                city.addCitizen( newCitizen);
                city.setFoodStorage(city.getFoodStorage() - 100);
            }
        }
        else if ( foodIncrease < 0) // Killing Citizens
        {
            foodIncrease = - foodIncrease;
            int numberOfDyingCitizens = foodIncrease / 2;
            for ( int i = 0; i < numberOfDyingCitizens; i++)
            {
                city.removeCitiZen(i);
            }

        }
        // update constructions

    }

    public void destroyCity(Civilization destroyer,Civilization loser, City city)
    {
        loser.removeCity(city);
        destroyer.setHappiness( destroyer.getHappiness() + 100 );
        // handle the tile itself


    }

    public void makePuppet(Civilization maker,Civilization loser, City city)
    {

    }

    public void attachCity( Civilization civilization, City city)
    {
        civilization.addCity(city);
        city.setOwner(civilization);
        civilization.setHappiness(civilization.getHappiness() - 10000);

    }

    public HashMap<String, String> cityOutput (City city )
    {
        HashMap<String, String> output = new HashMap<>();
        output.put("food", String.valueOf(city.getFood()));
        output.put("production", String.valueOf(city.getProduction()));
        output.put("gold", String.valueOf(city.getGold()));
        output.put("turns remaining until population increase", String.valueOf(city.getTurnsRemainingUntilPopulationIncrease()));
        return output;

    }

    public HashMap<String, ArrayList<String>> cityMenu (City city)
    {


    }

    public String createUnit(Matcher matcher, City city)
    {
        Civilization civilization = city.getOwner();
        String unitName = matcher.group("unitName");
        int money = city.getGold();
        String notEnoughMoney = "You do not have enough gold to construct this unit";
        String lackTechnology = "You lack the required technology to construct this unit";
        String lackResources = "You lack the required resources to construct this unit";
        String unitAlreadyExists = "There is already a unit in this city";

        if (unitName.equals("ARCHER"))
        {
            if (money < UnitTypes.ARCHER.getCost())
            {
                return notEnoughMoney;
            } else if (!civilization.getTechnologies().contains(UnitTypes.ARCHER.getTechnologyRequirements()))
            {
                return lackTechnology;
            } else if (city.getCombatUnit() != null)
            {
                return unitAlreadyExists;
            } else
            {
                RangedCombatUnit newArcher = new RangedCombatUnit(city.getCombatUnit().getX(), city.getCombatUnit().getY(), 0, 0, 0, 0, false, false, UnitTypes.ARCHER, false, false, false, false, false, false);
                civilization.setGold(money - UnitTypes.ARCHER.getCost());
                civilization.addUnit((Unit) newArcher);
                city.setCombatUnit((CombatUnit) newArcher);
            }
        } else if (unitName.equals("CHARIOT_ARCHER"))
        {
            if (money < UnitTypes.CHARIOT_ARCHER.getCost())
            {
                return notEnoughMoney;
            } else if (!civilization.getTechnologies().contains(UnitTypes.CHARIOT_ARCHER.getTechnologyRequirements()))
            {
                return lackTechnology;
            } else if (!city.getCentralTerrain().getResource().getResourceType().equals(ResourceTypes.HORSES))
            {
                return lackResources;
            } else if (city.getCombatUnit() != null)
            {
                return unitAlreadyExists;
            } else
            {
                RangedCombatUnit newChariotArcher = new RangedCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.CHARIOT_ARCHER, false, false, false, false, false, false);
                civilization.setGold(money - UnitTypes.CHARIOT_ARCHER.getCost());
                civilization.addUnit((Unit) newChariotArcher);
                city.setCombatUnit((CombatUnit) newChariotArcher);
            }


        } else if (unitName.equals("SCOUT"))
        {
            if (money < UnitTypes.SCOUT.getCost())
            {
                return notEnoughMoney;
            } else if (city.getCombatUnit() != null)
            {
                return unitAlreadyExists;
            } else
            {
                NonRangedCombatUnit newScout = new NonRangedCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.SCOUT, false, false, false, false, false);
                civilization.setGold(money - UnitTypes.SCOUT.getCost());
                civilization.addUnit((Unit) newScout);
                city.setCombatUnit((CombatUnit) newScout);
            }

        } else if (unitName.equals("SETTLER"))
        {
            if (money < UnitTypes.SETTLER.getCost())
            {
                return notEnoughMoney;
            } else if (city.getNonCombatUnit() != null)
            {
                return unitAlreadyExists;
            } else
            {
                NonCombatUnit newSettler = new NonCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.SETTLER, false);
                civilization.setGold(money - UnitTypes.SETTLER.getCost());
                civilization.addUnit((Unit) newSettler);
                city.setNonCombatUnit((NonCombatUnit) newSettler);
            }

        } else if (unitName.equals("SPEARMAN"))
        {
            if (money < UnitTypes.SPEARMAN.getCost())
            {
                return notEnoughMoney;
            } else if (city.getCombatUnit() != null)
            {
                return unitAlreadyExists;
            } else
            {
                NonRangedCombatUnit newScout = new NonRangedCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.SPEARMAN, false, false, false, false, false);
                civilization.setGold(money - UnitTypes.SPEARMAN.getCost());
                civilization.addUnit((Unit) newScout);
                city.setCombatUnit((CombatUnit) newScout);
            }

        } else if (unitName.equals("WARRIOR"))
        {
            if (money < UnitTypes.WARRIOR.getCost())
            {
                return notEnoughMoney;
            } else if (city.getCombatUnit() != null)
            {
                return unitAlreadyExists;
            } else
            {
                NonRangedCombatUnit newWarrior = new NonRangedCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.WARRIOR, false, false, false, false, false);
                civilization.setGold(money - UnitTypes.WARRIOR.getCost());
                civilization.addUnit((Unit) newWarrior);
                city.setCombatUnit((CombatUnit) newWarrior);
            }

        } else if (unitName.equals("WORKER"))
        {
            if (money < UnitTypes.WORKER.getCost())
            {
                return notEnoughMoney;
            } else if (city.getNonCombatUnit() != null)
            {
                return unitAlreadyExists;
            } else
            {
                NonCombatUnit newWorker = new NonCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.WORKER, false);
                civilization.setGold(money - UnitTypes.WORKER.getCost());
                civilization.addUnit((Unit) newWorker);
                city.setNonCombatUnit((NonCombatUnit) newWorker);
            }

        } else if (unitName.equals("CATAPULT"))
        {
            if (money < UnitTypes.CATAPULT.getCost())
            {
                return notEnoughMoney;
            } else if (!city.getCentralTerrain().getResource().getResourceType().equals(UnitTypes.CATAPULT.getResourceRequirements()))
            {
                return lackResources;
            } else if (!civilization.getTechnologies().contains(UnitTypes.CATAPULT.getTechnologyRequirements()))
            {
                return lackTechnology;
            } else if (city.getCombatUnit() != null)
            {
                return unitAlreadyExists;
            } else
            {
                RangedCombatUnit newCatapult = new RangedCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.CATAPULT, false, false, false, false, false, false);
                civilization.setGold(money - UnitTypes.CATAPULT.getCost());
                civilization.addUnit((Unit) newCatapult);
                city.setCombatUnit((CombatUnit) newCatapult);
            }

        } else if (unitName.equals("HORSESMAN"))
        {
            if (money < UnitTypes.HORSESMAN.getCost())
            {
                return notEnoughMoney;
            } else if (!city.getCentralTerrain().getResource().getResourceType().equals(UnitTypes.HORSESMAN.getResourceRequirements()))
            {
                return lackResources;
            } else if (!civilization.getTechnologies().contains(UnitTypes.HORSESMAN.getTechnologyRequirements()))
            {
                return lackTechnology;
            } else if (city.getCombatUnit() != null)
            {
                return unitAlreadyExists;
            } else
            {
                NonRangedCombatUnit newHorsesman = new NonRangedCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.HORSESMAN, false, false, false, false, false);
                civilization.setGold(money - UnitTypes.HORSESMAN.getCost());
                civilization.addUnit((Unit) newHorsesman);
                city.setCombatUnit((CombatUnit) newHorsesman);
            }

        } else if (unitName.equals("SWORDSMAN"))
        {
            if (money < UnitTypes.SWORDSMAN.getCost())
            {
                return notEnoughMoney;
            } else if (!city.getCentralTerrain().getResource().getResourceType().equals(UnitTypes.SWORDSMAN.getResourceRequirements()))
            {
                return lackResources;
            } else if (!civilization.getTechnologies().contains(UnitTypes.SWORDSMAN.getTechnologyRequirements()))
            {
                return lackTechnology;
            } else if (city.getCombatUnit() != null)
            {
                return unitAlreadyExists;
            } else
            {
                NonRangedCombatUnit newSwordsman = new NonRangedCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.SWORDSMAN, false, false, false, false, false);
                civilization.setGold(money - UnitTypes.SWORDSMAN.getCost());
                civilization.addUnit((Unit) newSwordsman);
                city.setCombatUnit((CombatUnit) newSwordsman);
            }

        } else if (unitName.equals("CROSSBOWMAN"))
        {
            if (money < UnitTypes.CROSSBOWMAN.getCost())
            {
                return notEnoughMoney;
            } else if (!civilization.getTechnologies().contains(UnitTypes.CROSSBOWMAN.getTechnologyRequirements()))
            {
                return lackTechnology;
            } else if (city.getCombatUnit() != null)
            {
                return unitAlreadyExists;
            } else
            {
                RangedCombatUnit newCrossbowman = new RangedCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.CROSSBOWMAN, false, false, false, false, false, false);
                civilization.setGold(money - UnitTypes.CROSSBOWMAN.getCost());
                civilization.addUnit((Unit) newCrossbowman);
                city.setCombatUnit((CombatUnit) newCrossbowman);
            }

        } else if (unitName.equals("KNIGHT"))
        {
            if (money < UnitTypes.HORSESMAN.getCost())
            {
                return notEnoughMoney;
            } else if (!city.getCentralTerrain().getResource().getResourceType().equals(UnitTypes.KNIGHT.getResourceRequirements()))
            {
                return lackResources;
            } else if (!civilization.getTechnologies().contains(UnitTypes.HORSESMAN.getTechnologyRequirements()))
            {
                return lackTechnology;
            } else if (city.getCombatUnit() != null)
            {
                return unitAlreadyExists;
            } else
            {
                NonRangedCombatUnit newKnight = new NonRangedCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.KNIGHT, false, false, false, false, false);
                civilization.setGold(money - UnitTypes.KNIGHT.getCost());
                civilization.addUnit((Unit) newKnight);
                city.setCombatUnit((CombatUnit) newKnight);

            }

        } else if (unitName.equals("LONGSWORDSMAN"))
        {
            if (money < UnitTypes.LONGSWORDSMAN.getCost())
            {
                return notEnoughMoney;
            } else if (!city.getCentralTerrain().getResource().getResourceType().equals(UnitTypes.LONGSWORDSMAN.getResourceRequirements()))
            {
                return lackResources;
            } else if (!civilization.getTechnologies().contains(UnitTypes.LONGSWORDSMAN.getTechnologyRequirements()))
            {
                return lackTechnology;
            } else if (city.getCombatUnit() != null)
            {
                return unitAlreadyExists;
            } else
            {
                NonRangedCombatUnit newLong = new NonRangedCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.LONGSWORDSMAN, false, false, false, false, false);
                civilization.setGold(money - UnitTypes.LONGSWORDSMAN.getCost());
                civilization.addUnit((Unit) newLong);
                city.setCombatUnit((CombatUnit) newLong);
            }

        } else if (unitName.equals("PIKEMAN"))
        {
            if (money < UnitTypes.PIKEMAN.getCost())
            {
                return notEnoughMoney;
            } else if (!civilization.getTechnologies().contains(UnitTypes.PIKEMAN.getTechnologyRequirements()))
            {
                return lackTechnology;
            } else if (city.getCombatUnit() != null)
            {
                return unitAlreadyExists;
            } else
            {
                NonRangedCombatUnit newPikeman = new NonRangedCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.PIKEMAN, false, false, false, false, false);
                civilization.setGold(money - UnitTypes.PIKEMAN.getCost());
                civilization.addUnit((Unit) newPikeman);
                city.setCombatUnit((CombatUnit) newPikeman);
            }

        } else if (unitName.equals("TREBUCHET"))
        {
            if (money < UnitTypes.TREBUCHET.getCost())
            {
                return notEnoughMoney;
            } else if (!city.getCentralTerrain().getResource().getResourceType().equals(UnitTypes.TREBUCHET.getResourceRequirements()))
            {
                return lackResources;
            } else if (!civilization.getTechnologies().contains(UnitTypes.TREBUCHET.getTechnologyRequirements()))
            {
                return lackTechnology;
            } else if (city.getCombatUnit() != null)
            {
                return unitAlreadyExists;
            } else
            {
                RangedCombatUnit newTrebuchet = new RangedCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.TREBUCHET, false, false, false, false, false, false);
                civilization.setGold(money - UnitTypes.TREBUCHET.getCost());
                civilization.addUnit((Unit) newTrebuchet);
                city.setCombatUnit((CombatUnit) newTrebuchet);

            }

        } else if (unitName.equals("CANNON"))
        {
            if (money < UnitTypes.CANNON.getCost())
            {
                return notEnoughMoney;
            } else if (!civilization.getTechnologies().contains(UnitTypes.CANNON.getTechnologyRequirements()))
            {
                return lackTechnology;
            } else if (city.getCombatUnit() != null)
            {
                return unitAlreadyExists;
            } else
            {
                RangedCombatUnit newCannon = new RangedCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.CANNON, false, false, false, false, false, false);
                civilization.setGold(money - UnitTypes.CANNON.getCost());
                civilization.addUnit((Unit) newCannon);
                city.setCombatUnit((CombatUnit) newCannon);

            }

        } else if (unitName.equals("CAVALRY"))
        {
            if (money < UnitTypes.CAVALRY.getCost())
            {
                return notEnoughMoney;
            } else if (!city.getCentralTerrain().getResource().getResourceType().equals(UnitTypes.CAVALRY.getResourceRequirements()))
            {
                return lackResources;
            } else if (!civilization.getTechnologies().contains(UnitTypes.CAVALRY.getTechnologyRequirements()))
            {
                return lackTechnology;
            } else if (city.getCombatUnit() != null)
            {
                return unitAlreadyExists;
            } else
            {
                NonRangedCombatUnit newUnit = new NonRangedCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.CAVALRY, false, false, false, false, false);
                civilization.setGold(money - UnitTypes.CAVALRY.getCost());
                civilization.addUnit((Unit) newUnit);
                city.setCombatUnit((CombatUnit) newUnit);

            }

        } else if (unitName.equals("LANCER"))
        {
            if (money < UnitTypes.LANCER.getCost())
            {
                return notEnoughMoney;
            } else if (!city.getCentralTerrain().getResource().getResourceType().equals(UnitTypes.LANCER.getResourceRequirements()))
            {
                return lackResources;
            } else if (!civilization.getTechnologies().contains(UnitTypes.LANCER.getTechnologyRequirements()))
            {
                return lackTechnology;
            } else if (city.getCombatUnit() != null)
            {
                return unitAlreadyExists;
            } else
            {
                NonRangedCombatUnit newUnit = new NonRangedCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.LANCER, false, false, false, false, false);
                civilization.setGold(money - UnitTypes.LANCER.getCost());
                civilization.addUnit((Unit) newUnit);
                city.setCombatUnit((CombatUnit) newUnit);
            }

        } else if (unitName.equals("MUSKETMAN"))
        {
            if (money < UnitTypes.MUSKETMAN.getCost())
            {
                return notEnoughMoney;
            } else if (!civilization.getTechnologies().contains(UnitTypes.MUSKETMAN.getTechnologyRequirements()))
            {
                return lackTechnology;
            } else if (city.getCombatUnit() != null)
            {
                return unitAlreadyExists;
            } else
            {
                NonRangedCombatUnit newUnit = new NonRangedCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.MUSKETMAN, false, false, false, false, false);
                civilization.setGold(money - UnitTypes.MUSKETMAN.getCost());
                civilization.addUnit((Unit) newUnit);
                city.setCombatUnit((CombatUnit) newUnit);
            }

        } else if (unitName.equals("RIFLEMAN"))
        {
            if (money < UnitTypes.RIFLEMAN.getCost())
            {
                return notEnoughMoney;
            } else if (!civilization.getTechnologies().contains(UnitTypes.RIFLEMAN.getTechnologyRequirements()))
            {
                return lackTechnology;
            } else if (city.getCombatUnit() != null)
            {
                return unitAlreadyExists;
            } else
            {
                NonRangedCombatUnit newUnit = new NonRangedCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.RIFLEMAN, false, false, false, false, false);
                civilization.setGold(money - UnitTypes.RIFLEMAN.getCost());
                civilization.addUnit((Unit) newUnit);
                city.setCombatUnit((CombatUnit) newUnit);

            }

        } else if (unitName.equals("ANTI_TANKGUN"))
        {
            if (money < UnitTypes.ANTI_TANKGUN.getCost())
            {
                return notEnoughMoney;
            } else if (!civilization.getTechnologies().contains(UnitTypes.ANTI_TANKGUN.getTechnologyRequirements()))
            {
                return lackTechnology;
            } else if (city.getCombatUnit() != null)
            {
                return unitAlreadyExists;
            } else
            {
                NonRangedCombatUnit newUnit = new NonRangedCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.ANTI_TANKGUN, false, false, false, false, false);
                civilization.setGold(money - UnitTypes.ANTI_TANKGUN.getCost());
                civilization.addUnit((Unit) newUnit);
                city.setCombatUnit((CombatUnit) newUnit);
            }

        } else if (unitName.equals("ARTILLERY"))
        {
            if (money < UnitTypes.ARTILLERY.getCost())
            {
                return notEnoughMoney;
            } else if (!civilization.getTechnologies().contains(UnitTypes.ARTILLERY.getTechnologyRequirements()))
            {
                return lackTechnology;
            } else if (city.getCombatUnit() != null)
            {
                return unitAlreadyExists;
            } else
            {
                RangedCombatUnit newUnit = new RangedCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.ARTILLERY, false, false, false, false, false, false);
                civilization.setGold(money - UnitTypes.ARTILLERY.getCost());
                civilization.addUnit((Unit) newUnit);
                city.setCombatUnit((CombatUnit) newUnit);
            }

        } else if (unitName.equals("INFANTRY"))
        {
            if (money < UnitTypes.INFANTRY.getCost())
            {
                return notEnoughMoney;
            } else if (!civilization.getTechnologies().contains(UnitTypes.INFANTRY.getTechnologyRequirements()))
            {
                return lackTechnology;
            } else if (city.getCombatUnit() != null)
            {
                return unitAlreadyExists;
            } else
            {
                NonRangedCombatUnit newUnit = new NonRangedCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.INFANTRY, false, false, false, false, false);
                civilization.setGold(money - UnitTypes.INFANTRY.getCost());
                civilization.addUnit((Unit) newUnit);
                city.setCombatUnit((CombatUnit) newUnit);
            }

        } else if (unitName.equals("PANZER"))
        {
            if (money < UnitTypes.PANZER.getCost())
            {
                return notEnoughMoney;
            } else if (!civilization.getTechnologies().contains(UnitTypes.PANZER.getTechnologyRequirements()))
            {
                return lackTechnology;
            } else if (city.getCombatUnit() != null)
            {
                return unitAlreadyExists;
            } else
            {
                NonRangedCombatUnit newUnit = new NonRangedCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.PANZER, false, false, false, false, false);
                civilization.setGold(money - UnitTypes.PANZER.getCost());
                civilization.addUnit((Unit) newUnit);
                city.setCombatUnit((CombatUnit) newUnit);
            }

        } else if (unitName.equals("TANK"))
        {
            if (money < UnitTypes.TANK.getCost())
            {
                return notEnoughMoney;
            } else if (!civilization.getTechnologies().contains(UnitTypes.TANK.getTechnologyRequirements()))
            {
                return lackTechnology;
            } else if (city.getCombatUnit() != null)
            {
                return unitAlreadyExists;
            } else
            {
                NonRangedCombatUnit newUnit = new NonRangedCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.TANK, false, false, false, false, false);
                civilization.setGold(money - UnitTypes.TANK.getCost());
                civilization.addUnit((Unit) newUnit);
                city.setCombatUnit((CombatUnit) newUnit);
            }

        }
        return "invalid unit name";
    }

    public void assignCitizen(City city, Citizen citizen, Terrain tile)
    {
        if ( city.getCitizens().contains(citizen))
        {
            if ( city.getNeighbors().contains(tile) && citizen.getHasWork())
            {
                citizen.assignWork(tile);
                return;
            }
        }
        System.out.println("error");

    }

    public void garrisonACity( City city)
    {
        if ( city.getCombatUnit() != null)
        {
            city.setGarrisoned(true);
            city.setCombatStrength(city.getCombatStrength() + city.getCombatUnit().getCombatStrength());

        }
    }

    public void playATurnInCombat ( City city, Unit AttackingUnit)
    {

    }




}
