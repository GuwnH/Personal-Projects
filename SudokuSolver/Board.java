/**
File: Board.java
Author: Hesed Guwn
Date: 10/04/2022
**/
import java.io.*;

import javax.lang.model.util.ElementScanner14;
import java.util.Random;
import java.awt.Color;
import java.awt.Graphics;

public class Board 
{
  //Field cellGrid is a 2D array of cells, SIZE is the length of the arrays, finished is a boolean that tells if the board is solved/notsolved
  private Cell[][] cellGrid;
  public static final int SIZE = 9;
  private boolean finished;
  
  //Default constructor that intializes cellGrid to be a board of 0s and finished to be false at first
  public Board()
  {
    this.cellGrid = new Cell[this.SIZE][this.SIZE];
    this.finished = false;

    for(int i=0; i<this.SIZE; i++)
    {
      for(int j=0; j<this.SIZE; j++)
      {
        this.cellGrid[i][j] = new Cell(i,j,0);
      }
    }
  }

  //Constructor with a given integer numLocked parameter that dictates the amount of locked cells at random locations of the board with random values
  public Board(int numLocked)
  {
    this.cellGrid = new Cell[this.SIZE][this.SIZE];
    this.finished = false;
    
    //Random number generators
    Random rand1 = new Random();
    Random rand2 = new Random();
    Random rand3 = new Random();

    //Initially builds the board with full 0s
    for(int i=0; i<this.SIZE; i++)
    {
      for(int j=0; j<this.SIZE; j++)
      {
        this.cellGrid[i][j] = new Cell(i,j,0);
      }
    }

    //While loop that iterates through the freshly made board while the amount of locked cells is not met
    while (this.numLocked() < numLocked) 
    {
      //randrow and randCol decide where the locked cell will be located
      int randRow = rand1.nextInt(1,9);
      int randCol = rand2.nextInt(1,9);
      
      //randValue decides a random value between 1-9
      int randValue = rand3.nextInt(1,10);

      //Uses the random variables to create a cell at the random locations with random value
      this.cellGrid[randRow][randCol] = new Cell(randRow,randCol,randValue, true);

      //Checks if the locked cell makes the board illegal
      if(this.validValue(randRow, randCol, this.cellGrid[randRow][randCol].getValue()) == false)
      {
        //While loop that runs until the specific cell gets a randomly generated value that is valid 
        while(this.validValue(randRow, randCol, this.cellGrid[randRow][randCol].getValue()) == false)
        {
          //Rerolls a random value between 1 and 9
          randValue = rand3.nextInt(1,10);
          //Unlocks cell to make sure it can set value
          this.cellGrid[randRow][randCol].setLocked(false);
          //Sets cell with new value and locks it
          this.cellGrid[randRow][randCol] = new Cell(randRow,randCol,randValue, true);
        }
      }
    }
  }

  //Returns the String representation of the board/cellGrid
  public String toString()
  {
    String accumulator = "";
    for(int i=0; i<this.SIZE; i++)
    {
      for(int j=0; j<this.SIZE; j++)
      {
        accumulator += this.cellGrid[i][j] + " ";
      }
      accumulator += "\n";
    }
    return accumulator;
  }

  //Returns the size of columns of board
  public int getCols()
  {
      return SIZE;
  }

  //Returns the size of rows of board
  public int getRows()
  {
    return SIZE;
  }

  //Returns the specific cell located at row r and column c
  public Cell get(int r, int c)
  {
    return this.cellGrid[r][c];
  }

  //Returns boolean locked of specific cell at row r and column c
  public boolean isLocked(int r, int c)
  {
    return this.cellGrid[r][c].isLocked();
  }

  //Returns integer number of cells locked in the board
  public int numLocked()
  {
    //Initial counter of locked cells
    int numLock = 0;
    //Iterates through the board and counts every cell that is locked 
    for(int i=0; i<this.SIZE; i++)
    {
      for(int j=0; j<this.SIZE; j++)
      {
        if(this.cellGrid[i][j].isLocked() == true)
        {
          numLock += 1;
        }
      }
    }
    return numLock;
  }

  //Returns the value of the specific cell at row r and column c
  public int value(int r, int c)
  {
    return this.cellGrid[r][c].getValue();
  }

  //Sets the value of the specfic cell at row r and column c if cell is unlocked
  public void set(int r, int c, int value)
  {
    if(this.cellGrid[r][c].isLocked() == false)
    {
      this.cellGrid[r][c].setValue(value);
    }
  }

  //Sets the value of the specfic cell at row r and column c if cell is unlocked and sets the lock of the cell to argument locked
  public void set(int r, int c, int value, boolean locked)
  {
    if(this.cellGrid[r][c].isLocked() == false)
    {
      this.cellGrid[r][c].setValue(value);
    }
    this.cellGrid[r][c].setLocked(locked);
  }

