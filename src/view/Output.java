package view;

import model.Ploca;

/**
 * Klasa koja upravlja svim izlazima u konzolu tokom igre.
 * Ispisuje informacije o igri, ploči i rezultatima korisniku.
 */
public class Output {

    /**
     * Metoda koja prikazuje trenutno stanje ploče igre.
     * Ispisuje trenutni rezultat, high score, i vizuelni prikaz ploče.
     * 
     * @param plocaObjekat Objekt tipa Ploca koji sadrži informacije o trenutnom stanju igre.
     */
    public static void pokaziPlocu(Ploca plocaObjekat) {

        // Ispisuje trenutni rezultat (score) i visok rezultat (high score)
        System.out.println("Score: " + plocaObjekat.getScore());
        System.out.println("High Score: " + plocaObjekat.getHighScore());
        System.out.println();

        // Dobija matricu koja predstavlja ploču
        int[][] ploca = plocaObjekat.getPloca();

        // Ispisuje gornji deo okvira ploče
        for (int i = 0; i < (Ploca.VELICINA); i++) {
            System.out.print("-------");
        }
        System.out.println();

        // Ispisuje sadržaj ploče red po red
        for (int i = 0; i < (Ploca.VELICINA); i++) {

            // Ispisuje levo vertikalno odvajanje
            System.out.print("|");
            for (int j = 0; j < (Ploca.VELICINA); j++) {
                System.out.print("      |"); // Prazno polje
            }
            System.out.println();

            // Ispisuje vrednosti u trenutnom redu ploče
            System.out.print("|");
            for (int j = 0; j < (Ploca.VELICINA); j++) {
                if (ploca[i][j] == 0) {
                    // Ako je polje prazno, ispisuje prazno mesto
                    System.out.printf("  %-3s |", "");
                } else {
                    // Ako polje nije prazno i ako je broj trocifer ili manje, ispisuje vrednost
                    if(ploca[i][j] < 1000) {
                    	System.out.printf("  %-3s |", "" + ploca[i][j]);
                    }
                    // Ako polje nije prazno i ako je broj cetverocifren, ispisuje vrednost sa jednim space manje
                    else {
                    	System.out.printf(" %-3s |", "" + ploca[i][j]);
                    }
                }
            }
            System.out.println();

            // Ispisuje desno vertikalno odvajanje
            System.out.print("|");
            for (int j = 0; j < (Ploca.VELICINA); j++) {
                System.out.print("      |");
            }
            System.out.println();

            // Ispisuje donji okvir reda
            for (int j = 0; j < (Ploca.VELICINA); j++) {
                System.out.print("-------");
            }
            System.out.println();
        }
    }

    /**
     * Metoda koja ispisuje string u konzolu bez prelaza u novi red.
     * 
     * @param string String koji se ispisuje.
     */
    public static void print(String string) {
        System.out.print(string);
    }

    /**
     * Metoda koja ispisuje string u konzolu sa prelazom u novi red.
     * 
     * @param string String koji se ispisuje.
     */
    public static void println(String string) {
        System.out.println(string);
    }
}
