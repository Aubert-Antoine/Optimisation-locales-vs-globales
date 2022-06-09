

/**
 * EvalStat est la classe qui s'occupe des evaluations statistique des ex du projet.
 */
public class EvalStat {

    /**
     * evalMax retourne la distance relative entre deux solution, une optimal -> dynamique et une autre -> gloutonne
     * @param pValDyn pram double pour plus de precision, valeur dynamique    
     * @param pValGlou pram double pour plus de precision, valeur gloutonne 
     * @return  la distance pour une rechecher de maximisation
     */
    public static double evalMax(double pValDyn, double pValGlou) {
        return (pValDyn-pValGlou)/pValDyn;
    }

    /**
     * evalMax retourne la distance relative entre deux solution, une optimal -> dynamique et une autre -> gloutonne
     * @param pValDyn pram double pour plus de precision, valeur dynamique    
     * @param pValGlou pram double pour plus de precision, valeur gloutonne 
     * @return  la distance pour une rechecher de maximisation
     */
    public static double evalMin(double pValDyn, double pValGlou) {
        return ((pValGlou-pValDyn)/pValDyn);
    }






    public static void mainEvalStat(){
        System.out.println("Hello, World! from EvalStat Class");
    }
}
