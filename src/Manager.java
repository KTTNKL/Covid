import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;


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
    private String TreatmentPlace;

    public UserObject(String userid, String firstName, String lastName, int yearOfBirth, String city, String district, String ward, String state, int debt, String password, String username, String userType, Boolean managerLock, String source, String treatmentPlace) {
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
        TreatmentPlace = treatmentPlace;
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

    public String getSource(){ return Source;}

    public String getTreatmentPlace(){return TreatmentPlace;}

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

    public void setTreatmentPlace(String treatmentPlace){TreatmentPlace = treatmentPlace;}

    String[] objectConvert(){
        String Year=String.valueOf(YearOfBirth);
        String D=String.valueOf(Debt);

        String data[]=new String[11];
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
        data[10]= TreatmentPlace;

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
        System.out.println(TreatmentPlace);
    }


}

class Manager_ActivityObject{
    private String ActivityID;
    private String ManagerID;
    private String UserID;
    private String Before;
    private String After;
    private String Type;
    private Date DateAc;

    public Manager_ActivityObject(String activityID, String managerID, String userID, String before, String after, String type, Date dateAc) {
        ActivityID = activityID;
        ManagerID = managerID;
        UserID = userID;
        Before = before;
        After = after;
        Type = type;
        DateAc = dateAc;
    }

    public String getActivityID() {
        return ActivityID;
    }

    public String getManagerID() {
        return ManagerID;
    }

    public String getUserID() {
        return UserID;
    }

    public String getBefore() {
        return Before;
    }

    public String getAfter() {
        return After;
    }

    public String getType() {
        return Type;
    }

    public Date getDateAc() {
        return DateAc;
    }

    public void setActivityID(String activityID) {
        ActivityID = activityID;
    }

    public void setManagerID(String managerID) {
        ManagerID = managerID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public void setBefore(String before) {
        Before = before;
    }

    public void setAfter(String after) {
        After = after;
    }

    public void setType(String type) {
        Type = type;
    }

    public void setDateAc(Date dateAc) {
        DateAc = dateAc;
    }

    String[] objectConvert(){
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        String strDate = formatter.format(DateAc);
        String data[]=new String[7];
        data[0]=ActivityID;
        data[1]=ManagerID;
        data[2]=UserID;
        data[3]=Before;
        data[4]=After;
        data[5]=Type;
        data[6]=strDate;
        return data;
    }
}

class ManageActivity{
    public static List<Manager_ActivityObject> ViewAll(){
        List<Manager_ActivityObject> activityList = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:src/covid.db");
            String sql = "SELECT * FROM Activity ";
            statement = connection.createStatement();

            ResultSet res = statement.executeQuery(sql);

            while (res.next()) {
                String dateString = res.getString("Date");
                Date date = new SimpleDateFormat("MM-dd-yyyy").parse(dateString);
                Manager_ActivityObject acobj = new Manager_ActivityObject(res.getString("ActivityID"),
                        res.getString("ManagerID"),
                        res.getString("UserID"),
                        res.getString("Before"),
                        res.getString("After"),
                        res.getString("Type"),
                        date);

                activityList.add(acobj);
            }

        } catch (SQLException | ParseException ex) {
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

        return activityList;
    }
    public static void insert(Manager_ActivityObject acobj) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:src/covid.db");

            String sql = "INSERT INTO Activity(ActivityID, ManagerID, UserID, Before, After, Type, Date) VALUES(?, ?, ?, ?, ?, ? ,? )";
            statement = connection.prepareStatement(sql);

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy");
            LocalDateTime now = LocalDateTime.now();

            String date=dtf.format(now);

            statement.setString(1, acobj.getActivityID());
            statement.setString(2, acobj.getManagerID());
            statement.setString(3, acobj.getUserID());
            statement.setString(4, acobj.getBefore());
            statement.setString(5, acobj.getAfter());
            statement.setString(6, acobj.getType());
            statement.setString(7, date);


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
                        res.getString("UserType"), res.getBoolean("ManagerLock"), res.getString("Source"), res.getString("TreatmentPlace"));

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

