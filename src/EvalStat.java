


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


    //la mediane, la moyenne, et l’ecart-type des distances relatives.

    /**
     * mediane return la mediane du tableau passé en param
     * @param pTabRef
     * @return
     */
    public static double mediane(double[] pTabRef) {
         return pTabRef[(pTabRef.length)/2];
    }

    /**
     * retourne la moyenne du tableau en double pour plus de precision
     * @param pTabRef
     * @return
     */
    public static double moyenne(double[] pTabRef) {
        double moyenne = 0;
        for (int i = 0; i < pTabRef.length; i++) {
            moyenne += pTabRef[i];
        }  
        return moyenne/pTabRef.length;
    }

    

    public static void mainEvalStat(){
        System.out.println("Hello, World! from EvalStat Class");
    }
}
