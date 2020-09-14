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

    public void receiveDamage(int damage){
        this.health -= damage;
        if(this.shield > 0) this.shield--;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName().split("@")[0] + " {" +
                "health=" + health +
                ", speed=" + speed +
                ", shield=" + shield +
                '}';
    }
}
