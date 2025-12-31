import java.util.List;

/**
 * This is the Main class
 * @author Oscar Allen
 */
public class Main {
    public static void main(String[] args) {
        Fleet player1Fleet = new Fleet("Player 1", 1, 3);
        Fleet player2Fleet = new Fleet("Player 2", 2, 3);
        System.out.println("Player 1 Fleet and Player 2 Fleet have joined the game.");
        System.out.println(player1Fleet);
        System.out.println(player2Fleet);

        player1Fleet.mobilise(2);
        System.out.println("Player 1 Fleet mobilises to Sector 2.");

        Starbase p2Base = player2Fleet.getStarbases().getFirst();
        List<Starship> p2Ships = player2Fleet.getShips();

        p2Ships.get(0).dockWithStarbase(p2Base);
        System.out.println("Player 2 has docked Ship 1 in its Starbase.");
        p2Ships.get(1).dockWithStarbase(p2Base);
        System.out.println("Player 2 has docked Ship 2 in its Starbase.");

        Starship p2Ship3 = p2Ships.get(2);

        List<Starship> p1Ships = player1Fleet.getShips();
        Starship p1Ship1 = p1Ships.getFirst();
        p1Ship1.attack(p2Ship3);
        System.out.println("Player 1 Ship 1 has attacked Player 2 Ship 3.");
        p1Ship1.attack(p2Ship3);
        System.out.println("Player 1 Ship 1 has attacked Player 2 Ship 3.");

        p2Ship3.dockWithStarbase(p2Base);
        System.out.println("Player 2 has docked Ship 3 in its Starbase.");
        p2Ship3.repair();
        System.out.println("Player 2 has repaired Player 2 Ship 3.");

        while (!p2Base.isDestroyed()) {
            player1Fleet.attackTarget(p2Base);
            System.out.println("Player 1 Fleet has attacked Player 2 Starbase.");
        }
        System.out.println("Player 1 Fleet has destroyed Player 2 Starbase.");

        System.out.println(player1Fleet);
        System.out.println(player2Fleet);
    }
}
