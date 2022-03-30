package com.shop.Shop.v1.api.Controllers;

import java.util.List;
import com.shop.Shop.v1.api.Entitys.Produto;
import com.shop.Shop.v1.api.Repository.IRepo;
import com.shop.Shop.v1.api.Repository.ProdutoRepo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin()
@RestController
public class ItemController {

    @GetMapping("/getProduto")
    public ResponseEntity<Produto> getProduto(@RequestParam(value = "id", defaultValue = "01") String id) {
        IRepo<Produto> repo = new ProdutoRepo();
        return new ResponseEntity<Produto>(repo.GetItemById(id), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Produto>> getProduto2() {
        return new ResponseEntity<List<Produto>>(new ProdutoRepo().GetItensByParameter("Categoria", "MÓVEL"),HttpStatus.ACCEPTED);
    }

    @GetMapping("/listAll")
    public List<Produto> getProduto12() {
        return new ProdutoRepo().GetAll();
    }

    @PostMapping("/PostProduto")
    public Produto eotaaa(@RequestBody Produto produto) {
        return new ProdutoRepo().Insert(produto);
    }
}
