package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.AccountService;

/**
 *
 *
 */
public class ResetPasswordServlet extends HttpServlet 
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        String uuid = request.getParameter("uuid");
        
        if(uuid != null)
        {
            request.setAttribute("uuid", uuid);
            getServletContext().getRequestDispatcher("/WEB-INF/resetNewPassword.jsp").forward(request, response);
        } 
        else 
        {
            getServletContext().getRequestDispatcher("/WEB-INF/reset.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        AccountService as = new AccountService();
        String uuid = request.getParameter("uuid");
        
        if(uuid == null)
        {
            String url = request.getRequestURL().toString();
            String path = getServletContext().getRealPath("/WEB-INF");
            String email = request.getParameter("resetEmail");
        
            as.resetPassword(email, path, url);
            request.setAttribute("message", "Reset email sent");
            getServletContext().getRequestDispatcher("/WEB-INF/reset.jsp").forward(request, response);   
        } 
        else 
        {
            String newPassword = request.getParameter("newPassword");
            boolean passwordChangeStatus = as.changePassword(uuid, newPassword);
            
            String passwordChangeMessage = passwordChangeStatus ? "Successfully changed password" : "Unable to change password";
            request.setAttribute("message", passwordChangeMessage);
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response); 
        }
    }
}