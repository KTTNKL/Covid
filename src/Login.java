import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.Kernel;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

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

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tfPassword.getText().equals("") || tfUsername.getText().equals("")){
                    JOptionPane.showMessageDialog(null,
                            "Both field must not be blank",
                            "Login failed",
                            JOptionPane.WARNING_MESSAGE);
                }else {
                    Connect app = new Connect();
                    String password=app.getPasswordFromName(tfUsername.getText()); //Tu database
                    if(password.equals("")){
                        JOptionPane.showMessageDialog(null,
                                "Username or Password is incorrect",
                                "Login failed",
                                JOptionPane.WARNING_MESSAGE);
                    }else{
                    try {
                        if(Hash.checkPassword(password,tfPassword.getText())){
                            if(tfUsername.getText().startsWith("A")){
                                new Admin();
                                dispose();

                            }else if(tfUsername.getText().startsWith("M")){
                                if(app.isLockManager(tfUsername.getText())){
                                    JOptionPane.showMessageDialog(null,
                                            "This manager is locked",
                                            "Cannot login",
                                            JOptionPane.WARNING_MESSAGE);
                                }else{

                                    System.out.println(password);

                                    String ID=app.GetID(tfUsername.getText());
                                    new Manager(ID);
                                    dispose();
                                }

                            }else{
                                String ID=app.GetID(tfUsername.getText());
                                new User(ID);
                                dispose();
                            }
                        }else{
                            JOptionPane.showMessageDialog(null,
                                    "Username or Password is incorrect",
                                    "Login failed",
                                    JOptionPane.WARNING_MESSAGE);
                        }
                    } catch (NoSuchAlgorithmException |InvalidKeySpecException ex) {

                    }}
                }

            }
        });
    }

}
class CovidSystem {
    public static void main(String[] args) {
        Login loginFrame = new Login();
    }
}