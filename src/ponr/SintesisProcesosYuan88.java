/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ponr;

/**
 *
 * @author admin
 */
public class SintesisProcesosYuan88 extends PONR {
    
     public SintesisProcesosYuan88() {
                  
        this.setNombre("Process synthesis MINLP de Yuan et al (1988)");
        this.setMejorConocido(4.579582);
        this.setNumVariables(7);
        
        this.setOrdenVariables(new String[]{
                "x1","x2","x3","y1","y2","y3","y4"
            }
        );
        
        this.setVariableDiscreta(new boolean[]{
                false,false,false,true,true,true,true
//                false,false,false,false,false,false,false
                }
        );
        
        this.setRangos(new double[][]{
                 {0, 1.2}
                ,{0, 1.8}        
                ,{0, 2.5} 
                ,{0, 1} 
                ,{0, 1} 
                ,{0, 1} 
                ,{0, 1} 
        }
        );
        
        this.setResDesigualdad(new double[9]);
        
        this.setComparacionRestriccion(new double[]{
                5, 5.5, 1.2, 1.8, 2.5, 1.2, 1.64, 4.25, 4.64
            }
        );
        
        
    }
    
        
    @Override
    public double[][] evaluarFO(double[][] x){
        
        for (double[] ind1 : x) {            
            //Evaluando la función objetivo
            ind1[this.getNumVariables()] = 
                      Math.pow(ind1[3] - 1, 2) //
                    + Math.pow(ind1[4] - 2, 2) //
                    + Math.pow(ind1[5] - 1, 2) //
                    - Math.log(ind1[6] + 1) //
                    + Math.pow(ind1[0] - 1, 2) 
                    + Math.pow(ind1[1] - 2, 2) 
                    + Math.pow(ind1[2] - 3, 2);
            
            ind1[this.getNumVariables() + 1] = this.evaluarRestriccionesD(ind1);
        }
        return x;
        
    }
    
    @Override
    protected double evaluarRestriccionesD(double[] x){
        this.setSvr(0);
       
        
        this.getResDesigualdad()[0] = x[3] + x[4] + x[5] + x[0] + x[1] + x[2];
        this.getResDesigualdad()[1] = Math.pow(x[5],2) + Math.pow(x[0],2) + Math.pow(x[1],2) + Math.pow(x[2],2);
        this.getResDesigualdad()[2] = x[3] + x[0];
        this.getResDesigualdad()[3] = x[4] + x[1];
        this.getResDesigualdad()[4] = x[5] + x[2];
        this.getResDesigualdad()[5] = x[6] + x[0];
        this.getResDesigualdad()[6] = Math.pow(x[4],2) + Math.pow(x[1],2);
        this.getResDesigualdad()[7] = Math.pow(x[5],2) + Math.pow(x[2],2);
        this.getResDesigualdad()[8] = Math.pow(x[4],2) + Math.pow(x[2],2);

        //suma de violacion de restricciones
        for (int i = 0; i < this.getResDesigualdad().length; i++) {   
                      
            this.setSvr( this.getSvr() +  Math.max(0
                    , (this.getResDesigualdad()[i] - this.getCompRestriccion()[i])
                ) 
            );              
        }

        return this.getSvr();
    }
    
}
