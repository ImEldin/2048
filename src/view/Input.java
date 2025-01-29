package view;

import java.util.Scanner;
import model.Igra;

/**
 * Klasa koja upravlja unosom sa strane korisnika putem konzole.
 * Omogućava čitanje pojedinačnih karaktera i rukovanje unosima korisnika.
 */
public class Input {

    /** Instanca skenera za čitanje unosa sa tastature. */
    private static final Scanner SCANNER = new Scanner(System.in);

    /**
     * Metoda za potvrdu izlaska iz igre.
     * Pita korisnika da li je siguran da želi da napusti igru (Y/N).
     * 
     * @return true ako korisnik potvrdi izlazak (Y), false ako izabere N.
     */
    public static boolean potvrdiIzlaz() {
        // Pita korisnika da li želi da napusti igru
        char quit = citajZnak("Da li ste sigurni da želite izaći? (Y/N): ");
        quit = Character.toUpperCase(quit); // Osigurava da unos bude u velikim slovima

        // Provera da li je unos validan (Y/N)
        while (!(quit == 'Y' || quit == 'N')) {
            Output.println("Pogrešan unos. Unesite Y ili N.");
            quit = citajZnak("Da li ste sigurni da želite izaći? (Y/N): ");
        }

        return quit == 'Y'; // Ako je odgovor Y, izlazi iz igre
    }

    /**
     * Metoda za čitanje jednog karaktera sa tastature, uz potvrdu da unos sadrži tačno jedan karakter.
     * 
     * @param poruka Poruka koja se prikazuje korisniku prilikom unosa.
     * @return Jedan karakter unet od strane korisnika.
     */
    private static char citajZnak(String poruka) {
        Output.print(poruka); // Ispisuje poruku korisniku
        String input = SCANNER.nextLine(); // Čita unos sa tastature

        // Provjera da li je uneseno samo jedno slovo
        while (input.length() != 1) {
            System.out.println("Možete unijeti samo jedan karakter!");
            Output.println("");
            Output.print(poruka); // Ponovo traži unos
            input = SCANNER.nextLine();
        }

        return input.charAt(0); // Vraća prvi karakter unosa
    }

    /**
     * Metoda za učitavanje prethodne igre, pita korisnika da li želi da učita prethodnu igru (Y/N).
     * 
     * @return Odgovor korisnika (Y ili N).
     */
    public static char ucitajIgru() {
        // Pita korisnika da li želi da učita prethodnu igru
        char odgovor = citajZnak("Da li želite da učitate prethodnu igru? (Y/N): ");
        odgovor = Character.toUpperCase(odgovor); // Osigurava da odgovor bude u velikim slovima

        // Provera da li je unos validan (Y/N)
        while (!(odgovor == 'Y' || odgovor == 'N')) {
            Output.println("Nevalidan odgovor!");
            Output.println("");
            odgovor = citajZnak("Da li želite da učitate prethodnu igru? (Y/N): ");
            odgovor = Character.toUpperCase(odgovor);
        }

        return odgovor; // Vraća odgovor korisnika
    }
    
    public static char nastaviIgru() {
        // Pita korisnika da li želi da nastavi sa igrom nakon dostizanja 2048
        char odgovor = citajZnak("Čestitamo! Dostigli ste 2048. Želite li nastaviti igrati? (Y/N):");
        odgovor = Character.toUpperCase(odgovor); // Osigurava da odgovor bude u velikim slovima

        // Provera da li je unos validan (Y/N)
        while (!(odgovor == 'Y' || odgovor == 'N')) {
            Output.println("Nevalidan odgovor!");
            Output.println("");
            odgovor = citajZnak("Čestitamo! Dostigli ste 2048. Želite li nastaviti igrati? (Y/N):");
            odgovor = Character.toUpperCase(odgovor);
        }

        return odgovor; // Vraća odgovor korisnika
    }

    /**
     * Metoda za čitanje poteza korisnika tokom igre.
     * Pita korisnika da izabere jedan od mogućih poteza (lijevo, desno, gore, dole, restart, izlaz).
     * 
     * @return Karakter koji označava korisnikov potez.
     */
    public static char uzmiPotez() {
        // Ispisuje opcije za poteze i traži od korisnika da unese svoj potez
        char potez = citajZnak(
                "Izaberite potez: \n" +
                "W/w: lijevo\n" +
                "S/s: dole\n" +
                "A/a: gore\n" +
                "D/d: desno\n" +
                "R/r: Restart\n" +
                "Q/q: Quit\n" +
                "Unesite potez: "
        );

        potez = Character.toUpperCase(potez); // Osigurava da unos bude u velikim slovima

        // Proverava da li je uneseni potez validan
        while (!(potez == Igra.POMJERI_LIJEVO || potez == Igra.POMJERI_DESNO ||
                 potez == Igra.POMJERI_GORE || potez == Igra.POMJERI_DOLE ||
                 potez == Igra.RESTARTAJ_IGRU || potez == Igra.NAPUSTI_IGRU)) {
            Output.println("Nevalidan potez!");
            Output.println("");
            // Ponovo traži unos od korisnika
            potez = citajZnak(
                    "Izaberite potez: \n" +
                    "W/w: lijevo\n" +
                    "S/s: dole\n" +
                    "A/a: gore\n" +
                    "D/d: desno\n" +
                    "R/r: Restart\n" +
                    "Q/q: Quit\n" +
                    "Unesite potez: "
            );
            potez = Character.toUpperCase(potez); // Osigurava da unos bude u velikim slovima
        }

        return potez; // Vraća korisnikov validni potez
    }
}
