package inventorymanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Hub extends JFrame implements ActionListener {

    JButton Sales, orderprod, payment, logout, popprod, costdetail, lowstock;

    Hub() {
        setLayout(null);

        JLabel lblreception = new JLabel("CENTRAL HUB");
        lblreception.setBounds(300, 5, 260, 40);
        lblreception.setFont(new Font("Tahoma", Font.BOLD, 26));
        add(lblreception);

        Sales = new JButton("SALE");
        Sales.setBounds(20, 100, 200, 30);
        Sales.setForeground(Color.WHITE);
        Sales.setBackground(Color.BLUE.darker());
        Sales.addActionListener(this);
        add(Sales);

        orderprod = new JButton("ORDER PRODUCT");
        orderprod.setBounds(20, 150, 200, 30);
        orderprod.setForeground(Color.WHITE);
        orderprod.setBackground(Color.CYAN.darker());
        orderprod.addActionListener(this);
        add(orderprod);

        payment = new JButton("PAYMENT");
        payment.setBounds(20, 200, 200, 30);
        payment.setForeground(Color.WHITE);
        payment.setBackground(Color.GREEN.darker());
        payment.addActionListener(this);
        add(payment);

        popprod = new JButton("POPULAR PRODUCT");
        popprod.setBounds(20, 250, 200, 30);
        popprod.setForeground(Color.WHITE);
        popprod.setBackground(Color.PINK);
        popprod.addActionListener(this);
        add(popprod);

        lowstock = new JButton("LOW STOCK");
        lowstock.setBounds(20, 300, 200, 30);
        lowstock.setForeground(Color.WHITE);
        lowstock.setBackground(Color.RED);
        lowstock.addActionListener(this);
        add(lowstock);

        costdetail = new JButton("COST DETAIL");
        costdetail.setBounds(20, 350, 200, 30);
        costdetail.setForeground(Color.WHITE);
        costdetail.setBackground(Color.ORANGE.darker());
        costdetail.addActionListener(this);
        add(costdetail);

        logout = new JButton("LOGOUT");
        logout.setBounds(20, 400, 200, 30);
        logout.setForeground(Color.WHITE);
        logout.setBackground(Color.RED.darker());
        logout.addActionListener(this);
        add(logout);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/eight.jpg"));
        JLabel image = new JLabel(i1);
        image.setBounds(250, 80, 500, 344);
        add(image);

        setBounds(350, 120, 800, 570);
        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == Sales) {
            setVisible(false);
            new Sales();
        } else if (ae.getSource() == orderprod) {
            setVisible(false);
            new OrderProduct();
        } else if (ae.getSource() == payment) {
            setVisible(false);
            new Payment();
        } else if (ae.getSource() == popprod) {
            setVisible(false);
            new PopularProduct();
        } else if (ae.getSource() == costdetail) {
            setVisible(false);
            new InventoryCostDetail();
        } else if (ae.getSource() == lowstock) {
            setVisible(false);
            new LowStock();
        } else if (ae.getSource() == logout) {
            System.exit(0);
        }

    }

    public static void main(String[] args) {
        new Hub();
    }
}
