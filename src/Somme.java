import java.util.Arrays;

// https://projecteuler.net/problem=67
public class Somme {

    private static final boolean debug = false;
    private static final boolean info = false;

    /**
     * MakeTrig() retourne un tableau, qui contient les valeurs du triangle
     * @return le tableau de ref du projet Euler
     */
    public static int[] MakeTrig() {
        int[] TabTrig = new int[]{3,7,4,2,4,6,8,5,9,3};
        //if(debug) System.out.println("TAbTrig: "+  Arrays.toString(TabTrig));
        return TabTrig; 
    }


    /**
     * 
     * Repr ́esentation du triangle dans un tableau T[0:n]
     * 
     * */


    /**
     * g(pTabRef) retourne le descendant gauche du triangle passé en param
     * @param pTabRef est le tableau de reference d'entrée
     * @param indice est le numero dont l'on cherche l'incide descendant gauche
     * @return l'indice descendant gauche
     */
    public static int g(int[] pTabRef, int pIndice){
        int l=0;      // ligne/etage dans le triangle
        int p=0;      // position dans la ligne
        int nbElementDL=0; // nb element derniere ligne
        int nbElementAvantDL=0;         // le nombre d'element sans la derniere linge

        if(pIndice > pTabRef.length-1 || pIndice < 0) {
            System.out.println("ERROR \n --> l'indice est superieur a la taille du tableau ou negatif.");

            //if(info) System.out.println("indice : "+pIndice+" et taille Tab : "+ pTabRef.length);

            return -1;
        }
        else if(pIndice == 0) return 1;
        else{
            //On cherche le niveau max du tableau
            for (int niveau = 1; niveau < pTabRef.length; niveau++) {
                if(pTabRef.length == niveau*(niveau+1)/2){
                    nbElementDL = pTabRef.length-(((niveau-1)*niveau)/2);
                    break;              //sort du for??? 
                }
            }
            nbElementAvantDL=pTabRef.length-nbElementDL;

            if(pIndice > nbElementAvantDL-1){
                System.out.println("ERROR \n --> l'indice saisi est sur la derniére ligne => pas de descendant gauche");
                return -1;
            }

            for (int niveau = 1; niveau < pTabRef.length; niveau++) {
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
         * @param pTabRef tab d'entré
         * @param pIndice indice du quel on cherche son descendant droit
         * @return l'indice bas droite de pIndice
         */
        public static int d(int[] pTabRef, int pIndice) {
            int out = g(pTabRef, pIndice);
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
     * prend en entree le triangle T [0 : n] et retourne un tableau M [0 : n] 
     * de terme general M [i] = m(i) = la valeur d’un chemin de somme maximum
     * 
     * @param pTabRef
     * @return un tableau avec la somme cumulée du chemin au cout max
     */
    public static int[] calculerM(int[] pTabRef) {
        int nbNiveaux = niveau(pTabRef, pTabRef.length-1);
        int[] TabSM = new int[nbNiveaux];
        int[] TemTab = new int[pTabRef.length];

        if(debug) System.out.println("Le tableau "+pTabRef+" de longeur "+pTabRef.length+" a "+nbNiveaux+" niveaux");

        TabSM[0] = pTabRef[0];

        for (int niveau = 1; niveau < TabSM.length; niveau++) {
            TabSM[niveau]=TabSM[niveau-1]+Math.max(
                pTabRef[g(pTabRef, niveau)],
                pTabRef[d(pTabRef, niveau)]
            );
        }
        return [0];
    }

    public static int[] calRecurMem(int[] pTabRef, int pIndice) {
        int nbNiveaux = niveau(pTabRef, pTabRef.length-1);
        int[] TabSM = new int[nbNiveaux];
        int[] TabSC = new int[pTabRef.length];

        
        TabSM[0] = pTabRef[0];

        int i=1;
        TabSM[i] = Math.max(somme(calRecurMem(pTabRef, pTabRef[g(pTabRef, i)])),
                            somme(calRecurMem(pTabRef, pTabRef[d(pTabRef, i)])) );

        System.out.println(Arrays.toString(TabSM));
        return TabSM;
    }


    // METHODE Gloutonne : 
    //
    
    /**
     * calculeTabSMGlouton est la fonction de calcule du chemin de somme max selon le pTabRef
     * @param pTabRef tableau du quel on cherche le chemin de somme max
     * @return  TabSM est le tableau de somme cumulée
     */
    public static int[] calculeTabSMGlouton(int[] pTabRef) {
        int nbNiveaux = niveau(pTabRef,pTabRef.length);
        int[] TabSM = new int[nbNiveaux];

        TabSM[0] = pTabRef[0];

        if(debug){
            System.out.println(Arrays.toString(pTabRef));
            System.out.println("niveau = 0 TabSM[0] = "+TabSM[0] );
        }

        int i = 0;
        for(int j=1; j<nbNiveaux; j++){
            int ig = g(pTabRef, i);
            int id = d(pTabRef, i);
            int maxgd = Math.max(pTabRef[ig], pTabRef[id]);
            if(maxgd == pTabRef[ig]) i = ig;
            else i = id;
            TabSM[j] = TabSM[j-1] + maxgd;

            // if(debug) System.out.println("niveau = "+j+" valg(i) = "+pTabRef[g(pTabRef, i)]+" vald(i) = "
            //     +pTabRef[d(pTabRef, i)]+" TabSM[niveau] = "+TabSM[i-1]+" valeur actuel "+pTabRef[j] );
            // bug d'indice entre i et j --> prb si g() return -1 
        }

        if(info) System.out.println("\nTabSM en mode glouton : "+Arrays.toString(TabSM));
        return TabSM;        
    }//calculeTabSMGlouton



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

    /**
     * fait la somme
     * @author R.Natovitch
     * @param T
     * @return
     */
    static int somme(int[] T){ int n = T.length;
		int s = 0; 
		for (int i = 0; i < n; i++) s = s + T[i];
		return s;
	}
    
    public static void mainSomme(){
        System.out.println("Hello, World! from Somme Class \n");
        d(MakeTrig(), 3);
        calculeTabSMGlouton(MakeTrig());
    }
    
}
