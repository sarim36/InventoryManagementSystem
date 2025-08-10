package inventorymanagementsystem;

import java.awt.Color;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Dashboard extends JFrame implements ActionListener {

    Dashboard() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 1500, 1000);
        setLayout(null);
//        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/third.jpg"));
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/tester.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1350, 1350, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0, 0, 1350, 1350);
        add(image);

        JLabel text1 = new JLabel("DASHBOARD");
        text1.setBounds(200, 20, 1000, 150);
        text1.setForeground(Color.RED.darker());
        text1.setOpaque(rootPaneCheckingEnabled);
        text1.setBackground(Color.WHITE);
        text1.setBorder(BorderFactory.createLineBorder((Color.RED.darker()), 5));
        text1.setFont(new Font("Courier", Font.BOLD, 150));
        image.add(text1);

        JMenuBar mb = new JMenuBar();
        mb.setBounds(0, 0, 1500, 18);
        image.add(mb);

        JMenu manage = new JMenu("Management");
        manage.setForeground(Color.RED);
        mb.add(manage);

        JMenuItem ch = new JMenuItem("Central Hub");
        ch.addActionListener(this);
        manage.add(ch);

        
        
        JMenu view = new JMenu("Views");
        view.setForeground(Color.MAGENTA.darker());
        mb.add(view);

        JMenuItem vh = new JMenuItem("View Hub");
        vh.addActionListener(this);
        view.add(vh);
        
        JMenu admin = new JMenu("Admin");
        admin.setForeground(Color.BLUE.darker());
        mb.add(admin);
        
        JMenuItem addsupplier = new JMenuItem("Add Supplier");
        addsupplier.addActionListener(this);
        admin.add(addsupplier);

        JMenuItem addproduct = new JMenuItem("Add Product");
        addproduct.addActionListener(this);
        admin.add(addproduct);

        JMenuItem addcustomer = new JMenuItem("Add Customer");
        addcustomer.addActionListener(this);
        admin.add(addcustomer);

        
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals("Add Supplier")) {
            new AddSupplier();
        } else if (ae.getActionCommand().equals("Add Product")) {
            new AddProduct();
        } else if (ae.getActionCommand().equals("Add Customer")) {
            new AddCustomer();
        } else if (ae.getActionCommand().equals("Central Hub")) {
            new Hub();
        } else if (ae.getActionCommand().equals("View Hub")) {
            new ViewHub();
        } 
    }
    public static void main(String[] args) {
        new Dashboard();
    }
}
