package markodojkic.warships;

/**
 * @author Марко Дојкић
 */

public abstract class BattleShip extends Ship{
    private int damage;

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void Attack(Ship ship){
        ship.getDamage(damage);
    }

    public BattleShip(int health, int speed, int damage, int shield) {
        this.setHealth(health);
        this.setDamage(damage);
        this.setSpeed(speed);
        this.setShield(shield);
    }
}
