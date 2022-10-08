/**
File: AgentSimulation.java
Author: Hesed Guwn
Date: 09/27/2022
Project03
Course: CS231 B
**/

import java.util.Random;

public class AgentSimulation {
    public static void main(String[] args) throws InterruptedException
    {
        if(args.length == 0)
        {
            System.out.println("Please Provide Command Line Arguments Like This: java LifeSimulationExtension (int wdith) (int height) (int number of agents) (int social agent neighbors) (int antisocial agent neighbors");
            System.out.println("(radius of socialAgents) (radius of antiSocialAgents)");
            return;
        }
        else
        {
            int width = Integer.parseInt(args[0]);
            int height = Integer.parseInt(args[1]);
            int agents = Integer.parseInt(args[2]);
            int socialAgentNeighborTolerance = Integer.parseInt(args[3]);
            int antiAgentNeighborTolerance = Integer.parseInt(args[4]);
            int socialRadius = Integer.parseInt(args[5]);
            int antiSocialRadius = Integer.parseInt(args[6]);

            Landscape scape = new Landscape(width, height, socialAgentNeighborTolerance, antiAgentNeighborTolerance);
            Random gen = new Random();

            // Creates 100 SocialAgents and 100 AntiSocialAgents (200 Agents in total)
            for (int i = 0; i < agents; i++) 
            {
                scape.addAgent(new SocialAgent(gen.nextDouble() * scape.getWidth(),
                        gen.nextDouble() * scape.getHeight(),
                        socialRadius));
                scape.addAgent(new AntiSocialAgent(gen.nextDouble() * scape.getWidth(),
                        gen.nextDouble() * scape.getHeight(),
                        antiSocialRadius));
            }

            LandscapeDisplay display = new LandscapeDisplay(scape);

            // Uncomment below when updateAgents() has been implemented
            while(true)
            {
                Thread.sleep(250);
                scape.updateAgents();
                display.repaint();
            }
        }
    }   
}
