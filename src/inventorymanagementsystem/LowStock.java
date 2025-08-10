package inventorymanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class LowStock extends JFrame implements ActionListener {

    JTable table;
    JButton back;

    LowStock() {
        setLayout(null);

        JLabel heading = new JLabel("LOW STOCK ITEMS");
        heading.setFont(new Font("Tahoma", Font.BOLD, 22));
        heading.setForeground(Color.RED.darker());
        heading.setBounds(380, 0, 260, 40);
        add(heading);

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 80, 960, 400);
        add(scrollPane);

        back = new JButton("BACK");
        back.setForeground(Color.WHITE);
        back.setBackground(Color.DARK_GRAY);
        back.setBounds(420, 500, 120, 30);
        back.addActionListener(this);
        add(back);

        loadLowStockData();

        setBounds(150, 100, 1000, 600);
        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }

    private void loadLowStockData() {
        try {
            Conn c = new Conn();
            // Adjust the SQL query to select items with quantity below 5
            ResultSet rs = c.s.executeQuery("SELECT * FROM productView WHERE quantity < 5 ORDER BY quantity ASC;");
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == back) {
            setVisible(false);
             new Hub();
        }
    }

    public static void main(String[] args) {
        new LowStock();
    }
}
