package inventorymanagementsystem;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

public class TotalProfitChart extends JFrame {

    public TotalProfitChart() {
        setLayout(new BorderLayout());
        setTitle("Total Profit Chart");

        // Create a panel for the pie chart
        JPanel chartPanel = createPieChartPanel();
        add(chartPanel, BorderLayout.CENTER);

        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createPieChartPanel() {
        DefaultPieDataset dataset = new DefaultPieDataset();

        try {
            Conn conn = new Conn();
            String query = "SELECT product_id, SUM(`Sell Value`) - SUM(`Buy Value`) AS \"Total Profit\" FROM InventoryCost GROUP BY product_id;";
            ResultSet rs = conn.s.executeQuery(query);

            while (rs.next()) {
                String productID = rs.getString("product_id");
                double totalProfit = rs.getDouble("Total Profit");
                dataset.setValue(productID, totalProfit);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Total Profit by Product",
                dataset,
                true,
                true,
                false
        );

        return new ChartPanel(chart);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TotalProfitChart());
    }
}
