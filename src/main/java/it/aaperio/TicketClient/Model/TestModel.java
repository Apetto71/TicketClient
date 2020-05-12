package it.aaperio.TicketClient.Model;

import java.io.IOException;

import it.aaperio.ticketserver.model.Comandi;
import it.aaperio.ticketserver.model.Messaggio;
import it.aaperio.ticketserver.model.User;

public class TestModel {

	Model m = Model.getModel();
	
	
	public static void main(String[] args) {
		TestModel test = new TestModel() ;
		test.run() ;

	}

	
	public void run() {
		System.out.println("Mi collego con " + Configuration.getHOST() + " " + Configuration.getPORTA()) ;
		m.StartConnection(Configuration.getHOST(), Integer.parseInt(Configuration.getPORTA())) ;
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		User u1 = new User("aaperio", "Password_personale") ;
		System.out.println("Utente creato " + u1.toString()) ;
		m.autorizza(u1) ;
		
		
		User u2 = new User("mtarsilla", "PasswordTarsilla") ;
		System.out.println("Utente creato " + u2.toString()) ;
		m.autorizza(u2) ;
		
		
		
		
	}
}
