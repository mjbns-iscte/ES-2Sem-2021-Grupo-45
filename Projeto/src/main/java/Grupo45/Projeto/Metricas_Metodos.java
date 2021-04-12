package Grupo45.Projeto;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.traversal.NodeIterator;

import com.github.javaparser.ParseException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.ComboBoxModel;
import javax.swing.JButton;
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
	private JFrame f= new JFrame();
	private JFrame f1= new JFrame();
	private JLabel l= new JLabel();
	private JLabel l1= new JLabel();
	private JFileChooser j;
	private String[] options= new String[]{"LOC_method","Cyclo_method","NOM_class","WMC_class"};
	private String[] metodos= new String[]{"AND","OR"};
	private JComboBox jcb = new JComboBox(metodos);
    private JComboBox jcb1 = new JComboBox(metodos);
    private JComboBox jcb2 = new JComboBox(options);
    private JComboBox jcb3 = new JComboBox(metodos);
    private JComboBox jcb4 = new JComboBox(metodos);
    private JComboBox jcb5 = new JComboBox(options);
    private JTextField jf= new JTextField("Number");
    private JTextField jf1= new JTextField("Number");
    private JTextField jf2= new JTextField("Number");
    private JTextField jf3= new JTextField("Number");
    JPanel jp= new JPanel();
    JPanel jp1= new JPanel();
    JPanel jp2= new JPanel();
    JPanel jp3= new JPanel();
    JPanel jp4= new JPanel();
 
	private ArrayList<File> ficheiros= new ArrayList<>();

	public Metricas_Metodos(int j) {
		super();
		for(int i=0;i!=j;i++)
			al.add(new ArrayList<String>());


	}

	public void analyze(File file) throws FileNotFoundException {
		nom_class=0;
		loc_class = 0;
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

	}
	

	public void analyzeCyclometicComplexity (File file) throws FileNotFoundException {
		InputStream is = new FileInputStream(file);
		map = new Cyclo_method().cyclo_method(is);
		wmc = 0;
		for(int i = 0; i < getAl().get(0).size();i++) {
			String nomes = getAl().get(0).get(i);		
			int aux =getMap().get(nomes);
			wmc = wmc + aux;

		}
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


	public ArrayList<ArrayList<String>> getAl() {
		return al;
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
			String[] columnNames = {"MethodID","package","class","method","NOM_class","LOC_class","WMC_class","is_God_Class","LOC_method","CYCLO_method","is_Long_Method"};
	       
			f = new JFrame("GUI");
	        f.setSize(1000, 750);
	        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        f.setLayout(new BorderLayout());
	        jp1.setLayout(new BorderLayout());
	        f.add(jp, BorderLayout.NORTH);
	        JButton button = new JButton("OPEN");
	        jp.setLayout(new BorderLayout());
	        jp.add(button, BorderLayout.NORTH);
	        l = new JLabel("No folder selected");
	        jp.add(l);
//	        l1 = new JLabel("Is_GOD_Class Rule");
//	        jp1.add(l1);
	        
	        File file = new File("teste_metricas.xlsx");
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

	        for(int x = 0; x!= sheet.getLastRowNum(); x++) {
	        	for (int y = 0; y!= sheet.getRow(0).getLastCellNum(); y++) {
	        		System.out.println(matriz[x][y]);
	        	}
	        }

	        JTable table = new JTable(matriz, columnNames);
	        jp.add(table, BorderLayout.SOUTH);
	        jp.add(new JScrollPane(table));
	        is.close();
	        w.close();

	        button.addActionListener(new ActionListener() {
	        	@Override
	        	public void actionPerformed(ActionEvent evt) {
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
	        				System.out.println(path);
	        				f1 = new JFrame("Rules");
	        				f1.setSize(500, 375);
	        				f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        				f1.setLayout(new BorderLayout());
	        				f1.add(jp1, BorderLayout.CENTER);
	        				jp1.add(jp2, BorderLayout.NORTH);
	        				jp1.add(jp3, BorderLayout.CENTER);
	        				f1.add(jp4, BorderLayout.SOUTH);
	        				JButton button1 = new JButton("RUN");
	        				jp4.add(button1);
	        				jp2.add(jcb);
	        				jp2.add(jf);
	        				jp2.add(jcb2);
	        				jp2.add(jcb1);
	        				jp2.add(jf1);
	        				jp3.add(jcb3);
	        				jp3.add(jf2);
	        				jp3.add(jcb5);
	        				jp3.add(jcb4);
	        				jp3.add(jf3);
	        				//l1 = new JLabel("Is_GOD_Class Rule");
	        				//jp1.add(l1);
	        				//jp1.add(l1, BorderLayout.SOUTH);
	        				f1.setVisible(true);
	        				f1.show();


	        			}
	        			else
	        				l.setText("the user cancelled the operation");
	        		}
	        	}

	        });
		}

	public static void main(String[] args) throws FileNotFoundException, Exception {
		//File file = new File("C://jasml//src//com//jasml//compiler//SourceCodeParser.java");
		//File file = new File("C:\\Users\\jtfgb\\Downloads\\ES_Projeto Teste\\src\\jasml.java"); 
		//File file = new File("C:\\Users\\Amado\\Desktop\\Gosto muito de programar\\src\\com\\jasml\\compiler\\SourceCodeParser.java");
//		File file = new File("/Users/guilhenriques/Desktop/Faculdade/ES_Projeto Teste/src/com/jasml/compiler/ParsingException.java");
		//File file = new File("C:\\Users\\Francisco\\git\\ES-2Sem-2021-Grupo-45\\Projeto\\src\\main\\java\\src");

		Metricas_Metodos mm = new Metricas_Metodos(2);
		//mm.analyze(file);
		//mm.analyzeCyclometicComplexity(file);
		//Excel e = new Excel();
		//e.setupExcel("teste");
		mm.setupGUI();    
		mm.f.setVisible(true);
		mm.f.show();
//		for(int i=0;i!=mm.al.get(0).size();i++) {
//			System.out.println(mm.al.get(0).get(i));
//			String aux= mm.al.get(0).get(i);
//			System.out.println(mm.al.get(1).get(i));
//			System.out.println(mm.getMap().get(aux));
//		}
//		System.out.println("A classe tem " +  mm.getWmc() + " de complexidade ciclomática.");
//		System.out.println("A classe tem " +  mm.getNom_class() + " metodos.");
//		System.out.println("A classe tem " +  mm.getLoc_class() + " linhas de código.");
//
	}






}


