package inventorymanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddProduct extends JFrame implements ActionListener {

    JButton add, cancel;
    JTextField tfname, tfbuyprice,tfsellprice, tftype;
    Choice csupplier;

    AddProduct() {
        setLayout(null);

        JLabel heading = new JLabel("ADD PRODUCT");
        heading.setFont(new Font("Tahoma", Font.BOLD, 25));
        heading.setBounds(120, 20, 260, 40);
        add(heading);

        JLabel lblname = new JLabel("Product Name");
        lblname.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblname.setBounds(60, 80, 120, 30);
        add(lblname);

        tfname = new JTextField();
        tfname.setBounds(200, 80, 150, 30);
        add(tfname);

        JLabel lbltype = new JLabel("Procuct Type");
        lbltype.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lbltype.setBounds(60, 130, 120, 30);
        add(lbltype);

        tftype = new JTextField();
        tftype.setBounds(200, 130, 150, 30);
        add(tftype);

        JLabel lblbuyprice = new JLabel("Buy Price");
        lblbuyprice.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblbuyprice.setBounds(60, 180, 120, 30);
        add(lblbuyprice);

        tfbuyprice = new JTextField();
        tfbuyprice.setBounds(200, 180, 150, 30);
        add(tfbuyprice);
        
        JLabel lblsellprice = new JLabel("Sell Price");
        lblsellprice.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblsellprice.setBounds(60, 230, 120, 30);
        add(lblsellprice);

        tfsellprice = new JTextField();
        tfsellprice.setBounds(200, 230, 150, 30);
        add(tfsellprice);
        

        JLabel lbtype = new JLabel("Supplier");
        lbtype.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lbtype.setBounds(60, 280, 120, 30);
        add(lbtype);

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

        csupplier.setBounds(200, 280, 150, 25);
        add(csupplier);

        add = new JButton("ADD PRODUCT");
        add.setForeground(Color.WHITE);
        add.setBackground(Color.DARK_GRAY);
        add.setBounds(60, 350, 130, 30);
        add.addActionListener(this);
        add(add);

        cancel = new JButton("CANCEL");
        cancel.setForeground(Color.WHITE);
        cancel.setBackground(Color.DARK_GRAY);
        cancel.setBounds(220, 350, 130, 30);
        cancel.addActionListener(this);
        add(cancel);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/seventh.jpg"));
        JLabel image = new JLabel(i1);
        image.setBounds(400, 30, 500, 400);
        add(image);

        getContentPane().setBackground(Color.WHITE);
        setBounds(260, 120, 940, 500);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == add) {
            String name = tfname.getText();
            String type = tftype.getText();
            String s_buy_price = tfbuyprice.getText();
            String s_sell_price = tfsellprice.getText();
//            int price = Integer.parseInt(s_price);
            String i_supplier = csupplier.getSelectedItem();
//            String numericPart = i_supplier.replaceAll("[^0-9]", "");
//            int supplier = Integer.parseInt(numericPart);

            try {
                int buyprice = Integer.parseInt(s_buy_price);
                int sellprice = Integer.parseInt(s_sell_price);
                String numericPart = i_supplier.replaceAll("[^0-9]", "");
                int supplier = Integer.parseInt(numericPart);

                Conn conn = new Conn();
                String str = "CALL insert_product('" + name + "','" + type + "'," + buyprice + "," + sellprice + "," + supplier + ");";
                conn.s.executeUpdate(str);

                JOptionPane.showMessageDialog(null, "Product Added Successfully");
                setVisible(false);

            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error adding product: " + sqlException.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

            } catch (NumberFormatException numberFormatException) {
                numberFormatException.printStackTrace();
                JOptionPane.showMessageDialog(null, "Invalid price or supplier format", "Error", JOptionPane.ERROR_MESSAGE);

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "An unexpected error occurred", "Error", JOptionPane.ERROR_MESSAGE);

            }
        } else {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new AddProduct();
    }
}
