// rene.natowicz@esiee.fr - 18/04/2022
import java.util.Random;
import java.util.Arrays;
import java.io.*;
class ArbresBinairesDeRecherche{
	static class AB{
		int r; 
		AB g, d;
		AB(int racine, AB gauche, AB droit){
			r = racine ; g = gauche ; d = droit;}
		}
	static AB abrglouton(int[] F){ int n = F.length;
	// retourne un abr construit par insertion des valeurs de V = [0:n] dans l'ordre des
	// fréquences décroissantes.
		// 1) trier V par ordre des fréquences décroissantes;
		int[][] FV = new int[n][2]; // FV de terme général FV[i] = [F[i], i]
		for (int i = 0; i < n; i++) {FV[i][0] = F[i]; FV[i][1] = i;}
		qs(FV,0,n); // FV est trié par fréquences croissantes 
		// 2) calculer dans V' une permutation de V triée par fréquences décroissantes
		renverser(FV); // FV est trié par fréquences décroissantes
		int[] Vprime = new int[n]; // V' sera V trié par fréquences croissantes
		for (int i = 0; i < n; i++) Vprime[i] = FV[i][1]; // V' = F[0:n][1]
		// 3) ajouter en séquence les valeurs de V' à un abr initialement vide.
		AB a = null;
		for (int i = 0; i < n; i++) a = ajout(Vprime[i],a);
		return a;
	}
	static AB abrhmin(int i, int j){ // retourne un abr de hauteur min construit sur [0:n]
		if (j-i <= 0) return null; // sous-ensemble vide, arbre vide.
		int k = (i+j)/2; // valeur médiane
		AB g = abrhmin(i,k); // abr de hauteur min construit sur [i:k]
		AB d = abrhmin(k+1,j); // idem sur [k+1:j]
		return new AB (k, g, d); // abr de hauteur min construit sur [i:j]
	}
	/* AB de coût moyen de recherche minimum (abrmin).
	Construction par programmation dynamique.
	Equation de récurrence du coût minimum m(i,j) :
		Base : les problèmes de taille t = j-i = 0.
			m(i,i) = 0  qqsoit i 0 ≤ i < n+1
		Cas général, 1 ≤ t = j-i < n+1.
			Puisque t  = j - i on a j = i + t.
			Ces problèmes de tailles t sont : m(0,0+t), m(1,1+t), ..., m(n-t,n-t+t=n)
			qqsoit 0 ≤ i < n-t+1 : 
			m(i,j) = ( min_{i ≤ k < j} m(i,k) + m(k+1,j) ) + (f_i+...+f_{j-1})
		Le programme s'en déduit. Les valeurs m(i,j), 0 ≤ i ≤ j < n+1 sont calculées
		par tailles t = j-i croissantes et mémorisées dans un tableau M[0:n+1][0:n+1].*/	
	static int[][] calculerM(int[] F){ int n = F.length;
	/* Calcule et retourne M[0:n+1][0:n+1] de terme général :
	-- 0 ≤ i ≤ j < n+1 : M[i][j] = m(i,j), coût min. d'un abr construit sur [i:j].
	Le demi-triangle supérieur, 0 ≤ j < i < n+1 est libre. On peut y mémoriser les
	arguments des valeurs m(i,j)
	-- 0 ≤ i < j < n+1 : M[j][i] = arg m(i,j) = valeur racine de l'abr de coût min 
	construit sur [i:j]. */
		int[][] M = new int[n+1][n+1]; // sera de tg M[i][j] = m(i,j) pour i ≤ j
				// et M[j][j] = arg m(i,j)
		// Base, t = j-i = 0 : 
		// for (int i = 0; i < n+1; i++) M[i][i] = 0; // inutile en Java car M = 0.
		// CG, 1 ≤ t < n+1 : calcul des valeurs m(i,j) par tailles t = j-i croissantes
		for (int t = 1; t < n+1; t++) 
			for (int i = 0; i < n-t+1; i++) { int j = i+t; M[i][j] = Integer.MAX_VALUE;
				for (int k = i; k < j; k++){ int mikj = M[i][k]+M[k+1][j]; 
					 if (mikj < M[i][j]){ M[i][j] = mikj; 
					 	M[j][i] = k; // arg m(i,j)
					 }
				
			}
			M[i][j] = M[i][j] + somme(F,i,j); 
		}
		return M;
	} // complexité Theta(n^3) (à comparer à Theta(4^n/n^1.5) de l'approche exhaustive.)
	static AB abrcmin(int[][] M, int i, int j){ 
	// Retourne un abrmin de valeurs [i:j] 
		if (j-i <= 0) return null; // [i:j] = Ø, l'abr construit sur Ø est vide.
		int kij = M[j][i] ; // arg min m(i,j) = valeur racine de l'abr construit sur [i:j]
		return new AB(kij, abrcmin(M,i,kij), abrcmin(M,kij+1,j));
	}
	static int cout(AB a, int[] F){ 
	// Retourne le coût de l'arbre a. 
		return cout(a,F,0); // coût de l'arbre a dont la racine est au niveau 0.
	}
	static int cout(AB a, int[] F, int l){ 
	// retourne le coût de l'arbre a dont la racine est au niveau l.
		if (vide(a)) return 0;
		int v = vr(a), f = F[v]; // f : fréquence d'occurence de la valeur v
		return f*(l+1) + cout(sag(a),F,l+1) + cout(sad(a),F,l+1);
	}
	static int somme(int[] F, int i, int j){int s = 0; 
		for (int k = i; k < j; k++) s = s+F[k]; 
		return s;
	}
	/* Fonctions annexes */
	static int t(AB a){ // retourne la taille de l'AB a : son nombre de valeurs
		if (vide(a)) return 0;
		return 1 + t(sag(a)) + t(sad(a));
	}
	static int h(AB a){ // retourne la hauteur de l'AB a : lg du plus long chemin dans a.
		if (vide(a)) return -1;
		return 1 + max(h(sag(a)), h(sad(a)));
	}
	static int max(int x, int y){if (x >= y) return x; return y;}
	static boolean vide(AB a){ return a == null; }
	static boolean feuille(AB a){ return vide(sag(a)) && vide(sad(a));}
	static int vr(AB a){ return a.r; }
	static AB sag(AB a){ return a.g; } // sous-arbre gauche
	static AB sad(AB a){ return a.d; } // sous-arbre droit
	static void grd(AB a){ // a est un abr. Affiche ses valeurs à l'endroit.
		if (vide(a)) return ; // les valeurs de l'abr vide ont été affichées à l'endroit.
		grd(sag(a)); // les valeurs de son sag ont été affichées à l'endroit
		System.out.print(vr(a)+ " "); // sa valeur racine a été affichée
		grd(sad(a)); // les valeurs de son sad ont été affichées à l'endroit
		// x in sag(a) ==> x < vr(a) et  x in sad(a) ==> x > vr(a) et
		// sag(a) affiché à l'endroit puis vr(a) affichée puis sad(a) affiché à l'endroit
		// ==> a affiché à l'endroit.
	}
	static void drg(AB a){ // a est un abr. Affiche ses valeurs à l'envers.
		if (vide(a)) return ; // les valeurs de l'abr vide ont été affichées à l'envers.
		drg(sad(a)); // les valeurs de son sad ont été affichées à l'envers.
		System.out.print(vr(a)+ " "); // sa valeur racine a été affichée
		drg(sag(a)); // les valeurs de son sag ont été affichées à l'envers
		// x in sag(a) ==> x < vr(a) et  x in sad(a) ==> x > vr(a) et
		// sad(a) affiché à l'envers puis vr(a) affichée puis sag(a) affiché à l'envers
		// ==> a affiché à l'envers.
	}
	static AB ajout(int x, AB a){ // a est un abr. Retourne l'abr "a auquel x a été ajouté."
		if (vide(a)) return new AB(x,null,null);
		if (x == vr(a)) return a; // arbre inchangé (pas de répétition de val. dans un abr.)
		if (x < vr(a)) // ajout dans le sag
			return new AB(vr(a), ajout(x, sag(a)), sad(a)); 
		// x > vr(a), ajout dans le sad
		return new AB(vr(a), sag(a), ajout(x, sad(a))); 
	}
	static AB in(int x, AB a){ // Retourne : si x est dans a, le sous-arbre 
	// dont il est racine, sinon l'arbre vide */
	   if (vide(a)) return a;
	   if (x == vr(a)) return a;
	   if (x < vr(a)) return in(x, sag(a));
	   return in(x, sad(a));
	}		
	static void qs(int[][] FV, int i, int j){// quick sort de FV. 
	// Rappel : V = [0:n]. FV[0:n] est de terme général FV[i] = [f_i, v_i]. 
	// Calcule une permutation de FV, croissante selon les valeurs f_i.
		if (j-i <= 1) return;
		int k = segmenter(FV, i, j);
		qs(FV,i,k); qs(FV,k+1,j);
	}
	static Random rand = new Random(); 	
	static int segmenter(int[][] FV, int i, int j){ int r = i + rand.nextInt(j-i);
		permuter(FV,i,r);
		// Invariant I(k,jp) : FV[0:k] ≤ FV[k] < FV[k+1:jp]. Voir cours.
		int k = i, jprime = k+1; // I(k,j') 
		int fk = FV[k][0];
		while (jprime < j)
			if (FV[jprime][0] > fk) jprime++; 
			else { 	permuter(FV,k+1,jprime); permuter(FV,k,k+1); 
				k++; jprime++; // I(k,j')
			} 
		 return k;
	}
	static void permuter(int[][] FV,int i, int j){ int[] fvi = FV[i]; 
		FV[i] = FV[j]; FV[j] = fvi;
	}
	static void renverser(int[][] FV){ int n = FV.length;
		/* I(k) : le k-préfixe de FV est le renversé du (n-k)suffixe de FV
		(le sous-tableau FV[k:n-k] n'est pas encore renversé.)
		Remarque : FV[k:n-k] non encore renversé est de longueur n-k-k = n-2k.
		Initialisation : k = 0
		Arrêt : le sous-tableau FV[k:n-k] qui reste à renverser est de longueur 0 ou 1
		(il est son propre renversé).
		Progression : I(k) et (n-2k > 1) et FV[k] permutée avec FV[n-k-1] ==> I(k+1) */
		int k = 0; // I(k)
		while (n-2*k > 1) { // I(k) et non arrêt
			permuter(FV,k,n-k-1); // I(k+1)
			k++; // I(k)
		}
		// I(k) et n-2k ≤ 1 ==> FV est renversé.
	}
	static void afficher(int[][] M){int n = M.length;
		for (int i = n-1; i > -1; i--) 
			System.out.println("i = " + i + " : " + Arrays.toString(M[i]));
	}
	static void descriptionGraphviz(AB a, int[] F, String name)
	// Ecrit une description de l'AB a pour GraphViz dans le fichier de nom "name"
	// voir https://graphviz.org    et    https://dreampuf.github.io/GraphvizOnline/
		throws IOException {
  			int t = t(a); // nombre de valeurs de l'arbre
  			name = name + "_" + t; // exemple : abrglouton_63;
  			String fileName = name + ".txt"; // exemple : abrglouton_63.txt
     		FileWriter fileWriter = new FileWriter(fileName);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.printf("// description ci-dessous à copier-coller dans\n");
			printWriter.printf("// la fenêtre gauche du site web GraphViz online : \n");
			printWriter.printf("// https://dreampuf.github.io/GraphvizOnline \n");						
			printWriter.printf("\ndigraph %s{\n", name);
			arcs(a,F,printWriter); 
			printWriter.write("}\n");    
    		printWriter.close();
	}
	static void arcs(AB a, int[] F, PrintWriter writer){ 
	// Affichage des arcs de l'ab a par un parcours rgd.
	// Remarque : les arcs sont affichés par un parcours "racine gauche droit".
	// Un parcours grd ou drg ferait aussi bien l'affaire car dans la description
	// du graphe, l'ordre dans lequel les arcs sont données est indifférent.
		if (vide(a)) return;
		if (feuille(a)) return;
		if (!vide(sag(a))) {
			writer.printf("\"v=%d\nf=%d\" -> \"v=%d\nf=%d\";\n",
								vr(a), F[vr(a)],
								vr(sag(a)), F[vr(sag(a))]); 
			arcs(sag(a),F,writer);
		}
		if (!vide(sad(a))) {
			writer.printf("\"v=%d\nf=%d\" -> \"v=%d\nf=%d\";\n",
								vr(a), F[vr(a)],
								vr(sad(a)), F[vr(sad(a))]); 
			arcs(sad(a),F,writer);
		}
	}	
	/* Programme principal */
	public static void main(String[] Args) throws IOException {
		if (Args.length == 0){ 
			System.out.println("usage : ArbresBinairesDeRecherche n"); return;}
		int n = Integer.parseInt(Args[0]);
		{ // FREQUENCES PROPORTIONNELLES A LA DISTANCE DES VALEURS A LA MEDIANE
			int fmax = n+1; // fréquences de recherche dans l'intervalle [1:n] 
			/* Nous donnons à chaque valeur v_i une fréquence proportionnelle à sa distance 
			à la valeur médiane. En conséquence, l'approche gloutonne produira un abr de très 
			grande hauteur.
			*/
			int[] F = new int[n];
			int m = n/2; // valeur médiane
			F[m] = 1; 
			for (int i = m+1; i < n; i++)  F[i] = (i-m); 
			for (int i = m-1; i > -1; i--) F[i] = (m-i); 
			/* abr glouton */
			AB abrglouton = abrglouton(F);
			int cout_abrglouton = cout(abrglouton,F);
			System.out.printf("abrglouton : hauteur = %d, coût = %d\n",
										 h(abrglouton), cout_abrglouton);
			/* abr de hauteur minimum */
			AB abrhmin = abrhmin(0,n);
			int cout_abrhmin = cout(abrhmin,F);
			System.out.printf("abrhmin : hauteur = %d, coût = %d\n",
										 h(abrhmin), cout_abrhmin);
			/* abr de coût moyen de recherche minimum */
			int[][] M = calculerM(F); 
			AB abrcmin = abrcmin(M,0,n);
			int cout_abrcmin = cout(abrcmin,F);
			System.out.printf("abrcmin : hauteur = %d, coût = %d\n",
										 h(abrcmin), cout_abrcmin);
			System.out.printf("Distance relative glouton vs. cmin : %f\n",
				(float)(cout_abrglouton - cout_abrcmin)/cout_abrcmin
			);
			System.out.printf("Distance relative hmin vs. cmin : %f\n",
				(float)(cout_abrhmin - cout_abrcmin)/cout_abrcmin
			);
			descriptionGraphviz(abrglouton,F,"abrglouton_1");
			descriptionGraphviz(abrhmin,F,"abrhmin_1");
			descriptionGraphviz(abrcmin,F,"abrcmin_1");
			String file_glouton = "abrglouton_" + n + "_1.txt", 
				file_hmin = "abrhmin_" + n + "_1.txt",
				file_cmin = "abrcmin_" + n + "_1.txt";
			System.out.printf("Descriptions des arbres dans les fichiers %s, %s, %s\n",
				file_glouton, file_hmin, file_cmin);
		}
		{ 	// FREQUENCES F[0:n/4] = [1,1,...] = FREQUENCES F[3n/4:n]
			// FREQUENCES FREQUENCES F[n/4:n/2] = [5,5,...] = F[n/2:3n/4] 
			int[] F = new int[n];
			for (int i = 0; i < n/4; i++)  F[i] = 5; 
			for (int i = n/4; i < n/2; i++) F[i] = 1;  
			for (int i = n/2; i < 3*n/4; i++)  F[i] = 5; 
			for (int i = 3*n/4; i < n; i++) F[i] = 1;  
			/* abr glouton */
			AB abrglouton = abrglouton(F);
			int cout_abrglouton = cout(abrglouton,F);
			System.out.printf("abrglouton : hauteur = %d, coût = %d\n",
										 h(abrglouton), cout_abrglouton);
			/* abr de hauteur minimum */
			AB abrhmin = abrhmin(0,n);
			int cout_abrhmin = cout(abrhmin,F);
			System.out.printf("abrhmin : hauteur = %d, coût = %d\n",
										 h(abrhmin), cout_abrhmin);
			/* abr de coût moyen de recherche minimum */
			int[][] M = calculerM(F); 
			AB abrcmin = abrcmin(M,0,n);
			int cout_abrcmin = cout(abrcmin,F);
			System.out.printf("abrcmin : hauteur = %d, coût = %d\n",
										 h(abrcmin), cout_abrcmin);
			System.out.printf("Distance relative glouton vs. cmin : %f\n",
				(float)(cout_abrglouton - cout_abrcmin)/cout_abrcmin
			);
			System.out.printf("Distance relative hmin vs. cmin : %f\n",
				(float)(cout_abrhmin - cout_abrcmin)/cout_abrcmin
			);
			descriptionGraphviz(abrglouton,F,"abrglouton_2");
			descriptionGraphviz(abrhmin,F,"abrhmin_2");
			descriptionGraphviz(abrcmin,F,"abrcmin_2");
			String file_glouton = "abrglouton_" + n + "_2.txt", 
				file_hmin = "abrhmin_" + n + "_2.txt",
				file_cmin = "abrcmin_" + n + "_2.txt";
			System.out.printf("Descriptions des arbres dans les fichiers %s, %s, %s\n",
				file_glouton, file_hmin, file_cmin);
		}
		{ 	// F[0:n/5] = 1000, F[n-n/5:n] = 1000, F[n/5:n-n/5] = 1
			int[] F = new int[n];
			for (int i = 0; i < n/10; i++)  F[i] = 1000; 
			for (int i = n-n/10; i < n; i++)  F[i] = 1000; 	
			for (int i = n/10; i< n-n/10; i++) F[i] = 1;		
			/* abr glouton */
			AB abrglouton = abrglouton(F);
			int cout_abrglouton = cout(abrglouton,F);
			System.out.printf("abrglouton : hauteur = %d, coût = %d\n",
										 h(abrglouton), cout_abrglouton);
			/* abr de hauteur minimum */
			AB abrhmin = abrhmin(0,n);
			int cout_abrhmin = cout(abrhmin,F);
			System.out.printf("abrhmin : hauteur = %d, coût = %d\n",
										 h(abrhmin), cout_abrhmin);
			/* abr de coût moyen de recherche minimum */
			int[][] M = calculerM(F); 
			AB abrcmin = abrcmin(M,0,n);
			int cout_abrcmin = cout(abrcmin,F);
			System.out.printf("abrcmin : hauteur = %d, coût = %d\n",
										 h(abrcmin), cout_abrcmin);
			System.out.printf("Distance relative glouton vs. cmin : %f\n",
				(float)(cout_abrglouton - cout_abrcmin)/cout_abrcmin
			);
			System.out.printf("Distance relative hmin vs. cmin : %f\n",
				(float)(cout_abrhmin - cout_abrcmin)/cout_abrcmin
			);
			descriptionGraphviz(abrglouton,F,"abrglouton_3");
			descriptionGraphviz(abrhmin,F,"abrhmin_3");
			descriptionGraphviz(abrcmin,F,"abrcmin_3");
			String file_glouton = "abrglouton_" + n + "_3.txt", 
				file_hmin = "abrhmin_" + n + "_3.txt",
				file_cmin = "abrcmin_" + n + "_3.txt";
			System.out.printf("Descriptions des arbres dans les fichiers %s, %s, %s\n",
				file_glouton, file_hmin, file_cmin);
		}
		
		/* cette dernière partie du "main" génère les arbres de taille n = 9 de la 
		figure 3 du document ArbresBinairesDeRecherche.pdf.*/
		{ 	n = 9; int[] F = new int[]{2,3,1,8,5,4,6,7,9} ;
			// abr glouton 
			AB abrglouton = abrglouton(F);
			int cout_abrglouton = cout(abrglouton,F);
			System.out.printf("abrglouton : hauteur = %d, coût = %d\n",
										 h(abrglouton), cout_abrglouton);
			// abr de hauteur minimum 
			AB abrhmin = abrhmin(0,n);
			int cout_abrhmin = cout(abrhmin,F);
			System.out.printf("abrhmin : hauteur = %d, coût = %d\n",
										 h(abrhmin), cout_abrhmin);
			// abr de coût moyen de recherche minimum 
			int[][] M = calculerM(F); 
			AB abrcmin = abrcmin(M,0,n);
			int cout_abrcmin = cout(abrcmin,F);
			System.out.printf("abrcmin : hauteur = %d, coût = %d\n",
										 h(abrcmin), cout_abrcmin);
			System.out.printf("Distance relative glouton vs. cmin : %f\n",
				(float)(cout_abrglouton - cout_abrcmin)/cout_abrcmin
			);
			System.out.printf("Distance relative hmin vs. cmin : %f\n",
				(float)(cout_abrhmin - cout_abrcmin)/cout_abrcmin
			);
			descriptionGraphviz(abrglouton,F,"abrglouton_figure3");
			descriptionGraphviz(abrhmin,F,"abrhmin_figure3");
			descriptionGraphviz(abrcmin,F,"abrcmin_figure3");
			String file_glouton = "abrglouton_figure3_" + n + ".txt", 
				file_hmin = "abrhmin_figure3_" + n + ".txt",
				file_cmin = "abrcmin_figure3_" + n + ".txt";
			System.out.printf("Descriptions des arbres dans les fichiers %s, %s, %s\n",
				file_glouton, file_hmin, file_cmin);
		}	
	}	
}
/* Compilation et exemple d'exécution dans un terminal Unix
% java ArbresBinairesDeRecherche 127 
abrglouton : hauteur = 126, coût = 172831
abrhmin : hauteur = 6, coût = 24577
abrcmin : hauteur = 11, coût = 23595
Distance relative glouton vs. cmin : 6,324899
Distance relative hmin vs. cmin : 0,041619
Descriptions des arbres dans les fichiers abrglouton_127_1.txt, abrhmin_127_1.txt, abrcmin_127_1.txt
abrglouton : hauteur = 18, coût = 2963
abrhmin : hauteur = 6, coût = 2301
abrcmin : hauteur = 9, coût = 2171
Distance relative glouton vs. cmin : 0,364809
Distance relative hmin vs. cmin : 0,059880
Descriptions des arbres dans les fichiers abrglouton_127_2.txt, abrhmin_127_2.txt, abrcmin_127_2.txt
abrglouton : hauteur = 21, coût = 134620
abrhmin : hauteur = 6, coût = 148621
abrcmin : hauteur = 10, coût = 95013
Distance relative glouton vs. cmin : 0,416859
Distance relative hmin vs. cmin : 0,564218
Descriptions des arbres dans les fichiers abrglouton_127_3.txt, abrhmin_127_3.txt, abrcmin_127_3.txt
abrglouton : hauteur = 5, coût = 140
abrhmin : hauteur = 3, coût = 123
abrcmin : hauteur = 3, coût = 113
Distance relative glouton vs. cmin : 0,238938
Distance relative hmin vs. cmin : 0,088496
Descriptions des arbres dans les fichiers abrglouton_figure3_9.txt, abrhmin_figure3_9.txt, abrcmin_figure3_9.txt
*/