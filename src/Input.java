import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Input {
    //Variable Declarations
    static double numValue;
    private static RMIInterface look_up;

    //"Measure" Num Value
    public double measureValue() {
        numValue = Math.random() * (50 - 20) + 20;
        return numValue;
    }

    public static void main(String[] args)
            throws MalformedURLException, RemoteException, NotBoundException, InterruptedException {

        look_up = (RMIInterface) Naming.lookup("//localhost/MyServer");
        Input inputProcess = new Input();
        int count = 0;

        //Can add Heartbeat pulse if needed
        while (true) {
            count++;
            look_up.getInput(inputProcess.measureValue());
            Thread.sleep(look_up.sendingInterval);
            //if (count == (10 + (10 / Integer.parseInt(args[0])))) {
             //   count = count / 0;
           // }
        }

    }
}
