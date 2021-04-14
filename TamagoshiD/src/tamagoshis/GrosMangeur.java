package tamagoshis;

public class GrosMangeur extends Tamagoshi {

    public GrosMangeur(String name) {
        super(name);
    }

    @Override
    public boolean consommeEnergie() {
        energy--;
        return super.consommeEnergie();
    }

    public static Tamagoshi create(String name) {
        return new GrosMangeur(name);
    }
}
