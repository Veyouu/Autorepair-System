import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AutomotiveClientInfo {
    private JTextField txtName;
    private JTextField txtUser;
    private JButton btnSignUp;
    private JPasswordField txtPass;
    private JPasswordField txtRePass;
    private JPanel registerPanel;
    private JButton btnCancel;
    private final AutomotivePasswords clientInfo;
    String pass, rePass, user, name;

    public AutomotiveClientInfo(AutomotivePasswords clientInfo) {
        this.clientInfo = clientInfo;
        btnSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pass = txtPass.getText();
                rePass = txtRePass.getText();
                user = txtUser.getText();
                name = txtName.getText();

                if(pass.isEmpty() || rePass.isEmpty() || user.isEmpty() || name.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Fill out all blanks!", "Missing Information", JOptionPane.WARNING_MESSAGE);
                } else if(!pass.equals(rePass)){
                    JOptionPane.showMessageDialog(null, "Passwords do not match!", "Incorrect Password", JOptionPane.WARNING_MESSAGE);
                    return;
                }else{
                    clientInfo.setClientUsername(user);
                    clientInfo.setClientPass(pass);
                    clientInfo.setClientName(name);

                    if(clientInfo.checkClientCred(clientInfo.getClientUsername(), clientInfo.getClientPass(), clientInfo.getClientName())==1){
                        return;
                    };
                }

                try{
                    JFrame loginFrame = new JFrame("Login");
                    AutomotiveServiceLogin loginPanel = new AutomotiveServiceLogin(clientInfo);

                    JPanel panel = loginPanel.getMainPanel();
                    if (panel==null) {
                        JOptionPane.showMessageDialog(null, "Error: getMainPanel() returned null. Check AutomotiveServiceLogin.java!", "Null Panel Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    loginFrame.setContentPane(panel);
                    loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    loginFrame.pack();
                    loginFrame.setLocationRelativeTo(null);
                    loginFrame.setVisible(true);

                    JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(registerPanel);
                    if (currentFrame != null) {
                        currentFrame.dispose();
                    }
                } catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    JFrame loginFrame = new JFrame("Login");
                    AutomotiveServiceLogin loginPanel = new AutomotiveServiceLogin(clientInfo);

                    JPanel panel = loginPanel.getMainPanel();
                    if (panel==null) {
                        JOptionPane.showMessageDialog(null, "Error: getMainPanel() returned null. Check AutomotiveServiceLogin.java!", "Null Panel Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    loginFrame.setContentPane(panel);
                    loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    loginFrame.pack();
                    loginFrame.setLocationRelativeTo(null);
                    loginFrame.setVisible(true);

                    JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(registerPanel);
                    if (currentFrame != null) {
                        currentFrame.dispose();
                    }
                } catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }

    public JPanel getRegisterPanel(){
        return registerPanel;
    }
}
