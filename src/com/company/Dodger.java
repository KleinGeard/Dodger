package com.company;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;

public class Dodger extends JFrame {

    //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    //double width = screenSize.getWidth();
    //double height = screenSize.getHeight();



    private DrawCanvas canvas;
    public static final int CANVAS_HEIGHT = 768; //change to whatever you screen size is
    public static final int CANVAS_WIDTH = 1366;


    private static final int UPDATE_INTERVAL = 50;
    private ArrayList<Item> items;
    private Avatar avatar;
    private FallingBlocks fallingBlocks;
    private int dropTimer;
    private int accelTimer;
    public static int dropRate = 5;
    public static int emergencyBreaksLeft = 3;
    public static int numberOfCrashes = 0;
    public static int score = 0;
    public static GameState gameState = GameState.TITLE;
    public static Text text = new Text("HELLO!");
    private Text gameOverText;
    private File file = new File("src/com/company/DodgerHighscore.txt");
    private Scanner reader;
    public static int highScoreRead;
    public static dubs doubleOrSingle = dubs.SINGLE; //are two blocks drops at the given frame??

    public Dodger() {

        //System.out.println(width + " " + height);

        //initialise items
        this.setResizable(false);
        this.items = new ArrayList<Item>();
        this.avatar = new Avatar();
        this.dropTimer = 0;
        this.accelTimer = 0;
        this.gameOverText = new Text("GAME OVER!");
        this.gameOverText.fallOut();
        try {
            this.reader = new Scanner(file);
            this.highScoreRead = Integer.parseInt(reader.nextLine());
        } catch (Exception e) {

        }

        //add items
        this.items.add(this.avatar);
        this.items.add(this.text);
        this.items.add(this.gameOverText);

        //initialise canvas
        this.canvas = new DrawCanvas(this.items);
        this.canvas.setPreferredSize(new Dimension(this.CANVAS_WIDTH, this.CANVAS_HEIGHT));
        //Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        //this.canvas.setPreferredSize(new Dimension(dim));
        this.setContentPane(this.canvas);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setTitle("Dodger");
        this.setVisible(true);

        //key listener
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {

                switch (evt.getKeyCode()) {
                    case KeyEvent.VK_LEFT:

                        if (gameState == GameState.TITLE) {
                            avatar.changeDirection(Direction.LEFT);
                            titlePress();
                        } else if (gameState == GameState.RUNNING) {
                            avatar.changeDirection(Direction.LEFT);
                        }
                        break;

                    case KeyEvent.VK_RIGHT:

                        if (gameState == GameState.TITLE) {
                            avatar.changeDirection(Direction.RIGHT);
                            titlePress();
                        } else if (gameState == GameState.RUNNING) {
                            avatar.changeDirection(Direction.RIGHT);
                        }
                        break;

                    case KeyEvent.VK_DOWN:

                        if (gameState == GameState.TITLE) {
                            avatar.changeDirection(Direction.STATIONARY);
                            titlePress();
                        } else if (gameState == GameState.RUNNING) {
                            if (emergencyBreaksLeft > 0) {
                                avatar.changeDirection(Direction.STATIONARY);
                                emergencyBreaksLeft--;
                            }
                        } else if (gameState == GameState.GAME_OVER) {
                            gameOverPress();
                        }
                        break;

                }
            }



        });

        //thread
        Thread updateThread = new Thread() {
            public void run() {
                while (true) {
                    update();
                    repaint();
                    try {
                        Thread.sleep(UPDATE_INTERVAL);
                    } catch (InterruptedException e) {}
                }
            }
        }; //end thread
        updateThread.start();

    } //end constructor

    //happens when pressing any button while the GameState is GAME_OVER
    public void gameOverPress() {
        this.accelTimer = 0;
        this.dropTimer = 0;
        this.dropRate = 5;
        this.emergencyBreaksLeft = 3;
        this.score = 0;
        numberOfCrashes = 0;
        this.gameOverText.fallOut();
        this.fallingBlocks.removeAll();
        repaint();
        gameState = GameState.TITLE;
        doubleOrSingle = dubs.SINGLE;
    }

    //happens when pressing any button while the GameState is TITLE
    public void titlePress() {
        gameState = GameState.RUNNING;
        fallingBlocks = new FallingBlocks();
        items.add(fallingBlocks);
        this.text.fallOut();
        this.repaint();
    }

    //update everything
    public void update() {

        if (this.gameState == GameState.RUNNING) {

            this.timerLogic();
            this.fallingBlocks.fall();
            this.crashLogic();
            this.avatar.update();

        } else if (this.gameState == GameState.GAME_OVER) {

            this.timerLogic();
            this.fallingBlocks.fall();

        }

    }

    public void timerLogic() {
        dropTimer++;
        accelTimer++;

        //increases frequency of box drops every 100 box drops
        if (dropRate > 1) {
            if (accelTimer == dropRate * 100 && gameState == GameState.RUNNING) {
                dropRate --;
                accelTimer = 0;
            }
        } else {

            if (accelTimer == dropRate * 200 && gameState == GameState.RUNNING) {
                if (Dodger.doubleOrSingle == dubs.SINGLE) {
                    Dodger.doubleOrSingle = dubs.DOUBLE;
                }
                accelTimer = 0;
            }

        }

        /*
        if (accelTimer == dropRate * 100 && gameState == GameState.RUNNING) {
            if (dropRate > 1) {
                dropRate --;
            } else {
                if (Dodger.doubleOrSingle == dubs.SINGLE) {
                    Dodger.doubleOrSingle = dubs.DOUBLE;
                }
            }
            accelTimer = 0;
        }

        */

        //drops boxes at certain intervals
        if (dropTimer >= dropRate) {
            fallingBlocks.dropNewBlock();
            fallingBlocks.deleteIfOffScreen();
            dropTimer = 0;
        }

    }

    public void crashLogic() {
        boolean doesCrash = this.fallingBlocks.checkCrash(this.avatar.getX(), this.avatar.getY(), this.avatar.getRectSize());

        if (doesCrash) {
            this.numberOfCrashes++;
            if (this.numberOfCrashes >= 1) {
                try {
                    FileWriter writer = new FileWriter(file);
                    writer.write(this.highScoreRead + "");
                    writer.close();
                } catch(Exception e) {}
                this.gameOverText.fallIn();
                this.gameState = GameState.GAME_OVER;
            }
        }
    }

}
