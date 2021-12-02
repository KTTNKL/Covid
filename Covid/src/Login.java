import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.Kernel;

public class Login extends JFrame {
    private JTextField tfPassword;
    private JTextField tfUsername;
    private JButton btnLogin;
    private JPanel loginPanel;

    public Login(){
        setContentPane(loginPanel);
        setTitle("Covid Login");
        setSize(450,300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

    }
    public static void main(String[] args){

        Login loginFrame= new Login();
    }
}
