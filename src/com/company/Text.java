package com.company;

import java.awt.*;

public class Text implements Item {

    private String out;
    private int fontSize = Dodger.CANVAS_WIDTH / 20;
    private int x;
    private int y = Dodger.CANVAS_HEIGHT / 2;


    public Text (String out) {
        this.out = out;
        //this code ensures that the text is displayed exactly in the middle of the screen
        this.x = ((Dodger.CANVAS_WIDTH / 2) - ((out.length() / 2) * (this.fontSize / 2)));
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
        g.drawString(this.out, this.x, this.y);
    }

    //removes text from screen without having to actually delete it from the items ArrayList
    public void fallOut() {
        this.y += (Dodger.CANVAS_HEIGHT * 2);
    }

    //displays it again
    public void fallIn() {
        this.y -= (Dodger.CANVAS_HEIGHT * 2);
    }

}
