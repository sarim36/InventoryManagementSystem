package inventorymanagementsystem;

import java.awt.Color;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {

    JTextField username;
    JPasswordField password;
    JButton login, cancel;

    Login() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        JLabel user = new JLabel("Username");
        user.setBounds(40, 20, 100, 30);
        add(user);

        username = new JTextField();
        username.setBounds(160, 20, 140, 30);
        add(username);

        JLabel pass = new JLabel("Password");
        pass.setBounds(40, 70, 100, 30);
        add(pass);

        password = new JPasswordField();
        password.setBounds(160, 70, 140, 30);
        add(password);

        login = new JButton("LOGIN");
        login.setBounds(40, 150, 120, 30);
        login.setBackground(Color.GREEN);
        login.setForeground(Color.WHITE);
        login.addActionListener(this);
        add(login);

        cancel = new JButton("CANCEL");
        cancel.setBounds(180, 150, 120, 30);
        cancel.setBackground(Color.RED);
        cancel.setForeground(Color.WHITE);
        cancel.addActionListener(this);
        add(cancel);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/second.jpg"));
        Image i2 = i1.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(350, 10, 200, 200);
        add(image);
        
        setBounds(420, 200, 600, 300);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == login) {
            String user = username.getText();
            String pass = password.getText();
            if(user.equals("") || pass.equals("")){
                JOptionPane.showMessageDialog(null, "Username and Password Required");
                return;
            }
            try {
                Conn c = new Conn();
                String query = "SELECT * FROM Login WHERE user_id = '" + user + "' AND user_pass = '" + pass + "'";
                ResultSet rs = c.s.executeQuery(query);
                if (rs.next()) {
                    setVisible(false);
                    new Dashboard();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password");
                    setVisible(false);
                    new Login();
                }
            } catch (Exception e) {

                e.printStackTrace();
            }
        } else if (ae.getSource() == cancel) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
