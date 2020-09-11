package markodojkic.warships;

import java.util.ArrayList;

/**
 * @author Марко Дојкић
 */

public class CommandShip extends BattleShip{
    private final ArrayList<Ship> fleet = new ArrayList<>();

    public void assignToFleet(Ship ship) {
        this.fleet.add(ship);
    }

    public ArrayList<Ship> getFleet() {
        return fleet;
    }
    
    public CommandShip(int speed) {
        super(1000, speed, 150, 20);
    }

    @Override
    public void getDamage(int damage) {
        super.getDamage(damage-this.getShield());
    }

    @Override
    public String toString() {
        return ("( ID: " + System.identityHashCode(this) + " Health: " + this.getHealth() + " Shield: " + this.getShield() + " Damage: " + this.getDamage() + " Speed: " + this.getSpeed() + " Fleet: " + fleet.toString() + ")\n");
    }
}
