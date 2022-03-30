package com.shop.Shop.v1.api.Entitys;

public class Produto {

	private final String id;
	private final String produto;
	private final EnumCategoria categoria;
	private final long preço;

	public Produto(String id,long preço, String produto, EnumCategoria categoria) {
		this.id= id;
		this.preço = preço;
		this.produto = produto;
		this.categoria = categoria;
	}

	public long getPreço() {
		return preço;
	}

	public String getId() {
		return id;
	}

	public EnumCategoria getCategoria()
	{
		return categoria;
	}

	public String getProduto() {
		return produto;
	}
}