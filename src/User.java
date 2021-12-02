import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class User extends JFrame {
    private JButton findButton;
    private JButton buyButton;
    private JComboBox comboBox1;
    private JLabel tFNameU;
    private JLabel tLNameU;
    private JLabel tYOBU;
    private JLabel tCityU;
    private JLabel tDistictU;
    private JLabel tWardU;
    private JLabel tStateU;
    private JLabel tDebtU;
    private JScrollPane panel1;
    private JTable PaymentTable;
    private JTable ManagementTable;
    private JButton logoutButton;
    private JScrollPane PaymentHistory;
    private JScrollPane ManagementHistory;
    private JPanel UserPanel;
    private JTable PackageTable;
    ArrayList<PackageObject> listPackage;
    ArrayList<ActivityObject> listActivity;
    ArrayList<PurchaseObject> listPurchase;

    UserObjectT currentUser;

    public User(){
        setContentPane(UserPanel);
        setTitle("Treatment Places");
        setSize(5000,2000);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        showPackageTable();
        showActivityTable("US001");
        showPurchaseTable("US001");
        showUserInformation("US001");
    }

    public void showActivityTable(String UserID) {
        Connect app = new Connect();
        listActivity=new ArrayList<ActivityObject>(app.selectActivity(UserID));
        String data[][] = new String[listActivity.size()][];
        for (int i = 0; i < listActivity.size(); i++) {
            data[i] = new String[7];
            data[i] = listActivity.get(i).objectConvert();
        }
        ManagementTable.setModel(new DefaultTableModel(
                data,
                new String[]{"ActivityID", "ManagerID",  "UserID", "Before", "After", "Type", "Date"}
        ));
    }

    public void showPurchaseTable(String UserID) {
        Connect app = new Connect();

        listPurchase=new ArrayList<PurchaseObject>(app.selectPurchase(UserID));
        String data[][] = new String[listPurchase.size()][];
        for (int i = 0; i < listPurchase.size(); i++) {
            data[i] = new String[4];
            data[i] = listPurchase.get(i).objectConvert();
        }
        PaymentTable.setModel(new DefaultTableModel(
                data,
                new String[]{"PurchaseID", "PackageID",  "UserID", "Date"}
        ));
    }

    public void showPackageTable(){
        Connect app = new Connect();
        listPackage = new ArrayList<PackageObject>(app.selectPackageALL());
        String data[][] = new String[listPackage.size()][];
        for (int i = 0; i < listPackage.size(); i++) {
            data[i] = new String[6];
            data[i] = listPackage.get(i).objectConvert();
        }
        PackageTable.setModel(new DefaultTableModel(
                data,
                new String[]{"PackageID", "Name", "Limit", "Limit-time", "Price", "Stock"}
        ));
    }
    public void showUserInformation(String UserID){
        Connect app = new Connect();
        currentUser=app.GetInformationUser(UserID);
        tFNameU.setText(currentUser.getFirstName());
        tLNameU.setText(currentUser.getLastName());
        tYOBU.setText(currentUser.getYOB());
        tCityU.setText(currentUser.getCity());
        tDistictU.setText(currentUser.getDistict());
        tWardU.setText(currentUser.getWard());
        tStateU.setText(currentUser.getState());
        tDebtU.setText(currentUser.getDebt());
    }
}

class PackageObject{
    private String ID;
    private String Name;
    private Integer Limit;
    private Integer Limit_time;
    private Integer Price;
    private Integer Stock;
    PackageObject(){
        ID="";
    }
    PackageObject(String id, String nm,Integer lm,Integer lmt ,Integer price,Integer stock){
        ID=id;
        Name=nm;
        Limit=lm;
        Limit_time=lmt;
        Price=price;
        Stock=stock;
    }
    String[] objectConvert(){
        String lm=String.valueOf(Limit);
        String limit_time=String.valueOf(Limit_time);
        String price=String.valueOf(Price);
        String stock=String.valueOf(Stock);

        String data[]=new String[6];
        data[0]=ID;
        data[1]=Name;
        data[2]=lm;
        data[3]=limit_time;
        data[4]=price;
        data[5]=stock;
        return data;
    }
}
class UserObjectT{
    private String ID;
    private String firstName;
    private String lastName;
    private Integer YoB;
    private String city;
    private String distict;
    private String ward;
    private String state;
    private Integer debt;

    UserObjectT(){
        ID="";
    }
    UserObjectT(String id, String fn,String ln,Integer yob ,String ct,String dt,String w,String s,Integer d){
        ID=id;
        firstName=fn;
        lastName=ln;
        YoB=yob;
        city=ct;
        distict=dt;
        ward=w;
        state=s;
        debt=d;
    }

