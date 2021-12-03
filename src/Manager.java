import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


class UserObject{
    private String Userid;
    private String FirstName;
    private String LastName;
    private int YearOfBirth;
    private String City;
    private String District;
    private String Ward;
    private String State;
    private int Debt;
    private String Password;
    private String Username;
    private String UserType;
    private Boolean ManagerLock;
    private String Source;

    public UserObject(String userid, String firstName, String lastName, int yearOfBirth, String city, String district, String ward, String state, int debt, String password, String username, String userType, Boolean managerLock, String source) {
        Userid = userid;
        FirstName = firstName;
        LastName = lastName;
        YearOfBirth = yearOfBirth;
        City = city;
        District = district;
        Ward = ward;
        State = state;
        Debt = debt;
        Password = password;
        Username = username;
        UserType = userType;
        ManagerLock = managerLock;
        Source = source;
    }
    UserObject(){
        Userid="";
    }
    UserObject(String id, String fn,String ln,Integer yob ,String ct,String dt,String w,String s,Integer d){
        Userid=id;
        FirstName=fn;
        LastName=ln;
        YearOfBirth=yob;
        City=ct;
        District=dt;
        Ward=w;
        State=s;
        Debt=d;
    }

    public String getUserid() {
        return Userid;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public int getYearOfBirth() {
        return YearOfBirth;
    }
    public String getStringYearOfBirth() {
        return String.valueOf(YearOfBirth);
    }

    public String getCity() {
        return City;
    }

    public String getDistrict() {
        return District;
    }

    public String getWard() {
        return Ward;
    }

    public String getState() {
        return State;
    }

    public int getDebt() {
        return Debt;
    }

    public String getStringDebt() {
        return String.valueOf(Debt);
    }

    public String getPassword() {
        return Password;
    }

    public String getUsername() {
        return Username;
    }

    public String getUserType() {
        return UserType;
    }

    public Boolean getManagerLock() {
        return ManagerLock;
    }

    public String getSource(){
        return Source;
    }

    public void setUserid(String userid) {
        Userid = userid;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public void setYearOfBirth(int yearOfBirth) {
        YearOfBirth = yearOfBirth;
    }

    public void setCity(String city) {
        City = city;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public void setWard(String ward) {
        Ward = ward;
    }

    public void setState(String state) {
        State = state;
    }

    public void setDebt(int debt) {
        Debt = debt;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }

    public void setManagerLock(Boolean managerLock) {
        ManagerLock = managerLock;
    }

    public void setSource(String source){
        Source = source;
    }

    String[] objectConvert(){
        String Year=String.valueOf(YearOfBirth);
        String D=String.valueOf(Debt);

        String data[]=new String[10];
        data[0]= Userid;
        data[1]= FirstName;
        data[2]=LastName;
        data[3]=Year;
        data[4]=City;
        data[5]=District;
        data[6]=Ward;
        data[7]=State;
        data[8]= D;
        data[9]=Source;

        return data;
    }

    void show(){
        System.out.println(Userid);
        System.out.println(FirstName);
        System.out.println(LastName);
        System.out.println(YearOfBirth);
        System.out.println(City);
        System.out.println(District);
        System.out.println(Ward);
        System.out.println(State);
        System.out.println(Debt);
        System.out.println(Password);
        System.out.println(Username);
        System.out.println(UserType);
        System.out.println(ManagerLock);
        System.out.println(Source);
    }


}

class ManageUser{
    public static List<UserObject> ViewAll(){
        List<UserObject> userList = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        try {
            //lay tat ca danh sach cac user
            connection = DriverManager.getConnection("jdbc:sqlite:src/covid.db");

            //query
            String sql = "SELECT * FROM user WHERE UserType='User'";
            statement = connection.createStatement();

            ResultSet res = statement.executeQuery(sql);

            while (res.next()) {
                UserObject usrobj = new UserObject(res.getString("UserID"),
                        res.getString("FirstName"), res.getString("LastName"),
                        res.getInt("YearOfBirth"), res.getString("City"),
                        res.getString("District"), res.getString("Ward"), res.getString("State"),
                        res.getInt("Debt"),res.getString("Password"), res.getString("Username"),
                        res.getString("UserType"), res.getBoolean("ManagerLock"), res.getString("Source"));

                userList.add(usrobj);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ManageUser.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ManageUser.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ManageUser.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return userList;
    }


    public static void insert(UserObject usrobj) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:src/covid.db");

            String sql = "INSERT INTO User(UserID, FirstName, LastName, YearOfBirth, City, District, Ward, State, Debt, Password, Username, UserType, ManagerLock, Source) VALUES(?, ?, ?, ?, ?, ? ,? ,? ,? ,? ,?, ?, null, ?)";
            statement = connection.prepareStatement(sql);

            statement.setString(1, usrobj.getUserid());
            statement.setString(2, usrobj.getFirstName());
            statement.setString(3, usrobj.getLastName());
            statement.setInt(4, usrobj.getYearOfBirth());
            statement.setString(5, usrobj.getCity());
            statement.setString(6, usrobj.getDistrict());
            statement.setString(7, usrobj.getWard());
            statement.setString(8, usrobj.getState());
            statement.setInt(9, usrobj.getDebt());
            statement.setString(10, usrobj.getPassword());
            statement.setString(11, usrobj.getUsername());
            statement.setString(12, usrobj.getUserType());
            //statement.setBoolean(13, usrobj.getManagerLock());
            statement.setString(13, usrobj.getSource());

            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ManageUser.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ManageUser.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ManageUser.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static void delete(String id) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = DriverManager.getConnection("jdbc:sqlite:src/Covid.db");

            String sql = "delete from user where UserID = ?";
            statement = connection.prepareStatement(sql);

            statement.setString(1, id);

            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ManageUser.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ManageUser.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ManageUser.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static void update(UserObject usrobj){
        Connection connection = null;
        PreparedStatement statement = null;
        try {

            connection = DriverManager.getConnection("jdbc:sqlite:src/Covid.db");

            String sql = "UPDATE User SET FirstName = ? , LastName = ?, YearOfBirth = ?, City = ?, District =?, Ward = ?, State =?, Source= ? "
                    + "WHERE UserID = ?";
            statement = connection.prepareStatement(sql);

            statement.setString(1, usrobj.getFirstName());
            statement.setString(2, usrobj.getLastName());
            statement.setInt(3, usrobj.getYearOfBirth());
            statement.setString(4, usrobj.getCity());
            statement.setString(5, usrobj.getDistrict());
            statement.setString(6, usrobj.getWard());
            statement.setString(7, usrobj.getState());
            statement.setString(8, usrobj.getSource());
            statement.setString(9, usrobj.getUserid());

            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ManageUser.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ManageUser.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ManageUser.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    public static List<UserObject> SearchByFirstname(String firstname) {
        List<UserObject> userList = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:src/Covid.db");

            //query
            String sql = "select * from User where firstname like ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + firstname + "%");

            ResultSet res = statement.executeQuery();

            while (res.next()) {
                UserObject usrobj = new UserObject(res.getString("UserID"),
                        res.getString("FirstName"), res.getString("LastName"),
                        res.getInt("YearOfBirth"), res.getString("City"),
                        res.getString("District"), res.getString("Ward"), res.getString("State"),
                        res.getInt("Debt"), res.getString("Password"), res.getString("Username"),
                        res.getString("UserType"), res.getBoolean("ManagerLock"), res.getString("Source"));
                userList.add(usrobj);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManageUser.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ManageUser.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ManageUser.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return userList;
    }
}


public class Manager<tableModel> extends JFrame {
    private JTextField tFName;
    private JTextField tLName;
    private JTextField tYear;
    private JTextField tCity;
    private JTextField tDistrict;
    private JTextField tWard;
    private JTextField tState;
    private JButton addButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton findButton;
    private JTable UserTable;
    private JTextField tSource;
    private JScrollPane tablePanel;
    private JButton backButton;
    private JButton packageButton;
    private JPanel ManagerPanel;
    private JComboBox comboBox1;
    

    List<UserObject> userList = new ArrayList<>();

    public Manager(){

        setContentPane(ManagerPanel);
        setTitle("Manager");
        setSize(5000,2000);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        showUser();
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String UserID;
                userList = ManageUser.ViewAll();
                String []parts = userList.get(userList.size()-1).getUserid().split("S");
                int id_count = Integer.parseInt(parts[1]);
                id_count += 1;

                if(id_count<10){
                    UserID = "US00"+ String.valueOf(id_count);
                }
                else if(id_count >=10 && id_count <100){
                    UserID = "US0" + String.valueOf(id_count);
                }
                else{
                    UserID = "US" + String.valueOf(id_count);
                }
                System.out.println(UserID);
                String FirstName = tFName.getText();
                String LastName = tLName.getText();
                int YearOfBirth = Integer.parseInt(tYear.getText());
                String City = tCity.getText();
                String District = tDistrict.getText();
                String Ward = tWard.getText();
                String State = tState.getText();
                String Source = tSource.getText();
                UserObject  usr = new UserObject(UserID, FirstName, LastName, YearOfBirth, City, District, Ward, State, 0, "123456789", UserID+String.valueOf(YearOfBirth), "User", null, Source);
                //usr.show();
                ManageUser.insert(usr);
                showUser();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userList = ManageUser.ViewAll();
                int selectedIndex = UserTable.getSelectedRow();
                if (selectedIndex >= 0) {
                    UserObject usr = userList.get(selectedIndex);

                    int opt = JOptionPane.showConfirmDialog(null, "Do you want to delete this user?");
                    // yes is 0, no is 1, cancel is 2
                    if (opt == 0) {
                        ManageUser.delete(usr.getUserid());
                        showUser();
                    }
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userList = ManageUser.ViewAll();
                int selectedIndex = UserTable.getSelectedRow();
                if (selectedIndex >= 0) {
                    UserObject usr = userList.get(selectedIndex);
                    if(!tFName.getText().equals(""))usr.setFirstName(tFName.getText());
                    if(!tLName.getText().equals(""))usr.setLastName(tLName.getText());
                    if(!tYear.getText().equals(""))usr.setYearOfBirth(Integer.parseInt(tYear.getText()));
                    if(!tCity.getText().equals(""))usr.setCity(tCity.getText());
                    if(!tDistrict.getText().equals(""))usr.setDistrict(tDistrict.getText());
                    if(!tWard.getText().equals(""))usr.setWard(tWard.getText());
                    if(!tState.getText().equals(""))usr.setState(tState.getText());
                    if(!tSource.getText().equals(""))usr.setSource(tSource.getText());
                    usr.show();
                    int opt = JOptionPane.showConfirmDialog(null, "Do you want to update this user?");
                    // yes is 0, no is 1, cancel is 2
                    if (opt == 0) {
                        ManageUser.update(usr);
                        showUser();
                    }
                }
            }
        });
        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userList = ManageUser.ViewAll();
                String input = JOptionPane.showInputDialog("Find", "Enter firstname to search");
                if (input != null && input.length() > 0) {
                    userList = ManageUser.SearchByFirstname(input);
                    String data[][] = new String[userList.size()][];
                    for (int i = 0; i < userList.size(); i++) {
                        data[i] = new String[10];
                        data[i] = userList.get(i).objectConvert();
                    }
                    UserTable.setModel(new DefaultTableModel(
                            data,
                            new String[]{"UserID", "First Name", "Last Name", "Year of Birth", "City", "District", "Ward", "State", "Debt", "Source"}
                    ));
                    clearTextField();
                } else {
                    showUser();
                }
            }
        });
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userList = ManageUser.ViewAll();
                String option = (String) comboBox1.getSelectedItem();
                if(option.equals("Sort by Firstname")){
                    sortByFirstname(userList);
                    showUser();
                }
                else{
                    sortByID(userList);
                    showUser();
                }
            }
        });
    }


