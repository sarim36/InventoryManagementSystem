package inventorymanagementsystem;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;

public class SaleMonthChart extends JFrame {

    public SaleMonthChart() {
        setLayout(new BorderLayout());
        setTitle("Total Sales by Month Chart");

        // Create a panel for the bar chart
        JPanel chartPanel = createBarChartPanel();
        add(chartPanel, BorderLayout.CENTER);

        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createBarChartPanel() {
        CategoryDataset dataset = createDataset();

        JFreeChart chart = ChartFactory.createBarChart(
                "Total Sales by Month",
                "Months",
                "Total Sales",
                dataset
        );
        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, Color.DARK_GRAY);  
        plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_45);


        return new ChartPanel(chart);
    }

    private CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try {
            Conn conn = new Conn();
            String query = "SELECT MONTHNAME(sale_date) AS Months, SUM(amount) AS TotalSales FROM Sales GROUP BY MONTHNAME(sale_date);";
            ResultSet rs = conn.s.executeQuery(query);

            while (rs.next()) {
                String month = rs.getString("Months");
                double totalSales = rs.getDouble("TotalSales");
                dataset.addValue(totalSales, "Total Sales", month);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
       
        return dataset;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SaleMonthChart());
    }
}
