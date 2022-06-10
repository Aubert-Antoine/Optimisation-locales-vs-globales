// rene.natowicz@esiee.fr - 24/05/2022
import java.util.Random;
//import java.util.Arrays;
import java.io.*;
class EcrireValeursGaussiennesDansFichier{

	public static void main(String[] Args) throws IOException {
		if (Args.length != 2) {
			System.out.println("Usage : EcrireValeursGaussiennesDansFichier n F"); 
			return;
		}
		int n = Integer.parseInt(Args[0]); // nombre de valeurs gaussiennes à produire
		String F = Args[1]; // fichier dans lequel ces valeurs seront écrites
		Random rand = new Random();
		double[] G = new double[n];
		for (int i = 0; i < n; i++)
			G[i] = rand.nextGaussian();
		EcrireGdansF(G,F);
	}

	static void EcrireGdansF(double[] G, String F)
	// Ecrit les valeurs de G dans le fichier de nom F. Une valeur par ligne.
		throws IOException {
  			int n = G.length;
     		FileWriter fileWriter = new FileWriter("src\\"+F);	// canhgement de repertoire    "csv\\"+
			PrintWriter pw = new PrintWriter(fileWriter);
			for (int i = 0; i < n; i++)
				pw.println(G[i]) ; 
    		pw.close();
    	}
}
/* Compilation et exemple d'exécution dans un terminal Unix
% javac EcrireValeursGaussiennesDansFichier.java         
% java EcrireValeursGaussiennesDansFichier 10000 GAUSS.CSV
% python3 histogramme.py GAUSS 2                          
l'histogramme est dans le fichier GAUSS.PNG
% 
*/