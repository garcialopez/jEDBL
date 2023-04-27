package evoluciondiferencial;

import java.util.ArrayList;
import java.util.List;
import ponr.PONR;

public class EvolucionDiferencial {

    //Números de individuos en la población [10 - 150]
    private int poblacion = 50;

    //Cruza - Crossover [0 - 1]
    private double CR = 0.65;

    //Mutación [0 - 1]
    private double F = 0.65;

    //Número de generaciones [100 - 1000]
    private int gmax = 300;

    //Población
    private double[][] individuos;

    //Mejores valores encontrados
    private double[][] mejoresValores = new double[0][0];

    //Procesos de evolución diferencial
    private ProcesoED procesoEd;

    private NumberRandom rnd;
    
    private List<Double> convergencia;

    //parametros para la busqueda local
    private double PRO_BUS = 0.1;
    private double MAX_ITER = 10;

    public EvolucionDiferencial() {
        //objeto para numeros aletorios
        this.rnd = new NumberRandom();

        //Intancia de la clase ProcesoED
        this.procesoEd = new ProcesoED();
        
        this.convergencia = new ArrayList();
    }

    //RESORTE
//    double vConocido = 0.012681;
//    int var = 3;  //variables del problema x1, x2 y x3
//    int numRestricD = 4;  //restricciones de desigualdad
//    double[][] rango = {{0.05, 2}, {0.25, 1.3}, {2, 15}};  // RANGO DE VARIABLES
    public void iniciar(PONR ponr, int iteracion) {

        //evaluamos que el numero de iteraciones sea correcto        
        iteracion = ((iteracion < 1) || (iteracion > 30)) ? 1/*SI*/ : iteracion/*NO*/;
        
        //en caso de ser mañor a 1 ejecución independiente
            int medianaIter = 1;
            if (iteracion > 1) {
                medianaIter = (int) iteracion/2;
            }

        double[][] mutantes = new double[this.getPoblacion()][ponr.getNumVariables() + 2];

        //Se establece el tamaño de la matriz individuos donde se guardara
        //los valores de las variables, la función objetivo y 
        //la suma de violación de restricciones
        this.individuos = new double[this.getPoblacion()][ponr.getNumVariables() + 2];

        //cuenta las evaluaciones realizadas
        int contadorEvaluaciones;

        //inicializamos la matriz para mejores valores
        if (iteracion == 1) {
            this.mejoresValores = this.individuos;
        } else {
            this.mejoresValores = new double[iteracion][ponr.getNumVariables() + 2];
        }

        //Inicia las iteraciones del algoritmo [1 - 30]
        for (int iter = 0; iter < iteracion; iter++) {

            //variables para calcular el performance
            contadorEvaluaciones = 0;

            //Crear la problación inicial de acuerdo a los rangos de variables
            //Se establece el tamaño de la matriz mejores valores
            this.individuos = NuevaPoblacion.poblacionInicial(this.getPoblacion(),
                    ponr
            );

//            for (int i = 0; i < this.individuos.length; i++) {
//                System.out.println(java.util.Arrays.toString(individuos[i]));
//            }
            //evaluar la FO y restricciones con la poblacion inicial
            this.individuos = ponr.evaluarFO(individuos);

            //contar evaluaciones de los individuos en la FO
            contadorEvaluaciones += this.getPoblacion();

            System.out.println("Población inicial evaluada.");

            //inicia las generaciones del algoritmo
            for (int g = 0; g < this.getGmax(); g++) {

//                System.out.println("Inicia la generación " + (g + 1));
                //inicia el proceso de evolucion diferencial
                for (int i = 0; i < this.getPoblacion(); i++) {

                    //seleccion de 3 individuos aleatorios
                    int[] ind = this.procesoEd.posicionesAleatorias(this.getPoblacion(), i/*numero a descartar*/);

                    //instaciamos el vector hijo
                    double[] hijo = new double[ponr.getNumVariables() + 2];

                    //generamos un valor aleatorio uniforme
                    //creamos un aleatorio uniforme
                    double aleatorio;
                    int jrand = this.rnd.getRandomRankUnif(0, ponr.getNumVariables());
//
//                    //iteramos cada individuo segun el numero de variables
                    for (int j = 0; j < ponr.getNumVariables(); j++) {
                        //generamos un número aleatorio
                        aleatorio = this.rnd.getRandomUnif();

                        if (aleatorio <= getCR() || j == jrand) {//cruza controla la recombinación                                                    

                            //se mutan los tres individuos
                            //se pregunta si existe pertenencia en los rangos de variables
                            if (ponr.isVariableDiscreta()[j]) {
                                hijo[j] = (int) this.procesoEd.mutacion(this.individuos[ind[0]][j], this.individuos[ind[1]][j], this.individuos[ind[2]][j], this.getF());
                            } else {
                                hijo[j] = this.procesoEd.mutacion(this.individuos[ind[0]][j], this.individuos[ind[1]][j], this.individuos[ind[2]][j], this.getF());
                            }

                            //verificamos los rangos de cada variable de acuerdo a la mutación
                            if ((hijo[j] < ponr.getRangos()[j][0])
                                    || (hijo[j] > ponr.getRangos()[j][1])) {
                                
                                if (ponr.isVariableDiscreta()[j]) 
                                    hijo[j] = (int)this.individuos[i][j];
                                else hijo[j] = this.individuos[i][j];

                            }//termina IF
                        } else { //no se mutan
                            hijo[j] = this.individuos[i][j];
                        }

                    }//cierra for

                    mutantes[i] = hijo;

                    // Búsqueda local
                    if (this.rnd.getRandomUnif() <= this.getPRO_BUS()) {
//                        System.out.println("entre");
                        double[][] hijoAux = new double[1][];
                        hijoAux[0] = hijo;
                        //evaluar hijo
                        hijo = ponr.evaluarFO(hijoAux)[0];

                        int iteracionBL = 0;
                        double[] puntoInicial = hijo;
                        double[] optimoLocal = puntoInicial;
                        double fitnessInicial = hijo[ponr.getNumVariables()];

                        while (iteracionBL < this.getMAX_ITER()) {
                            double[] puntoCandidato = optimoLocal;

                            for (int j = 0; j < ponr.getNumVariables(); j++) {
                                puntoCandidato[j] += this.rnd.getRandomGaussian();

//                                puntoCandidato[j] = puntoCandidato[j] + (Math.random() - 1) * 0.1;
//                                System.out.println(this.rnd.getRandomGaussian());
                                if (ponr.isVariableDiscreta()[j]) {
                                    puntoCandidato[j] = (int) Math.max(Math.min(puntoCandidato[j],
                                             ponr.getRangos()[j][1]),
                                             ponr.getRangos()[j][0]
                                    );
                                }else puntoCandidato[j] = Math.max(Math.min(puntoCandidato[j],
                                             ponr.getRangos()[j][1]),
                                             ponr.getRangos()[j][0]
                                    );

                            }
                            hijoAux[0] = puntoCandidato;
                            double fitnessCandidato = ponr.evaluarFO(hijoAux)[0][ponr.getNumVariables()];

                            if (fitnessCandidato < fitnessInicial) {
                                optimoLocal = hijoAux[0];
                                fitnessInicial = fitnessCandidato;
//                                System.out.println("cdscs");
                            }

                            iteracionBL++;
                        } // cierra while

                        hijoAux[0] = optimoLocal;

                        double fitnessOL = ponr.evaluarFO(hijoAux)[0][ponr.getNumVariables()];

                        if (fitnessOL < hijo[ponr.getNumVariables()]) {
                            hijo = optimoLocal;
                            System.out.println("VBSDFVBSDFVBKSDFVSDFVSDFVBDFBDFBDF");
                        }

                    }// cierra if busqueda local

                    //para evaluar hijo, lo convertimos en array
                    double[][] hijoAux = new double[1][];
                    hijoAux[0] = hijo;

                    //evaluar hijo
                    hijoAux = ponr.evaluarFO(hijoAux);
                    hijo = hijoAux[0];

                    //incrementamos contador de evaluaciones
                    contadorEvaluaciones++;

                    //comparamos la calidad del hijo con el padre
                    if ((hijo[ponr.getNumVariables() + 1] == 0) && (this.individuos[i][ponr.getNumVariables() + 1] == 0)) {

                        if (hijo[ponr.getNumVariables()] < this.individuos[i][ponr.getNumVariables()]) {
                            this.individuos[i] = hijo; //copiamos todo el hijo por ser mejor
                        }

                    }//cierra if

                    if ((hijo[ponr.getNumVariables() + 1] > 0) && (this.individuos[i][ponr.getNumVariables() + 1] > 0)) {
                        if (hijo[ponr.getNumVariables()] < this.individuos[i][ponr.getNumVariables()]) {
                            this.individuos[i] = hijo; //copiamos todo el hijo por ser mejor
                        }
                    }//cierra if

                    if ((hijo[ponr.getNumVariables() + 1] == 0) && (this.individuos[i][ponr.getNumVariables() + 1] > 0)) {
                        this.individuos[i] = hijo; //copiamos todo el hijo por ser mejor
                    }//cierra if

                }//cierra proceso evolución diferencial

                //ordenamos los individuos
                this.individuos = this.procesoEd.ordenarP(this.individuos, ponr.getNumVariables());
                
                
                
                if ((iter+1) == medianaIter) {
                        //System.out.println("ESTE ES: " + this.tsmbfoa.getCount());
                        this.convergencia.add(this.individuos[0][ponr.getNumVariables()]);
                    }
                
                

            }//cierra for de generaciones

//            for (int j = 0; j < this.individuos.length; j++) {
//                System.out.println(java.util.Arrays.toString(this.individuos[j]));
//            }
            if (iteracion > 1) {
                this.mejoresValores[iter] = this.individuos[0];
            } else {
                this.mejoresValores = this.individuos;
            }

        }//termina for del número de iteraciones

        //ordenamos las mejores soluciones
        this.mejoresValores = this.procesoEd.ordenarP(this.mejoresValores, ponr.getNumVariables());

    }

