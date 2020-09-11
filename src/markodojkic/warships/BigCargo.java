package markodojkic.warships;

/**
 * @author Марко Дојкић
 */

public class BigCargo extends Cargo{
    public BigCargo(int speed) {
        super(200, speed, 10, 5000);
    }

    @Override
    public String toString() {
        return "BigCargo@" + System.identityHashCode(this);
    }
}
