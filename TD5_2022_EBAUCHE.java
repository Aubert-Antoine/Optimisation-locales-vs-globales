import java.util.Arrays;
class TD5_2022{ 
// Ebauche du programme -- mars 2022 - rene.natowicz@esiee.fr
static final int plusInfini = Integer.MAX_VALUE, moinsInfini = Integer.MIN_VALUE;

/* Exercice 1 : spots et slots */
	static int[][] calculerM(int[] G, int[] D, int T){int K = G.length; 
		int[][] M = new int[K+1][T+1];
		// base

		// cas général

		return M;
	} 
	static void asegtm(int[][] M, int[] G, int[] D, int k, int t){
	/* affiche  sous-ensemble des k premiers spots de gain total maximum
	pour le slot de durée t.
	Notation : Sstar(k,t), sous-ensemble des k-premiers spots de gain 
	total maximum  pour le slot de durée t.  Appel principal : asegtm(M,G,D,K,T) */
	// cas de base = condition d'arrêt

	// cas général : appel récursif
	}

/* Exercice 2 : 2 sacs. */
	static int[][][] calculerM(int[] V, int[] T, int C0, int C1){ int n = V.length;
		int[][][] M = new int[n+1][C0+1][C1+1];
		// base 
		  
		// cas général
		   
		return M;
	}
	static void acsm(int[][][] M, int[] V, int[] T, int k, int c0, int c1){
	/* affiche le contenu des sacs de contenance c0 et c1 de valeur maximum, 
	contenant un sous-ensemble des k premiers objets. */
		// base = condition d'arrêt
		
		// cas général : appel récursif
		
	}  
/* Exercice 3 : répartition optimale d'un stock S sur n entrepôts
	m(k,s) : gain d'une répartition optimale d'un stock s sur le sous-ensemble
	des k premiers entrepôts. */
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
		int n = G.length, S = G.length - 1;
		aro(M,A,G,n,S); // afficher une répartition optimale du stock S sur le 
		// sous-ensemble des n premiers entrepôts. Autrement dit : afficher une
		// répartition optimale du stock S sur tous les entrepôts (sans contrainte.)
	}
	static void aro(int[][] M, int[][] A, int[][] G, int k, int s){ /* affichage d'une 
		répartition optimale du stock s sur le sous-ensemble des k premiers entrepôts.
		Notation : ro(k,s) = répartition optimale du stock s sur le sous-ensemble [0:k] */
		// base = condition d'arrêt
			
		// cas général : récursion
	}
	
/* Exercice 4 : sous-ensemble de X, de somme S */
	static boolean[][] calculerE(int[] X, int S){ int n = X.length;
		boolean E[][] = new boolean[n+1][S+1]; // de terme général E[k][s] = e(k,s)
		// base = condition d'arrêt
		
		// cas général : récursion
		
		return E;
	}
	static void ase(boolean[][] E, int[] X, int k, int s){
	/* Affiche un sous-ensemble de X[0:k] de somme s
	Notation : se(k,s) = sous-ensemble de X[0:k] de somme s */
		// base : condition d'arrêt
		
		// cas général : récursion
		
	}

