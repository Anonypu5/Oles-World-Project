package no.Strohm.game2D.items.attributes;

/**
 * Created by Ole on 30/04/2014.
 */
public class SwordAttributes {

    enum Tiers {
        WOOD(1);


        int damage;

        private Tiers(int damage) {
            this.damage = damage;
        }
    }

}
