package Grupo45.Projeto;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Date May 07-2021
 * This class represents a histogram as a JFrame
 * @author G45
 * @author Andre Amado, Guilherme Henriques, João Guerra, Miguel Nunes, Francisco Mendes, Tiago Geraldo
 * @version 1.0
 */

public class Graph extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * This method creates a histogram for the given int values
	 * 
	 * @param a is the value of the first histogram column
	 * @param b is the value of the second histogram column
	 * @param c is the value of the third histogram column
	 * @param d is the value of the fourth histogram column
	 * @param e is the value of the fifth histogram column
	 */

	public void createGraph(int a, int b, int c, int d, int e) {
		DefaultCategoryDataset barra = new DefaultCategoryDataset();
		barra.setValue(a, "True Positive", "");
		barra.setValue(b, "True Negative", "");
		barra.setValue(c, "False Positive", "");
		barra.setValue(d, "False Negative", "");
		barra.setValue(e, "Not Analysed", "");
		
		JFreeChart chart = ChartFactory.createBarChart("Code Smells Evaluation Quality", "Indicators",
				"Number of Occurrences", barra, PlotOrientation.VERTICAL, true, true, false);
		chart.addSubtitle(new TextTitle("True Positive: " + a +" True Negative: " + b +" False Positive: " + c +" False Negative: " + d +" NA: " + e));
		ChartPanel panel = new ChartPanel(chart);
		add(panel);
		this.setSize(500, 500);

	}
}
