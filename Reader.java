
package Utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Vector;


public class Reader {

    public static Vector<Integer> ReaderI(File file) throws FileNotFoundException, IOException {
        

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine().trim();
            String[] elements = line.split("\\s+");
            int Fixed_cost = Integer.parseInt(elements[0]);// first element of first line in file FLEETCOMP.txt
            int Variable_cost = Integer.parseInt(elements[1]); // fsecond element of first line in file FLEETCOMP.txt
            int Hiring_cost = Integer.parseInt(elements[2]);// third element of first line in file FLEETCOMP.txt
            line = reader.readLine().trim();// move to the following line, the secondo of file FLEETCOMP.txt
            int dim = Integer.parseInt(line);// save the numer of remaining lines=52
            Vector<Integer> data = new Vector<Integer>(dim + 3);
            data.add(Fixed_cost);
            data.add(Variable_cost);
            data.add(Hiring_cost);
            for (int i = 0; i < dim; ++i) {
                line = reader.readLine().trim();
                data.add(Integer.parseInt(line));
            }
            List subvector = data.subList(3, data.size());// save in a list che values of v_t
            int max = (int) Collections.max(subvector);// save max value of v_t == v_max
            data.add(max);
            return data;
    }

    public static void printdata(Vector<Integer> d) {
        for (int i = 0; i < d.size(); ++i) {
            if (i == 0) {
                System.out.println("Fixed cost = " + d.get(i));
            } else if (i == 1) {
                System.out.println("Variable cost = " + d.get(i));
            } else if (i == 2) {
                System.out.println("Hiring cost = " + d.get(i));
            } else if (i > 2 & i < d.size() - 1){
                System.out.println("v[" + (i - 2) + "] = " + d.get(i));
            } else {
                System.out.println("mav value = " + d.get(i));
            }
        }

    }

}
