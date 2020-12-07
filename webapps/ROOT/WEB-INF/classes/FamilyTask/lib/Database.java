package FamilyTask.lib;

import java.sql.*;
import javax.naming.*;
import javax.sql.DataSource;
import java.util.*;

public class Database {
    static String host = "localhost";
    static String port = "5433";
    static String dataBaseName = "FamilyTask";
    static String userName = "postgres";
    static String password = "123";

    static Boolean isInitialized = false;
    //static String connectionString = "jdbc:postgresql://" + userName + ":" + password + "@" + host + ":" + port + "/" + dataBaseName;

    public static void Init()
    {
        try
        {
            Context context = new InitialContext();
            Context envContext = (Context) context.lookup("java:/comp/env");
            
            Database.host = (String) envContext.lookup("db_host");
            Database.port = (String) envContext.lookup("db_port");
            Database.dataBaseName = (String) envContext.lookup("db_name");
            Database.userName = (String) envContext.lookup("db_username");
            Database.password = (String) envContext.lookup("db_password");
        }
        catch(NamingException exc)
        {
            Log.Error(exc.getMessage());
        }

        Database.isInitialized = true; 
    }

    public static Connection Connect()
    {
        if(!Database.isInitialized)
            Database.Init();

        Properties connectionProps = new Properties();
        connectionProps.put("user", Database.userName);
        connectionProps.put("password", Database.password);
        Connection conn = null;
        
        try
        {
            conn = DriverManager.getConnection("jdbc:postgresql://" + Database.host + ":" + Database.port + "/" + dataBaseName, connectionProps);
        }
        catch(SQLException exception)
        {
            
            Log.Error(exception.getErrorCode() + ": " + exception.getMessage());
            return null;
        }
        return conn;
    }
}
