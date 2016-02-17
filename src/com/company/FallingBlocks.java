package com.company;

import java.awt.*;
import java.util.ArrayList;

public class FallingBlocks implements Item {

    private ArrayList<FallingBlock> blocks;

    public FallingBlocks() {
        this.blocks = new ArrayList<FallingBlock>();
    }

    public void dropNewBlock() {

        FallingBlock newBlock = new FallingBlock();
        this.blocks.add(newBlock);

        if (Dodger.gameState == GameState.RUNNING) {//score is determined by the size of the block

            if (Dodger.doubleOrSingle == dubs.DOUBLE) { //final level dropping

                FallingBlock secondBlock = new FallingBlock();
                this.blocks.add(secondBlock);

                Dodger.score += (secondBlock.getSize() * 15);

            }

            Dodger.score += newBlock.getSize();
            
            if (Dodger.score > Dodger.highScoreRead) {
                Dodger.highScoreRead = Dodger.score;
            }
        }




    }

    public void draw(Graphics g) {

        for (FallingBlock block : this.blocks) {
            block.draw(g);
        }


    }

    //standard falling pace
    public void fall() {
        for (FallingBlock block : this.blocks) {
            block.fall();
        }
    }

    public void removeAll() {
        this.blocks.clear();
    }

    //stops out ArrayList from getting too big
    public void deleteIfOffScreen() {
        if (this.blocks.get(0).getY() > Dodger.CANVAS_HEIGHT) {
            this.blocks.remove(0);
        }
    }

    //does the avatar crash into any blocks?
    public boolean checkCrash(int x, int y, int size) {
        for (FallingBlock block : this.blocks) {
            if ( (x + size >= block.getX() && x <= (block.getX() + block.getSize()) ) &&
                    ( y <= block.getY() + block.getSize() && y >= (block.getY() - (50 / 3)) ) &&
                    !block.getHasCrashed()
                    ) {

                block.crash();
                return true;
            }
        }
        return false;
    }

}
