    /* Sac de valeur maximum -- rene.natowicz@esiee.fr -- 15/03/2022
-- Un ensemble de n objets, [0:n].
L'objet i est de valeur v_i et de taille t_i (v indice i et t indice i).
La numÃ©rotation des objets est quelconque.
Les valeurs et tailles sont dans les tableaux V[0:n] et T[0:n].
-- Un sac de contenance C.

ProblÃ¨me : calculer un sous-ensemble d'objets 
	1) de taille infÃ©rieure ou Ã©gale Ã  la contenance C du sac et 
	2) de valeur maximum.
La taille et la valeur d'un sous-ensemble d'objets est la somme des tailles 
et des valeurs de ses Ã©lÃ©ments.

Notation : m(n,C) est la valeur maximum d'un sac de contenance C, contenant un 
sous-ensemble des n objets (c'est-Ã -dire un sous-ensemble de [0:n])

Supposons le problÃ¨me rÃ©solu. 
Quelle est la derniÃ¨re Ã©tape de sa rÃ©solution ?
Il n'y a que 2 cas possibles : 
	a) le n-Ã¨me objet n'est pas dans le sac.
		Alors : m(n,C) = m(n-1,C). 
		En effet : le sac de contenance C et de valeur maximum contient un sous-ensemble  
		des n-1 premiers objets.
	b) il est dans le sac. 
		Alors : m(n,C) = v_{n-1} + m(n-1,C-t_{n-1}). 
		En effet : le n-Ã¨me objet est de taille t_{n-1}. Le reste du sac est de contenance 
		C-t_{n-1}. Il contient un sous-ensemble des n-1 premiers objets.
		Sa valeur est maximum, sinon le sac de contenance C contenant un sous-ensemble
		de [0:n] ne serait pas de valeur maximum.
		Formellement : supposons que le reste du sac est de valeur m' < m(n-1,C-t_{n-1}). 
		Alors la valeur du sac de contenance C est v_{n-1} + m' < v_{n-1} + m(n,C). 
		Donc le sac de contenance C contenant un sous-ensemble de [0:n] n'est pas de valeur 
		maximum. Or, par hypothÃ¨se, il est de valeur maximum.

Donc : m(n,C) = max(m(n-1,C), v_{n-1} + m(n-1,C-t_{n-1})).
La valeur m(n,C) s'exprime en fonction des deux sous-problÃ¨mes m(n-1,C) et m(n-1,C-t_{n-1}). 

GÃ©nÃ©ralisation : m(k,c) est la valeur maximum d'un sac de contenance c, son contenu est
un sous-ensemble d'objets [0:k].
-- Cas de base : un sac contenant un sous-ensemble des 0 premiers objets 
(un sous-ensemble de [0:0] = Ã˜). Il n'y a qu'un sous-ensemble de l'ensemble vide : 
l'ensemble vide lui-mÃªme. Donc : pour toute contenance c, 0 â‰¤ c < C+1, m(0,c) = 0.
-- Cas gÃ©nÃ©ral : pour tous k et c, 1 â‰¤ k < n+1  et  0 â‰¤ c < C+1
	m(k,c) = max( m(k-1,c), v_{k-1} + m(k-1, c-t_{k-1} ).

La valeur m(k,c) est calculable si les valeurs m(k-1,c) et m(k-1,c-t_{k-1}) ont dÃ©jÃ 
Ã©tÃ© calculÃ©es. Pour s'assurer que ces deux valeurs sont effectivement dÃ©jÃ  calculÃ©es 
lorsque la valeur m(k,c) doit Ãªtre calculÃ©e, nous calculerons toutes les valeurs m(0,c),
puis toutes les valeurs m(1,c), etc. et nous mÃ©moriserons ces valeurs dans un tableau
M[0:n+1][0:C+1] de terme gÃ©nÃ©ral M[k][c] = m(k,c). 
Ã€ la fin de tous ces calculs, nous la valeur m(n,C) sera M[n][C].

De plus, dans un second temps, les valeurs du tableau M nous permettrons d'afficher le 
contenu d'un sac de valeur maximum m(n,C).

Affichage du contenu d'un sac de valeur m(n,C).
Soit svm(n,C) le contenu du sac de valeur maximum. Il s'agit d'un sous-ensemble d'objets.
Sa valeur est m(n,C). 
Le n-Ã¨me objet est-il dans le sac ? 
	a) Si m(n,C) = m(n-1,C), il n'y est pas. 
	Afficher le sac svm(n-1,C) c'est afficher le sac svm(n,C).
	b) Si m(n,C) â‰  m(n-1,C), il y est. (Dans ce cas, m(n,c) = v_{n-1} + m(n-1,c-t_{n-1})).
	Alors, svm(n,C) = svm(n-1,C-t_{n-1}) union {n-1}.
	Afficher svm(n-1,C-t_{n-1}) puis afficher {n-1} c'est afficher svm(n,C).

GÃ©nÃ©ralisons : soit svm(k,c) le sac Ã  afficher. 
	Base de la rÃ©currence : k = 0. Le sac est vide. Sans rien faire il a Ã©tÃ© affichÃ©.
	Cas gÃ©nÃ©ral : 1 â‰¤ k < n+1. 
		a) m(k,c) = m(k-1,c) : si svm(k-1,c) a Ã©tÃ© affichÃ©, alors svm(k,c) a Ã©tÃ© affichÃ©.
		b) m(k,c) â‰  m(k-1,c) : si svm(k-1,c-t_{k-1}) a Ã©tÃ© affichÃ© puis {k-1} a Ã©tÃ© affichÃ©,
			alors svm(k,c) a Ã©tÃ© affichÃ©.
*/

