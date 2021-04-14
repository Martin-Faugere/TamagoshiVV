package tamagoshis;

public class GrosJoueur extends Tamagoshi {

    public GrosJoueur(String name) {
        super(name);
    }

    @Override
    public boolean consommeFun() {
        fun--;
        return super.consommeFun();
    }

    public static Tamagoshi create(String name) {
        return new GrosJoueur(name);
    }
}