# -*- coding: latin-1 -*-
""" 
Le fichier des distances relatives dont vous voulez afficher l'histogramme contient 
une distance relative par ligne et rien d'autre.
Exemple : 
0.494520822486
0.0939254807197
0.365846578605
0.150577932504
...

Dans ce qui suit, ce fichier est DR.CSV   (CSV = comma separated values)

Ouvrir un terminal Unix ou Linux et se placer dans le répertoire qui contient
le fichier DR.CSV

Taper la ligne de commande     
   python3 histogramme.py DR   
ou 
   python histogramme.py DR    
(respectivement Python version 3 et Python version 2)

Vous obtiendrez un fichier image 
   DR.PNG
contenant l'histogramme. 

Vous pouvez donner le nom de fichier que vous voulez. 

Exemple : si le fichier DR_PROBLEME_TRUC_MACHIN.CSV contient les distances relatives
du problème truc machin, la ligne de commande
	python3 histogramme.py DR_PROBLEME_TRUC_MACHIN
génère un fichier image 
    DR_PROBLEME_TRUC_MACHIN.PNG
contenant l'histogramme.

Exemple d'exécution dans un terminal Unix : 
% python histogramme.py DR_PROBLEME_TRUC_MACHIN
l'histogramme est dans le fichier DR_PROBLEME_TRUC_MACHIN.PNG
% 

"""
import sys
import csv
import matplotlib.pyplot as plt
import os

# absPass = "D:\D_Perso\travail\Optimisation-locales-vs-globales\histogramme"




def histogramme(fileName) : 
	DR = [] # distances relatives 

	csvPath = './csv/{}.csv'.format(os.path.basename(fileName[:-4]))
	plotName = './histogramme/{}.jpg'.format(os.path.basename(fileName[:-4]))

	with open(csvPath) as csvfile:		# fileName+".CSV"
		reader = csv.reader(csvfile)
		for row in reader:
			dr = row[0]
			DR.append(float(dr))
	plt.ylim(0, 40)						
	h = plt.hist(DR,bins=len(DR))
	plt.savefig(plotName)  # os.path.join(absPass,fileName)+
	plt.close()
	csvfile.close

def main() : 
	if len(sys.argv) != 2 :
		print("Usage : python3 histogramme.py fileName")
		print("Exemple : python3 hystogramme.py DR")
		return
	
	fileName = sys.argv[1]
	histogramme(fileName)
	print("l'histogramme est dans le fichier " + fileName+".PNG")

main()