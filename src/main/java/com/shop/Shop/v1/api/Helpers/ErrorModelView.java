package com.shop.Shop.v1.api.Helpers;

public class ErrorModelView {
    public String CodigoDeErro;
    public String DescriçãoDoErro;
    public String Codigo;
    public ErrorModelView(){}
    public ErrorModelView(String codigoDeErro, String descriçãoDoErro, String codigo) {
        CodigoDeErro = codigoDeErro;
        DescriçãoDoErro = descriçãoDoErro;
        Codigo = codigo;
    }
    
}