            String sql = "INSERT INTO User(UserID, FirstName, LastName, YearOfBirth, City, District, Ward, State, Debt, Password, Username, UserType, ManagerLock, Source, TreatmentPlace) VALUES(?, ?, ?, ?, ?, ? ,? ,? ,? ,? ,?, ?, null, ?, ?)";
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
            statement.setString(14, usrobj.getTreatmentPlace());

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

            String sql = "UPDATE User SET FirstName = ? , LastName = ?, YearOfBirth = ?, City = ?, District =?, Ward = ?, State =?, Source= ?, TreatmentPlace =? "
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
            statement.setString(9, usrobj.getTreatmentPlace());
            statement.setString(10, usrobj.getUserid());


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
            String sql = "select * from User where firstname like ? and UserType='User'";
            statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + firstname + "%");

            ResultSet res = statement.executeQuery();

            while (res.next()) {
                UserObject usrobj = new UserObject(res.getString("UserID"),
                        res.getString("FirstName"), res.getString("LastName"),
                        res.getInt("YearOfBirth"), res.getString("City"),
                        res.getString("District"), res.getString("Ward"), res.getString("State"),
                        res.getInt("Debt"), res.getString("Password"), res.getString("Username"),
                        res.getString("UserType"), res.getBoolean("ManagerLock"), res.getString("Source"), res.getString("TreatmentPlace"));
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

    public static void insertAccount (String id){
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:src/account.db");

            String sql = "INSERT INTO Account(UserID, AccountBalance) VALUES(?, ?)";
            statement = connection.prepareStatement(sql);


            statement.setString(1, id);
            statement.setInt(2, 2000000);



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
    private JButton packageButton;
    private JPanel ManagerPanel;
    private JComboBox comboBox1;
    private JTextField tTreatmentPlace;
    private JLabel labelF3;
    private JLabel labelF2;
    private JLabel labelF1;
    private JLabel labelF0;
    private JLabel labelCured;
    private JLabel labelStateTransition;
    private JLabel labelDebt;
    private JButton btnLogout;


    List<UserObject> userList = new ArrayList<>();

    public Manager(String CurrentUserID){
        Connect app = new Connect();

        try {
            if(Hash.checkPassword(app.getPassword(CurrentUserID),"123456789")){
                Boolean flag=true;
                while(flag){
                    String result = (String)JOptionPane.showInputDialog(
                            null,
                            "This is the first time Manager log in, you need to change password",
                            "Change Password",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            null,
                            ""
                    );
                    if(result!=null && !result.equals("")) {
                        app.updatePassword(CurrentUserID, Hash.getPasswordHash(result));
                        flag=false;
                    }
                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        setContentPane(ManagerPanel);
        setTitle("Manager");
        setSize(5000,2000);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setStatistic();


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

                String FirstName = tFName.getText();
                String LastName = tLName.getText();
                if(!isNumeric(tYear.getText())) {
                    JOptionPane.showMessageDialog(null,
                            "Year of birth should be a number.",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                }
                else{
                int YearOfBirth = Integer.parseInt(tYear.getText());
                String City = tCity.getText();
                String District = tDistrict.getText();
                String Ward = tWard.getText();
                String State = tState.getText();
                String Source = tSource.getText();
                String Treatment = tTreatmentPlace.getText();
                    UserObject  usr = null;
                    try {
                        usr = new UserObject(UserID, FirstName, LastName, YearOfBirth, City, District, Ward, State, 0, Hash.getPasswordHash("123456789"), UserID+String.valueOf(YearOfBirth), "User", null, Source, Treatment);
                    } catch (NoSuchAlgorithmException ex) {
                        ex.printStackTrace();
                    } catch (InvalidKeySpecException ex) {
                        ex.printStackTrace();
                    }
                    //usr.show();
                ManageUser.insert(usr);
                ManageUser.insertAccount(UserID);
                setStatistic();
                showUser();
                }
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
                        setStatistic();
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
                boolean flag = true;
                if (selectedIndex >= 0) {
                    UserObject usr = userList.get(selectedIndex);
                    if (!tFName.getText().equals("")) usr.setFirstName(tFName.getText());
                    if (!tLName.getText().equals("")) usr.setLastName(tLName.getText());
                    if (!tYear.getText().equals(""))
                    {
                        if(!isNumeric(tYear.getText())) {
                            JOptionPane.showMessageDialog(null,
                                    "Year of birth should be a number.",
                                    "Warning",
                                    JOptionPane.WARNING_MESSAGE);
                                    flag = false;
                        }
                        else {
                            usr.setYearOfBirth(Integer.parseInt(tYear.getText()));
                        }
                    }
                    if (!tCity.getText().equals("")) usr.setCity(tCity.getText());
                    if (!tDistrict.getText().equals("")) usr.setDistrict(tDistrict.getText());
                    if (!tWard.getText().equals("")) usr.setWard(tWard.getText());
                    if (!tState.getText().equals("")) {
                        String Before = userList.get(selectedIndex).getState();
                        usr.setState(tState.getText());
                        // xu li state lien quan
                        String AcID;
                        List<Manager_ActivityObject> activityList = ManageActivity.ViewAll();
                        String[] parts = activityList.get(activityList.size() - 1).getActivityID().split("C");
                        int id_count = Integer.parseInt(parts[1]);
                        id_count += 1;

                        if (id_count < 10) {
                            AcID = "AC00" + String.valueOf(id_count);
                        } else if (id_count >= 10 && id_count < 100) {
                            AcID = "AC0" + String.valueOf(id_count);
                        } else {
                            AcID = "AC" + String.valueOf(id_count);
                        }
                        Manager_ActivityObject acobj = new Manager_ActivityObject(AcID, CurrentUserID, usr.getUserid(),Before , tState.getText(),"State",null);
                        ManageActivity.insert(acobj);

                        List<UserObject> related = find_f_after(usr);
                        for (int i = 0; i < related.size(); ++i) {
                            char n = usr.getState().charAt(1);
                            int num = Character.getNumericValue(n);
                            num += 1;
                            Before = related.get(i).getState();
                            related.get(i).setState("F" + Integer.toString(num));
                            ManageUser.update(related.get(i));

                            activityList = ManageActivity.ViewAll();
                            parts = activityList.get(activityList.size() - 1).getActivityID().split("C");
                            id_count = Integer.parseInt(parts[1]);
                            id_count += 1;

                            if (id_count < 10) {
                                AcID = "AC00" + String.valueOf(id_count);
                            } else if (id_count >= 10 && id_count < 100) {
                                AcID = "AC0" + String.valueOf(id_count);
                            } else {
                                AcID = "AC" + String.valueOf(id_count);
                            }
                            acobj = new Manager_ActivityObject(AcID, CurrentUserID, related.get(i).getUserid(),Before , related.get(i).getState(),"State",null);
                            ManageActivity.insert(acobj);
                        }
                    }


                    if (!tSource.getText().equals("")) usr.setSource(tSource.getText());

                    if(!tTreatmentPlace.getText().equals("")){
                        String Before = userList.get(selectedIndex).getTreatmentPlace();
                        usr.setTreatmentPlace(tTreatmentPlace.getText());
                        String AcID;
                        List<Manager_ActivityObject> activityList = ManageActivity.ViewAll();
                        String[] parts = activityList.get(activityList.size() - 1).getActivityID().split("C");
                        int id_count = Integer.parseInt(parts[1]);
                        id_count += 1;

                        if (id_count < 10) {
                            AcID = "AC00" + String.valueOf(id_count);
                        } else if (id_count >= 10 && id_count < 100) {
                            AcID = "AC0" + String.valueOf(id_count);
                        } else {
                            AcID = "AC" + String.valueOf(id_count);
                        }
                        Manager_ActivityObject acobj = new Manager_ActivityObject(AcID, CurrentUserID, usr.getUserid(),Before , tTreatmentPlace.getText(),"TreatmentPlace",null);
                        ManageActivity.insert(acobj);

                    }
                    if (flag) {
                        int opt = JOptionPane.showConfirmDialog(null, "Do you want to update this user?");
                        // yes is 0, no is 1, cancel is 2
                        if (opt == 0) {
                            ManageUser.update(usr);
                            setStatistic();
                            showUser();
                        }
                    }

                }
            }
        });
        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userList = ManageUser.ViewAll();
                String input = JOptionPane.showInputDialog("Find By First Name", "Enter firstname to search");
                if (input != null && input.length() > 0) {
                    userList = ManageUser.SearchByFirstname(input);
                    String data[][] = new String[userList.size()][];
                    for (int i = 0; i < userList.size(); i++) {
                        data[i] = new String[11];
                        data[i] = userList.get(i).objectConvert();
                    }
                    UserTable.setModel(new DefaultTableModel(
                            data,
                            new String[]{"UserID", "First Name", "Last Name", "Year of Birth", "City", "District", "Ward", "State", "Debt", "Source", "TreatmentPlace"}
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
                int option =comboBox1.getSelectedIndex();
                if(option == 1){
                    sortByFirstname(userList);
                    showUser(userList);
                }
                else{
                    sortByID(userList);
                    showUser(userList);
                }
            }
        });
        packageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ManagerPackage(CurrentUserID);
                dispose();

            }
        });
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login();
                dispose();
            }
        });
    }


    private void showUser(){
        userList = ManageUser.ViewAll();
        String data[][] = new String[userList.size()][];
        for (int i = 0; i < userList.size(); i++) {
            data[i] = new String[11];
            data[i] = userList.get(i).objectConvert();
        }
        UserTable.setModel(new DefaultTableModel(
                data,
                new String[]{"UserID", "First Name", "Last Name", "Year of Birth", "City", "District", "Ward", "State", "Debt", "Source", "TreatmentPlace"}
        ));
        clearTextField();
    }
    private void showUser(List<UserObject> userList){
        String data[][] = new String[userList.size()][];
        for (int i = 0; i < userList.size(); i++) {
            data[i] = new String[11];
            data[i] = userList.get(i).objectConvert();
        }
        UserTable.setModel(new DefaultTableModel(
                data,
                new String[]{"UserID", "First Name", "Last Name", "Year of Birth", "City", "District", "Ward", "State", "Debt", "Source", "TreatmentPlace"}
        ));
        clearTextField();
    }

    private void setStatistic(){
        this.labelF3.setText("F3: "+String.valueOf(countF("F3")));
        this.labelF2.setText("F2: "+String.valueOf(countF("F2")));
        this.labelF1.setText("F1: "+String.valueOf(countF("F1")));
        this.labelF0.setText("F0: "+String.valueOf(countF("F0")));
        this.labelCured.setText("Cured: "+String.valueOf(countF("Cured")));
        this.labelStateTransition.setText("Number of State Transition:  "+String.valueOf(countStateTrasition()));
        this.labelDebt.setText("Total Debt:  "+ String.valueOf(countTotalDebt()));
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
        tTreatmentPlace.setText("");
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

    private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }
    public int countF(String type){
        int count =0;
        List<UserObject> userList =  ManageUser.ViewAll();
        for(int i=0; i<userList.size(); ++i){
            if(userList.get(i).getState().compareTo(type)==0)
            {
                count ++;
            }
        }
        return count;
    }

    public int countStateTrasition(){
        int count = 0;
        List<Manager_ActivityObject> activityList = ManageActivity.ViewAll();
        for(int i=0; i<activityList.size(); ++i){
            if(activityList.get(i).getType().compareTo("State")==0){
                count ++;
            }
        }
        return count;
    }

    public int countTotalDebt(){
        int total = 0;
        List<UserObject> userList =  ManageUser.ViewAll();
        for(int i=0; i<userList.size(); ++i){
            total += userList.get(i).getDebt();
        }
        return total;
    }

    public List<UserObject> find_f_after(UserObject usr){
        List<UserObject> userList =  ManageUser.ViewAll();
        List<UserObject> related = new ArrayList<>();
        if(usr.getState().compareTo("Cured")!=0){
            for(int i=0; i<userList.size(); ++i)
            {
                if(userList.get(i).getSource()!=null) {
                    if (userList.get(i).getSource().compareTo(usr.getUserid()) == 0) {

                        related.add(userList.get(i));
                    }
                }
            }
        }
        return related;

    }
//    public static void main(String[] args){
//        try {
//            System.out.print(Hash.getPasswordHash("123456789"));
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (InvalidKeySpecException e) {
//            e.printStackTrace();
//        }
//        new Manager("MA002");
//    }
}
