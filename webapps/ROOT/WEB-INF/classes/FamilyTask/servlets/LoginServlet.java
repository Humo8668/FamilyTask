package FamilyTask.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import FamilyTask.lib.Database;
import FamilyTask.lib.Log;

public class LoginServlet extends HttpServlet 
{
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException 
    {
        String name = request.getHeader("name");
        String password = request.getHeader("password");
        Boolean remember = Boolean.parseBoolean(request.getHeader("remember"));
        java.sql.Connection conn = Database.Connect();
        System.out.println(conn);
        System.out.println("123123");
    }
}