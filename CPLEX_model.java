package default_package.fleet;

import ilog.concert.*;
import ilog.cplex.*;
import java.util.Vector;

public class CPLEX_model {// definiamo le strutture dati caratteristiche del modello

    protected int n;
    protected IloCplex model;
    protected boolean CPLEX_status;
    protected Vector<Integer> d;
    protected IloIntVar v;
    protected IloIntVar dummy;// questa variabile ci serve per gestire inserimento di un termine costante nella funzione obiettivo
    protected IloIntVar[] y;
    protected IloIntVar[] x;

// adesso definiamo il costruttore
    public CPLEX_model(Vector<Integer> data, int n) throws IloException {// il modello prende in input il Vector dei dati di 56 elementi (52 valorei di v_t + 3 valori dei costi+ un valore relativo ad v_max)
        d = data;// 3 costs + 52 values of v_t + the value of v_max
        this.n = n;
        n = (d.size() - 4); // periodi di tempo
        CPLEX_status = false;// si setta il booleand relativo allo status inizialmente false
        y = new IloIntVar[data.size() - 4]; // 56-4
        x = new IloIntVar[data.size() - 4];
        model = new IloCplex();
    }

    public void addvariables() throws IloException {// definiamo con questo metodo le variabili da aggiungere al modello

        v = model.intVar(0, d.get(d.size() - 1), "v");// aggiungiamo la variabile v al modello dove d.getsize()-1= posizione 55 del Vector(ultima)= massimo valore di v_t=v_max
        //d.get(d.size()-1) corrisponde a prendere(d.get....) il valore in ultima posizione del Vector ovvero d.size()-1

        dummy = model.intVar(1, 1, "dummy");// variabile fittizia che assume valore 1

        for (int i = 0; i < (d.size() - 4); i++) {// con questo ciclo definiamo il campo di esistenza delle variabili
            y[i] = model.intVar(0, d.get(d.size() - 1), "y[" + (i + 1) + "]");// popoliamo il vettore y delle variabili
            x[i] = model.intVar(0, 1, "x[" + (i + 1) + "]");// popoliamo il vettore x delle variabili
        }

    }

