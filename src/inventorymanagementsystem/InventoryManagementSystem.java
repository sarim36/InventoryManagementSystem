package inventorymanagementsystem;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InventoryManagementSystem extends JFrame {

    public InventoryManagementSystem() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(350, 200, 700, 400);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/loading_bar.gif"));
        JLabel image = new JLabel(i1);
        image.setBounds(0, 0, 700, 400);
        add(image);

        JLabel text1 = new JLabel("Stationery");
        text1.setBounds(80, 10, 700, 90);
        text1.setForeground(Color.MAGENTA);
        text1.setFont(new Font("serif", Font.BOLD, 40));
        image.add(text1);
        
        JLabel text2 = new JLabel("Management");
        text2.setBounds(260, 10, 700, 90);
        text2.setForeground(Color.RED);
        text2.setFont(new Font("serif", Font.BOLD, 40));
        image.add(text2);
        
        JLabel text3 = new JLabel("System");
        text3.setBounds(500, 10, 700, 90);
        text3.setForeground(Color.ORANGE);
        text3.setFont(new Font("serif", Font.BOLD, 40));
        image.add(text3);
        
        Timer timer = new Timer(5000, e -> {
            setVisible(false);
            new Login();
        });
        timer.setRepeats(false); 
        timer.start();

        setVisible(true);
    }

    public static void main(String[] args) {
        new InventoryManagementSystem();
    }
}
