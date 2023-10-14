package com.foodway.api.utils;

import com.foodway.api.model.Establishment;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class GerenciadorDeArquivo {
    public static void gravaArquivoCsv(ListaObj<Establishment> lista, String nomeArq) {
        FileWriter arq = null;
        Formatter saida = null;
        Boolean deuRuim = false;

        nomeArq += ".csv";

        // Bloco try-catch para abrir o arquivo
        try {
            arq = new FileWriter(nomeArq);
            saida = new Formatter(arq);
        } catch (IOException erro) {
            System.out.println("Erro ao abrir o arquivo");
            System.exit(1);
        }

        // Bloco try-catch para gravar o arquivo
        try {
            for (int i = 0; i < lista.getTamanho(); i++) {

                //Recupere um elemento da lista e formate aqui:
                Establishment establishment = lista.getElemento(i);
                saida.format("%s;%s;%s;%s;%s;%s;%s;%s\n",


                        establishment.getIdUser(),
                        establishment.getName(),
                        establishment.getEmail(),
                        establishment.getTypeUser(),
                        establishment.getCep(),
                        establishment.getNumber(),
                        establishment.
                        establishment.getRate(),
                        establishment.getCnpj());
            }
        } catch (FormatterClosedException erro) {
            System.out.println("Erro ao gravar o arquivo");
            deuRuim = true;
        } finally {
            saida.close();
            try {
                arq.close();
            } catch (IOException erro) {
                System.out.println("Erro ao fechar o arquivo");
                deuRuim = true;
            }
            if (deuRuim) {
                System.exit(1);
            }
        }
    }

    public static void leArquivoCsv(String nomeArq) {
        FileReader arq = null;
        Scanner entrada = null;
        Boolean deuRuim = false;

        nomeArq += ".csv";

        // Bloco try-catch para abrir o arquivo
        try {
            arq = new FileReader(nomeArq);
            entrada = new Scanner(arq).useDelimiter(";|\\n");
        } catch (FileNotFoundException erro) {
            System.out.println("Arquivo nao encontrado");
            System.exit(1);
        }

        // Bloco try-catch para ler o arquivo
        try {
            //Leia e formate a saída no console aqui:

            // Cabeçalho
            System.out.printf("-5s %-40s %-15s %-15s %-20s %-15s %-15s%\n",
                    "id","titulo","Premios","bilheteria","diretor","origem","lançamento" );
            while (entrada.hasNext()) {

                int id = entrada.nextInt();
                String titulo = entrada.next();
                int premios = entrada.nextInt();
                double bilheteria = entrada.nextDouble();
                String origem = entrada.next();
                String lancamento = entrada.next();

                System.out.printf("-5s %-30s %-15s %-15s %-20s %-15s %-15s%\n",
                        id, titulo, premios, bilheteria, origem, lancamento);

            }
        } catch (NoSuchElementException erro) {
            System.out.println("Arquivo com problemas");
            deuRuim = true;
        } catch (IllegalStateException erro) {
            System.out.println("Erro na leitura do arquivo");
            deuRuim = true;
        } finally {
            entrada.close();
            try {
                arq.close();
            } catch (IOException erro) {
                System.out.println("Erro ao fechar o arquivo");
                deuRuim = true;
            }
            if (deuRuim) {
                System.exit(1);
            }
        }
    }
}
