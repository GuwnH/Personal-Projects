/**
File: Cell.java
Author: Hesed Guwn
Date: 10/04/2022
**/

import java.awt.Graphics;
import java.awt.Color;

public class Cell 
{
    //Field rowIDX/colIDX is the index of the row/column of the cell, value is the integer value of the cell, boolean lock is used to tell if the cell is locked/changeable
    private int rowIDX;
    private int colIDX;
    private int value;
    private boolean lock;

    //Default constructor that sets all fields to be 0 and false
    public Cell()
    {
        this.rowIDX = 0;
        this.colIDX = 0;
        this.value = 0;
        this.lock = false;
    }

    //Constructor that sets row, column, and value to the given arguments, but makes lock false
    public Cell(int row, int col, int value)
    {
        this.rowIDX = row;
        this.colIDX = col;
        this.value = value;
        this.lock = false;
    }

    //Constructor that sets row, column, value, and lock to the given arguments
    public Cell(int row, int col, int value, boolean locked)
    {
        this.rowIDX = row;
        this.colIDX = col;
        this.value = value;
        this.lock = locked;
    }

    //Returns the int row index of the cell
    public int getRow()
    {
        return this.rowIDX;
    }

    //Returns the int column index of the cell
    public int getCol()
    {
        return this.colIDX;
    }

    //Returns the int value of the cell
    public int getValue()
    {
        return this.value;
    }

    //Sets the value of the cell to be the given int parameter
    public void setValue(int newVal)
    {
        this.value = newVal;
    }

    //Returns the boolean lock of the cell
    public boolean isLocked()
    {
        return this.lock;
    }

    //Sets the lock boolean of the cell to be the given boolean parameter
    public void setLocked(boolean lock)
    {
        this.lock = lock;
    }

    //Returns the string representation of the cell
    public String toString()
    {
        return "" + this.value;
    }

    //Draws the gui representation of the cell
    public void draw(Graphics g, int x, int y, int scale)
    {
        char toDraw = (char) ((int) '0' + value);
        g.setColor(lock? Color.BLUE : Color.RED);
        g.drawChars(new char[] {toDraw}, 0, 1, x, y);
    }
}
