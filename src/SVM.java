import java.util.Arrays;
import java.util.Random;


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
		int C = 3;
		System.out.printf("C = %d\n",C);

        // Méthode Dynamique
		System.out.println("\n\nMETHODE DYNAMIQUE\n");
		int[][] M = calculerM(V,T,C); 
		System.out.println("M = "); afficher(M); 
		System.out.printf("Valeur des sacs de valeur maximum = M[%d][%d] = %d\n",
			n, C, M[n][C]);
		System.out.print("Contenu d'un tel sac :\n");
		// afficher un sac de contenance C, de valeur max, contenant un sous-ensemble
		// des n objets
		asm(M,V,T,n,C); 
		System.out.println();

        // Méthodes Greedy
		System.out.println("\nMETHODE GREEDY : Valeur\n");
		System.out.printf("Taille du sac : %d\n", C);
		System.out.printf("Contenu du sac :\n", C);
		contenuGreedyValeur(V, T, C);
		System.out.println("\nMETHODE GREEDY : Densité\n");
		System.out.printf("Taille du sac : %d\n", C);
		System.out.printf("Contenu du sac :\n", C);
		contenuGreedyDensite(V, T, C);

        System.out.println();
    }
	

    //
	/* Methodes Dynamiques */
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
	/* Methodes Greedy */
	//

	/**
	 * contenuGreedyValeur permettant de placer les objets dans l’ordre des valeurs décroissantes dans un sac de contenance maximum
	 * @param V tableau des valeurs des objets
	 * @param T tableau des tailles des objets
	 * @param C capacité du sac
	 */
	static void contenuGreedyValeur(int[] V, int[] T, int C) {
		int[] valeurTab = Arrays.copyOf(V, V.length); // tableau copiant le tableau déjà existant des valeurs des objets
		qsInt(valeurTab, 0, valeurTab.length); // tri du nouveau tableau des valeurs des objets par ordre décroissant
		int tailleSac = 0; // taille actuelle du sac au fur et à mesure où les objets sont ajoutés dedans
		int valeurSac = 0; // valeur actuelle du sac au fur et à mesure où les objets sont ajoutés dedans
		for(int i=0; i<valeurTab.length; i++) { // mise en place de l'ajout des objets dans le sac en fonction de leur valeur
			int objet = 0; // indice faisant correspondre la taille de l'objet dans le tableau T avec sa valeur dans le tableau valeurTab
			int taille = somme(T); // taille comparative d'objets
			for(int j=0; j<V.length; j++){ // parcours du tableau V afin de retrouver l'indice initial de l'objet ayant comme valeur valeurTab[i]
				if(valeurTab[i] == V[j] && T[j] < taille) { // indice initial de l'objet trouvé
					objet = j;
					taille = T[j];
				}
			}
			if(T[objet] <= (C-tailleSac)) { // taille de l'objet <= place restante dans le sac
				tailleSac += T[objet]; // augmentation de la taille su sac avec le nouvel objet ajouté
				valeurSac += valeurTab[i]; // augmentation de la valeur su sac avec le nouvel objet ajouté
				System.out.printf(". objet %d : valeur = %d, taille = %d\n", objet, valeurTab[i], T[objet]);
			}
		}
		System.out.printf("--> Valeur totale du sac : %d\n", valeurSac);
	}//contenuGreedyValeur()

	/**
	 * contenuGreedyDensite permettant de mettre les objets par ratios “valeur/taille” décroissants dans un sac de contenance maximum
	 * @param V tableau des valeurs des objets
	 * @param T tableau des tailles des objets
	 * @param C capacité du sac
	 */
	static void contenuGreedyDensite(int[] V, int[] T, int C) {
		float[] ratioTab = new float[V.length]; // tableau permettant de contenir les ratio valeur/taille des objets
		for(int i=0; i<ratioTab.length; i++) { // attribution des valeurs du tableau
			if(T[i] == 0) ratioTab[i] = (float) V[i]; // cas taille nulle
			else ratioTab[i] = (float) V[i]/T[i]; // calcul du ratio valeur/taille
		}
		qsFloat(ratioTab, 0, ratioTab.length); // tri du nouveau tableau des ratio valeur/taille des objets par ordre décroissant
		int tailleSac = 0; // taille actuelle du sac au fur et à mesure où les objets sont ajoutés dedans
		int valeurSac = 0; // valeur actuelle du sac au fur et à mesure où les objets sont ajoutés dedans
		for(int i=0; i<ratioTab.length; i++) { // mise en place de l'ajout des objets dans le sac en fonction de leur ratio valeur/taille
			int objet = 0; // indice faisant correspondre la taille et la valeur de l'objet dans les tableaux T et V avec son ratio valeur/taille dans le tableau ratioTab
			int valeur = 0; // valeur comparative d'objets
			for(int j=0; j<V.length; j++){ // parcours du tableau V afin de retrouver l'indice initial de l'objet ayant comme valeur valeurTab[i]
				if(T[j] != 0) { // cas génaral
					if(ratioTab[i] == ((float) V[j]/T[j]) && valeur < V[j]) { // indice initial de l'objet trouvé
						objet = j;
						valeur = V[j];
					}
				}
				else { // cas taille nulle
					if(ratioTab[i] == (float) V[j]) { // indice initial de l'objet trouvé
						objet = j;
					}
				}
			}
			if(T[objet] <= (C-tailleSac)) { // taille de l'objet <= place restante dans le sac
				tailleSac += T[objet]; // augmentation de la taille su sac avec le nouvel objet ajouté
				valeurSac += V[objet]; // augmentation de la valeur su sac avec le nouvel objet ajouté
				System.out.printf(". objet %d : ratio = %s, valeur = %d, taille = %d\n", objet, ratioTab[i], V[objet], T[objet]);
			}
			V[objet] = 0;
		}
		System.out.printf("--> Valeur totale du sac : %d\n", valeurSac);
	}//contenuGreedyDensite()


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

	static void qsInt(int[] T, int i, int j){ 
		if (j-i <= 1) return ; // le sous-tableau T[i:j] est décroissant
		// ici : j-i >= 2
		int k = segmenterInt(T,i,j); // T[i:k] >= T[k] > T[k+1:j]    <<< (1)
		qsInt(T,i,k);   // (1) et T[i:k] décroissant 
		qsInt(T,k+1,j); // (1) et T[i:k] décroissant et T[k+1:j] décroissant, donc T[i:j] décroissant
	}//qsInt()

	static Random randInt= new Random();

	static int segmenterInt(int[] T, int i, int j){
	/* Calcule une permutation des valeurs de T[i:j] vérifiant T[i:k] >= T[k] > T[k+1:j], et retourne k
	Fonction construite sur la propriété I(k,j') : T[i:k] >= T[k] > T[k+1:j']
	Arrêt j'=j
	Initialisation : k = i, j'=k+1
	Progression : I(k,j') et j'<j et t_{j'}>t_{k} ==> I(k,j'+1)
				  I(k,j') et j'<j et t_{j'}<=t_{k} et T[k]=t_{j'} et T[k+1]=t_{k} et T[j']=t_{k+1} ==> I(k+1,j'+1)
	*/
		int r = i + randInt.nextInt(j-i);
		permuterInt(T,i,r);
		int k = i, jp = k+1; // I(k,j')
		while (jp < j) // I(k,j') et jp < j 
			if (T[k] > T[jp]) // I(k,j'+1)
				jp++; // I(k,j')
			else {
				permuterInt(T,jp,k+1);
				permuterInt(T,k,k+1); // I(k+1,j'+1)
				k++; jp++; // I(k,jp)
			}
		return k;				
	}//segmenterInt()

	static void permuterInt(int[] T, int i, int j){
		int ti = T[i];
		T[i] = T[j];
		T[j] = ti;
	}//permuterInt()

	static void qsFloat(float[] T, int i, int j){ 
		if (j-i <= 1) return ; // le sous-tableau T[i:j] est décroissant
		// ici : j-i >= 2
		int k = segmenterFloat(T,i,j); // T[i:k] >= T[k] > T[k+1:j]    <<< (1)
		qsFloat(T,i,k);   // (1) et T[i:k] décroissant 
		qsFloat(T,k+1,j); // (1) et T[i:k] décroissant et T[k+1:j] décroissant, donc T[i:j] décroissant
	}//qsFloat()

	static Random randFloat = new Random();

	static int segmenterFloat(float[] T, int i, int j){
	/* Calcule une permutation des valeurs de T[i:j] vérifiant T[i:k] >= T[k] > T[k+1:j], et retourne k
	Fonction construite sur la propriété I(k,j') : T[i:k] >= T[k] > T[k+1:j']
	Arrêt j'=j
	Initialisation : k = i, j'=k+1
	Progression : I(k,j') et j'<j et t_{j'}>t_{k} ==> I(k,j'+1)
				  I(k,j') et j'<j et t_{j'}<=t_{k} et T[k]=t_{j'} et T[k+1]=t_{k} et T[j']=t_{k+1} ==> I(k+1,j'+1)
	*/
		int r = i + randFloat.nextInt(j-i);
		permuterFloat(T,i,r);
		int k = i, jp = k+1; // I(k,j')
		while (jp < j) // I(k,j') et jp < j 
			if (T[k] > T[jp]) // I(k,j'+1)
				jp++; // I(k,j')
			else {
				permuterFloat(T,jp,k+1);
				permuterFloat(T,k,k+1); // I(k+1,j'+1)
				k++; jp++; // I(k,jp)
			}
		return k;				
	}//segmenterFloat()

	static void permuterFloat(float[] T, int i, int j){
		float ti = T[i];
		T[i] = T[j];
		T[j] = ti;
	}//permuterFloat()



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