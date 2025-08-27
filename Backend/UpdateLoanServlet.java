import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/UpdateLoanServlet")
public class UpdateLoanServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Retrieving form parameters
        String loanID = request.getParameter("LoanID");
        String memberID = request.getParameter("MemberID");
        String memberName = request.getParameter("MemberName");
        double bankLoan = Double.parseDouble(request.getParameter("BankLoan"));
        String bankLoanDate = request.getParameter("BankLoanDate");
        String bankLoanDuration = request.getParameter("BankLoanDuration");
        double internalLoan = Double.parseDouble(request.getParameter("InternalLoan"));
        String internalLInterest = request.getParameter("InternalLInterest");
       // String saving = request.getParameter("Saving");

        try (Connection conn = DBConnection.getConnection()) {
            // SQL query to update loan details
            String query = "UPDATE loan SET MemberID=?, MemberName=?, BankLoan=?, BankLoanDate=?, BankLoanDuration=?, InternalLoan=?, InternalLInterest=? WHERE LoanID=?";
            PreparedStatement ps = conn.prepareStatement(query);

            // Setting parameters
            ps.setString(1, memberID);
            ps.setString(2, memberName);
            ps.setDouble(3, bankLoan);
            ps.setString(4, bankLoanDate);
            ps.setString(5, bankLoanDuration);
            ps.setDouble(6, internalLoan);
            ps.setString(7, internalLInterest);
           // ps.setString(7, saving);
            ps.setString(8, loanID);

            // Executing the update
            int result = ps.executeUpdate();

            if (result > 0) {
                out.println("<script>");
                out.println("alert('Loan Details Updated Successfully!'); window.location='ViewLoanServlet';");
                out.println("</script>");
            } else {
                out.println("<script>alert('Error updating loan details. Try again.'); window.location='edit_loan.jsp?LoanID=" + loanID + "';</script>");
            }

            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<script>alert('Error: " + e.getMessage() + "'); window.location='edit_loan.jsp?LoanID=" + loanID + "';</script>");
        }
    }
}
