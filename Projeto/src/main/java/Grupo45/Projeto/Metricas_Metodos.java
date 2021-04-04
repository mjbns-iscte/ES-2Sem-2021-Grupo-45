package Grupo45.Projeto;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

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

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileSystemView;





public class Metricas_Metodos {
	private ArrayList<ArrayList<String>> al = new ArrayList<ArrayList<String>>();
	private ArrayList<String> a = new ArrayList<String>();
	private int nom_class=0;
	private HashMap<String, Integer> map= new HashMap<>();
	private int wmc;
	private int loc_class;
	private JFrame f= new JFrame();
	private JLabel l= new JLabel();
	private JFileChooser j;

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

	public void setupGUI() {
		f = new JFrame("GUI");
		f.setSize(400, 400);                                                       
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JButton button = new JButton("OPEN");
		JPanel p = new JPanel();
		p.add(button);
		l = new JLabel("No folder selected");
		p.add(l);
		f.add(p);
		button.addActionListener(new ActionListener() {			

			@Override
			public void actionPerformed(ActionEvent evt) {
				String command= evt.getActionCommand();
				l.setText("the user cancelled the operation");
				if (command.equals("OPEN")) {
					j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
					int r = j.showOpenDialog(null);
					if (r == JFileChooser.APPROVE_OPTION) {
						l.setText(j.getSelectedFile().getAbsolutePath());
						String s = j.getSelectedFile().getAbsolutePath();
						System.out.println(s);
					}
					else
						l.setText("the user cancelled the operation");
				}
			}

		});
	}
	public static void main(String[] args) throws FileNotFoundException, Exception {
		//     File file = new File("C://jasml//src//com//jasml//compiler//SourceCodeParser.java");
		File file = new File("C:\\Users\\jtfgb\\Downloads\\ES_Projeto Teste\\src\\jasml.java"); 
		// 	File file = new File("C:\\Users\\Amado\\Desktop\\Gosto muito de programar\\src\\com\\jasml\\compiler\\SourceCodeParser.java");
		//	File file = new File("C:\\Users\\migue\\Documents\\Projeto\\src\\com\\jasml\\compiler\\ParsingException.java");
		Metricas_Metodos mm = new Metricas_Metodos(2);
		mm.analyze(file);
		mm.analyzeCyclometicComplexity(file);
		mm.setupGUI();    
		mm.f.setVisible(true);
		mm.f.show();
		for(int i=0;i!=mm.al.get(0).size();i++) {
			System.out.println(mm.al.get(0).get(i));
			String aux= mm.al.get(0).get(i);
			System.out.println(mm.al.get(1).get(i));
			System.out.println(mm.getMap().get(aux));
		}
		System.out.println("A classe tem " +  mm.getWmc() + " de complexidade ciclomática.");
		System.out.println("A classe tem " +  mm.getNom_class() + " metodos.");
		System.out.println("A classe tem " +  mm.getLoc_class() + " linhas de código.");

	}





}


