package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.guiController;
import model.Igra;
import model.Ploca;

/**
 * Klasa Prozor predstavlja glavni prozor igre 2048.
 * Odgovorna je za prikazivanje mreže igre, obradu korisničkog unosa putem tastature,
 * ažuriranje rezultata i prikazivanje dijaloga za kraj igre.
 */
public class Prozor extends JFrame {
	
	 private static final long serialVersionUID = 1L;

    /**
     * Konstantna vrijednost koja predstavlja širinu jednog polja u igri.
     */
    private static final int SIRINA_POLJA = 100;

    /**
     * Konstantna vrijednost koja predstavlja visinu jednog polja u igri.
     */
    private static final int VISINA_POLJA = 100;

    /**
     * Konstantna vrijednost koja predstavlja širinu prozora na osnovu veličine mreže.
     */
    private static final int SIRINA_PROZORA = SIRINA_POLJA * Ploca.VELICINA + 50;

    /**
     * Konstantna vrijednost koja predstavlja visinu prozora na osnovu veličine mreže.
     */
    private static final int VISINA_PROZORA = VISINA_POLJA * Ploca.VELICINA + 100;

    /**
     * Boje koje se koriste za različite vrijednosti u igri 2048.
     */
    private static final Color BOJA_PRAZNO_POLJE = new Color(197, 183, 170);
    private static final Color BOJA_2 = new Color(240, 240, 240);
    private static final Color BOJA_4 = new Color(237, 224, 200);
    private static final Color BOJA_8 = new Color(242, 177, 121);
    private static final Color BOJA_16 = new Color(245, 149, 99);
    private static final Color BOJA_32 = new Color(246, 124, 95);
    private static final Color BOJA_64 = new Color(246, 94, 59);
    private static final Color BOJA_128 = new Color(237, 207, 114);
    private static final Color BOJA_256 = new Color(237, 204, 97);
    private static final Color BOJA_512 = new Color(237, 200, 80);
    private static final Color BOJA_1024 = new Color(237, 197, 63);
    private static final Color BOJA_2048 = new Color(237, 194, 46);
    private static final Color BOJA_4096 = new Color(235, 104, 109);
    private static final Color BOJA_8192 = new Color(231, 79, 91);

    /**
     * Flagg koji označava da li igra treba učitati sačuvano stanje.
     */
    public static boolean UCITAJ;

    /**
     * Unutrašnja klasa koja predstavlja jedno polje u mreži igre.
     * Odgovorna je za prikazivanje broja u tom polju i ažuriranje pozadinske boje.
     */
    private class IgraPolje extends JLabel {

    	 private static final long serialVersionUID = 1L;
    	 
        /**
         * Konstruktor za kreiranje objekta IgraPolje.
         * Postavlja podrazumijevane osobine, kao što su veličina, font i pozadinska boja.
         */
        public IgraPolje() {
            super("", SwingConstants.CENTER);

            setOpaque(true);
            setPreferredSize(new Dimension(SIRINA_POLJA, VISINA_POLJA));
            setBorder(BorderFactory.createLineBorder(new Color(147, 133, 120), 3));
            setBackground(BOJA_PRAZNO_POLJE);
            setFont(new Font("Serif", Font.BOLD, 40));
        }

        /**
         * Postavlja broj u polju i ažurira pozadinsku boju u skladu sa tim.
         *
         * @param n Broj koji treba prikazati u polju.
         */
        public void postaviBroj(int n) {
            if (n == 0) {
                setText("");
            } else {
                setText("" + n);
            }

            // Ažuriranje pozadinske boje na osnovu broja
            switch (n) {
                case 0: setBackground(BOJA_PRAZNO_POLJE); break;
                case 2: setBackground(BOJA_2); break;
                case 4: setBackground(BOJA_4); break;
                case 8: setBackground(BOJA_8); break;
                case 16: setBackground(BOJA_16); break;
                case 32: setBackground(BOJA_32); break;
                case 64: setBackground(BOJA_64); break;
                case 128: setBackground(BOJA_128); break;
                case 256: setBackground(BOJA_256); break;
                case 512: setBackground(BOJA_512); break;
                case 1024: setBackground(BOJA_1024); break;
                case 2048: setBackground(BOJA_2048); break;
                case 4096: setBackground(BOJA_4096); break;
                case 8192: setBackground(BOJA_8192); break;
            }
        }
    }

