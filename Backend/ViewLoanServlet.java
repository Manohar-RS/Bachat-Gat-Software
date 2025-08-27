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

@WebServlet("/ViewLoanServlet")
public class ViewLoanServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        String searchQuery = request.getParameter("search");  
        boolean isSearch = searchQuery != null && !searchQuery.trim().isEmpty();

        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM loan";
            if (isSearch) {
                query += " WHERE LOWER(MemberName) LIKE LOWER(?)"; 
          }
            PreparedStatement ps = conn.prepareStatement(query);
            if (isSearch) {
                ps.setString(1, "%" + searchQuery + "%"); 
            }
            ResultSet rs = ps.executeQuery();

            double totalBankLoan = 0;
            double totalInternalLoan = 0;

            out.println("<html><head>");
            out.println("<title>Loan Details</title>");
            out.println("<link rel='stylesheet' type='text/css' href='styles.css'>"); 
            out.println("<script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>");
            out.println("<link href='https://cdn.jsdelivr.net/npm/remixicon@4.5.0/fonts/remixicon.css' rel='stylesheet' />");
             out.println("</head><body>");
             out.println("<button class='btn-log'><a href='DownloadLoanPDF'>Download</a></button>\n" );
            out.println("<h1>Pragati Mahila Bachat Gat</h1>");
            out.println("<h3>Bank Of Maharashtra</h3>");
            
            out.println("<form method='GET'>");
            out.println("<input style='height:35px;' type='text' name='search' value='" + (searchQuery != null ? searchQuery : "") + "' placeholder='Search Member Name...'>");
            out.println("<button type='submit'>Search</button>");
            out.println("<a href='ViewLoanServlet'><button type='button'>Clear</button></a>");
            out.println("</form><br>");
            
            out.println("<br><h2>Loan Details</h2>");
            out.println("<table border='1'>");
            out.println("<tr><th>Loan ID</th><th>Member Name</th><th>Bank Loan</th><th>Date Of Bank Loan</th><th>Bank Loan Duration</th><th>Internal Loan</th><th> Interest Of Internal Loan</th><th>Actions</th></tr>");
            
            while (rs.next()) {
                int loanID = rs.getInt("LoanID");
                double bankLoan = rs.getDouble("BankLoan");
                double internalLoan = rs.getDouble("InternalLoan");

                totalBankLoan += bankLoan;
                totalInternalLoan += internalLoan;

                out.println("<tr>");
                out.println("<td>" + loanID + "</td>");
                out.println("<td>" + rs.getString("MemberName") + "</td>");
                out.println("<td>" + bankLoan + "</td>");
                out.println("<td>" + rs.getString("BankLoanDate") + "</td>");
                out.println("<td>" + rs.getString("BankLoanDuration") + "</td>");
                out.println("<td>" + internalLoan + "</td>");
                out.println("<td>" + rs.getString("InternalLInterest") + "</td>");
                out.println("<td>");
                out.println("<a href='EditLoanServlet?LoanID=" + loanID + "'><i class=\"ri-edit-2-line\"></i></a>");
                out.println("&nbsp;&nbsp;");
                out.println("<a href='DeleteLoanServlet?LoanID=" + loanID + "' onclick='return confirm(\"Are you sure?\")'><i class=\"ri-delete-bin-6-line\"></i></a>");
                out.println("</td>");
                out.println("</tr>");
            }
            
         

            out.println("<tr>");
            out.println("<td colspan='2'><strong>Total</strong></td>");
            out.println("<td><strong>" + totalBankLoan + "</strong></td>");
            out.println("<td colspan='2'></td>");
            out.println("<td><strong>" + totalInternalLoan + "</strong></td>");
            out.println("<td colspan='2'></td>");
            out.println("</tr>");

            out.println("</table>");
            out.println("<br><a href='home.html'>Back to Home</a>");
            out.println("</body></html>");
            
            
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<script>alert('Error fetching loan data: " + e.getMessage() + "');</script>");
        }
        
    }
}

    
        
