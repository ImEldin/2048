package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Klasa koja predstavlja ploču za igru 2048.
 * Omogućava manipulaciju ploče, čuvanje stanja igre, učitavanje igre i praćenje rezultata.
 */
public class Ploca {

    /** Generator slučajnih brojeva za igru. */
    public static final Random RANDOM = new Random();
    
    /** Ciljani rezultat za završetak igre. */
    public static final int POBJEDA = 2048;
    
    /** Ciljani rezultat za završetak igre. */
    public static final int KRAJ_IGRE = 8192;

    /** Vrijednost koja označava prazno polje na ploči. */
    public static final int PRAZNO_POLJE = 0;
    
    /** Dvodimenzionalni niz koji predstavlja ploču igre. */
    private int ploca[][];

    /** Trenutni rezultat igrača. */
    private int score;

    /** Najveći rezultat (High Score) postignut u igri. */
    private int highScore;
    
    /** Veličina ploče (broj redova i kolona, default postavljena na 4). */
    public static int VELICINA = 4;

    /**
     * Konstruktor koji inicijalizuje ploču, rezultat, i dodaje dva slučajna broja na ploču.
     * 
     * @param veličina ploče na kojoj će se igrati
     */
    public Ploca(int v) {
    	Ploca.VELICINA = v;
        ploca = new int[VELICINA][VELICINA];
        score = 0;
        highScore = ucitajHighScore();
        dodajRandBroj();
        dodajRandBroj();
    }

    /**
     * Dodaje vrijednost trenutnom rezultatu i ažurira najveći rezultat ako je potrebno.
     * 
     * @param vrijednost Vrijednost koja se dodaje trenutnom rezultatu.
     */
    public void dodajScore(int vrijednost) {
        score += vrijednost;
        if (score > highScore) {
            highScore = score;
        }
    }

    /**
     * Sprema najveći rezultat u fajl.
     */
    public void sacuvajHighScore() {
    	String highscore_save_file = "highscore_4x4.txt";
    	if(Ploca.VELICINA == 5) highscore_save_file = "highscore_5x5.txt";
    	
        try (BufferedWriter pisac = new BufferedWriter(new FileWriter(highscore_save_file))) {
            pisac.write(String.valueOf(highScore));
        } 
        catch (IOException e) {
            System.out.println("Greška pri čuvanju highscore: " + e.getMessage());
        }
    }

    /**
     * Učitava najveći rezultat iz fajla.
     * 
     * @return Najveći rezultat ako je fajl pronađen, inače 0.
     */
    private int ucitajHighScore() {
    	String highscore_save_file = "highscore_4x4.txt";
    	if(Ploca.VELICINA == 5) highscore_save_file = "highscore_5x5.txt";
    	
        try (BufferedReader citac = new BufferedReader(new FileReader(highscore_save_file))) {
            String linija = citac.readLine();
            if (linija != null && !linija.trim().isEmpty()) {
                return Integer.parseInt(linija.trim());
            }
        } 
        catch (IOException e) {
            System.out.println("Nije pronađen highscore, krećemo ispočetka!");
        }
        return 0;
    }

    /**
     * Vraća trenutni rezultat igrača.
     * 
     * @return Trenutni rezultat.
     */
    public int getScore() {
        return score;
    }

    /**
     * Postavlja trenutni rezultat.
     * 
     * @param score Novi rezultat.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Vraća najveći rezultat postignut u igri.
     * 
     * @return Najveći rezultat.
     */
    public int getHighScore() {
        return highScore;
    }

    /**
     * Sprema trenutnu ploču igre u fajl.
     */
    public void spasiPlocu() {
    	String save_game_file = "game_save_4x4.txt";
    	if(Ploca.VELICINA == 5) save_game_file = "game_save_5x5.txt";
    	
        try (BufferedWriter pisac = new BufferedWriter(new FileWriter(save_game_file))) {
            pisac.write(score + " ");
            pisac.newLine();
            for (int i = 0; i < VELICINA; i++) {
                for (int j = 0; j < VELICINA; j++) {
                    pisac.write(ploca[i][j] + " ");
                }
                pisac.newLine();
            }
        } 
        catch (IOException e) {
            System.out.println("Greška pri spašavnju igre: " + e.getMessage());
        }
    }

