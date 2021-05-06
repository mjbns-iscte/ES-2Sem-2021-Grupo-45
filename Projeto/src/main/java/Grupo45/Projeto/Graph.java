package Grupo45.Projeto;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class Graph extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void createGraph(int a, int b, int c, int d, int e) {
		DefaultCategoryDataset barra = new DefaultCategoryDataset();
		barra.setValue(a, "True Positive", "");
		barra.setValue(b, "True Negative", "");
		barra.setValue(c, "False Positive", "");
		barra.setValue(d, "False Negative", "");
		barra.setValue(e, "Not Analysed", "");

		JFreeChart chart = ChartFactory.createBarChart("Code Smells Evaluation Quality", "Indicators",
				"Number of Occurrences", barra, PlotOrientation.VERTICAL, true, true, false);
		ChartPanel panel = new ChartPanel(chart);
		add(panel);
		this.setSize(500, 500);
		// add(new JLabel("TP: " + a +"TN: " + b +"FP: " + c +"FN: " + d +"NA: " + e));

	}
}
