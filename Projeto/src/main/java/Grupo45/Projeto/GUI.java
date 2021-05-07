package Grupo45.Projeto;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


	/**
	 * Date May 06-2021
	 * This program is able to extract metrics from all .java files in a given source.
	 * It analyzes the code of java files for the metrics 'Lines of Code', 'Weighted Metric Count', 'Number of Class Methods' and 'Cyclomatic Complexity' creating a .xlsx file with the data.
	 * It has a Graphical User Interface that also shows the analyzed metrics and allows users to create rules for Code Smells evaluation.
	 * The GUI shows the detected code smells for the given rule, and has functionalities of rule manipulation.
	 * 
	 * This is the the graphical user interface and the main class of the program
	 * @author G45
	 * @author Andre Amado, Guilherme Henriques, João Guerra, Miguel Nunes, Francisco Mendes, Tiago Geraldo
	 * @version 1.0
	 */
public class GUI {
		/**
		 * Main frame
		 */
		private JFrame f = new JFrame();
		/**
		 * Secondary frame with rule manipulation functions
		 */
		private JFrame f1 = new JFrame();
		/**
		 * Auxiliar label for errors
		 */
		private JLabel l = new JLabel();
		
		/**
		 * The metrics shown in the secondary frame that are available for rule creation
		 */
		private String[] metodos = new String[] { "LOC_method", "CYCLO_method", "LOC_class", "NOM_class", "WMC_class" };
		/**
		 * The operators available for rule creation and manipulation
		 */
		private String[] options = new String[] { "AND", "OR" };
		/**
		 * The logical signals available for rule creation and manipulation
		 */
		private String[] sinais = new String[] { ">", "<", ">=", "<=", "=" };

		/**
		 * Drop down menu with metrics names
		 */
		private JComboBox<String> method1 = new JComboBox<String>(metodos);
		/**
		 * Drop down menu with metrics names
		 */
		private JComboBox<String> method2 = new JComboBox<String>(metodos);
		/**
		 * Drop down menu with metrics names
		 */
		private JComboBox<String> method3 = new JComboBox<String>(metodos);

		/**
		 * Drop down menu with logical operators available
		 */
		private JComboBox<String> option2 = new JComboBox<String>(options);
		/**
		 * Drop down menu with available logical operators
		 */
		private JComboBox<String> option1 = new JComboBox<String>(options);

		/**
		 * Drop down menu with available logical signals
		 */
		private JComboBox<String> sinal1 = new JComboBox<String>(sinais);
		/**
		 * Drop down menu with available logical signals
		 */
		private JComboBox<String> sinal2 = new JComboBox<String>(sinais);
		/**
		 * Drop down menu with available logical signals
		 */
		private JComboBox<String> sinal3 = new JComboBox<String>(sinais);
		/**
		 * Drop down menu that will display active rules
		 */
		private JComboBox<String> ruleSelect = new JComboBox<String>();

		/**
		 * Name of the rule beeing created/modified
		 */
		private JTextField nome = new JTextField(10);
		/**
		 * Value of first condition
		 */
		private JTextField text1 = new JTextField(2);
		/**
		 * Value of second condition
		 */
		private JTextField text2 = new JTextField(2);
		/**
		 * Value of third condition
		 */
		private JTextField text3 = new JTextField(2);

		/**
		 * Main panel of main frame
		 */
		private JPanel jp = new JPanel();
		/**
		 * Main panel of the secondary frame
		 */
		private JPanel jp1 = new JPanel();

		/**
		 * Panel with the first row of components to modify the first condition, at the secondary frame
		 */
		private JPanel jp3 = new JPanel();
		/**
		 * Panel with the all the buttons, at the secondary frame
		 */
		private JPanel jp4 = new JPanel();
		/**
		 * Panel with the secondary row of components to modify the secondary condition, at the secondary frame
		 */
		private JPanel jp5 = new JPanel();
		/**
		 * Panel with the third row of components to modify the third condition, at the secondary frame
		 */
		private JPanel jp6 = new JPanel();

		/**
		 * Checkbox at the secondary frame that indicates if the second condition will be used
		 */
		private JCheckBox cond1 = new JCheckBox("use this condition", false);
		/**
		 * Checkbox at the secondary frame that indicates if the third condition will be used
		 */
		private JCheckBox cond2 = new JCheckBox("use this condition", false);

