/**
 * La class Main permet d'executer les programme que l'on souhaite.
 * Mettre en commentaire les programme que l'on ne veut pas executer.
 */


public class Main {
  public static void main(String[] Args) throws Exception { 

    System.out.println("\n\n\nMain Class : main()\n\n\n");

    System.out.println("");

    System.out.println("    \nEXEMPLE 1 : Robot    ");
    Robot.mainRobot();
    System.out.println("");

    System.out.println("    \nEXEMPLE 2 : SVM      ");
    SVM.mainSVM();
    System.out.println("");

    System.out.println("    \nEXEMPLE 3 : Stock    ");
    Stock.mainStock();
    System.out.println("");

    System.out.println("    \nEXEMPLE 4 : Travail  ");
    Travail.mainTravail();
    System.out.println("");

    System.out.println("    \nEXEMPLE 5 : Somme    ");
    Somme.mainSomme();
    System.out.println("");

    System.out.println("\nFIN MAIN CLASS\n\n\n");

  }
}