    /**
     * @return the poblacion
     */
    public int getPoblacion() {
        return poblacion;
    }

    /**
     * @param poblacion the poblacion to set
     */
    public void setPoblacion(int poblacion) {
        this.poblacion = poblacion;
    }

    /**
     * @return the CR
     */
    public double getCR() {
        return CR;
    }

    /**
     * @param CR the CR to set
     */
    public void setCR(double CR) {
        this.CR = CR;
    }

    /**
     * @return the F
     */
    public double getF() {
        return F;
    }

    /**
     * @param F the F to set
     */
    public void setF(double F) {
        this.F = F;
    }

    /**
     * @return the gmax
     */
    public int getGmax() {
        return gmax;
    }

    /**
     * @param gmax the gmax to set
     */
    public void setGmax(int gmax) {
        this.gmax = gmax;
    }

    /**
     * @return the mejoresValores
     */
    public double[][] getMejoresValores() {
        return mejoresValores;
    }

    /**
     * @return the PRO_BUS
     */
    public double getPRO_BUS() {
        return PRO_BUS;
    }

    /**
     * @param PRO_BUS the PRO_BUS to set
     */
    public void setPRO_BUS(double PRO_BUS) {
        this.PRO_BUS = PRO_BUS;
    }

    /**
     * @return the MAX_ITER
     */
    public double getMAX_ITER() {
        return MAX_ITER;
    }

    /**
     * @param MAX_ITER the MAX_ITER to set
     */
    public void setMAX_ITER(double MAX_ITER) {
        this.MAX_ITER = MAX_ITER;
    }

    /**
     * @return the convergencia
     */
    public double[][] getConvergencia() {
        
        int f = this.convergencia.size();
        
        double[][] conver = new double[2][f];
        
        for (int i = 0; i < f; i++) {
            conver[0][i] = i+1;
            conver[1][i] =  this.convergencia.get(i);                        
        }
        
        return conver;
    }

}