import java.util.Arrays;

public class SVM{


    /**
     * mainSVM appelant les méthodes dynamique et greedy et affichant leurs résultats
     */
    public static void mainSVM(String[] Args){
        System.out.println("\n\nExercice : sac de valeur maximum");
        int[] V = {2,1,3,8,4}; 
		int[] T = {1,1,2,4,3}; 
		int n = V.length;
		System.out.println("V = " + Arrays.toString(V));
		System.out.println("T = " + Arrays.toString(T));
		{
			int C = 3; System.out.printf("C = %d\n",C);
			int[][] M = calculerM(V,T,C); 
			System.out.println("M = "); afficher(M); 
			System.out.printf("Valeur des sacs de valeur maximum = M[%d][%d] = %d\n",
				n, C, M[n][C]);
			System.out.print("Contenu d'un tel sac :\n");
			// afficher un sac de contenance C, de valeur max, contenant un sous-ensemble
			// des n objets
			asm(M,V,T,n,C); 
			System.out.println();
		}
		{			
			System.out.println("V = " + Arrays.toString(V));
			System.out.println("T = " + Arrays.toString(T));
			System.out.println("somme(V) = " + somme(V));
			System.out.println("somme(T) = " + somme(T));
			System.out.printf("\nSacs de valeur maximum de contenance C, 0 â‰¤ C < %d + 1\n",
				somme(T));
			for (int C = 0; C < somme(T)+1; C++){
				System.out.printf("Sac de contenance %d\n",C);
				int[][] M = calculerM(V,T,C); 
				System.out.printf("Valeur des sacs de valeur maximum : %d\n",M[n][C]);
				System.out.print("Contenu d'un tel sac :\n");
				// afficher un sac de contenance C, de valeur max, contenant un 
				// sous-ensemble des n objets
				asm(M,V,T,n,C); 
				System.out.println();
			}
		}

        // Méthode Dynamique
		System.out.println("\n\nMETHODE DYNAMIQUE");

        // Méthode Greedy
		System.out.println("\n\nMETHODE GREEDY");
    }
	

    //
	/* Methode Dynamique */
	//
	
	static int[][] calculerM(int[] V, int[] T, int C){int n = V.length;
	// Retourne M[0:n+1][0:C+1], de terme général M[k][c] = m(k,c)
		int[][] M = new int[n+1][C+1];

		// Base : m(0,c) = 0 pour toute contenance c, 0 <= c < C+1
		for (int c = 0; c < C+1; c++) M[0][c] = 0;

		// Cas général, pour tous k et c, 1 <= k < n+1, 0 <= c < C+1,
		// m(k,c) = max(M[k-1][c], V[k-1] + M[k-1][c-T[k-1]])
		for (int k = 1; k < n+1; k++)
			for (int c = 0; c < C+1; c++) // calcul et mémorisation de m(k,c)
				if (c-T[k-1] < 0) // le k-ème objet est trop gros pour entrer dans le sac 
					M[k][c] = M[k-1][c];
				else  
					M[k][c] = max(M[k-1][c], V[k-1]+M[k-1][c-T[k-1]]);
		
        return M;
	}//calculerM()	
	
	static void asm(int[][] M, int[] V, int[] T, int k, int c){
	// affichage d'un sac svm(k,c), sac de valeur maximum, de contenance c, contenant un 
	// un sous-ensemble de [0:k]. Appel principal : asm(M,V,T,n,C).
		if (k == 0) // svm(0,c) est vide. Sans rien faire, il a Ã©tÃ© affichÃ©.
			return; // svm(0,c) a Ã©tÃ© affichÃ©.
		// ici : k > 0
		if (M[k][c] == M[k-1][c]) // le k-Ã¨me objet n'est pas dans svm(k,c), 
		// donc svm(k,c) = svm(k-1,c). 
			asm(M, V, T, k-1, c) ; // svm(k-1,c) a Ã©tÃ© affichÃ©, donc svm(k,c) a Ã©tÃ© affichÃ©
		else {// le k-Ã¨me objet est dans le sac. Donc svm(k,c) = svm(k-1,c-t(k-1)) union {k-1}
			asm(M,V,T,k-1,c-T[k-1]); // svm(k-1,c-t(k-1)) a Ã©tÃ© affichÃ©
			System.out.printf("objet, valeur, taille = %d, %d, %d\n",
				k-1, V[k-1], T[k-1]); // Le k-Ã¨me objet Ã©tÃ© affichÃ©
			// svm(k-1,c-t(k-1)) union {k-1} a Ã©tÃ© affichÃ©, donc svm(k,c) a Ã©tÃ© affichÃ©.
		}
	}


