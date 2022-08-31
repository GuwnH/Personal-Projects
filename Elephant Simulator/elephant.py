''' 
Hesed Guwn
3/29/22
'''

'''The purpose of this file is to simulate the elephant populaiton in Kruger National Park, identifying the population distribution through the methods of culling
or darting elephants, and seeing how many female elpehants have to be darted to maintain the population without culling'''

'''This program runs off the terminal command line and should look similar to this---> python3 elephant.py NUMBER BETWEEN 0.0-1.0
Default simulation with percetly default parameters should look like ----> python3 elephant.py 0.3
'''

import random
from unittest import defaultTestLoader
from numpy import number
import sys
import matplotlib.pyplot as plt

#Parameter Indexies for Population
IDXCalvingInterval = 0
IDXPercentDarted = 1
IDXJuvenileAge = 2
IDXMaxAge = 3
IDXProbabilityCalfSurvival = 4
IDXProbabilityAdultSurvival = 5
IDXProbabilitySeniorSurvival = 6
IDXCarryingCapacity = 7
IDXNumberYears = 8

#Parameter Indexies for Elephants
IDXGender = 0
IDXAge = 1
IDXMonthsPregnant = 2
IDXMonthsContraceptiveRemaining = 3

def test():
    '''This function is used for testing the functions created in the lab (newElephant, initPopulation, incrementAge)'''

    # assign each parameter from the table above to a variable with an informative name
    calvingInterval = 3.1
    percentDarted = 0.0
    juvenileAge = 12
    maxAge = 60
    probabilityCalfSurvival = .85
    probabilityAdultSurvival = .996
    probabilitySeniorSurvival = .20
    carryingCapacity = 30
    numberYears = 200
    
    # make the parameter list out of the variables
    parameterList = [calvingInterval,percentDarted,juvenileAge,maxAge,probabilityCalfSurvival,probabilityAdultSurvival,probabilitySeniorSurvival,carryingCapacity,numberYears]
    
    #prints the parameter list
    print(parameterList)

    pop = []
    for i in range(15):
        pop.append( newElephant( parameterList, random.randint(1,parameterList[IDXMaxAge]) ))
    
    for e in pop:
        print(e)
    
    pop = initPopulation(parameterList)
    print(pop)
    agePop = incrementAge(pop)
    print(agePop)
    

def newElephant(parameters,age):
    '''Creates an elephant (which is represented as a list) using the population list as the first paramter and a given age as the 
   second paramter'''    
    #Starting elephant settings
    elephant = [0,0,0,0]

    #Gives elephant gender
    elephant[IDXGender] = random.choice(['m','f'])

    #Uses parameter age to give elephant age
    elephant[IDXAge] = age

    #Decides if elephant is pregnant or not and decides how pregnant
    if 'f' in elephant[IDXGender]:

        #Decides if elpehant is old enough and young enough
        if parameters[IDXJuvenileAge] < elephant[IDXAge] <= parameters[IDXMaxAge]:

            #Random choice on pregnancy
            probabilityPregnant = random.random()

            #Decides if the random choice is enough to assign elephant to be pregnant
            if probabilityPregnant < ( 1 / parameters[IDXCalvingInterval] ):

                #Randomly assigns how long the elephant has been pregnant
                monthsCarrying = random.randint(1,22)

                #Rightfully assigns the index of months carrying to the elephant list
                elephant[IDXMonthsPregnant] = monthsCarrying
    
    return elephant


def initPopulation(parameterList):
    '''Creates an inital population list of elephants using the list of chosen population parameters and using the newElephant method to create the carrying capacity
    amounts of elephants'''

    #Initializes population
    populationList = []

    #Amount of elephants to create
    populationNumber = parameterList[IDXCarryingCapacity]

    #For loop that repeats the wanted amount of times
    for i in range(populationNumber):
        
        #Randomly decides age
        age = random.randint(0, parameterList[IDXMaxAge])
        
        #Creates elephant with randomly decided age
        elephant = newElephant(parameterList, age)
        
        #Adds elephant to population list
        populationList.append(elephant)
    
    return populationList


