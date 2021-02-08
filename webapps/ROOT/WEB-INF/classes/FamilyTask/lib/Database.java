package FamilyTask.lib;

import java.sql.*;
import javax.naming.*;
import java.util.*;
import java.util.regex.*;

public class Database {
    /* ********************* Connection settings ********************* */
    static String host = "localhost";
    static String port = "5433";
    static String dataBaseName = "FamilyTask";
    static String userName = "postgres";
    static String password = "123";

    /* ********************* Common SQL queries ********************* */
    static String SQL_select = "SELECT %s FROM %s where %s order by %s ;";
    static String SQL_insert = "insert into %s (%s) values (%s) where %s;";
    static String SQL_update = "update %s set %s where %s;";
    /* ************************************************************** */
        
    static Boolean isInitialized = false;

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

    static DataTable ExecuteSelect(String sql)
    {
        Connection conn = Database.Connect();
        DataTable dt = null;
        if(conn == null)
            return null;
            
        PreparedStatement stmt = null;
        ResultSet rs = null;    
        try(PreparedStatement st = conn.prepareStatement(sql))
        {
            rs = st.executeQuery();
        }
        catch(SQLException ex)
        {
            Log.Error("SQL error: " + ex.getMessage());
            rs = null;
        }

        if(rs == null)
        {
            dt = new DataTable();
            return dt;
        }
        String columnNames = "";
        try
        {
            ResultSetMetaData rsmd = rs.getMetaData();
            for(int i = 1; i <= rsmd.getColumnCount(); i++)
                columnNames += (rsmd.getColumnName(i) + ",");
            
            columnNames = columnNames.substring(0, columnNames.length() - 1);   // remove last comma
            dt = new DataTable(columnNames);

            ArrayList<Object> row = new ArrayList<Object>(rsmd.getColumnCount());
            LinkedList<ArrayList<Object>> rows = new LinkedList<ArrayList<Object>>();
            for(int i = 1; i <= rsmd.getColumnCount(); i++)
                row.add("");
            while(rs.next())
            {
                for(int i = 1; i <= rsmd.getColumnCount(); i++)
                    row.set(i-1, rs.getObject(i));
                
                rows.add(row);
            }

            dt.addAll(rows);
        }
        catch(SQLException ex) 
        { 
            Log.Error(ex.getMessage()); 
            dt = new DataTable();
        }

        return dt;
    }

    static Boolean hasSqlInjection(String parameter)
    {
        Pattern p_baseKeywords = Pattern.compile("(?i).*\\b(select|insert|update|delete|create|table|from|where|order|by|row|drop|)+\\b.*");
        Pattern p_signs = Pattern.compile(".*([\"%]+).*");
        Matcher m_baseKeywords = p_baseKeywords.matcher(parameter);
        Matcher m_signs = p_signs.matcher(parameter);
        
        if(m_baseKeywords.matches() || m_signs.matches())
            return true;
        else
            return false;
    }

    public static DataTable getTable(String tableName)
    {
        DataTable dt = null;
        if(hasSqlInjection(tableName))
        {
            Log.Info("Sql injection occured: " + tableName);
            return new DataTable();
        }
        String sql_query = String.format(SQL_select, "*", tableName, "1=1", "1");
        dt = Database.ExecuteSelect(sql_query);
        
        return dt;
    }

    public static DataTable getTable(String tableName, String columnList)
    {
        DataTable dt = null;
        if(hasSqlInjection(tableName))
        {
            Log.Info("Sql injection occured: " + tableName);
            return new DataTable();
        }
        else if(hasSqlInjection(columnList))
        {
            Log.Info("Sql injection occured: " + columnList);
            return new DataTable();
        }
        
        String sql_query = String.format(SQL_select, columnList, tableName, "1=1", "1");
        dt = Database.ExecuteSelect(sql_query);
        
        return dt;
    }

    public static DataTable getTable(String tableName, String columnList, String whereCond)
    {
        DataTable dt = null;
        if(hasSqlInjection(tableName))
        {
            Log.Info("Sql injection occured: " + tableName);
            return new DataTable();
        }
        else if(hasSqlInjection(columnList))
        {
            Log.Info("Sql injection occured: " + columnList);
            return new DataTable();
        }
        else if(hasSqlInjection(whereCond))
        {
            Log.Info("Sql injection occured: " + columnList);
            return new DataTable();
        }
        
        String sql_query = String.format(SQL_select, columnList, tableName, whereCond, "1");
        dt = Database.ExecuteSelect(sql_query);
        
        return dt;
    }
}
