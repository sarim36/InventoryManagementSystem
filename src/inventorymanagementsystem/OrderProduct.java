package inventorymanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class OrderProduct extends JFrame implements ActionListener, ItemListener, DocumentListener {

    JButton add, back;
    JTextField tfqty;
    Choice cpname, csname;
    JLabel lblProductId, lblProductType, lblBuyPrice, lblTotalAmount;

    OrderProduct() {
        setLayout(null);

        JLabel heading = new JLabel("ORDER PRODUCT");
        heading.setFont(new Font("Tahoma", Font.BOLD, 25));
        heading.setForeground(Color.CYAN.darker());
        heading.setBounds(280, 20, 260, 40);
        add(heading);

        JLabel lblpname = new JLabel("Product Name");
        lblpname.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblpname.setBounds(60, 80, 120, 30);
        add(lblpname);

        cpname = new Choice();
        try {
            Conn conn = new Conn();
            String query = "SELECT Distinct product_name FROM Product ; ";
            ResultSet rs = conn.s.executeQuery(query);
            while (rs.next()) {
                cpname.add(rs.getString("product_name"));
            }
            cpname.addItemListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        cpname.setBounds(200, 80, 150, 25);
        add(cpname);

        JLabel lblsname = new JLabel("Supplier Name");
        lblsname.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblsname.setBounds(400, 80, 120, 30);
        add(lblsname);

        csname = new Choice();
        updateSupplierChoice();
        csname.setBounds(540, 80, 150, 25);
        csname.addItemListener(this);
        add(csname);

        // New JLabels to display additional information
        lblProductId = new JLabel("Product ID:");
        lblProductId.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblProductId.setBounds(60, 140, 200, 30);
        add(lblProductId);

        lblBuyPrice = new JLabel("Buy Price:");
        lblBuyPrice.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblBuyPrice.setBounds(300, 140, 200, 30);
        add(lblBuyPrice);

        lblProductType = new JLabel("Product Type:");
        lblProductType.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblProductType.setBounds(540, 140, 280, 30);
        add(lblProductType);

        JLabel lblqty = new JLabel("Quantity");
        lblqty.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblqty.setBounds(60, 200, 120, 30);
        add(lblqty);

        tfqty = new JTextField();
        tfqty.setBounds(200, 200, 150, 30);
        add(tfqty);
        tfqty.getDocument().addDocumentListener(this);

        lblTotalAmount = new JLabel("Total Amount:");
        lblTotalAmount.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblTotalAmount.setBounds(540, 200, 280, 30);
        add(lblTotalAmount);

        add = new JButton("PURCHASE");
        add.setForeground(Color.WHITE);
        add.setBackground(Color.DARK_GRAY);
        add.setBounds(250, 350, 130, 30);
        add.addActionListener(this);
        add(add);

        back = new JButton("BACK");
        back.setForeground(Color.WHITE);
        back.setBackground(Color.DARK_GRAY);
        back.setBounds(430, 350, 130, 30);
        back.addActionListener(this);
        add(back);

        
        getContentPane().setBackground(Color.WHITE);
        setBounds(320, 120, 820, 500);
        setVisible(true);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == cpname && e.getStateChange() == ItemEvent.SELECTED) {
            updateSupplierChoice();
            updateProductInformation();
        }
        if ((e.getSource() == cpname || e.getSource() == csname) && e.getStateChange() == ItemEvent.SELECTED) {
            updateProductInformation();
        }
    }

    private void updateSupplierChoice() {
        csname.removeAll();

        try {
            Conn conn = new Conn();
            String selectedProduct = cpname.getSelectedItem();
            String query = "SELECT CONCAT((SELECT supplier_name FROM Supplier S where S.supplier_id=P.supplier_id),'  @Rs. ',P.buy_price)AS SName FROM Product P where product_name = '"
                    + selectedProduct + "' order by buy_price asc; ";
            ResultSet rs = conn.s.executeQuery(query);
            while (rs.next()) {
                csname.add(rs.getString("SName"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getSupplierIdForProduct(int productId) {
        try {
            Conn conn = new Conn();
            String query = "SELECT supplier_id FROM Product WHERE product_id = " + productId;
            ResultSet rs = conn.s.executeQuery(query);
            if (rs.next()) {
                return rs.getInt("supplier_id");
            } else {
                // Handle the case where no supplier ID is found for the given product ID
                System.out.println("Error: No supplier ID found for product ID " + productId);
                return -1; // Or some default value or throw an exception as needed
            }
        } catch (SQLException e) {
            // Handle SQL exceptions
            e.printStackTrace();
            return -1; // Or throw an exception as needed
        }
    }

    private void updateProductInformation() {
        try {
            Conn conn = new Conn();
            String selectedProduct = cpname.getSelectedItem();
            String selectedSupplier = csname.getSelectedItem();

            // Parse the selectedSupplier to get only the supplier name
            String supplierName = selectedSupplier.split("@Rs.")[0].trim();

            String query = "SELECT product_id, product_type, buy_price FROM Product P "
                    + "JOIN Supplier S ON P.supplier_id = S.supplier_id "
                    + "WHERE product_name = '" + selectedProduct + "' AND S.supplier_name = '" + supplierName + "';";
            ResultSet rs = conn.s.executeQuery(query);
            if (rs.next()) {
                lblProductId.setText("Product ID: " + rs.getString("product_id"));
                lblProductType.setText("Product Type: " + rs.getString("product_type"));
                lblBuyPrice.setText("Buy Price: " + rs.getString("buy_price"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {

        int qty = Integer.parseInt(tfqty.getText());
        int buyprice = Integer.parseInt(lblBuyPrice.getText().split(" ")[2]);
        int total = buyprice * qty;
        lblTotalAmount.setText("Total Amount: " + total);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        int qty = 0;
        int buyprice = Integer.parseInt(lblBuyPrice.getText().split(" ")[2]);
        int total = buyprice * qty;
        lblTotalAmount.setText("Total Amount: " + total);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        int qty = Integer.parseInt(tfqty.getText());
        int buyprice = Integer.parseInt(lblBuyPrice.getText().split(" ")[2]);
        int total = buyprice * qty;
        lblTotalAmount.setText("Total Amount: " + total);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == add) {
            int qty = Integer.parseInt(tfqty.getText());
            int productId = Integer.parseInt(lblProductId.getText().split(" ")[2]);
            qty = Integer.parseInt(tfqty.getText());
            int total = Integer.parseInt(lblTotalAmount.getText().split(" ")[2]);
            int supplierId = getSupplierIdForProduct(productId);
            try {
                Conn conn = new Conn();
                String str = "CALL order_product(" + productId + "," + qty + "," + supplierId + "," + total + ")";
                conn.s.executeUpdate(str);

                JOptionPane.showMessageDialog(null, "Product Ordered Successfully");
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
        new OrderProduct();
    }

}
