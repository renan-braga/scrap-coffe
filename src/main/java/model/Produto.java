package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Produto {
	
	private String titulo = "";
	private String subtitulo = "";
	private String versao = "";
	private String subcategorias = "";
	private String categoria = "";
	private String descricao = "";
	private String updated = "";
	private String developed = "";
	private String status = "";
	private String normalPrice = "";
	private String linkImagem = "";
	private String linkDemo = "";
	private String linkProduto = "";
	private String pagina = "";
	
	public Produto() {
		
		
		
	}
	
	public static List<String> retornaCabecalhos() {
		List<String> list = new ArrayList<String>();
		
		list.addAll(Arrays.asList(
				"Título",
				"Subtítulo",
				"Versão",
				"Subcategorias",
				"Categoria",
				"Descrição",
				"Updated",
				"Developed by",
				"Status",
				"Normal Price",
				"Link Imagem",
				"Link Demo",
				"Link Produto",
				"Página"
				));
		
		return list;		
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getSubtitulo() {
		return subtitulo;
	}

	public void setSubtitulo(String subtitulo) {
		this.subtitulo = subtitulo;
	}

	public String getVersao() {
		return versao;
	}

	public void setVersao(String versao) {
		this.versao = versao;
	}

	public String getSubcategorias() {
		return subcategorias;
	}

	public void setSubcategorias(String subcategorias) {
		this.subcategorias = subcategorias;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public String getDeveloped() {
		return developed;
	}

	public void setDeveloped(String developed) {
		this.developed = developed;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNormalPrice() {
		return normalPrice;
	}

	public void setNormalPrice(String normalPrice) {
		this.normalPrice = normalPrice;
	}

	public String getLinkImagem() {
		return linkImagem;
	}

	public void setLinkImagem(String linkImagem) {
		this.linkImagem = linkImagem;
	}

	public String getLinkDemo() {
		return linkDemo;
	}

	public void setLinkDemo(String linkDemo) {
		this.linkDemo = linkDemo;
	}

	public String getLinkProduto() {
		return linkProduto;
	}

	public void setLinkProduto(String linkProduto) {
		this.linkProduto = linkProduto;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getPagina() {
		return pagina;
	}

	public void setPagina(String pagina) {
		this.pagina = pagina;
	}

	public List<String> retornaLista() {
		ArrayList<String> lista = new ArrayList<>();
		
		lista.addAll(
				Arrays.asList(
						titulo, subtitulo, versao, subcategorias, categoria, descricao, 
						updated, developed, status, normalPrice, linkImagem, linkDemo, linkProduto, pagina
						)
				);
		return lista;
	}


}
