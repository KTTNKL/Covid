import javax.swing.*;

public class AdminTreatmentPlace extends JFrame {
    private JTextField tTreatmentName;
    private JTextField tTreatmentCapicity;
    private JButton addButton;
    private JButton updateButton;
    private JTable table1;
    private JButton backButton;
    private JScrollPane TreatmentTable;
    private JPanel AdminTreatmentPanel;
    public AdminTreatmentPlace(){
        setContentPane(AdminTreatmentPanel);
        setTitle("Treatment Places");
        setSize(5000,2000);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public static void main(String[] args){
        new AdminTreatmentPlace();
    }
}
