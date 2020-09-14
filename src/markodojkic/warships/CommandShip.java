package markodojkic.warships;

import java.util.ArrayList;

/**
 * @author Марко Дојкић
 */

public class CommandShip extends BattleShip{
    private final ArrayList<String> sCargos,bCargos,sBShips,bBShips;

    public CommandShip(int speed) {
        super(1000, speed, 150, 200);
        this.sCargos = new ArrayList<>();
        this.bCargos = new ArrayList<>();
        this.sBShips = new ArrayList<>();
        this.bBShips = new ArrayList<>();
    }

    public ArrayList<String> getsCargos() {
        return sCargos;
    }

    public ArrayList<String> getbCargos() {
        return bCargos;
    }

    public ArrayList<String> getsBShips() {
        return sBShips;
    }

    public ArrayList<String> getbBShips() {
        return bBShips;
    }

    @Override
    public String toString() {
        String fleet = "SmallCargoShips: " + this.sCargos.size() + ", BigCargoShips: " + this.bCargos.size() + ", SmallBattleShips: " + this.sBShips.size() + ", BigBattleShips: " + this.bBShips.size();
        return (" Health: " + this.getHealth() + " Shield: " + this.getShield() + " Damage: " + this.getDamage() + " Speed: " + this.getSpeed() + " Fleet: " + fleet + ")\n");
    }
}
