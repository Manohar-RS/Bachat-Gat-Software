import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ViewBankLoan")
public class ViewBankLoan extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM bloan";
       
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
        
            out.println("<html><head>");
            out.println("<title>Bank Details</title>");
            out.println("<link rel='stylesheet' type='text/css' href='styles.css'>");
            out.println("<script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>");
            out.println("<link href='https://cdn.jsdelivr.net/npm/remixicon@4.5.0/fonts/remixicon.css' rel='stylesheet' />");
           out.println("</head><body>");
           
            out.println("<table border='1'>");
            out.println("<tr><th>B_Loan ID</th><th>Bank ID</th><th>Bank Name</th><th>Bank Loan</th><th>Duration</th><th>Date</th><th>Action</th></tr>");
            
            out.println("<h1>Pragati Mahila Bachat Gat</h1>");
            out.println("<h3>Bank Of Maharashtra</h3>");
            out.println("<br><h2>Bank Details</h2>");
            while (rs.next()) {  

                int bLoanID = rs.getInt("BLoanID");
               
                out.println("<tr>");
                out.println("<td>" + rs.getInt("BLoanID")+ "</td>");
                out.println("<td>" + rs.getInt("BankID")+ "</td>");
                out.println("<td>" + rs.getString("BankName")+ "</td>");
                out.println("<td>" + rs.getString("ApprovedBankLoan")+ "</td>");
                out.println("<td>" + rs.getString("LoanRepaymentPeriod") + "</td>");
                out.println("<td>" + rs.getString("DateOfLoanReceipt") + "</td>");
                out.println("<td>");
                out.println("<a href='EditBankLoan?BlankID=" + bLoanID + "'><i class=\"ri-edit-2-line\"></i></a>");
                out.println("&nbsp;&nbsp;");
                out.println("<a href='DeleteBankLoan?BlankID=" + bLoanID + "' onclick='return confirm(\"Are you sure?\")'><i class=\"ri-delete-bin-6-line\"></i></a>");
                out.println("</td>");
                out.println("</tr>");
            }

            out.println("</table>");
            out.println("<br><a href='ViewBankServlet'>< Back </a>");
            out.println("</body></html>");

            ps.close();
            conn.close();
        
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<script>alert('Error fetching data: " + e.getMessage() + "');</script>");
        }
    }
}



