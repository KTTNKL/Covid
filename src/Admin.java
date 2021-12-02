import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
class ManagerObject {
    String ID;
    String FirstName;
    String LastName;
    int YearOfBirth;
    String City;
    String District;
    String Ward;
    String UserName;
    String Password;
    String UserType;
    String ManagerBlock;
}
public class Admin extends JFrame{
    private JTextField tFNameM;
    private JTextField tLNameM;
    private JTextField tYOBM;
    private JTextField tCityM;
    private JTextField tDistrictM;
    private JButton addButton;
    private JButton activityButton;
    private JButton blockButton;
    private JButton updateButton;
    private JTable table1;
    private JButton treatmentPlaceButton;
    private JTextField tWardM;
    private JScrollPane tableAdmin;
    private JPanel AdminPanel;

    private String[] columnNames = { "ID", "First Name", "Last Name", "YearOfBirth", "City", "District", "Ward", "Password", "UserName", "UserType" };;
    private Connection conn;
    List<String[]> values;
    void loadData(){
        if(values != null)  values.clear();
        values = new ArrayList<String[]>();
        String sql = "SELECT * FROM User WHERE UserType = 'Manager'";

        try {
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            // loop through the result set
            while (rs.next()) {
                values.add(new String[] {rs.getString("UserID"),rs.getString("FirstName"),rs.getString("LastName"),Integer.toString(rs.getInt("YearOfBirth")),rs.getString("City"), rs.getString("District"), rs.getString("Ward"), rs.getString("Password"), rs.getString("Username"), rs.getString("UserType")});
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        table1.setModel(new DefaultTableModel(
                values.toArray(new Object[][]{}),
                columnNames
        ));
    }
    public Admin(){
        String url = "jdbc:sqlite:D:/Java/Covid/src/covid.db";
        conn = null;
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        loadData();


        setContentPane(AdminPanel);
        setTitle("Admin");
        setSize(5000,2000);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sql = "INSERT INTO User(UserID, FirstName, LastName, YearOfBirth, City, District, Ward, Password, UserName, UserType, ManagerLock ) VALUES(?,?,?,?,?,?,?,?,?,?,?)";

                int numOfID = values.size() + 1;
                String ID;
                if(numOfID < 10)    ID = "MA" + "00" + String.valueOf(numOfID);
                else if(numOfID < 100)   ID = "MA" + "0" + String.valueOf(numOfID);
                else ID = "MA" + String.valueOf(numOfID);
                System.out.println(ID);

                String username = ID + tYOBM.getText();
                String password = "123456789";

                try{
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, ID);
                    pstmt.setString(2, tFNameM.getText());
                    pstmt.setString(3, tLNameM.getText());
                    pstmt.setInt(4, Integer.parseInt(tYOBM.getText()));
                    pstmt.setString(5, tCityM.getText());
                    pstmt.setString(6, tDistrictM.getText());
                    pstmt.setString(7, tWardM.getText());
                    pstmt.setString(8, password);
                    pstmt.setString(9, username);
                    pstmt.setString(10, "Manager");
                    pstmt.setInt(11, 0);
                    pstmt.executeUpdate();
                    loadData();
                } catch (SQLException ecpt) {
                    System.out.println(ecpt.getMessage());
                }
            }
        });
    }
    public static void main(String[] args){
        new Admin();
    }
}
