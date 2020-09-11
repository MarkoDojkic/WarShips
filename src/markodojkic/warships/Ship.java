package markodojkic.warships;

/**
 * @author Марко Дојкић
 */

public abstract class Ship {
    private int health, speed, shield;

    public int getHealth() {
        return health;
    }

    public int getSpeed() {
        return speed;
    }

    public int getShield() {
        return shield;
    }
    
    public void setHealth(int health) {
        this.health = health;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setShield(int shield) {
        this.shield = shield;
    }
    
    public void getDamage(int damage) {
        this.health -= damage;
    }
}
