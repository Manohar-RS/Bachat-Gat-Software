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

@WebServlet("/RepaymentServlet")
public class RepaymentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
//      String repaymentID = request.getParameter("RepaymentID");
        String loanID = request.getParameter("LoanID");
        String memberID = request.getParameter("MemberID");
        String memberName = request.getParameter("MemberName");
        String dateOfRepayment = request.getParameter("DateOfRepayment");
        String amountOfRepayment = request.getParameter("AmountOfRepayment");
        String savingInstallment= request.getParameter("SavingInstallment");
       
        System.out.println("Received Data: " + memberID + ", " + loanID + ", " + amountOfRepayment + ", " + dateOfRepayment);
        try (Connection conn = DBConnection.getConnection()) {
           
        	String query = "INSERT INTO repayment1 (LoanID ,MemberID, MemberName, DateOfRepayment, AmountOfRepayment, SavingInstallment) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
//          ps.setString(1, repaymentID);
            ps.setString(1, loanID);
            ps.setString(2, memberID);
            ps.setString(3, memberName);
            ps.setString(4, dateOfRepayment);
            ps.setString(5, amountOfRepayment);
            ps.setString(6, savingInstallment);
            
            
            int result = ps.executeUpdate();
            if (result > 0) {
                out.println("<script>");
                out.println("alert('Loan Repayment Details Add Successfully!'); window.location='home.html';  ");
                out.println("</script>");
            } else {
                out.println("<script>alert('Error adding Repayment Details. Try again.'); window.location='add_bank.html';</script>");
            }
            ps.close();
            conn.close();
        } catch (Exception e) {
            out.print("<script>alert('Error: \" + e.getMessage() + \"');window.location='repayment.html';</script>\");");
        }
    }
}
