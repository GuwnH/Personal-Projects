/**
File: Landscape.java
Author: Hesed Guwn
Date: 09/27/2022
Project03
Course: CS231 B
**/

//Extension changes at the constructor and the updateAgents method (line 63)

import java.awt.Graphics;
import java.awt.Color;
import java.util.Random;

public class Landscape 
{
    //Field width, height are the dimensions of the field, agents is a linkedlist of agents
    private int width;
    private int height;
    private int neighborToleranceSocial;
    private int neighborToleranceAnti;
    private LinkedList<Agent> agents;


    //Constructor that intializes width, height, and agents
    public Landscape(int w, int h, int neighborToleranceSocial, int neighborToleranceAnti) 
    {
        this.width = w;
        this.height = h;
        this.neighborToleranceSocial = neighborToleranceSocial;
        this.neighborToleranceAnti = neighborToleranceAnti;
        agents = new LinkedList<Agent>();
    }
    
    //Returns the height of landscape
    public int getHeight()
    {
        return this.height;
    }
    
    //Returns the width of landscape
    public int getWidth() 
    {
        return this.width;
    }
    
    //Adds agent to the linkedlist
    public void addAgent( Agent a ) 
    {
        this.agents.add(a);
    }
    
    //Creates a string representation of the list of agents
    public String toString() 
    {
        int agentCount = 0;
        for (Agent agent : agents) 
        {
            agentCount += 1;    
        }
        return "There are " + agentCount + " agents";
    }

    //Calls the update state for every agent in the list
    public void updateAgents()
    {
        for (Agent agent : agents) 
        {
            if(agent.getClass().getSimpleName() == "SocialAgentExtension")
            {
                agent.updateState(this, neighborToleranceSocial);
            }
            else
            {
                agent.updateState(this, neighborToleranceAnti);
            }
        }
    }
    
    //Returns a list of neighboring agents at the given coordinates
    public LinkedList<Agent> getNeighbors(double x0, double y0, double radius) 
    {
        //List that will contain the neighbors
        LinkedList<Agent> neighbors = new LinkedList<Agent>();
        
        //Iterates through each agent in the list
        for (Agent agent : agents) 
        {
            //Makes sure to not count the agent itself as a neighbor
            if(agent.getX() == x0 && agent.getY() == y0)
            {
                continue;
            }
            //Checks if the agent is close enough to be a neighbor and adds the agent if true
            if(agent.getX() < x0 + radius && agent.getY() < y0 && agent.getX() > x0 - radius && agent.getY() > y0 -radius)
            {
                neighbors.add(agent);
            }
        }
        return neighbors;
    }
    
    //Draws every agent in the list
    public void draw(Graphics g) 
    {
        for (Agent agent : agents) 
        {
            agent.draw(g);    
        }
    }
}
