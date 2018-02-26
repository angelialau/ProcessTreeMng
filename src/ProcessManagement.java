/**
 * Created by jit_biswas on 2/1/2018.
 */
import java.io.*;
import java.util.*;
import java.nio.file.*;
import java.nio.charset.*;

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
        ArrayList<ProcessGraphNode> allProcesses = new ArrayList<>();

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

            allProcesses.add(node);
        }

        // check if all the nodes are executed
        // WRITE YOUR CODE
        
        // tree traversal
        // first, find all the parent nodes with no prior dependencies 
        for(ProcessGraphNode node: ProcessGraph.nodes){
            if(node.getParents().size() == 0 && node.isRunnable() && !node.isExecuted()) {
                System.out.println("Running node " + node.getNodeId() + " as a starting node.");
                boolean success = runCommand(node);
                if(success){
                    node.setExecuted();
                    allProcesses.remove(node);
                    System.out.println("Finished executing process/node " + node.getNodeId());
                }
                
            } 
        } 

        // then keep checking processes whose parents have been executed 
        while(!allProcesses.isEmpty()){
            
            // if parents have been executed, can just execute too
            for(ProcessGraphNode node : ProcessGraph.nodes){
                if (allProcesses.contains(node) && node.allParentsExecuted() && node.isRunnable() && !node.isExecuted()){
                    System.out.println("Running child node " + node.getNodeId() + " because it's parents have completed execution");
                     boolean success = runCommand(node);
                    if(success){
                        node.setExecuted();
                        allProcesses.remove(node);
                        System.out.println("Finished executing process/node " + node.getNodeId());
                    }
                }
            }
        }
        boolean allExecuted = true;
        for(ProcessGraphNode node: ProcessGraph.nodes){
            if(!node.isExecuted()) allExecuted = false;
        }
        if(allExecuted) System.out.println("All process finished successfully");
        else System.out.println("Not all processes were finished successfully");
    }

    public static boolean runCommand(ProcessGraphNode node){
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(node.getCommand().split(" "));
        if(!node.getInputFile().equals(new File("stdin"))) processBuilder.redirectInput(node.getInputFile());
        if(!node.getOutputFile().equals(new File("stdout"))) processBuilder.redirectOutput(node.getOutputFile());

        try{
            Process process = processBuilder.start();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            for (String line; (line = bufferedReader.readLine()) != null;) {
                System.out.println(line);  }
            bufferedReader.close();
        }catch(Exception e){
            System.out.println("there appears to be an error:");
            System.out.println(e.getMessage());
            return false;
        }

        return true;
        
    }

}