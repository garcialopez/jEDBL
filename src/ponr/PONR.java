/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ponr;

/**
 *
 * @author admin
 */
public class PONR {

   
    
    private String nombre;
    private double mejorConocido;
    private double[][] rangos;
    private double[] restriccionesI;
    private double[] restriccionesD;
    private int numVariables;
    private String[] ordenVariables;
    private double[][] cConstantes;
    private double [] compRestriccion;
    private boolean[] existePertenencia;
    private double svr;
    
    
    public double[][] evaluarFO(double[][] ind){
        evaluarRestriccionesD(ind[0]);
        return ind;
    }
    
    
    /**
     * Evalua todas las restrucciones
     * @param ind
     * @return suma de violación de restricciones
     */
    protected double evaluarRestriccionesD(double[] ind){
        return ind[0];
    }
    
    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the mejorConocido
     */
    public double getMejorConocido() {
        return mejorConocido;
    }

    /**
     * @param mejorConocido the mejorConocido to set
     */
    public void setMejorConocido(double mejorConocido) {
        this.mejorConocido = mejorConocido;
    }

    /**
     * @return the rangos
     */
    public double[][] getRangos() {
        return rangos;
    }

    /**
     * @param rangos the rangos to set
     */
    public void setRangos(double[][] rangos) {
        this.rangos = rangos;
    }

    /**
     * @return the restriccionesI
     */
    public double[] getRestriccionesI() {
        return restriccionesI;
    }

    /**
     * @param restriccionesI the restriccionesI to set
     */
    public void setRestriccionesI(double[] restriccionesI) {
        this.restriccionesI = restriccionesI;
    }

    /**
     * @return the restriccionesD
     */
    public double[] getResDesigualdad() {
        return restriccionesD;
    }

    /**
     * @param restriccionesD the restriccionesD to set
     */
    public void setResDesigualdad(double[] restriccionesD) {
        this.restriccionesD = restriccionesD;
    }

    /**
     * @return the numVariables
     */
    public int getNumVariables() {
        return numVariables;
    }

    /**
     * @param numVariables the numVariables to set
     */
    public void setNumVariables(int numVariables) {
        this.numVariables = numVariables;
    }

    /**
     * @return the cConstantes
     */
    public double[][] getcConstantes() {
        return cConstantes;
    }

    /**
     * @param cConstantes the cConstantes to set
     */
    public void setcConstantes(double[][] cConstantes) {
        this.cConstantes = cConstantes;
    }
    
     /**
     * @return the svr
     */
    public double getSvr() {
        return svr;
    }

    /**
     * @param svr the svr to set
     */
    public void setSvr(double svr) {
        this.svr = svr;
    }

    /**
     * @return the ordenVariables
     */
    public String[] getOrdenVariables() {
        return ordenVariables;
    }

    /**
     * @param ordenVariables the ordenVariables to set
     */
    public void setOrdenVariables(String[] ordenVariables) {
        this.ordenVariables = ordenVariables;
    }

    /**
     * @return the compRestriccion
     */
    public double[] getCompRestriccion() {
        return compRestriccion;
    }

    /**
     * @param compRestriccion the compRestriccion to set
     */
    public void setComparacionRestriccion(double[] compRestriccion) {
        this.compRestriccion = compRestriccion;
    }

    /**
     * @return the existePertenencia
     */
    public boolean[] isVariableDiscreta() {
        return existePertenencia;
    }

    /**
     * Método que asigna true si la variable es discreta, caso contrario, es false.
     * 
     * @param existePertenencia the existePertenencia to set
     */
    public void setVariableDiscreta(boolean[] existePertenencia) {
        this.existePertenencia = existePertenencia;
    }

 
    
    
    
}
