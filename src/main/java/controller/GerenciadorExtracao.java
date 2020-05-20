package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import model.Produto;

public class GerenciadorExtracao {

	private int numeroPaginas;
	private JLabel lblStatus;
	private long linhaAtual = 1;
	private GerenciadorArquivoExcel excel;
	private Elements paginas;
	private Document domHome;
	private int pgAtual;
	private String linkPagina;
	private String seletorPadrao;
	private Document dom;
	private Elements linksProdutos;
	private Produto produto;
	private String linkProduto;
	private Document domProduto;
	private String categoria;
	private String linkPadrao;
	private boolean cancela;
	private DriverExtrator extrator;

	public GerenciadorExtracao(JLabel status) throws Exception{
		cancela = false;
		String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath() +  "extracao.xlsx";
		excel = new GerenciadorArquivoExcel(path);
		excel.criarCabecalho(Produto.retornaCabecalhos());
		excel.salvaPlanilha();
		removeArquivoLog();
		lblStatus = status;
	}


	public void extrairPlugins() throws IOException {
		if(cancela)
			return;		
		extrair("plugins");
	}

	public void extrairScripts() throws IOException {
		if(cancela)
			return;
		extrair("scripts");
	}

	public void extrairTemas() throws IOException {
		if(cancela)
			return;		
		extrair("themes");
	}

	public void extrair(String categoriaEscolhida) throws IOException {
		try {
			categoria = categoriaEscolhida;
			lblStatus.setText("Extraindo número de páginas");
			domHome = Jsoup.connect("https://gpl.coffee/item-category/"+ categoria +"/")
					.timeout(60000000)
					.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")
					.get();
			paginas = domHome.select("li > a.page-numbers");
			numeroPaginas = Integer.parseInt(paginas.get(paginas.size()-2).text());
			pgAtual = 1;
			linkPadrao = "https://gpl.coffee/item-category/"+ categoria +"/page/";

			while(pgAtual <= numeroPaginas) {
				linkPagina = linkPadrao + pgAtual;
				seletorPadrao = "span.ae-element-custom-field-label";
				dom = Jsoup.connect(linkPagina)
						.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")
						.timeout(60000000)
						.get();

				linksProdutos = dom.select("div.fl-rich-text > h3 > a");

				for(Element element : linksProdutos) {
					linkProduto = (element.attr("href"));
					domProduto = Jsoup.connect(linkProduto)
							.timeout(60000000)
							.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")
							.get();

					produto = new Produto();
					System.out.println(isCancela());

					if(isCancela()) {
						JOptionPane.showMessageDialog(null, "Cancelando aplicação");
						lblStatus.setText("Aplicação cancelada durante a extração");
						return;
					}

					try {
						if(true)
							extraiJsoup();
						else
							extraiSelenium();
					}catch(Exception e) {
						informaErroArquivo(e);
					}finally {
						informarLabel(categoriaEscolhida, pgAtual, produto.getTitulo());
						registrarProduto(produto);
					}
				}
				pgAtual++;
				lblStatus.setText("Extração Finalizada com sucesso");
			}
		}catch(Exception e) {
			informaErroArquivo(e);
			e.printStackTrace();
		}
	}


	private void extraiSelenium() throws IOException {
		extrator = new DriverExtrator(false, false);
		WebDriver driver = extrator.getDriver();

		driver.get(linkProduto);
		extrator.waitForLoad();

		produto.setTitulo(driver.findElement(By.xpath("//h1[@class='ae-element-woo-title']")).getText());
		produto.setSubtitulo(driver.findElement(By.xpath("(//div[@class='ae-element-woo-content'])[1]")).getText());
		//		produto.setVersao(driver.findElement(By.xpath("")));
		//		produto.setDescricao(domProduto.select("div.ae-element-woo-content").last().text());
		//		produto.setSubcategorias(extrairSubCategorias(domProduto));
		//		produto.setCategoria(categoria);
		//		produto.setVersao(retornaPeloTexto("Version", domProduto, seletorPadrao));
		//		produto.setUpdated(retornaPeloTexto("Updated", domProduto, seletorPadrao));
		//		produto.setDeveloped(retornaPeloTexto("Developed", domProduto, seletorPadrao));
		//		produto.setNormalPrice(retornaPeloTexto("Normal price", domProduto, seletorPadrao));
		//		produto.setStatus(retornaPeloTexto("Status", domProduto, seletorPadrao));
		//		produto.setLinkImagem(domProduto.select("meta[property='og:image']").attr("content"));
		//		produto.setLinkDemo(domProduto.selectFirst("a.ae-element-custom-field").attr("href"));
		//		produto.setLinkProduto(linkProduto);
		//		produto.setPagina(String.valueOf(pgAtual));
	}


