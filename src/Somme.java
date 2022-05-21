import java.util.Arrays;

// https://projecteuler.net/problem=67
public class Somme {

    private static final boolean debug = true;

    /**
     * MakeTrig() retourne un tableau, qui contient les valeurs du triangle
     * @return le tableau de ref du projet Euler
     */
    public static int[] MakeTrig() {
        int[] TabTrig = new int[]{3,7,4,2,4,6,8,5,9,3};
        if(debug) System.out.println("TAbTrig: "+  Arrays.toString(TabTrig));
        return TabTrig; 
    }


    /**
     * 
     * Repr ́esentation du triangle dans un tableau T[0:n]
     * 
     *  */


    /**
     * g(pTAbRef) retourne le descendant gauche du triangle passé en param
     * @param pTAbRef est le tableau de reference d'entrée
     * @param indice est le numero dont l'on cherche l'incide descendant gauche
     * @return l'indice descendant gauche
     */
    public static int g(int[] pTAbRef, int pIndice){
        int l=0;      // ligne/etage dans le triangle
        int p=0;      // position dans la ligne
        int nbElementDL=0; // nb element derniere ligne
        int nbElementAvantDL=0;         // le nombre d'element sans la derniere linge

        if(pIndice > pTAbRef.length-1 || pIndice < 0) {
            System.out.println("ERROR \n --> l'indice est superieur a la taille du tableau ou negatif.");

            if(debug) System.out.println("indice : "+pIndice+" et taille Tab : "+ pTAbRef.length);

            return -1;
        }
        else if(pIndice == 0) return 1;
        else{
            //On cherche le niveau max du tableau
            for (int niveau = 1; niveau < pTAbRef.length; niveau++) {
                if(pTAbRef.length == niveau*(niveau+1)/2){
                    nbElementDL = pTAbRef.length-(((niveau-1)*niveau)/2);
                    break;              //sort du for??? 
                }
            }
            nbElementAvantDL=pTAbRef.length-nbElementDL;

            if(pIndice > nbElementAvantDL-1){
                System.out.println("ERROR \n --> l'indice saisi est sur la derniére ligne => pas de descendant gauche");
                return -1;
            }

            for (int niveau = 1; niveau < pTAbRef.length; niveau++) {
                if(niveau*(niveau+1)/2 > pIndice){
                    l=niveau-1;
                    p=pIndice-(niveau*(niveau-1)/2);
                    break;
                }
            }
            
            if(debug){
                System.out.println("L'indice "+pIndice+" est en :");
                System.out.println("-l : "+l +"\n-p : "+p);
                System.out.println("Donc l'indice de son descendant gauche est :");
                if(p==2) System.out.println("-l : "+(l+1) +"\n-p : "+(p-1));
                else{System.out.println("-l : "+(l+1) +"\n-p : "+p);}
                System.out.println("avec comme indice");
                System.out.println(((l+2)*(l+1)/2)+p);
            }

            return ((l+2)*(l+1)/2)+p;
            }
        }//g()

        /**
         * d() renvoie l'indice bas droite de pIndice. Utilise g()
         * @param pTAbRef tab d'entré
         * @param pIndice indice du quel on cherche son descendant droit
         * @return l'indice bas droite de pIndice
         */
        public static int d(int[] pTAbRef, int pIndice) {
            int out = g(pTAbRef, pIndice);
            if(out == -1){
                System.out.println("ERROR");
                return -1;
            }
            return out+1;
        }//d()

        

    /**
     * 
     * Calcul de la valeur des chemins de somme maximum
     * 
     *  */

    
    // METHODE DYNAMIQUE : 
    //

    /**
     * prend en entr ́ee le triangle T [0 : n] et retourne un tableau M [0 : n] 
     * de terme general M [i] = m(i) = la valeur d’un chemin de somme maximum
     * 
     * @param pTAbRef
     * @return un tableau avec la somme cumulée du chemin au cout max
     */
    public static int[] calculerM(int[] pTabRef) {
        int nbNiveaux = niveau(pTabRef, pTabRef.length-1)+1;
        int[] TabSM = new int[nbNiveaux];

        if(debug) System.out.println("Le tableau "+pTabRef+" de longeur "+pTabRef.length+" a "+nbNiveaux+" niveaux");

        TabSM[0] = pTabRef[0];

        for (int niveau = 1; niveau < TabSM.length; niveau++) {
            TabSM[niveau]=TabSM[niveau-1]+min(
                pTabRef[g(pTabRef, pIndice)],
                pTabRef[d(pIndice)]
            );
        }
    }

    /**
     * niveau renvoie le niveau ou se trouve le param    !!! commence à 0 
     * @param pTabRef 
     * @param pIndice 
     * @return renvoie le niveau ou se trouve le param
     */
    public static int niveau(int[] pTabRef, int pIndice) {
        for (int niveau = 1; niveau < pTabRef.length; niveau++) {
            if(niveau*(niveau+1)/2 > pIndice){
                return niveau-1;
            }
        }return -1;
    }//niveau

    
    public static void mainSomme(){
        System.out.println("Hello, World! from Somme Class \n");
        d(MakeTrig(), 5);
    }
    
}
