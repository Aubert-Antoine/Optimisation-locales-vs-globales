import java.io.IOException;
import java.util.Arrays;

// https://projecteuler.net/problem=67
public class Somme {

    private static final boolean debug = false;
    private static final boolean info = false;

    /**
     * MakeTrig() retourne un tableau, qui contient les valeurs du triangle du projet euler
     * @return le tableau de ref du projet Euler
     */
    public static int[] MakeTrig() {
        int[] TabTrig = new int[]{3,7,4,2,4,6,8,5,9,3};
        if(debug) System.out.println("TAbTrig: "+  Arrays.toString(TabTrig));
        return TabTrig; 
    }

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
                    break;
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
            
            }//else
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

        
    
    // METHODE DYNAMIQUE : 
    //

    /**
     * calculerM(pTabRef) est la fonction qui calcule dynamiquement TabSomme, le tableau de somme cumulée max vis a vis de pTabRef 
     * Strategie iterative
     * @param pTabRef Tableau dont on cherche le chemin de somme max
     * @return  TabSomme le Tableau de somme max cummulée
     */
    public static int[] calculerM(int[] pTabRef) {
        int nbNiveaux = niveau(pTabRef);
        int indiceDeb = pTabRef.length-1-nbNiveaux;             //indice de debut de somme
        int[] TabSomme = Arrays.copyOf(pTabRef, pTabRef.length);

        for (int i = indiceDeb; i >= 0; i--) { 
            TabSomme[i] = Math.max(TabSomme[g(pTabRef, i)], TabSomme[d(pTabRef, i)])+pTabRef[i];      //prb pTabRef n'eset pas la somme donc pas de memoisation
            // On fait la somme de la valeur à l'indice courant + du max de la ligne inf
            
            if(debug) System.out.println("i = "+i+" et TabSomme[i] = "+TabSomme[i]);

        }//for

        if(info) System.out.println("TabSomme : "+Arrays.toString(TabSomme));

        return TabSomme;
    }//calculerM


    // METHODE Gloutonne : 
    //
    
    /**
     * calculeTabSMGlouton est la fonction de calcule du chemin de somme max selon le pTabRef
     * @param pTabRef tableau du quel on cherche le chemin de somme max
     * @return  TabSM est le chemin --> /!\ pas la somme cumulé des chemins /!\ 
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
            TabSM[j] = maxgd; 
        }

        if(info)System.out.println("\nTabSM en mode glouton : "+Arrays.toString(TabSM));
        
        return TabSM;        
    }//calculeTabSMGlouton


    /**
     * acsm affiche un chemin de somme maximum commencant en i.
     * Methode recurcive --> pour connaitre la valeur a l'indice courant -> [(Val n-1) - val n]
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
     * niveau renvoit le nombre de niveaux du tableau passé en param 
     * /!\ commence a 1 /!\
     * @param pTabRef tableau dont on chrche le nb de niveau
     * @return le nb de niveau en int
     */
    public static int niveau(int[] pTabRef) {
        if(pTabRef.length == 0){
            System.out.println("La taille du tableau est nulle \n"+pTabRef+" = "+Arrays.toString(pTabRef));
            return -1;
        }
        else if(pTabRef.length == 1) return 1;
        else{
            for (int niveau = 2; niveau < pTabRef.length; niveau++) {   //La condition de fin est suffisante
                if(niveau*(niveau+1)/2 == pTabRef.length) return niveau;
                else if(niveau == pTabRef.length) System.out.println("Somme > niveau : le if n'est pas catch");
            }
        }//else
        return -1;
    }//niveau()

    /**
     * fait la somme d'un tableau
     * @author R.Natovitcz
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
     * Renvoit des double pour plus de precision
     * @param pLmax le nombre de niveaux maximum
     * @param pNruns le nombre de runs de l’evaluation statistique
     * @param pVmax la plus grande valeur pouvant être presente dans le triangle
     * @return D[0 : N runs] qui contiendra pour chaque run la distance relative entre la valeur du chemin de somme maximum et la valeur du chemin glouton.
     */
    public static double[] EvalStatSomme(int pLmax, int pNruns, int pVmax) {
        double[] D = new double[pNruns];

        System.out.println("Les param sont : pLmax = "+pLmax+"  pNruns = "+pNruns+"  pVmax = "+pVmax+"\n");

        if(pLmax <= 0 || pNruns <= 0 || pVmax <= 0){
            System.out.println("\nLes param doivent etre positifs\n!!!!!!!!!!!!!!!!!!!!!\n");
            return D;
        }

        for (int r = 0; r < D.length; r++) {
            int nbNiveaux = RandomGen.randomInt(1, pLmax);
            int nbElement = (nbNiveaux*(nbNiveaux+1))/2;
            int[] T = RandomGen.randomTabInt(nbElement, pVmax);

            

            if(info) {
                System.out.println("Runs numero : "+r);
                System.out.println("Le tableau random : T = "+Arrays.toString(T));
                System.out.println("La valeur dynamique : calculerM(T)[0] = "+calculerM(T)[0]);
                System.out.println("La valeur gloutonne : somme(calculeTabSMGlouton(T)) = "+somme(calculeTabSMGlouton(T))+"\n");
            }

           
            
            D[r] = EvalStat.evalMax(calculerM(T)[0], somme(calculeTabSMGlouton(T)));
            //On regarde la valeur m(0) qui est le max, on fait le ration puis on attribut le ratio
            // a D[r], on fait cela Nruns fois
        }
        if(info) System.out.println("Somme > EvalStatSomme : D = "+Arrays.toString(D));
        return D;
    }//EvalStatSomme()

    /**
     * mainSomme() permet l'execution du programme somme. 
     * 1ere partie => 1 run 
     * 2eme partie => n runs
     * @throws IOException
     */
    public static void mainSomme() throws IOException{
        System.out.println("\n\nExercice : chemin de somme maximum\n");

        //** Evaluation avec run unique et avec un tableaux deterministe*/

        // int[] curTab = MakeTrig();
        // System.out.println("MakeTrig()"+Arrays.toString(curTab));
        // int[] M = calculerM(curTab);         
        // System.out.println("\nLe chemin de somme cumulee max en dynamique : ");
        // acsm(M, curTab, 0, curTab.length);


        // System.out.println("\nLe chemin de somme cumulee max en glouton : ");
        // calculeTabSMGlouton(curTab);



        //** Evaluation des runs multiples et avec des tableaux random*/

        System.out.println("Evaluation statistique de Somme :\n");
        double[] out = EvalStatSomme(100, 5000, 100);
        // System.out.println("out : " + Arrays.toString(out)+"\n");

        System.out.println("medianne = "+EvalStat.mediane(out));

        System.out.println("moyenne = "+EvalStat.moyenne(out));
        System.out.println("ecart type = "+EvalStat.ecartType(out));

        EcrireValeursGaussiennesDansFichier.EcrireGdansF(out, "Somme.csv");

        System.out.println("\n\nFIN de SOMME\n\n\n");
    }//mainSomme()
    
}//Somme Class
