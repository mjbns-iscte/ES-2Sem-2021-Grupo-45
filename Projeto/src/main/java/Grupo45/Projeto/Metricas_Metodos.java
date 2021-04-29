package Grupo45.Projeto;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.github.javaparser.ast.PackageDeclaration;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.traversal.NodeIterator;

import com.github.javaparser.ParseException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.InstanceOfExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.MethodReferenceExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;

import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
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





public class Metricas_Metodos {

	private ArrayList<ArrayList<String>> al = new ArrayList<ArrayList<String>>();
	private ArrayList<String> a = new ArrayList<String>();
	private int nom_class=0;
	private HashMap<String, Integer> map= new HashMap<>();
	private int wmc;
	private int loc_class;
	private String pack;
	private JFrame f= new JFrame();
	private JFrame f1= new JFrame();
	private JLabel l= new JLabel();
	private JLabel l1= new JLabel();
	private JFileChooser j;
	private String[] metodos= new String[]{"LOC_method","Cyclo_method","NOM_class","WMC_class"};
	private String[] options= new String[]{"AND","OR"};
	private String[] sinais = new String[]{">","<",">=","<=","="};

	private ArrayList<Rule> rules = new ArrayList<>();
	private ArrayList<String> operators = new ArrayList<>();

	private JComboBox method1 = new JComboBox(metodos);
	private JComboBox method2 = new JComboBox(metodos);
	private JComboBox method3 = new JComboBox(metodos);
	private JComboBox method4 = new JComboBox(metodos); 

	private JComboBox option2 = new JComboBox(options);
	private JComboBox option1 = new JComboBox(options);

	private JComboBox sinal1 = new JComboBox(sinais);
	private JComboBox sinal2 = new JComboBox(sinais);
	private JComboBox sinal3 = new JComboBox(sinais);
	private JComboBox sinal4 = new JComboBox(sinais);

	private JComboBox<String> ruleSelect = new JComboBox();

	private JTextField nome= new JTextField(10);
	private JTextField text1= new JTextField(2);
	private JTextField text2= new JTextField(2);
	private JTextField text3= new JTextField(2);
	private JTextField text4= new JTextField(2);

	private JPanel jp= new JPanel();
	private JPanel jp1= new JPanel();
	private JPanel jp2= new JPanel();
	private JPanel jp3= new JPanel();
	private JPanel jp4= new JPanel();
	private JPanel jp5= new JPanel();
	private JPanel jp6= new JPanel();

	private JButton button1;
	private JButton button3;

	JCheckBox cond1 = new JCheckBox("use this condition",false);
	JCheckBox cond2 = new JCheckBox("use this condition",false);

	private ArrayList<File> ficheiros= new ArrayList<>();

	private Excel excel;
	public Metricas_Metodos(int j) {
		super();
		for(int i=0;i!=j;i++)
			al.add(new ArrayList<String>());


	}

	public void analyze(File file) throws FileNotFoundException {
		nom_class=0;
		loc_class = 0;
		for(int i=0;i!=al.size();i++)
			al.get(i).clear();;

			InputStream is = new FileInputStream(file);
			CompilationUnit cu = StaticJavaParser.parse(is);
			new LOC_method().visit(cu, al);
			if(!al.isEmpty()) {
				nom_class= al.get(0).size(); 
			}
			new VoidVisitorAdapter<Object>() {
				public void visit (ClassOrInterfaceDeclaration n, Object t) {
					super.visit( n, loc_class);
					loc_class = (n.getEnd().get().line - n.getBegin().get().line + 1);
				}
			}.visit(cu, null);
			new VoidVisitorAdapter<Object>() {
				public void visit (PackageDeclaration n, Object t) {
					super.visit( n, pack);
					pack = n.getNameAsString();
				}
			}.visit(cu, null);
	}


	public void analyzeCyclometicComplexity (File file) throws FileNotFoundException {
		InputStream is = new FileInputStream(file);
		map.clear();
		map = new Cyclo_method().cyclo_method(is);
		wmc = 0;

		for(int i = 0; i < getMainArray().get(0).size();i++) {
			String nomes = getMainArray().get(0).get(i);	
			int aux =getMap().get(nomes);
			wmc = wmc + aux;

		}
	}