/* Exercice 5 : sous-ensemble de X, de somme S, répétitions autorisées */	
	static boolean[][] calculerEar(int[] X, int S){ int n = X.length; 
	// ar = avec répétitions
		boolean E[][] = new boolean[n+1][S+1]; // de terme général E[k][s] = e(k,s)
		// base : condition d'arrêt
		
		// cas général : récursion
		
		return E;
	}
	static void asear(boolean[][] E, int[] X, int k, int s){
	/* Affiche un sous-ensemble de X[0:k] de somme s, avec répétitions autorisées
	Notation : se(k,s) = sous-ensemble de X[0:k] de somme s, avec répétitions 
	autorisées */
		// base = condition d'arrêt
		
		// cas général : récursion 
		
	}		
	/* Exercice 6 : le petit robot. */
	static int[][] calculerM(int L, int C){ // une grille L x C
		int[][] M = new int[L][C]; // de terme général M[l][c] = m(l,c)
		// base 
		
		// cas général
		
		return M;
	}
	static void accm(int[][] M, int L, int C, int l, int c){
	// affiche un chemin de coût minimimum (ccm) de 0,0 à l,c
	
	}
	/* Fonctions de coût des déplacements.
	1) depuis la case 00, les déplacements N et E coûtent 1, le déplacement NE coûte 0.
	2) sur la colonne 0, les autres déplacements coûtent 0
	3) sur la ligne L-1 les déplacements E coûtent 0
	4) tous les autres déplacements coûtent 1.
	Chemin de coût minimum : 
		00 -1-> 10 -0->  -0-> (L-1)0 -0-> (L-1)1 -0->  -0-> L(L-1)(C-1).
	Il est de coût 1.
	*/ 
	static int n(int l, int c, int L, int C){
		if (l==L-1) return plusInfini;
		if (l==0 && c==0) return 1;
		if (c==0) return 0;
		return 1;
	}
	static int ne(int l, int c, int L, int C){
		if (l == L-1 || c == C-1) return plusInfini;
		if (l==0 && c==0) return 0;
		return 1;
	}
	static int e(int l, int c, int L, int C){
		if (c == C-1) return plusInfini;
		if (l == L-1) return 0;
		return 1;
	}	
	public static void main0(String[] args){
		{	System.out.println("Exercice 1 : spots & slots");
			int[] D = {20, 20, 70, 10, 10, 40, 10, 80, 10, 40, 45}, // durées des spots
				  G = {25, 25, 65, 15, 5, 35, 15, 75, 15, 45, 50}; // gains des spots
			int T = 100 ; // durée du slot
			System.out.println("durées des spots : " + Arrays.toString(D));
			System.out.println("gains des spots  : " + Arrays.toString(G));
			System.out.printf("durée du slot : %d\n", T);
			int[][] M = calculerM(G,D,T);
			int n = D.length; // nombre de spots
			System.out.printf("gain maximum du slot : %d\n", M[n][T]);
			System.out.println("sous-ensemble de spots de gain maximum :");
			asegtm(M,G,D,n,T);
			System.out.println(); System.out.println();
		}		
			
		{	System.out.println("Exercice 2 : deux sacs");
			int[] T = {20, 20, 70, 10, 10, 40, 10, 80, 10, 40, 45}, // tailles des objets
				  V = {25, 25, 65, 15, 5, 35, 15, 75, 15, 45, 50}; // valeurs des objets
			int C0 = 25, C1 = 75  ; // contenance des sacs
			System.out.println("Tailles des objets: " + Arrays.toString(T));
			System.out.println("Valeurs des objets  : " + Arrays.toString(V));
			System.out.printf("Contenances des sacs : C0 = %d, C1 = %d\n", C0,C1);
			int[][][] M = calculerM(V,T,C0,C1);
			int n = T.length; // nombre de spots
			System.out.printf("gain maximum des sacs : %d\n", M[n][C0][C1]);
			System.out.println("sous-ensemble d'objets de gain toal maximum :");
			acsm(M,V,T,n,C0,C1);
			System.out.println(); System.out.println();
		}		
			
		{	System.out.println("Exercice 3 : répartition optimale d'un stock");
			int[][] G = new int[][] // g(k,s) = gain obtenu d'une livraison 
			// d'une quantité de stock s à l'entrepôt k
				{	{ 0, 5, 5, 7, 7,10,10,12,12,13,13}, // 
					{ 0, 8,10,10,10,12,12,14,14,14,14},
					{0,10,10,12,12,13,13,14,15,16,16},
					{0,14,14,14,16,16,16,16,16,16,16},
					{0,10,14,14,14,14,14,14,14,16,16},
					{0,10,12,12,16,16,16,16,16,16,16},
					{0,12,12,14,14,15,15,15,17,17,17}
				} ;		
				int K = G.length, S = G[0].length - 1;
				System.out.println("tableau des gain : g(k,s) = gain obtenu en livrant s à k");
				afficher(G);
				int[][][] MA = calculerMA(G);
				int[][] M = MA[0], A = MA[1];
				System.out.printf("gain total maximum : %d\n", M[K][S]);
				System.out.println("tableau M des gains maximum :");
				afficher(M); 
				System.out.println("une affectation optimale :");
				aro(M,A,G,K,S);
				System.out.println();
		}
		
		{	System.out.println("Exercice 4 : sous-ensemble de somme S");
			int[] X = new int[] {8, 6, 4, 2};
			int n = X.length;
			System.out.println("X = " + Arrays.toString(X)); 
			int sommeX = somme(X);
			for (int S = 0; S < sommeX + 2; S++){
				boolean[][] E = calculerE(X,S);
				if (E[n][S]) {System.out.printf("sous-ensemble de somme %d : ", S);
					ase(E,X,n,S); System.out.println();System.out.println();
				} 
				else System.out.printf("il n'y a pas de sous-ensemble de somme %d\n\n", S);
			}
			System.out.println(); System.out.println();
		}
		
		{	System.out.println("Exercice 5 : sous-ensemble de somme S avec répétitions");
			int[] X = new int[] {8, 6, 4, 2};
			System.out.println("X = " + Arrays.toString(X));
			int sommeX = somme(X), n = X.length;
			for (int S = 0; S < sommeX + 5; S++){
				boolean[][] E = calculerEar(X,S); // ar : avec répétition
				if (E[n][S]) {System.out.printf("sous-ensemble de somme %d : ", S);
					asear(E,X,n,S); System.out.println();System.out.println(); // ar : idem.
				} 
				else System.out.printf("il n'y a pas de sous-ensemble de somme %d\n\n", S);
			}
			System.out.println();System.out.println();
		}
		
		{	System.out.println("Exercice 6 : le petit robot");
			int L = 5, C = 7; // grille 5 x 7
			System.out.printf("Grille à %d lignes et %d colonnes\n",L,C);
			int[][] M = calculerM(L,C);
			System.out.println("Tableau M des coûts minimum :");
			afficher(M);
			System.out.printf("Coût minimum d'un chemin de (0,0) à (%d,%d) = %d\n",
				L-1,C-1,M[L-1][C-1]);
			accm(M,L,C,L-1,C-1); // affichage d'un chemin de coût min de 0,0 à L-1,C-1.
			System.out.println();
		}	
	} // end main()
	/* fonctions annexe */
	static int max(int x, int y){ if (x >= y) return x; return y;}
	static int max(int x, int y, int z){ if (x >= max(y,z)) return x; 
		if (y >= z) return y; 
		return z;
	}	
	static int min(int x, int y){ if (x <= y) return x; return y;}
	static int min(int x, int y, int z){ if (x <= min(y,z)) return x; 
		if (y <= z) return y; 
		return z;
	}
	static int somme(int[] T){ int n = T.length;
		int s = 0; 
		for (int i = 0; i < n; i++) s = s + T[i];
		return s;
	}
	static void afficher(int[][] T){int n = T.length;
		for (int i = n-1; i >= 0; i--)
			System.out.println(Arrays.toString(T[i]));
	}
} // end classe

