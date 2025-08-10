package inventorymanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;


public class InventoryCostDetail extends JFrame implements ActionListener {

    JTable table;
    JButton back,viewtb,viewts,viewp;
    JLabel lbltval2, lblsval2, lblpval2;

    InventoryCostDetail() {
        setLayout(null);

        JLabel heading = new JLabel("INVENTORY COST DETAILS");
        heading.setFont(new Font("Tahoma", Font.BOLD, 22));
        heading.setForeground(Color.ORANGE.darker());
        heading.setBounds(340, 0, 400, 40);
        add(heading);

        JLabel lbltval = new JLabel("Total Buy Value");
        lbltval.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lbltval.setBounds(50, 500, 120, 30);
        add(lbltval);

        JLabel lbltval2 = new JLabel("");
        lbltval2.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lbltval2.setForeground(Color.BLUE.darker());
        lbltval2.setBounds(200, 500, 120, 30);
        add(lbltval2);

        JLabel lblsval = new JLabel("Total Sell Value");
        lblsval.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblsval.setBounds(50, 525, 120, 30);
        add(lblsval);

        JLabel lblsval2 = new JLabel("");
        lblsval2.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblsval2.setForeground(Color.ORANGE.darker());
        lblsval2.setBounds(200, 525, 120, 30);
        add(lblsval2);

        JLabel lblpval = new JLabel("Total Profit");
        lblpval.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblpval.setBounds(50, 550, 120, 30);
        add(lblpval);

        JLabel lblpval2 = new JLabel("");
        lblpval2.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblpval2.setForeground(Color.GREEN.darker());
        lblpval2.setBounds(200, 550, 120, 30);
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

       
        back = new JButton("BACK");
        back.setForeground(Color.WHITE);
        back.setBackground(Color.DARK_GRAY);
        back.setBounds(800, 525, 140, 30);
        back.addActionListener(this);
        add(back);
        
        viewtb = new JButton("VIEW BUY CHART");
        viewtb.setForeground(Color.WHITE);
        viewtb.setBackground(Color.BLUE.darker());
        viewtb.setBounds(300, 505, 150, 20);
        viewtb.addActionListener(this);
        add(viewtb);
        
        viewts = new JButton("VIEW SALE CHART");
        viewts.setForeground(Color.WHITE);
        viewts.setBackground(Color.YELLOW.darker());
        viewts.setBounds(300, 530, 150, 20);
        viewts.addActionListener(this);
        add(viewts);
        
        viewp = new JButton("VIEW PROFIT CHART");
        viewp.setForeground(Color.WHITE);
        viewp.setBackground(Color.GREEN.darker());
        viewp.setBounds(300, 555, 150, 20);
        viewp.addActionListener(this);
        add(viewp);
        
        loadData();

        setBounds(250, 20, 1000, 650);
        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }

    private void loadData() {
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT *  FROM InventoryCostDetail;");
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
        else if (ae.getSource() == viewtb) {
            new TotalBuyValueChart();
        }
        else if (ae.getSource() == viewts) {
            new TotalSaleValueChart();
        }
        else if (ae.getSource() == viewp) {
            new TotalProfitChart();
        }
        
        
    }

  
    public static void main(String[] args) {
        new InventoryCostDetail();
    }
}
