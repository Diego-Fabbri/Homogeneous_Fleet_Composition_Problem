
package default_package.fleet;

import Utility.Reader;
import ilog.concert.IloException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Vector;

public class Main {
    
    public static void main (String[] args) throws IloException, FileNotFoundException {
      
            System.setOut(new PrintStream("Homogeneous Fleet Composition Problem.log"));// questo comando permette di stampare in output i risultati su un file.log
            
            
      
                                                        // WARNING: insert your own path file
        Vector<Integer> DATA = Reader.ReaderI(new File("C:\\Users\\diego\\Documents\\NetBeansProjects\\FLEET\\FLEETCOMP.txt"));// il metodo prende in input il file indicato nel path
        Reader.printdata(DATA);
        int n = (DATA.size()-4) ;// numero di periodi t
        
        CPLEX_model m = new CPLEX_model(DATA,n);
        m.solveMovel();
    }
    
}
