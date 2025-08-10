package inventorymanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class ViewCustomerPayment extends JFrame implements ActionListener {

    JTable table;
    JButton back;

    ViewCustomerPayment() {
        setLayout(null);

        JLabel heading = new JLabel("CUSTOMER PAYMENT HISTORY");
        heading.setFont(new Font("Tahoma", Font.BOLD, 22));
        heading.setForeground(Color.GREEN.darker());
        heading.setBounds(300, 0, 400, 40);
        add(heading);

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 80, 960, 400);
        add(scrollPane);

        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT * FROM Customerpayment;");
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }

        back = new JButton("BACK");
        back.setForeground(Color.WHITE);
        back.setBackground(Color.DARK_GRAY);
        back.setBounds(420, 500, 120, 30);
        back.addActionListener(this);
        add(back);

        setBounds(150, 100, 1000, 600);
        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        setVisible(false);
        new ViewHub();
    }

    public static void main(String[] args) {
        new ViewCustomerPayment();
    }
}
