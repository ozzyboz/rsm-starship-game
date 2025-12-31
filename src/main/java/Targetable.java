/**
 * This is the Targetable interface
 * @author Oscar Allen
 */
public interface Targetable {
    /**
     * Gets the current health
     * @return currentHealth
     */
    int getCurrentHealth();

    /**
     * Gets the current defence strength
     * @return currentDefStrength
     */
    int getCurrentDefence();

    /**
     * Inflicts damage on an object by an integer amount
     * @param amount of damage to take
     */
    void takeDamage(int amount);

    /**
     * Checks if the objects health is equal to or less than zero
     * @return true or false
     */
    boolean isDestroyed();

    /**
     * Gets the current sector of the object
     * @return sector
     */
    int getSector();

    /**
     * Sets the current Fleet of a Starship or Starbase
     * @param fleet
     */
    void setFleet(String fleet);

    /**
     * Gets the current Fleet of a Starship or Starbase
     * @return String fleet
     */
    String getFleet();
}
