''' 
Hesed Guwn
Spring 2022
CS 152 Project 6
3/29/22
'''

'''The purpose of this file is to run a binary search through a sorted list'''

import random

def searchSortedList( mylist, value ):
    '''This function searches through a sorted list (mylist) for a wanted value (value) using binary search'''

    # assign to the variable done, the value False
    done = False
    # assign to the variable found, the value False
    found = False
    # assign to the variable count, the value 0
    count = 0
    # assign to the variable maxIdx, the one less than the length of mylist 
    maxIdx = len(mylist)-1
    # assign to the variable minIdx, the value 0
    minIdx = 0

    # start a while loop that executes while done is not True
    while done != True:    
        # increment count  (which keeps track of how many times the loop executes
        count = count + 1
        # assign to testIndex the average of maxIdx and minIdx (use integer math)
        testIndex = round( (maxIdx + minIdx) / 2)
        #testIndex=(maxIdx + minIdx)//2        

        # if the myList value at testIndex is less than value
        if mylist[testIndex] < value:
            
            # assign to minIdx the value testIndex + 1
            minIdx = testIndex + 1

        # elif the myList value at testIndex is greater than value
        elif mylist[testIndex] > value:

            # assign to maxIdx the value testIndex - 1
            maxIdx = testIndex - 1

        # else
        else:
    
            # set done to True
            done = True
            # set found to True
            found = True

            # if maxIdx is less than minIdx
            if maxIdx < minIdx:
            
                # set done to True
                done = True
                # set found to False
                found = False

    return (found, count)

def test():
    '''This function tests our searchSortedList function'''
    a = []
    for i in range(10000):
        a.append( random.randint(0,100000) )

    a.append(42)

    a.sort()

    print(searchSortedList( a, 42 ))

if __name__ == "__main__":
    test()
