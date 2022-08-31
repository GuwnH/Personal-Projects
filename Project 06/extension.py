''' 
Hesed Guwn
Spring 2022
CS 152 Project 6
3/31/22
'''

from cgi import test
import sys
import elephant
import random 
import matplotlib.pyplot as plt
'''The purpose of this program is to run a search function through a sorted list for values and use that to find the optimal darting percentage in relation to the 
wanted parameters that are compared'''

'''My extension is that I added code in the evalParameterEffect function so that it graphed the results
Extension starts at line 70-88, 104-108, 129, and ends at line 170''' 

# min: minimum parameter value to search
# max: maximum parameter value to search
# optfunc: function to optimize
# parameters: optional parameter list to pass to optfunc
# tolerance: how close to zero to get before terminating the search
# maIterations: how many iterations to run before terminating the search
# verbose: whether to print lots of information or not

def optimize( min, max, optfunc, parameters = None, tolerance = 0.001, maxIterations = 20, verbose=False ):
    '''Executes a search to bring the result of the function optfunc to zero.'''

    # assign to the variable done, the value False
    done = False

    # start a while loop that executes while done is not True
    while done != True:    
        
        testValue = (max + min) / 2

        if verbose == True:
            print(testValue)

        result = optfunc(testValue,parameters)

        if result > 0:
            max = testValue

        elif result < 0:
            min = testValue
        
        else:
            done = True

        if (max-min) < tolerance:
            done = True
        
        maxIterations = maxIterations - 1

        if maxIterations <= 0:
            done = True

    return testValue

# whichParameter: the index of the parameter to test
# testmin: the minimum value to test
# testmax: the maximum value to test
# teststep: the step between parameter values to test
# defaults: default parameters to use (default value of None)
def evalParameterEffect( whichParameter, testmin, testmax, teststep, defaults=None, verbose=False ):
    '''Evaluates the effects of the selected parameter on the dart percentage'''
    
    #Giving the x axis a label depending on which parameter is used
    if whichParameter == elephant.IDXCalvingInterval:
        xValueLabel = "Calving Interval"
    if whichParameter == elephant.IDXPercentDarted:
        xValueLabel = "Percent Darted"
    if whichParameter == elephant.IDXJuvenileAge:
        xValueLabel = "Juvenile Age"
    if whichParameter == elephant.IDXMaxAge:
        xValueLabel = "Max Age"
    if whichParameter == elephant.IDXProbabilityCalfSurvival:
        xValueLabel = "Probability Calf Survival"
    if whichParameter == elephant.IDXProbabilityAdultSurvival:
        xValueLabel = "Probability Adult Survival"
    if whichParameter == elephant.IDXProbabilitySeniorSurvival:
        xValueLabel = "Probability Senior Survival"
    if whichParameter == elephant.IDXCarryingCapacity:
        xValueLabel = "Carrying Capacity"
    if whichParameter == elephant.IDXNumberYears:
        xValueLabel = "Number Years Simulated"

    # if defaults is None, assign to simParameters the result of calling elephant.defaultParameters.
    if defaults == None:

        simParameters = elephant.defaultParameters()
    # else, assign to simParameters a copy of defaults  (e.g. simParameters = defaults[:]
    else:
        simParameters = defaults[:]
    
    # create an empty list (e.g. results) to hold the results
    results = []

    if verbose:
        print ("Evaluating parameter %d from %.3f to %.3f with step %.3f" % (whichParameter, testmin, testmax, teststep))

    #Opens and creates a new data file and sets it up to be written in
    fp = open("data.csv", 'w')

    #Writes into a new file the headers
    fp.write(xValueLabel + ", Darting Percentage" + "\n")

    # assign to t the value testmin
    t = testmin
    # while t is less than testmax
    while t < testmax:
        
        # assign to the whichParameter element of simParameters (e.g. simParameters[whichParameter]) the value t
        simParameters[whichParameter] = t
        # assign to percDart the result of calling optimize with the appropriate arguments, including simParameters
        percDart = optimize(0,1,elephant.elephantSim,simParameters,0.001,20,verbose=False)
        # append to results the tuple (t, percDart)
        results.append((t, percDart))

        if verbose:
            print("%8.3f \t%8.3f" % (t, percDart))
            fp.write(str(t) + "," + str(percDart) + "\n")
        
        # increment t by the value teststep
        t = t + teststep

    #Extension Starts Here
    fp.close()

    #Reopens the file to be read
    gp = open("data.csv", 'r')

    #Sets each row in the data file to be read
    line = gp.readline()
    
    #Intializes the x and multiple y axis lists that will be plotted
    xList = []
    y1 = []

    #Loop that creates the axes lists
    for line in gp:
        
        words = line.split(",")

        testedParameter = words[0]
        xList.append(float(testedParameter))
        
        dartPercentage = words[1]
        y1.append(float(dartPercentage))

    #X axis of graph which is just the years that are passing in the simulation
    x = xList

    #sets plot with what to use as points
    plt.plot(x,y1,'o')

    #titles the plot
    plt.title("The effect of " + xValueLabel + " on Darting Percentage")

    #labels x axis
    plt.xlabel(xValueLabel)

    #labels y axis
    plt.ylabel("Darting Percentage")
    
    #shows graph
    plt.show()

    #Extension ends here

    if verbose:
        print("Terminating")

    # return the list of results
    return results

def testEsim():
    '''This function tests the optimize function using the elephantSim function'''
    res = optimize( 0.0, 0.5, elephant.elephantSim, tolerance = 0.01, verbose=True)
    print(res)
    return

def target(x, pars):
    '''This function returns x - target'''
    return x - 0.73542618


# Try changing the tolerance to see how that affects the search.
def testTarget():
    '''Tests the binary search using a simple target function'''
    res = optimize( 0.0, 1.0, target, tolerance = 0.01, verbose=True)
    print(res)
    return

if __name__ == "__main__":
    #testTarget()
    #testEsim()
    evalParameterEffect( elephant.IDXCalvingInterval, 3.0, 3.4, 0.05, verbose=True )