  //Reads a 9x9 textfile full of integers and sets the board to be the same as the textfile
  //Returns a boolean that states if the file was able to be read or not
  public boolean read(String filename) 
  {
    try {
      // assign to a variable of type FileReader a new FileReader object, passing filename to the constructor
      FileReader fr = new FileReader(filename);
      // assign to a variable of type BufferedReader a new BufferedReader, passing the FileReader variable to the constructor
      BufferedReader br = new BufferedReader(fr);

      // assign to a variable of type String line the result of calling the readLine method of your BufferedReader object.
      String line = br.readLine();

      //Row value of the given file
      int row = 0;
      // start a while loop that loops while line isn't null
      while(line != null){
          // assign to an array of type String the result of calling split on the line with the argument "[ ]+"
          String[] words = line.split("[ ]+");
          //Iterates through the array words and assigns the specific cell of the board with the corresponding file cell
          for(int i=0; i< words.length; i++)
          {
            this.set(row, i, Integer.parseInt(words[i]));
          }
          // assign to line the result of calling the readLine method of your BufferedReader object.
          line = br.readLine();

          //Increment row by 1
          row ++;
      }
      // call the close method of the BufferedReader
      br.close();
      return true;
    }
    catch(FileNotFoundException ex) {
      System.out.println("Board.read():: unable to open file " + filename );
    }
    catch(IOException ex) {
      System.out.println("Board.read():: error reading file " + filename);
    }

    return false;
  }

  //Returns a boolean that says if a cell at int row and int col with specified int value is a valid value
  public boolean validValue(int row, int col, int value)
  {
    //Checks if given value is within the range of 1-9
    if(1 <= value && value <= 9)
    {
      //Iterates through the rows of the board to check if there are any same values
      for(int i=0; i<this.SIZE; i++)
      {
        if(i != col)
        {
          if(this.cellGrid[row][i].getValue() == value)
          {
            return false;
          }
        }
      }

      //Iterates through the columns of the board to check if there are any same values
      for(int i=0; i<this.SIZE; i++)
      {
        if(i != row)
        {
          if(this.cellGrid[i][col].getValue() == value)
          {
            return false;
          }
        }
      }
      
      //Uses integer division to find the local squares of the cell
      int startRow = (row / 3) * 3;
      int startCol = (col / 3) * 3;

      //Iterates through the local square and checks if there are same values
      for(int r= startRow; r <= startRow + 2; r++)
      {
        for(int c= startCol; c <= startCol + 2; c++)
        {
          if(r == row && c == col)
          {
            continue;
          }
          else if(this.cellGrid[r][c].getValue() == value)
          {
            return false;
          }
        }
      }
      //Returns true if everything passes
      return true;
    }
    else
    {
      //Returns false otherwise
      return false;
    }
  }

  //Returns boolean that states if the board is a valid solution or not
  public boolean validSolution()
  {
    //Iterates through the entire board
      for(int i=0; i<this.SIZE; i++)
      {
        for(int j=0; j<this.SIZE; j++)
        {
          //Checks if the cell at i row and j column is a valid value
          if(this.cellGrid[i][j].getValue() == 0 || this.validValue(i, j, this.cellGrid[i][j].getValue()) == false)
          {
            //Returns false if illegal
            return false;
          }
        }
      }
      //Returns true if every value is valid
      return true;
  }

  //Returns Cell after finding the next available cell and setting it to a value, returns null if no cells are available
  public Cell findNextCell()
  {
    for(int i=0; i<this.SIZE; i++)
    {
      for(int j=0; j<this.SIZE; j++)
      {
        if(this.cellGrid[i][j].getValue() == 0 && this.cellGrid[i][j].isLocked() == false)
        {
          //start checking the possible values from 1 to 9 using the validValue method
          int attemptValue = 1;
          while(attemptValue <= 9)
          {
            boolean validValue = this.validValue(i, j, attemptValue);
            if(validValue == true)
            {
              //Use the first valid value found
              this.cellGrid[i][j].setValue(attemptValue);
              return this.cellGrid[i][j];
            }
            attemptValue += 1; 
          }
          return null;
        }
      }
    }
    return null; 
  }

  //Sets the finished field to the parameter status
  public void setFinished(boolean status)
  {
    this.finished = status;
  }

  //Draws a GUI representation of the board
  public void draw(Graphics g, int scale)
  {
    for(int i = 0; i<9; i++){
        for(int j = 0; j<9; j++){
            this.cellGrid[i][j].draw(g, j*scale+5, i*scale+10, scale);
        }
    } if(finished){
        if(validSolution()){
            g.setColor(new Color(0, 127, 0));
            g.drawChars("Hurray!".toCharArray(), 0, "Hurray!".length(), scale*3+5, scale*10+10);
        } else {
            g.setColor(new Color(127, 0, 0));
            g.drawChars("No solution!".toCharArray(), 0, "No Solution!".length(), scale*3+5, scale*10+10);
        }
    }
}


  public static void main(String[] args) 
  {
      Board board = new Board();
      board.read("BoardSolved.txt");
      System.out.print(board);
      // System.out.println("***" + board.validValue(0, 1, board.cellGrid[0][1].getValue()));
      // System.out.print(board.validValue(5,5,board.value(5, 5)));
      // System.out.print(board.validSolution());
  }
}
