
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String url = "jdbc:mysql://localhost:3306/Project1";
    private static final String user = "root";
    private static final String password = "root";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Database Driver Not Found!", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}





















//
//<!DOCTYPE html>
//<html lang="en">
//
//<head>
//    <meta charset="UTF-8">
//    <meta name="viewport" content="width=device-width, initial-scale=1.0">
//    <title>Home</title>
//    <style>
//        .dropdown {
//            position: relative;
//            margin-top: -40px;
//        }
//
//        .dropdown .dropbtn {
//            padding-left: 10px;
//            cursor: pointer;
//            display: inline-block;
//            margin-top: 40px;
//        }
//
//        .dropbtn i {
//            padding-left: 65px;
//            font-size: 25px;
//
//        }
//
//        .dropdown:hover .dropbtn {
//            background-color: #ddd;
//        }
//
//        .dropdownContent h5 {
//            font-weight: 400;
//        }
//
//        .dropdownContent {
//            display: none;
//
//            position: absolute;
//            background-color: white;
//            width: 270px;
//            border: 1px solid #ccc;
//            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
//        }
//
//        .dropdown:hover .dropdownContent {
//            display: block;
//        }
//
//        .dropdownContent a {
//            padding: 10px 15px;
//            display: block;
//            text-decoration: none;
//            color: #333;
//            font-weight: 300;
//        }
//
//        .dropdownContent a:hover {
//            background-color: #f1f1f1;
//        }
//
//       .dropdown {
//            position: relative;
//            display: inline-block;
//        }
//
//        .dropdown-button {
//            background-color: #ddd;
//            border: none;
//            padding: 10px 15px;
//            font-size: 16px;
//            cursor: pointer;
//            display: flex;
//            align-items: center;
//        }
//
//        .dropdown-menu {
//            display: none;
//            position: absolute;
//            background-color: white;
//            box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
//            list-style: none;
//            padding: 0;
//            margin: 0;
//            width: 200px;
//        }
//
//        .dropdown-menu li {
//            padding: 10px;
//            cursor: pointer;
//        }
//
//        .dropdown-menu li:hover {
//            background-color: #f0f0f0;
//        }
//
//        .dropdown:hover .dropdown-menu {
//            display: block;
//        }
//
//        body {
//            font-family: Arial, sans-serif;
//            margin: 0;
//            padding: 0;
//            background: #fff;
//        }
//
//        header {
//            display: flex;
//            justify-content: space-between;
//            align-items: center;
//            padding: 15px 50px;
//            background: white;
//            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
//        }
//
//        .pname {
//            background: linear-gradient(to right, #eb9f48ae, #e69f35fb);
//            display: flex;
//            color: black;
//            text-align: center;
//            justify-content: center;
//            padding-right: 50px;
//
//        }
//
//        .logo {
//            font-size: 24px;
//            font-weight: bold;
//            color: black;
//        }
//
//        .logo span {
//            color: rgb(241, 173, 106);
//        }
//
//        nav ul {
//            list-style: none;
//            display: flex;
//            text-align: center;
//            justify-content: center;
//            padding-right: 450px;
//            gap: 20px;
//        }
//
//        nav ul li {
//            display: inline;
//        }
//
//        nav ul li a {
//            text-decoration: none;
//            color: black;
//            font-weight: bold;
//        }
//
//        .content-area {
//            background: linear-gradient(to right, #eb9f48ae, #e69f35fb);
//            display: flex;
//            flex-direction: column;
//            align-items: center;
//        }
//
//        .rectangle {
//            width: 90%;
//            max-width: 1000px;
//            height: auto;
//            border-radius: 12px;
//            box-shadow: 0 6px 10px rgba(0, 0, 0, 0.5);
//            overflow: hidden;
//            background-color: #fff;
//            display: flex;
//            justify-content: center;
//            align-items: center;
//        }
//
//        .rectangle img {
//            width: 100%;
//            height: auto;
//            object-fit: cover;
//        }
//
//        .members-section {
//            margin-top: 20px;
//            text-align: center;
//            background-color: white;
//            color: black;
//            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
//            border-radius: 12px;
//            padding: 20px;
//            width: 90%;
//            max-width: 700px;
//            transition: transform 0.3s ease, box-shadow 0.3s ease;
//
//        }
//
//        .members-section:hover {
//            transform: scale(1.008);
//            box-shadow: 0 6px 12px rgba(0, 0, 0, 0.3);
//        }
//
//        .members-section h2 {
//            font-size: 20px;
//            margin-bottom: 15px;
//        }
//
//        .button-group {
//            display: flex;
//            flex-wrap: wrap;
//            justify-content: center;
//            gap: 10px;
//        }
//
//        .button-group button {
//            padding: 10px 15px;
//            font-size: 14px;
//            color: white;
//            background-color: green;
//            border: none;
//            border-radius: 8px;
//            cursor: pointer;
//            font-weight: bold;
//            transition: background-color 0.3s ease, transform 0.2s ease;
//        }
//
//        .button-group button:hover {
//            background-color: darkgreen;
//            transform: scale(1.01);
//        }
//
//        .button-group a {
//            text-decoration: none;
//            color: white;
//            font-weight: bold;
//        }
//
//        @media (max-width: 768px) {
//            nav {
//                flex-direction: column;
//                align-items: center;
//            }
//
//            nav h2 {
//                font-size: 16px;
//            }
//
//            .rectangle {
//                height: auto;
//            }
//
//            .members-section {
//                padding: 15px;
//            }
//
//            .button-group button {
//                font-size: 12px;
//                padding: 8px 12px;
//            }
//        }
//
//        @media (max-width: 480px) {
//            .nav-links a {
//                font-size: 14px;
//            }
//
//            nav h2 {
//                font-size: 14px;
//            }
//
//            .button-group button {
//                font-size: 10px;
//                padding: 6px 10px;
//            }
//
//            .members-section h2 {
//                font-size: 18px;
//            }
//        }
//
//        footer {
//            text-align: center;
//            padding: 10px 0px;
//            background: #4f3501;
//            color: #ffffff;
//            position: relative;
//            bottom: 0;
//            width: 100%;
//        }
//    </style>
//</head>
//
//<body>
//    <header>
//        <div class="logo"><a href="dashboard.html"><img style="height: 60px;width: 80px;" src="logo3-removebg-preview.png"
//                    alt=""></a></div>
//        <nav>
//            <ul>
//                <li><a href="home.html">Home</a></li>
//                <li><a href="about.html">About Us</a></li>
//                <li><a href="services.html">Services</a></li>
//                <li>
//                    <div class="dropdown">
//                        <a href="#" class="dropbtn ">Contact Us</a>
//                        <div class="dropdownContent">
//                            <h5>1. Manohar Sonawane +91 9322934231</h5>
//                            <h5>2. Nikita Sonawane +91 9322934231</h5>
//                            <h5>3. Rohan Sanap +91 9322934231</h5>
//                            <h5>4. Sraddha Shirsath +91 9322934231</h5>
//                        </div>
//                    </div>
//                </li>
//
//            </ul>
//        </nav>
//    </header>
//    <div class="pname">
//        <h2>Pragati Mahila Bachat Gat</h2>
//    </div>
//    <main class="content-area">
//        <div class="rectangle">
//            <img src="mahila.webp" alt="Mahila">
//        </div>
//        <div class="members-section">
//            <h2>Saving Group</h2>
//            <div class="button-group">
//                <button><a href="ViewGroupServlet">View Group Details</a></button>
//            </div>
//        </div>
//        <div class="members-section">
//            <h2>Bank </h2>
//            <div class="button-group">
//                <button><a href="ViewBankServlet">View Bank Details</a></button>
//            </div>
//        </div>
//        <div class="members-section">
//            <h2>Members</h2>
//            <div class="button-group">
//               <button><a href="add_member.html">Add Member </a></button>
//                <button><a href="ViewMemberServlet">View Member Details</a></button>
//            </div>
//        </div>
//        
//        <div class="members-section">
//            <h2>Member Loan Details</h2>
//            <div class="button-group">
//                <button><a href="add_loan.html">Add Loan Details</a></button>
//                <button><a href="ViewLoanServlet">View Loan Details</a></button>
//            </div>
//        </div>
//        <div class="members-section">
//            <h2>Member Installment Details</h2>
//            <div class="button-group">
//                <button><a href="repayment.html">Add Installment Details</a></button>
//                <button><a href="ViewRepaymentServlet">View Installment Details</a></button>
//            </div>
//        </div>
//        <div class="members-section">
//            <h2>Meetings Details</h2>
//            <div class="button-group">
//                <button><a href="meeting.html">Add Meeting Details </a></button>
//                <button><a href="ViewMeetingServlet">View Meeting Details </a></button>
//            </div>
//        </div>
//    </main>
//    <footer>
//        <p>&copy; 2025 Saving Group Management System. All rights reserved.</p>
//    </footer>
//</body>
//
//</html>