def incrementAge(population):
    '''Ages every single elephant in the population by 1 year using a for loop that loops throughout every elephant in the population list and accesses their age index
    and adds 1 to it'''

    #Loops through the entire population
    for i in range(len(population)):

        #Adds 1 to the age of the specific elephant
        population[i][IDXAge] = population[i][IDXAge] + 1

    #Gives us updated population
    return population


def calcSurvival(parameter,population):
    '''Creates a new population by using random chance to decide if an elephant of a specific age group survives to next year, with living elephants being added to 
    the new new population'''

    #initializes new population after one year
    new_population = []

    #Loops over the population list
    for i in range(len(population)):

        #Gets the age of the specific elephant
        elephantAge = population[i][IDXAge]

        #Randomly decides elephant survival
        elephantSurvivalChance = random.random()

        #If statement that decides if a Calf survives
        if elephantAge < 2:

            #If statement that decides if elephant lives or dies by random chance
            if elephantSurvivalChance < parameter[IDXProbabilityCalfSurvival]:

                #Adds living elephant to new population
                new_population.append(population[i])

        #If statement that decides if an Adult survives
        if 2 <= elephantAge <= parameter[IDXMaxAge]:

            #If statement that decides if elephant lives or dies by random chance
            if elephantSurvivalChance < parameter[IDXProbabilityAdultSurvival]:
                
                #Adds living elephant to new population
                new_population.append(population[i])

        #If statement that decides if elephant is a Senior 
        if elephantAge > parameter[IDXMaxAge]:

            #If statement that decides if elephant lives or dies by random chance
            if elephantSurvivalChance < parameter[IDXProbabilitySeniorSurvival]:

                #Adds living elephant to new population
                new_population.append(population[i])   
    
    #Gives us the updated population
    return new_population

def dartElephants(parameter,population):
    '''This function calculates how many female elephants need to be darted with contraceptives given the list of population parameters and a list of the 
    elephant population itself'''
    
    #Sets variables to the respective population parameter index for convinience    
    probDarting = parameter[IDXPercentDarted]
    juvenileAge = parameter[IDXJuvenileAge]
    maxAge = parameter[IDXMaxAge]

    #For loop that spans the length of the popuation of elephants
    for i in range(len(population)):       

        #If statement that decides if elephant at index i is female
        if population[i][IDXGender] == 'f':

            #If statement that decides if female elephant is an adult
            if juvenileAge < population[i][IDXAge] < maxAge:

                #Probability of darting this specific elephant
                dartingProb = random.random()

                #If the probability of darting is within the population parameter probabibility of darting, the elephant got darted
                if dartingProb < probDarting:

                    #Elephant's pregnancy is reset to 0
                    population[i][IDXMonthsPregnant] = 0
                    
                    #Elephant's months of contraceptive remaining is reset to 22 
                    population[i][IDXMonthsContraceptiveRemaining] = 22
    
    #Returns updated population
    return population


def cullElephants(parameter,population):
    '''This function uses the parameter list and population of elephants to determine if there are an excessive amount of elephants (the population is greater
    than the carrying capcity) and uses random numbers to get rid of the excess amounts of elephants'''
    
    #Sets a variable to the population parameter carrying capacity
    carryingCap = parameter[IDXCarryingCapacity]
    
    #If statement that checks if the population is bigger than the carrying capacity
    if len(population) > carryingCap: 

        #Expression that calculates the amount of extra elephants needed to be culled
        animalsCulled = len(population) - carryingCap
        #Shuffles the population randomly to set up for random removal
        random.shuffle(population)
        #Creates a new list the size of the carrying capacity (removes the excess of elephants )
        newPopulation = population[:carryingCap]
    
    #Else statement for when the population is not bigger than the carrying capacity
    else:

        #No animals need to be culled
        animalsCulled = 0
        #The new population is the same as the old population as no change is needed
        newPopulation = population
    
    #Returns the amount of animals culled and the updated culled population
    return (newPopulation, animalsCulled)


def controlPopulation(parameters,population):
    '''This function determines whether the population should be darted or culled and calls the appropriate function given the population parameter list and the 
    population of elephants.'''

    # if the parameter value for percent darted is zero:
    if parameters[IDXPercentDarted] == 0:
        # call cullElephants, storing the return values in a two variables
        #( e.g. (newpop, numCulled) = cullElephants( parameters, population ))
        (newpop, numCulled) = cullElephants(parameters,population)

    # else
    else:
        # call dartElephants and store the result in a variable named newpop
        newpop = dartElephants(parameters,population)
        # set a variable named numCulled to zero
        numCulled = 0
    
    # return (newpop, numCulled)
    return (newpop, numCulled)