    void show(){
        System.out.println(ID);
        System.out.println(firstName);
        System.out.println(lastName);
        System.out.println(YoB);
        System.out.println(city);
        System.out.println(distict);
        System.out.println(ward);
        System.out.println(state);
        System.out.println(debt);
    }
    String[] objectConvert(){
        String Year=String.valueOf(YoB);
        String Debt=String.valueOf(debt);

        String data[]=new String[9];
        data[0]=ID;
        data[1]=firstName;
        data[2]=lastName;
        data[3]=Year;
        data[4]=city;
        data[5]=distict;
        data[6]=ward;
        data[7]=state;
        data[8]=Debt;

        return data;
    }
    String getFirstName(){
        return firstName;
    }
    String getLastName(){
        return lastName;
    }
    String getYOB(){
        return String.valueOf(YoB);
    }
    String getCity(){
        return city;
    }
    String getDistict(){
        return distict;
    }
    String getWard(){
        return ward;
    }
    String getState(){
        return state;
    }
    String getDebt(){
        return String.valueOf(debt);
    }
}
class ActivityObject{
    private String ActivityID;
    private String ManagerID;
    private String UserID;
    private String Before;
    private String After;
    private String Type;
    private Date DateAc;
    ActivityObject(String AID,String MID,String UID,String b,String a,String type,Date date){
        ActivityID=AID;
        ManagerID=MID;
        UserID=UID;
        Before=b;
        After=a;
        Type=type;
        DateAc=date;
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
class PurchaseObject{
    private String PurchaseID;
    private String PackageID;
    private String UserID;
    private Date DatePur;
    PurchaseObject(String PuID,String PacID,String UID,Date date){
        PurchaseID=PuID;
        PackageID=PacID;
        UserID=UID;
        DatePur=date;
    }
    String[] objectConvert(){
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        String strDate = formatter.format(DatePur);
        String data[]=new String[4];
        data[0]=PurchaseID;
        data[1]=PackageID;
        data[2]=UserID;
        data[3]=strDate;
        return data;
    }
}
class Connect {
    private Connection connect() {
        String url = "jdbc:sqlite:src/covid.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    public ArrayList<PurchaseObject> selectPurchase(String UserID) {
        String sql = "SELECT * FROM PurchaseHistory WHERE UserID =? ";
        ArrayList<PurchaseObject> list = new ArrayList<PurchaseObject>();
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, UserID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String dateString = rs.getString("Date");
                Date date = new SimpleDateFormat("MM-dd-yyyy").parse(dateString);
                PurchaseObject temp = new PurchaseObject(rs.getString("PurchaseID"),
                        rs.getString("PackageID"),
                        rs.getString("UserID"),
                        date);
                list.add(temp);
            }

        } catch (SQLException | ParseException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public ArrayList<ActivityObject> selectActivity(String UserID) {
        String sql = "SELECT * FROM Activity WHERE UserID =? ";
        ArrayList<ActivityObject> list = new ArrayList<ActivityObject>();
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, UserID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String dateString = rs.getString("Date");
                Date date = new SimpleDateFormat("MM-dd-yyyy").parse(dateString);
                ActivityObject temp = new ActivityObject(rs.getString("ActivityID"),
                        rs.getString("ManagerID"),
                        rs.getString("UserID"),
                        rs.getString("Before"),
                        rs.getString("After"),
                        rs.getString("Type"),
                        date);
                list.add(temp);
            }

        } catch (SQLException | ParseException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }
    public ArrayList<PackageObject> selectPackageALL() {
        String sql = "SELECT * FROM Package";
        ArrayList<PackageObject> list = new ArrayList<PackageObject>();
        try {
            Connection conn = this.connect();
            Statement sm = conn.createStatement();
            ResultSet rs = sm.executeQuery(sql);
            while (rs.next()) {

                PackageObject temp = new PackageObject(rs.getString("PackageID"),
                        rs.getString("Name"),
                        rs.getInt("Limit"),
                        rs.getInt("Limit_time"),
                        rs.getInt("Price"),
                        rs.getInt("Stock"));

                list.add(temp);
            }

        } catch (SQLException  e) {
            System.out.println(e.getMessage());
        }
        return list;
    }
    public UserObjectT GetInformationUser(String UserID) {
        String sql = "SELECT * FROM User WHERE UserID =?";
        UserObjectT temp=new UserObjectT();
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, UserID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                temp = new UserObjectT(
                        rs.getString("UserID"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getInt("YearOfBirth"),
                        rs.getString("City"),
                        rs.getString("District"),
                        rs.getString("Ward"),
                        rs.getString("State"),
                        rs.getInt("Debt")
                );

            }

        } catch (SQLException  e) {
            System.out.println(e.getMessage());
        }
        return temp;
    }

//    public void insert(int id, String fn, String ln, String dob, String adr) {
//        String sql = "INSERT INTO Student VALUES(?,?,?,?,?)";
//
//        try {
//            Connection conn = this.connect();
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//
//            pstmt.setInt(1, id);
//            pstmt.setString(2, fn);
//            pstmt.setString(3, ln);
//            pstmt.setString(4, dob);
//            pstmt.setString(5, adr);
//
//            pstmt.execute();
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    public void delete(int id) {
//        String sql = "DELETE FROM Student WHERE ID= ?";
//
//        try {
//            Connection conn = this.connect();
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//
//            pstmt.setInt(1, id);
//
//            pstmt.execute();
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    public void update(int id, String fn, String ln, String dob, String adr) {
//        System.out.println(id);
//        System.out.println(fn);
//        System.out.println(ln);
//        System.out.println(dob);
//        System.out.println(adr);
//        int count = 1;
//        int posF = 1;
//        int checkF = 0;
//        int posL = 1;
//        int checkL = 0;
//        int postD = 1;
//        int checkD = 0;
//        int posA = 1;
//        int checkA = 0;
//        String sql = "UPDATE Student SET";
//        if (!Objects.equals(fn, "")) {
//            sql += " 'First Name'=? ,";
//            count += 1;
//            checkF = 1;
//        }
//        if (!Objects.equals(ln, "")) {
//            sql += " 'Last Name'=? ,";
//            count += 1;
//            posL += 1;
//            postD += 1;
//            posA += 1;
//            checkL = 1;
//        }
//        if (!Objects.equals(dob, "")) {
//            sql += " DOB=? ,";
//            count += 1;
//            postD += 1;
//            posA += 1;
//            checkD = 1;
//        }
//        if (!Objects.equals(adr, "")) {
//            sql += " Address=? ";
//            count += 1;
//            posA += 1;
//            checkA = 1;
//        }
//        sql += "WHERE ID=?";
//
//        try {
//            Connection conn = this.connect();
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//
//            pstmt.setInt(count, id);
//            if (checkF == 1)
//                pstmt.setString(posF, fn);
//            if (checkL == 1)
//                pstmt.setString(posL, ln);
//            if (checkD == 1)
//                pstmt.setString(postD, dob);
//            if (checkA == 1)
//                pstmt.setString(posA, adr);
//
//            pstmt.execute();
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    public ArrayList<StudentObject> searchName(String ln) {
//        String sql = "SELECT * FROM Student WHERE 'Last Name'='" + ln + "'";
//        ArrayList<StudentObject> list = new ArrayList<StudentObject>();
//        System.out.println(sql);
//        try {
//            Connection conn = this.connect();
//            Statement sm = conn.createStatement();
//            ResultSet rs = sm.executeQuery(sql);
//
//            while (rs.next()) {
//
//                String dateString = rs.getString("DOB");
//                Date date = new SimpleDateFormat("MM-dd-yyyy").parse(dateString);
//                StudentObject temp = new StudentObject(rs.getInt("ID"),
//                        rs.getString("First Name"),
//                        rs.getString("Last Name"),
//                        date,
//                        rs.getString("Address"));
//                list.add(temp);
//            }
//        } catch (SQLException | ParseException e) {
//            System.out.println(e.getMessage());
//        }
//
//        return list;
//    }
//
//    public StudentObject searchID(int ID) {
//        String sql = "SELECT * FROM Student WHERE ID =?";
//        StudentObject temp = new StudentObject();
//        try {
//            Connection conn = this.connect();
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//            pstmt.setInt(1, ID);
//            ResultSet rs = pstmt.executeQuery();
//            while (rs.next()) {
//                String dateString = rs.getString("DOB");
//                Date date = new SimpleDateFormat("MM-dd-yyyy").parse(dateString);
//                temp = new StudentObject(rs.getInt("ID"),
//                        rs.getString("First Name"),
//                        rs.getString("Last Name"),
//                        date,
//                        rs.getString("Address"));
//            }
//        } catch (SQLException | ParseException e) {
//            System.out.println(e.getMessage());
//        }
//        return temp;
//    }
}
class UserRun{
    public static void main(String[] args) {

            new User();
    }
}