import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private String activities;

    public ManagerObject(String id, String fn, String ln, int year, String city, String dis, String ward, String username, String pass, String type, boolean block, String act){
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
        activities = act;
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
    public boolean getManagerLock(){ return ManagerLock;}
    public String getAct(){ return activities;}
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

    private String[] columnNames = { "ID", "First Name", "Last Name", "YearOfBirth", "City", "District", "Ward", "UserName", "Password", "UserType", "Lock", "Activities" };;
    private Connection conn;

    List<ManagerObject> values;
    List<String []> getActivity(){
        String sql = "SELECT * FROM Activity";
        List<String []> activities = new ArrayList<String []>();
        try {
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            // loop through the result set
            while (rs.next()) {
                String dateString = rs.getString("Date");
                Date date = new SimpleDateFormat("MM-dd-yyyy").parse(dateString);

                activities.add(new String[] {rs.getString("ManagerID"),rs.getString("UserID"),rs.getString("Before"),rs.getString("After"),rs.getString("Type"),rs.getString("Date")});

            }
        } catch (SQLException | ParseException e) {
            System.out.println(e.getMessage());
        }
        return activities;
    }
    void loadData(){
        if(values != null)  values.clear();
        values = new ArrayList<ManagerObject>();
        String sql = "SELECT * FROM User WHERE UserType = 'Manager'";
        List<String[]> activities = getActivity();

        try {
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            // loop through the result set
            while (rs.next()) {
                String temp = "<html>";
                for(int i = 0; i < activities.size();i++){
                    if(activities.get(i)[0].equals(rs.getString("UserID"))){
                        temp+= activities.get(i)[5] + ": " + activities.get(i)[1] + " (" + activities.get(i)[2] + "->" + activities.get(i)[3] + ", Type: " + activities.get(i)[4] + ")<br>";
                    }
                }
                temp+= "</html>";
                values.add(new ManagerObject ( rs.getString("UserID") ,rs.getString("FirstName"),rs.getString("LastName"),rs.getInt("YearOfBirth"),rs.getString("City"), rs.getString("District"), rs.getString("Ward"), rs.getString("Username"), rs.getString("Password"), rs.getString("UserType"), rs.getBoolean("ManagerLock"), temp));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        List<String[]> temp = new ArrayList<String[]>();

        for(int i = 0; i < values.size();i++){
            temp.add(new String[] {values.get(i).getID(),values.get(i).getFirstName(),values.get(i).getLastName(),Integer.toString(values.get(i).getYear()),values.get(i).getCity(), values.get(i).getDistrict(), values.get(i).getWard(), values.get(i).getUserName(), values.get(i).getPassword(), values.get(i).getType(), Boolean.toString(values.get(i).getManagerLock()), values.get(i).getAct()});
        }

        table1.setModel(new DefaultTableModel(
                temp.toArray(new Object[][]{}),
                columnNames
        ));
        for (int row = 0; row < table1.getRowCount(); row++)
        {
            int rowHeight = table1.getRowHeight();

            for (int column = 0; column < table1.getColumnCount(); column++)
            {
                Component comp = table1.prepareRenderer(table1.getCellRenderer(row, column), row, column);
                rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
            }

            table1.setRowHeight(row, rowHeight);
        }
        final TableColumnModel columnModel = table1.getColumnModel();
        for (int column = 0; column < table1.getColumnCount(); column++) {
            int width = 15; // Min width
            for (int row = 0; row < table1.getRowCount(); row++) {
                TableCellRenderer renderer = table1.getCellRenderer(row, column);
                Component comp = table1.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width +1 , width);
            }
            if(width > 300)
                width=300;
            columnModel.getColumn(column).setPreferredWidth(width);
        }
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
    void updatePassword(String ID, String password){
        String sql = "UPDATE User SET Password = ? WHERE UserID = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            // set the corresponding param
            pstmt.setString(1, password);
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
    String getPasswordAdmin(String ID) {
        String sql = "SELECT * FROM User WHERE UserID = 'AD001'";
        String password ="";
        try {
            Statement stmt  = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            password = rs.getString("Password");

            } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return password;
    }

    public Admin() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String url = "jdbc:sqlite:src/covid.db";
        conn = null;
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if(Hash.checkPassword(getPasswordAdmin("AD001"),"123456789")){
            String result = (String)JOptionPane.showInputDialog(
                    null,
                    "This is the first time admin log in, you need to change password",
                    "Change Password",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    "123456789"
            );
            updatePassword("AD001", Hash.getPasswordHash(result));
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


                try {
                    addManager(ID, Hash.getPasswordHash("123456789"), username);
                } catch (NoSuchAlgorithmException ex) {
                    ex.printStackTrace();
                } catch (InvalidKeySpecException ex) {
                    ex.printStackTrace();
                }
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
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
        new Admin();
        //System.out.println(Hash.checkPassword("1000:0abc237210424b0282d190978414ef73:19beed38f09dc00969329e7e094882eca747ee016af8b3bb233902d47dabeecff333a68a2e9bf15903078e529777d1f17be089f560195d18e19585a95f992b64","123456789"));
    }
}
