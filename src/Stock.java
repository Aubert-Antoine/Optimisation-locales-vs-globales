import java.io.IOException;
import java.util.Arrays;
import java.util.Random;


public class Stock {


    private static final boolean info = false;

    /**
     * mainStock appelant les méthodes dynamique et greedy et affichant leurs résultats
     * @throws IOException
     */
    public static void mainStock() throws IOException{
		System.out.println("\n\nExercice : répartition optimale d'un stock sur un ensemble d’entrepôts");

        /* cas particulier */

        // int[][] G = new int[][] // g(k,s) = gain obtenu d'une livraison d'une quantité de stock s à l'entrepôt k
        //     {	{0, 5, 5, 7, 7,10,10,12,12,13,13},
        //         {0, 8,10,10,10,12,12,14,14,14,14},
        //         {0,10,10,12,12,13,13,14,15,16,16},
        //         {0,14,14,14,16,16,16,16,16,16,16},
        //         {0,10,14,14,14,14,14,14,14,16,16},
        //         {0,10,12,12,16,16,16,16,16,16,16},
        //         {0,12,12,14,14,15,15,15,17,17,17}
        //     } ;		
        // int K = G.length, S = G[0].length - 1;
        // System.out.println("\nTableau des gains : g(k,s) = gain obtenu en livrant s à k");
        // afficher(G);

		// // Méthode Dynamique
		// System.out.println("\n\nMETHODE DYNAMIQUE");
        // int[][][] MA = calculerMA(G);
        // int[][] M = MA[0], A = MA[1];
        // System.out.printf("\nGain total maximum : %d\n", M[K][S]);
        // System.out.println("\nTableau M des gains maximums :");
        // afficher(M);
        // System.out.println("\nUne affectation optimale :");
		// aro(M,A,G,K,S);
		
		// //Méthode Greedy
		// System.out.println("\n\nMETHODE GREEDY");
		// repartitionGreedy(G);
		
		// System.out.println();


        /* Runs */
        System.out.println("Evaluation statistique de Stock : ");
        double[] out = EvalStatStock(5000, 100);
        //System.out.println("out : " + Arrays.toString(out)+"\n");

        System.out.println("medianne = "+EvalStat.mediane(out));
        System.out.println("moyenne = "+EvalStat.moyenne(out));
        System.out.println("ecart type = "+EvalStat.ecartType(out));

        EcrireValeursGaussiennesDansFichier.EcrireGdansF(out, "Stock.csv");

        System.out.println("\n\nFIN de Stock \n\n\n");

	}//mainStock()


    /**
     * EvalStatStock genere les runs et stocke la distance relative entre les solutions goulonne et dynamique
     * @param pNruns le nombre de runs de l’evaluation statistique
     * @param pVmax la plus grande valeur pour 'nombre entrepots' et 'nombre stocks'
     * @return D[0 : N runs] qui contiendra pour chaque runla distance relative entre la valeur du chemin de somme maximum et la valeur du chemin glouton.
     */
	public static double[] EvalStatStock(int pNruns, int pVmax) {
        double[] D = new double[pNruns];

        System.out.println("Les param sont : pNruns = "+pNruns+"  pVmax = "+pVmax+"\n");

        if(pNruns <= 0 || pVmax <= 0){
            System.out.println("\nLes param doivent etre positifs\n!!!!!!!!!!!!!!!!!!!!!\n");
            D[0] = -1;
            return D;       // return une Exception ? 
        }

        for (int r = 0; r < D.length; r++) {
			int nbreEntrepots = RandomGen.randomInt(1, pVmax);
            int nbreStocks = RandomGen.randomInt(1, pVmax);
			int[][] G = estimations(nbreEntrepots, nbreStocks); // gains aléatoires, croissants selon les stocks
            

            if(info) {
                System.out.println("Runs numero : "+r);
                System.out.println("Le nombre d'unités random : nbreEntrepots = "+nbreEntrepots);
				System.out.println("Le nombre d'heures max random : nbreStocks = "+nbreStocks);
                System.out.println("Le tableau random : G = "+Arrays.toString(G));
                System.out.println("La valeur dynamique : calculerMA(G)[0][G.length][G[0].length-1] = "+calculerMA(G)[0][G.length][G[0].length-1]);
                System.out.println("La valeur gloutonne : repartitionGreedy(G) = "+repartitionGreedy(G)+"\n");
            }

           
            
            D[r] = EvalStat.evalMax(calculerMA(G)[0][G.length][G[0].length-1], repartitionGreedy(G));
            //On regarde la valeur m(0) qui est le max, on fait le ration puis on attribut le ratio
            // a D[r], on fait cela Nruns fois
        }
        if(info) System.out.println("Stock > EvalStatStock : D = "+Arrays.toString(D));
        return D;
    }//EvalStatStock()


    //
	/* Methode Dynamique */
	//

    // Exercice : répartition optimale d'un stock S sur n entrepôts
	// m(k,s) : gain d'une répartition optimale d'un stock s sur le sous-ensemble des k premiers entrepôts.

	static int[][][] calculerMA(int[][] G){
    // G[0:n][0:S+1] de terme général 
    // G[i][s] = gain d'une livraison d'un stock s à l'entrepôt i
    // Calcule : M[0:n+1][0:S+1] de tg M[k][s] = m(k,s) et A = arg M
    // Retourne : int[][][] MA = {M,A}
        int n = G.length; int S = G[0].length - 1;
        int[][] M = new int[n+1][S+1], A =  new int[n+1][S+1];

        // Base
        for(int s=0; s<S+1; s++) M[0][s] = 0;

        // Cas général
        for(int k=1;  k<n+1; k++)
            for(int s=0; s<S+1; s++) {
                int mks = -1;
                int arg = -1;
                for(int sp=0; sp<s+1; sp++) {
                    if(mks < M[k-1][s-sp]+G[k-1][sp]) {
                        mks = M[k-1][s-sp]+G[k-1][sp];
                        arg = sp;
                    }
                    M[k][s] = mks;
                    A[k][s] = arg;
                }
            }	

        return new int[][][] {M,A};
    }//calculerMA()

