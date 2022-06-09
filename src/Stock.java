import java.util.Arrays;


public class Stock {


    /**
     * mainStock appelant les méthodes dynamique et greedy et affichant leurs résultats
     */
    public static void mainStock(){
		System.out.println("\n\nExercice : répartition optimale d'un stock");
        int[][] G = new int[][] // g(k,s) = gain obtenu d'une livraison d'une quantité de stock s à l'entrepôt k
            {	{0, 5, 5, 7, 7,10,10,12,12,13,13},
                {0, 8,10,10,10,12,12,14,14,14,14},
                {0,10,10,12,12,13,13,14,15,16,16},
                {0,14,14,14,16,16,16,16,16,16,16},
                {0,10,14,14,14,14,14,14,14,16,16},
                {0,10,12,12,16,16,16,16,16,16,16},
                {0,12,12,14,14,15,15,15,17,17,17}
            } ;		
        int K = G.length, S = G[0].length - 1;
        System.out.println("\nTableau des gains : g(k,s) = gain obtenu en livrant s à k");
        afficher(G);

		// Méthode Dynamique
		System.out.println("\n\nMETHODE DYNAMIQUE");
        int[][][] MA = calculerMA(G);
        int[][] M = MA[0], A = MA[1];
        System.out.printf("\nGain total maximum : %d\n", M[K][S]);
        System.out.println("\nTableau M des gains maximums :");
        afficher(M);
        System.out.println("\nUne affectation optimale :");
		aro(M,A,G,K,S);
		
		//Méthode Greedy
		System.out.println("\n\nMETHODE GREEDY");
		repartitionGreedy(G);
		
		System.out.println();
	}//mainRobot()


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
     * @param G tableau des gains liés à la livraison d'un stock dans un entrepôt
     */
    static void repartitionGreedy(int[][] G){
        int n = G.length; int S = G[0].length-1; // n le nombre d'entrepôts et S le nombre de stocks
        int[] stockTab = new int[n]; // tableau associant à chaque entrepôt le nombre de stocks attribués 
        int[] gainTab = new int[n]; // tableau associant à chaque entrepôt son gain total
        int gainTotal = 0; // gain total de l'ensemble des entrepôts une fois les stocks répartis
        for(int s=0; s<=S; s++) { // attribution de chaque stock un à un à un entrepôt
            int entrepot = maxGain(G,s)[0]; // entrepôt contenant le stock
            int gain = maxGain(G,s)[1]; // gain associé au stock dans l'entrepôt sélectionné
            stockTab[entrepot] += 1; // augmentation du nombre de stocks dans l'entrepôt
            gainTab[entrepot] += gain; // augmentation du gain global de l'entrepôt
            gainTotal += gain; // augmentation du gain total avec la répartition d'un stock supplémentaire
        }
        System.out.printf("\nGain total maximum : %d\n\n", gainTotal);
        for(int e=0; e<n; e++) {
            System.out.printf("entrepôt %d : stock livré = %d, gain = %d\n", e, stockTab[e], gainTab[e]);
        }
    }//repartitionGreedy()

    /**
     * maxGain calculant le gain maximum potentiel pour une association entrepôt-stock
     * @param T tableau des gains liés à la livraison d'un stock dans un entrepôt
     * @param s entier représentant le stock à placer
     * @return tableau d'entiers contenant l'entrepôt sélectionné pour un stock particulier et le gain associé
     */
    static int[] maxGain(int[][] G, int s){
        int[] Tab = new int[2]; // tableau contenant l'entrepôt et son gain pour un stock précis
        int entrepot = 0;
        int gainMax = G[0][s];
        for(int i=1; i<G.length; i++) { // meilleure attribution possible d'un stock en recherchant parmi tous les entrepôts
            if(gainMax < G[i][s]) { // gain maximum actuel moins grand que gain maximum potentiel
                entrepot = i; // nouvel entrepôt contenant le stock
                gainMax = G[i][s]; // nouveau gain maximum
            }
        }
        Tab[0] = entrepot;
        Tab[1] = gainMax;
        return Tab;
    }//maxGain()


    /* Fonctions annexes */
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


/*

Exercice 3 : répartition optimale d'un stock

tableau des gain : g(k,s) = gain obtenu en livrant s à k
[0, 12, 12, 14, 14, 15, 15, 15, 17, 17, 17]
[0, 10, 12, 12, 16, 16, 16, 16, 16, 16, 16]
[0, 10, 14, 14, 14, 14, 14, 14, 14, 16, 16]
[0, 14, 14, 14, 16, 16, 16, 16, 16, 16, 16]
[0, 10, 10, 12, 12, 13, 13, 14, 15, 16, 16]
[0, 8, 10, 10, 10, 12, 12, 14, 14, 14, 14]
[0, 5, 5, 7, 7, 10, 10, 12, 12, 13, 13]

gain total maximum : 77

tableau M des gains maximum :
[0, 14, 26, 36, 46, 56, 64, 69, 73, 75, 77]
[0, 14, 24, 34, 44, 52, 57, 61, 63, 65, 67]
[0, 14, 24, 34, 42, 47, 51, 53, 53, 55, 56]
[0, 14, 24, 32, 37, 39, 39, 41, 42, 44, 44]
[0, 10, 18, 23, 25, 25, 27, 28, 30, 30, 32]
[0, 8, 13, 15, 15, 17, 18, 20, 20, 22, 22]
[0, 5, 5, 7, 7, 10, 10, 12, 12, 13, 13]
[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]

une affectation optimale :
entrepôt  0 : stock livré = 1, gain = 5
entrepôt  1 : stock livré = 2, gain = 10
entrepôt  2 : stock livré = 1, gain = 10
entrepôt  3 : stock livré = 1, gain = 14
entrepôt  4 : stock livré = 2, gain = 14
entrepôt  5 : stock livré = 2, gain = 12
entrepôt  6 : stock livré = 1, gain = 12

*/