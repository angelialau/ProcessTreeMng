/∗Programming Assignment 1 

∗ Author : Angelia Lau, Tham Yee Ting

∗ ID: 1002417, 1002516

∗ Date: 03/02/2018
∗/
# ProcessTreeMng

###The purpose of your program:
The purpose of the program is to construct and 
generate a directed acyclic graph (DAG) of user 
programs from an input text file. The program will 
traverse the constructed DAG and execute it by reading
the commands in the text file. The program models control 
dependencies and data dependencies.

###How to compile your program:
1) In ProcessManagement.java, under TODO (1), change the path name for currentDirectory 
respectively to where ProcessTreeMng/src. 

2) In ProcessManagement.java, under TODO(2), change the path name for instructionSet 
respectively to the name of the test file. This test file must be located in the currentDirectory.

3) To run the program, 
    (a) IDE:
        Run ProcessManagement.java after you have changed the path name for currentDirectory
        as well as the instructionSet. 
    
    (b) Terminal:
        Change directory to where the ProcessTreeMng/src is located.
        This can be done with the use of cd:
        E.g. $ cd thamyeeting/IdeaProjects/ProcessTreeMng/src
        
        Compile ProcessManagement.java:
        E.g. $ javac ProcessManagement.java
        
        Run ProcessManagmenet.java:
        E.g. $ java ProcessManagement

4) To ensure that the program ran smoothly, check the results. If all processes finish smoothly,
"All processes finished successfully" will be shown after the test. Else, "Not all processes were 
finished successfully" will be shown. The error message will be displayed accordingly.
        

###What exactly your program does:
1) Each node in the graph represents a program to be run. Input nodes are written to 
an input file where each line represents a node. Each line should appear to be:
<program name with its arguments if any>: <children nodes>: <input file>: <output file>

2) The program uses ParseFile to generate a ProcessGraph from the input text file. 
The program reads each formatted line of the input text file using Scanner and parse
the information in it via colon delimiter. The program name with its arguments if any, 
child node(s), input file and output file are stored for each respective node added to ProcessGraph.nodes. 
The first line of the input text file is given the node with index 0, the second line is given node with index 1,
etc. 

3) The graph information of each node, including its index, parent node, children nodes, command,
input file name, output file name is shown. Each node is initiated to runnable boolean of true and 
executed boolean of false. The executed boolean will be set to true only after the program has run
the node.

4) In ProcessManagement.java, the ArrayList allProcesses stores all the nodes in ProcessGraph.nodes.
The purpose of allProcesses is to keep track of the executed nodes. Executed nodes will be removed 
from allProcesses. Hence, program only terminates when allProcesses is empty or if an error is detected.

5) Our program traverses through all nodes and identify the nodes without parents and is runnable 
and not executed yet. These nodes with no prior dependencies are run first and removed from allProcesses
when done. 

6) While allProcesses is not empty, meaning that not all the nodes are executed, our program 
repetitively checks every node to check if the prior dependencies have finished their execution. If the node's 
parent(s) have finished execution, the program then runs the node and remove the node from allPrcoesses after
its execution. 

7) The function runCommand is responsible for the running of each node. If the input and output of the node are not
stdin and stdout respectively, they are redirected accordingly to their input file and output file names.

8) If all the nodes have executed successfully, the while loop terminates and the message "All processes finished
 successfully" will be shown. Else, if any node returns a false boolean for its runCommand, the break statement the while loop 
 and the message "Not all processes were finished successfully" will be shown.