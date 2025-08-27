import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/DeleteBankServlet")
public class DeleteBankServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        int bankID = Integer.parseInt(request.getParameter("BankID"));

        try (Connection conn = DBConnection.getConnection()) {
            String query = "DELETE FROM bank WHERE BankID = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, bankID);

            int result = ps.executeUpdate();
            if (result > 0) {
                out.println("<script>");
                out.println("alert('Bank Detail deleted successfully!'); window.location='ViewBankServlet';");
                out.println("</script>");
            } else {
                out.println("<script>alert('Error occured. Try again.'); window.location='ViewBankServlet';</script>");
            }
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<script>alert('Error: " + e.getMessage() + "'); window.location='ViewBankServlet';</script>");
        }
    }
}
