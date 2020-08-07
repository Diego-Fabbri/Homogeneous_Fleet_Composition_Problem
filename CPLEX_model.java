
package default_package.fleet;
import ilog.concert.*;
import ilog.cplex.*;
import java.util.Vector;

public class CPLEX_model {
    protected IloCplex model;
    protected boolean CPLEX_status;
    protected Vector<Integer> d;
    protected IloIntVar v;
    protected IloIntVar dummy;
    protected IloIntVar[] y;
    protected IloIntVar[] x;
    
    public CPLEX_model (Vector<Integer> data) {
        d = data;// 3 costs + 52 values of v_t + the value of v_max
        CPLEX_status = false;
        try {
            model = new IloCplex();
            y = new IloIntVar[data.size() - 4];// == 52
            x = new IloIntVar[data.size() - 4];
        } catch (IloException e) {
            e.printStackTrace();
        }
    }
    
    protected void addvariables () throws IloException {
            v = model.intVar(0, d.get(d.size() - 1), "v");
            dummy = model.intVar(1, 1, "dummy");
            for (int i = 0; i < 52; ++i) {
                y[i] = model.intVar(0, d.get(d.size() - 1), "y["+(i + 1)+"]");
                x[i] = model.intVar(0, 1, "x["+(i + 1)+"]");
            }

    }
    
    protected void addconstrains () throws IloException {
        // Constrains (2) of attached pdf File
            for (int i = 0; i < (d.size() - 4); ++i) {
                IloLinearNumExpr exp = model.linearNumExpr();
                exp.addTerm(1, v);// v
                exp.addTerm(-d.get(d.size() - 1), x[i]);
                model.addGe(exp, d.get(i + 3) - d.get(d.size() - 1)); 
                
         // Constrains (3) of attached pdf File       
                IloLinearNumExpr exp1 = model.linearNumExpr();
                exp1.addTerm(1, v);
                exp1.addTerm(-d.get(d.size() - 1), x[i]);
                model.addLe(exp1, d.get(i + 3));
         // Constrains (6) of attached pdf File       
                IloLinearNumExpr exp3 = model.linearNumExpr();
                exp3.addTerm(1, y[i]);
                exp3.addTerm(-1, v);
                model.addLe(exp3, 0);
         // Constrains (7) of attached pdf File       
                IloLinearNumExpr exp4 = model.linearNumExpr();
                exp4.addTerm(1, y[i]);
                exp4.addTerm(-d.get(d.size() - 1), x[i]);
                model.addLe(exp4, 0);
         // Constrains (8) of attached pdf File       
                IloLinearNumExpr exp5 = model.linearNumExpr();
                exp5.addTerm(1, y[i]);
                exp5.addTerm(-1, v);
                exp5.addTerm(-d.get(d.size() - 1), x[i]);
                model.addGe(exp5, -d.get(d.size() - 1));
            }
            
        
    }
    // (1) of attached pdf File
    protected void addObjective () throws IloException {
      
            IloLinearNumExpr Obj = model.linearNumExpr();
            Obj.addTerm(52*d.get(0), v);
            int value = 0;
            for (int i = 0; i < 52; ++i) {
                Obj.addTerm(d.get(1)*d.get(i + 3), x[i]);
                Obj.addTerm(-d.get(1), y[i]);
                value += d.get(2)*d.get(i + 3);
                Obj.addTerm(-d.get(2)*d.get(i + 3), x[i]);
                Obj.addTerm(d.get(2), y[i]);
            }
            Obj.addTerm(-52*d.get(2), v);
            Obj.addTerm(value, dummy);
            Obj.addTerm(52*d.get(1), v);
            
            model.addObjective(IloObjectiveSense.Minimize, Obj); 
       
    }
    
    public void solveMovel() {
        try {
            addvariables();
            addconstrains();
            addObjective();
            model.exportModel("Fleet.lp");
            model.solve();
            System.out.println("Objective value = "+model.getObjValue());
            System.out.println(v.getName() + " = "+model.getValue(v));
        } catch (IloException e) {
            e.printStackTrace();
        }
    }
    
}
