import java.io.File;
import java.util.ArrayList;

import com.jasml.compiler.ParsingException;
import com.jasml.compiler.Scanner;

public class Metricas_Metodos {

	public static void LOC_method(String path) throws Exception {
		File file = new File("c://Users//tiago//OneDrive - ISCTE-IUL//Ambiente de Trabalho//IGE//ES//jasmlfiles//src//com//jasml//compiler//GrammerException.java");
		Scanner sc = new Scanner(file);
		int i=0,j=0;
		int abre=0,fecha=0;
		boolean name = false;
		ArrayList<String> method_name = new ArrayList<>();
		ArrayList<Integer> method_loc = new ArrayList<>();

		while (true) {
			try {
				sc.nextToken();
			} catch (ParsingException e) {

				return;
			}
			if (sc.tokenType() == sc.AccessFlag) {
				i = sc.getLineNumberStart();
				
				
				System.out.println(j-i);
				i=0;j=0;
//				if (sc. == 23 && !name) {
//					System.out.print(sc.token());
//					method_name.add(sc.token());
//				}

			}

			System.out.print(sc.tokenType() + " , " + sc.token()  + " , "
					+ sc.getColumnNumberStart() + " , " + sc.getLineNumberStart() + '\n');
			if (sc.tokenType() == sc.EOF) {
				break;
			}
		}

	}
public static void main(String[] s) throws Exception {
		
		//InputStream in = new FileInputStream(
	//		      new File("/src/com/jasml/compiler/GrammerException.java"));
		
		Metricas_Metodos mm = new Metricas_Metodos();
		mm.LOC_method("abc");
	}

}
