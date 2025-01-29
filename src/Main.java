
import controller.consoleController;
import controller.guiController;
import model.Igra;
import view.Prozor;

/**
 * 
 * @author Eldin Popara
 * @version 06.12.2024
 *
 */

public class Main {
	
	/**
	 * Main klasa namjenjena za pokretanje cijele igrice
	 * @param args
	 */

	public static void main(String[] args) {
		
		int v = Prozor.odabirPloce();

		Igra igra = new Igra(v);
				
		/**
		 * Odabir da li igramo u konzoli ili u prozoru
		 * @param izbor
		 */
		boolean izbor = Prozor.pocetniDijalog();
		if(!izbor) {
			
			consoleController consoleController = new consoleController();
			
			consoleController.inicijaliziraj(igra);
			
			consoleController.igrajKonzola();
		}
		else {
			Prozor prozor = new Prozor();
			
			guiController guicontroller = new guiController();
			
			guicontroller.inicijaliziraj(igra);
			
			guicontroller.inicijalizirajProzor(prozor);
			
		    prozor.inizijalizirajController(guicontroller);
		    
		} 
	}
}
