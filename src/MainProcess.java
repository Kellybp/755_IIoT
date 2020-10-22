import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.lang.String;

public class MainProcess extends UnicastRemoteObject implements RMIInterface {

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
        System.out.println("2: " + numValue*3);
        System.out.println("----------------------------------");
    }

    //To make the interface work
    public void getValues(int exponents[]){
        //Nothing
    }

    public void logValues(String args[], String process){
        //WIP
        //Create file if not exist
        //Write Value to it
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