		/**
		 * OPEN button at main frame
		 */
		private JButton button = new JButton("OPEN");
		/**
		 * SAVE RULE button at secondary frame
		 */
		private JButton button1 = new JButton("SAVE RULE");
		/**
		 * RULES button at main frame
		 */
		private JButton button2 = new JButton("RULES");
		/**
		 * APPLY RULE button at secondary frame
		 */
		private JButton button3 = new JButton("APPLY RULE");
		/**
		 * MODIFY RULE button at secondary frame
		 */
		private JButton button4 = new JButton("MODIFY RULE");
		/**
		 * DELETE RULE button at secondary frame
		 */
		private JButton button5 = new JButton("DELETE RULE");
		/**
		 * CHECK SMELLS button at secondary frame
		 */
		private JButton button6 = new JButton("CHECK SMELLS");

		/**
		 * Excel header 
		 */
		private String[] columnNames = { "MethodID", "package", "class", "method", "NOM_class", "LOC_class", "WMC_class",
				"LOC_method", "CYCLO_method" };

		/**
		 * Excel file where the metrics will be exported and where the Rules will be applied
		 */
		private Excel excel;

		/**
		 * Frame with a histogram of code smells quality
		 */
		private Graph graph = new Graph();
		/**
		 * Panel with detected code smells information
		 */
		private TestPane smells = new TestPane();

		/**
		 * HashMap with the correct code smells values from Code_Smells.xlsx for rules is_Long_Method
		 */
		HashMap<String, Boolean> islong = new HashMap<>();
		/**
		 * HashMap with the correct code smells values from Code_Smells.xlsx for rules is_God_Class
		 */
		HashMap<String, Boolean> isgod = new HashMap<>();
		/**
		 * LinkedHashSet with Code Smells detected from a given Rule on the GUI
		 */
		LinkedHashSet<String> codeSmells = new LinkedHashSet<>();

		/**
		 * Main Metrics class being used
		 */
		Metricas_Metodos mm;

		/**
		 * Creates and initializes the graphical user interface with all the components
		 * @throws IOException when it finds a problem with File or Excel input
		 */
		public GUI() throws IOException {
			mm = new Metricas_Metodos();
			f = new JFrame("GUI");
			f.setSize(1000, 750);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.setLayout(new BorderLayout());
			jp1.setLayout(new BoxLayout(jp1, BoxLayout.PAGE_AXIS));
			jp.setLayout(new BorderLayout());
			f.add(jp, BorderLayout.NORTH);
			f.add(button2, BorderLayout.SOUTH);

			mm.readTextFile();
			addOpenButton();

			for (Rule r : mm.getRuleArray())
				ruleSelect.addItem(r.getRuleName());
			jp3.add(new JLabel("Rule Name:"));

			readCodeSmellsExcel();
			addRulesButton();
			addSaveButton();
			addApplyButton();
			addModifyButton();
			addDeleteButton();
			addExitAction();
			addCheckCodeSmells();
		}

