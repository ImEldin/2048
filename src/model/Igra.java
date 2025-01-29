package model;

/**
 * Klasa koja upravlja logikom igre 2048.
 * Definiše pravila igre, omogućava poteze i provjerava završetak igre.
 */
public class Igra {

    // Konstantne vrijednosti za unos poteza
    public static final char POMJERI_LIJEVO = 'A'; // Pomicanje ploče ulijevo
    public static final char POMJERI_DESNO = 'D'; // Pomicanje ploče udesno
    public static final char POMJERI_GORE = 'W';  // Pomicanje ploče prema gore
    public static final char POMJERI_DOLE = 'S';  // Pomicanje ploče prema dolje
    public static final char RESTARTAJ_IGRU = 'R'; // Restart igre
    public static final char NAPUSTI_IGRU = 'Q';  // Napuštanje igre

    /** Objekat koji predstavlja ploču za igru. */
    private Ploca ploca;

    /**
     * Konstruktor klase Igra.
     * Inicijalizuje novu ploču za igru.
     * 
     * @param veličina ploče na kojoj će se igrati
     */
    public Igra(int v) {
        ploca = new Ploca(v);
    }

    /**
     * Vraća trenutnu ploču igre.
     * 
     * @return Objekat ploče.
     */
    public Ploca getPloca() {
        return ploca;
    }

    /**
     * Provjerava da li je korisnik postigao max_pobjedu (8196 na ploči).
     * 
     * @return True ako korisnik pobijedi, inače false.
     */
    public boolean korisnikPobijedio() {
        return ploca.traziNaPloci(Ploca.KRAJ_IGRE);
    }
    
    /**
     * Provjerava da li je korisnik postigao pobjedu (2048 na ploči).
     * 
     * @return True ako korisnik pobijedi, inače false.
     */
    public boolean dostigao2048() {
    	return ploca.traziNaPloci(Ploca.POBJEDA);
    }

    /**
     * Provjerava da li je igra završena (pobjeda ili nema više poteza).
     * 
     * @return True ako je igra završena, inače false.
     */
    public boolean daLiJeKraj() {
        if (korisnikPobijedio()) {
            return true; // Igra završena pobjedom
        }
        if (ploca.traziNaPloci(Ploca.PRAZNO_POLJE)) {
            return false; // Ima praznih polja, igra nije završena
        }
        return !korisnikImaPotez(); // Provjera da li ima validnih poteza
    }

    /**
     * Provjerava da li korisnik ima barem jedan validan potez.
     * 
     * @return True ako postoji potez, inače false.
     */
    public boolean korisnikImaPotez() {
        int ploca[][] = this.ploca.getPloca();

        // Provjera susjednih elemenata (horizontalno i vertikalno)
        for (int i = 0; i < (Ploca.VELICINA - 1); i++) {
            for (int j = 0; j < (Ploca.VELICINA - 1); j++) {
                if (ploca[i][j] == ploca[i][j + 1] || ploca[i][j] == ploca[i + 1][j]) {
                    return true; // Postoji validan potez
                }
            }
        }

        // Provjera posljednjeg reda
        for (int j = 0; j < (Ploca.VELICINA - 1); j++) {
            if (ploca[Ploca.VELICINA - 1][j] == ploca[Ploca.VELICINA - 1][j + 1]) {
                return true;
            }
        }

        // Provjera posljednje kolone
        for (int i = 0; i < (Ploca.VELICINA - 1); i++) {
            if (ploca[i][Ploca.VELICINA - 1] == ploca[i + 1][Ploca.VELICINA - 1]) {
                return true;
            }
        }

        return false; // Nema validnih poteza
    }

