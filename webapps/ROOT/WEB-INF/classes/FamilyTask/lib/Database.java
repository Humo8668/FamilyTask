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

    static ResultSet ExecuteSelect(String sql)
    {
        Connection conn = Database.Connect();
        if(conn == null)
            return null;
        System.out.println(sql);
        PreparedStatement stmt = null;
        ResultSet rs = null;    
        try(PreparedStatement st = conn.prepareStatement(sql))
        {
            rs = st.executeQuery();
            //conn.commit();
        }
        catch(SQLException ex)
        {
            //conn.rollback();
            Log.Error("SQL error: " + ex.getMessage());
            rs = null;
        }

        return rs;
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
        String columnNames = "";
        String sql_query = String.format(SQL_select, "*", tableName, "1=1", "1");
        System.out.println(tableName);

        ResultSet rs = Database.ExecuteSelect(sql_query);
        System.out.println(rs == null);
        if(rs == null)
        {
            dt = new DataTable();
            return dt;
        }
        try
        {
            ResultSetMetaData rsmd = rs.getMetaData();
            System.out.println(rsmd.getColumnCount());
            for(int i = 0; i < rsmd.getColumnCount(); i++)
                columnNames += (rsmd.getColumnName(i) + ",");
            
            System.out.println("Columns: ");
            System.out.println(columnNames);
            columnNames = columnNames.substring(0, columnNames.length() - 1);   // remove last comma
            dt = new DataTable(columnNames);

            System.out.println("On create: \n");
            System.out.println(dt);

            ArrayList<Object> row = new ArrayList<Object>(rsmd.getColumnCount());
            LinkedList<ArrayList<Object>> rows = new LinkedList<ArrayList<Object>>();
            for(int i = 0; i < rsmd.getColumnCount(); i++)
                row.add("");
            while(rs.next())
            {
                System.out.println(rs.getObject(0));
                for(int i = 0; i < rsmd.getColumnCount(); i++)
                    row.set(i, rs.getObject(i));
                
                rows.add(row);
            }

            dt.addAll(rows);
        }
        catch(SQLException ex) { Log.Error(ex.getMessage()); }
        return dt;
    }
}
