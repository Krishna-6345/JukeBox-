import java.sql.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogIn {

    Connection con;
    Scanner sc= new Scanner(System.in);

    public void createNewAccount() throws SQLException
    {
            con=DBConfiguration.getConnection();
            PreparedStatement ps1= con.prepareStatement("insert into user(user_name, user_email, user_password) values(?,?,?);");
            System.out.println("Enter you name-");
            String user_name=sc.next();
            ps1.setString(1,user_name);

            System.out.println("Enter email id-");
            String user_mail= sc.next();

            Pattern p1 = Pattern.compile("[a-z0-9_]+[@][a-z]+.[a-z]+");
            boolean b1 = true;
            while (b1)
            {
                Matcher m1 = p1.matcher(user_mail);
                if (m1.matches())
                {
                    ps1.setString(2, user_mail);
                    b1 = false;
                }
                else
                {
                    System.out.println(" Enter valid MailId: ");
                    user_mail = sc.next();
                    ps1.setString(2, user_mail);
                }
            }

            System.out.println("Enter password-");
            String user_password=sc.next();

            Pattern p2 = Pattern.compile("[A-Z][a-z0-9_@]+");
            Matcher m2 = p2.matcher(user_password);
            if (m2.matches())
            {
                ps1.setString(3, user_password);
            }
            else
            {
                System.out.println("Enter Valid Password: ");
                user_password = sc.next();
                ps1.setString(3, user_password);
            }

            ps1.executeUpdate();

            PreparedStatement ps2= con.prepareStatement("select * from user order by user_id desc limit 1;");
            ResultSet rs2= ps2.executeQuery();
            while (rs2.next())
            {
                System.out.println("=====================================================================");
                System.out.println("Your Account is created successfully");
                System.out.println("=====================================================================");
                System.out.println("Please Find Below Your Login Credential-");
                System.out.println("---------------------------------------------------------------------");
                System.out.println("User Name- "+ rs2.getString(2)+ " Login id-"+ rs2.getString(3)+
                        " Password-"+rs2.getString(4));
                System.out.println("=====================================================================");
            }
    }


    public String login(String mail) throws SQLException {
        con=DBConfiguration.getConnection();
        //System.out.println("Enter your mail id-");
        String user_mail=mail;
        System.out.println("Enter your password-");
        String user_password= sc.next();

        PreparedStatement ps1= con.prepareStatement("select * from user where user_email=?");
        ps1.setString(1,user_mail);
        ResultSet rs1=ps1.executeQuery();
        rs1.next();
        String password=rs1.getString(4);

        if(password.equals(user_password))
        {
            System.out.println("===================================================================");
            System.out.println("                      Log in successfull");
            System.out.println("===================================================================");
            return "success";
        }
        else
            return "failed";
    }

}
