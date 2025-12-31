import java.util.Objects;

/**
 * This is the Starship object class
 * @author Oscar Allen
 */
public class Starship implements Targetable {
    private static int idCounter;

    private int id;

    private final int maxAtkStrength = 30;
    private final int maxDefStrength = 10;
    private final int maxCrew = 10;
    private final int maxHealth = 100;

    private int currentHealth;
    private int currentCrew;
    private int currentAtkStrength;
    private int currentDefStrength;

    private String fleet;
    private int sector;
    private int skipTurns;

    private boolean isDocked = false;
    private Starbase dockedBase;

    /**
     * Initialises the Starship
     * @param startingSector
     */

    public Starship(int startingSector) {
        this.sector = startingSector;
        this.currentHealth = maxHealth;
        this.currentCrew = maxCrew;

        this.id = ++idCounter;
    }

    /**
     * Checks if the Starship is still under repair
     * @return true or false
     */

    private boolean isUnderRepair() {
        if (skipTurns > 0) {
            skipTurns--;
            return true;
        }
        return false;
    }

    /**
     * Moves the Starship to a new sector provided it is not docked or under repair
     * @param newSector
     */

    public void moveSector(int newSector) {
        if (isUnderRepair()) return;
        if (isDocked) return;

        this.sector = newSector;
    }

    /**
     * Attacks the chosen target after checking that the Starship is not under repair, not docked, in the same sector and not in the same fleet
     * @param target to attack
     */

    public void attack(Targetable target) {
        if (isUnderRepair()) return;
        if (isDocked) return;
        if (this.sector != target.getSector()) return;
        if (Objects.equals(this.fleet, target.getFleet())) return;

        int damage = Math.max(getCurrentAtkStrength() - target.getCurrentDefence(), 5);
        target.takeDamage(damage);
    }

    /**
     * Docks Starship with chosen Starbase provided it is in the same sector and fleet, and the ship is not currently docked or under repair
     * @param base to dock with
     * @see Starbase#dockShip(Starship)
     */

    public void dockWithStarbase(Starbase base) {
        if (isUnderRepair()) return;
        if (isDocked) return;
        if (this.sector != base.getSector()) return;
        if (!Objects.equals(this.fleet, base.getFleet())) return;

        this.dockedBase = base;
        this.isDocked = true;
        base.dockShip(this);
    }

    /**
     * Undocks a Ship from its Starbase provided it is not under repair
     * @see Starbase#unDockShip(Starship)
     */

    public void unDockWithStarbase() {
        if (isUnderRepair()) return;
        if (dockedBase != null) {
            dockedBase.unDockShip(this);
            dockedBase = null;
        }
        isDocked = false;
    }

    /**
     * Checks to make sure the ship is docked before assigning the number of turns to skip based off the current health percentage and restoring max health and crew
     */

    public void repair() {
        if (!isDocked) return;

        double healthRatio = ((double) currentHealth / (double) maxHealth);
        double healthPercentage = healthRatio * 100;

        if (healthPercentage < 25) skipTurns = 4;
        else if (healthPercentage < 50) skipTurns = 3;
        else if (healthPercentage < 75) skipTurns = 2;
        else if (healthPercentage < 100) skipTurns = 1;
        else skipTurns = 1;

        currentHealth = maxHealth;
        currentCrew = maxCrew;
    }

    /**
     * Check if the Starship is docked
     * @return isDocked
     */

    public boolean isDocked() {
        return isDocked;
    }

    /**
     * Calculates and returns the current attack strength
     * @return currentAtkStrength
     */

    public int getCurrentAtkStrength() {
        currentAtkStrength = (int) Math.ceil(maxAtkStrength * ((double) currentCrew / maxHealth));
        return currentAtkStrength;
    }

    /**
     * Sets the Fleet that the Starship belongs to
     * @param fleet
     * @see Fleet#addShip(Starship) 
     */

    @Override
    public void setFleet(String fleet) {
        this.fleet = fleet;
    }

    /**
     * Gets the Fleet that the Starship belongs to
     * @return fleet
     */

    @Override
    public String getFleet() {
        return fleet;
    }

    /**
     * Gets the Sector that the Starship is located in
     * @return sector
     */

    @Override
    public int getSector() {
        return sector;
    }

    /**
     * Calculates and returns the current defence strength
     * @return currentDefStrength
     */

    @Override
    public int getCurrentDefence() {
        currentDefStrength = (int) Math.floor((double) maxDefStrength * ((currentHealth + currentCrew)) / (maxHealth + maxCrew));
        return currentDefStrength;
    }

    /**
     * Gets the current health
     * @return currentHealth
     */

    @Override
    public int getCurrentHealth() {
        return currentHealth;
    }

    /**
     * Calculates the current health and the crew lost/current crew of the ship after taking damage
     * @param damage of damage to take
     */

    @Override
    public void takeDamage(int damage) {
        currentHealth -= damage;
        if (currentHealth < 0) currentHealth = 0;

        int crewLost = (int) Math.ceil(((double) damage / maxHealth) * currentCrew);
        currentCrew -= crewLost;
        if (currentCrew < 1) currentCrew = 1;
    }

    /**
     * Checks if Starship is destroyed by checking if current health <= 0
     * @return true or false
     */

    @Override
    public boolean isDestroyed() {
        return currentHealth <= 0;
    }

    @Override
    public String toString() {
        return "Starship{" +
                "maxAtkStrength=" + maxAtkStrength +
                ", maxDefStrength=" + maxDefStrength +
                ", maxCrew=" + maxCrew +
                ", maxHealth=" + maxHealth +
                ", currentHealth=" + currentHealth +
                ", currentCrew=" + currentCrew +
                ", currentAtkStrength=" + currentAtkStrength +
                ", currentDefStrength=" + currentDefStrength +
                ", fleet='" + fleet + '\'' +
                ", sector=" + sector +
                ", skipTurns=" + skipTurns +
                ", isDocked=" + isDocked +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Starship starship = (Starship) o;
        return id == starship.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}