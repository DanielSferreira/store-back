package com.shop.Shop.v1.api.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.shop.Shop.v1.api.Entitys.EnumCategoria;
import com.shop.Shop.v1.api.Entitys.Produto;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin()
@RestController
@RequestMapping(path = "/api/produtos")
public class ProdutosController {
	List<Produto> produtos = new ArrayList<Produto>();
	public ProdutosController()
	{
		/**
		 * DataBase fake para o front
		 */
		this.produtos.add(new Produto("01",150,"cadeira",EnumCategoria.MÓVEL));
		this.produtos.add(new Produto("02",80,"mesa",EnumCategoria.MÓVEL));
		this.produtos.add(new Produto("03",150,"caneta",EnumCategoria.ESCRITÓRIO));
		this.produtos.add(new Produto("04",150,"lapis",EnumCategoria.ESCRITÓRIO));
		this.produtos.add(new Produto("05",150,"borracha",EnumCategoria.ESCRITÓRIO));
		this.produtos.add(new Produto("06",150,"Quadro",EnumCategoria.CASA));
		this.produtos.add(new Produto("07",150,"Lavadoura de Roupa",EnumCategoria.ELETRO));
		this.produtos.add(new Produto("08",150,"Cesto",EnumCategoria.CASA));
		this.produtos.add(new Produto("09",150,"Vaso",EnumCategoria.CASA));
		this.produtos.add(new Produto("10",150,"Maquina de Lavar",EnumCategoria.ELETRO));
		this.produtos.add(new Produto("11",150,"Televisão",EnumCategoria.ELETRO));
		this.produtos.add(new Produto("12",150,"Kit Teclado e Mouse",EnumCategoria.INFO));
		this.produtos.add(new Produto("13",150,"Notebook",EnumCategoria.INFO));
		this.produtos.add(new Produto("14",150,"PC - Gamer",EnumCategoria.INFO));
	}
	
	@GetMapping("/")
	public List<Produto> Produtos()
	{
		return produtos;
	}
	@GetMapping("/peloNome/")
	public List<Produto> produtoByIDProduto(@RequestParam(name = "categoria") EnumCategoria name){
		return produtos.stream()
			.filter( prod -> prod.getCategoria() == name)
			.collect(Collectors.toList());
	}

}