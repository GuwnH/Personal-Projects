/**
File: Landscape.java
Author: Hesed Guwn
Date: 09/23/2022
Project02
Course: CS231 B
**/

package CS231.hsguwn25Project02;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class Landscape {

    /**
     * The underlying grid of Cells for Conway's Game
     */
    private Cell[][] landscape;

    /**
     * The original probability each individual Cell is alive
     */
    private double initialChance;

    //Random number generator to dictate if a cell should die or live
    private Random rand = new Random();

    //The rows and columns of Landscape
    private int rows;
    private int columns;

    /**
     * Constructs a Landscape of the specified number of rows and columns.
     * All Cells are initially dead.
     * 
     * @param rows    the number of rows in the Landscape
     * @param columns the number of columns in the Landscape
     */
    public Landscape(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.landscape = new Cell[this.rows][this.columns];
        this.initialChance = 0.5;
        reset();
    }

    /**
     * Constructs a Landscape of the specified number of rows and columns.
     * Each Cell is initially alive with probability specified by chance.
     * 
     * @param rows    the number of rows in the Landscape
     * @param columns the number of columns in the Landscape
     * @param chance  the probability each individual Cell is initially alive
     */
    public Landscape(int rows, int columns, double chance) {
        this.rows = rows;
        this.columns = columns;
        this.landscape = new Cell[this.rows][this.columns];
        this.initialChance = chance;
        
        reset();
    }

    /**
     * Recreates the Landscape according to the specifications given
     * in its initial construction.
     */
    public void reset() 
    {
        for(int i=0; i<this.landscape.length; i++)
        {
            for(int j=0; j < this.landscape[i].length; j++)
            {
                if(this.initialChance > rand.nextDouble(0,1.0))
                {
                    this.landscape[i][j] = new Cell(true);
                }
                else
                {
                    this.landscape[i][j] = new Cell(false);
                }
            }
        }
    }

    /**
     * Returns the number of rows in the Landscape.
     * 
     * @return the number of rows in the Landscape
     */
    public int getRows() {
        return this.rows;
    }

    /**
     * Returns the number of columns in the Landscape.
     * 
     * @return the number of columns in the Landscape
     */
    public int getCols() {
        return this.columns;
    }

    /**
     * Returns the Cell specified the given row and column.
     * 
     * @param row the row of the desired Cell
     * @param col the column of the desired Cell
     * @return the Cell specified the given row and column
     */
    public Cell getCell(int row, int col) {
        return this.landscape[row][col];
    }

    /**
     * Returns a String representation of the Landscape.
     */
    public String toString() {
        String result = "";
        for(int r = 0; r < this.landscape.length; r ++)
        {
            for(int c=0; c < this.landscape[r].length; c++)
            {
                result += "" + this.landscape[r][c] + " ";
            }
            result += "\n";
        }
        return result;

    }

    /**
     * Returns an ArrayList of the neighboring Cells to the specified location.
     * 
     * @param row the row of the specified Cell
     * @param col the column of the specified Cell
     * @return an ArrayList of the neighboring Cells to the specified location
     */
    public ArrayList<Cell> getNeighbors(int row, int col) 
    {
        //Creates a list to collect cells that are adjacent
        ArrayList<Cell> neighbors = new ArrayList<Cell>(8);

        //Iterates from below and above of the cell
        for(int r = row - 1; r < row + 2; r++)
        {
            //Iterates from the left and right of the cell
            for(int c = col-1; c < col + 2; c++ )
            {
                //Checks for bounds
                if(r > -1 && r < this.landscape.length && c > -1 && c < this.landscape[r].length)
                {
                    //Ignores the actual target cell
                    if(r == row && c == col)
                    {
                        continue;
                    }
                    //Adds neighbors
                    else
                    {
                        neighbors.add(this.landscape[r][c]);
                    }
                }
            }
        }
        return neighbors;
    }

    /**
     * Advances the current Landscape by one step. 
     */
    public void advance() 
    {
        //Creates a temporary copy of current landscape
        Cell[][] temp = new Cell[this.rows][this.columns];
        for(int i=0; i<temp.length; i++)
        {
            for(int j=0; j < temp[i].length; j++)
            {
                temp[i][j] = new Cell(this.landscape[i][j].getAlive());
            }
        }

        //Iterates through the grid and updates the state of each cell
        for(int i=0; i<temp.length; i++)
        {
            for(int j=0; j < temp[i].length; j++)
            {
                ArrayList<Cell> neighbors = getNeighbors(i, j);
                temp[i][j].updateState(neighbors);
            }
        }

        //Makes the copy the new landscape
        this.landscape = temp;
    }

    /**
     * Draws the Cell to the given Graphics object at the specified scale.
     * An alive Cell is drawn with a black color; a dead Cell is drawn gray.
     * 
     * @param g     the Graphics object on which to draw
     * @param scale the scale of the representation of this Cell
     */
    public void draw(Graphics g, int scale) {
        for (int x = 0; x < getRows(); x++) {
            for (int y = 0; y < getCols(); y++) {
                g.setColor(getCell(x, y).getAlive() ? Color.BLACK : Color.gray);
                g.fillOval(x * scale, y * scale, scale, scale);
            }
        }
    }

    public static void main(String[] args) {
        Landscape grid = new Landscape(10, 10);
        System.out.println(grid);
        System.out.println(grid.getNeighbors(5,5));
    }
}