	public void updateRules() {
		JLabel jl = new JLabel();
		if(rules.size()!=0) {
			for(int i=0;i!=rules.size();i++) {
				jl.setText(" '" + rules.get(i).toString() +"'");
				jp2.add(jl);
			}
		}else System.out.println("No rules");
		jp2.updateUI();
	}

	public int getWmc() {
		return wmc;
	}


	public int getNom_class() {
		return nom_class;
	}


	public int getLoc_class() {
		return loc_class;
	}


	public HashMap<String, Integer> getMap() {
		return map;
	} 


	public ArrayList<ArrayList<String>> getMainArray() {
		return al;
	}

	public String getPackage() {
		return pack;
	}

	public ArrayList<File> search(File main){
		File[] files = main.listFiles();
		for (File file : files) {
			if(file.isDirectory() ) {
				this.search(file);
			}
			if(file.isFile()&&file.getAbsolutePath().endsWith(".java")) {
				ficheiros.add(file);
			}

		}
		return ficheiros;
	}

	public void setupGUI() throws IOException {
		String[] columnNames = {"MethodID","package","class","method","NOM_class","LOC_class","WMC_class","LOC_method","CYCLO_method"};
		f = new JFrame("GUI");
		f.setSize(1000, 750);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(new BorderLayout());
		jp1.setLayout(new BoxLayout(jp1,BoxLayout.PAGE_AXIS));
		f.add(jp, BorderLayout.NORTH);
		JButton button = new JButton("OPEN");
		JButton button2 = new JButton("RULES");
		jp.setLayout(new BorderLayout());
		jp.add(button, BorderLayout.NORTH);
		f.add(button2,BorderLayout.SOUTH);
		// l = new JLabel("No folder selected");



		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt)  {
				String command= evt.getActionCommand();
				l.setText("the user cancelled the operation");
				if (command.equals("OPEN")) {
					j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
					j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					int r = j.showOpenDialog(null);
					if (r == JFileChooser.APPROVE_OPTION) {
						l.setText(j.getSelectedFile().getAbsolutePath());
						File mainDir = j.getSelectedFile();
						String path = j.getSelectedFile().getAbsolutePath();
						ArrayList<File> files = search(mainDir);
						Path pathToAFile = Paths.get(path);
						System.out.println("Path :" + path);

						excel = new Excel();
						String pathExcel=path + "\\" + pathToAFile.getFileName().toString() + "_metricas.xlsx";
						try {
							System.out.println(pathExcel);
							excel.setupExcel(pathExcel);

							metricsToExcel(files, excel);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} 



						/////////////////////////        				/////////////////////////
						try {


							File file = new File(pathExcel);
							System.out.println(file.toString());
							FileInputStream is = new FileInputStream(file);
							Workbook w = new XSSFWorkbook(is);
							org.apache.poi.ss.usermodel.Sheet sheet = w.getSheetAt(0);
							Iterator<Row> it = sheet.iterator();

							String[][] matriz = new String[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
							Row row;
							it.next();
							for(int i = 0; it.hasNext();i++) {
								row = it.next();
								Iterator<Cell> itcell= row.cellIterator();

								for(int j = 0; itcell.hasNext(); j++ ) {
									Cell cell = itcell.next();
									String aux;
									if(cell.toString().isEmpty()) {
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
						}catch(IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});

		/////////////////////////        				/////////////////////////  
		button1 = new JButton("ADD RULE");
		button3 = new JButton("APPLY RULE");

		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt2)  {

				String command= evt2.getActionCommand();
				if (command.equals("RULES")) {
					f1 = new JFrame("Rules");
					f1.setSize(1000, 375);
					f1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					f1.setLayout(new BorderLayout());
					f1.add(jp1, BorderLayout.CENTER);
					jp1.add(jp2, BorderLayout.NORTH);
					jp1.add(jp3, BorderLayout.CENTER);
					f1.add(jp4, BorderLayout.SOUTH);
					jp1.add(jp5, BorderLayout.CENTER);
					jp1.add(jp6, BorderLayout.CENTER);
					jp4.add(button1);
					jp4.add(ruleSelect);
					for(Rule r:rules)ruleSelect.addItem(r.toString());
					jp4.add(button3);
					jp3.add(new JLabel("Rule Name:"));
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
					//l1 = new JLabel("Is_GOD_Class Rule");
					//jp1.add(l1);
					//jp1.add(l1, BorderLayout.SOUTH);
					f1.setVisible(true);
					f1.show();
				}
				else
					l.setText("the user cancelled the operation");
			}
		});
		createTestRule(); //<------------------------------------

		button1.addActionListener(new ActionListener() { 

			public void actionPerformed(ActionEvent evt3
					)  {
				String command3= evt3.getActionCommand();
				System.out.println(command3);
				l.setText("the user cancelled the operation");

				ArrayList<Condition> conditions = new ArrayList<>();

				if (command3.equals("ADD RULE")) {

					if(nome.getText()!="") {
						Condition c = new Condition(method1.getSelectedItem().toString(),sinal1.getSelectedItem().toString(),Integer.parseInt(text1.getText()));
						conditions.add(c);
					}
					if(cond1.isSelected()) {
						Condition c1 = new Condition(method2.getSelectedItem().toString(),sinal2.getSelectedItem().toString(),Integer.parseInt(text2.getText()));
						operators.add(option1.getSelectedItem().toString());
						conditions.add(c1);

					}
					if(cond2.isSelected()) {
						Condition c2 = new Condition(method3.getSelectedItem().toString(),sinal3.getSelectedItem().toString(),Integer.parseInt(text3.getText()));
						operators.add(option2.getSelectedItem().toString());
						conditions.add(c2);

					}

					Rule rule = new Rule(nome.getText().toString(),conditions,operators);
					rules.add(rule);
					ruleSelect.addItem(rule.toString());
					System.out.println((String)nome.getText() + method1.getSelectedItem() + sinal1.getSelectedItem() + text1.getText() + option1.getSelectedItem() + method2.getSelectedItem() + sinal2.getSelectedItem() + text2.getText());
					//        		        			System.out.println((String)method3.getSelectedItem() + sinal3.getSelectedItem() + text3.getText() + option2.getSelectedItem() + method4.getSelectedItem() + sinal4.getSelectedItem() + text4.getText());

					try {
						FileWriter f = new FileWriter("Regras.txt");
						f.write((String)nome.getText() + method1.getSelectedItem() + sinal1.getSelectedItem() + text1.getText() + option1.getSelectedItem() + method2.getSelectedItem() + sinal2.getSelectedItem() + text2.getText() + 
								option2.getSelectedItem() + method3.getSelectedItem() + sinal3.getSelectedItem() + text3.getText());
						f.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					updateRules();

				}
			}
		}
				);button3.addActionListener(new ActionListener() { 

					public void actionPerformed(ActionEvent evt)  {
						String command= evt.getActionCommand();
						System.out.println(command);
						l.setText("the user cancelled the operation");

						ArrayList<Condition> conditions = new ArrayList<>();
						
						if (command.equals("APPLY RULE")) {
							
							try {
								LinkedHashSet<String> codeSmells = applyRule(getRuleNamed((String)ruleSelect.getSelectedItem()),excel);
								JScrollPane jscp = new JScrollPane(new TestPane(codeSmells));																								
								jp1.add(jscp);
								jp1.show();
								f1.setVisible(true);
								
							} catch (IOException e) {						
								e.printStackTrace();
							}

						}
					}
				});
	}

	public LinkedHashSet<String> applyRule(Rule rule, Excel e) throws IOException{
		String name = e.getG_path();
		FileInputStream file = new FileInputStream(name);		
		Workbook w = new XSSFWorkbook(file);
		org.apache.poi.ss.usermodel.Sheet sheet = w.getSheetAt(0);
		ArrayList<Integer> values = new ArrayList<>();
		LinkedHashSet<String> codeSmells = new LinkedHashSet<>();
		Iterator<Row> it = sheet.iterator();
		it.next();
		while(it.hasNext()) {
			values.clear();
			Row row = it.next();
			for(int j=0;j!=rule.getNumberOfConditions();j++) {
				System.out.println(rule.getCondition(j).getMetric());
				System.out.println(e.getMetricColumn(rule.getCondition(j).getMetric()));
				values.add((int)Double.parseDouble(row.getCell(e.getMetricColumn(rule.getCondition(j).getMetric())).toString()));
			}
			if(rule.ruleEvaluate(values)) {
				if(rule.isClassRule()) {
					codeSmells.add("Code Smell at Class " + row.getCell(2).toString());
				}else	codeSmells.add("Code Smell at Method " + row.getCell(0).toString());
			}
		}
		w.close();
		return codeSmells;


	}



	public Excel metricsToExcel(ArrayList<File> files, Excel e) throws IOException {
		String name = e.getG_path();
		//			org.apache.poi.ss.usermodel.Sheet sheet=e.getSheet();
		FileInputStream file = new FileInputStream(name);

		Workbook w = new XSSFWorkbook(file);
		org.apache.poi.ss.usermodel.Sheet sheet = w.getSheetAt(0);

		int x=1;
		for(File f: files) {
			analyze(f);

			analyzeCyclometicComplexity(f);
			for(int i=0;i!=getMainArray().get(0).size();i++) {
				Row row =sheet.createRow(x);

				row.createCell(0).setCellValue(x);
				row.createCell(1).setCellValue(getPackage());
				row.createCell(2).setCellValue(f.getName());
				row.createCell(3).setCellValue(getMainArray().get(0).get(i));
				row.createCell(4).setCellValue(getNom_class());
				row.createCell(5).setCellValue(getLoc_class());
				row.createCell(6).setCellValue(getWmc());
				row.createCell(8).setCellValue(Integer.parseInt(getMainArray().get(1).get(i)));
				row.createCell(9).setCellValue(getMap().get(getMainArray().get(0).get(i)));



				x++;

			}

		}
		System.out.println(getMainArray().get(1).size());

		FileOutputStream fileOut = new FileOutputStream(new File(name));
		w.write(fileOut);
		fileOut.close();

		return e;
	}

	public void createTestRule() {
		Condition a = new Condition("LOC_method",">",10);
		Condition a1 = new Condition("CYCLO_method",">",5);
		//			Condition a2 = new Condition("NOM_Class",">",11);
		ArrayList<Condition> cs = new ArrayList<>();
		cs.add(a);
		cs.add(a1);
		//			cs.add(a2);
		ArrayList<String> ops = new ArrayList<>();
		ops.add("AND");
		ops.add("OR");
		Rule rule = new Rule("is_Long_Method",cs,ops);
		rules.add(rule);

		jp2.add(new JLabel("Rules: "));
		updateRules();
	}
	public Rule getTestRule(int i) {			
		return rules.get(i);		
	}

	public Rule getRuleNamed(String s) {
		Rule out= null;
		for(int i=0;i!=rules.size();i++) {
			if(rules.get(i).getRuleName().equals(s))
				out=rules.get(i);
		}
		if(out.equals(null)) throw new IllegalStateException("There is no rule with given name");
		return out;
	}

	public class TestPane extends JPanel {

		public TestPane(LinkedHashSet<String> codeSmells) {
			setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridwidth = GridBagConstraints.REMAINDER;
			for(String s:codeSmells) {       
				JLabel label = new JLabel(s);              
				label.setPreferredSize(new Dimension(280, 50));
				add(label, gbc);               
			}

		}

	}

	public static void main(String[] args) throws FileNotFoundException, Exception {
		Metricas_Metodos mm = new Metricas_Metodos(2);
		mm.setupGUI();
		mm.f.setVisible(true);
		//		mm.f.show();
		//		mm.createTestRule();
		//		ArrayList<Integer> values = new ArrayList<>();
		//		values.add(9);
		//		values.add(6);
		//		Excel e = new Excel();
		//		e.setupExcel("C:\\jasml\\src\\src_metricas.xlsx");
		//		mm.applyRule(mm.getTestRule(0), e);

	}






}


