# **Introduction**
This section describes the problem your team is trying to solve by writing this program, the primary design goals of the project (i.e., where is it most flexible), and the primary architecture of the design (i.e., what is closed and what is open). This section should discuss the program at a high-level (i.e., without referencing specific classes, data structures, or code).

This program will simulate different CAs depending on the XML file chosen by the user. One of the primary design goals is to have an abstract cell class where when the grid is updated it can update the cells without having to ask exactly what kind of cell it is every time. This will allow an easy way to add more simulations to the program. Moreover, we want to be able to simulate a grid of arbitrary length/width. The CAs that our project should be able to support initially are: Schelling’s model of segregation, Wa-Tor World model, Spreading of Fire, and the Game of Life.
 The cell’s specific methods should not be able to change. The only thing open to the user is the grid dimensions and the type of simulation that will run on the grid. 

# **Overview**

The program will begin with the Main class which creates a CellSociety object and calls its initialSetUp() method which prompts the user for input and reads the appropriate XML file through a ReadFileUtil class. Main then calls CellSociety’s setUpGrid() method which uses the acquired data to populate an initial grid with cells. The grid is held in a CellGrid class. Main then calls CellSociety’s step function which runs the simulation by looping through the CellGrid and updating the state as appropriate. Within this step function, the new state of each cell is passed to a Display object that updates the display seen by the user. This continues indefinitely until the user presses a certain key detected by CellSociety that restarts the program by prompting the user for another simulation. The CellGrid is populated with instances of Cell objects where the specific Cell subclass used is determined based on the type of simulation being run. A Rules class may be used as well to hold information about how to update a cell based on the simulation type.
![Design Visualization](https://coursework.cs.duke.edu/CompSci308_2017Spring/cellsociety_team07/blob/8f5ecf70349403419fe0e39076ff7bcc878f0ab5/CellSocietyDesignVisulaizationg.jpg)

# **User Interface**
At the program’s onset, there will be a ‘splash screen’ where the user can select which XML file to base the simulation off of (by selecting the appropriate button--buttons are used so that there are no erroneous situation). From there, the user is taken to the actual simulation UI, where each cell is represented by a square and each cell state is represented by a color.



# **Design Details**
## CLASSES:

1. Main:
    * Creates CellSociety Class
    * Justification: Need a main class to start program

2. ReadFileUtil:
    * Reads the XML file chosen by the user
    * Justification: Encapsulate all of the methods needed to read an XML file, and allow them to be called statically

3. CellSociety:
    * Initially sets up CA based on what ReadFile reads from XML file
    * Sets up CellGrid, and continuously updates CellGrid based on CA type (continues indefinitely)
    * Has method that checks if cell is an edge cell
    * Have a cheat key to stop the simulation and ask the user for another simulation
    * Has step function that loops through CellGrid to get current state and updates to the new state on a second pass.
    * Has initialSetUp() and setUpGrid() methods
    * Justification: Updates the cells in the grid

4. Display:
    * Takes information from CellGrid and displays the cells in a grid visualization 
    * Justification: We want all of the major JavaFX visualization methods to be contained in one simple class. Don’t want CellGrid to contain methods for visualization b/c then it would be in control of too much.

5. CellGrid:
    * Holds 2D array of Cell objects
    * Justification: A separate object to hold the grid of cells keeps this data encapsulated from other classes. Other classes can retrieve the data desired from CellGrid without having to worry about messy method implementations.

6. Cell:
    * Abstract class. Specifies a state (and state color) 
    * Methods:  getNeighbors(), updateCell(), setState(), getState(), getNextState()
    * Justification: Having an abstract Cell class forces each new cell subclass to implement certain methods according to the rules of the simulation and the generic method can be called in other classes without worrying if the correct implementation will be used
7. Rule
    * Abstract class. Specifies how a cell must behave based on its surroundings.
    * Justification: Can make new rules and use them to control the cells
    * Note: we are still contemplating whether we should use different rules or different cell subclasses for a given CA.

8. Cell subclass for each simulation:
    * Updates the cells depending on the type of cell society chosen and the rules that pertain to that society
    * To add a new simulation a new subclass would be created that contains the new rules and appropriate implementations of Cell’s abstract methods
    * Justification: By having a separate subclass for each simulation, it is easy to add a new simulation by following the process above

## USE CASES:

1. Apply the rules to a middle cell: set the next state of a cell to dead by counting its number of neighbors using the Game of Life rules for a cell in the middle (i.e., with all its neighbors)
    * Create a new type of cell called GameOfLifeCell. GameOfLifeCell’s getNextState() method returns dead if all neighbors are alive (this is determined by calling cell’s getNeighbors() function and calling getState() on all the neighbors to check if they are alive).
2. Apply the rules to an edge cell: set the next state of a cell to live by counting its number of neighbors using the Game of Life rules for a cell on the edge (i.e., with some of its neighbors missing)
    * The getNeighbors() function for a cell will coordinate with CellGrid to determine if the cell is an edge cell, and set the next state accordingly.
3. Move to the next generation: update all cells in a simulation from their current state to their next state and display the result graphically
    * Within CellSociety’s step function cell.getNextState() is called for each cell in CellGrid. Then on other pass through CellGrid, cell.updateCell() is called which correctly updates the cell to its next state. On this pass updateDisplay(cell) is also called which updates the display of cell based on its new state.
4. Set a simulation parameter: set the value of a parameter, probCatch, for a simulation, Fire, based on the value given in an XML fire
    * Use the java xml parser to get the probCatch value in the ReadFile Class. Store the value of probCatch in the corresponding Rule class, which is used to regulate a cell.
5. Switch simulations: use the GUI to change the current simulation from Game of Life to Wator
    * Use a cheat key in the cell society class to end the simulation and ask the user for another simulation and then read it from the xml file


# **Design Considerations**
This section describes any issues which need to be addressed or resolved before attempting to devise a complete design solution. It should include any design decisions that the group discussed at length (include pros and cons from all sides of the discussion) as well as any assumptions or dependencies regarding the program that impact the overall design. This section should go into as much detail as necessary to cover all your team wants to say.

Our group still hasn’t come to a conclusion as to whether different cell class implementations, or different rule class implementations would be better to accomplish the problem at hand. With cell class implementations, an abstract cell class would be extended by a new cell class for each type of CA simulation, and the new cell class would define how the cell is updated based on its surroundings. For this case, each new cell instance will need to keep track of variables like probCatch, but you could easily associate each state with a color. On the other hand, with rule class implementations, a Rule interface would be implemented by a new rule class for each type of CA simulation. The new Rule class would have a function that defines how a cell is updated based on its surroundings, and these rules would be passed to all of the cells. The complication with this is that it would be difficult associate a cell state with a color, but it would be easy to keep track of variables.

We also discussed different variations of a user interface. The simple variation would be to allow the user to only choose the type of simulation while the rest of the parameters are read from the appropriate XML file. Another variation of the user interface would give the user more control over certain parameters of the simulation by showing a specialized sub menu based on the initial simulation chosen. The sub menus will have sliders and buttons that allow the user to choose the value of more parameters for the simulation. The simple variation would be easier to code but does not allow the user to do much with the program other than run a simulation on a pre-chosen set up. The second variation gives the user more influence over the parameters of the simulation but would be more difficult to code and reduces the necessity of an XML file since most of the parameters are determined by the user.


# **Team Responsibilities**
1. Main: - Jimmy and Jonathan
    * Creates CellSociety Class
    * Justification: Need a main class to start program

2. ReadFileUtil: - Jimmy and Jonathan
    * Reads the XML file chosen by the user
    * Justification: Encapsulate all of the methods needed to read an XML file, and allow them to be called statically

3. CellSociety: - Jimmy and Jonathan
    * Initially sets up CA based on what ReadFile reads from XML file
    * Sets up CellGrid, and continuously updates CellGrid based on CA type (continues indefinitely)
    * Has method that checks if cell is an edge cell
    * Have a cheat key to stop the simulation and ask the user for another simulation
    * Has step function that loops through CellGrid to get current state and updates to the new state on a second pass.
    * Justification: Updates the cells in the grid

4. Display: - Jimmy and Derek
    * Takes information from CellGrid and displays the cells in a grid visualization 
    * Justification: We want all of the major JavaFX visualization methods to be contained in one simple class. Don’t want CellGrid to contain methods for visualization b/c then it would be in control of too much.

5. CellGrid: - Jonathan and Derek
    * Holds 2D array of Cell objects
    * Justification: A separate object to hold the grid of cells keeps this data encapsulated from other classes. Other classes can retrieve the data desired from CellGrid without having to worry about messy method implementations.

6. Cell: - Derek and Jonathan
    * Abstract class. Specifies a state (and state color) 
    * Methods:  getNeighbors(), updateCell(), setState(), getState(), getNextState()
    * Justification: Having an abstract Cell class forces each new cell subclass to implement certain methods according to the rules of the simulation and the generic method can be called in other classes without worrying if the correct implementation will be used

7. Cell subclass for each simulation: Derek and Jonathan
    * Life - Derek
    * Segregation - Derek
    * Wa-tor - Jonathan
    * Fire - Jonathan
