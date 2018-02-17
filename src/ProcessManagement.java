/**
 * Created by jit_biswas on 2/1/2018.
 */
import java.io.*;
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
        ProcessBuilder processBuilder = new ProcessBuilder();

        // Print the graph information
        // WRITE YOUR CODE
        // ProcessGraph.printGraph(); // prints detailed ProcessGraph
        // ProcessGraph.printBasic();
        for(ProcessGraphNode node : ProcessGraph.nodes){
            System.out.print("node: " + node.getNodeId() + " ");
            System.out.print("parent nodes: ");
            for (ProcessGraphNode childNode : node.getParents())
                System.out.print(" " + childNode.getNodeId());
            System.out.print(" \n");
            System.out.print("child nodes: ");
            for (ProcessGraphNode childNode : node.getChildren())
                System.out.print(" " + childNode.getNodeId());
            System.out.print(" \n");
        }

        // Using index of ProcessGraph, loop through each ProcessGraphNode, to check whether it is ready to run
        // check if all the nodes are executed
        // WRITE YOUR CODE
        
        for(ProcessGraphNode node: ProcessGraph.nodes){
            if(node.getParents().size() == 0 && node.isRunnable() && !node.isExecuted()) {
                System.out.println("Running node " + node.getNodeId() + " as a starting node.");
                processBuilder.command(node.getCommand());
                // if(String.valueOf(node.getInputFile()).equalsIgnoreCase("stdin")){
                //     // Scanner sc = new Scanner(System.in);
                // } else processBuilder.redirectInput(node.getInputFile());
                // if(String.valueOf(node.getOutputFile()).equalsIgnoreCase("stdout")){
                //     processBuilder.redirectOutput();
                // } else processBuilder.redirectOutput(node.getOutputFile());
                
                try{
                    Process process = processBuilder.start();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    for (String line; (line = bufferedReader.readLine()) != null;) {
                        System.out.println(line);  }
                    bufferedReader.close();
                }catch(Exception e){
                    System.out.println(e.getMessage());
                }

                node.setExecuted();
                System.out.println("Finished executing process/node " + node.getNodeId());

            } else{
                
            }
            
        } 

        //mark all the runnable nodes
        // WRITE YOUR CODE

        //run the node if it is runnable
        // WRITE YOUR CODE
            //change Executed to true

        System.out.println("All process finished successfully");
    }

}