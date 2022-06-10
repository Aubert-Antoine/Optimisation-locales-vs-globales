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
        if(debug) System.out.println("TAbTrig: "+  Arrays.toString(TabTrig));
        return TabTrig; 
    }


    /**
     * 
     * Repr esentation du triangle dans un tableau T[0:n]
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
     * calculerM(pTabRef) est la fonction qui calcule dynamiquement TabSomme, le tableau de somme cumulée max vis a vis de pTabRef 
     * @param pTabRef Tableau dont on cherche le chemin de somme max
     * @return  TabSomme le Tableau de somme max cummulée
     */
    public static int[] calculerM(int[] pTabRef) {
        int nbNiveaux = niveau(pTabRef);
        int indiceDeb = pTabRef.length-1-nbNiveaux;             //indice de debut de somme
        int[] TabSomme = Arrays.copyOf(pTabRef, pTabRef.length);

        //System.out.println("avec i depart = "+indiceDeb+" et i se decremente en parcourant pTAbRef. C'est un indice");
        for (int i = indiceDeb; i >= 0; i--) {  //Pourquoi >= ? prb pour les Runs
            TabSomme[i] = Math.max(TabSomme[g(pTabRef, i)], TabSomme[d(pTabRef, i)])+pTabRef[i];      //prb pTabRef n'eset pas la somme donc pas de memoisation
            // On fait la somme de la valeur à l'indice courant + du max de la ligne inf
            // pas d'influence entre g(pTabRef) et g(TabSomme) ? de meme pout + pTabRef[i] ? 
            
            if(debug) System.out.println("i = "+i+" et TabSomme[i] = "+TabSomme[i]);

        }

        if(info) System.out.println("TabSomme : "+Arrays.toString(TabSomme));

        return TabSomme;
    }//calculerM


    // METHODE Gloutonne : 
    //
    
    /**
     * calculeTabSMGlouton est la fonction de calcule du chemin de somme max selon le pTabRef
     * @param pTabRef tableau du quel on cherche le chemin de somme max
     * @return  TabSM est le chemin 
     */
    public static int[] calculeTabSMGlouton(int[] pTabRef) {
        int nbNiveaux = niveau(pTabRef);
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
            TabSM[j] = maxgd;       // si on veut la somme cumulee on +TabSM[j-1]

            // if(debug) System.out.println("niveau = "+j+" valg(i) = "+pTabRef[g(pTabRef, i)]+" vald(i) = "
            //     +pTabRef[d(pTabRef, i)]+" TabSM[niveau] = "+TabSM[i-1]+" valeur actuel "+pTabRef[j] );
            // bug d'indice entre i et j --> prb si g() return -1 
        }

        //System.out.println("\nTabSM en mode glouton : "+Arrays.toString(TabSM));
        
        return TabSM;        
    }//calculeTabSMGlouton



    /**
     * 
     * affichage
     * 
     *  */

    /**
     * acsm affiche un chemin de somme maximum commencant en i.
     * Methode recurcive
     * @param M est le tableau de somme max cumulee 
     * @param T est le tableau triangle cree par makeTrig()
     * @param i indice de debut d'affichage
     * @param n Taille du triangle originel, fin d'affichaga
     */
    public static void acsm(int[] M, int[] T, int i, int n) {
        
        if(n<0 || i<0 || n<i) System.out.println("n et i doivent etre positifs n = "+n+" i = "+i
                                                    +"\net n>i");
        else{
            if(i<M.length-niveau(M)) {        //si nous ne sommes pas sur la derniere ligne
                int ip = i;                             // On memorise la valeur actuelle
                int ig = g(M, i);
                int id = d(M, i);
                int maxgd = Math.max(M[ig], M[id]);

                if(maxgd == M[ig]) i = ig;              //Determine l'indice du descendant max
                else i = id;
                System.out.println("valeur = "+(M[ip]-M[i]));  //La valeur de la case courante deT[] est 
                acsm(M, T, i, n);                              //la val precedente de M[] moins la courante de M[]
            }
            else {                                      //si nous sommes sur la derniere ligne
                System.out.println("valeur = "+(M[i]));
            }
        }

    }//acsm()



     /**
     * 
     * Autre methodes
     * 
     *  */

    /**
     * niveau renvoit le nombre de niveaux du tableau passé en param
     * @param pTabRef tableau dont on chrche le nb de niveau
     * @return le nb de niveau en int
     */
    public static int niveau(int[] pTabRef) {
        if(pTabRef.length == 0){
            System.out.println("La taille du tableau est null \n"+pTabRef+" = "+Arrays.toString(pTabRef));
            return -1;
        }
        else if(pTabRef.length == 1) return 1;
        else{
            for (int niveau = 2; niveau < pTabRef.length; niveau++) {   //La condition de fin est suffisante
                if(niveau*(niveau+1)/2 == pTabRef.length) return niveau;
                else if(niveau == pTabRef.length) System.out.println("Somme > niveau : le if n'est pas catch");
            }
        }//else
        System.out.println("Somme > niveau : le else n'est pas catch");
        return -1;
    }//niveau()

    /**
     * fait la somme d'un tableau
     * @author R.Natovitch
     * @param T
     * @return
     */
    static int somme(int[] T){ 
        int n = T.length;
		int s = 0; 
		for (int i = 0; i < n; i++) s = s + T[i];
		return s;
	}

    /**
     * EvalStatSomme genere les runs et stock la distance relative entre les solution goulonne et dynamique
     * @param pLmax le nombre de niveaux maximum.
     * @param pNruns le nombre de runs de l’ evaluation statistique
     * @param pVmax la plus grande valeur pouvant ˆetre pr ́esente dans le triangle
     * @return D[0 : N runs] qui contiendra pour chaque runla distance relative entre la valeur du chemin de somme maximum et la valeur du chemin glouton.
     */
    public static double[] EvalStatSomme(int pLmax, int pNruns, int pVmax) {
        double[] D = new double[pNruns];

        System.out.println("Les param sont : pLmax = "+pLmax+"  pNruns = "+pNruns+"  pVmax = "+pVmax+"\n");

        for (int r = 0; r < D.length; r++) {
            int nbNiveaux = RandomGen.randomInt(1, pLmax);
            int nbElement = (nbNiveaux*(nbNiveaux+1))/2;
            int[] T = RandomGen.randomTabInt(nbElement, pVmax);

            

            if(true) {
                System.out.println("Runs numero : "+r);
                System.out.println("Le tableau random : T = "+Arrays.toString(T));
                System.out.println("calculerM(T)[0] = "+calculerM(T)[0]);
                System.out.println("somme(calculeTabSMGlouton(T)) = "+somme(calculeTabSMGlouton(T))+"\n");
            }

           
            
            D[r] = EvalStat.evalMax(calculerM(T)[0], somme(calculeTabSMGlouton(T)));
            //On regarde la valeur m(0) qui est le max, on fait le ration puis on attribut le ratio
            // a D[r], on fait cela Nruns fois
        }
        if(true) System.out.println("Somme > EvalStatSomme : D = "+Arrays.toString(D));
        return D;
    }//EvalStatSomme()

    
    public static void mainSomme(){
        System.out.println("Hello, World! from Somme Class \n");


        // int[] curTab = MakeTrig();
        // System.out.println("MakeTrig()"+Arrays.toString(curTab));
        // int[] M = calculerM(curTab);         
        // System.out.println("\nLe chemin de somme cumulee max en dynamique : ");
        // acsm(M, curTab, 0, curTab.length);


        // System.out.println("\nLe chemin de somme cumulee max en glouton : ");
        // calculeTabSMGlouton(curTab);

        System.out.println("Evaluations statistique de Somme : ");
        double[] out = EvalStatSomme(6, 10, 15);
        System.out.println("out : " + Arrays.toString(out));
    }
    
}
