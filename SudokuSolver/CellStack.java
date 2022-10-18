/**
File: CellStack.java
Author: Hesed Guwn
Date: 10/04/2022
**/

public class CellStack 
{
    //Creates a Node class that holds values and acts as an individual piece of a stack
    private class Node
    {
        //Fields cell is a Cell object that Node holds and next is the Node next to the current Node
        Cell cell;
        Node next;

        //Constructor Node that intializes cell to be given cell parameter and its next to be null
        public Node(Cell cell)
        {
            this.cell = cell;
            this.next = null;
        }
    }
    
    //Field head is the top of the stack Node and size is an int that represents the amount of nodes in the stack 
    private Node head; //This should always be the top of my stack
    private int size;

    //Intializes stack with head being null and size being 0
    public CellStack()
    {
        this.head = null;
        this.size = 0;
    }

    //Adds a cell to the front of the stack
    public void push(Cell c)
    {
        Node newNode = new Node(c);
        newNode.next = this.head;
        this.head = newNode;
        this.size++;
    }

    //Returns the head of the stack
    public Cell peek()
    {
        return this.head.cell;
    }

    //Removes the head of the stack and returns the removed Node
    public Cell pop()
    {
        if(this.size > 0)
        {
            Cell poppedCell = this.head.cell;
            this.head = this.head.next;
            this.size--;
            return poppedCell;
        }
        else
        {
            return null;
        }
    }

    //Returns the int size of the stack
    public int size()
    {
        return this.size;
    }

    //Returns boolean that tells if the stack is empty or not
    public boolean empty()
    {
        if(this.size == 0 && this.head == null)
        {
            return true;
        }
        return false;
    }
}
