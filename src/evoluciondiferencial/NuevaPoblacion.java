
package evoluciondiferencial;

import ponr.PONR;

/**
 *
 * @author JOSEA
 */
public class NuevaPoblacion {
    private static NumberRandom NUMBER_RANDOM;
    
    public static double[][] poblacionInicial(int poblacion, PONR ponr){
        NUMBER_RANDOM = new NumberRandom();
        
        double[][] individuos = new double[poblacion][ponr.getNumVariables() + 2];
        
        for (int i = 0; i < poblacion; i++) {
            
            for (int j = 0; j < ponr.getNumVariables(); j++) {
                
                if (ponr.isExistePertenencia()[j]) {
                    
                    int indice = NUMBER_RANDOM.getNetxInt(ponr.getRangos()[j].length);
                    
//                    System.out.print("true: ");
                    individuos[i][j] =(int) ponr.getRangos()[j][indice];
//                    System.out.println(individuos[i][j]);
                }else{
//                    System.out.println("false");
                    individuos[i][j] = NUMBER_RANDOM
                        .getRandomRankUnif(ponr.getRangos()[j][0]
                                , ponr.getRangos()[j][1]);
                }
                
                
                
            }
            individuos[i][ponr.getNumVariables()] = 0;
            individuos[i][ponr.getNumVariables() + 1] = 0;
            
            
        }       
        return individuos;
    }
    
    
}
