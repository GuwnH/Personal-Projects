/**
File: AntiSocial.java
Author: Hesed Guwn
Date: 09/27/2022
Project03
Course: CS231 B
**/


import java.awt.Graphics;
import java.awt.Color;
import java.util.Random;

public class AntiSocialAgent extends Agent
{
    //Field moved tells us whether the agent moved after updating
    //Radius is used to tell if nearby agents are neighbors
    private boolean moved;
    private int radius;
    
    //Constructor, initializes the coordinates and radius of sensitivity
    public AntiSocialAgent(double x0, double y0, int radius)
    {
        super( x0, y0 );
        // remainder of constructor code
        this.radius = radius;
    }

    //Sets radius to given radius argument
    public void setRadius(int radius)
    {
        this.radius = radius;
    }

    //Returns radius
    public int getRadius()
    {
        return this.radius;
    }

    //Draws the visual representation of the agent
    public void draw(Graphics g)
    {
        if(!moved) g.setColor(new Color(255, 0, 0));
        else g.setColor(new Color(255, 125, 125));

        g.fillOval((int) getX(), (int) getY(), 5, 5);
    }

    //Updates the state of the agent
    public void updateState(Landscape scape, int neighborTolerance){

        Random ran = new Random();

        //Checks if there are more than (neighborTolerance) neighboring agents
        if (scape.getNeighbors(this.x, this.y, this.radius).size() > neighborTolerance)
        {
            //moves agent if true
            moved = true;
            this.x = this.x + ran.nextDouble(-10, 10);
            this.y = this.y + ran.nextDouble(-10, 10);

            //Runs while nothing gets outside the border of the screen
            while (this.x >= scape.getWidth() && this.y >= scape.getWidth() && this.x <= 0 && this.y <= 0)
            {    
                this.x = this.x + ran.nextDouble(-10, 10);
                this.y = this.y + ran.nextDouble(-10, 10);

            }
        }
        moved = false;
    }

}
