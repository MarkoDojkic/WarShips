package markodojkic.warships;

/**
 *
 * @author marko.dojkic.18
 */

public class SmallCargo extends Cargo{

    public SmallCargo(int speed) {
        super(100, speed, 0, 4000);
    }

    @Override
    public String toString() {
        return "SmallCargo@" + System.identityHashCode(this);
    }
}