    private JLabel rezultat;
    private JLabel highscore;

    private IgraPolje polja[][];

    private guiController igraController;

    /**
     * Konstruktor za klasu Prozor. Inicijalizuje prozor i postavlja slušatelje tastature.
     */
    public Prozor() {
        setup();

        setSize(new Dimension(SIRINA_PROZORA, VISINA_PROZORA));
        setTitle("2048");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    /**
     * Inicijalizuje kontroler igre.
     *
     * @param igraController Kontroler odgovoran za logiku igre.
     */
    public void inizijalizirajController(guiController igraController) {
        this.igraController = igraController;
    }

    /**
     * Postavlja izgled prozora i inicijalizuje komponente kao što su labela za rezultat i mreža igre.
     */
    private void setup() {
        JPanel glavniPanel = new JPanel(new BorderLayout());

        JPanel gornjiPanel = new JPanel(new BorderLayout());
        rezultat = new JLabel("Rezultat: 0");
        highscore = new JLabel("Najveći rezultat: 0");

        rezultat.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 0));
        highscore.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 15));
        highscore.setHorizontalAlignment(SwingConstants.RIGHT);

        gornjiPanel.add(rezultat, BorderLayout.WEST);
        gornjiPanel.add(highscore, BorderLayout.EAST);

        JPanel centralniPanel = new JPanel(new GridLayout(Ploca.VELICINA, Ploca.VELICINA));

        if (novaIgraDijalog()) {
            UCITAJ = true;
        } 
        else {
            UCITAJ = false;
        }

        polja = new IgraPolje[Ploca.VELICINA][Ploca.VELICINA];
        for (int i = 0; i < Ploca.VELICINA; i++) {
            for (int j = 0; j < Ploca.VELICINA; j++) {
                polja[i][j] = new IgraPolje();
                centralniPanel.add(polja[i][j]);
            }
        }

        glavniPanel.add(gornjiPanel, BorderLayout.NORTH);
        glavniPanel.add(centralniPanel, BorderLayout.CENTER);

        addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) { }

            @Override
            public void keyReleased(KeyEvent e) { }

            @Override
            public void keyPressed(KeyEvent e) {
            	//WASD
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    igraController.igrajProzor(Igra.POMJERI_GORE);
                }

                if (e.getKeyCode() == KeyEvent.VK_S) {
                    igraController.igrajProzor(Igra.POMJERI_DOLE);
                }

                if (e.getKeyCode() == KeyEvent.VK_A) {
                    igraController.igrajProzor(Igra.POMJERI_LIJEVO);
                }

                if (e.getKeyCode() == KeyEvent.VK_D) {
                    igraController.igrajProzor(Igra.POMJERI_DESNO);
                }
                //Strelice
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    igraController.igrajProzor(Igra.POMJERI_GORE);
                }

                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    igraController.igrajProzor(Igra.POMJERI_DOLE);
                }

                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    igraController.igrajProzor(Igra.POMJERI_LIJEVO);
                }

                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    igraController.igrajProzor(Igra.POMJERI_DESNO);
                }
                //Ostale komande
                if (e.getKeyCode() == KeyEvent.VK_R) {
                    igraController.igrajProzor(Igra.RESTARTAJ_IGRU);
                }

                if (e.getKeyCode() == KeyEvent.VK_Q) {
                    if (potvrdiIzlaz()) {
                        igraController.igrajProzor(Igra.NAPUSTI_IGRU);
                        System.exit(0);
                    }
                }
            }
        });

        setContentPane(glavniPanel);
    }

    /**
     * Ažurira mrežu igre i prikazuje trenutni rezultat.
     *
     * @param ploca Trenutno stanje mreže igre.
     * @param rez Trenutni rezultat.
     * @param high Najveći rezultat.
     */
    public void updatePlocu(Ploca ploca, int rez, int high) {
        int[][] novaPloca = ploca.getPloca();
        for (int i = 0; i < Ploca.VELICINA; i++) {
            for (int j = 0; j < Ploca.VELICINA; j++) {
                polja[i][j].postaviBroj(novaPloca[i][j]);
            }
        }
        rezultat.setText("Rezultat: " + rez);
        highscore.setText("Najveći rezultat: " + high);
    }

    /**
     * Prikazuje dijalog za kraj igre sa porukom.
     *
     * @param poruka Poruka koja će biti prikazana u dijalogu.
     */
	public void ispisiKraj(String poruka) {
		JOptionPane.showMessageDialog(this, poruka, "Kraj igre!", JOptionPane.INFORMATION_MESSAGE);
		System.exit(0);
	}
	
	/**
	 * Prikazuje dijalog za odabir veličine ploče (4x4 ili 5x5).
	 * Korisnik bira na kojoj ploči želi da igra.
	 *
	 * @return 4 ako koirnik izabere 4x4 ili 5 ako korisnik izabere 5x5.
	 */
	public static int odabirPloce() {
	    String[] opcije = {"4x4", "5x5"};
	    
	    int izbor = JOptionPane.showOptionDialog(
	        null, 
	        "Izaberite veličinu ploče:", 
	        "Odabir ploče",
	        JOptionPane.DEFAULT_OPTION,
	        JOptionPane.QUESTION_MESSAGE,
	        null,
	        opcije,
	        opcije[0]
	    );

	    if (izbor == 0) {
	        return 4;
	    } 
	    return 5;
	}
	
	/**
	 * Prikazuje dijalog za odabir načina igre (Konzola ili Prozor).
	 * Korisnik bira između pokretanja igre u konzoli ili u prozoru.
	 *
	 * @return True ako korisnik izabere "Prozor", false ako izabere "Konzola".
	 */
	public static boolean pocetniDijalog() {
	    String[] opcije = {"Konzola", "Prozor"};
	    
	    int izbor = JOptionPane.showOptionDialog(
	        null, 
	        "Izaberite način igre:", 
	        "Odabir igre",
	        JOptionPane.DEFAULT_OPTION,
	        JOptionPane.QUESTION_MESSAGE,
	        null,
	        opcije,
	        opcije[1]
	    );

	    if (izbor == 0) {
	        System.out.println("Pokrećete igru u konzoli...");
	        return false;
	    } 
	    return true;
	}

	/**
	 * Prikazuje dijalog koji pita korisnika da li stvarno želi izaći iz igre.
	 *
	 * @return True ako korisnik izabere "Da", false ako izabere "Ne".
	 */
	public static boolean potvrdiIzlaz() {
	    String[] opcije = {"Da", "Ne"};
	    
	    int izbor = JOptionPane.showOptionDialog(
	        null, 
	        "Da li stvarno želite izaći?", 
	        "Potvrda izlaska", 
	        JOptionPane.YES_NO_OPTION, 
	        JOptionPane.QUESTION_MESSAGE,
	        null,
	        opcije, 
	        opcije[1] 
	    );
	    
	    return izbor == 0; 
	}

	/**
	 * Prikazuje dijalog za izbor između započinjanja nove igre ili nastavka trenutne igre.
	 *
	 * @return True ako korisnik izabere "Nova igra", false ako izabere "Nastavi".
	 */
	public static boolean novaIgraDijalog() {
	    String[] opcije = {"Nova igra", "Nastavi"};
	    
	    int izbor = JOptionPane.showOptionDialog(
	        null,
	        "Želite li započeti novu igru ili nastaviti?",
	        "Izbor igre",
	        JOptionPane.DEFAULT_OPTION, 
	        JOptionPane.QUESTION_MESSAGE,
	        null, 
	        opcije,  
	        opcije[0]
	    );
	    
	    return izbor == 0;
	}
	
	/**
	 * Prikazuje dijalog za izbor između nastavka ili izlaska iz igre
	 *
	 * Ako korisnik pritisne "Završi igru" zatvara igru, u suprotnom ne radi ništa
	 */
	public void prikaziPoruku2048() {
		String[] opcije = {"Nastavi", "Završi igru"};
	    int izbor = JOptionPane.showOptionDialog(
	        null,
	        "Čestitamo! Dostigli ste 2048. Želite li nastaviti igrati?",
	        "Pobjeda!",
	        JOptionPane.YES_NO_OPTION,
	        JOptionPane.INFORMATION_MESSAGE,
	        null,
	        opcije,
	        opcije[0]
	    );

	    if (izbor == JOptionPane.NO_OPTION) {
	        System.exit(0);
	    }
	}
}
