package no.Strohm.game2D.entity.mob;

/**
 * Created by Ole on 14/02/14.
 */
public class Stats {

    private int health;
    private int mana;
    private int xp;
    private int totalXp;
    private int level;

    private double speed;
    private int attackSpeed;

    public Stats(double speed, int attackSpeed) {
        this.speed = speed;
        this.attackSpeed = attackSpeed;
        setLevel(1);
        mana = getMaxMana();
        health = getMaxHealth();
        xp = 0;
        totalXp = 0;
    }

    private int getNeededXP() {
        return level * 90;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int lev) {
        level = lev;
    }

    public int getDamage() {
        return 3 + 2 * level;
    }

    public void addXp(int amount) {
        xp += amount;
        totalXp += amount;

        while (xp >= getNeededXP()) {
            xp -= getNeededXP();
            level++;
        }
    }

    public void hurt(int dmg) {
        health -= dmg;
    }

    public int getMaxHealth() {
        return 17 + 3 * level;
    }

    public void heal(int healAmt) {
        health += healAmt;
        if (health > getMaxHealth()) health = getMaxHealth();
    }

    public int getMaxMana() {
        return 20 + 10 * level;
    }

    public void addMana(int amt) {
        mana += amt;
        if (mana > getMaxMana()) health = getMaxMana();
    }

    public int takeMana(int amt) {
        int amount = (amt <= mana ? amt : mana);
        mana -= amount;
        return amount;
    }

    public int getXp() {
        return xp;
    }

    public double getSpeed() {
        return speed;
    }

    public int getAttackSpeed() {
        return attackSpeed;
    }

    public int getTotalXp() {
        return totalXp;
    }

    public int getHealth() {
        return health;
    }

    public int getMana() {
        return mana;
    }
}
