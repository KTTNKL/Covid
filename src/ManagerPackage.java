import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

class Manager_PackageObject{
    private String ID;
    private String Name;
    private Integer Limit;
    private Integer Limit_time;
    private Integer Price;
    private Integer Stock;

    public Manager_PackageObject(String ID, String name, Integer limit, Integer limit_time, Integer price, Integer stock) {
        this.ID = ID;
        Name = name;
        Limit = limit;
        Limit_time = limit_time;
        Price = price;
        Stock = stock;
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

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getLimit() {
        return Limit;
    }

    public void setLimit(Integer limit) {
        Limit = limit;
    }

    public Integer getLimit_time() {
        return Limit_time;
    }

    public void setLimit_time(Integer limit_time) {
        Limit_time = limit_time;
    }

    public Integer getPrice() {
        return Price;
    }

    public void setPrice(Integer price) {
        Price = price;
    }

    public Integer getStock() {
        return Stock;
    }

    public void setStock(Integer stock) {
        Stock = stock;
    }
    void show(){
        System.out.println(ID);
        System.out.println(Name);
        System.out.println(Limit);
        System.out.println(Limit_time);
        System.out.println(Price);
        System.out.println(Stock);

    }
}
class ManagePackage{
    public static List<Manager_PackageObject> ViewAll(){
        List<Manager_PackageObject> pkgList = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        try {
            //lay tat ca danh sach cac user
            connection = DriverManager.getConnection("jdbc:sqlite:src/covid.db");

            //query
            String sql = "SELECT * FROM Package";
            statement = connection.createStatement();

            ResultSet res = statement.executeQuery(sql);

            while (res.next()) {
                Manager_PackageObject pkgobj = new Manager_PackageObject(res.getString("PackageID"),
                        res.getString("Name"), res.getInt("Limit_quantity"),
                        res.getInt("Limit_time"), res.getInt("Price"),
                        res.getInt("Stock"));

                pkgList.add(pkgobj);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ManagePackage.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ManagePackage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ManagePackage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return pkgList;
    }

    public static void insert(Manager_PackageObject pkg) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:src/covid.db");

            String sql = "INSERT INTO Package VALUES(?,?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(sql);

            statement.setString(1, pkg.getID());
            statement.setString(2, pkg.getName());
            statement.setInt(3, pkg.getLimit());
            statement.setInt(4, pkg.getLimit_time());
            statement.setInt(5, pkg.getPrice());
            statement.setInt(6, pkg.getStock());


            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ManagePackage.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ManagePackage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ManagePackage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static void delete(String id) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = DriverManager.getConnection("jdbc:sqlite:src/Covid.db");

            String sql = "delete from Package where PackageID = ?";
            statement = connection.prepareStatement(sql);

            statement.setString(1, id);

            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ManagePackage.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ManagePackage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ManagePackage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }


    public static void update(Manager_PackageObject pkg){
        Connection connection = null;
        PreparedStatement statement = null;
        try {

            connection = DriverManager.getConnection("jdbc:sqlite:src/Covid.db");

            String sql = "UPDATE Package SET Name = ? , Limit_quantity = ?, Limit_time = ?, Price = ?, Stock =? "
                    + "WHERE PackageID = ?";
            statement = connection.prepareStatement(sql);

            statement.setString(1, pkg.getName());
            statement.setInt(2, pkg.getLimit());
            statement.setInt(3, pkg.getLimit_time());
            statement.setInt(4, pkg.getPrice());
            statement.setInt(5, pkg.getStock());
            statement.setString(6, pkg.getID());
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ManagePackage.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ManagePackage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ManagePackage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static List<Manager_PackageObject> SearchByName(String name) {
        List<Manager_PackageObject> pkgList = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:src/Covid.db");

            //query
            String sql = "select * from Package where Name like ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + name + "%");

            ResultSet res = statement.executeQuery();

            while (res.next()) {
                Manager_PackageObject pkgobj = new Manager_PackageObject(res.getString("PackageID"),
                        res.getString("Name"), res.getInt("Limit_quantity"),
                        res.getInt("Limit_time"), res.getInt("Price"),
                        res.getInt("Stock"));

                pkgList.add(pkgobj);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManagePackage.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ManagePackage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ManagePackage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return pkgList;
    }

}

public class ManagerPackage extends JFrame{
    private JTextField tPkgName;
    private JTextField tQuantity;
    private JTextField tTime;
    private JTextField tPrice;
    private JTextField tStock;
    private JButton addButton;
    private JButton findButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JTable table1;
    private JComboBox comboBox1;
    private JScrollPane tablePackage;
    private JPanel ManagerPackagePanel;
    private JButton backButtonManger;



    List<Manager_PackageObject> listPackage = new ArrayList<>();

    public ManagerPackage(){
        setContentPane(ManagerPackagePanel);
        setTitle("Package");
        setSize(5000,2000);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        Manager_showPackageTable();
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String PackageID;
                String []parts = listPackage.get(listPackage.size()-1).getID().split("K");
                int id_count = Integer.parseInt(parts[1]);
                id_count += 1;

                if(id_count<10){
                    PackageID = "PK00"+ String.valueOf(id_count);
                }
                else if(id_count >=10 && id_count <100){
                    PackageID = "PK0" + String.valueOf(id_count);
                }
                else{
                    PackageID = "PK" + String.valueOf(id_count);
                }
                String Name = tPkgName.getText();
                if(!isNumeric(tQuantity.getText())|| !isNumeric(tTime.getText()) || !isNumeric(tPrice.getText()) || !isNumeric(tStock.getText())) {
                    JOptionPane.showMessageDialog(null,
                            "Quantity limit, Time Limit, Price and Stock should be all numbers.",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                }
                else{
                    int Limit = Integer.parseInt(tQuantity.getText()) ;
                    int Limit_time = Integer.parseInt(tTime.getText()) ;
                    int Price = Integer.parseInt(tPrice.getText()) ;
                    int Stock = Integer.parseInt(tStock.getText()) ;
                    Manager_PackageObject pkg = new Manager_PackageObject(PackageID, Name, Limit, Limit_time, Price, Stock);
                    ManagePackage.insert(pkg);
                    Manager_showPackageTable();
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listPackage = ManagePackage.ViewAll();
                int selectedIndex = table1.getSelectedRow();
                if (selectedIndex >= 0) {
                    Manager_PackageObject pkg = listPackage.get(selectedIndex);

                    int opt = JOptionPane.showConfirmDialog(null, "Do you want to delete this package?");
                    // yes is 0, no is 1, cancel is 2
                    if (opt == 0) {
                        ManagePackage.delete(pkg.getID());
                        Manager_showPackageTable();
                    }
                }
            }
        });
        backButtonManger.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Manager();
                dispose();
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listPackage = ManagePackage.ViewAll();
                int selectedIndex = table1.getSelectedRow();
                boolean flag = true;
                if (selectedIndex >= 0) {
                    Manager_PackageObject pkg = listPackage.get(selectedIndex);

                        if (!tPkgName.getText().equals("")) pkg.setName(tPkgName.getText());
                    if (!tQuantity.getText().equals(""))
                    {
                        if(!isNumeric(tQuantity.getText())) {
                            JOptionPane.showMessageDialog(null,
                                    "Quantity Limit should be a number.",
                                    "Warning",
                                    JOptionPane.WARNING_MESSAGE);
                            flag = false;
                        }
                        else {
                            pkg.setLimit(Integer.parseInt(tQuantity.getText()));
                        }
                    }
                    if (!tTime.getText().equals(""))
                    {
                        if(!isNumeric(tTime.getText())) {
                            JOptionPane.showMessageDialog(null,
                                    "Time Limit should be a number.",
                                    "Warning",
                                    JOptionPane.WARNING_MESSAGE);
                            flag = false;
                        }
                        else {
                            pkg.setLimit_time(Integer.parseInt(tTime.getText()));
                        }
                    }
                    if (!tPrice.getText().equals(""))
                    {
                        if(!isNumeric(tPrice.getText())) {
                            JOptionPane.showMessageDialog(null,
                                    "Price should be a number.",
                                    "Warning",
                                    JOptionPane.WARNING_MESSAGE);
                            flag = false;
                        }
                        else {
                            pkg.setPrice(Integer.parseInt(tPrice.getText()));
                        }
                    }
                    if (!tStock.getText().equals(""))
                    {
                        if(!isNumeric(tStock.getText())) {
                            JOptionPane.showMessageDialog(null,
                                    "Stock should be a number.",
                                    "Warning",
                                    JOptionPane.WARNING_MESSAGE);
                            flag = false;
                        }
                        else {
                            pkg.setStock(Integer.parseInt(tStock.getText()));
                        }
                    }
                    if (flag) {
                        int opt = JOptionPane.showConfirmDialog(null, "Do you want to update this package?");
                        // yes is 0, no is 1, cancel is 2
                        if (opt == 0) {
                            ManagePackage.update(pkg);
                            Manager_showPackageTable();
                        }
                    }
                }
            }
        });
        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listPackage = ManagePackage.ViewAll();
                String input = JOptionPane.showInputDialog("Find", "Enter Name to search");
                if (input != null && input.length() > 0) {
                    listPackage = ManagePackage.SearchByName(input);
                    String data[][] = new String[listPackage.size()][];
                    for (int i = 0; i < listPackage.size(); i++) {
                        data[i] = new String[10];
                        data[i] = listPackage.get(i).objectConvert();
                    }
                    table1.setModel(new DefaultTableModel(
                            data,
                            new String[]{"PackageID", "Name", "Quantity Limit", "Time Limit", "Price", "Stock"}
                    ));
                    clearTextField();
                } else {
                    Manager_showPackageTable();
                }
            }
        });
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listPackage = ManagePackage.ViewAll();
                int option =comboBox1.getSelectedIndex();
                if(option == 1){
                    sortByPackageName(listPackage);
                    Manager_showPackageTable(listPackage);
                }
                else{
                    sortByPackageID(listPackage);
                    Manager_showPackageTable(listPackage);
                }
            }

        });
    }


    public void Manager_showPackageTable(){
        listPackage = ManagePackage.ViewAll();
        String data[][] = new String[listPackage.size()][];
        for (int i = 0; i < listPackage.size(); i++) {
            data[i] = new String[6];
            data[i] = listPackage.get(i).objectConvert();
        }
        table1.setModel(new DefaultTableModel(
                data,
                new String[]{"PackageID", "Name", "Quantity Limit", "Time Limit", "Price", "Stock"}
        ));
        clearTextField();
    }
    public void Manager_showPackageTable(List<Manager_PackageObject> listPackage){
        String data[][] = new String[listPackage.size()][];
        for (int i = 0; i < listPackage.size(); i++) {
            data[i] = new String[6];
            data[i] = listPackage.get(i).objectConvert();
        }
        table1.setModel(new DefaultTableModel(
                data,
                new String[]{"PackageID", "Name", "Quantity Limit", "Time Limit", "Price", "Stock"}
        ));
        clearTextField();
    }
    private void clearTextField(){
        tPkgName.setText("");
        tQuantity.setText("");
        tTime.setText("");
        tPrice.setText("");
        tStock.setText("");
    }



    private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }
    private void sortByPackageName(List<Manager_PackageObject> pkgList){
        int i, j;
        Manager_PackageObject key;
        for (i = 1; i < pkgList.size(); i++)
        {
            key = pkgList.get(i);
            j = i - 1;

            while (j >= 0 && (pkgList.get(j).getName().compareTo(key.getName()))>0)
            {
                pkgList.set(j + 1, pkgList.get(j));
                j = j - 1;
            }
            pkgList.set(j + 1, key);
        }
    }

    private void sortByPackageID(List<Manager_PackageObject> pkgList){
        int i, j;
        Manager_PackageObject key;
        for (i = 1; i < pkgList.size(); i++)
        {
            key = pkgList.get(i);
            j = i - 1;

            while (j >= 0 && (pkgList.get(j).getID().compareTo(key.getID()))>0)
            {
                pkgList.set(j + 1, pkgList.get(j));
                j = j - 1;
            }
            pkgList.set(j + 1, key);
        }
    }
}
