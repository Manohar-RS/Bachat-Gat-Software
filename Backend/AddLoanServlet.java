import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AddLoanServlet")
public class AddLoanServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/html");
        PrintWriter out = response.getWriter();
    	
//    	String loanID = request.getParameter("LoanID");
        String memberID = request.getParameter("MemberID");
        String memberName = request.getParameter("MemberName");
        double bankLoan = Double.parseDouble(request.getParameter("BankLoan"));
        String bankLoanDate = request.getParameter("BankLoanDate");
        String bankLoanDuration = request.getParameter("BankLoanDuration");
        double internalLoan = Double.parseDouble(request.getParameter("InternalLoan"));
      //  String internalLoanDate = request.getParameter("InternalLoanDate");
        String internalLInterest = request.getParameter("InternalLInterest");
       // String saving = request.getParameter("Saving");
        
        
		try (Connection conn = DBConnection.getConnection()) {
            
			 String query = "INSERT INTO loan (MemberID, MemberName, BankLoan, BankLoanDate, BankLoanDuration, InternalLoan,InternalLInterest) VALUES ( ?, ?, ?, ?, ?, ?, ?)";
             PreparedStatement ps = conn.prepareStatement(query);
//            ps.setString(1, loanID);
            ps.setString(1, memberID);
            ps.setString(2, memberName);
            ps.setDouble(3, bankLoan);
            ps.setString(4, bankLoanDate);
            ps.setString(5, bankLoanDuration);
            ps.setDouble(6, internalLoan);
            ps.setString(7, internalLInterest);
           
            
            
            int result = ps.executeUpdate();
            
            if (result > 0) {
                out.println("<script>");
                out.println(" alert('Loan Details Add Successfully!'); window.location='home.html'; ");
                out.println("</script>");
            } else {
                out.println("<script>alert('Error Adding Loan Details. Try again.'); window.location='add_loan.html';</script>");
            }
            ps.close();
            conn.close();
        } catch (Exception e) {
        	out.println("<script>alert('Error: " + e.getMessage() + "'); window.location='add_loan.html';</script>");
        }
    }
}
