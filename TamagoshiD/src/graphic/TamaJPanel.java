package graphic;

import tamagoshis.Tamagoshi;
import jeu.Game;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;

public class TamaJPanel extends JFrame implements ActionListener {
    private Game game;
    private final AbstractButton btnSuivant;
    private final AbstractButton btnManger;
    private final AbstractButton btnJouer;
    private final Tamagoshi tamagoshi;
    private final JLabel etatTamagoshi;
    static boolean haveEat = false;
    static boolean havePlay = false;

    public TamaJPanel(Tamagoshi t, Point p) {
        tamagoshi = t;
        etatTamagoshi = new JLabel("");
        btnManger = new JButton("Manger");
        btnJouer = new JButton("Jouer");
        btnSuivant = new JButton("Suivant");
        btnSuivant.setEnabled(false);
        btnManger.addActionListener(this);
        btnJouer.addActionListener(this);
        btnSuivant.addActionListener(this);
        newWindow(p);
    }

    //crée fenetres
    private void newWindow(Point p) {
        JComponent panInformations = new JPanel(new FlowLayout());
        panInformations.add(etatTamagoshi);

        JComponent panButtons = new JPanel(new FlowLayout());
        panButtons.add(btnManger);
        panButtons.add(btnJouer);
        panButtons.add(btnSuivant);

        GridBagConstraints constraints = newConstraints();
        setLayout(new FlowLayout());
        setTitle(tamagoshi.getName());
        setSize(320, 150);
        setLocation(p);

        add(panInformations, constraints);
        add(panButtons, constraints);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);
    }

    private GridBagConstraints newConstraints() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets.bottom = 10;
        constraints.insets.right = 15;

        constraints.gridy = 0;
        constraints.gridwidth = GridBagConstraints.VERTICAL;
        return constraints;
    }

    //vérif chaque action pour avancer dans la manche
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Manger":
                game.eatTam(tamagoshi);
                haveEat = true;
                game.enableNextBtn(true, havePlay);
                break;
            case "Jouer":
                game.playTam(tamagoshi);
                havePlay = true;
                game.enableNextBtn(haveEat, true);
                break;
            case "Suivant":
                game.newCycle();
                haveEat = false;
                havePlay = false;
                break;
        }
    }

    public String getEtatTam() {
        return etatTamagoshi.getText();
    }

    public void setEtatTam(String e) {
        etatTamagoshi.setText(e);
    }

    public void setGame(Game g) {
        game = g;
    }

    //activent boutons

    public void enableBtnJouer(boolean e) {
        btnJouer.setEnabled(e);
    }

    public void enableBtnManger(boolean e) {
        btnManger.setEnabled(e);
    }

    public void enableBtnSuivant(boolean e) {
        btnSuivant.setEnabled(e);
    }
}