/*

% javac TD5_2022.java
% java TD5_2022      
Exercice 1 : spots & slots
durées des spots : [20, 20, 70, 10, 10, 40, 10, 80, 10, 40, 45]
gains des spots  : [25, 25, 65, 15, 5, 35, 15, 75, 15, 45, 50]
durée du slot : 100
gain maximum du slot : 125
sous-ensemble de spots de gain maximum :
Spot 0 : durée : 20, gain : 25
Spot 1 : durée : 20, gain : 25
Spot 3 : durée : 10, gain : 15
Spot 6 : durée : 10, gain : 15
Spot 9 : durée : 40, gain : 45


Exercice 2 : deux sacs
Tailles des objets: [20, 20, 70, 10, 10, 40, 10, 80, 10, 40, 45]
Valeurs des objets  : [25, 25, 65, 15, 5, 35, 15, 75, 15, 45, 50]
Contenances des sacs : C0 = 25, C1 = 75
gain maximum des sacs : 120
sous-ensemble d'objets de gain toal maximum :
Sac 1 : objet 0, valeur 25, taille 20
Sac 1 : objet 3, valeur 15, taille 10
Sac 0 : objet 6, valeur 15, taille 10
Sac 0 : objet 8, valeur 15, taille 10
Sac 1 : objet 10, valeur 50, taille 45


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

Exercice 4 : sous-ensemble de somme S
X = [8, 6, 4, 2]
sous-ensemble de somme 0 : 

il n'y a pas de sous-ensemble de somme 1

sous-ensemble de somme 2 : X[3]=2 

il n'y a pas de sous-ensemble de somme 3

sous-ensemble de somme 4 : X[2]=4 

il n'y a pas de sous-ensemble de somme 5

sous-ensemble de somme 6 : X[1]=6 

il n'y a pas de sous-ensemble de somme 7

sous-ensemble de somme 8 : X[0]=8 

il n'y a pas de sous-ensemble de somme 9

sous-ensemble de somme 10 : X[1]=6 X[2]=4 

il n'y a pas de sous-ensemble de somme 11

sous-ensemble de somme 12 : X[0]=8 X[2]=4 

il n'y a pas de sous-ensemble de somme 13

sous-ensemble de somme 14 : X[0]=8 X[1]=6 

il n'y a pas de sous-ensemble de somme 15

sous-ensemble de somme 16 : X[0]=8 X[1]=6 X[3]=2 

il n'y a pas de sous-ensemble de somme 17

sous-ensemble de somme 18 : X[0]=8 X[1]=6 X[2]=4 

il n'y a pas de sous-ensemble de somme 19

sous-ensemble de somme 20 : X[0]=8 X[1]=6 X[2]=4 X[3]=2 

il n'y a pas de sous-ensemble de somme 21



Exercice 5 : sous-ensemble de somme S avec répétitions
X = [8, 6, 4, 2]
sous-ensemble de somme 0 : 

il n'y a pas de sous-ensemble de somme 1

sous-ensemble de somme 2 : X[3]=2 

il n'y a pas de sous-ensemble de somme 3

sous-ensemble de somme 4 : X[2]=4 

il n'y a pas de sous-ensemble de somme 5

sous-ensemble de somme 6 : X[1]=6 

il n'y a pas de sous-ensemble de somme 7

sous-ensemble de somme 8 : X[0]=8 

il n'y a pas de sous-ensemble de somme 9

sous-ensemble de somme 10 : X[1]=6 X[2]=4 

il n'y a pas de sous-ensemble de somme 11

sous-ensemble de somme 12 : X[1]=6 X[1]=6 

il n'y a pas de sous-ensemble de somme 13

sous-ensemble de somme 14 : X[0]=8 X[1]=6 

il n'y a pas de sous-ensemble de somme 15

sous-ensemble de somme 16 : X[0]=8 X[0]=8 

il n'y a pas de sous-ensemble de somme 17

sous-ensemble de somme 18 : X[1]=6 X[1]=6 X[1]=6 

il n'y a pas de sous-ensemble de somme 19

sous-ensemble de somme 20 : X[0]=8 X[1]=6 X[1]=6 

il n'y a pas de sous-ensemble de somme 21

sous-ensemble de somme 22 : X[0]=8 X[0]=8 X[1]=6 

il n'y a pas de sous-ensemble de somme 23

sous-ensemble de somme 24 : X[0]=8 X[0]=8 X[0]=8 



Exercice 6 : le petit robot
Grille à 5 lignes et 7 colonnes
Tableau M des coûts minimum :
[1, 1, 1, 1, 1, 1, 1]
[1, 2, 2, 2, 3, 4, 5]
[1, 1, 1, 2, 3, 4, 5]
[1, 0, 1, 2, 3, 4, 5]
[0, 1, 2, 3, 4, 5, 6]
Coût minimum d'un chemin de (0,0) à (4,6) = 1
(0,0) -1-> (1,0) -0-> (2,0) -0-> (3,0) -0-> (4,0) -0-> (4,1) -0-> (4,2) -0-> (4,3) -0-> (4,4) -0-> (4,5) -0-> (4,6)
% 


*/

