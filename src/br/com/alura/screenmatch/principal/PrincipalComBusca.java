package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.excecao.ErroDeAnoConversaoException;
import br.com.alura.screenmatch.modelos.Titulo;
import br.com.alura.screenmatch.modelos.TituloOmdb;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PrincipalComBusca {

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        String filme = "";
        List<Titulo> titulos = new ArrayList<>();


        while (!filme.equalsIgnoreCase("sair")) {
        System.out.println("Qual o filme você deseja?");
        filme = scanner.nextLine();

        if (filme.equalsIgnoreCase("sair")) {
            break;
        }

        String path = "https://www.omdbapi.com/?t=" + filme.replace(" ", "+") + "&apikey=82afe8c1";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(path))
                .build();
        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        String json = response.body();
        System.out.println(json);

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .create();
        TituloOmdb meuTituloOmdb = gson.fromJson(json, TituloOmdb.class);
        try {
            Titulo meuTitulo = new Titulo(meuTituloOmdb);
            System.out.println(meuTituloOmdb);
            System.out.println(meuTitulo);

            FileWriter escrita = new FileWriter("filmes.txt");
            escrita.write(meuTitulo.toString());
            escrita.close();
        } catch (NumberFormatException e) {
            System.out.println("Aconteceu um erro: ");
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Algum erro de argumento na busca, verifique o endereço");
        } catch (ErroDeAnoConversaoException e) {
            System.out.println(e.getMensagem());
        }
        }
        System.out.println("O programaga finalizou corretamente");
    }

}
