import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/signupServlet")
public class signupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try (Connection conn = DBConnection.getConnection()) {
            // Check if the email is already registered
            String checkUserQuery = "select * from user where email = ?";
            PreparedStatement pst = conn.prepareStatement(checkUserQuery);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                out.println("<script>");
                out.println("alert('You Are Already Registered');");
                out.println("window.location.href='signup.html';");
                out.println("</script>");
            } else {
                // Insert the new user
                String insertUserQuery = "INSERT INTO user (username, email, password) VALUES (?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(insertUserQuery);
                ps.setString(1, username);
                ps.setString(2, email);
                ps.setString(3, password);
                int result = ps.executeUpdate();
                
                if (result > 0) {
                    out.println("<script>");
                    out.println("alert('Signup Successfully');");
                    out.println("window.location.href='login.html';");
                    out.println("</script>");
                } else {
                    out.println("<script>");
                    out.println("alert('Signup Failed. Please try again.');");
                    out.println("window.location.href='signup.html';");
                    out.println("</script>");
                }
                ps.close();
            }
            rs.close();
            pst.close();
        } catch (SQLException e) {
            out.println("<script>");
            out.println("alert('Error occurred: " + e.getMessage() + "');");
            out.println("window.location.href='signup.html';");
            out.println("</script>");
        }
        out.flush();
    }
}
