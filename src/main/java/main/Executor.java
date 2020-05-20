package main;
import javax.swing.JOptionPane;

import view.TelaExtracao;

public class Executor {

	public static void main(String[] args) throws Exception {
		
		try {
			TelaExtracao tela = new TelaExtracao();
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "[Erro] contate o desenvolvedor: " + e.getMessage());
		}
		
	}
	
}
