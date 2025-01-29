package controller;

import model.Igra;
import view.Input;
import view.Output;

public class consoleController {
	
	 /** Varijabla pomocu koje znamo da li je igrac dostigao 2048 */
		private boolean igracDostigao2048 = false;

	    /** Objekat igre koji upravlja stanjem i pravilima igre. */
	    private Igra igra;

	    /**
	     * Podrazumijevani konstruktor za inicijalizaciju kontrolera.
	     */
	    public consoleController() {
	    }

	    /**
	     * Inicijalizuje igru unutar kontrolera.
	     * 
	     * @param igra Objekat igre.
	     */
	    public void inicijaliziraj(Igra igra) {
	        this.igra = igra;
	    }

	    /**
	     * Obrada poteza kada se igra preko konzole.
	     */
	    public void igrajKonzola() {
	        // Učitavanje prethodne igre ako korisnik to želi
	        char ucitajIgruOdgovor = Input.ucitajIgru();
	        if (ucitajIgruOdgovor == 'Y') {
	            Output.println("Učitavam prethodnu igru...");
	            igra.getPloca().ucitajPlocu();
	        } 
	        else {
	            Output.println("Započinjete novu igru...");
	            igra.getPloca().spasiPlocu();
	        }

	        // Glavna petlja igre
	        while (!igra.daLiJeKraj()) {
	            Output.pokaziPlocu(igra.getPloca()); // Prikazuje trenutnu ploču

	            // Uzimanje korisnikovog poteza
	            char potez = Input.uzmiPotez();

	            // Restart igre
	            if (potez == 'R') {
	                Output.println("Restartovanje igre...");
	                igra.getPloca().restartajIgru();
	                igra.getPloca().setScore(0);
	                igra.getPloca().spasiPlocu();
	                continue;
	            }

	            // Izlaz iz igre
	            if (potez == 'Q') {
	                if (Input.potvrdiIzlaz()) {
	                    Output.println("Napuštam igru...");
	                    System.exit(0); // Zatvara program
	                } 
	                else {
	                    continue;
	                }
	            }
	            

	            // Izvršavanje poteza
	            boolean potezNapravljen = igra.odigrajPotez(potez);
	            if (potezNapravljen) {
	                // Dodavanje novog nasumičnog broja na ploču nakon validnog poteza
	                igra.getPloca().dodajRandBroj();
	            }

	            // Spremanje stanja igre
	            igra.getPloca().spasiPlocu();
	            igra.getPloca().sacuvajHighScore();
	            
	            // Da li je igrac dostigao 2048
	            if(igra.dostigao2048() && igracDostigao2048 == false) {
	            	Output.pokaziPlocu(igra.getPloca());
	                char nastaviIgruOdgovor = Input.nastaviIgru();
	                // Ako je odgovr da, nastavlja igru
	                if (nastaviIgruOdgovor == 'Y') {
	                    Output.println("Nastavljam igru...");
	                    Output.println("");
	                    igra.getPloca().spasiPlocu();
	                    igra.getPloca().sacuvajHighScore();
	                	igracDostigao2048 = true;
	                	continue;
	                } 
	                // Izlazi iz igre
	                else {
	                    Output.println("Napuštam igru...");
	                    System.exit(0); // Zatvara program
	                }
	            }
	        }

	        // Igra je završena - provjera pobjede ili poraza
	        Output.pokaziPlocu(igra.getPloca());
	        if (igra.korisnikPobijedio()) {
	            Output.println("Čestitam, prekucali ste igru!");
	        }
	        else {
	            Output.println("Izgubili ste!");
	        }
	    }

}