	private void extraiJsoup() {

		produto.setTitulo(domProduto.select("h1.ae-element-woo-title").first().text());
		produto.setSubtitulo(domProduto.select("div.ae-element-woo-content").first().text());
		produto.setVersao(domProduto.select("span.ae-element-custom-field-label + div.ae-element-custom-field").text());
		produto.setDescricao(domProduto.select("div.ae-element-woo-content").last().text());
		produto.setSubcategorias(extrairSubCategorias(domProduto));
		produto.setCategoria(categoria);
		produto.setVersao(retornaPeloTexto("Version", domProduto, seletorPadrao));
		produto.setUpdated(retornaPeloTexto("Updated", domProduto, seletorPadrao));
		produto.setDeveloped(retornaPeloTexto("Developed", domProduto, seletorPadrao));
		produto.setNormalPrice(retornaPeloTexto("Normal price", domProduto, seletorPadrao));
		produto.setStatus(retornaPeloTexto("Status", domProduto, seletorPadrao));
		produto.setLinkImagem(domProduto.select("meta[property='og:image']").attr("content"));
		produto.setLinkDemo(domProduto.selectFirst("a.ae-element-custom-field").attr("href"));
		produto.setLinkProduto(linkProduto);
		produto.setPagina(String.valueOf(pgAtual));
	}


	private void removeArquivoLog() {
		String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath() +  "extracao_erros.txt";
		File file = new File(path);
		if(file.exists()) {
			file.delete();
		}
	}


	private void informaErroArquivo(Exception e) throws IOException {
		String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath() +  "extracao_erros.txt";
		PrintWriter writer = new PrintWriter(new FileWriter(path, true));
		writer.append(System.lineSeparator() + "##### NOVO ERRO #####" + System.lineSeparator());
		writer.append(produto.getLinkProduto() + System.lineSeparator());
		e.printStackTrace(writer);
		writer.close();
	}

	private void informarLabel(String categoriaEscolhida, int pgAtual, String titulo) {
		lblStatus.setText("<html>Extraindo " + categoriaEscolhida
				+ "<br>Página: " + pgAtual + " de " + numeroPaginas
				+ "<br>" + titulo
				+ "<br> Produtos processados: " + linhaAtual + " </html>");

	}


	private void registrarProduto(Produto produto) throws Exception {
		List<String> listaProduto = produto.retornaLista();
		excel.registraObjeto(listaProduto);
		excel.salvaPlanilha();
		linhaAtual++;
	}


	private String retornaPeloTexto(String string, Document domProduto, String selector) {
		Elements elements = domProduto.select(selector);
		int total = elements.size();
		for(int i = 0; i < total; i++) {
			if(elements.get(i).text().contains(string)) {
				return elements.get(i).nextElementSibling().text();
			}
		}
		return "";
	}

	private String extrairSubCategorias(Document dom) {
		String stringCategorias = "";
		Elements subCategorias = dom.select("span.ae-element-woo-tags > a");
		for(Element element : subCategorias) {
			stringCategorias = stringCategorias + element.text() + ", ";
		}
		return stringCategorias;
	}


	public boolean isCancela() {
		return cancela;
	}


	public void setCancela(boolean cancela) {
		this.cancela = cancela;
	}

}
