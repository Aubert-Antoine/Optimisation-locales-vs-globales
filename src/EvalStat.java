import java.util.Arrays;
import java.util.Random;


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
        double[] qsTab = Arrays.copyOf(pTabRef, pTabRef.length);
        qs(qsTab, 0, qsTab.length); // tri du tableau pour ensuite effectuer la médiane
        return qsTab[(qsTab.length)/2];
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

    /**
     * ecartType retourne l'ecart type du tableau passé en param
     * @param pTabRef
     * @return
     */
    public static double ecartType(double[] pTabRef){
        double varInter = 0;

        for (int i = 0; i < pTabRef.length; i++) {
            varInter += Math.pow((pTabRef[i] - moyenne(pTabRef) ), 2);
        }
        return Math.sqrt(varInter/pTabRef.length);
    }


    static void qs(double[] T, int i, int j){ 
		if (j-i <= 1) return ; // le sous-tableau T[i:j] est croissant
		// ici : j-i >= 2
		int k = segmenter(T,i,j); // T[i:k] <= T[k] < T[k+1:j]    <<< (1)
		qs(T,i,k);   // (1) et T[i:k] croissant 
		qs(T,k+1,j); // (1) et T[i:k] croissant et T[k+1:j] croissant, donc T[i:j] croissant
	}//qs()

	static Random rand = new Random();

	static int segmenter(double[] T, int i, int j){
	/* Calcule une permutation des valeurs de T[i:j] vérifiant T[i:k] <= T[k] < T[k+1:j], et retourne k
	Fonction construite sur la propriété I(k,j') : T[i:k] <= T[k] < T[k+1:j']
	Arrêt j'=j
	Initialisation : k = i, j'=k+1
	Progression : I(k,j') et j'<j et t_{j'}>t_{k} ==> I(k,j'+1)
				  I(k,j') et j'<j et t_{j'}<=t_{k} et T[k]=t_{j'} et T[k+1]=t_{k} et T[j']=t_{k+1} ==> I(k+1,j'+1)
	*/
		int r = i + rand.nextInt(j-i);
		permuter(T,i,r);
		int k = i, jp = k+1; // I(k,j')
		while (jp < j) // I(k,j') et jp < j 
			if (T[k] < T[jp]) // I(k,j'+1)
				jp++; // I(k,j')
			else {
				permuter(T,jp,k+1);
				permuter(T,k,k+1); // I(k+1,j'+1)
				k++; jp++; // I(k,jp)
			}
		return k;				
	}//segmenter()

	static void permuter(double[] T, int i, int j){
		double ti = T[i];
		T[i] = T[j];
		T[j] = ti;
	}//permuter()

    public static void mainEvalStat(){
        System.out.println("Hello, World! from EvalStat Class");
    }
}