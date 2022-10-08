/**
File: Agent.java
Author: Hesed Guwn
Date: 09/27/2022
Project03
Course: CS231 B
**/

import java.awt.Graphics;

//Abstract parent class of social agents and anti social agents
public abstract class Agent 
{
    //Field coordinates of agent
    protected double x;
    protected double y;

    //Constructor, intializes x and y coordinates
    public Agent(double x0, double y0)
    {
        this.x = x0;
        this.y = y0;
    }   
    
    //Returns x coordinate
    public double getX()
    {
        return this.x;
    } 
    
    //Returns y coordinate
    public double getY()
    {
        return this.y;
    }
    
    //Sets x coordinate to given argument
    public void setX( double newX ) 
    {
        this.x = newX;
    }
    
    //Sets y coordinate to given argument
    public void setY( double newY ) 
    {
        this.y = newY;
    }
    
    //Returns string representation of agent via their coordinates
    public String toString() 
    {
        String coordinates = "(" + this.x + ", " + this.y + ")";
        return coordinates;
    }
    
    //Updates the state of agents
    public abstract void updateState(Landscape scape, int neighborTolerance); 
    
    //Draws agents
    public abstract void draw(Graphics g);
    
}
