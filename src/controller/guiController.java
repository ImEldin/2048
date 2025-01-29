package controller;

import model.Igra;
import view.Prozor;

public class guiController {
	
	 /** Varijabla pomocu koje znamo da li je igrac dostigao 2048 */
	private boolean igracDostigao2048 = false;

   /** Objekat igre koji upravlja stanjem i pravilima igre. */
   private Igra igra;

   /** Prozor koji predstavlja grafički korisnički interfejs igre. */
   private Prozor prozor;

   /**
    * Podrazumijevani konstruktor za inicijalizaciju kontrolera.
    */
   public guiController() {
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
    * Inicijalizuje prozor unutar kontrolera i postavlja početno stanje ploče.
    * 
    * @param prozor Objekat prozora (grafičkog korisničkog interfejsa).
    */
   public void inicijalizirajProzor(Prozor prozor) {
       this.prozor = prozor;

       // Provjera da li treba učitati prethodno stanje igre ili započeti novu
       if (Prozor.UCITAJ) {
           prozor.updatePlocu(igra.getPloca(), igra.getPloca().getScore(), igra.getPloca().getHighScore());
           igra.getPloca().spasiPlocu();
       } else {
           igra.getPloca().ucitajPlocu();
           prozor.updatePlocu(igra.getPloca(), igra.getPloca().getScore(), igra.getPloca().getHighScore());
       }
   }

   /**
    * Obrada poteza kada se igra preko grafičkog korisničkog interfejsa (prozor).
    * 
    * @param move Karakter koji predstavlja korisnikov potez.
    */
   public void igrajProzor(char move) {
       // Restart igre
       if (move == 'R') {
           igra.getPloca().restartajIgru();
           igra.getPloca().setScore(0);
           igra.getPloca().spasiPlocu();
           prozor.updatePlocu(igra.getPloca(), igra.getPloca().getScore(), igra.getPloca().getHighScore());
       }

       // Spremanje igre prije izlaska
       if (move == 'Q') {
           igra.getPloca().spasiPlocu();
       }

       // Izvršavanje poteza i provjera stanja igre
       boolean napravljenPotez = igra.odigrajPotez(move);

       if (igra.daLiJeKraj()) {
           prozor.updatePlocu(igra.getPloca(), igra.getPloca().getScore(), igra.getPloca().getHighScore());

           // Provjera da li je korisnik pobijedio,izgubio ili postigao 2048
           if (igra.korisnikPobijedio()) {
               prozor.ispisiKraj("Čestitam, prekucali ste igricu!");
           } 
           else {
               prozor.ispisiKraj("Izgubili ste!");
           }
       } 
       else if(igra.dostigao2048() && igracDostigao2048 == false) {
       	igra.getPloca().dodajRandBroj();
       	prozor.updatePlocu(igra.getPloca(), igra.getPloca().getScore(), igra.getPloca().getHighScore());
       	prozor.prikaziPoruku2048();
       	igracDostigao2048 = true;
       }
       else {
       	if (napravljenPotez) {
               // Dodavanje novog nasumičnog broja na ploču nakon poteza
               igra.getPloca().dodajRandBroj();
               prozor.updatePlocu(igra.getPloca(), igra.getPloca().getScore(), igra.getPloca().getHighScore());
               igra.getPloca().spasiPlocu();
               igra.getPloca().sacuvajHighScore();
           }
       }
   }

}
