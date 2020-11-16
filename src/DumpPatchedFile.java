import java.lang.instrument.*;
import java.io.*;

public class DumpPatchedFile {
	public static void agentmain(String agentArgs, Instrumentation inst)
	{
		try
		{
			System.out.println("Redefining DataProcessor class ...");
			File f = new File("/Users/raj.g/Documents/Courses/755SArch/patchability/755_IIoT/GhostSystem/target/classes/DataProcessor.class");
			byte[] reporterClassFile = new byte[(int) f.length()];
			DataInputStream in = new DataInputStream(new FileInputStream(f));
			in.readFully(reporterClassFile);
			in.close();
			ClassDefinition reporterDef = new ClassDefinition(Class.forName("DataProcessor"), reporterClassFile);
			inst.redefineClasses(reporterDef);
			System.out.println("Redefined DataProcessor class");
		}
		catch(Exception e)
		{
			System.out.println(e);
			e.printStackTrace();
		}
	}
}
