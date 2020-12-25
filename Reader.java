/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Vector;


/**
 *
 * @author Besitzer
 */
public class Reader {// questa classe permette di leggere il file .txt  e salvare il contenuto in un Vector
    
    public static Vector<Integer> ReaderI (File file){// il metodo legge in input i parametri del prblema dal file FLEETCOMP.txt e il inserisve in un Vector di java
        try {
            
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine().trim();
            String[] elements = line.split("\\s+");
              // il trim elimina tutto ciò che è ininfluente a sinistra e destra rispettivamente 
//del primo e dell'ultimo carattere significativo
            int Fixed_cost = Integer.parseInt(elements[0]);// nella prima riga del file il primo elemento (posizioe 0)  è il costo fisso
            int Variable_cost = Integer.parseInt(elements[1]);// nella prima riga del file il secondo elemento (posizioe 1)  è il costo variabile
            int Hiring_cost = Integer.parseInt(elements[2]);// nella prima riga del file il terzo elemento (posizioe 2)  è il costo di noleggio
            line = reader.readLine().trim();// andiamo alla riga successiva del file (ovvero la seconda)
            int dim = Integer.parseInt(line);//creiamo un intero il cui valore è pari al numero di righe del file di lettura meno la prima che è stata già letta
            //NB: il file lettura ha 53 righe, 52 dedicate ai valori di v_t e una relativa ai tre costi
            Vector<Integer> data = new Vector<Integer>(dim + 3);// creiamo un vector di dimensione dim(dim=52) + 3 (i costi sopra)
            // tale vettore conterrà i parametri del problema ovvero i 52(dim=52) valore di v_t e i 3 costi
            data.add(Fixed_cost);// in posizione 0 del vector mettiamo il costo fisso
            data.add(Variable_cost);// in posizione 1 del vector mettiamo il costo variabile
            data.add(Hiring_cost);// in posizione 2 del vector mettiamo il costo di noleggio
            // abbiamo popolato le prime 3 posizione del vectoe  con i costi adesso rimangono 
            //le 52 posizioni da popolare con i valori di v_t
            
      
            for (int i = 0; i < dim; ++i) {//partendo dalla seconda riga del file di lettura txt, per le successive 52=dim righe
                line = reader.readLine().trim();// si legge il valore sulla riga i correnti, permette di andare a capo
                data.add(Integer.parseInt(line));//lo si aggiunge al vector
            }
            List subvector = data.subList(3, data.size());// creiamo una Lista, contenente solo i valori di v_t, 
        //ovvero quello dalla poszione 3 all'ultima posizione del vector d in input (d.size())
        int max = (int) Collections.max(subvector);// ci salviamo il massimo valore di v_t
        data.add(max);// aggiungiamo il valore v_max in ultima poszione al Vector
            return data;// finito il ciclo di lettura si restituisce il vector con i dati
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static void printdata (Vector<Integer> d) {//METODO DI STAMPA del Vector in input con i dati
        System.out.println("Dimensione del vector = " + d.size());
        for (int i = 0; i < d.size(); ++i) {
            if (i == 0) {// in posizione 0(numerazione java) abbiamo il costo fisso
              //  System.out.println("Fixed cost = "+d.get(i)); 
              System.out.println("Costo Fisso = "+d.get(i)); 
            } else if (i == 1) {// in posizione 1(numerazione java) abbiamo il costo variabile
                //System.out.println("Variable cost = "+d.get(i)); 
                 System.out.println("Costo Variabile = "+d.get(i)); 
            } else if (i == 2) {// in posizione 2(numerazione java) abbiamo il costo di noleggio
                //System.out.println("Hiring cost = "+d.get(i));
                System.out.println("Costo Noleggio= "+d.get(i));
            } else{ if(i>2 & i<d.size()-1){// dalla posizione 3(numerazione java) in poi abbiamo i valori di v_t fino alla penultima posizione del vector
                System.out.println("v["+ (i - 2)+ "] = "+d.get(i));}
            else{// siamo in ultima poszione del vector dove è salvato il massimo
                System.out.println("max value = "+ d.get(i));// stampiamo il massimo valore di v_t
                    }
            }
        }
        //List subvector = d.subList(3, d.size());// creiamo una Lista, contenente solo i valori di v_t, 
        //ovvero quello dalla poszione 3 all'ultima posizione del vector d in input (d.size())
       // int max = (int) Collections.max(subvector);// ci salviamo il massimo valore di v_t
        //System.out.println("mav value = "+ max);// stampiamo il massimo valore di v_t
    }
   //  public static int vmax(Vector<Integer> d) {
  //       List subvector = d.subList(3, d.size());
   //      int max = (int) Collections.max(subvector);
    //     return max;
    // }
}
