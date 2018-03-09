/**
 * Created by jit_biswas on 2/1/2018.
 */
import java.io.*;
import java.util.*;

public class ProcessManagement {

    //set the working directory
    //TODO: (1) change the pathname respectively to where the code is located
    private static File currentDirectory = new File("/Users/thamyeeting/IdeaProjects/ProcessTreeMng/src");
    //set the instructions file
    //TODO: (2) change the pathname to the name of the test file
    private static File instructionSet = new File("test2.txt");

    public static Object lock=new Object();

    public static void main(String[] args) throws InterruptedException {

        //parse the instruction file and construct a data structure, stored inside ProcessGraph class
        ParseFile.generateGraph(new File(currentDirectory + "/" + instructionSet));
        ProcessBuilder processBuilder = new ProcessBuilder();

        //create a allProcesses ArrayList to keep count of which nodes have been executed
        //nodes executed will be removed from allProcesses
        ArrayList<ProcessGraphNode> allProcesses = new ArrayList<>();

        //adding all the nodes to allProcesses
        for(ProcessGraphNode node : ProcessGraph.nodes){
            allProcesses.add(node);
        }
        // Print the graph information with the current nodes information
        ProcessGraph.printGraph();

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
                else{
                    break;
                }
            }
        }

        // then keep checking processes whose parents have been executed
        // while loop will only terminate when allProcesses have no more nodes inside
        // else, program also exits while loop if there is an error running one of the nodes
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
                    else if (!success){ //There is an error
                        break;
                    }
                }
            }
            break;
        }
        System.out.println("\n");
        // Basic information of each node is printed after the execution.
        ProcessGraph.printBasic();

        boolean allExecuted = true;
        for(ProcessGraphNode node: ProcessGraph.nodes){
            if(!node.isExecuted()) allExecuted = false;
        }
        if(allExecuted) System.out.println("All processes finished successfully");
        else System.out.println("Not all processes were finished successfully");
    }

    public static boolean runCommand(ProcessGraphNode node){
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(node.getCommand().split(" "));
        // if the input file or the output file is not stdin or stdout respectively
        // redirect the input file and output file
        if(!node.getInputFile().equals(new File("stdin"))) processBuilder.redirectInput(node.getInputFile());
        if(!node.getOutputFile().equals(new File("stdout"))) processBuilder.redirectOutput(node.getOutputFile());

        try{
            Process process = processBuilder.start();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            for (String line; (line = bufferedReader.readLine()) != null;) {
                System.out.println(line);  }
            bufferedReader.close();
        }
        catch(Exception e){
            System.out.println("\nError occured while running node " + node.getNodeId());
            System.out.println(e.toString());
            return false;
        }

        return true;
    }

}