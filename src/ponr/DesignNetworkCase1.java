/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ponr;

import ponr.PONR;
import java.util.Arrays;

/**
 *
 * @author admin
 */
public class DesignNetworkCase1 extends PONR{
       
    
    public DesignNetworkCase1() {
        this.setRestriccionesI(new double[16]);
        this.setNombre("Diseño de la red de intercambiadores de calor (caso 1)");
        this.setMejorConocido(189.31162966);
        this.setNumVariables(9);
        
        this.setOrdenVariables(new String[]{
                "x1","x2","x3","x4","x5","x6","x7","x8","x9"
            }
        );
        
        this.setRangos(new double[][]{
                 {0,10.0}
                ,{0,200.0}
                ,{0,100.0}
                ,{0,200.0}
                ,{1000.0,2_000_000.0}
                ,{0,600.0}
                ,{100.0,600.0}
                ,{100.0,600.0}
                ,{100.0,900.0}                
        }
        );
        
    }
    
        
    @Override
    public double[][] evaluarFO(double[][] x){
        
        for (double[] ind1 : x) {            
            //Evaluando la función objetivo
//                                        35 * x(1)^(0.6) + 35 * x(2)^(0.6)
            ind1[this.getNumVariables()] = 35 * Math.pow(ind1[0], 0.6) + 35 * Math.pow(ind1[1], 0.6);
            ind1[this.getNumVariables() + 1] = this.evaluarRestriccionesI(ind1);
        }
        return x;
        
    }
    
    private double evaluarRestriccionesI(double[] x){
        this.setSvr(0);
       
         
        this.getRestriccionesI()[0] = 200 * x[0] * x[3] - x[2];
        
        this.getRestriccionesI()[1] = 200 * x[1] * x[5] - x[4];// = 0
                                     
        this.getRestriccionesI()[2] = x[2] - 10_000 * (x[6] - 100); // = 0,
        
        this.getRestriccionesI()[3] = x[4] - 10_000 * (300 - x[6]); // = 0
        
        this.getRestriccionesI()[4] = x[2] - 10_000 * (600 - x[7]); // = 0;
        
        this.getRestriccionesI()[5] = x[4] - 10_000 * (900 - x[8]); // = 0
        
        this.getRestriccionesI()[6] = x[3] * Math.log( x[7] - 100 ) - x[3] * Math.log( 600 - x[6] ) - x[7] + x[6] + 500; // = 0
        
//        this.getRestriccionesI()[7] =  ind[5] * Math.log( (ind[8] - ind[6]) )  -  ind[5] * Math.log(600) - ind[8] + ind[6] + 600; // = 0
        
        //                           x6            ln   (x9 −    x7)     − x6             ln(600) − x9 + x7 + 600
        this.getRestriccionesI()[7] =  x[5] * Math.log( x[8] - x[6] )  -  x[5] * Math.log(600) - x[8] + x[6] + 600; // = 0

        System.out.println("Res: " + this.getRestriccionesI()[7]);
        
        //suma de violacion de restricciones
        for (int i = 0; i < this.getRestriccionesI().length; i++) {   
            
            if (1_000_000 >= this.getRestriccionesI()[i]) {
                
            }
            
            this.setSvr( this.getSvr() +  Math.abs( this.getRestriccionesI()[i] ) - 0.0001  );   
            System.out.println(this.getSvr());
        }

        return this.getSvr();
    }
    
    
    
}
