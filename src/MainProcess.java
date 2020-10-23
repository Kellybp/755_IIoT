
import java.io.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.lang.String;
import java.util.HashMap;
import java.util.Map;

public class MainProcess extends UnicastRemoteObject implements RMIInterface {
    private static int lines;
    public MainProcess() throws RemoteException {
        super();
    }
    //Variable Declarations
    private static long lastUpdateTime;
    private double lastValue = 0.00;

    @Override
    //Checking if pressure is correct
    public void getInput(double numValue) throws RemoteException {
        //System.out.println("Primary: " + primarySenderProcess + "Process: " + processId); //Test to view processes sending to this.process
        System.out.println("----------------------------------");
        System.out.println("Initial Value Recorded: " + numValue);
        System.out.println("Exponents: \n1: " + numValue*numValue);
        double outputValue = numValue * 3;
        System.out.println("2: " + outputValue);
        if(outputValue != Math.pow(numValue,3)){
            System.out.println("UNPATCHED CODE");
        } else {
            System.out.println("PATCHED CODE");
        }
        System.out.println("----------------------------------");


    }

    //To make the interface work
    public void getValues(int exponents[]){
        //Nothing
    }

    @Override
    public void logValues(double num, int count) throws IOException {
        //WIP
        //Create file if not exist
        //Write Value to it
        if(count == lines){
            System.out.println("UPTO DATE");
        } else {
            System.out.println("DATA MISSING");
        }

        File mainLogs = new File("mainLogs.txt");
        mainLogs.createNewFile();
        FileWriter fw = new FileWriter(mainLogs, true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        lines++;
        pw.println("Input="+num + " : Output=" + num * 3);
        pw.close();
        bw.close();
        fw.close();
    }

    public static void main(String[] args) throws InterruptedException {
        //Connection to Server
        try {
            Naming.rebind("//localhost/MyServer", new MainProcess());
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }

        //Heartbeat
        /*while (true) {
            Thread.sleep(RMIInterface.sendingInterval);
            if (new Date().getTime() - lastUpdateTime > RMIInterface.sendingInterval) {
                ProcessFailure();
            }
        }*/
    }

    //Tell user of process failure, new process creation, and primary process shift
    //Implement new process and shift primary process id
    public static void ProcessFailure(){
        System.out.println("\n======================\n");
        System.out.println("Ded");
        System.out.println("\n======================\n");
    }
}
