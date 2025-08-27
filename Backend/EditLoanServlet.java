import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;

@WebServlet("/EditLoanServlet")
public class EditLoanServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String loanID = request.getParameter("LoanID");
        
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM loan WHERE LoanID = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, loanID);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                request.setAttribute("LoanID", rs.getInt("LoanID"));
                request.setAttribute("MemberID", rs.getString("MemberID"));
                request.setAttribute("MemberName", rs.getString("MemberName"));
                request.setAttribute("BankLoan", rs.getDouble("BankLoan"));
                request.setAttribute("BankLoanDate", rs.getString("BankLoanDate"));
                request.setAttribute("BankLoanDuration", rs.getString("BankLoanDuration"));
                request.setAttribute("InternalLoan", rs.getDouble("InternalLoan"));
                request.setAttribute("InternalLInterest", rs.getString("InternalLInterest"));
                //request.setAttribute("InternalLoanDate", rs.getString("InternalLoanDate"));
                //request.setAttribute("Saving", rs.getString("Saving"));
                
                RequestDispatcher dispatcher = request.getRequestDispatcher("edit_loan.jsp");
                dispatcher.forward(request, response);
            } else {
                response.getWriter().println("<script>alert('Loan record not found!'); window.location='ViewLoanServlet';</script>");
            }
            ps.close();
        } catch (Exception e) {
            response.getWriter().println("<script>alert('Error fetching loan data: " + e.getMessage() + "'); window.location='ViewLoanServlet';</script>");
        }
    }
}



//<label for="MemberID">Member ID</label>
//<input type="number" id="MemberID" name="MemberID" value="${MemberID}" placeholder="Enter The Member ID" required>

