/**
File: LifeSimulationExtension.java
Author: Hesed Guwn
Date: 09/23/2022
Project02
Course: CS231 B
To run type: java LifeSimulationExtension (int rows) (in columns) (float chance (any number between 0-1) (int number of iterations) 
Don't run in VS Code
**/


public class LifeSimulationExtension {

    public static void main(String[] args) throws InterruptedException 
    {
        if(args.length == 0)
        {
            System.out.println("Please Provide Command Line Arguments Like This: java LifeSimulationExtension (int rows) (int columns) (float chance) (int number of iterations)");
            return;
        }
        else
        {
            //Takes arguments from command line and turns it into usable parameters
            int rows = Integer.parseInt(args[0]);
            int cols = Integer.parseInt(args[1]);
            float chance = Float.parseFloat(args[2]);
            int iterations = Integer.parseInt(args[3]);

            //Makes a landscape and its display
            Landscape scape = new Landscape(rows, cols, chance);

            LandscapeDisplay display = new LandscapeDisplay(scape, 6);

            // Uncomment below when advance() has been finished
            
            //Runs until the iteration limit is reached (given by 4th command line argument)
            int i = 0;
            while (i < iterations) 
            {
                Thread.sleep(250);
                scape.advance();
                display.repaint();
                i += 1;
            }
        }
    }
    
}
