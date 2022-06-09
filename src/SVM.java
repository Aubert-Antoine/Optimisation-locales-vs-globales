import java.util.Arrays;


public class SVM{


    /**
     * mainSVM appelant les méthodes dynamique et greedy et affichant leurs résultats
     */
    public static void mainSVM(){
        System.out.println("\n\nExercice : sac de valeur maximum");
        int[] V = {2,1,3,8,4}; 
		int[] T = {1,1,2,4,3}; 
		int n = V.length;
		System.out.println("V = " + Arrays.toString(V));
		System.out.println("T = " + Arrays.toString(T));

        // Méthode Dynamique
		System.out.println("\n\nMETHODE DYNAMIQUE\n");
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
			System.out.printf("\nSacs de valeur maximum de contenance C, 0 <= C < %d + 1\n\n", somme(T));
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

        // Méthode Greedy
		System.out.println("\nMETHODE GREEDY");

        System.out.println();
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
	// Affichage d'un sac svm(k,c), sac de valeur maximum, de contenance c, contenant un  un sous-ensemble de [0:k]
    // Appel principal : asm(M,V,T,n,C)
		if (k == 0) // svm(0,c) est vide : sans rien faire, il a été affiché
			return; // svm(0,c) a été affiché
		// Ici : k > 0
		if (M[k][c] == M[k-1][c]) // le k-ème objet n'est pas dans svm(k,c) donc svm(k,c) = svm(k-1,c) 
			asm(M, V, T, k-1, c) ; // svm(k-1,c) a été affiché, donc svm(k,c) a été affiché
		else { // le k-ème objet est dans le sac donc svm(k,c) = svm(k-1,c-t(k-1)) union {k-1}
			asm(M,V,T,k-1,c-T[k-1]); // svm(k-1,c-t(k-1)) a été affiché
			System.out.printf("objet, valeur, taille = %d, %d, %d\n", k-1, V[k-1], T[k-1]); // Le k-ème objet a été affiché
			// svm(k-1,c-t(k-1)) union {k-1} a été affiché, donc svm(k,c) a été affiché
		}
	}//asm(=)


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


}//SVM


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

Sacs de valeur maximum de contenance C, 0 <= C < 11 + 1
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