import javax.swing.*;

public class User extends JFrame {
    private JButton findButton;
    private JButton buyButton;
    private JComboBox comboBox1;
    private JTable table1;
    private JLabel tFNameU;
    private JLabel tLNameU;
    private JLabel tYOBU;
    private JLabel tCityU;
    private JLabel tDistictU;
    private JLabel tWardU;
    private JLabel tStateU;
    private JLabel tDebtU;
    private JScrollPane PackageTable;
    private JTable table2;
    private JTable table3;
    private JButton logoutButton;
    private JScrollPane PaymentHistory;
    private JScrollPane ManagementHistory;
    private JPanel UserPanel;
    public User(){
        setContentPane(UserPanel);
        setTitle("Treatment Places");
        setSize(5000,2000);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public static void main(String[] args){
        new User();
    }
}
