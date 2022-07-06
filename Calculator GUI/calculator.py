'''
Hesed Guwn
06/19/22
'''

#Simple Calculator GUI, click the buttons on the calculator to make it do something
#Created without the use of eval()
#Does not follow PEMDAS when multiple operations are happening


from tkinter import *
from tkinter.ttk import *

class CalculatorGui(object):
    
    def __init__(self, window):
        """Sets up the initial GUI window and all the buttons and needed values for other functions"""
        
        #Number values set for calculator
        self.data = IntVar()
        self.data.set(0)

        #GUI Setup
        self.title = window.title("My Calculator")
        self.size = window.geometry("300x200")

        self.numberLabel = Label(window, textvariable=self.data)
        self.numberLabel.grid(row=0, column=0)

        self.button7 = Button(window, text="7", command=lambda: self.set_number(7))
        self.button7.grid(row=1, column=0)

        self.button8 = Button(window, text="8", command=lambda: self.set_number(8))
        self.button8.grid(row=1, column=1)

        self.button9 = Button(window, text="9", command=lambda: self.set_number(9))
        self.button9.grid(row=1, column=2)

        self.button4 = Button(window, text="4", command=lambda: self.set_number(4))
        self.button4.grid(row=2, column=0)

        self.button5 = Button(window, text="5", command=lambda: self.set_number(5))
        self.button5.grid(row=2, column=1)

        self.button6 = Button(window, text="6", command=lambda: self.set_number(6))
        self.button6.grid(row=2, column=2)

        self.button1 = Button(window, text="1", command=lambda: self.set_number(1))
        self.button1.grid(row=3, column=0)

        self.button2 = Button(window, text="2", command=lambda: self.set_number(2))
        self.button2.grid(row=3, column=1)

        self.button3 = Button(window, text="3", command=lambda: self.set_number(3))
        self.button3.grid(row=3, column=2)

        self.button0 = Button(window, text="0", command=lambda: self.set_number(0))
        self.button0.grid(row=4, column=0)

        self.buttonequal = Button(window, text="=", command= self.equalCommand)
        self.buttonequal.grid(row=4, column=2)

        self.clear = Button(window, text="C", command= self.clearCommand)
        self.clear.grid(row=4, column=1)

        self.add = Button(window, text="+", command= self.add_number)
        self.add.grid(row=1, column=4)

        self.subtract = Button(window, text="-", command= self.subtract_number)
        self.subtract.grid(row=2, column=4)

        self.divide = Button(window, text="/", command= self.divide_number)
        self.divide.grid(row=3, column=4)

        self.multiply = Button(window, text="*", command= self.multiply_number)
        self.multiply.grid(row=4, column=4)

        #Values needed for function use
        self.addValue = False
        self.subtractValue = False
        self.multiplyValue = False
        self.divideValue = False

        self.storedNum = 0
        self.listNums = []
        self.reset = False
        self.listTypeNums = []
    
    
    def set_number(self,buttonNum):
        """Sets the number displayed on the calculator"""
        currentNum = self.data.get()
        if self.reset == True:
            currentNum = 0
            self.reset = False

        if currentNum == 0:
            newNum = buttonNum
        else:
            clickedNum = int(buttonNum)
            newNum = str(currentNum) + str(clickedNum)
            newNum = int(newNum)

        self.data.set(newNum)

    def add_number(self):
        '''Function responsible for setting up the addition of numbers'''
        self.storedNum = self.data.get()
        self.listNums.append(self.storedNum)
        self.listTypeNums.append("add")
        self.data.set(0)
        self.addValue = True
        self.subtractValue = False
        self.multiplyValue = False
        self.divideValue = False

    def subtract_number(self):
        '''Function responsible for setting up the subtraction of numbers'''
        self.storedNum = self.data.get()
        self.listNums.append(self.storedNum)
        self.listTypeNums.append("sub")
        self.data.set(0)
        self.addValue = False
        self.subtractValue = True
        self.multiplyValue = False
        self.divideValue = False

    def multiply_number(self):
        '''Function responsible for setting up the multiplication of numbers'''
        if len(self.listNums) == 1 and self.listNums[0] == 0:
            self.listNums[0] = 1 
        self.storedNum = self.data.get()
        self.listNums.append(self.storedNum)
        self.listTypeNums.append("mult")
        self.data.set(0)
        self.addValue = False
        self.subtractValue = False
        self.multiplyValue = True
        self.divideValue = False
        
    def divide_number(self):
        '''Function responsible for setting up the division of numbers'''
        self.storedNum = self.data.get()
        self.listNums.append(self.storedNum)
        self.data.set(0)
        self.listTypeNums.append("div")
        self.addValue = False
        self.subtractValue = False
        self.multiplyValue = False
        self.divideValue = True

    def clearCommand(self):
        '''Resets the calculator'''
        self.storedNum = 0
        self.data.set(0)
        self.listNums = []
        self.listTypeNums = []
        self.addValue = False
        self.subtractValue = False
        self.multiplyValue = False
        self.divideValue = False
    
    def equalCommand(self):
        '''Function that evaluates the calculator'''
        currentNumber = self.data.get()
        self.listNums.append(currentNumber)

        i = 0
        newNum = self.listNums[0]

        while i < len(self.listNums)-1:
            
            if i == 0:
                newNum = self.listNums[0]

                if self.listTypeNums[i] == "add":
                    newNum = newNum + self.listNums[i+1]
                if self.listTypeNums[i] == "sub":
                    newNum = newNum - self.listNums[i+1]
                if self.listTypeNums[i] == "mult":
                    newNum = newNum * self.listNums[i+1]
                if self.listTypeNums[i] == "div":
                    newNum = newNum / self.listNums[i+1]
            else:
                if self.listTypeNums[i] == "add":
                    newNum = newNum + self.listNums[i+1]
                if self.listTypeNums[i] == "sub":
                    newNum = newNum - self.listNums[i+1]
                if self.listTypeNums[i] == "mult":
                    newNum = newNum * self.listNums[i+1]
                if self.listTypeNums[i] == "div":
                    newNum = newNum / self.listNums[i+1]
                    
            i += 1

        self.data.set(newNum)

        self.listNums = []
        self.listTypeNums = []
        self.reset = True

        
def main():
    """Set up the GUI and run it"""
    window = Tk()
    calculator = CalculatorGui(window)
    window.mainloop()

if __name__ == "__main__":
    main()