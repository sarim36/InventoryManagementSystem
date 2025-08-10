package inventorymanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

public class Sales extends JFrame implements ActionListener, ItemListener, DocumentListener {

    DefaultTableModel model;
    JTable table;
    JButton back, add, remove, checkout;
    Choice cpname, ccname;
    JLabel lblprice, lblstock, lblTotalAmount, lblsid, lblgt;
    JTextField tfqty;
    int grandtotal = 0;

    Sales() {
        setLayout(null);

        JLabel heading = new JLabel("SALE");
        heading.setFont(new Font("Tahoma", Font.BOLD, 30));
        heading.setForeground(Color.BLUE.darker());
        heading.setBounds(450, 15, 260, 40);
        add(heading);

        try {
            Conn conn = new Conn();
            String query = "SELECT MAX(sale_id) AS s_id FROM Sales;";
            ResultSet rs = conn.s.executeQuery(query);

            int nextSaleId = 1;

            if (rs.next()) {
                String temp = rs.getString("s_id");
                if (temp != null) {
                    nextSaleId = Integer.parseInt(temp) + 1;
                }
            }

            JLabel lblsid1 = new JLabel("Sale ID");
            lblsid1.setFont(new Font("Tahoma", Font.PLAIN, 17));
            lblsid1.setBounds(20, 300, 80, 30);
            add(lblsid1);

            lblsid = new JLabel("" + nextSaleId);
            lblsid.setFont(new Font("Tahoma", Font.PLAIN, 17));
            lblsid.setBounds(200, 300, 40, 30);
            add(lblsid);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel lblcname = new JLabel("Customer ID");
        lblcname.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblcname.setBounds(20, 340, 120, 30);
        add(lblcname);

        ccname = new Choice();
        try {
            Conn conn = new Conn();
            String query = "SELECT CONCAT(customer_id,' : ',customer_name)AS cinfo FROM Customer; ";
            ResultSet rs = conn.s.executeQuery(query);
            while (rs.next()) {
                ccname.add(rs.getString("cinfo"));
            }
            ccname.addItemListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ccname.setBounds(200, 340, 200, 25);
        add(ccname);

        JLabel lblpname = new JLabel("Product Name");
        lblpname.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblpname.setBounds(20, 380, 120, 30);
        add(lblpname);

        cpname = new Choice();
        try {
            Conn conn = new Conn();
            String query = "SELECT CONCAT(product_id,' : ',product_name,' @Rs. ',sell_price)AS product FROM Product ; ";
            ResultSet rs = conn.s.executeQuery(query);
            while (rs.next()) {
                cpname.add(rs.getString("product"));
            }
            cpname.addItemListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        cpname.setBounds(200, 380, 200, 25);
        add(cpname);

        JLabel lblprice1 = new JLabel("Price");
        lblprice1.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblprice1.setBounds(20, 420, 80, 30);
        add(lblprice1);

        lblprice = new JLabel();
        lblprice.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblprice.setBounds(200, 420, 40, 30);
        add(lblprice);

        JLabel lblstock1 = new JLabel("In Stock");
        lblstock1.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblstock1.setBounds(20, 460, 80, 30);
        add(lblstock1);

        lblstock = new JLabel();
        lblstock.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblstock.setBounds(200, 460, 40, 30);
        add(lblstock);

        JLabel lblqty = new JLabel("Quantity");
        lblqty.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblqty.setBounds(20, 500, 120, 30);
        add(lblqty);

        tfqty = new JTextField();
        tfqty.setBounds(200, 500, 70, 30);
        add(tfqty);
        tfqty.getDocument().addDocumentListener(this);

        JLabel lblTotalAmount1 = new JLabel("Total Amount:");
        lblTotalAmount1.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblTotalAmount1.setBounds(20, 540, 280, 30);
        add(lblTotalAmount1);

        lblTotalAmount = new JLabel();
        lblTotalAmount.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblTotalAmount.setBounds(200, 540, 280, 30);
        add(lblTotalAmount);

        JLabel lblgrandtotal = new JLabel("Grand Total");
        lblgrandtotal.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblgrandtotal.setBounds(500, 340, 120, 30);
        add(lblgrandtotal);

        lblgt = new JLabel("Rs. 0");
        lblgt.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblgt.setBounds(680, 340, 280, 30);
        add(lblgt);

        Vector<String> columnNames = new Vector<>();
        columnNames.add("Sale ID");
        columnNames.add("Customer ID");
        columnNames.add("Product ID");
        columnNames.add("Quantity");
        columnNames.add("Total Amount");

        model = new DefaultTableModel(null, columnNames);
        table = new JTable(model);
        table.setBounds(0, 200, 800, 300);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 80, 945, 200);
        add(scrollPane);

//        try {
//            Conn c = new Conn();
//            ResultSet rs = c.s.executeQuery("SELECT * FROM customer");
//            table.setModel(DbUtils.resultSetToTableModel(rs));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        add = new JButton("ADD");
        add.setForeground(Color.WHITE);
        add.setBackground(Color.GREEN.darker());
        add.setBounds(50, 600, 120, 30);
        add.addActionListener(this);
        add(add);

        remove = new JButton("REMOVE");
        remove.setForeground(Color.WHITE);
        remove.setBackground(Color.RED.darker());
        remove.setBounds(200, 600, 120, 30);
        remove.addActionListener(this);
        add(remove);

        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            model.removeRow(selectedRow);
        }

        back = new JButton("BACK");
        back.setForeground(Color.WHITE);
        back.setBackground(Color.DARK_GRAY);
        back.setBounds(500, 600, 250, 30);
        back.addActionListener(this);
        add(back);

        checkout = new JButton("CHECKOUT");
        checkout.setForeground(Color.WHITE);
        checkout.setBackground(Color.BLACK);
        checkout.setBounds(500, 400, 250, 40);
        checkout.addActionListener(this);
        add(checkout);

        
        setBounds(250, 20, 1000, 700);
        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }

    private void updateprice() {
        try {
            Conn conn = new Conn();
            String selectedProduct = cpname.getSelectedItem();

            String productId = selectedProduct.split(" : ")[0];

            String query = "SELECT sell_price FROM Product WHERE product_id = " + productId;
            ResultSet rs = conn.s.executeQuery(query);
            if (rs.next()) {
                lblprice.setText(rs.getString("sell_price"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updatestock() {
        try {
            Conn conn = new Conn();
            String selectedProduct = cpname.getSelectedItem();
            String query = "SELECT quantity FROM Inventory WHERE product_id = "
                    + "SUBSTRING_INDEX(SUBSTRING_INDEX('" + selectedProduct + "', ' : ', -2), ' : ', 1)";
            ResultSet rs = conn.s.executeQuery(query);
            if (rs.next()) {
                lblstock.setText(rs.getString("quantity"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == add) {
            try {
                int saleId = Integer.parseInt(lblsid.getText());
                String customerInfo = ccname.getSelectedItem();
                int customerId = Integer.parseInt(customerInfo.split(" : ")[0]);
                String productInfo = cpname.getSelectedItem();
                int productId = Integer.parseInt(productInfo.split(" : ")[0]);
                int quantity = Integer.parseInt(tfqty.getText());
                int totalAmount = Integer.parseInt(lblTotalAmount.getText());
                int stock = Integer.parseInt(lblstock.getText());
                grandtotal += totalAmount;
                if (quantity <= stock) {
                    Vector<Object> rowData = new Vector<>();
                    rowData.add(saleId);
                    rowData.add(customerId);
                    rowData.add(productId);
                    rowData.add(quantity);
                    rowData.add(totalAmount);

                    model.addRow(rowData);

                    tfqty.setText("");
                    totalAmount = 0;
                    lblgt.setText("Rs. " + grandtotal);
                } else {
                    JOptionPane.showMessageDialog(null, "Quantity Exceeds the Stock");

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == remove) {
            int selectedRow = table.getSelectedRow();
            int temp = (int) table.getValueAt(table.getSelectedRow(), table.getColumnModel().getColumnIndex("Total Amount"));
            try {
                grandtotal -= temp;
                if (selectedRow != -1) {
                    model.removeRow(selectedRow);
                    lblgt.setText("Rs. " + grandtotal);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Quantity Exceeds the Stock");
                e.printStackTrace();
            }

        } else if(ae.getSource() == checkout){
        for (int row = 0; row < table.getRowCount(); row++) {
            int saleId = (int) table.getValueAt(row, table.getColumnModel().getColumnIndex("Sale ID"));
            int customerId = (int) table.getValueAt(row, table.getColumnModel().getColumnIndex("Customer ID"));
            int productId = (int) table.getValueAt(row, table.getColumnModel().getColumnIndex("Product ID"));
            int quantity = (int) table.getValueAt(row, table.getColumnModel().getColumnIndex("Quantity"));
            int totalAmount = (int) table.getValueAt(row, table.getColumnModel().getColumnIndex("Total Amount"));
            
             try {
                Conn conn = new Conn();
                CallableStatement callableStatement = conn.c.prepareCall("{call proc_sale(?, ?, ?, ?, ?)}");
                callableStatement.setInt(1, saleId);
                callableStatement.setInt(2, customerId);
                callableStatement.setInt(3, productId);
                callableStatement.setInt(4, quantity);
                callableStatement.setInt(5, totalAmount);

                callableStatement.execute();

            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error executing proc_sale: " + sqlException.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        JOptionPane.showMessageDialog(null, "CHECKOUT COMPLETE!");
        setVisible(false);
        
        }else if (ae.getSource() == back) {
            setVisible(false);
            new Hub();
        }
    }

    public static void main(String[] args) {
        new Sales();
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == cpname && e.getStateChange() == ItemEvent.SELECTED) {
            updateprice();
            updatestock();
        }
        if (e.getSource() == ccname && e.getStateChange() == ItemEvent.SELECTED) {
           ccname.disable();
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {

        int qty = Integer.parseInt(tfqty.getText());
        int buyprice = Integer.parseInt(lblprice.getText());
        int total = buyprice * qty;
        lblTotalAmount.setText("" + total);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        int qty = 0;
        int buyprice = Integer.parseInt(lblprice.getText());
        int total = buyprice * qty;
        lblTotalAmount.setText("" + total);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        int qty = Integer.parseInt(tfqty.getText());
        int buyprice = Integer.parseInt(lblprice.getText());;
        int total = buyprice * qty;
        lblTotalAmount.setText("" + total);
    }
}
