package inventorymanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ViewHub extends JFrame implements ActionListener {

    JButton vcustomer, vsupplier, vproduct, vsales, vcpay, vspay, logout;

    ViewHub() {
        setLayout(null);

        JLabel lblreception = new JLabel("VIEW HUB");
        lblreception.setBounds(300, 5, 260, 40);
        lblreception.setFont(new Font("Tahoma", Font.BOLD, 26));
        add(lblreception);

        vsupplier = new JButton("SUPPLIER");
        vsupplier.setBounds(20, 100, 200, 30);
        vsupplier.setForeground(Color.WHITE);
        vsupplier.setBackground(Color.BLUE.darker());
        vsupplier.addActionListener(this);
        add(vsupplier);

        vcustomer = new JButton("CUSTOMER");
        vcustomer.setBounds(20, 150, 200, 30);
        vcustomer.setForeground(Color.WHITE);
        vcustomer.setBackground(Color.GREEN.darker());
        vcustomer.addActionListener(this);
        add(vcustomer);

        vproduct = new JButton("PRODUCT");
        vproduct.setBounds(20, 200, 200, 30);
        vproduct.setForeground(Color.WHITE);
        vproduct.setBackground(Color.MAGENTA.darker());
        vproduct.addActionListener(this);
        add(vproduct);

        vsales = new JButton("SALES");
        vsales.setBounds(20, 250, 200, 30);
        vsales.setForeground(Color.WHITE);
        vsales.setBackground(Color.ORANGE.darker());
        vsales.addActionListener(this);
        add(vsales);

        vcpay = new JButton("CUSTOMER PAYMENT");
        vcpay.setBounds(20, 300, 200, 30);
        vcpay.setForeground(Color.WHITE);
        vcpay.setBackground(Color.GREEN);
        vcpay.addActionListener(this);
        add(vcpay);

        vspay = new JButton("SUPPLIER PAYMENT");
        vspay.setBounds(20, 350, 200, 30);
        vspay.setForeground(Color.WHITE);
        vspay.setBackground(Color.BLUE);
        vspay.addActionListener(this);
        add(vspay);

        logout = new JButton("LOGOUT");
        logout.setBounds(20, 400, 200, 30);
        logout.setForeground(Color.WHITE);
        logout.setBackground(Color.RED.darker());
        logout.addActionListener(this);
        add(logout);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/ninth.jpg"));
        JLabel image = new JLabel(i1);
        image.setBounds(250, 80, 500, 344);
        add(image);

        setBounds(350, 120, 800, 570);
        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == vsupplier) {
            setVisible(false);
            new ViewSupplier();
        } else if (ae.getSource() == vcustomer) {
            setVisible(false);
            new ViewCustomer();
        } else if (ae.getSource() == vproduct) {
            setVisible(false);
            new ViewProduct();
        } else if (ae.getSource() == vsales) {
            setVisible(false);
            new ViewSales();
        } else if (ae.getSource() == vcpay) {
            setVisible(false);
            new ViewCustomerPayment();
        } else if (ae.getSource() == vspay) {
            setVisible(false);
            new ViewSupplierPayment();
        } else if (ae.getSource() == logout) {
            System.exit(0);
        }

    }

    public static void main(String[] args) {
        new ViewHub();
    }
}