    /**
     * Pomjera red ulijevo i kombinuje susjedne elemente ako su isti.
     * 
     * @param red Niz koji predstavlja jedan red ploče.
     * @return Novi niz nakon pomjeranja ulijevo.
     */
    public int[] lijeviPotez(int red[]) {
        int noviRed[] = new int[Ploca.VELICINA];
        int j = 0;

        // Pomicanje svih nepraznih brojeva ulijevo
        for (int i = 0; i < Ploca.VELICINA; i++) {
            if (red[i] != 0) {
                noviRed[j++] = red[i];
            }
        }

        // Kombinacija susjednih elemenata ako su isti
        for (int i = 0; i < Ploca.VELICINA - 1; i++) {
            if (noviRed[i] != 0 && noviRed[i] == noviRed[i + 1]) {
                noviRed[i] = 2 * noviRed[i];
                ploca.dodajScore(noviRed[i]);

                // Pomicanje ostatka reda nakon kombinacije
                for (j = i + 1; j < Ploca.VELICINA - 1; j++) {
                    noviRed[j] = noviRed[j + 1];
                }
                noviRed[Ploca.VELICINA - 1] = 0;
            }
        }
        return noviRed;
    }

    /**
     * Obrće red elemenata.
     * 
     * @param niz Niz koji treba obrnuti.
     * @return Obrnuti niz.
     */
    public int[] obrnutiNiz(int niz[]) {
        int[] obrnutiNiz = new int[niz.length];
        for (int i = niz.length - 1; i >= 0; i--) {
            obrnutiNiz[i] = niz[niz.length - i - 1];
        }
        return obrnutiNiz;
    }

    /**
     * Pomjera red udesno koristeći lijevi potez i obrnut red.
     * 
     * @param red Niz koji predstavlja jedan red ploče.
     * @return Novi niz nakon pomjeranja udesno.
     */
    public int[] desniPotez(int red[]) {
        int noviRed[] = obrnutiNiz(red);
        noviRed = lijeviPotez(noviRed);
        return obrnutiNiz(noviRed);
    }

    /**
     * Provjerava da li je potez promijenio stanje ploče.
     * 
     * @param staraPloca Stara ploča prije poteza.
     * @param novaPloca Nova ploča nakon poteza.
     * @return True ako je potez napravljen, inače false.
     */
    private boolean napravljenPotez(int[][] staraPloca, int[][] novaPloca) {
        for (int i = 0; i < Ploca.VELICINA; i++) {
            for (int j = 0; j < Ploca.VELICINA; j++) {
                if (staraPloca[i][j] != novaPloca[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Izvršava potez na osnovu unosa korisnika.
     * 
     * @param potez Karakter koji predstavlja potez.
     * @return True ako je potez napravljen, inače false.
     */
    public boolean odigrajPotez(char potez) {
        int[][] ploca = this.ploca.getPloca();

        // Izvršavanje poteza zavisno od unosa
        switch (potez) {
            case POMJERI_LIJEVO:
                for (int i = 0; i < Ploca.VELICINA; i++) {
                    int noviRed[] = lijeviPotez(ploca[i]);
                    System.arraycopy(noviRed, 0, ploca[i], 0, Ploca.VELICINA);
                }
                break;

            case POMJERI_DESNO:
                for (int i = 0; i < Ploca.VELICINA; i++) {
                    int noviRed[] = desniPotez(ploca[i]);
                    System.arraycopy(noviRed, 0, ploca[i], 0, Ploca.VELICINA);
                }
                break;

            case POMJERI_GORE:
                for (int j = 0; j < Ploca.VELICINA; j++) {
                    int red[] = new int[Ploca.VELICINA];
                    for (int i = 0; i < Ploca.VELICINA; i++) {
                        red[i] = ploca[i][j];
                    }
                    int noviRed[] = lijeviPotez(red);
                    for (int i = 0; i < Ploca.VELICINA; i++) {
                        ploca[i][j] = noviRed[i];
                    }
                }
                break;

            case POMJERI_DOLE:
                for (int j = 0; j < Ploca.VELICINA; j++) {
                    int red[] = new int[Ploca.VELICINA];
                    for (int i = 0; i < Ploca.VELICINA; i++) {
                        red[i] = ploca[i][j];
                    }
                    int noviRed[] = desniPotez(red);
                    for (int i = 0; i < Ploca.VELICINA; i++) {
                        ploca[i][j] = noviRed[i];
                    }
                }
                break;
        }

        // Provjera da li je potez promijenio stanje ploče
        boolean potezNapravljen = napravljenPotez(this.ploca.getPloca(), ploca);
        this.ploca.setPloca(ploca);
        return potezNapravljen;
    }
}
