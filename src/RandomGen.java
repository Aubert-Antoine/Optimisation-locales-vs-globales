import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;


/**
 * RandomGen est la classe qui génère aléatoirement les tableaux requis pour les runs
 */
public class RandomGen {


    public static boolean debug = false;
    public static boolean info = false;

    public static void mainRandomGen(){
        System.out.println("Hello, World! from RandomGen Class");
    }


    /**
     * randoomInt retourne un entier dans la plage indiquée (borne sup comprise)
     * @param pMin min de la borne
     * @param pMax max de la borne (inclue)
     * @return un int au hasard dans l'intervalle [pMin, pMax]
     */
    public static int randomInt(int pMin, int pMax) {
        return ThreadLocalRandom.current().nextInt(pMin, pMax + 1);
    }


    /**
     * randomTabInt retourne un tableau d'entiers dans la plage indiquée (borne sup comprise)
     * @param pTabLen taille du tableau
     * @param pMaxVal max de la borne (inclue)
     * @return un tableau d'int au hasard avec des valeurs dans l'intervalle [0, pMaxVal]
     */
    public static int[] randomTabInt(int pTabLen, int pMaxVal) {
        int[] randomTab = new int[pTabLen];

        for (int i = 0; i < randomTab.length; i++) {
            randomTab[i] = randomInt(1, pMaxVal);
            if(debug) System.out.println(Arrays.toString(randomTab));
        }
        if(info) System.out.println("RANDOM > randomTabInt :\n"+Arrays.toString(randomTab));

        return randomTab;
    }


    /**
     * randomTab2D retourne un tableau à 2D d'entiers dans la plage indiquée (borne sup comprise)
     * @param pTabLenL nombre de lignes du tableau
     * @param pTabLenC nombre de colonnes du tableau
     * @param pMaxVal max de la borne (inclue)
     * @param pDebutCTab début initialisation des colonnes du tableau
     * @return un tableau 2D d'int au hasard avec des valeurs dans l'intervalle [0, pMaxVal]
     */
    public static int[][] randomTab2D(int pTabLenL, int pTabLenC, int pDebutCTab, int pMaxVal) {
        int[][] randomTab2D = new int[pTabLenL][pTabLenC];

        for(int i = 0; i < randomTab2D.length; i++) {
            for(int j = pDebutCTab; j < randomTab2D[i].length; j++) {
                randomTab2D[i][j] = randomInt(0, pMaxVal);
            if(debug) System.out.println(Arrays.toString(randomTab2D));
            }
        }
        if(info) System.out.println("RANDOM > randomTab2D :\n"+Arrays.toString(randomTab2D));

        return randomTab2D;
    }


}//class RandomGen
