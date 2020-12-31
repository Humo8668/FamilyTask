package FamilyTask.filters;

import java.io.IOException;
 
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AccessFilter implements Filter 
{
    public AccessFilter() 
    {
    }
 
    @Override
    public void init(FilterConfig fConfig) throws ServletException
    {
    }
 
    @Override
    public void destroy() 
    {
    }
 
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException 
    {
                
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String path = req.getServletPath();

        if( path.contains("/public") ||
            path.equals("/") || path.isEmpty() || path.equals("/index.jsp") ||
            (path.equals("/login") && req.getMethod().toLowerCase().equals("post")) ||
            path.contains("/users"))
            chain.doFilter(request, response);
        else
            res.sendRedirect("/");


        return;
    }
}
