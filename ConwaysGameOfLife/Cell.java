/**
File: Cell.java
Author: Hesed Guwn
Date: 09/23/2022
Project02
Course: CS231 B
**/

import java.util.ArrayList;

public class Cell {

    /**
     * The status of the Cell.
     */
    private boolean alive;

    /**
     * Constructs a dead cell.
     */
    public Cell() {
        this.alive = false;
    }

    /**
     * Constructs a cell with the specified status.
     * 
     * @param status a boolean to specify if the Cell is initially alive
     */
    public Cell(boolean status) {
        this.alive = status;
    }

    /**
     * Returns whether the cell is currently alive.
     * 
     * @return whether the cell is currently alive
     */
    public boolean getAlive() {
        return this.alive;
    }

    /**
     * Sets the current status of the cell to the specified status.
     * 
     * @param status a boolean to specify if the Cell is alive or dead
     */
    public void setAlive(boolean status) {
        this.alive = status;
    }

    /**
     * Updates the state of the Cell.
     * 
     * If this Cell is alive and if there are 2 or 3 alive neighbors,
     * this Cell stays alive. Otherwise, it dies.
     * 
     * If this Cell is dead and there are 3 alive neighbors,
     * this Cell comes back to life. Otherwise, it stays dead.
     * 
     * @param neighbors An ArrayList of Cells
     */
    public void updateState(ArrayList<Cell> neighbors) 
    {
        //Checks for how many live neighboring cells there are
        int countAlive = 0;
        for(int i = 0; i < neighbors.size(); i++)
        {
            if(neighbors.get(i).getAlive() == true)
            {
                countAlive += 1;
            }
        }

        //Checks for situations when chosen cell is alive
        if(this.getAlive() == true)
        {
            if(countAlive == 2 || countAlive == 3)
            {
                this.alive = true;
            }
            else
            {
                this.alive = false;
            }
        }
        //Checks for situations when chosen cell is dead
        else if(this.getAlive() == false)
        {
            if(countAlive == 3)
            {
                this.alive = true;
            }
            else
            {
                this.alive = false;
            }
        }
    }

    /**
     * Returns a String representation of this Cell.
     * 
     * @return 1 if this Cell is alive, otherwise 0.
     */
    public String toString() {
        if(this.alive == true)
        {
            return "1";
        }
        else
        {
            return "0";
        }
    }
}
