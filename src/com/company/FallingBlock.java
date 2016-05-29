package com.company;

import java.awt.*;
import java.util.Random;

public class FallingBlock implements Item {

    private int size;
    private int x;
    private int y;
    private int speed = 6;
    private int accel = 1;
    private Color color;
    private boolean hasCrashed = false; //ensures that the avatar can't crash into the same block twice

    public FallingBlock() {

        /*
        switch(Dodger.dropRate) {
            /*
            case 10:
                this.color = Color.WHITE;\
                break;
            case 9:
                this.color = Color.YELLOW;
                break;
            case 8:
                this.color = Color.ORANGE;
                break;
            case 7:
                this.color = Color.RED;
                break;
            case 6:
                this.color = Color.ORANGE;
                break;

            case 5:
                this.color = new Color (0, 0, 255);
                break;
            case 4:
                this.color = new Color (0, 127, 255);
                break;
            case 3:
                this.color = new Color (0, 255, 255);
                break;
            case 2:
                this.color = new Color (0, 255, 127);
                break;
            case 1:

                if (Dodger.doubleOrSingle == dubs.SINGLE) {
                    this.color = new Color (0, 255, 0);
                } else {
                    this.color = color.WHITE;
                }
                break;
        }
        */
        Random random = new Random();
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        this.color = new Color(r, g, b, 150);

        //makes the game less ridiculous when the drop rate is only 1
        if (Dodger.dropRate == 1) {
            if (Dodger.doubleOrSingle == dubs.SINGLE) {
                this.size = 50;
            } else {
                this.size = 10;
            }
        } else {
            this.size = new Random().nextInt(150) + 30;
        }
        this.y = 0 - size;
        this.x = new Random().nextInt(Dodger.CANVAS_WIDTH - this.size);

    }

    public void draw(Graphics g) {
        if (this.hasCrashed) {
            g.setColor(new Color(0, 255, 0, 150));
        } else {
            g.setColor(this.color);
        }

        g.fillOval(x, y, size, size);

    }

    public void fall() {
        this.y += speed;
        this.speed += accel;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getSize() {
        return this.size;
    }

    public void crash() { //has this particular block been crashed into yet? (for use when playing with multiple lives)
        this.hasCrashed = true;
    }

    public boolean getHasCrashed() {
        return this.hasCrashed;
    }

}
