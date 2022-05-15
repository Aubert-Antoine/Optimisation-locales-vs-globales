import java.util.Arrays;

// https://projecteuler.net/problem=67
public class Somme {

    private static final boolean debug = true;

    /**
     * MakeTrig() retourne un tableau, qui contient les valeurs du triangle
     * @return le tableau de ref du projet Euler
     */
    public static int[] MakeTrig() {
        int[] TabTrig = new int[]{3,7,4,2,4,6,8,5,9,3};
        if(debug) System.out.println("TAbTrig: "+  Arrays.toString(TabTrig));
        return TabTrig; 
    }


    /**
     * g(pTAbRaf) retourne le descendant gauche du triangle passée en param
     * @param pTAbRef est le tableau de referance d'entrée
     * @param indice est le numero dont l'on cherche l'incide descendant gauche
     * @return l'indice descendant gauche
     */
    public static int g(int[] pTAbRef, int pIndice){
        int l=0;      // ligne/etage dans le triangle
        int p=0;      // position dans la ligne
        int niveau=0; // niveau de la derniere ligne
        int nbElement=0;
        int nbElementMoins1=0;
        int nbElementNM1=0;     //nombre d'élement au niveau n-1

        if(pIndice > pTAbRef.length || pIndice < 0) {
            System.out.println("ERROR \n --> l'indice est superieur a la taille du tableau ou negatif.");

            if(debug) System.out.println("indice : "+pIndice+" et taille Tab : "+ pTAbRef.length);

            return -1;
        }
        else if(pIndice == 0) return 0;
        else{
            //On cherche le niveau max du tableau
            for (niveau = 1; niveau < pTAbRef.length; niveau++) {
                if(pTAbRef.length == niveau*(niveau+1)/2){
                    nbElementNM1 = pTAbRef.length-((niveau-1)*niveau)/2;
                    break;
                }
            }

            for (int i = 1; i < pTAbRef.length; i++) {
                nbElement = (i*(i+1))/2;
                nbElementMoins1 = ((i-1)*i)/2;
                if(nbElement >= pIndice){        // ==> indice sur cette ligne courante
                    l=i;
                    p=pIndice-nbElementMoins1;
                    break;
                }
            }

            

            if(pIndice > pTAbRef.length-nbElementNM1){
                System.out.println("ERROR \n --> l'indice saisie est sur la derniére ligne => pas de descendant gauche");
                return -1;
            }

            if(debug){
                System.out.println("L'indice "+pIndice+" est en :");
                System.out.println("-l : "+l +"\n-p : "+p);
                System.out.println("Donc l'indice de son descendant gauche est :");
                System.out.println("-l : "+(l+1) +"\n-p : "+p);
                System.out.println("avec comme indice");
                System.out.println(nbElementMoins1+p);

            }

            return nbElementMoins1+p;
            }
        }


        




    public static void mainSomme(){
        System.out.println("Hello, World! from Somme Class \n");
        g(MakeTrig(), 5);
    }
    
}