    /**
     * Učitava stanje igre iz fajla.
     */
    public void ucitajPlocu() {
    	String save_game_file = "game_save_4x4.txt";
    	if(Ploca.VELICINA == 5) save_game_file = "game_save_5x5.txt";
    	
        try (BufferedReader citac = new BufferedReader(new FileReader(save_game_file))) {
            String line = citac.readLine();
            if (line == null || line.trim().isEmpty()) {
                throw new IOException("Fajl je prazan ili nevalidan.");
            }
            score = Integer.parseInt(line.trim());
            for (int i = 0; i < VELICINA; i++) {
                line = citac.readLine();
                if (line == null || line.trim().isEmpty()) {
                    throw new IOException("Fajl je prazan ili nevalidan.");
                }
                String[] vrijednost = line.split(" ");
                if (vrijednost.length != VELICINA) {
                    throw new IOException("Fajl je korumpiran ili nevalidan.");
                }
                for (int j = 0; j < VELICINA; j++) {
                    ploca[i][j] = Integer.parseInt(vrijednost[j]);
                }
            }
        } 
        catch (IOException e) {
            System.out.println("Greška pri učitavanju igre: " + e.getMessage());
            System.out.println("Započinjem novu igru...");
            this.restartajIgru();
        }
    }

    /**
     * Restartuje igru tako što briše ploču i dodaje dva slučajna broja.
     */
    public void restartajIgru() {
        for (int i = 0; i < VELICINA; i++) {
            for (int j = 0; j < VELICINA; j++) {
                ploca[i][j] = PRAZNO_POLJE;
            }
        }
        dodajRandBroj();
        dodajRandBroj();
    }

    /**
     * Postavlja ploču igre sa novim vrijednostima.
     * 
     * @param ploca Dvodimenzionalni niz koji predstavlja novu ploču igre.
     */
    public void setPloca(int ploca[][]) {
        for (int i = 0; i < VELICINA; i++) {
            for (int j = 0; j < VELICINA; j++) {
                this.ploca[i][j] = ploca[i][j];
            }
        }
    }

    /**
     * Vraća kopiju trenutne ploče igre.
     * 
     * @return Kopija ploče igre.
     */
    public int[][] getPloca() {
        int[][] plocaKopija = new int[VELICINA][VELICINA];
        for (int i = 0; i < VELICINA; i++) {
            for (int j = 0; j < VELICINA; j++) {
                plocaKopija[i][j] = this.ploca[i][j];
            }
        }
        return plocaKopija;
    }

    /**
     * Provjerava da li se broj nalazi na ploči.
     * 
     * @param x Broj koji se traži.
     * @return True ako se broj nalazi na ploči, inače false.
     */
    public boolean traziNaPloci(int x) {
        for (int i = 0; i < VELICINA; i++) {
            for (int j = 0; j < VELICINA; j++) {
                if (ploca[i][j] == x) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Dodaje slučajni broj (2 ili 4) na prazno polje ploče.
     */
    public void dodajRandBroj() {
        if (RANDOM.nextDouble() <= 0.8) {
            dodajRandBroj(2);
        } 
        else {
            dodajRandBroj(4);
        }
    }

    /**
     * Dodaje specifičan broj na slučajno prazno polje ploče.
     * 
     * @param broj Broj koji se dodaje na ploču.
     */
    private void dodajRandBroj(int broj) {
        int i = RANDOM.nextInt(VELICINA);
        int j = RANDOM.nextInt(VELICINA);

        while (ploca[i][j] != 0) {
            i = RANDOM.nextInt(VELICINA);
            j = RANDOM.nextInt(VELICINA);
        }

        ploca[i][j] = broj;
    }
}
