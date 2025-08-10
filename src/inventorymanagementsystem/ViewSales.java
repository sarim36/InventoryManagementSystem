package inventorymanagementsystem;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;


public class ViewSales extends JFrame implements ActionListener {

    JTable table;
    JButton back,viewchart;
    JLabel lblsale2, lblpsold2, lblscount2;

    ViewSales() {
        setLayout(null);

        JLabel heading = new JLabel("SALES RECORD");
        heading.setFont(new Font("Tahoma", Font.BOLD, 22));
        heading.setForeground(Color.ORANGE.darker());
        heading.setBounds(400, 0, 400, 40);
        add(heading);

        JLabel lblsale = new JLabel("Total Sales");
        lblsale.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblsale.setBounds(50, 500, 150, 30);
        add(lblsale);

        lblsale2 = new JLabel("");
        lblsale2.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblsale2.setForeground(Color.GREEN.darker());
        lblsale2.setBounds(270, 500, 120, 30);
        add(lblsale2);

        JLabel lblpsold = new JLabel("Total Product Sold");
        lblpsold.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblpsold.setBounds(50, 525, 150, 30);
        add(lblpsold);

        lblpsold2 = new JLabel("");
        lblpsold2.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblpsold2.setForeground(Color.ORANGE.darker());
        lblpsold2.setBounds(270, 525, 120, 30);
        add(lblpsold2);

        JLabel lblscount = new JLabel("Total Sale Count");
        lblscount.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblscount.setBounds(50, 550, 150, 30);
        add(lblscount);

        lblscount2 = new JLabel("");
        lblscount2.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblscount2.setForeground(Color.BLUE.darker());
        lblscount2.setBounds(270, 550, 120, 30);
        add(lblscount2);

        try {
            Conn conn = new Conn();
            String query = "SELECT count(DISTINCT(sale_id))AS salecount,sum(quantity)AS tquantity,sum(amount) AS tsales FROM Sales;";
            ResultSet rs = conn.s.executeQuery(query);
            while (rs.next()) {
                lblsale2.setText(rs.getString("tsales"));
                lblpsold2.setText(rs.getString("tquantity"));
                lblscount2.setText(rs.getString("salecount"));

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
        
        viewchart = new JButton("VIEW BAR CHART");
        viewchart.setForeground(Color.WHITE);
        viewchart.setBackground(Color.RED.darker());
        viewchart.setBounds(420, 525, 140, 30);
        viewchart.addActionListener(this);
        add(viewchart);

        
        loadData();

        setBounds(250, 20, 1000, 650);
        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }

    private void loadData() {
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT *  FROM Sales ORDER BY sale_id DESC ;");
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
        }
        else if (ae.getSource() == viewchart) {
            new SaleMonthChart();
        }
        
        
    }

  
    public static void main(String[] args) {
        new ViewSales();
    }
}
