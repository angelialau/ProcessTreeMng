/**
 * Created by jit_biswas on 2/1/2018.
 */
import java.io.File;
import java.util.*;

public class ProcessManagement {

    //set the working directory
    // private static File currentDirectory = new File("");
    //set the instructions file
    // private static File instructionSet = new File("test1.txt");
    public static Object lock=new Object();

    public static void main(String[] args) throws InterruptedException {

        //parse the instruction file and construct a data structure, stored inside ProcessGraph class
        ParseFile.generateGraph(new File(args[0]));

        // Print the graph information
        // WRITE YOUR CODE
        // ProcessGraph.printGraph(); // prints detailed ProcessGraph
        // ProcessGraph.printBasic();
        Stack<ArrayList<Integer>> stack = new Stack<>();
        

        // Using index of ProcessGraph, loop through each ProcessGraphNode, to check whether it is ready to run
        // check if all the nodes are executed
        // WRITE YOUR CODE
        ArrayList<Integer> terminalNodes = new ArrayList<>();
        for(ProcessGraphNode node: ProcessGraph.nodes){
            if(node.getChildren().size() == 0) terminalNodes.add(node.getNodeId());
            System.out.print("child node: " + node.getNodeId() + " ");
            System.out.print("parent nodes: ");
            for (ProcessGraphNode childNode : node.getParents())
                System.out.print(" " + childNode.getNodeId());
            System.out.print(" \n");
        }
        stack.push(terminalNodes);

        for(Integer nodeIndex : stack.peek()){ // end nodes 

        }

        //mark all the runnable nodes
        // WRITE YOUR CODE

        //run the node if it is runnable
        // WRITE YOUR CODE
            //change Executed to true

        System.out.println("All process finished successfully");
    }

}