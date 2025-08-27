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

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("select * from user where email = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                out.println("<script>alert('User not found! Redirecting to Sign Up.');window.location='signup.html';</script>");
            } else {
                String storedPassword = rs.getString("password");
                if (!storedPassword.equals(password)) {
                    out.println("<script>alert('Password Incorrect. Please try again.');window.location='login.html';</script>");
                } else {
                    out.println("<script>alert('Login Successful!');window.location='create_group.html';</script>");
                }
            }
            ps.close();
            conn.close();
        } catch (SQLException e) {
            out.println("<script>alert('Database Error: " + e.getMessage() + "');window.location='login.html';</script>");
        }
    }
}
