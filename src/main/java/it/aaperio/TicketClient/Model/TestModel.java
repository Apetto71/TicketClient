package it.aaperio.TicketClient.Model;

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
		System.out.println("La coda è vuota? " + m.getCodamsginput().isEmpty()) ;
		if (!m.getCodamsginput().isEmpty()) {
			System.out.println("Il session ID ricevuto è: " + m.getCodamsginput().peek().getSessionId().toString()) ;
		}
		
	}
}
