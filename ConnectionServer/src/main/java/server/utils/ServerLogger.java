package server.utils;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;



public class ServerLogger {
	
	
	private static ServerLogger instance;
	private Logger logger = Logger.getLogger("MyLog");  
    
	private ServerLogger() {
	    try {  
	    	FileHandler fh;  
	        fh = new FileHandler("logfile.log");  
	        logger.addHandler(fh);
	        SimpleFormatter formatter = new SimpleFormatter();
	        fh.setFormatter(formatter);  
	    } catch (SecurityException e) {  
	        e.printStackTrace();  
	    } catch (IOException e) {
			
			e.printStackTrace();
		} 
	    logger.info("-------\t Startup \t--------");  
	}
	
	
	public void warn(String method, String message) {
		logger.warning(String.format("%18s|%30s", method,message ));
	}
	
	public void info(String message) {
		logger.warning(message);
	}
	
	public void error(String method, String className,String message) {
		logger.warning("ERROR!");
		logger.warning(String.format("%18s|%18s|%30s", method, className,message));
	}
	
	
	public static ServerLogger getInstance() {
		if(ServerLogger.instance == null) {
			ServerLogger.instance = new ServerLogger();
		}
		return ServerLogger.instance;
		
		
	}
	

	
}