def simulateMonth(parameter, population):
    '''This function simulates the changes that happens to the population during one month using the list of population parameters and list elephant population. The 
    changes only affect female elephants (their months contraceptive remaining and their pregnancy time) and adds newborns to the population list of elephants
    if the female elephants gave birth.'''

    for e in population:
        # assign to gender the IDXGender item in e
        gender = e[IDXGender]
        # assign to age the IDXAge item in e
        age = e[IDXAge]
        # assign to monthsPregnant the IDXMonthsPregnant item in e
        monthsPregnant = e[IDXMonthsPregnant]
        # assign to monthsContraceptive the IDXMonthsContraceptiveRemaining item in e
        monthsContraceptive = e[IDXMonthsContraceptiveRemaining]

        #if gender is female and the elephant is an adult
        if gender == 'f' and parameter[IDXJuvenileAge] < age < parameter[IDXMaxAge]:

            # if monthsContraceptive is greater than zero
            if monthsContraceptive > 0:
                
                #decrement the months of contraceptive left (IDXMonthsContraceptiveRemaining element of e) by one
                e[IDXMonthsContraceptiveRemaining] = e[IDXMonthsContraceptiveRemaining] - 1

            # else if monthsPregnant is greater than zero
            elif monthsPregnant > 0:

                # if monthsPregnant is greater than or equal to 22
                if monthsPregnant >= 22:

                    # create a new elephant of age 1 and append it to the population list
                    babyElephant = newElephant(parameter, 1)
                    population.append(babyElephant)
                    # reset the months pregnant (the IDXMonthsPregnant element of e) to zero
                    e[IDXMonthsPregnant] = 0
                
                # else
                else:
                    # increment the months pregnant (IDXMonthsPregnant element of e) by 1
                    e[IDXMonthsPregnant] = e[IDXMonthsPregnant] + 1
            # else
            else:
                
                # if the elephant becomes pregnant
                if ( monthsContraceptive == 0 and random.random() < 1.0 / (parameter[IDXCalvingInterval]*12 - 22)):
                    
                    # set months pregnant (IDXMonthsPregnant element of e) to 1
                    e[IDXMonthsPregnant] = 1
    
    #Gives us the updated population
    return population

def simulateYear(parameter,population):
    '''This function calls the calcSurvival,incrementAge, and simulateMonth function 12 times to simulate what would happen to the population in a year using the 
    given list of population parameters and list population of elephants'''

    #Calculates which elephants survive this year
    population = calcSurvival(parameter,population)
    
    #Ages every elephant by a year
    incrementAge(population)

    #While loop that repeatedly calls simulateMonth 12 times
    i = 0 
    while i < 12:
        population = simulateMonth(parameter,population)
        i = i + 1

    #Gives us the updated population
    return population

def runSimulation(parameters):
    '''This function uses the given population parameters to run a simulation given the population parameters number of years and uses every created function
    to simulate an elephant population and their change over a course of the given years '''
    
    #Sets a variable to the population parameter index that holds the carrying capacity
    popsize = parameters[IDXCarryingCapacity]

    # initalizes the population
    population = initPopulation(parameters)
    [population,numCulled] = controlPopulation( parameters, population )

    # run the simulation for N years, storing the results
    results = []
    for i in range(parameters[IDXNumberYears]):
        
        population = simulateYear( parameters, population )
        
        [population, numCulled] = controlPopulation( parameters, population )
        
        results.append( calcResults( parameters, population, numCulled ) )
        
        if results[i][0] > 2 * popsize or results[i][0] == 0 : 
            # cancel early, out of control
            print( 'Terminating early' )
            break
        
    return results

