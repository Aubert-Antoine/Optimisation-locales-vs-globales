import java.io.IOException;
import java.util.Arrays;


public class Robot{ 

	
	static final int plusInfini = Integer.MAX_VALUE, moinsInfini = Integer.MIN_VALUE;

	static Boolean debug = true;
	private static final boolean info = false;

	/**
	 * mainRobot appelant les méthodes dynamique et greedy et affichant leurs résultats
	 * @throws IOException
	 */
	public static void mainRobot() throws IOException{
		System.out.println("\n\nExercice : le petit robot\n");

		/* ancienne version cas particulier : ne marche plus à cause de l'ajout aléatoire dans calculerM, accm et cheminGreedy */
		
		// int L = 5, C = 7; // grille 5 x 7
		// System.out.printf("Grille à %d lignes et %d colonnes",L,C);
		
		// // Méthode Dynamique
		// System.out.println("\n\nMETHODE DYNAMIQUE");
		// int[][] M = calculerM(L,C);
		// System.out.println("Tableau M des coûts minimum :");
		// afficher(M);
		// System.out.printf("Coût minimum d'un chemin de (0,0) à (%d,%d) = %d\n",L-1,C-1,M[L-1][C-1]);
		// accm(M,L,C,L-1,C-1); // affichage d'un chemin de coût min de 0,0 à L-1,C-1
		
		// // Méthode Greedy
		// System.out.println("\n\nMETHODE GREEDY");
		// cheminGreedy(L, C); // affichage d'un chemin de coût min de 0,0 à L-1,C-1
		
		// System.out.println();


		/* Runs */
        System.out.println("Evaluation statistique de Robot :\n");
        double[] out = EvalStatRobot(5000, 100);
        //System.out.println("out : " + Arrays.toString(out)+"\n");

        System.out.println("medianne = "+EvalStat.mediane(out));
        System.out.println("moyenne = "+EvalStat.moyenne(out));
        System.out.println("ecart type = "+EvalStat.ecartType(out));

        EcrireValeursGaussiennesDansFichier.EcrireGdansF(out, "Robot.csv");

        System.out.println("\n\nFIN de ROBOT\n\n\n");

	}//mainRobot()
	

	/**
     * EvalStatRobot genere les runs et stocke la distance relative entre les solutions goulonne et dynamique
     * @param pNruns le nombre de runs de l’evaluation statistique
     * @param pVmax la plus grande valeur pour les lignes et colonnes de la grille
     * @return D[0 : N runs] qui contiendra pour chaque runla distance relative entre la valeur du chemin de somme maximum et la valeur du chemin glouton.
     */
	public static double[] EvalStatRobot(int pNruns, int pVmax) {
        double[] D = new double[pNruns];

        System.out.println("Les param sont : pNruns = "+pNruns+"  pVmax = "+pVmax+"\n");

        if(pNruns <= 0 || pVmax <= 0){
            System.out.println("\nLes param doivent etre positifs\n!!!!!!!!!!!!!!!!!!!!!\n");
            D[0] = -1;
            return D;
        }

        for (int r = 0; r < D.length; r++) {
			int L = RandomGen.randomInt(3, pVmax); // nombre de lignes à 3 min car sinon problème pour la taille du tableau
			int C = RandomGen.randomInt(3, pVmax); // nombre de colonnes à 3 min car sinon problème pour la taille du tableau
			
			int[][] N = new int[L][C]; // grille direction Nord LxC
			int[][] NE = new int[L][C]; // grille direction Nord-Est LxC
			int[][] E = new int[L][C]; // grille direction Est LxC
			// Attribution des coûts de chaque déplacement
			for(int l=0; l<L; l++){
				for(int c=0; c<C; c++) {
					N[l][c] = n(l, c, L, C);
					NE[l][c] = ne(l, c, L, C);
					E[l][c] = e(l, c, L, C);
				}
			}
            

            if(info) {
                System.out.println("Runs numero : "+r);
				System.out.println("Le nombre de lignes de la grille random : L = "+L);
				System.out.println("Le nombre de colonnes de la grille random : C = "+C);
                System.out.println("Le tableau déplacement Nord random : N = "+Arrays.toString(N));
				System.out.println("Le tableau déplacement Nord-Est random : NE = "+Arrays.toString(NE));
				System.out.println("Le tableau déplacement Est random : E = "+Arrays.toString(E));
                System.out.println("La valeur dynamique : calculerM(L, C, N, NE, E)[L-1][C-1] = "+calculerM(L, C, N, NE, E)[L-1][C-1]);
                System.out.println("La valeur gloutonne : cheminGreedy(L, C, N, NE, E) = "+cheminGreedy(L, C, N, NE, E)+"\n");
			}

           
            
            D[r] = EvalStat.evalMin(calculerM(L, C, N, NE, E)[L-1][C-1], cheminGreedy(L, C, N, NE, E));
            //On regarde la valeur m(0) qui est le min, on fait le ration puis on attribut le ratio
            // a D[r], on fait cela Nruns fois
        }
        if(info) System.out.println("SVM > EvalStatSVM : D = "+Arrays.toString(D));
        return D;
    }//EvalStatSVM()

	
	//
	/* Methode Dynamique */
	//

