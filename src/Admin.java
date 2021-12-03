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
    private String ID;
    private String FirstName;
    private String LastName;
    private int YearOfBirth;
    private String City;
    private String District;
    private String Ward;
    private String UserName;
    private String Password;
    private String UserType;
    private Boolean ManagerLock;

    public ManagerObject(String id, String fn, String ln, int year, String city, String dis, String ward, String username, String pass, String type, boolean block){
        ID = id;
        FirstName = fn;
        LastName = ln;
        YearOfBirth = year;
        City = city;
        District = dis;
        Ward = ward;
        UserName = username;
        Password = pass;
        UserType = type;
        ManagerLock = block;

    }
    public String getFirstName(){
        return FirstName;
    }
    public String getLastName(){
        return LastName;
    }
    public String getID(){
        return ID;
    }
    public int getYear(){
        return YearOfBirth;
    }
    public String getCity(){
        return City;
    }
    public String getWard(){
        return Ward;
    }
    public String getDistrict(){
        return District;
    }
    public String getUserName(){
        return UserName;
    }
    public String getPassword(){
        return Password;
    }
    public String getType(){
        return UserType;
    }
    public boolean getManagerLock(){
        return ManagerLock;
    }
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

    private String[] columnNames = { "ID", "First Name", "Last Name", "YearOfBirth", "City", "District", "Ward", "UserName", "Password", "UserType", "Lock" };;
    private Connection conn;

    List<ManagerObject> values;
    void loadData(){
        if(values != null)  values.clear();
        values = new ArrayList<ManagerObject>();
        String sql = "SELECT * FROM User WHERE UserType = 'Manager'";

        try {
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            // loop through the result set
            while (rs.next()) {
                values.add(new ManagerObject (rs.getString("UserID"),rs.getString("FirstName"),rs.getString("LastName"),rs.getInt("YearOfBirth"),rs.getString("City"), rs.getString("District"), rs.getString("Ward"), rs.getString("Username"), rs.getString("Password"), rs.getString("UserType"), rs.getBoolean("ManagerLock")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        List<String[]> temp = new ArrayList<String[]>();

        for(int i = 0; i < values.size();i++){
            temp.add(new String[] {values.get(i).getID(),values.get(i).getFirstName(),values.get(i).getLastName(),Integer.toString(values.get(i).getYear()),values.get(i).getCity(), values.get(i).getDistrict(), values.get(i).getWard(), values.get(i).getUserName(), values.get(i).getPassword(), values.get(i).getType(), Boolean.toString(values.get(i).getManagerLock())});
        }

        table1.setModel(new DefaultTableModel(
                temp.toArray(new Object[][]{}),
                columnNames
        ));
    }
    void addManager(String ID, String password, String username){
        String sql = "INSERT INTO User(UserID, FirstName, LastName, YearOfBirth, City, District, Ward, Password, UserName, UserType, ManagerLock ) VALUES(?,?,?,?,?,?,?,?,?,?,?)";

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
    void updateLock(String ID){
        String sql = "UPDATE User SET ManagerLock = ? WHERE UserID = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            // set the corresponding param
            pstmt.setBoolean(1, true);
            pstmt.setString(2, ID);
            // update
            pstmt.executeUpdate();
            loadData();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    void updateInformation(String id, String fn, String ln, int year, String city, String dis, String ward){
        String sql = "UPDATE User SET FirstName = ?, LastName = ?, YearOfBirth = ?, City = ?, District = ?, Ward = ? WHERE UserID = ?";

        System.out.println(fn);
        System.out.println(ln);
        System.out.println(year);
        System.out.println(city);
        System.out.println(dis);
        System.out.println(ward);

        if(!tFNameM.getText().equals("")) fn = tFNameM.getText();
        if(!tLNameM.getText().equals("")) ln = tLNameM.getText();
        if(!tYOBM.getText().equals("")) year = Integer.parseInt(tYOBM.getText());
        if(!tCityM.getText().equals("")) city = tCityM.getText();
        if(!tDistrictM.getText().equals("")) dis = tDistrictM.getText();
        if(!tWardM.getText().equals("")) ward = tWardM.getText();

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            // set the corresponding param
            pstmt.setString(1, fn);
            pstmt.setString(2, ln);
            pstmt.setInt(3, year);
            pstmt.setString(4, city);
            pstmt.setString(5, dis);
            pstmt.setString(6, ward);
            pstmt.setString(7, id);
            // update
            pstmt.executeUpdate();
            loadData();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
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
                int numOfID = values.size() + 1;
                String ID;
                if(numOfID < 10)    ID = "MA" + "00" + String.valueOf(numOfID);
                else if(numOfID < 100)   ID = "MA" + "0" + String.valueOf(numOfID);
                else ID = "MA" + String.valueOf(numOfID);

                String username = ID + tYOBM.getText();
                String password = "123456789";
                addManager(ID, password, username);
            }
        });
        blockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = table1.getSelectedRow();
                if(selectedIndex >= 0){
                    ManagerObject mng = values.get(selectedIndex);
                    int option = JOptionPane.showConfirmDialog(null, "Do you want to lock this Manager");
                    if(option == 0) updateLock(values.get(selectedIndex).getID());
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = table1.getSelectedRow();
                if(selectedIndex >= 0){
                    ManagerObject mng = values.get(selectedIndex);
                    int option = JOptionPane.showConfirmDialog(null, "Do you want to change information of this Manger");
                    if(option == 0) updateInformation(values.get(selectedIndex).getID(),values.get(selectedIndex).getFirstName(),values.get(selectedIndex).getLastName(),values.get(selectedIndex).getYear(), values.get(selectedIndex).getCity(), values.get(selectedIndex).getDistrict(), values.get(selectedIndex).getWard());
                }
            }
        });
    }
    public static void main(String[] args){
        new Admin();
    }
}
