import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class User extends JFrame {
    private JButton findButton;
    private JButton buyButton;
    private JComboBox packageSort;
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
    private JButton refreshButton;
    private JButton payButton;
    ArrayList<PackageObject> listPackage;
    ArrayList<ActivityObject> listActivity;
    ArrayList<PurchaseObject> listPurchase;
    UserObject currentUser;

    public User(String CurrentUserID) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Connect app = new Connect();

        if(Hash.checkPassword(app.getPassword(CurrentUserID),"123456789")){
            Boolean flag=true;
            while(flag){
                String result = (String)JOptionPane.showInputDialog(
                    null,
                    "This is the first time user log in, you need to change password",
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

        setContentPane(UserPanel);
        setTitle("Treatment Places");
        setSize(5000,2000);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        showPackageTable();
        showActivityTable(CurrentUserID);
        showPurchaseTable(CurrentUserID);
        showUserInformation(CurrentUserID);

        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connect app = new Connect();
                JTextField PackageID = new JTextField(10);
                JTextField Name = new JTextField(10);
                Object[] message = {
                        "PackageID:", PackageID,
                        "Name:", Name,
                };

                int result = JOptionPane.showConfirmDialog(null, message, "Search Package", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    if (PackageID.getText().equals("") && Name.getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "Do not leave both field blank");
                    }
                    if (!PackageID.getText().equals("") && !Name.getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "Choose only one criteria to search");
                    }
                    if (PackageID.getText().equals("")) {
                        listPackage = new ArrayList<PackageObject>(app.SearchNamePackage(Name.getText()));
                        showPackageTable(listPackage);
                    } else {
                        listPackage = new ArrayList<PackageObject>();
                        listPackage.add(app.SearchIDPackage(PackageID.getText()));
                        showPackageTable(listPackage);
                    }
                }
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPackageTable();
            }
        });

        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connect app = new Connect();
                listPackage=new ArrayList<PackageObject>(app.selectPackageALL());
                int selectedIndex=PackageTable.getSelectedRow();
                if(selectedIndex>=0){
                    PackageObject pkg= listPackage.get(selectedIndex);
                    int opt=JOptionPane.showConfirmDialog(null,"Do you want to buy this package");
                    if(opt==0){
                        try {
                            if(app.CheckLimitTimeUserBuyPackage(CurrentUserID,pkg.getPackageID())){
                                if(app.CheckLimitUserBuyPackage(CurrentUserID,pkg.getPackageID())) {
                                    if(app.CheckStock(pkg.getPackageID())){
                                        app.UserBuyPackage(CurrentUserID, pkg.getPackageID(), pkg.getPrice());
                                        showPurchaseTable(CurrentUserID);
                                        showUserInformation(CurrentUserID);
                                        showPackageTable();
                                    }else{
                                        JOptionPane.showMessageDialog(null,
                                                "Current stock for this package is 0",
                                                "Can not buy package",
                                                JOptionPane.WARNING_MESSAGE);
                                    }
                                }else{
                                    JOptionPane.showMessageDialog(null,
                                            "You have reached the limit",
                                            "Can not buy package",
                                            JOptionPane.WARNING_MESSAGE);
                                }
                            }else{
                                JOptionPane.showMessageDialog(null,
                                        "Limit time for this package is passed",
                                        "Can not buy package",
                                        JOptionPane.WARNING_MESSAGE);
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }

                    }
                }
            }
        });
        packageSort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connect app = new Connect();

                listPackage = new ArrayList<PackageObject>(app.selectPackageALL());;
                int option =packageSort.getSelectedIndex();
                if(option == 1){
                    sortByFirstname(listPackage);
                    showPackageTable(listPackage);
                }
                else{
                    sortByID(listPackage);
                    showPackageTable(listPackage);
                }
            }
        });
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connect app = new Connect();
                app.pay(CurrentUserID);
                showUserInformation(CurrentUserID);
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login();
                dispose();
            }
        });
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
    public void showPackageTable(ArrayList<PackageObject> listPackage){
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
        tYOBU.setText(currentUser.getStringYearOfBirth());
        tCityU.setText(currentUser.getCity());
        tDistictU.setText(currentUser.getDistrict());
        tWardU.setText(currentUser.getWard());
        tStateU.setText(currentUser.getState());
        tDebtU.setText(currentUser.getStringDebt());
    }
    private void sortByFirstname(List<PackageObject> listPackage){
        int i, j;
        PackageObject key;
        for (i = 1; i < listPackage.size(); i++)
        {
            key = listPackage.get(i);
            j = i - 1;

            while (j >= 0 && (listPackage.get(j).getPackageName().compareTo(key.getPackageName()))>0)
            {
                listPackage.set(j + 1, listPackage.get(j));
                j = j - 1;
            }
            listPackage.set(j + 1, key);
        }
    }
    private void sortByID(List<PackageObject> listPackage){
        int i, j;
        PackageObject key;
        for (i = 1; i < listPackage.size(); i++)
        {
            key = listPackage.get(i);
            j = i - 1;

            while (j >= 0 && (listPackage.get(j).getPackageID().compareTo(key.getPackageID()))>0)
            {
                listPackage.set(j + 1, listPackage.get(j));
                j = j - 1;
            }
            listPackage.set(j + 1, key);
        }
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
    public String getPackageID() {
        return ID;
    }
    public String getPackageName() {
        return Name;
    }
    public Integer getPrice() {
        return Price;
    }
    public Integer getLimit() {
        return Limit;
    }
    public Integer getLimit_time() {
        return Limit_time;
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
    String getPurchaseID(){return PurchaseID;}
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
    private Connection connectAccount() {
        String url = "jdbc:sqlite:src/account.db";
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
    public ArrayList<PurchaseObject> selectPurchase() {
        String sql = "SELECT * FROM PurchaseHistory";
        ArrayList<PurchaseObject> list = new ArrayList<PurchaseObject>();
        try {
            Connection conn = this.connect();
            Statement sm = conn.createStatement();
            ResultSet rs = sm.executeQuery(sql);
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
                        rs.getInt("Limit_quantity"),
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
    public UserObject GetInformationUser(String UserID) {
        String sql = "SELECT * FROM User WHERE UserID =?";
        UserObject temp=new UserObject();
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, UserID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                temp = new UserObject(
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
    public PackageObject SearchIDPackage(String PackageID) {
        String sql = "SELECT * FROM Package WHERE PackageID =?";
        PackageObject temp=new PackageObject();
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, PackageID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                temp = new PackageObject(
                        rs.getString("PackageID"),
                        rs.getString("Name"),
                        rs.getInt("Limit_quantity"),
                        rs.getInt("Limit_time"),
                        rs.getInt("Price"),
                        rs.getInt("Stock")
                );

            }

        } catch (SQLException  e) {
            System.out.println(e.getMessage());
        }
        return temp;
    }
    public ArrayList<PackageObject> SearchNamePackage(String Name) {


        ArrayList<PackageObject> list = new ArrayList<PackageObject>();
        String sql = "select * from Package where Name like ?";
        PreparedStatement statement=null;
        try {
            Connection conn = this.connect();
            statement = conn.prepareStatement(sql);
            statement.setString(1, "%"+Name+"%");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                PackageObject temp = new PackageObject(rs.getString("PackageID"),
                        rs.getString("Name"),
                        rs.getInt("Limit_quantity"),
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
    public Boolean CheckLimitTimeUserBuyPackage(String UserID,String PackageID) throws SQLException {

        int limit_time=0;
        String date="";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        String sql ="SELECT Limit_time FROM Package WHERE PackageID=?";
        Connection conn =null;
        try {
            conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, PackageID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                limit_time = rs.getInt(1);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        sql ="SELECT Date FROM PurchaseHistory WHERE PackageID=? and UserID=?";

        try {
            conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, PackageID);
            pstmt.setString(2, UserID);
            ResultSet rs = pstmt.executeQuery();

            date = rs.getString(1);

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        LocalDate firstDay=null;
        try{
            firstDay = LocalDate.parse(date, formatter);}
        catch (DateTimeException ex){
            return true;
        }
        LocalDate limitDay=firstDay.plusDays(limit_time);


        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        LocalDateTime now = LocalDateTime.now();
        String DateNow=dtf.format(now);
        LocalDate currentDate = LocalDate.parse(DateNow, formatter);

        if(limitDay.isAfter(currentDate)||limitDay.isEqual(currentDate)){
            conn.close();
            return true;
        }
        else{
            conn.close();

            return false;
        }
    }
    public Boolean CheckLimitUserBuyPackage(String UserID,String PackageID) throws SQLException {
        int limit=0;
        int count=0;

        String sql ="SELECT Limit_quantity FROM Package WHERE PackageID=?";
        Connection conn =null;

        try {
            conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, PackageID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                limit = rs.getInt(1);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        sql ="SELECT COUNT(*) FROM PurchaseHistory WHERE PackageID=? and UserID=?";
        try {
            conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, PackageID);
            pstmt.setString(2, UserID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        conn.close();
        if(count<limit){
            return true;

        }else{
            return false;
        }
    }
    public void UserBuyPackage(String UserID,String PackageID,int Price){
        String sql = "INSERT INTO PurchaseHistory VALUES(?,?,?,?)";
        ArrayList<PurchaseObject> listPurchase=selectPurchase();
        String []parts = listPurchase.get(listPurchase.size()-1).getPurchaseID().split("C");
        String PurchaseID;
        int id_count = Integer.parseInt(parts[1]);
        id_count += 1;

        if(id_count<10){
            PurchaseID = "PC00"+ String.valueOf(id_count);
        }
        else if(id_count >=10 && id_count <100){
            PurchaseID = "PC0" + String.valueOf(id_count);
        }
        else{
            PurchaseID = "PC" + String.valueOf(id_count);
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        LocalDateTime now = LocalDateTime.now();

        String date=dtf.format(now);

        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, PurchaseID);
            pstmt.setString(2, PackageID);
            pstmt.setString(3, UserID);
            pstmt.setString(4, date);

            pstmt.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        sql = "SELECT Debt FROM User WHERE UserID=?";
        int value=0;
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, UserID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                value = rs.getInt(1);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());

        }
        value+=Price;
        sql = "UPDATE User SET Debt=? WHERE UserID=?";
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, value);
            pstmt.setString(2, UserID);
            pstmt.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        sql = "SELECT Stock FROM Package WHERE PackageID=?";
        int currentStock=0;
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, PackageID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                currentStock = rs.getInt(1);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        currentStock-=1;

        sql = "UPDATE Package SET Stock=? WHERE PackageID=?";
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, currentStock);
            pstmt.setString(2, PackageID);
            pstmt.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public boolean CheckStock(String packageID) {
        String sql = "SELECT Stock FROM Package WHERE PackageID=?";
        int currentStock=0;
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, packageID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                currentStock = rs.getInt(1);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        if(currentStock==0){
            return false;
        }else{
            return true;
        }
    }
    public void pay(String UserID){
        String sql = "SELECT Debt FROM User WHERE UserID=?";
        int value=0;
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, UserID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                value = rs.getInt(1);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        sql = "SELECT AccountBalance FROM Account WHERE UserID=?";
        int current=0;
        try {
            Connection conn = this.connectAccount();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, UserID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                current = rs.getInt(1);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        current-=value;
        sql = "UPDATE Account SET AccountBalance=? WHERE UserID=?";
        try {
            Connection conn = this.connectAccount();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, current);
            pstmt.setString(2, UserID);
            pstmt.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        sql = "UPDATE User SET Debt=0 WHERE UserID=?";
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, UserID);
            pstmt.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public String getPassword(String UserID){
        String sql = "SELECT Password FROM User WHERE UserID=?";
        String password="";
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, UserID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                password = rs.getString(1);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());

        }
        return password;
    }
    public String getPasswordFromName(String UserID){
        String sql = "SELECT Password FROM User WHERE Username=?";
        String password="";
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, UserID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                password = rs.getString(1);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());

        }
        return password;
    }
    public void updatePassword(String UserID, String password){
        String sql = "UPDATE User SET Password = ? WHERE UserID = ?";
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            // set the corresponding param
            pstmt.setString(1, password);
            pstmt.setString(2, UserID);
            // update
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public Boolean isLockManager(String UserID){
        String sql = "SELECT ManagerLock FROM User WHERE Username = ?";
        Boolean lock=true;
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, UserID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                lock = rs.getBoolean(1);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return lock;
    }
    public String GetID(String Username){
        String sql = "SELECT UserID FROM User WHERE Username = ?";
        String ID="";
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, Username);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ID = rs.getString(1);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return ID;
    }


}
