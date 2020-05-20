package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GerenciadorArquivoExcel {

	private Sheet sheet;
	private Workbook workbook;
	private String path;


	public GerenciadorArquivoExcel(String pathTmp) throws FileNotFoundException, IOException, InvalidFormatException {

		try {
			path = pathTmp;
			workbook = new XSSFWorkbook();
			sheet = workbook.createSheet();
			workbook.write(new FileOutputStream(new File(path)));

		}catch(Exception e) {
			e.printStackTrace();
		}

	}

	public void criarCabecalho(List<String> palavras) {
		int coluna = 0;
		sheet.createRow(0);
		for(String nome : palavras) {
			sheet.getRow(0).createCell(coluna).setCellValue(nome);;
			coluna++;
		}
	}

	public int retornaNumeroTotalLinhas() {
		return sheet.getLastRowNum();
	}

	public String retornaDadoLinhaColuna(int linha, int coluna) {
		if(sheet.getRow(linha) != null
				&& sheet.getRow(linha).getCell(coluna) != null
				&& sheet.getRow(linha).getCell(coluna).getStringCellValue() != null) {

			return sheet.getRow(linha).getCell(coluna).getStringCellValue();
		}
		return "";
	}

	public void gravaDadoLinhaColuna(int linha, int coluna, String dado) throws IOException {
		sheet.getRow(linha).createCell(coluna).setCellValue(dado);
	}

	public void salvaPlanilha() throws FileNotFoundException, IOException {
		workbook.write(new FileOutputStream(path));
	}

	public Sheet getSheet() {
		return sheet;
	}

	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}

	public void registraObjeto(List<String> listaDados) {
		int novaLinha = sheet.getLastRowNum()+1;
		sheet.createRow(novaLinha);
		
		for(String dado : listaDados) {
			sheet.getRow(novaLinha).createCell(listaDados.indexOf(dado)).setCellValue(dado);
		}
		
	}

}
