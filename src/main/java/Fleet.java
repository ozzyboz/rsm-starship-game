import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This is the Fleet object class
 * @author Oscar Allen
 */
public class Fleet {

    private String playerName;
    private List<Starship> ships = new ArrayList<>();
    private List<Starbase> starbases = new ArrayList<>();

    /**
     * Initialises the Fleet - creating a Starbase and the required number of Ships in the specified Sector
     * @param playerName
     * @param sector
     * @param numberOfStarships
     */

    public Fleet(String playerName, int sector, int numberOfStarships) {
        this.playerName = playerName;

        Starbase starbase = new Starbase(sector);
        addStarbase(starbase);

        for (int i = 0; i < numberOfStarships; i++) {
            Starship starship = new Starship(sector);
            addShip(starship);
        }
    }

    /**
     * Adds a ship to the Fleet
     * @param ship
     */

    public void addShip(Starship ship) {
        ships.add(ship);
        ship.setFleet(playerName);
    }

    /**
     * Gets the list of Ships in the Fleet
     * @return ships
     */

    public List<Starship> getShips() {
        return ships;
    }

    /**
     * Adds a Starbase to the Fleet
     * @param starbase
     */

    public void addStarbase(Starbase starbase) {
        starbases.add(starbase);
        starbase.setFleet(playerName);
    }

    /**
     * Gets the list of Starbases in the Fleet
     * @return starbases
     */

    public List<Starbase> getStarbases() {
        return starbases;
    }

    /**
     * Mobilises the fleet by moving all undocked ships into the specified sector
     * @param sector
     */

    public void mobilise(int sector){
        for (Starship ship : ships) {
            if (!ship.isDocked()) {
                ship.moveSector(sector);
            }
        }
    }

    /**
     * Every Ship in the Fleet attacks the chosen target, provided it is undocked, in the same sector and in a different Fleet
     * @param target
     */

    public void attackTarget(Targetable target) {
        if (Objects.equals(playerName, target.getFleet())) return;

        for (Starship ship : ships) {
            if (!ship.isDocked() && ship.getSector() == target.getSector()) {
                ship.attack(target);
            }
        }
    }

    @Override
    public String toString() {
        return "Fleet{" +
                "playerName='" + playerName + '\'' +
                ", ships=" + ships +
                ", starbases=" + starbases +
                '}';
    }
}