def calcResults(parameter,population,culledAnimals):
    '''This function uses the population parameter list and population of elephants list to count how many elephants of each age demographic there are
    (calves, juveniles,adult males, adult females, and seniors) and creates and returns a list of the counted numbers of elephants while also including 
    the size of the population and the number of culled animals (given one of the parameters)'''

    #Sets variables to given parameter indexies
    juvenileAge = parameter[IDXJuvenileAge]
    maxAge = parameter[IDXMaxAge]
    calfAge = 1

    #Initalizes the counts of each elephant in age demographic
    numberCalves = 0
    numberJuvenile = 0
    numberAdultMales = 0
    numberAdultFemales = 0
    numberSenior = 0

    #For loop that loops for every elephant in the population
    for e in population:

        #Checks if elephant is a calf
        if e[IDXAge] <= calfAge:

            #Increments the count of calves
            numberCalves = numberCalves + 1

        #Checks if elephant is a juvenile
        if calfAge < e[IDXAge] <= juvenileAge:

            #Increments the count of juveniles
            numberJuvenile = numberJuvenile + 1

        #Checks if elephant is an adult and male
        if juvenileAge < e[IDXAge] <= maxAge and e[IDXGender] == 'm':

            #Increments the count of adult males
            numberAdultMales = numberAdultMales + 1

        #Checks if elephant is a an adult and female
        if juvenileAge < e[IDXAge] <= maxAge and e[IDXGender] == 'f':

            #Increments the count of adult females
            numberAdultFemales = numberAdultFemales + 1

        #Checks if elephant is a senior
        if e[IDXAge] >= maxAge:

            #Increments the count of senior elephants
            numberSenior = numberSenior + 1
        
    return [len(population),numberCalves,numberJuvenile,numberAdultMales,numberAdultFemales,numberSenior,culledAnimals]

def defaultParameters():
    '''This function creates and returns a list of default population parameters'''
    calvint= 3.1
    probdart= 0.0
    juvage= 12
    maxage=60
    probcalfsurvival= 0.85
    probadultsurvival= 0.996
    probseniorsurvival= 0.2
    carryingcapacity= 1000
    nyears=200

    parameterList = [calvint,probdart,juvage,maxage,probcalfsurvival,probadultsurvival,probseniorsurvival,carryingcapacity,nyears]
    return parameterList

def elephantSim(percDart, inputParameters = None):
    '''This function runs 5 simulations given a percentage darting and simulation parameters, stores the results of the simulations, calculates the average elephant 
    population per simulation, and then calculates and returns the difference between the carrying capacity and the average elephant population '''

    #If statements that decides whether or not to use default parameters
    if inputParameters == None:

        parameters = defaultParameters()

    #Uses given parameters
    else:
        parameters = inputParameters

    #Sets the darting percentage of the parameter list to the parameter of percDart
    parameters[IDXPercentDarted] = percDart

    #Sets the list of results from running a simulation to a value results
    results = runSimulation(parameters)

    #Loop that repeats 4 times and runs the simulation with the parameters
    for i in range(4):
        results = results + runSimulation(parameters)
    
    #Intializes total elephants
    totalElephants = 0

    #Loop that spans the entire results list
    for i in results:
        
        #counts the total elephant population
        totalElephants = totalElephants + i[0]

    #Finds the average elephant population
    avgTotalElephants = totalElephants / len(results)
    
    #Calculates the difference between the carrying capacity and averge elephant population
    difference = int(parameters[IDXCarryingCapacity] - avgTotalElephants)

    return difference

