package Grupo45.Projeto;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class Graph extends JPanel {

	public Graph() {

//        createGraph(200,50,6,4);
//        setVisible(true);
	}

	public void createGraph(int a, int b, int c, int d) {
		DefaultCategoryDataset barra = new DefaultCategoryDataset();
		barra.setValue(a, "True Positive", "");
		barra.setValue(b, "True Negative", "");
		barra.setValue(c, "False Positive", "");
		barra.setValue(d, "False Negative", "");

		JFreeChart chart = ChartFactory.createBarChart("Code Smells Evaluation Quality", "Indicators",
				"Number of Occurrences", barra, PlotOrientation.VERTICAL, true, true, false);
		ChartPanel panel = new ChartPanel(chart);
		add(panel);

	}

	public static void main(String[] args) {
		Graph g = new Graph();
	}

}
