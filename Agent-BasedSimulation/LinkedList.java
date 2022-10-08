/**
File: LinkedList.java
Author: Hesed Guwn
Date: 09/27/2022
Project03
Course: CS231 B
**/

import java.util.Iterator;    // defines the Iterator interface
import javax.lang.model.util.ElementScanner14;
import java.util.ArrayList;   
import java.util.Collections; // contains a shuffle function


public class LinkedList<T> implements Iterable<T>
{
    //Iterator class that allows us to use a foreach loop
    private class LLIterator implements Iterator<T>
    {
        //Node that we will use to iterate through the linkedlist
        private Node iterator;

        //Constructor that sets the iterator
        public LLIterator(Node head)
        {
            this.iterator = head;
        } 
        
        //Returns true if there are values to traverse, otherwise returns false 
        public boolean hasNext()
        {
            if(this.iterator != null)
            {
                return true;
            }
            return false;
        }
        
        //returns the next item in the list and increments the iterator
        public T next() 
        {
            T data = this.iterator.getData();
            this.iterator = this.iterator.getNext();
            return data;
        }
        
        //Optional method, does nothing
        public void remove()
        {
            return;
        } 
    }
    
    // Return a new LLIterator pointing to the head of the list
    public Iterator<T> iterator() 
    {
        return new LLIterator( this.head );
    }

    //Creates nodes of the linkedlist
    private class Node
    {
        //Data is any type of data stored in Node
        //Next is the next node connected
        T data;
        Node next;

        //Constructor that creates node given data
        public Node(T data)
        {
            this.data = data;
            this.next = null;
        }

        //Constructor that creates node given data and given next node
        public Node(T data, Node next)
        {
            this.data = data;
            this.next = next;
        }

        //Gets the data of node
        public T getData()
        {
            return this.data;
        }
        
        //Gets the next connected node
        public Node getNext()
        {
            return this.next;
        }

        //Sets the next node
        public void setNext(Node newNext)
        {
            this.next = newNext;
        }
       
    }   
    
    //Size is the length of the linked list and head is the first node of the list
    private int size;
    private Node head;

    //Constructs the list and sets the size field and head field
    public LinkedList()
    {
        this.size = 0;
        this.head = null;
    }
     
    //Adds a node to the front of the list with the contained item as data
    public void add(T item)
    {
        Node newNode = new Node(item);
        newNode.setNext(head);
        head = newNode;
        size++;
    }

    //Adds a node to the list at the given index with the contained item
    public void add(int index, T item)
    {
        //Checks if given index is out of range
        if(index < 0 || index > size)
        {
            System.out.println("Invalid index for add");
            return;
        }
         //Calls normal add if given index is 0
        if(index == 0)
        {
            add(item);
            return;
        }
        //Otherwise iterates to the index and adds the item at the right spot
        Node walker = head;
        for(int i=0; i < index - 1; i++)
        {
            walker = walker.getNext();
            //after this for loop finishes, walker is sitting at position index-1
        }

        Node newNode = new Node(item);

        newNode.setNext(walker.getNext());
        walker.setNext(newNode);

        size++;
    }

    //Resets the list
    public void clear()
    {
        this.size = 0;
        this.head = null;
    }

    //Checks if the object argument is inside the linked list and returns true if so, false otherwise
    public boolean contains(Object o)
    {
        if(this.size == 0)
        {
            return false;
        }
        else
        {
            //Iterates through the list to check if item is contained in each individual node
            Node walker = this.head;
            for(int i=0; i < this.size; i++)
            {
                if(walker.getData().equals(o))
                {
                    return true;
                }
                else
                {
                    walker = walker.getNext();
                }
            }
        }
        return false;
    }

    //Checks if the given object argument is the same as the linkedlist and returns a boolean
    public boolean equals(Object o)
    {
        //If given object isnt LinkedList returns false
        if (!(o instanceof LinkedList))
        {
            return false;
        }
        
        // If I have reached this line, o must be a LinkedList
        LinkedList oAsALinkedList = (LinkedList) o;
        // Now I have a reference to something Java knows is a LinkedList!

        Node walker = this.head;
        //Iterates through the list and checks if each corresponding node contains same data
        for (int i =0; i < oAsALinkedList.size(); i++)
        {
            if(walker.getData().equals(oAsALinkedList.get(i)))
            {
                walker = walker.getNext();
            }
            else
            {
                return false;
            }
        }
        return true;
    }

    //Returns the data of the node at the given index
    public T get(int index)
    {
        Node walker = this.head;
        for(int i = 0; i < index; i++)
        {
            walker = walker.getNext();
        }
        return walker.getData();
    }

    //Checks if the list is empty and returns true if it is and false otherwise
    public boolean isEmpty()
    {
        if(this.head == null)
        {
            return true;
        }
        else
        {
            return false;
        }

    }

     //Removes the first node of the linked list and returns the removed node data value
    public T remove()
    {
        T data = this.head.getData();
        this.head = this.head.getNext();
        this.size --;
        return data;
    }

    //Removes a node from the list and the given index and returns the data of the removed node
    public T remove(int index)
    {
        Node walker = this.head;
        Node skipper;
        T data;

        if(index == 0)
        {
            return this.remove();
        }
        else if(index < 0 || index == size)
        {
            System.out.println("Input Index Is Out of Range");
            data = this.head.getData();
            return null;
        }

        else
        {
            for(int i = 0; i < index-1; i++)
            {
                walker = walker.getNext();
            }
            skipper = walker.getNext();
            data = skipper.getData();
            walker.setNext(skipper.getNext());
            this.size --;
            return data;
        }
    }

    //Returns the size of the LinkedList
    public int size()
    {
        return this.size;
    }

    //Returns a string representation of the LinkedList
    public String toString()
    {
        //Empty list string
        if(this.head == null)
        {
            return "[]";
        }
        //Iterates through the linked list and adds to the accumulator string and returns it
        else
        {    
            String accumulator = "[";
            Node walker = this.head;

            for(int i=0; i < this.size(); i++)
            {
                accumulator = accumulator + walker.getData() + ", ";
                walker = walker.getNext();
            }

            //Creates a copy that is better formatted
            String accumulator2 = accumulator.substring(0, accumulator.length()-2);
            accumulator2 = accumulator2 + "]";

            return accumulator2;
        }
    }

    //Converts linkedlist into arraylist
    public ArrayList<T> toArrayList()
    {
        ArrayList<T> array = new ArrayList<T>();
        Node walker = this.head;
        for(int i=0; i < this.size; i++)
        {
            array.add(walker.getData());
            walker = walker.getNext();
        }
        return array;
    }

    public static void main(String[] args) 
    {
        LinkedList<Integer> list = new LinkedList<Integer>();
        list.add(1);   
        list.add(2);
        list.add(3);
        list.add(2,10);

        // LinkedList<Integer> list2 = new LinkedList<Integer>();
        // list2.add(1);   
        // list2.add(2);
        // list2.add(3);
        // list2.add(4);

        System.out.println(list);
        // System.out.println(list.contains(12));

        // list.add(2,10);

        list.remove(0);

        System.out.println(list);

        list.clear();
        System.out.println(list);
               
    }

}