    //
	/* Methode Greedy */
	//


    /* Fonctions annexes */
    static void afficher(int[][] M){ int n = M.length; // affichage du tableau M
        System.out.println("\t[");
        for (int i = n-1; i>=0; i--) 
            System.out.println("\t\t" + Arrays.toString(M[i]));
        System.out.println("\t]");
    }//afficher()
   static int somme(int[] T){
        int s = 0; 
        for (int i = 0; i<T.length; i++) 
            s = s+T[i]; 
        return s;
    }//somme()
   static int max(int x, int y){ 
        if (x >= y) 
            return x; 
        return y; 
    }//max()


}//SVM()


/*

% javac SacValMax.java
% java SacValMax      
V = [2, 1, 3, 8, 4]
T = [1, 1, 2, 4, 3]
C = 3
M = 
	[
		[0, 2, 3, 5]
		[0, 2, 3, 5]
		[0, 2, 3, 5]
		[0, 2, 3, 3]
		[0, 2, 2, 2]
		[0, 0, 0, 0]
	]
Valeur des sacs de valeur maximum = M[5][3] = 5
Contenu d'un tel sac :
objet, valeur, taille = 0, 2, 1
objet, valeur, taille = 2, 3, 2

V = [2, 1, 3, 8, 4]
T = [1, 1, 2, 4, 3]
somme(V) = 18
somme(T) = 11

Sacs de valeur maximum de contenance C, 0 â‰¤ C < 11 + 1
Sac de contenance 0
Valeur des sacs de valeur maximum : 0
Contenu d'un tel sac :

Sac de contenance 1
Valeur des sacs de valeur maximum : 2
Contenu d'un tel sac :
objet, valeur, taille = 0, 2, 1

Sac de contenance 2
Valeur des sacs de valeur maximum : 3
Contenu d'un tel sac :
objet, valeur, taille = 0, 2, 1
objet, valeur, taille = 1, 1, 1

Sac de contenance 3
Valeur des sacs de valeur maximum : 5
Contenu d'un tel sac :
objet, valeur, taille = 0, 2, 1
objet, valeur, taille = 2, 3, 2

Sac de contenance 4
Valeur des sacs de valeur maximum : 8
Contenu d'un tel sac :
objet, valeur, taille = 3, 8, 4

Sac de contenance 5
Valeur des sacs de valeur maximum : 10
Contenu d'un tel sac :
objet, valeur, taille = 0, 2, 1
objet, valeur, taille = 3, 8, 4

Sac de contenance 6
Valeur des sacs de valeur maximum : 11
Contenu d'un tel sac :
objet, valeur, taille = 0, 2, 1
objet, valeur, taille = 1, 1, 1
objet, valeur, taille = 3, 8, 4

Sac de contenance 7
Valeur des sacs de valeur maximum : 13
Contenu d'un tel sac :
objet, valeur, taille = 0, 2, 1
objet, valeur, taille = 2, 3, 2
objet, valeur, taille = 3, 8, 4

Sac de contenance 8
Valeur des sacs de valeur maximum : 14
Contenu d'un tel sac :
objet, valeur, taille = 0, 2, 1
objet, valeur, taille = 1, 1, 1
objet, valeur, taille = 2, 3, 2
objet, valeur, taille = 3, 8, 4

Sac de contenance 9
Valeur des sacs de valeur maximum : 15
Contenu d'un tel sac :
objet, valeur, taille = 0, 2, 1
objet, valeur, taille = 1, 1, 1
objet, valeur, taille = 3, 8, 4
objet, valeur, taille = 4, 4, 3

Sac de contenance 10
Valeur des sacs de valeur maximum : 17
Contenu d'un tel sac :
objet, valeur, taille = 0, 2, 1
objet, valeur, taille = 2, 3, 2
objet, valeur, taille = 3, 8, 4
objet, valeur, taille = 4, 4, 3

Sac de contenance 11
Valeur des sacs de valeur maximum : 18
Contenu d'un tel sac :
objet, valeur, taille = 0, 2, 1
objet, valeur, taille = 1, 1, 1
objet, valeur, taille = 2, 3, 2
objet, valeur, taille = 3, 8, 4
objet, valeur, taille = 4, 4, 3

*/