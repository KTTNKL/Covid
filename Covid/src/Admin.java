import javax.swing.*;

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
    public Admin(){
        setContentPane(AdminPanel);
        setTitle("Admin");
        setSize(5000,2000);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public static void main(String[] args){
        new Admin();
    }
}
