package com.foodway.api.utils;

import com.foodway.api.model.Establishment;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
                saida.format("%s;%s;%s;%s;%s;%s;%s;%s;%.2f;%s;%s;%s\n",

                        establishment.getIdUser(),
                        establishment.getName(),
                        establishment.getEstablishmentName(),
                        establishment.getEmail(),
                        establishment.getTypeUser(),
                        establishment.getAddress().getCep(),
                        establishment.getAddress().getNumber(),
                        establishment.getAddress().getComplement(),
                        establishment.getRate(),
                        establishment.getCnpj()
//                        establishment.getCreatedAt(),
//                        establishment.getUpdatedAt()
                );
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
            System.out.printf("%-40s %-20s %-30s %-30s %-15s %-10s %-8s %-15s %-5s %-15s %-28s %-28s\n",
                    "ID", "NAME", "ESTABLISHMENT NAME", "EMAIL", "TYPE USER", "CEP", "NUMBER", "COMPLEMENT", "RATE", "CNPJ", "CREATED AT", "UPDATED AT");
            while (entrada.hasNext()) {

                String id = entrada.next();
                String name = entrada.next();
                String establishmentName = entrada.next();
                String email = entrada.next();
                String typeUser = entrada.next();
                String cep = entrada.next();
                String number = entrada.next();
                String complement = entrada.next();
                Double rate = entrada.nextDouble();
                String cnpj = entrada.next();
                String createdAt = entrada.next();
                String updatedAt = entrada.next();

                System.out.printf("%-40s %-20s %-30s %-30s %-15s %-10s %-8s %-15s %-5.2f %-15s %-28s %-28s\n",
                        id, name, establishmentName, email, typeUser, cep, number, complement, rate, cnpj, createdAt, updatedAt);
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

    public static void gravaRegistro(String registro, String nomeArq) {
        BufferedWriter saida = null;

        try {
            saida = new BufferedWriter(new FileWriter(nomeArq, true));
        } catch (IOException erro) {
            System.out.println("Erro na abertura do arquivo");
        }

        try {
            saida.append(registro + "\n");
            saida.close();
        } catch (IOException erro) {
            System.out.println("Erro ao gravar o arquivo");
            erro.printStackTrace();
        }
    }

    public static void gravaArquivoTxt(List<Establishment> lista, String nomeArq) {
        int contaRegDados = 0;

        // Monta o registro de header
        String header = "00ESTABLISHMENTS"; //Verificar documento de layout
        header += LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        header += "01";

        // Grava o registro de header
        gravaRegistro(header, nomeArq);

        // Grava os registros de dados (ou registros de corpo)
        for (Establishment e : lista) {
            String corpo = "02";
            corpo += String.format("%-40s", e.getIdUser());
            corpo += String.format("%-40s", e.getEstablishmentName());
            corpo += String.format("%-30s", e.getEmail());
            corpo += String.format("%-15s", e.getTypeUser());
            corpo += String.format("%-10s", e.getAddress().getCep());
            corpo += String.format("%-8s", e.getAddress().getNumber());
            corpo += String.format("%-15s", e.getAddress().getComplement());
            corpo += String.format("%5s", e.getRate());
            corpo += String.format("%-15s", e.getCnpj());
//            corpo += String.format("%19s", e.getCreatedAt());
//            corpo += String.format("%19s", e.getUpdatedAt());

            //Gravando corpo no arquivo:
            gravaRegistro(corpo, nomeArq);
            // Incrementa o contador de registros de dados gravados
            contaRegDados++;
        }

        // Monta e grava o registro de trailer
        String trailer = "01";
        trailer += String.format("%010d", contaRegDados);

        gravaRegistro(trailer, nomeArq);
    }

    public static void leArquivoTxt(String nomeArq) {

        BufferedReader entrada = null;
        String registro, tipoRegistro;
        int contaRegDadosLidos = 0;
        int qtdRegDadosGravados;

        List<Establishment> listaLida = new ArrayList<>();

        try {
            entrada = new BufferedReader(new FileReader(nomeArq));
        } catch (IOException erro) {
            System.out.println("Erro na abertura do arquivo");
        }

        try {
            registro = entrada.readLine();
            while (registro != null) {
                tipoRegistro = registro.substring(0, 2);

                if (tipoRegistro.equals("00")) {

                    System.out.printf("Contexto: " + registro.substring(2, 16) + "\n");
                    System.out.printf("Data e Hora: " + registro.substring(16, 35) + "\n");
                    System.out.printf("Versão do Layout: " + registro.substring(35, 37) + "\n");
                } else if (tipoRegistro.equals("01")) {
                    System.out.printf("Quantidade de registros: " + registro.substring(2, 12) + "\n");
                } else if (tipoRegistro.equals("02")) {
                    String idUser = registro.substring(2,42).trim();
                    String name = registro.substring(42,82).trim();
                    String email = registro.substring(82,112).trim();
                    String typeUser = registro.substring(112,127).trim();
                    String cep = registro.substring(127,137).trim();
                    String number = registro.substring(137,145).trim();
                    String complement = registro.substring(145,160).trim();
                    Double rate = Double.valueOf(registro.substring(160,165).trim().replace(",","."));
                    String cnpj = registro.substring(165,180).trim();
                    String createdAt = registro.substring(180,199).trim();
                    String updatedAt = registro.substring(199,218).trim();

                    System.out.printf("idUser: " + idUser + "\n");
                    System.out.printf("Nome: " + name + "\n");
                    System.out.printf("Email: " + email + "\n");
                    System.out.printf("Tipo de usuário: " + typeUser + "\n");
                    System.out.printf("CEP: " + cep + "\n");
                    System.out.printf("Número: " + number + "\n");
                    System.out.printf("Complemento: " + complement + "\n");
                    System.out.printf("Avaliação: " + rate + "\n");
                    System.out.printf("CNPJ: " + cnpj + "\n");
                    System.out.printf("Criado em: " + createdAt + "\n");
                    System.out.printf("Atualizado em: " + updatedAt + "\n");
                    contaRegDadosLidos++;

                } else {
                    System.out.println("Registro inválido");
                }
                registro = entrada.readLine();
            }
            entrada.close();
        } catch (IOException erro) {
            System.out.println("Erro ao ler o arquivo");
            erro.printStackTrace();
        }

        System.out.println("\nLista lida do arquivo:");
        for (Establishment e : listaLida) {
            System.out.println(e);
        }

        // Aqui tb seria possível salvar a lista no BD
        // repository.saveAll(lista);
    }
}