		/**
		 * Adds Open button to main frame that opens a file explorer windows. User selects the file where he will search for java files
		 */
		public void addOpenButton() {
			jp.add(button, BorderLayout.NORTH);
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					String command = evt.getActionCommand();
					l.setText("the user cancelled the operation");
					if (command.equals("OPEN")) {
						JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
						j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						int r = j.showOpenDialog(null);
						if (r == JFileChooser.APPROVE_OPTION) {
							l.setText(j.getSelectedFile().getAbsolutePath());
							File mainDir = j.getSelectedFile();
							String path = j.getSelectedFile().getAbsolutePath();
							ArrayList<File> files = mm.search(mainDir);
							Path pathToAFile = Paths.get(path);
							System.out.println("Path :" + path);

							excel = new Excel();
							String pathExcel = path + "\\" + pathToAFile.getFileName().toString() + "_metricas.xlsx";
							try {
								excel.setupExcel(pathExcel);

								mm.metricsToExcel(files, excel);

								excelToGUI(pathExcel);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			});
		}

		/**
		 * Imports excel file with metric values to gUI
		 * @param pathExcel is the path of the Excel file to be imported
		 * @throws IOException when excel file is not found
		 */
		public void excelToGUI(String pathExcel) throws IOException {
			File file = new File(pathExcel);
			System.out.println(file.toString());
			FileInputStream is = new FileInputStream(file);
			Workbook w = new XSSFWorkbook(is);
			org.apache.poi.ss.usermodel.Sheet sheet = w.getSheetAt(0);
			Iterator<Row> it = sheet.iterator();

			String[][] matriz = new String[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
			it.next();
			for (int i = 0; it.hasNext(); i++) {
				Row row = it.next();
				Iterator<Cell> itcell = row.cellIterator();

				for (int j = 0; itcell.hasNext(); j++) {
					Cell cell = itcell.next();
					String aux;
					if (cell.toString().isEmpty()) {
						aux = "empty";
					}
					aux = cell.toString();
					matriz[i][j] = aux;
				}
			}

			JTable table = new JTable(matriz, columnNames);
			jp.add(table, BorderLayout.SOUTH);
			jp.add(new JScrollPane(table));
			f.setVisible(true);
			is.close();
			w.close();
		}

		/**
		 * Class that represents a Panel. Displays Code Smells details
		 * @author G45
		 * @author André Amado, Guilherme Henriques, João Guerra, Miguel Nunes, Francisco Mendes, Tiago Geraldo
		 *
		 */
		public class TestPane extends JPanel {

			private static final long serialVersionUID = 1L;

			/**
			 * Adds the Code Smells information to the TestPane
			 * @param codeSmells is the LinkedHashSet with the Code Smells detected with given Rule
			 */
			public void update(LinkedHashSet<String> codeSmells) {
				setLayout(new GridBagLayout());
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.gridwidth = GridBagConstraints.REMAINDER;
				for (String s : codeSmells) {
					JLabel label = new JLabel("Code Smell at " + s);
					label.setPreferredSize(new Dimension(400, 50));
					add(label, gbc);
				}
			}
		}

		/**
		 * Adds Rules button to main frame, that displays rule manipulation frame when selected
		 */
		public void addRulesButton() {
			button2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt2) {
					String command = evt2.getActionCommand();
					if (command.equals("RULES")) {

						f1 = new JFrame("Rules");
						f1.setSize(1000, 375);
						f1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						f1.setLayout(new BorderLayout());
						f1.add(jp1, BorderLayout.CENTER);
//						jp1.add(jp2, BorderLayout.NORTH); <-------------------------------------
						jp1.add(jp3, BorderLayout.CENTER);
						f1.add(jp4, BorderLayout.SOUTH);
						jp1.add(jp5, BorderLayout.CENTER);
						jp1.add(jp6, BorderLayout.CENTER);

						jp4.add(button1);
						jp4.add(ruleSelect);
						jp4.add(button3);
						jp4.add(button4);
						jp4.add(button5);
						jp4.add(button6);

						jp3.add(nome);
						jp3.add(method1);
						jp3.add(sinal1);
						jp3.add(text1);

						jp5.add(cond1);
						jp5.add(option1);
						jp5.add(method2);
						jp5.add(sinal2);
						jp5.add(text2);

						jp6.add(cond2);
						jp6.add(option2);
						jp6.add(method3);
						jp6.add(sinal3);
						jp6.add(text3);

						jp1.add(new JScrollPane(smells));

						f1.setVisible(true);

					} else
						l.setText("the user cancelled the operation");
				}
			});
		}

		/**
		 * Adds Save button to secondary frame. When selected
		 */
		public void addSaveButton() {
			button1.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent evt3) {
					String command3 = evt3.getActionCommand();
					System.out.println(command3);
					l.setText("the user cancelled the operation");

					ArrayList<Condition> conditions = new ArrayList<>();

					if (command3.equals("SAVE RULE")) {
						ArrayList<String> operators = new ArrayList<>();

						if (nome.getText() != "") {
							Condition c = new Condition(method1.getSelectedItem().toString(),
									sinal1.getSelectedItem().toString(), Integer.parseInt(text1.getText()));
							conditions.add(c);
						}
						if (cond1.isSelected()) {
							Condition c1 = new Condition(method2.getSelectedItem().toString(),
									sinal2.getSelectedItem().toString(), Integer.parseInt(text2.getText()));
							operators.add(option1.getSelectedItem().toString());
							conditions.add(c1);

						}
						if (cond2.isSelected()) {
							Condition c2 = new Condition(method3.getSelectedItem().toString(),
									sinal3.getSelectedItem().toString(), Integer.parseInt(text3.getText()));
							operators.add(option2.getSelectedItem().toString());
							conditions.add(c2);
						}

						Rule rule = new Rule(nome.getText().toString(), conditions, operators);

						boolean exists = false;
						for (int i = 0; i != mm.getRuleArray().size(); i++) {
							Rule r = mm.getRuleArray().get(i);
							if (r.getRuleName().equals(rule.getRuleName())) {
								mm.getRuleArray().set(i, rule);
								exists = true;
							}
						}
						if (!exists) {
							mm.addRuleToArray(rule);
							ruleSelect.addItem(rule.getRuleName());
						}

						System.out.println((String) nome.getText() + method1.getSelectedItem() + sinal1.getSelectedItem()
								+ text1.getText() + option1.getSelectedItem() + method2.getSelectedItem()
								+ sinal2.getSelectedItem() + text2.getText());

						resetJPanel(jp3);
						resetJPanel(jp5);
						resetJPanel(jp6);
					}
				}
			});
		}

		/**
		 * Adds apply button to secondary frame. When selected it applies the selected rule to a given excel file
		 *
		 */
		public void addApplyButton() {
			button3.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent evt) {
					String command = evt.getActionCommand();
					System.out.println(command);
					l.setText("the user cancelled the operation");

					if (command.equals("APPLY RULE")) {
						try {
							codeSmells = mm.applyRule(mm.getRuleNamed((String) ruleSelect.getSelectedItem()), excel);
							smells.removeAll();
							smells.update(codeSmells);
							smells.validate();

							f1.setVisible(true);

						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			});
		}

		/**
		 * Adds Modify button to secondary frame. When selected, it displays the selected Rule information in the GUI so it can be changed
		 * 
		 */
		public void addModifyButton() {
			button4.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent evt) {
					String command = evt.getActionCommand();
					System.out.println(command);
					l.setText("the user cancelled the operation");

					if (command.equals("MODIFY RULE")) {

						Rule r = mm.getRuleNamed((String) ruleSelect.getSelectedItem());
						nome.setText((String) ruleSelect.getSelectedItem());
						method1.setSelectedItem(r.getCondition(0).getMetric());
						sinal1.setSelectedItem(r.getCondition(0).getSignal());
						text1.setText((String) r.getCondition(0).getLimit());
						if (r.getNumberOfConditions() >= 2) {
							cond1.setSelected(true);
							option1.setSelectedItem(r.getOperator(0));
							method2.setSelectedItem(r.getCondition(1).getMetric());
							sinal2.setSelectedItem(r.getCondition(1).getSignal());
							text2.setText((String) r.getCondition(1).getLimit());
						}
						if (r.getNumberOfConditions() >= 3) {
							cond2.setSelected(true);
							option2.setSelectedItem(r.getOperator(1));
							method3.setSelectedItem(r.getCondition(2).getMetric());
							sinal3.setSelectedItem(r.getCondition(2).getSignal());
							text3.setText((String) r.getCondition(2).getLimit());
						}
					}
				}
			});
		}

		/**
		 * Adds delete button to secondary frame. When selected it deletes the selected rule
		 */
		public void addDeleteButton() {
			button5.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent evt) {
					String command = evt.getActionCommand();
					System.out.println(command);
					l.setText("the user cancelled the operation");

					if (command.equals("DELETE RULE")) {
						for (Rule r : mm.getRuleArray()) {
							if (r.getRuleName().equals((String) ruleSelect.getSelectedItem())) {
								mm.removeRule(r);
								ruleSelect.removeItem(r.getRuleName());
								break;
							}
						}
					}
				}
			});
		}


		/**
		 * This method resets and empties a given JPanel components
		 * @param jp
		 */
		public void resetJPanel(JPanel jp) {
			for (Component c : jp.getComponents()) {

				if (c instanceof JTextField) {
					JTextField tf = (JTextField) c;
					tf.setText("");
				} else if (c instanceof JComboBox) {
					@SuppressWarnings("unchecked")
					JComboBox<String> cb = (JComboBox<String>) c;
					cb.setSelectedIndex(0);
				} else if (c instanceof JCheckBox) {
					JCheckBox cb = (JCheckBox) c;
					cb.setSelected(false);
				}
			}
		}
		
		/**
		 * Adds a window listener that saves all the active rules into a text file when the user closes the GUI
		 */
		public void addExitAction() {
			f.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent ev) {
					try {
						FileWriter f = new FileWriter("Rules.txt");
						System.out.println("Saving rules...");
						for (Rule r : mm.getRuleArray()) {
							f.append(r.toString() + "\n");
							System.out.println(r.toString() + r.getNumberOfConditions());
						}
						f.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		}

		/**
		 * Generates a histogram with the quality of the Code Smells detected. Compares the Code Smells detected by this program with the ones
		 * detected by a professional at Code_Smells.xlsx, and calculates the true positive, true negative, false positivem and false negative
		 * @param rule
		 * @throws IOException
		 */
		public void generateCodeSmellsQuality(Rule rule) throws IOException {
			int tp = 0, tn = 0, fp = 0, fn = 0, nf = 0; 
			boolean b = false;
			for (String s : codeSmells) { 		
				String key;
				if (rule.isClassRule()) {
					key = s;
					if (isgod.containsKey(key)) {
						b = isgod.get(key) == true;
						isgod.remove(key);
					} else {
						nf++;
					}
				} else {
					key = s.split(" ")[1];
					if (islong.containsKey(key)) {
						b = islong.get(key) == true;
						islong.remove(key);
					} else {
						nf++;
					}
					if (b) {
						tp++;
					} else {
						fp++; 
					}

				}
			}
			for (Map.Entry<String, Boolean> cursor : islong.entrySet()) {
				b = cursor.getValue() == false;
				if (b) {
					tn++;
				} else {
					fn++;
				}
			}

			System.out.println(tp + " " + tn + " " + fp + " " + fn + " " + nf);
			graph.createGraph(tp, tn, fp, fn, nf);

		}

		/**
		 * Adds the Check Smells button that when selected activates the method that generates the histogram
		 */
		public void addCheckCodeSmells() {

			button6.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent evt) {
					String command = evt.getActionCommand();
					System.out.println(command);
					l.setText("the user cancelled the operation");

					if (command.equals("CHECK SMELLS")) {
						try {
							generateCodeSmellsQuality(mm.getRuleNamed((String) ruleSelect.getSelectedItem()));
							graph.setVisible(true);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			});

		}

		/**
		 * Reads the Code Smells from the Excel file Code_Smells.xlsx into HashMaps
		 * @throws IOException when the Code_Smells.xlsx file is not found
		 */
		public void readCodeSmellsExcel() throws IOException {
			InputStream is = this.getClass().getResourceAsStream("/Excel/Code_Smells.xlsx");
			Workbook w = new XSSFWorkbook(is);
			org.apache.poi.ss.usermodel.Sheet sheet = w.getSheetAt(0);
			Iterator<Row> it = sheet.iterator();

			it.next();
			while (it.hasNext()) {
				Row row = it.next();
				// TODO
				Cell meth = row.getCell(3);
				Cell clas = row.getCell(2);
				Cell cell = row.getCell(7);
				Cell cell1 = row.getCell(10);
				if (cell.getCellType().equals(CellType.BOOLEAN) && cell1.getCellType().equals(CellType.BOOLEAN)) {
					islong.put(meth.getStringCellValue(), cell1.getBooleanCellValue());
					isgod.put(clas.getStringCellValue(), cell.getBooleanCellValue());
				}
			}

			for (Map.Entry<String, Boolean> cursor : islong.entrySet()) {
			}
			w.close();
		}

		/**
		 * The main that runs the program
		 */
		public static void main(String[] args) throws FileNotFoundException, Exception {
			GUI gui = new GUI();
			gui.f.setVisible(true);
		}
	}