import java.util.Arrays;
public class TD5_2022{

static final int plusInfini = Integer.MAX_VALUE, moinsInfini = Integer.MIN_VALUE;

// Exercice 6 : le petit robot.
        static int[][] calculerM(int L, int C){ // une grille L x C
                int[][] M = new int[L][C]; // de terme général M[l][c] = m(l,c)
                // base
                M[0][0] = 0;
                for (int c=1; c<C; c++) M[0][c] = M[0][c-1]+e(0,c-1,L,C); // Calcul du coût de la première ligne
                for (int l=1; l<L; l++) M[l][0] = M[l-1][0]+n(l-1,0,L,C); // Calcul du coût de la première colonne
                // cas général
                for (int l=1; l<L; l++) {
                    for (int c=1; c<C; c++) {
                        M[l][c] = min(M[l][c-1] + e(l,c-1,L,C), M[l-1][c] + n(l-1,c,L,C), M[l-1][c-1] + ne(l-1,c-1,L,C));
                    }
                }
                return M;
        }

        static void accm(int[][] M, int L, int C, int l, int c){
        // affiche un chemin de coût minimimum (ccm) de 0,0 à l,c
        // Base
            if (l==0 & c==0) {
                System.out.print("(0,0)");
                return;
            }
            else if (l==0) { // dernier mouvement : Est depuis 0,c-1
                accm(M,L,C,l,c-1);
                System.out.printf(" -%d-> (%d,%d)", e(l,c-1,L,C),l,c);
            }
            else if (c==0) { // dernier mouvement : Nord depuis l-1,0
                accm(M,L,C,l-1,c);
                System.out.printf(" -%d-> (%d,%d)", n(l-1,c,L,C),l,c);
            }
            else { // l,c > 0,0
                // Coûts du pas de déplacement conduisant jusqu'en (l,c)
                int n=n(l-1,c,L,C), ne=ne(l-1,c-1,L,C), e=e(l,c-1,L,C);
                // Coûts du chemin jusqu'en (l,c) selon le dernier pas de déplacement
                int Mn = M[l-1][c] + n, Mne = M[l-1][c-1] + ne, Me = M[l][c-1] + e;
                if (Mn == min(Mn,Me,Mne)) { // dernier mouvement : Nord
                    accm(M,L,C,l-1,c);
                    System.out.printf(" -%d-> (%d,%d)", n(l-1,c,L,C),l,c);
                }
                else if (Mne == min(Mn,Me,Mne)) { // dernier mouvement : Nord Est
                    accm(M,L,C,l-1,c-1);
                    System.out.printf(" -%d-> (%d,%d)", ne(l-1,c-1,L,C),l,c);
                }
                else if (Me == min(Mn,Me,Mne)) { // dernier mouvement : Est
                    accm(M,L,C,l,c-1);
                    System.out.printf(" -%d-> (%d,%d)", e(l,c-1,L,C),l,c);
                }
            }
        }


        // Fonctions de coût des déplacements.
        // 1) depuis la case 00, les déplacements N et E coûtent 1, le déplacement NE coûte 0.
        // 2) sur la colonne 0, les autres déplacements coûtent 0
        // 3) sur la ligne L-1 les déplacements E coûtent 0
        // 4) tous les autres déplacements coûtent 1.
        // Chemin de coût minimum :
        //      00 -1-> 10 -0-> ... -0-> (L-1)0 -0-> (L-1)1 -0-> ... -0-> L(L-1)(C-1).
        // Il est de coût 1.
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
        public static void main(String[] args){
                {       System.out.println("Exercice 6 : le petit robot");
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

        // fonctions annexe
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
