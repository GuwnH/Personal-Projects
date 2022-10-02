package CS231.hsguwn25Project02;

/*
file name:      LandscapeTests.java
Authors:        Max Bender & Naser Al Madi
last modified:  9/18/2022

How to run:     java -ea LandscapeTests
*/

import java.util.ArrayList;

public class LandscapeTests {

    public static void landscapeTests() {

        // case 1: testing Landscape(int, int)
        {
            // set up
            Landscape l1 = new Landscape(2, 4);
            Landscape l2 = new Landscape(10, 10);

            // verify
            System.out.println(l1);
            System.out.println("\n");
            System.out.println(l2);

            // test
            assert l1 != null : "Error in Landscape::Landscape(int, int)";
            assert l2 != null : "Error in Landscape::Landscape(int, int)";
        }

        // case 2: testing reset()
        {
            // set up
            Landscape l3 = new Landscape(10, 10);

            // verify
            System.out.println(l3);
            l3.reset();
            System.out.println(l3);
            

            // test
            assert l3 != null : "Error in Landscape::Reset()";
        }

        // case 3: testing getRows()
        {
            // set up
            Landscape l4 = new Landscape(5, 5);

            // verify
            System.out.println("Rows of l4: " + l4.getRows());

            // test
            assert l4 != null : "Error in Landscape::getRows()";
        }

        // case 4: testing getCols()
        {
            // set up
            Landscape l5 = new Landscape(5, 5);

            // verify
            System.out.println("Cols of l4: " + l5.getCols());

            // test
            assert l5 != null : "Error in Landscape::getRows()";

        }

        // case 5: testing getCell(int, int)
        {
             // set up
             Landscape l6 = new Landscape(10, 10);

             // verify
             System.out.println("Cell at 5,5: " + l6.getCell(5,5));
             System.out.println("");
 
             // test
             assert l6 != null : "Error in Landscape::getCell(rows, columns)";

        }

        // case 6: testing getNeighbors()
        {
             // set up
             Landscape l7 = new Landscape(10, 10);

             // verify
             System.out.println(l7);
             System.out.println("Neighbors at 5,5: " + l7.getNeighbors(5,5));
             System.out.println("");
 
             // test
             assert l7 != null : "Error in Landscape::getNeighbors(rows, columns)";

        }

        // case 7: testing advance()
        {
             // set up
             Landscape l8 = new Landscape(10, 10);

             // verify
             System.out.println(l8);
             l8.advance();
             System.out.println(l8);
 
             // test
             assert l8 != null : "Error in Landscape::advance()";

        }
        System.out.println("*** Done testing Landscape! ***\n");
    }


    public static void main(String[] args) {

        landscapeTests();
    }
}