    static void aro(int[][] M, int[][] A, int[][] G){
    /* affichage d'une répartition optimale du stock S sur les n entrepôts
    G : tableau des gains (g(i,s) = gain d'une livraison d'un stock s à l'entrepôt i)
    G est à n lignes et S+1 colonnes où n est le nombre d'entrepôts et S le stock total
    M est le tableau de terme général m(k,s) = gain d'une répartition optimale d'un stock s sur le sous-ensemble des k premiers entrepôts
    M est à n+1 lignes et S+1 colonnes
    A = arg M : a(k,s) = quantité de stock livré au k-ème entrepôt (de numéro k-1)
    dans une répartition optimale du stock s sur les k premiers entrepôts */
        int n = G.length, S = G.length-1;
        aro(M,A,G,n,S);
        // afficher une répartition optimale du stock S sur le sous-ensemble des n premiers entrepôts
        // autrement dit : afficher une répartition optimale du stock S sur tous les entrepôts (sans contrainte)
    }//aro()

    static void aro(int[][] M, int[][] A, int[][] G, int k, int s){
        /* affichage d'une répartition optimale du stock s sur le sous-ensemble des k premiers entrepôts
        notation : ro(k,s) = répartition optimale du stock s sur le sous-ensemble [0:k] */
        
        // Base : condition d'arrêt
        if(k==0 && s==0) return;

        // Cas général : récursion
        int sp = A[k][s];
        aro(M, A, G, k-1, s-sp);
        System.out.printf("entrepôt %d : stock livré = %d, gain = %d\n", k-1, sp, G[k-1][sp]);
    }//aro()


    //
	/* Methode Greedy */
	//

    /**
     * repartitionGreedy permettant de calculer la répartition de stocks sur des entrepôts avec la méthode gloutonne
     * @param G tableau des gains liés à la livraison d'un ensemble de stocks dans un entrepôt
     * @return le gain max de l'ensemble des entrepôts
    */
    static int repartitionGreedy(int[][] G){
        int n = G.length; int S = G[0].length-1; // n le nombre d'entrepôts et S le stock total
        int[] stockTab = new int[n]; // tableau associant à chaque entrepôt le nombre de stocks attribués 
        int[] gainTab = new int[n]; // tableau associant à chaque entrepôt son gain global
        int gainTotal = 0; // gain total de l'ensemble des entrepôts
        for(int s=1; s<=S; s++) { // attribution de chaque stock un à un à un entrepôt
            int entrepot = 0;
            int gainMax = 0;
            for(int e=0; e<n; e++) { // visualisation de l'ensemble des entrepôts un à un
                if(gainMax < (G[e][stockTab[e]+1]-gainTab[e])) { // gain maximum actuel d'un entrepôt < gain potentiel d'un autre entrepôt grâce à l'ajout du stock s
                    entrepot = e; // nouvel entrepôt où le stock s est attribué
                    gainMax = G[e][stockTab[e]+1]-gainTab[e]; // nouveau gain maximum lié au placement du stock s
                }
            }
            stockTab[entrepot] += 1; // attribution du stock s à l'entrepôt avec le meilleur gain
            gainTab[entrepot] += gainMax; // attribution du nouveau gain de l'entrepôt
            gainTotal += gainMax; // ajout du gain amassé au gain total
        }
        if(info) {
            System.out.printf("\nGain total maximum : %d\n\n", gainTotal);
            for(int e=0; e<n; e++) {
                System.out.printf("entrepôt %d : stock livré = %d, gain = %d\n", e, stockTab[e], gainTab[e]);
            }
        }
        return gainTotal;
    }//repartitionGreedy()


    /* Fonctions annexes */

    static int[][] estimations(int n, int S){ // retourne G[0:n][0:S+1] de terme général G[k][s] = g(k,s)
    // Les estimations sont aléatoires, croissantes selon s
        int[][] G = new int[n][S+1];
        Random rand = new Random(); // pour génération aléatoire des gains estimés
        for (int k = 0; k < n; k++) {
            G[k][0] = 0;
            G[k][1] = 5 + rand.nextInt(10);
        }
        for (int k = 2; k < n; k++)
            for (int s = 1; s < S+1; s++)
                G[k][s] = G[k][s-1] + (rand.nextInt(3));
        return G;
    }//estimations()

	static int max(int x, int y){ if (x >= y) return x; return y;}//max()

	static int max(int x, int y, int z){ if (x >= max(y,z)) return x; 
		if (y >= z) return y; 
		return z;
	}//max()

	static int min(int x, int y){ if (x <= y) return x; return y;}//min()

	static int min(int x, int y, int z){ if (x <= min(y,z)) return x; 
		if (y <= z) return y; 
		return z;
	}//min()

	static int somme(int[] T){ int n = T.length;
		int s = 0; 
		for (int i = 0; i < n; i++) s = s + T[i];
		return s;
	}//somme()

	static void afficher(int[][] T){int n = T.length;
		for (int i = n-1; i >= 0; i--)
			System.out.println(Arrays.toString(T[i]));
	}//afficher()


}//Stock