	static int[][] calculerM(int L, int C, int[][] N, int[][] NE, int[][] E){ // grille LxC
		int[][] M = new int[L][C]; // de terme général M[l][c] = m(l,c)
		
		// Base 
		M[0][0] = 0;
		for(int c=1; c<C; c++) M[0][c] = M[0][c-1]+E[0][c-1]; // calcul du coût de la 1ère ligne
		for(int l=1; l<L; l++) M[l][0] = M[l-1][0]+N[l-1][0]; // calcul du coût de la 1ère colonne
		
		// Cas général
		for (int l = 1; l < L; l++) {
			for (int c = 1; c < C; c++) {
				M[l][c] = min(
					M[l][c-1] + E[l][c-1],
					M[l-1][c-1] + NE[l-1][c-1],
					M[l-1][c] + N[l-1][c]);
			}
		}

		return M;
	}//calculerM()

	static void accm(int[][] M, int L, int C, int l, int c, int[][] N, int[][] NE, int[][] E){
	// affiche un chemin de coût minimimum (ccm) de 0,0 à l,c
		
		// Base
		if (l==0 & c==0) {
			if(info) System.out.print("(0,0)");
			return;
		}

		// Cas général
		else if (l==0) { // dernier mouvement : Est depuis 0,c-1
			accm(M,L,C,l,c-1,N,NE,E);
			if(info) System.out.printf(" -%d-> (%d,%d)", E[l][c-1],l,c);
		}
		else if (c==0) { // dernier mouvement : Nord depuis l-1,0
			accm(M,L,C,l-1,c,N,NE,E);
			if(info) System.out.printf(" -%d-> (%d,%d)", N[l-1][c],l,c);
		}
		else { // l,c > 0,0
			// Coûts du pas de déplacement conduisant jusqu'en (l,c)
			int n=N[l-1][c], ne=NE[l-1][c-1], e=E[l][c-1];
			// Coûts du chemin jusqu'en (l,c) selon le dernier pas de déplacement
			int Mn = M[l-1][c] + n, Mne = M[l-1][c-1] + ne, Me = M[l][c-1] + e;
			if (Mn == min(Mn,Me,Mne)) { // dernier mouvement : Nord
				accm(M,L,C,l-1,c,N,NE,E);
				if(info) System.out.printf(" -%d-> (%d,%d)", N[l-1][c],l,c);
			}
			else if (Mne == min(Mn,Me,Mne)) { // dernier mouvement : Nord Est
				accm(M,L,C,l-1,c-1,N,NE,E);
				if(info) System.out.printf(" -%d-> (%d,%d)", NE[l-1][c-1],l,c);
			}
			else if (Me == min(Mn,Me,Mne)) { // dernier mouvement : Est
				accm(M,L,C,l,c-1,N,NE,E);
				if(info) System.out.printf(" -%d-> (%d,%d)", E[l][c-1],l,c);
			}
		}
	}//accm()


	//
	/* Methode Greedy */
	//
	
	/**
	 * cheminGreedy permettant de calculer le chemin du robot à plus faible coût avec la méthode gloutonne
	 * @param L nombre de lignes de la grille
	 * @param C nombre de colonnes de la grille
	 * @param N tableau des directions Nord
	 * @param NE tableau des directions Nord-Est
	 * @param E tableau des directions Est
	 * @return coût du chemin emprunté
	 */
	static int cheminGreedy(int L, int C, int[][] N, int[][] NE, int[][] E){ // une grille L x C
		int cout = 0; // coût de départ
        int l = 0; // départ de la ligne 0
        int c = 0; // départ de la colonne 0
        if(info) System.out.print("(0,0)");
        while(l < L && c < C) { // tant que le robot est dans la grille
            int dir = 0; // coût de la direction choisie
            if(l < L-1 && c < C-1 && NE[l][c] <= N[l][c] && NE[l][c] <= E[l][c]) { // direction faisable et favorable : Nord-Est
                dir = NE[l][c];
                l++; c++;
            }
            else if(l < L-1 && N[l][c] <= E[l][c]) { // direction faisable et favorable : Nord
                dir = N[l][c];
                l++;
            }
            else if(c < C-1) { // direction faisable et favorable : Est
                dir = E[l][c];
                c++;
            }
			else{ // destination finale atteinte
				if(info) System.out.printf("\nCoût minimum d'un chemin de (0,0) à (%d,%d) = %d\n", L-1, C-1, cout);
				return cout;
			}
            cout += dir; // augmentation du coût du chemin emprunté
            if(info) System.out.printf(" --%d--> (%d,%d)",dir,l,c);
        }
		return -1;
	}//cheminGreedy()


	/* Fonctions de coût des déplacements*/ 
	static int n(int l, int c, int L, int C){ // coût d'un déplacement Nord
		if (l==L-1) return plusInfini;
		return RandomGen.randomInt(0, 15);
	}//n()
	static int ne(int l, int c, int L, int C){ // coût d'un déplacement Nord-Est
		if (l == L-1 || c == C-1) return plusInfini;
		return RandomGen.randomInt(0, 15);
	}//ne()
	static int e(int l, int c, int L, int C){ // coût d'un déplacement Est
		if (c == C-1) return plusInfini;
		return RandomGen.randomInt(0, 15);
	}//e()


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


}//Robot


/*

% javac TD5_2022.java
% java TD5_2022      

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

*/