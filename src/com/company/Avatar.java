package com.company;

import java.awt.*;

public class Avatar implements Item {

    private int rectSize = 20;
    private int x = (Dodger.CANVAS_WIDTH / 2) - (this.rectSize / 2);
    private int y = (Dodger.CANVAS_HEIGHT / 2) + (Dodger.CANVAS_HEIGHT / 3);
    private int speed = 15;
    private Direction direction = Direction.STATIONARY;

    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillOval(x, y, this.rectSize, this.rectSize);
    }

    public void update() {
        this.move();
        this.changeIfBumps();
    }

    public void move() {
        switch(this.direction) {
            case LEFT:
                this.x -= this.speed;
                break;
            case RIGHT:
                this.x += this.speed;
                break;
            case STATIONARY:
                break;
        }

    }

    //changes direction if avatar hits wall
    public void changeIfBumps() {
        if (this.x <= 0) {
            this.x = 0;
            this.direction = Direction.RIGHT;
        } else if (this.x >= Dodger.CANVAS_WIDTH - this.rectSize) {
            this.x = Dodger.CANVAS_WIDTH - this.rectSize;
            this.direction = Direction.LEFT;
        }
    }


    public void changeDirection(Direction newDirection) {
        this.direction = newDirection;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getRectSize() {
        return this.rectSize;
    }

}
