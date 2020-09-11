package markodojkic.warships;

/**
 *
 * @author marko.dojkic.18
 */
public class SmallShip extends BattleShip{
    public SmallShip(int speed) {
        super(300, speed, 50, 0);
    }

    @Override
    public String toString() {
        return "SmallShip@" + System.identityHashCode(this);
    }
}
