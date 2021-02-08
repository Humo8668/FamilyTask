package FamilyTask.lib;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.*;
import javax.naming.*;

public class Log
{
    private static Boolean isInit = false;
    private static Logger LOGGER = null;
    //private static String loggerName = "General";
    private static Handler fileHandler = null;
    private static ConsoleHandler consoleHandler = null;
    private static String logFileNamePrefix = "";
    private static String logFilePath = "";
    private static SimpleFormatter sFormatter = null;

    private static void Init()
    {
        if(isInit && LOGGER != null)
            return ;
        
        Context context, envContext;
        try
        {
            context = new InitialContext();
            envContext = (Context)context.lookup("java:/comp/env");
            Log.logFileNamePrefix = (String)envContext.lookup("log_filenameprefix");
            Log.logFilePath = (String)envContext.lookup("log_path");
        }
        catch(NamingException exception)
        {
            Log.logFileNamePrefix = "debug_log";
            Log.logFilePath = "./Log/";
        }
        Log.logFilePath = System.getProperty("catalina.base") + "/" + Log.logFilePath;
        
        try 
        {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime now = LocalDateTime.now();
            LOGGER  = Logger.getLogger("GlobalLogger");
            LOGGER.setLevel(Level.ALL);
            fileHandler = new FileHandler(Log.logFilePath + "/" + Log.logFileNamePrefix + "_" + dtf.format(now) + ".log", true) ;
            consoleHandler = new ConsoleHandler();
            LOGGER.addHandler(fileHandler);
            LOGGER.addHandler(consoleHandler);
            
            sFormatter = new SimpleFormatter() {
                private static final String format = "[%1$tF %1$tT] [%2$-7s] %4$s : %3$s %n";
      
                @Override
                public synchronized String format(LogRecord lr) {
                    return String.format(format,
                            new Date(lr.getMillis()),
                            lr.getLevel().getLocalizedName(),
                            lr.getMessage(),
                            lr.getSourceClassName() + "." + lr.getSourceMethodName()
                    );
                }
            };
            fileHandler.setFormatter(sFormatter);
            consoleHandler.setFormatter(sFormatter);
        }
        catch(Exception exc)
        {
            System.out.println("Error: Couldn't create or open Log file. Error message: " + exc.getMessage());
        }
        isInit = true;
        
        return;
    }

    public static void Info(String message)
    {
        if(!isInit)
            Log.Init();
        
        //System.out.println("Info: " + message);
        LOGGER.info(message);
    }

    public static void Error(String message)
    {
        if(!isInit)
            Log.Init();

        //System.out.println("Error: " + message);
        LOGGER.warning("Error: " + message);
    }

    protected void finalize() throws Throwable 
    {
        for(Handler h:LOGGER.getHandlers())
        {
            h.close();
        }
    } 
}
