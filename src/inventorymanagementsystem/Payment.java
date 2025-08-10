package inventorymanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Payment extends JFrame implements ActionListener {

    JButton pay, back, receive;
    JTextField tfpay, tfreceive;
    Choice csupplier, ccustomer;
    JLabel lbltopay1, lbltoreceive1;

    Payment() {
        setLayout(null);

        JLabel heading = new JLabel("PAYMENT");
        heading.setFont(new Font("Tahoma", Font.BOLD, 25));
        heading.setForeground(Color.GREEN.darker());
        heading.setBounds(410, 0, 260, 40);
        add(heading);

        JLabel heading1 = new JLabel("SUPPLIER");
        heading1.setFont(new Font("Tahoma", Font.BOLD, 22));
        heading1.setForeground(Color.BLUE.darker());
        heading1.setBounds(130, 60, 260, 40);
        add(heading1);

        JLabel heading2 = new JLabel("CUSTOMER");
        heading2.setFont(new Font("Tahoma", Font.BOLD, 22));
        heading2.setForeground(Color.MAGENTA.darker());
        heading2.setBounds(670, 60, 260, 40);
        add(heading2);

        JLabel lblsname = new JLabel("Supplier");
        lblsname.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblsname.setBounds(60, 120, 120, 30);
        add(lblsname);

        csupplier = new Choice();
        try {
            Conn conn = new Conn();
            String query = "SELECT CONCAT(supplier_id,' : ',supplier_name) AS supplier_info FROM Supplier; ";
            ResultSet rs = conn.s.executeQuery(query);
            while (rs.next()) {
                csupplier.add(rs.getString("supplier_info"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        csupplier.setBounds(200, 120, 150, 25);
        add(csupplier);
        csupplier.addItemListener((ItemEvent e) -> {
            try {
                Conn conn = new Conn();
                String supplier_id = csupplier.getSelectedItem().split(" : ")[0];
                String query = "SELECT payment FROM Supplier WHERE supplier_id = " + supplier_id;
                ResultSet rs = conn.s.executeQuery(query);
                if (rs.next()) {
                    lbltopay1.setText(rs.getString("payment"));
                }
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        });

        JLabel lblcname = new JLabel("Customer");
        lblcname.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblcname.setBounds(600, 120, 120, 30);
        add(lblcname);

        ccustomer = new Choice();
        try {
            Conn conn = new Conn();
            String query = "SELECT CONCAT(customer_id,' : ',customer_name) AS customer_info FROM Customer WHERE customer_id>0; ";
            ResultSet rs = conn.s.executeQuery(query);
            while (rs.next()) {
                ccustomer.add(rs.getString("customer_info"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ccustomer.setBounds(740, 120, 150, 25);
        add(ccustomer);

        ccustomer.addItemListener((ItemEvent e) -> {
            try {
                Conn conn = new Conn();
                String customer_id = ccustomer.getSelectedItem().split(" : ")[0];
                String query = "SELECT payment FROM Customer WHERE customer_id = " + customer_id;
                ResultSet rs = conn.s.executeQuery(query);
                if (rs.next()) {
                    lbltoreceive1.setText(rs.getString("payment"));
                }
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        });

        JLabel lbltopay = new JLabel("To Pay");
        lbltopay.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lbltopay.setBounds(60, 180, 120, 30);
        add(lbltopay);

        lbltopay1 = new JLabel("");
        lbltopay1.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lbltopay1.setBounds(200, 180, 120, 30);
        add(lbltopay1);

        JLabel lbltoreceive = new JLabel("To Receive");
        lbltoreceive.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lbltoreceive.setBounds(600, 180, 120, 30);
        add(lbltoreceive);

        lbltoreceive1 = new JLabel("");
        lbltoreceive1.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lbltoreceive1.setBounds(740, 180, 120, 30);
        add(lbltoreceive1);

        JLabel lblpay = new JLabel("Pay");
        lblpay.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblpay.setBounds(60, 240, 120, 30);
        add(lblpay);

        tfpay = new JTextField();
        tfpay.setBounds(200, 240, 150, 30);
        add(tfpay);

        JLabel lblreceive = new JLabel("Receive");
        lblreceive.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblreceive.setBounds(600, 240, 120, 30);
        add(lblreceive);

        tfreceive = new JTextField();
        tfreceive.setBounds(740, 240, 150, 30);
        add(tfreceive);

        pay = new JButton("PAY");
        pay.setForeground(Color.BLUE.darker());
        pay.setBackground(Color.LIGHT_GRAY);
        pay.setBounds(120, 320, 130, 30);
        pay.addActionListener(this);
        add(pay);

        receive = new JButton("RECEIVE");
        receive.setForeground(Color.GREEN.darker());
        receive.setBackground(Color.LIGHT_GRAY);
        receive.setBounds(680, 320, 130, 30);
        receive.addActionListener(this);
        add(receive);

        back = new JButton("BACK");
        back.setForeground(Color.WHITE);
        back.setBackground(Color.DARK_GRAY);
        back.setBounds(400, 400, 130, 30);
        back.addActionListener(this);
        add(back);

        getContentPane().setBackground(Color.WHITE);
        setBounds(260, 120, 1000, 500);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == pay) {
            int supplier_id = Integer.parseInt(csupplier.getSelectedItem().split(" : ")[0]);
            int amount = Integer.parseInt(tfpay.getText());

            try {
                Conn conn = new Conn();
                String str = "Call supplier_pay(" + supplier_id + "," + amount + ");";
                conn.s.executeUpdate(str);
                JOptionPane.showMessageDialog(null, "Supplier Payment Updated!");
                setVisible(false);

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "An unexpected error occurred", "Error", JOptionPane.ERROR_MESSAGE);

            }

        } else if (ae.getSource() == receive) {
            int customer_id = Integer.parseInt(ccustomer.getSelectedItem().split(" : ")[0]);
            int amount = Integer.parseInt(tfreceive.getText());

            try {
                Conn conn = new Conn();
                String str = "Call customer_pay(" + customer_id + "," + amount + ");";
                conn.s.executeUpdate(str);
                JOptionPane.showMessageDialog(null, "Customer Payment Updated!");
                setVisible(false);

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "An unexpected error occurred", "Error", JOptionPane.ERROR_MESSAGE);

            }
        } else if (ae.getSource() == back) {
            setVisible(false);
            new Hub();

        }
    }
    
    public static void main(String[] args) {
        new Payment();
    }
}
