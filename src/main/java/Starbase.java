import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.SequencedCollection;

/**
 * This is the Starbase object class
 * @author Oscar Allen
 */
public class Starbase implements Targetable {
    private static int idCounter;

    private int id;

    private int maxDefStrength = 20;
    private int maxHealth = 500;

    private int currentHealth;
    private int currentDefStrength;

    private String fleet;
    private int sector;
    private List<Starship> dockedShips = new ArrayList<>();

    /**
     * Initialises the Starbase
     * @param sector
     */

    public Starbase(int sector) {
        this.sector = sector;
        this.currentHealth = maxHealth;

        this.id = ++idCounter;
    }

    /**
     * The dockShip() method checks that a ship is not docked before adding it to the dockedShips array
     * @param ship
     * @see Starship#dockWithStarbase(Starbase) 
     */

    public void dockShip(Starship ship) {
        if (!dockedShips.contains(ship)) {
            dockedShips.add(ship);
        }
    }

    /**
     * The unDockShip() method removes a ship from the dockedShips array
     * @param ship
     * @see Starship#unDockWithStarbase()
     */

    public void unDockShip(Starship ship) {
        dockedShips.remove(ship);
    }

    /**
     * Returns a list of Starships docked in Starbase
     * @return dockedShips
     */

    public List<Starship> getDockedShips() {
        return dockedShips;
    }

    /**
     * Sets the fleet that the Starbase belongs to
     * @param fleet
     * @see Fleet#addStarbase(Starbase) 
     */

    @Override
    public void setFleet(String fleet) {
        this.fleet = fleet;
    }

    /**
     * Gets the Fleet that the Starbase belongs to
     * @return fleet
     */

    @Override
    public String getFleet() {
        return fleet;
    }

    /**
     * Gets the current health of the Starbase
     * @return currentHealth
     */

    @Override
    public int getCurrentHealth() {
        return currentHealth;
    }

    /**
     * The getCurrentDefence() method is responsible for calculating and returning the current defence strength of the Starship
     * @return currentDefStrength
     */

    @Override
    public int getCurrentDefence() {
        int totalDockedDefence = 0;

        for (Starship ship : dockedShips) {
            totalDockedDefence += ship.getCurrentDefence();
        }

        double healthFactor = (double) currentHealth / maxHealth;
        double dockedBonus = (double) totalDockedDefence * ((double) dockedShips.size() / maxDefStrength);
        currentDefStrength = (int) Math.floor(maxDefStrength * healthFactor + dockedBonus);
        return currentDefStrength;
    }

    /**
     * Calculates the current health of the Starbase after taking damage
     * @param damage of damage to take
     */

    @Override
    public void takeDamage(int damage) {
        currentHealth -= damage;
        if (currentHealth < 0) currentHealth = 0;
    }

    /**
     * Checks if Starship is destroyed by checking if current health <= 0
     * @return true or false
     */

    @Override
    public boolean isDestroyed() {
        return currentHealth <= 0;
    }

    /**
     * Gets the Sector that the Starship is located in
     * @return sector
     */

    @Override
    public int getSector() {
        return sector;
    }

    @Override
    public String toString() {
        return "Starbase{" +
                "maxDefStrength=" + maxDefStrength +
                ", maxHealth=" + maxHealth +
                ", currentHealth=" + currentHealth +
                ", currentDefStrength=" + getCurrentDefence() +
                ", fleet='" + fleet + '\'' +
                ", sector=" + sector +
                ", dockedShips=" + dockedShips +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Starbase starbase = (Starbase) o;
        return id == starbase.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
