package com.shop.Shop.v1.api.Services;

import com.shop.Shop.v1.api.Entitys.Produto;
import com.shop.Shop.v1.api.Helpers.ErrorModelView;
import com.shop.Shop.v1.api.Repository.ProdutoRepo;

public class ProdutoService extends IService<Produto> {

    private static ProdutoRepo repo = new ProdutoRepo();

    public static boolean ExisteRegistro(Produto item) {
        try {
            var prod = repo.GetItensByParameter("produto", item.getProduto());
            if (!prod.isEmpty())
                return true;

            prod = repo.GetItensByParameter("id", item.getId());
            if (!prod.isEmpty())
                return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public Object insere(Produto produto) {

        if (ExisteRegistro(produto)) 
            return new ErrorModelView("400","Deu Ruim Parceiro","O Objeto Já Existe");
         else 
            return new ProdutoRepo().Insert(produto);

    }

}
