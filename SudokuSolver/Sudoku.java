/**
File: Sudoku.java
Author: Hesed Guwn
Date: 10/04/2022
**/
import java.util.Random;
import java.lang.Thread;

public class Sudoku 
{
    //Field board is Board object and ld is a LandscapeDisplay object
    private Board board;
    private LandscapeDisplay ld;

    //Default constructor that initializes board field to an empty board full of 0s and sets ld to be a new display of the board
    public Sudoku()
    {
        this.board = new Board();
        this.ld = new LandscapeDisplay(this.board);
    }

    //Default constructor that initializes board field to a board with int numLock number of locked cells with randomized values and sets ld to be a new display of the board
    public Sudoku(int numLock)
    {
        this.board = new Board(numLock);
        this.ld = new LandscapeDisplay(this.board);
    }

    //Returns the toString display of the board for testing convinience 
    public String boardDisplay()
    {
        return this.board.toString();
    }

    //Attempts to solve the board and returns a boolean that says if the board is solvable or not
    public boolean solve(int delay) throws InterruptedException
    {
        //Allocate a stack, initially empty
        CellStack stack = new CellStack();
        // while the stack size is less than the number of unspecified cells?
        while(stack.size() < (this.board.SIZE*this.board.SIZE) - this.board.numLocked())
        {
            if (delay > 0)
            {
                Thread.sleep(delay);
            }
            if (ld != null)
            {
                ld.repaint();
            }
            //select the next cell to check (you'll be calling findNextCell, described below)
            Cell nextCell = this.board.findNextCell();
            //if this cell has a valid value to try
            if(nextCell != null)
            {
                //push the cell onto the stack
                stack.push(nextCell);
                //update the board
                this.board.set(nextCell.getRow(), nextCell.getCol(), nextCell.getValue());
            }
            //else
            else
            {
                boolean stuck = true;
                //while it is possible to backtrack (if the stack is nonempty)
                while(stack.size() != 0 && stuck)
                {
                    //pop a cell off the stack (use popped)
                    Cell popped = stack.pop();
                    //check if there are other untested values this cell could try
                    for(int i = popped.getValue() + 1; i <= 9; i++)
                    {
                        boolean check = this.board.validValue(popped.getRow(), popped.getCol(), i);
                        if(check)
                        {
                            popped.setValue(i);
                            this.board.set(popped.getRow(),popped.getCol(), popped.getValue());
                            stack.push(popped);
                            stuck = false;
                            break;
                        }
                    } 
                    
                    //if the algorithim is stuck/the cell has no other working values
                    if(stuck)
                    {
                        // set this cell's value to 0 on the board
                        this.board.set(popped.getRow(), popped.getCol(), 0);
                    }
                }

                //if the stack size is 0 (no more backtracking possible)
                if(stack.size() == 0)
                {
                    //return false: there is no solution and sets the board variable to be true
                    this.board.setFinished(true);
                    return false;
                }
            }
        }
        // return true: the board contains the solution and sets the board finished variable to be true
        this.board.setFinished(true);
        return true;
    }

    public static void main(String[] args) throws InterruptedException 
    {
        long total = 0;
        float solvable = 0;
        for(int i=0;  i< 50; i++)
        {
            Sudoku board = new Sudoku(10);
            // System.out.println(board.boardDisplay());
            long startTime = System.nanoTime();
            boolean solve = board.solve(0);
            long duration = System.nanoTime() - startTime;
            if(solve)
            {
                // System.out.println("solved");
                solvable += 1.0;
            }
            System.out.println(duration);
            total += duration;
            // System.out.println(board.boardDisplay());
                 
        }
        long average = total / 20;
        float averageSolve = solvable / 20;
        System.out.println("The duration of this process took: " + average + " nano seconds"); 
        System.out.println("This many boards were solved: " + averageSolve); 
           
    }
}
