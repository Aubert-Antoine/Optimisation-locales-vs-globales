

/**
 * EvalStat est la classe qui s'occupe des evaluations statistique des ex du projet.
 */
public class EvalStat {

    /**
     * evalMax retourne la distance relative entre deux solution, une optimal -> dynamique et une autre -> gloutonne
     * @param pValDyn pram int, valeur dynamique    
     * @param pValGlou pram int, valeur gloutonne 
     * @return  la distance pour une rechecher de maximisation
     */
    public static int evalMax(int pValDyn, int pValGlou) {
        return (pValDyn-pValGlou)/pValDyn;
    }

    /**
     * evalMax retourne la distance relative entre deux solution, une optimal -> dynamique et une autre -> gloutonne
     * @param pValDyn pram int, valeur dynamique    
     * @param pValGlou pram int, valeur gloutonne 
     * @return  la distance pour une rechecher de maximisation
     */
    public static int evalMin(int pValDyn, int pValGlou) {
        return (pValDyn-pValGlou)/pValDyn;
    }






    public static void mainEvalStat(){
        System.out.println("Hello, World! from EvalStat Class");
    }
}
