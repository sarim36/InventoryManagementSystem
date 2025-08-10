package inventorymanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class ViewProduct extends JFrame implements ActionListener {

    JTable table;
    JButton back, sortAscButton, sortDescButton, sortByQuantityAscButton, sortByQuantityDescButton, sortByTypeAscButton, sortByTypeDescButton;
    JLabel lbltval2, lblsval2, lblpval2;

    ViewProduct() {
        setLayout(null);

        JLabel heading = new JLabel("PRODUCT LIST");
        heading.setFont(new Font("Tahoma", Font.BOLD, 22));
        heading.setForeground(Color.MAGENTA.darker());
        heading.setBounds(400, 0, 260, 40);
        add(heading);

        JLabel lbltval = new JLabel("Total Buy Value");
        lbltval.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lbltval.setBounds(500, 500, 120, 30);
        add(lbltval);

        JLabel lbltval2 = new JLabel("");
        lbltval2.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lbltval2.setForeground(Color.BLUE.darker());
        lbltval2.setBounds(650, 500, 120, 30);
        add(lbltval2);

        JLabel lblsval = new JLabel("Total Sell Value");
        lblsval.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblsval.setBounds(500, 525, 120, 30);
        add(lblsval);

        JLabel lblsval2 = new JLabel("");
        lblsval2.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblsval2.setForeground(Color.ORANGE.darker());
        lblsval2.setBounds(650, 525, 120, 30);
        add(lblsval2);

        JLabel lblpval = new JLabel("Total Profit");
        lblpval.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblpval.setBounds(500, 550, 120, 30);
        add(lblpval);

        JLabel lblpval2 = new JLabel("");
        lblpval2.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblpval2.setForeground(Color.GREEN.darker());
        lblpval2.setBounds(650, 550, 120, 30);
        add(lblpval2);

        try {
            Conn conn = new Conn();
            String query = "SELECT SUM(`Buy Value`) AS \"Total Buy Value\" ,SUM(`Sell Value`) AS \"Total Sell Value\",(SUM(`Sell Value`)-SUM(`Buy Value`)) AS \"Total Profit\" FROM InventoryCost;";
            ResultSet rs = conn.s.executeQuery(query);
            while (rs.next()) {
                lbltval2.setText(rs.getString("Total Buy Value"));
                lblsval2.setText(rs.getString("Total Sell Value"));
                lblpval2.setText(rs.getString("Total Profit"));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 80, 960, 400);
        add(scrollPane);

        sortAscButton = new JButton("\u2193 Profit");
        sortAscButton.setBounds(60, 550, 100, 30);
        sortAscButton.setForeground(Color.WHITE);
        sortAscButton.setBackground(Color.RED.darker());
        sortAscButton.addActionListener(this);
        add(sortAscButton);

        sortDescButton = new JButton("\u2191 Profit");
        sortDescButton.setBounds(60, 500, 100, 30);
        sortDescButton.setForeground(Color.WHITE);
        sortDescButton.setBackground(Color.GREEN.darker());
        sortDescButton.addActionListener(this);
        add(sortDescButton);

        sortByQuantityAscButton = new JButton("\u2193 Quantity");
        sortByQuantityAscButton.setBounds(200, 550, 100, 30);
        sortByQuantityAscButton.setForeground(Color.WHITE);
        sortByQuantityAscButton.setBackground(Color.ORANGE.darker());
        sortByQuantityAscButton.addActionListener(this);
        add(sortByQuantityAscButton);

        sortByQuantityDescButton = new JButton("\u2191 Quantity");
        sortByQuantityDescButton.setBounds(200, 500, 100, 30);
        sortByQuantityDescButton.setForeground(Color.WHITE);
        sortByQuantityDescButton.setBackground(Color.BLUE.brighter());
        sortByQuantityDescButton.addActionListener(this);
        add(sortByQuantityDescButton);

        sortByTypeAscButton = new JButton("\u2193 Type");
        sortByTypeAscButton.setBounds(340, 550, 100, 30);
        sortByTypeAscButton.setForeground(Color.WHITE);
        sortByTypeAscButton.setBackground(Color.PINK.darker());
        sortByTypeAscButton.addActionListener(this);
        add(sortByTypeAscButton);

        sortByTypeDescButton = new JButton("\u2191 Type");
        sortByTypeDescButton.setBounds(340, 500, 100, 30);
        sortByTypeDescButton.setForeground(Color.WHITE);
        sortByTypeDescButton.setBackground(Color.MAGENTA.darker());
        sortByTypeDescButton.addActionListener(this);
        add(sortByTypeDescButton);

        back = new JButton("BACK");
        back.setForeground(Color.WHITE);
        back.setBackground(Color.DARK_GRAY);
        back.setBounds(800, 525, 140, 30);
        back.addActionListener(this);
        add(back);

        loadData();

        setBounds(250, 20, 1000, 650);
        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }

    private void loadData() {
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT * FROM productView;");
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == back) {
            setVisible(false);
            new ViewHub();
        } else if (ae.getSource() == sortAscButton) {
            sortTableBy("Profit Margin", true);
        } else if (ae.getSource() == sortDescButton) {
            sortTableBy("Profit Margin", false);
        } else if (ae.getSource() == sortByQuantityAscButton) {
            sortTableBy("Quantity", true);
        } else if (ae.getSource() == sortByQuantityDescButton) {
            sortTableBy("Quantity", false);
        } else if (ae.getSource() == sortByTypeAscButton) {
            sortTableBy("Type", true);
        } else if (ae.getSource() == sortByTypeDescButton) {
            sortTableBy("Type", false);
        }
    }

    private void sortTableBy(String columnName, boolean ascending) {
        try {
            Conn c = new Conn();
            String sortOrder = ascending ? "ASC" : "DESC";
            String query = "SELECT * FROM productView ORDER BY `" + columnName + "` " + sortOrder + ";";
            ResultSet rs = c.s.executeQuery(query);
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ViewProduct();
    }
}
