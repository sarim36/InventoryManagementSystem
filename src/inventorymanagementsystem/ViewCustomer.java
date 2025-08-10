package inventorymanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class ViewCustomer extends JFrame implements ActionListener {

    JTable table;
    JButton back;

    ViewCustomer() {
        setLayout(null);
        
        JLabel heading = new JLabel("CUSTOMER LIST");
        heading.setFont(new Font("Tahoma", Font.BOLD, 22));
        heading.setForeground(Color.GREEN.darker());
        heading.setBounds(400, 0, 260, 40);
        add(heading);

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 80, 960, 400);
        add(scrollPane);
        
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT customer_id AS \"ID\", customer_name AS \"Name\", phone AS \"Phone Number\", payment AS \"Customer Outstanding\" FROM Customer ORDER BY payment DESC;");
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
        new ViewCustomer();
    }
}
