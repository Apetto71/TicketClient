package it.aaperio.TicketClient.Model;

public class TestModel {

	public static void main(String[] args) {
		System.out.println("Faccio partire il model");
		Model model = Model.getModel();
			
		System.out.println("Faccio partire la connessione a " + Configuration.getHOST() + ":" + Integer.parseInt(Configuration.getPORTA()));
		model.StartConnection(Configuration.getHOST(), Integer.parseInt(Configuration.getPORTA()));
		
		if (model.isConnected()) {
			int i = 0;
			System.out.println("Faccio partire 10 messaggi di Buongiorno");
			
				boolean c = model.sendString("Buongiorno");
				if (c) {
				System.out.println ("Messaggio di Buongiorno inviato correttamente");
			} else {System.out.println ("Messaggio di Buongiorno non inviato") ;}
			
			
		}
		
		
	}

}
