package jeu;

import tamagoshis.Tamagoshi;
import tamagoshis.GrosJoueur;
import tamagoshis.GrosMangeur;
import graphic.TamaJPanel;
import util.Utilisateur;

import javax.swing.*;
import java.awt.geom.Dimension2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.awt.*;

public class Game extends JFrame {
    private final List<Tamagoshi> initialTamagoshis;
    private final List<Tamagoshi> onRunTamagoshis;
    private final List<String> tamagoshiPossibleName;
    private final JTextArea mainPanel;
    private int cycle;

    public Game() {
        initialTamagoshis = new ArrayList<>();
        onRunTamagoshis = new ArrayList<>();
        tamagoshiPossibleName = new ArrayList<>();
        tamagoshiPossibleName.add("Coralie");
        tamagoshiPossibleName.add("Odé");
        tamagoshiPossibleName.add("Nath");
        tamagoshiPossibleName.add("Mamo");
        tamagoshiPossibleName.add("Colette");
        tamagoshiPossibleName.add("Noémie");
        tamagoshiPossibleName.add("Mélanie");
        tamagoshiPossibleName.add("Lorianne");
        tamagoshiPossibleName.add("Lola");
        tamagoshiPossibleName.add("Simonne");
        mainPanel = new JTextArea();
        cycle = 1;
        initialisation();
    }

    //crée un game
    public void newGame() {
        print("------------Cycle n°0-------------");
        setVisible(true);
        createPanels();
    }

    //l'initialise
    private void initialisation() {
        randomClassToTam(getNbTam());
        onRunTamagoshis.addAll(initialTamagoshis);
        setSize(300, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new JScrollPane(mainPanel));
    }

    //recup nb tams
    private int getNbTam() {
        int nbTam = 0;
        while (nbTam < 3 || nbTam > 8) try {
            nbTam = Integer.parseInt(JOptionPane.showInputDialog(
                    "Nombre de tamagoshis souhaités (>2,<9) :", "4"));
        } catch (NumberFormatException var11) {
            JOptionPane.showMessageDialog(null,
                    "Choisissez un nombre compris entre 3 et 8", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        return nbTam;
    }

    //crée Tams spéciaux
    private void randomClassToTam(int nbTam) {
        String nameTamagoshi;
        for (int i = 0; i < nbTam; i++) {
            nameTamagoshi = tamagoshiPossibleName.get(Utilisateur.randomizer(0, tamagoshiPossibleName.size() - 1));
            if (Utilisateur.randomizer(0, 1) == 0)
                initialTamagoshis.add(GrosMangeur.create(nameTamagoshi));
            else
                initialTamagoshis.add(GrosJoueur.create(nameTamagoshi));
        }
    }

    //crée panels
    private void createPanels() {
        Point p = new Point();
        TamaJPanel tamaJPanel;
        Dimension2D screenDimension = new Dimension(
                Toolkit.getDefaultToolkit().getScreenSize().width,
                Toolkit.getDefaultToolkit().getScreenSize().height);
        for (Tamagoshi tam : onRunTamagoshis) {
            tamaJPanel = new TamaJPanel(tam, p);
            tamaJPanel.setGame(this);
            tam.setTamaJPanel(tamaJPanel);
            if (xIsNotOverflowing(p, screenDimension, tamaJPanel))
                p.x += tamaJPanel.getSize().width + 20;
            else {
                p.y += screenDimension.getHeight() - tamaJPanel.getSize().height - 50;
                p.x = 0;
            }
            tam.parle();
            print(tamaJPanel.getEtatTam());
        }
    }

    //vérif que pnel ne dépasse pas de l'écran
    private boolean xIsNotOverflowing(Point p, Dimension2D screenDimension, TamaJPanel tamaJPanel) {
        return p.x <= screenDimension.getWidth() - tamaJPanel.getWidth() * 2;
    }

    //nouvelle manche
    public void newCycle() {
        Tamagoshi tamagoshi;
        TamaJPanel tamaJPanel;
        print("------------------Cycle n°" + cycle + "------------------");
        Iterator<Tamagoshi> tamagoshiIterator = onRunTamagoshis.iterator();
        while (tamagoshiIterator.hasNext()) {
            tamagoshi = tamagoshiIterator.next();
            tamaJPanel = tamagoshi.getTamaJPanel();

            if (isTamAlive(tamagoshi)) {
                tamagoshi.parle();
                print(tamaJPanel.getEtatTam());
                enableMangerAndJouerBtns(tamaJPanel);
            } else {
                print(tamaJPanel.getEtatTam());
                tamagoshiIterator.remove();
                disableAllBtns(tamaJPanel);
            }
        }
        cycle++;
        if (onRunTamagoshis.isEmpty())
            result();
    }

    //active bouton manger et jouer mais pas suivant
    private void enableMangerAndJouerBtns(TamaJPanel tamaJPanel) {
        tamaJPanel.enableBtnSuivant(false);
        tamaJPanel.enableBtnManger(true);
        tamaJPanel.enableBtnJouer(true);
    }

    //desac tous boutons
    private void disableAllBtns(TamaJPanel tamaJPanel) {
        tamaJPanel.enableBtnJouer(false);
        tamaJPanel.enableBtnManger(false);
        tamaJPanel.enableBtnSuivant(false);
    }

    //active bouton suivant
    public void enableNextBtn(boolean alreadyEat, boolean alreadyPlay) {
        if (alreadyEat && alreadyPlay)
            for (Tamagoshi tam : onRunTamagoshis)
                tam.getTamaJPanel().enableBtnSuivant(true);
    }

    //verif tamagoshi vivant
    private boolean isTamAlive(Tamagoshi tamagoshi) {
        return tamagoshi.consommeEnergie() && tamagoshi.consommeFun() && !tamagoshi.vieillit();
    }

    //appelle tam mange
    public void eatTam(Tamagoshi t) {
        t.mange();
        print(t.getTamaJPanel().getEtatTam());
        for (Tamagoshi tam : onRunTamagoshis)
            tam.getTamaJPanel().enableBtnManger(false);
    }

    //appelle tam joue
    public void playTam(Tamagoshi t) {
        t.joue();
        print(t.getTamaJPanel().getEtatTam());
        for (Tamagoshi tam : onRunTamagoshis)
            tam.getTamaJPanel().enableBtnJouer(false);
    }

    //compte score
    private int score() {
        int score = 0;
        for (Tamagoshi tam : initialTamagoshis)
            score += tam.getAge();
        return score * 100 / (Tamagoshi.getLifeTime() * initialTamagoshis.size());
    }

    //affiche résult
    private void result() {
        print("\n-------------Fin------------");
        for (Tamagoshi tam : initialTamagoshis)
            if (tam.getAge() == Tamagoshi.getLifeTime())
                print(" Le " + tam.getClass().getSimpleName() + " " + tam.getName() + " est vivant !!!");
            else
                print(" Le " + tam.getClass().getSimpleName() + " " + tam.getName() + " est mort...");
        print("Difficulté : " + initialTamagoshis.size() + "\nScore : " + score() + "%");
    }

    //déssine
    private void print(String i) {
        mainPanel.append(i + "\n");
        mainPanel.setCaretPosition(mainPanel.getDocument().getLength());
    }
}
