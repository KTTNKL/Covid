import javax.swing.*;

public class Manager extends JFrame {
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
    private JTable table1;
    private JTextField tSource;
    private JScrollPane tableManager;
    private JButton backButton;
    private JButton packageButton;
    private JPanel ManagerPanel;
    private JComboBox comboBox1;

    public Manager(){
        setContentPane(ManagerPanel);
        setTitle("Manager");
        setSize(5000,2000);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public static void main(String[] args){
        new Manager();
    }
}
