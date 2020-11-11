//import java.rmi.Naming;
//import java.rmi.RemoteException;
//import java.rmi.server.UnicastRemoteObject;
//import java.util.Date;
//import java.lang.String;
//
//public class Output extends UnicastRemoteObject implements RMIInterface {
//
//    public Output() throws RemoteException {
//        super();
//    }
//    //Variable Declarations
//    private static long lastUpdateTime;
//    private double lastValue = 0.00;
//
//    //Checking if pressure is correct
//    public void getInput(double numValue) throws RemoteException {
//        //Nothing
//    }
//
//    //To make the interface work
//    public void getValues(int exponents[]){
//        //Nothing
//    }
//
//    public void logValues(String args[], String process){
//        //WIP
//        //Create file if not exist
//        //Write Value to it
//    }
//
//    public static void main(String[] args) throws InterruptedException {
//        //Connection to Server
//        try {
//            Naming.rebind("//localhost/MyServer", new MainProcess());
//        } catch (Exception e) {
//            System.err.println("Server exception: " + e.toString());
//            e.printStackTrace();
//        }
//
//        //Heartbeat
//        /*while (true) {
//            Thread.sleep(RMIInterface.sendingInterval);
//            if (new Date().getTime() - lastUpdateTime > RMIInterface.sendingInterval) {
//                ProcessFailure();
//            }
//        }*/
//    }
//
//    //Tell user of process failure, new process creation, and primary process shift
//    //Implement new process and shift primary process id
//    public static void ProcessFailure(){
//        System.out.println("\n======================\n");
//        System.out.println("Ded");
//        System.out.println("\n======================\n");
//    }
//}
