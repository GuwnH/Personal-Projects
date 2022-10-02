/**
File: LifeSimulation.java
Author: Hesed Guwn
Date: 09/23/2022
Project02
Course: CS231 B
**/

package CS231.hsguwn25Project02;

public class LifeSimulation {

    public static void main(String[] args) throws InterruptedException {
        
        //Makes physical landscape and the gui version of it
        Landscape scape = new Landscape(100, 100, .5);

        LandscapeDisplay display = new LandscapeDisplay(scape, 6);

        // Uncomment below when advance() has been finished

        //Runs the simulation forever with a slighy delay per frame
        while (true) {
        Thread.sleep(250);
        scape.advance();
        display.repaint();
        }
    }
    
}