    private void showUser(){
        userList = ManageUser.ViewAll();
        String data[][] = new String[userList.size()][];
        for (int i = 0; i < userList.size(); i++) {
            data[i] = new String[10];
            data[i] = userList.get(i).objectConvert();
        }
        UserTable.setModel(new DefaultTableModel(
                data,
                new String[]{"UserID", "First Name", "Last Name", "Year of Birth", "City", "District", "Ward", "State", "Debt", "Source"}
        ));
        clearTextField();

    }
    private void clearTextField(){
        tFName.setText("");
        tLName.setText("");
        tYear.setText("");
        tCity.setText("");
        tDistrict.setText("");
        tWard.setText("");
        tState.setText("");
        tSource.setText("");
    }

    private void sortByFirstname(List<UserObject> userList){
        int i, j;
        UserObject key;
        for (i = 1; i < userList.size(); i++)
        {
            key = userList.get(i);
            j = i - 1;

            while (j >= 0 && (userList.get(j).getFirstName().compareTo(key.getFirstName()))>0)
            {
                userList.set(j + 1, userList.get(j));
                j = j - 1;
            }
            userList.set(j + 1, key);
        }
    }

    private void sortByID(List<UserObject> userList){
        int i, j;
        UserObject key;
        for (i = 1; i < userList.size(); i++)
        {
            key = userList.get(i);
            j = i - 1;

            while (j >= 0 && (userList.get(j).getUserid().compareTo(key.getUserid()))>0)
            {
                userList.set(j + 1, userList.get(j));
                j = j - 1;
            }
            userList.set(j + 1, key);
        }
    }
    public static void main(String[] args){
        new Manager();
    }
}
