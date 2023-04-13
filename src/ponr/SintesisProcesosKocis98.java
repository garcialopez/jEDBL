/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ponr;

/**
 *
 * @author admin
 */
public class SintesisProcesosKocis98 extends PONR{
    
    public SintesisProcesosKocis98() {
        this.setNombre("Process synthesis MINLP de Kocis and Grossmann (1998)");
        this.setMejorConocido(2.0);
        this.setNumVariables(2);
        
        this.setOrdenVariables(new String[]{
                "x1","x2"
            }
        );
        
        this.setExistePertenencia(new boolean[]{
                false,true
                }
        );
        
        this.setRangos(new double[][]{
                 {0,1.6}
                ,{0, 1}        // indice de pertenencia                         
        }
        );
        
        this.setRestriccionesD(new double[2]);
        this.setCompRestriccion(new double[]{
                0,1.6
            }
        );
        
        
        
    }
    
        
    @Override
    public double[][] evaluarFO(double[][] x){
        
        for (double[] ind1 : x) {            
            //Evaluando la función objetivo
//                                        35 * x(1)^(0.6) + 35 * x(2)^(0.6)
            ind1[this.getNumVariables()] = ind1[1] + 2 * ind1[0];
            ind1[this.getNumVariables() + 1] = this.evaluarRestriccionesD(ind1);
        }
        return x;
        
    }
    
  
    @Override
    protected double evaluarRestriccionesD(double[] x){
        this.setSvr(0);
       
         
        this.getRestriccionesD()[0] = - Math.pow(x[0], 2) - x[1] + 1.25;        
        this.getRestriccionesD()[1] = x[0] + x[1];
                                     
       
        //suma de violacion de restricciones
        for (int i = 0; i < this.getRestriccionesD().length; i++) {   
                      
            this.setSvr( this.getSvr() +  Math.max(0
                    , (this.getRestriccionesD()[i] - this.getCompRestriccion()[i])
                ) 
            );              
        }

        return this.getSvr();
    }
    
    
}
