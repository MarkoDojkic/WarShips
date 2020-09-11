package markodojkic.warships;

/**
 * @author Марко Дојкић
 */

public class BigShip extends BattleShip{
    public BigShip(int speed) {
        super(500, speed, 100, 0);
    }

    @Override
    public String toString() {
        return "BigShip@" + System.identityHashCode(this);
    }
}
