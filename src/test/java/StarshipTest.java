import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This is the JUnit Testing class
 * @author Oscar Allen
 */
public class StarshipTest {
    @Test
    public void testStarshipTakesDamage() {
        Starship ship = new Starship(1);

        int initialHealth = ship.getCurrentHealth();
        ship.takeDamage(20);

        assertEquals(initialHealth - 20, ship.getCurrentHealth());
    }

    @Test
    public void testHealthNeverBelowZero() {
        Starship ship = new Starship(1);
        ship.takeDamage(1000);

        assertEquals(0, ship.getCurrentHealth());
    }

    @Test
    public void testAttackReducesHealth() {
        Fleet attackers = new Fleet("Attacker", 1, 1);
        Fleet defenders = new Fleet("Defender", 1, 1);

        Starship attacker = attackers.getShips().getFirst();
        Starship target = defenders.getShips().getFirst();
        Starbase targetBase = defenders.getStarbases().getFirst();

        int targetHealthBefore = target.getCurrentHealth();
        attacker.attack(target);

        assertTrue(target.getCurrentHealth() < targetHealthBefore);

        int targetBaseHealthBefore = targetBase.getCurrentHealth();
        attacker.attack(targetBase);

        assertTrue(targetBase.getCurrentHealth() < targetBaseHealthBefore);
    }

    @Test
    public void testFleetAttackReducesHealth() {
        Fleet attackers = new Fleet("Attacker", 1, 1);
        Fleet defenders = new Fleet("Defender", 1, 1);

        Starship target = defenders.getShips().getFirst();
        Starbase targetBase = defenders.getStarbases().getFirst();

        int targetHealthBefore = target.getCurrentHealth();
        attackers.attackTarget(target);

        assertTrue(target.getCurrentHealth() < targetHealthBefore);

        int targetBaseHealthBefore = targetBase.getCurrentHealth();
        attackers.attackTarget(targetBase);

        assertTrue(targetBase.getCurrentHealth() < targetBaseHealthBefore);
    }

    @Test
    public void testRepairRestoresHealth() {
        Starship ship = new Starship(1);
        Starbase base = new Starbase(1);

        int health = ship.getCurrentHealth();
        ship.takeDamage(50);
        ship.dockWithStarbase(base);
        ship.repair();

        assertEquals(health, ship.getCurrentHealth());
    }

    @Test
    public void cannotMoveWhileDocked() {
        Starship ship = new Starship(1);
        Starbase base = new Starbase(1);
        ship.dockWithStarbase(base);

        int sector = ship.getSector();
        assertEquals(1, sector);

        ship.moveSector(2);

        assertEquals(1, ship.getSector());
    }

    @Test
    public void cannotAttackWhileDocked() {
        Fleet attackers = new Fleet("Attacker", 1, 1);
        Fleet defenders = new Fleet("Defender", 1, 1);

        Starship attacker = attackers.getShips().getFirst();
        Starship target = defenders.getShips().getFirst();
        Starbase attackerBase = attackers.getStarbases().getFirst();

        attacker.dockWithStarbase(attackerBase);
        int targetHealth = target.getCurrentHealth();
        attacker.attack(target);

        assertEquals(targetHealth, target.getCurrentHealth());
    }

    @Test
    public void checkDockingWorksProperly() {
        Starbase base = new Starbase(1);
        Starship ship = new Starship(1);

        ship.dockWithStarbase(base);

        List<Starship> dockedShips = base.getDockedShips();
        assertTrue(dockedShips.contains(ship));
    }

    @Test
    public void checkUndockingWorksProperly() {
        Starbase base = new Starbase(1);
        Starship ship = new Starship(1);

        ship.dockWithStarbase(base);
        ship.unDockWithStarbase();

        List<Starship> dockedShips = base.getDockedShips();
        assertFalse(dockedShips.contains(ship));
    }

    @Test
    public void repairCausesSkippedTurns() {
        Starship ship = new Starship(1);
        Starbase base = new Starbase(1);

        ship.takeDamage(15);
        ship.dockWithStarbase(base);

        ship.repair();
        ship.unDockWithStarbase();
        assertTrue(ship.isDocked());

        ship.unDockWithStarbase();
        assertFalse(ship.isDocked());
    }

    @Test
    public void shipOnlyAttacksValidTargets() {
        Fleet friendlies = new Fleet("Attacker", 1, 1);

        Starship friendly = friendlies.getShips().getFirst();
        int friendlyHealth = friendly.getCurrentHealth();
        Starbase friendlyBase = friendlies.getStarbases().getFirst();
        int friendlyBaseHealth = friendlyBase.getCurrentHealth();

        friendly.attack(friendly);
        assertEquals(friendlyHealth, friendly.getCurrentHealth());

        friendly.attack(friendlyBase);
        assertEquals(friendlyBaseHealth, friendlyBase.getCurrentHealth());
    }

    @Test
    public void fleetOnlyAttacksValidTargets() {
        Fleet friendlies = new Fleet("Attacker", 1, 1);

        Starship friendly = friendlies.getShips().getFirst();
        int friendlyHealth = friendly.getCurrentHealth();
        Starbase friendlyBase = friendlies.getStarbases().getFirst();
        int friendlyBaseHealth = friendlyBase.getCurrentHealth();

        friendlies.attackTarget(friendly);
        assertEquals(friendlyHealth, friendly.getCurrentHealth());

        friendlies.attackTarget(friendlyBase);
        assertEquals(friendlyBaseHealth, friendlyBase.getCurrentHealth());
    }

    @Test
    public void mobiliseMovesFleetCorrectly() {
        Fleet attackers = new Fleet("Attacker", 1, 2);

        Starbase base = attackers.getStarbases().getFirst();
        Starship ship1 = attackers.getShips().get(0);
        Starship ship2 = attackers.getShips().get(1);

        int ship1SectorBefore = ship1.getSector();
        int ship2SectorBefore = ship2.getSector();

        ship1.dockWithStarbase(base);

        attackers.mobilise(2);
        assertEquals(ship1SectorBefore, ship1.getSector());
        assertNotEquals(ship2SectorBefore, ship2.getSector());
    }

    @Test
    public void integrationTest() {
        Fleet player1Fleet = new Fleet("Player 1", 1, 3);
        Fleet player2Fleet = new Fleet("Player 2", 2, 3);

        player1Fleet.mobilise(2);
        assertEquals(2, player1Fleet.getShips().getFirst().getSector());

        Starbase p2Base = player2Fleet.getStarbases().getFirst();
        List<Starship> p2Ships = player2Fleet.getShips();

        p2Ships.get(0).dockWithStarbase(p2Base);
        assertTrue(p2Ships.get(0).isDocked());
        p2Ships.get(1).dockWithStarbase(p2Base);
        assertTrue(p2Ships.get(1).isDocked());

        Starship p2Ship3 = p2Ships.get(2);
        int p2Ship3HealthBefore = p2Ship3.getCurrentHealth();

        List<Starship> p1Ships = player1Fleet.getShips();
        Starship p1Ship1 = p1Ships.getFirst();
        p1Ship1.attack(p2Ship3);
        p1Ship1.attack(p2Ship3);
        assertNotEquals(p2Ship3HealthBefore, p2Ship3.getCurrentHealth());

        p2Ship3.dockWithStarbase(p2Base);
        p2Ship3.repair();
        assertEquals(p2Ship3HealthBefore, p2Ship3.getCurrentHealth());

        while (!p2Base.isDestroyed()) {
            player1Fleet.attackTarget(p2Base);
        }
        assertTrue(p2Base.isDestroyed());
    }
}
