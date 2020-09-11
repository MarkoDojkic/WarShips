package markodojkic.warships;

/**
 * @author Марко Дојкић
 */

public abstract class Cargo extends Ship{
    private int capacity;

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Cargo(int healt, int speed, int shield, int capacity) {
        this.setHealth(healt);
        this.setSpeed(speed);
        this.setShield(shield);
        this.setCapacity(capacity);
    }
}
