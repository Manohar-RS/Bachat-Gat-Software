import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/DeleteLoanServlet")
public class DeleteLoanServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        int loanID = Integer.parseInt(request.getParameter("LoanID"));

        try (Connection conn = DBConnection.getConnection()) {
            String query = "DELETE FROM loan WHERE LoanID = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, loanID);

            int result = ps.executeUpdate();
            if (result > 0) {
                out.println("<script>");
                out.println("alert('Loan Detail deleted successfully!'); window.location='ViewLoanServlet';");
                out.println("</script>");
            } else {
                out.println("<script>alert('Error occured. Try again.'); window.location='ViewLoanServlet';</script>");
            }
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<script>alert('Error: " + e.getMessage() + "'); window.location='ViewLoanServlet';</script>");
        }
    }
}
