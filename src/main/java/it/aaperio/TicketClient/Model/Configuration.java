package it.aaperio.TicketClient.Model;

import java.util.Properties;
import java.io.*;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;





	public class Configuration {
	private static Configuration configurazione;
	private static Logger logger ;
	private static Properties sys_prop =  System.getProperties();
	private static Properties config;
	
	// Imposto parametri di configuarazione di default
	private static final String FILE_SEPARATOR = sys_prop.getProperty("file.separator");
	private static final String LINE_SEPARATOR = sys_prop.getProperty("line.separator");
	private static final String OS_NAME = sys_prop.getProperty("os.name");
	private static final String OS_VERSION = sys_prop.getProperty("os.version");
	private static final String USER_DIR = sys_prop.getProperty("user.dir");
	private static final String USER_HOME = sys_prop.getProperty("user.home");
	private static final String WORK_DIR = new String(USER_HOME + FILE_SEPARATOR + ".TicketClient");
			
	// File di configurazione:
	private static File config_file;
	// Parametri di connessione al server ed al DB
	private static String PORTA = new String("30500");
	private static String HOST = new String();
	private static String LAST_USER = new String();

	// Costruttore vuoto per classe singleton
	private Configuration() {};
	
	public static Configuration getConfiguration() {
		Configuration c;
		if (configurazione == null) {
			c= new Configuration();
			configurazione = c;
		} else {c = configurazione;};
		
		// Verifico la presenza della directory e file di configurazione del logger
		File work_dir = new File(WORK_DIR);
		if (!work_dir.exists())		{
			work_dir.mkdir();
		}
		
		// Verifico che esista la configurazione del logger, se non esiste la creo
		File log_file = new File (WORK_DIR + FILE_SEPARATOR +"TKClientLog.cfg");
		if (!log_file.exists())	{
			try {
				log_file.createNewFile();
				log_file.setWritable(true);
				FileWriter fw = new FileWriter (log_file.toString());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write("#log4j.properties\r\n" + 
						"#LOGGER\r\n" + 
						"log4j.rootLogger=INFO,  APPENDER_FILE \r\n" + 
						"#, APPENDER_OUT,\r\n" + 
						"#APPENDER_OUT\r\n" + 
						"//log4j.appender.APPENDER_OUT=org.apache.log4j.ConsoleAppender\r\n" + 
						"//log4j.appender.APPENDER_OUT.layout=org.apache.log4j.PatternLayout\r\n" + 
						"//log4j.appender.APPENDER_OUT.layout.ConversionPattern=%5p [%t] (%F:%L) - %m%n\r\n" + 
						"#APPENDER_FILE\r\n" + 
						"log4j.appender.APPENDER_FILE=org.apache.log4j.RollingFileAppender\r\n" + 
						"log4j.appender.APPENDER_FILE.File=TKClient.log\r\n" + 
						"log4j.appender.APPENDER_FILE.MaxFileSize=1000KB\r\n" + 
						"log4j.appender.APPENDER_FILE.MaxBackupIndex=1\r\n" + 
						"log4j.appender.APPENDER_FILE.layout=org.apache.log4j.PatternLayout\r\n" + 
						"log4j.appender.APPENDER_FILE.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %p [%C:%L] - %m%n");
				bw.flush();
				bw.close();
			} catch (IOException e) {System.out.println("Errore nella creazione del file di configurazione logger");}
		}
		
		// instanzio il logger e inizio a loggare
		PropertyConfigurator.configure(log_file.toString());
		logger = Logger.getLogger(Configuration.class);
		logger.info("TicketClient attivo");
		
		// Verifico che esista il file di configurazione dell'applicazione
		config = new Properties();
		config_file = new File (WORK_DIR + FILE_SEPARATOR + "TKClient.cfg");
		
		
		logger.info("Verifico che il file di configurazione dell'applicazione esista");
		if (!config_file.exists()) {
			try {
				config_file.createNewFile();
				logger.info("Il file di configurazione non esiste. Definisco il file di configurazione del client: " + config_file.toString());
			} catch (IOException e) {
				logger.error("Errore nella creazione del file di configurazione",e);
			}
		
			//Definisco i parametri di default che vengono salvati nel file
			logger.info("Imposto i parametri di configurazione di default");
			config.setProperty("PORTA", "30500");
			config.setProperty("HOST", "localhost");
			config.setProperty("LAST_USER", "");
						
			try {
				logger.info("Definisco lo Stream per salvare i valori di default");
				FileOutputStream out;
				out = new FileOutputStream(config_file);
				config.store(out, "Commento");
				out.close();
			} catch (IOException e) {
				logger.error("Errore nel salvataggio dei parametri di configurazione",e);
			}
		}
		
		logger.info("Carico il file di configurazione");
		FileInputStream in = null;
		try {
			logger.debug("Definisco lo Stream in input per leggere il file di configurazione");
			in = new FileInputStream (config_file);
			logger.debug("Inizializzato il FileInputStream in: " + in.toString());
		} catch (FileNotFoundException e) {
			logger.error("Errore nella creazione dello Stream di input File",e);
		}
		
		try {
			config.load(in);
			logger.debug("Caricati i parametri di configurazione: " + config.toString());
		} catch (IOException e) {
			logger.error("Errore in lettura file configurazione", e);
		}
		try {
			logger.debug("Chiudo lo stream per la lettura del file di configurazione");
			in.close();
		} catch (IOException e) {
			logger.error("Errore in chiusura del file di configurazione", e);
		}
		
		
		//  imposto le variabili di classe con il contenuto di quanto letto
		// dal file di configurazione
		logger.info("Imposto i parametri di configurazione letti dal file");
		setPORTA(config.getProperty("PORTA"));
		setHOST(config.getProperty("HOST"));
		setLAST_USER(config.getProperty("LAST_USER"));
		
		
		
		return c;
	}

	public static String getPORTA() {
		return PORTA;
	}

	public static void setPORTA(String pORTA) {
		logger.debug("Imposto la PORTA a:" + pORTA);
		PORTA = pORTA;
	}

	public static String getHOST() {
		return HOST;
	}

	public static void setHOST(String hOST) {
		logger.debug("Imposto HOST a:" + hOST);
		HOST = hOST;
	}

	public static String getLAST_USER() {
		return LAST_USER;
	}

	public static void setLAST_USER(String lAST_USER) {
		logger.debug("Imposto la LAST_USER a:" + lAST_USER);
		LAST_USER = lAST_USER;
	}
	
	
}
