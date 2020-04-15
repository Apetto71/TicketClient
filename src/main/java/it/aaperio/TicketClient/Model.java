package it.aaperio.TicketClient;

import org.apache.log4j.Logger;


public class Model {

	private static Model model = null;
	
	static private Logger logger;
	private static Configuration config;
	
	
	// Costruttore della classe singleton
	private Model() {}
	
	
	// Costruttore Singleton
		public static Model getModel() {
			Model m;
			if (model == null)	{
				m = new Model();
				model = m;
			} else {m = model;};
			
			config = new Configuration();
			
			return m;
		}
}
