package com.company;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class DrawCanvas extends JPanel {

    private ArrayList<Item> items;

    public DrawCanvas(ArrayList<Item> items) {
        this.items = items;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.GRAY);

        for (Item i : this.items) {
            i.draw(g);
        }

        g.setFont(new Font("TimesRoman", Font.PLAIN, 11));
        g.setColor(Color.WHITE);
        g.drawString("Emergency breaks left: " + Dodger.emergencyBreaksLeft, 10, 20);
        //crashes used when playing with multiple lives
        //g.drawString("Crashes: " + Dodger.numberOfCrashes, 10, Dodger.CANVAS_HEIGHT - 20);
        g.drawString("Score: " + Dodger.score, 10, 30);
        //g.drawString("Highscore: " + Dodger.highScore, 10, Dodger.CANVAS_HEIGHT - 40);
        g.drawString("highscore: " + Dodger.highScoreRead, 10, 40);
    }
}
