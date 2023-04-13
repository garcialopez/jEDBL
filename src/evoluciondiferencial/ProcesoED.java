package evoluciondiferencial;


/**
 *
 * @author admin
 */
public class ProcesoED {

    NumberRandom rnd;

    public ProcesoED() {
        this.rnd = new NumberRandom();
    }
       
    /**
     * Metodo que deuelve 3 posiciones aleattorias diferentes entre 0 y
     * población
     *
     * @param poblacion tamaño maximo de la población
     * @param diferente posición para excluir en la busqueda de las otras 3
     * posiciones.
     * @return un ventor de 3 numeros enteros diferentes
     */
    public int[] posicionesAleatorias(int poblacion, int diferente) {
        //restamos 1 para el control de posiciones
        poblacion = poblacion-1;
        
        
        int[] pos = new int[3];

        pos[0] = this.rnd.getRandomRankUnif(0, poblacion);
        pos[1] = this.rnd.getRandomRankUnif(0, poblacion);
        pos[2] = this.rnd.getRandomRankUnif(0, poblacion);
       
        //evaluamos para que sean diferentes
        while (pos[0] == diferente) 
            pos[0] = this.rnd.getRandomRankUnif(0, poblacion);
        
        while ((pos[1] == diferente) || (pos[1] == pos[0])) 
            pos[1] = this.rnd.getRandomRankUnif(0, poblacion);
        
        while ((pos[2] == diferente) || (pos[2] == pos[1]) || (pos[2] == pos[0])) 
            pos[2] = this.rnd.getRandomRankUnif(0, poblacion);
        
        
        return pos;
    }
    
    public double [][] ordenarP(double[][] indv, int numVariables) {
         

        double[] aux;
        
        for (int i = 0; i < indv.length; i++) {
            
            for (int j = 0; j < (indv.length-1); j++) {
                
                if ((indv[j][numVariables + 1] == 0) && (indv[j + 1][ numVariables + 1] == 0)) {
                    if (indv[j][numVariables] > indv[j + 1][numVariables]) {
                        
                        aux = indv[j];
                        indv[j] = indv[j + 1];
                        indv[j + 1] = aux;
                        
                    }
                }
                if ((indv[j][numVariables + 1] > 0) && (indv[j + 1][numVariables + 1] > 0)) {
                    if (indv[j][numVariables+1] > indv[j + 1][numVariables+1]) {
                        aux = indv[j];
                        indv[j] = indv[j + 1];
                        indv[j + 1] = aux;
                    }
                }
                if ((indv[j][numVariables + 1]) > 0 && (indv[j + 1][numVariables + 1] == 0)) {
                    aux = indv[j];
                    indv[j] = indv[j + 1];
                    indv[j + 1] = aux;
                }

            }
        }
        
        return indv;
    }
    
    /**
     * 
     * @param r1
     * @param r2
     * @param r3
     * @param F
     * @return Vatago
     */
    public double mutacion(double r1, double r2, double r3, double F){
        return r1 + F * (r2 - r3);
    }
   
    
    
} //cierra clase ProcesoED
