package com.shop.Shop.v1.api.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shop.Shop.v1.api.Entitys.EnumCategoria;
import com.shop.Shop.v1.api.Entitys.Produto;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeAction;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.AttributeValueUpdate;
import software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.ExecuteStatementRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;

public class ProdutoRepo implements IRepo<Produto> {

    private DynamoDbClient client;
    private String table;

    public void GenerateData(String TableName) {
        client = DynamoDbClient.builder().build();
        table = TableName;
    }

    public ProdutoRepo() {
        GenerateData("produtos");
    }

    @Override
    public Produto CreateTable(Produto objectE) {
        String tableName = "table";
        String key = "id";
        String keyVal = "2147bd61-f11f-4642-92a8-0eb33c8406b3";
        HashMap<String, AttributeValue> keyToGet = new HashMap<String, AttributeValue>();

        keyToGet.put(key, AttributeValue.builder()
                .s(keyVal)
                .build());

        GetItemRequest request = GetItemRequest.builder()
                .key(keyToGet)
                .tableName(tableName)
                .build();

        try {
            Map<String, AttributeValue> returnedItem = client.getItem(request).item();

            Produto produto = new Produto(
                    returnedItem.get("Id").s(),
                    Long.parseLong(returnedItem.get("preço").s()),
                    returnedItem.get("produto").s(),
                    EnumCategoria.valueOf(returnedItem.get("categoria").s()));

            System.out.format("%s tem preço: %s, está cadastrado na categoria: %s", produto.getProduto(),
                    produto.getCategoria(), produto.getCategoria());

            client.close();

            return produto;
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            client.close();
            return null;
        }
    }

    @Override
    public Produto GetItemById(String id) {
        System.out.println(id);
        String key = "id";
        HashMap<String, AttributeValue> keyToGet = new HashMap<String, AttributeValue>();

        keyToGet.put(key, AttributeValue.builder()
                .s(id)
                .build());

        GetItemRequest request = GetItemRequest.builder()
                .key(keyToGet)
                .tableName(table)
                .build();

        try {
            Map<String, AttributeValue> returnedItem = client.getItem(request).item();
            Produto produto;
            try {
                produto = new Produto(
                        returnedItem.get("id").s(),
                        Long.parseLong(returnedItem.get("Preço").s()),
                        returnedItem.get("Produto").s(),
                        EnumCategoria.valueOf(returnedItem.get("Categoria").s()));

            } catch (Exception e) {
                produto = new Produto(
                        "00",
                        0,
                        "Erro",
                        EnumCategoria.CASA);
            } finally {
                client.close();
            }
            return produto;

        } catch (DynamoDbException e) {
            System.out.println("Cai aqui três");
            System.err.println(e.getMessage());
            client.close();
            return null;
        }
    }

    @Override
    public List<Produto> GetItensByParameter(String key, String value) {
        ExecuteStatementRequest request = ExecuteStatementRequest.builder()
                .statement(String.format("SELECT * FROM produtos where %s = '%s' ", key, value))
                .build();
        List<Map<String, AttributeValue>> aoa = client.executeStatement(request).toBuilder().build().items();
        List<Produto> returning = new ArrayList<Produto>();
        for (Map<String, AttributeValue> Item : aoa) {
            returning.add(new Produto(
                    Item.get("id").s(),
                    Long.parseLong(Item.get("Preço").s()),
                    Item.get("Produto").s(),
                    EnumCategoria.valueOf(Item.get("Categoria").s())));

        }
        return returning;
    }

    @Override
    public Produto Insert(Produto objectE) {
        HashMap<String, AttributeValue> itemValue = new HashMap<String, AttributeValue>();
        itemValue.put("id", AttributeValue.builder().s(objectE.getId()).build());
        itemValue.put("Produto", AttributeValue.builder().s(objectE.getProduto()).build());
        itemValue.put("Preço", AttributeValue.builder().s(String.valueOf(objectE.getPreço())).build());
        itemValue.put("Categoria", AttributeValue.builder().s(String.valueOf(objectE.getCategoria())).build());

        PutItemRequest pir = PutItemRequest.builder()
                .tableName(table)
                .item(itemValue)
                .build();
        try {
            client.putItem(pir);
            System.out.println(" produtos was successfully updated");
            client.close();

        } catch (ResourceNotFoundException e) {
            System.err.format("Error: %s ", e.getMessage());
            client.close();
            return null;
        } catch (DynamoDbException e) {
            System.err.format("Error 2: %s ", e.getMessage());
            client.close();
            return null;
        }
        return objectE;
    }

    @Override
    public Produto Put(String key, String keyVal, String name, String updateVal) {

        HashMap<String, AttributeValue> itemKey = new HashMap<String, AttributeValue>();

        itemKey.put(key, AttributeValue.builder().s(keyVal).build());

        HashMap<String, AttributeValueUpdate> updatedValues = new HashMap<String, AttributeValueUpdate>();

        // Update the column specified by name with updatedVal
        updatedValues.put(name, AttributeValueUpdate.builder()
                .value(AttributeValue.builder().s(updateVal).build())
                .action(AttributeAction.PUT)
                .build());

        UpdateItemRequest request = UpdateItemRequest.builder()
                .tableName(table)
                .key(itemKey)
                .attributeUpdates(updatedValues)
                .build();

        try {
            client.updateItem(request);
        } catch (ResourceNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return new Produto("03", 2, table, EnumCategoria.CASA);
    }

    @Override
    public boolean DeleteById(String Id) {

        HashMap<String, AttributeValue> keyToGet = new HashMap<String, AttributeValue>();

        keyToGet.put("id", AttributeValue.builder()
                .s(Id)
                .build());

        DeleteItemRequest deleteReq = DeleteItemRequest.builder()
                .tableName(table)
                .key(keyToGet)
                .build();

        try {
            client.deleteItem(deleteReq);
            return true;
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            return false;
        }

    }

    @Override
    public List<Produto> GetAll() {
        ExecuteStatementRequest request = ExecuteStatementRequest.builder()
                .statement("SELECT * FROM produtos")
                .build();
        List<Map<String, AttributeValue>> aoa = client.executeStatement(request).toBuilder().build().items();
        List<Produto> returning = new ArrayList<Produto>();
        for (Map<String, AttributeValue> Item : aoa) {
            returning.add(new Produto(
                    Item.get("id").s(),
                    Long.parseLong(Item.get("Preço").s()),
                    Item.get("Produto").s(),
                    EnumCategoria.valueOf(Item.get("Categoria").s())));

        }
        client.close();
        return returning;
    }

}
