package tamagoshis;

import util.Utilisateur;
import graphic.TamaJPanel;

public abstract class Tamagoshi {
    private final String name;
    private int age;
    private final int maxEnergy;
    protected int energy;
    private final int maxFun;
    protected int fun;
    private static final int lifeTime = 10;
    private TamaJPanel tamaJPanel;

    public Tamagoshi(String n) {
        name = n;
        age = 0;
        maxEnergy = Utilisateur.randomizer(5, 9);
        energy = Utilisateur.randomizer(3, 7);
        maxFun = Utilisateur.randomizer(5, 9);
        fun = Utilisateur.randomizer(3, 7);
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public static int getLifeTime() {
        return lifeTime;
    }

    //affiche infos
    @Override
    public String toString() {
        return name + " a " + age + " ans\nIl a " + energy + " d'énergie sur un max de " + maxEnergy;
    }

    //parle à chaque manche
    public void parle() {
        String str = "";
        if (energy <= 4 && fun <= 4)
            str += "* a faim et s'ennuie *";
        else if (energy <= 4)
            str += "* a faim  *";
        else if (fun <= 4)
            str += "* s'ennuie *";
        if (str.isEmpty())
            print("R.A.S");
        else
            print(str);
    }

    //prend de l'age a chaque manche
    public boolean vieillit() {
        age++;
        return age == lifeTime;
    }

    //différentes actions du tamagoshi
    public void mange() {
        if (energy < maxEnergy) {
            energy += Utilisateur.randomizer(1, 3);
            print("Miam !");
        } else
            print("J'ai pas envie de manger!");
    }

    public void joue() {
        if (fun < maxFun) {
            fun += Utilisateur.randomizer(1, 3);
            print("Ah c'est marrant!");
        } else
            print("je suis déjà mort de rire");
    }

    public boolean consommeEnergie() {
        energy--;
        if (isDead()) {
            print("* est mort de faim *");
            return false;
        } else
            return true;
    }

    public boolean consommeFun() {
        fun--;
        if (isDead()) {
            print("* est mort de fun *");
            return false;
        } else
            return true;
    }

    //affiche état
    private void print(String str) {
        tamaJPanel.setEtatTam(name + " : " + str);
    }

    //vérif vivant/mort
    public boolean isDead() {
        return energy <= 0;
    }

    public void setTamaJPanel(TamaJPanel tp) {
        tamaJPanel = tp;
    }

    public TamaJPanel getTamaJPanel() {
        return tamaJPanel;
    }
}