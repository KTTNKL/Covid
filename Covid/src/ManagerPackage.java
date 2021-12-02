import javax.swing.*;

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

    public ManagerPackage(){
        setContentPane(ManagerPackagePanel);
        setTitle("Package");
        setSize(5000,2000);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public static void main(String[] args){
        new ManagerPackage();
    }
}