    protected void addconstrains() throws IloException {

        for (int i = 0; i < n; i++) { // stiamo creando il vincolo (2) di pag 1 della traccia homework linearizzata
            // nella forma   v - v_max*x_t >= v_t - v_max
            IloLinearNumExpr expr = model.linearNumExpr();// creiamo un oggetto espressione lineare
// all'espressione aggiungiamo i termini dipendenti da variabili variabili 
            expr.addTerm(1, v);// aggiungiamo il termine  v all'espressione
            expr.addTerm(-d.get(d.size() - 1), x[i]);// stiamo aggiungendo il termine -v_max*x_t
//NOTA: expr = v-v_max*x_t
// adesso definiamo il vincolo aggiungendo anche i termini noti che mancano ovvero la quantità (v_t-v_max)
//v-v_max*x_t>=v_t-v_max

            model.addGe(expr, d.get(i + 3) - d.get(d.size() - 1));//per prendere il valore di v_t giusto le prime 3 posizioni del Vector si saltano poichè contegono i costi, ecco perchè i+3
        }
        for (int i = 0; i < n; ++i) { // stiamo creando il vincolo (3) di pag 1 della traccia homework linearizzata
            // ovvero   v - v_max*x_t <= v_t
            IloLinearNumExpr expr1 = model.linearNumExpr();// creiamo un oggetto espressione lineare
// all'espressione aggiungiamo i termini dipendenti da variabili variabili 
            expr1.addTerm(1, v);// aggiungiamo il termine  v all'espressione
            expr1.addTerm(-d.get(d.size() - 1), x[i]);// stiamo aggiungendo il termone -v_max*x_t
//NOTA: expr = v-v_max*x_t
// adesso definiamo il vincolo aggiungendo anche i termini noti che mancano ovvero la quantità v_t
//v-v_max*x_t<=v_t
            model.addLe(expr1, d.get(i + 3));
        }

        for (int i = 0; i < n; ++i) { // stiamo creando il vincolo (6) di pag 1 della traccia homework linearizzata
            // ovvero   y_t-v <= 0
            IloLinearNumExpr expr3 = model.linearNumExpr();// creiamo un oggetto espressione lineare
// all'espressione aggiungiamo i termini dipendenti da variabili variabili 
            expr3.addTerm(1, y[i]);// aggiungiamo il termine  y_t all'espressione
            expr3.addTerm(-1, v);// stiamo aggiungendo il termone -v
//NOTA: expr = y_t-v
// adesso definiamo il vincolo aggiungendo anche i termini noti che mancano ovvero la quantità v_t
//y_t-v<=0
            model.addLe(expr3, 0);
        }

        for (int i = 0; i < n; ++i) { // stiamo creando il vincolo (7) di pag 1 della traccia homework linearizzata
            // ovvero   y_t-v_max*x_t <= 0
            IloLinearNumExpr expr4 = model.linearNumExpr();// creiamo un oggetto espressione lineare
// all'espressione aggiungiamo i termini dipendenti da variabili variabili 
            expr4.addTerm(1, y[i]);// aggiungiamo il termine  y_t all'espressione
            expr4.addTerm(-d.get(d.size() - 1), x[i]);// stiamo aggiungendo il termone -v_max*x_t
//NOTA: expr = y_t - v_max*x_t
// adesso definiamo il vincolo aggiungendo anche i termini noti che mancano ovvero la quantità v_t
//y_t-v_max*x_t<=0
            model.addLe(expr4, 0);
        }

        for (int i = 0; i < n; ++i) { // stiamo creando il vincolo (8) di pag 1 della traccia homework linearizzata
            // ovvero   y_t -v -v_max*x_t >= -v_max
            IloLinearNumExpr expr5 = model.linearNumExpr();// creiamo un oggetto espressione lineare
// all'espressione aggiungiamo i termini dipendenti da variabili variabili 
            expr5.addTerm(1, y[i]);// aggiungiamo il termine  y_t all'espressione
            expr5.addTerm(-1, v);// aggiungiamo il termine  v all'espressione
            expr5.addTerm(-d.get(d.size() - 1), x[i]);// stiamo aggiungendo il termone -v_max*x_t
//NOTA: expr = y_t-v_max*x_t-v
// adesso definiamo il vincolo aggiungendo anche i termini noti che mancano ovvero la quantità v_t
//y_t - v_max*x_t -v >= -v_max

            model.addGe(expr5, -d.get(d.size() - 1));
        }

//ADESSO SCRIVIAMO LA FUNZIONE OBIETTIVO LINEARIZZATA (1)
    }

    protected void addObjective() throws IloException {
        
        IloLinearNumExpr Obj = model.linearNumExpr();
            Obj.addTerm(n*d.get(0), v);
            int value = 0;
            for (int i = 0; i < n; ++i) {
                Obj.addTerm(d.get(1)*d.get(i + 3), x[i]);
                Obj.addTerm(-d.get(1), y[i]);
                value += d.get(2)*d.get(i + 3);
                Obj.addTerm(-d.get(2)*d.get(i + 3), x[i]);
                Obj.addTerm(d.get(2), y[i]);
            }
            Obj.addTerm(-n*d.get(2), v);
            Obj.addTerm(value, dummy);
            Obj.addTerm(n*d.get(1), v);
            
            model.addObjective(IloObjectiveSense.Minimize, Obj); 

    }

    public void solveMovel() throws IloException {

        addvariables();
        addconstrains();
        addObjective();
        model.exportModel("Homogeneous Fleet Composition Problem.lp");
        model.solve();
        System.out.println("Objective value = " + model.getObjValue());
        System.out.println(v.getName() + " = " + model.getValue(v));

    }

}
