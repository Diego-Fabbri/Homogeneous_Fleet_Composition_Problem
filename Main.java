
package default_package.fleet;

import Utility.Reader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Vector;


public class Main {
    
    public static void main (String[] args) throws IOException, FileNotFoundException {
        
            System.setOut(new PrintStream("Fleet.log"));
            
       
        Vector<Integer> DATA = Reader.ReaderI(new File(args[0]));
        Reader.printdata(DATA);
        
        CPLEX_model m = new CPLEX_model(DATA);
        m.solveMovel();
    }
    
}
