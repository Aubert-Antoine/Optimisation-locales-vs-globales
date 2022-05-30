public class Stock {

    /*
    Methode Dynamique : 
     */



    public static void mainStock(){
        System.out.println("Hello, World! from Stock Class");
    }
}




/* Exercice 3 : répartition optimale d'un stock S sur n entrepôts
	m(k,s) : gain d'une répartition optimale d'un stock s sur le sous-ensemble
	des k premiers entrepôts. 
	static int[][][] calculerMA(int[][] G){ // G[0:n][0:S+1] de terme général 
        // G[i][s] = gain d'une livraison d'un stock s à l'entrepôt i.
        // Calcule : M[0:n+1][0:S+1] de tg M[k][s] = m(k,s) et A = arg M.
        // Retourne : int[][][] MA = {M,A}.
            int n = G.length; int S = G[0].length - 1;
            int[][] M = new int[n+1][S+1], A =  new int[n+1][S+1];
            // Base : 
            
            // cas général
            
            return new int[][][] {M,A};
        }
        static void aro(int[][] M, int[][] A, int[][] G){ /* affichage d'une répartition
        optimale du stock S sur les n entrepôts. G
        G : tableau des gains (g(i,s) = gain d'une livraison d'un stock s à l'entrepôt i)
        G est à n lignes et S+1 colonnes où n est le nombre d'entrepôts et S le stock total. 
        M est le tableau de terme général m(k,s) = gain d'une répartition optimale d'un
        stock s sur le sous-ensemble des k premiers entrepôts. M est à n+1 lignes et S+1 col.
        A = arg M : a(k,s) = quantité de stock livré au k-ème entrepôt (de numéro k-1)
        dans une répartition optimale du stock s sur les k premiers entrepôts. */

    /*
            int n = G.length, S = G.length - 1;
            aro(M,A,G,n,S); // afficher une répartition optimale du stock S sur le 
            // sous-ensemble des n premiers entrepôts. Autrement dit : afficher une
            // répartition optimale du stock S sur tous les entrepôts (sans contrainte.)
        }
        static void aro(int[][] M, int[][] A, int[][] G, int k, int s){ /* affichage d'une 
            répartition optimale du stock s sur le sous-ensemble des k premiers entrepôts.
            Notation : ro(k,s) = répartition optimale du stock s sur le sous-ensemble [0:k] 
            // base = condition d'arrêt
                
            // cas général : récursion
        }
    */

/** Version TD Pauline 
 * 
 * /* Exercice 3 : répartition optimale d'un stock S sur n entrepôts
	m(k,s) : gain d'une répartition optimale d'un stock s sur le sous-ensemble
	des k premiers entrepôts. */
	static int[][][] calculerMA(int[][] G){ // G[0:n][0:S+1] de terme général 
        // G[i][s] = gain d'une livraison d'un stock s à l'entrepôt i.
        // Calcule : M[0:n+1][0:S+1] de tg M[k][s] = m(k,s) et A = arg M.
        // Retourne : int[][][] MA = {M,A}.
            int n = G.length; int S = G[0].length - 1;
            int[][] M = new int[n+1][S+1], A =  new int[n+1][S+1];
            // Base : 
            for(int s=0; s<S+1; s++) M[0][s] = 0;
            // cas général
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
        }
        static void aro(int[][] M, int[][] A, int[][] G){ /* affichage d'une répartition
        optimale du stock S sur les n entrepôts. G
        G : tableau des gains (g(i,s) = gain d'une livraison d'un stock s à l'entrepôt i)
        G est à n lignes et S+1 colonnes où n est le nombre d'entrepôts et S le stock total. 
        M est le tableau de terme général m(k,s) = gain d'une répartition optimale d'un
        stock s sur le sous-ensemble des k premiers entrepôts. M est à n+1 lignes et S+1 col.
        A = arg M : a(k,s) = quantité de stock livré au k-ème entrepôt (de numéro k-1)
        dans une répartition optimale du stock s sur les k premiers entrepôts. */
            int n = G.length, S = G.length - 1;
            aro(M,A,G,n,S); // afficher une répartition optimale du stock S sur le 
            // sous-ensemble des n premiers entrepôts. Autrement dit : afficher une
            // répartition optimale du stock S sur tous les entrepôts (sans contrainte.)
        }
        static void aro(int[][] M, int[][] A, int[][] G, int k, int s){ /* affichage d'une 
            répartition optimale du stock s sur le sous-ensemble des k premiers entrepôts.
            Notation : ro(k,s) = répartition optimale du stock s sur le sous-ensemble [0:k] */
            // base = condition d'arrêt
            if(k==0 && s==0) return;
            // cas général : récursion
            int sp = A[k][s];
            System.out.printf("Entrepôt : %d, stock : %d, valeur : %d", k-1, sp, G[k-1][sp]);
            aro(M, A, G, k-1, s-sp);
        }
 */


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