def main(argv):
    '''This is the main function that has a set list of default parameters that runs a simulation and prints out the average number of elephants that were calves, 
    juveniles, male adults, female adults, and seniors. While also printing out the average number of elephants culled. The function uses 2 command line arguments, 
    the name of the file and the wanted probability of female elephants darting'''

    #The terminal command line should look similar to this---> python3 elephant.py NUMBER BETWEEN 0.0-1.0

    #Sets command line argument to how many elephants should be darted
    probDart = float(sys.argv[1])

    #Set of default parameters
    calvingInterval = 3.1
    probDarting = probDart
    juvenileAge = 12
    maxAge = 60
    probabilityCalfSurvival = .85
    probabilityAdultSurvival = .996
    probabilitySeniorSurvival = .20
    carryingCapacity = 7000
    numberYears = 200

    #List of parameters
    parameterList = [calvingInterval,probDarting,juvenileAge,maxAge,probabilityCalfSurvival,probabilityAdultSurvival,probabilitySeniorSurvival,carryingCapacity,numberYears]

    #Runs a simulation and sets the results to a variable
    results = runSimulation(parameterList)

    #Intializes the counts of wanted information
    totalElephants = 0
    totalCalves = 0
    totalJuveniles = 0
    totalAdultFemales = 0
    totalAdultMales = 0
    totalSeniors = 0
    totalCulled = 0
    
    #Loop that spans through the entire length of results and gets the sum of each respective wanted information
    for i in results:
        totalElephants = totalElephants + i[0]
        totalCalves = totalCalves + i[1]
        totalJuveniles = totalJuveniles + i[2]
        totalAdultMales = totalAdultMales + i[3]
        totalAdultFemales = totalAdultFemales + i[4]
        totalSeniors = totalSeniors + i[5]
        totalCulled = totalCulled + i[6]

    #Calculates the averages of wanted information
    avgPopulation = totalElephants / len(results)
    avgCalves = totalCalves / len(results)
    avgJuveniles = totalJuveniles / len(results)
    avgAdultFemales = totalAdultFemales / len(results)
    avgAdultMales = totalAdultMales / len(results)
    avgSeniors = totalSeniors / len(results)
    numCulled = totalCulled / len(results)

    #Prints the results into terminal
    print("In An Average Year There Were: " + str(avgPopulation) + " Elephants, " + str(avgCalves) + " Calves, " + str(avgJuveniles) + " Juveniles, " + str(avgAdultMales) + " Male Adults, " + str(avgAdultFemales) +
    " Female Adults, " + str(avgSeniors) + " Seniors. " + str(numCulled) + " Elephants were culled")
   
    #This block of code below prints each individual result of the simulation into data.csv file and plots it into a graph using matplotlib 
    
    #Opens and creates a new data file and sets it up to be written in
    fp = open("data.csv", "w")

    #Writes into a new file the headers
    fp.write("Population, Calves, Juveniles, Adult Males, Adult Females, Seniors, Number Culled" + "\n")
    
    #For loop that spans the entirity of the list of results and writes every single result for every year into the data file
    for i in range(len(results)):

        fp.write( str(results[i][0]) + ", " + str(results[i][1]) + ", " + str(results[i][2]) + ", " + str(results[i][3]) + ", " + str(results[i][4]) + ", " + str(results[i][5]) + ", " + str(results[i][6]) + "\n" )

    #Closes the file
    fp.close()

    #Reopens the file to be read
    gp = open("data.csv", 'r')

    #Sets each row in the data file to be read
    line = gp.readline()
    
    #Intializes the x and multiple y axis lists that will be plotted
    # i will be used to extract individual indexies
    i = 0
    xList = []
    y1 = []
    y2 = []
    y3 = []
    y4 = []
    y5 = []
    y6 = []
    y7 = []

    '''For loop that spans the entire data file and appends the x axis list to be the total amount of rows (which can be seen as years passed) and y1-y7 to append 
    their respective information in terms of location on the index of results '''
    for line in gp:
        
        xList.append(i)
        i = i + 1
        
        words = line.split(",")
        population = words[0]
        y1.append(float(population))

        calf = words[1]
        y2.append(float(calf))
    
        juvenile = words[2]
        y3.append(float(juvenile))

        adultMale = words[3]
        y4.append(float(adultMale))
    
        adultFem = words[4]
        y5.append(float(adultFem))
    
        seniors = words[5]
        y6.append(float(seniors))

        culled = words[6]
        y7.append(float(culled))

    #X axis of graph which is just the years that are passing in the simulation
    x = xList

    #sets plot with what to use as points
    plt.plot(x,y1,'o', label = "Population")
    plt.plot(x,y2,'o', label = "Calves")
    plt.plot(x,y3,'o', label = "Juveniles")
    plt.plot(x,y4,'o', label = "Adult Males")
    plt.plot(x,y5,'o', label = "Adult Females")
    plt.plot(x,y6,'o', label = "Seniors")
    plt.plot(x,y7,'o', label = "Elephants Culled")

    #titles the plot
    plt.title("Population of Elephants in Different Age Demographics Given " + str(probDart) + " Population Darting")

    #Adds legend for easy reading
    plt.legend(loc="upper left")
    
    #shows graph
    plt.show()

    return

if __name__ == "__main__":
    #test()
    main(sys.argv[1])
