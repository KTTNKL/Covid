import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class TreatmentPlace{
    String PlaceID;
    String Name;
    int Capacity;
    int CurrentPeople;
    public TreatmentPlace(String id, String name, int cap, int numPeople){
        PlaceID = id;
        Name = name;
        Capacity = cap;
        CurrentPeople = numPeople;
    }
    String getPlaceID(){ return PlaceID; }
    String getName(){ return Name; }
    int getCap(){ return Capacity; }
    int getNumPeople(){ return CurrentPeople; }
}
public class AdminTreatmentPlace extends JFrame {
    private JTextField tTreatmentName;
    private JTextField tTreatmentCapicity;
    private JButton addButton;
    private JButton updateButton;
    private JTable table1;
    private JButton backButton;
    private JScrollPane TreatmentTable;
    private JPanel AdminTreatmentPanel;

    private String[] columnNames = { "PlaceID", "Name", "Capacity", "CurrentPeople"};
    private Connection conn;
    List<TreatmentPlace> values;

    List<String []> getCurrentPeople(){
        String sql = "Select P.PlaceID, count(*) as NumUser from TreatmentPlace P,TreatmentPlace_User U where P.PlaceID = U.PlaceID group by P.PlaceID";
        List<String []> currentPeople = new ArrayList<String []>();

        try {
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            while (rs.next()) {
                currentPeople.add(new String[] {rs.getString("PlaceID"), Integer.toString(rs.getInt("NumUser"))});
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return currentPeople;

    }
    void loadTreamentPlace(){
        if(values != null)  values.clear();
        values = new ArrayList<TreatmentPlace>();
        String sql = "SELECT * FROM TreatmentPlace";
        List<String[]> currentPeople = getCurrentPeople();
        int NumOfPeople = 0;
        try {
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            while (rs.next()) {
                NumOfPeople = 0;
                for(int i = 0; i < currentPeople.size(); i++){
                    if(rs.getString("PlaceID").equals(currentPeople.get(i)[0])){
                        NumOfPeople = Integer.valueOf(currentPeople.get(i)[1]);
                        break;
                    }
                }
                values.add(new TreatmentPlace (rs.getString("PlaceID"), rs.getString("Name"),rs.getInt("Capacity"), NumOfPeople));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        List<String[]> temp = new ArrayList<String[]>();

        for(int i = 0; i < values.size();i++){
            temp.add(new String[] {values.get(i).getPlaceID(),values.get(i).getName(),Integer.toString(values.get(i).getCap()),Integer.toString(values.get(i).getNumPeople())});
        }

        table1.setModel(new DefaultTableModel(
                temp.toArray(new Object[][]{}),
                columnNames
        ));

    }
    void addTreamentPlace(String ID){
        String sql = "INSERT INTO TreatmentPlace (PlaceID, Name, Capacity, CurrentPeople) VALUES(?,?,?,?)";

        try{
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, ID);
            pstmt.setString(2, tTreatmentName.getText());
            pstmt.setInt(3, Integer.valueOf(tTreatmentCapicity.getText()));
            pstmt.setInt(4, 0);
            pstmt.executeUpdate();
            loadTreamentPlace();
        } catch (SQLException ecpt) {
            System.out.println(ecpt.getMessage());
        }

    }
    public AdminTreatmentPlace(){
        String url = "jdbc:sqlite:D:/Java/Covid/src/covid.db";
        conn = null;
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        loadTreamentPlace();

        setContentPane(AdminTreatmentPanel);
        setTitle("Treatment Places");
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

                addTreamentPlace(ID);
            }
        });
    }
    public static void main(String[] args){
        new AdminTreatmentPlace();
    }
}
