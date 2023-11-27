package com.foodway.api.utils;

import com.foodway.api.model.Establishment;
import com.foodway.api.model.Product;
import com.foodway.api.repository.EstablishmentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class GerenciadorDeArquivo {

    @Autowired
    private static EstablishmentRepository establishmentRepository;

    public static void gravaArquivoCsv(ListaObj<Establishment> listaEstab, String nomeArq) {

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
            Establishment establishment = listaEstab.getElemento(0);
            String menu = establishment.getMenu().toString();
            menu = menu.substring(1, menu.length() - 1);
            menu = menu.replaceAll(",\\s*", ";");
//            for (int i = 0; i < listaEstab.getTamanho(); i++) {

            //Recupere um elemento da lista e formate aqui:
            saida.format("%s;%s;%s;%s;%s;%s;%s;%s;%.2f;%s;%s\n",

                    establishment.getIdUser(),
                    establishment.getName(),
                    establishment.getEstablishmentName(),
                    establishment.getEmail(),
                    establishment.getTypeUser(),
                    establishment.getAddress().getCep(),
                    establishment.getAddress().getNumber(),
                    establishment.getAddress().getComplement(),
                    establishment.getGeneralRate(),
                    establishment.getCnpj(),
                    menu
            );

//            }
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
        int count = 0;

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
            System.out.printf("%-40s %-20s %-30s %-30s %-15s %10s %8s %-15s %5s %-15s %-40s %-30s %15s %-28s %-28s\n",
                    "ID", "USERNAME", "ESTABLISHMENT", "EMAIL", "TYPE USER", "CEP", "NUMBER", "COMPLEMENT", "RATE", "CNPJ", "PRODUCT ID", "NAME", "PRICE", "CREATED AT", "UPDATED AT");
            while (entrada.hasNext()) {

                String idEstablishment = "N/A";
                String name = "N/A";
                String establishmentName = "N/A";
                String email = "N/A";
                String typeUser = "N/A";
                String cep = "N/A";
                String number = "N/A";
                String complement = "N/A";
                Double rate = 0.0;
                String cnpj = "N/A";

                if (count == 0) {
                    idEstablishment = entrada.next();
                    name = entrada.next();
                    establishmentName = entrada.next();
                    email = entrada.next();
                    typeUser = entrada.next();
                    cep = entrada.next();
                    number = entrada.next();
                    complement = entrada.next();
                    rate = entrada.nextDouble();
                    cnpj = entrada.next();
                }

                String idProduct = entrada.next();
                String productName = entrada.next();
                Double productPrice = Double.valueOf(entrada.next());
                String createdAt = entrada.next();
                String updatedAt = entrada.next();


                System.out.printf("%-40s %-20s %-30s %-30s %-15s %10s %8s %-15s %5.2f %15s %-40s %-30s %15.2f %-28s %-28s\n",
                        idEstablishment, name, establishmentName, email, typeUser, cep, number, complement, rate, cnpj,
                        idProduct, productName, productPrice, createdAt, updatedAt);

                count++;
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

        String header = "00ESTABLISHMENTS";
        header += LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        header += "01";
        int count = 0;

        gravaRegistro(header, nomeArq);

        for (Establishment e : lista) {
            String corpo = null;
            if (count == 0) {
                corpo = "02";
                corpo += String.format("%-40s", e.getIdUser());
                corpo += String.format("%-40s", e.getEstablishmentName());
                corpo += String.format("%-30s", e.getEmail());
                corpo += String.format("%-15s", e.getTypeUser());
                corpo += String.format("%-10s", e.getAddress().getCep());
                corpo += String.format("%-8s", e.getAddress().getNumber());
                corpo += String.format("%-15s", e.getAddress().getComplement());
                corpo += String.format("%5s", e.getGeneralRate());
                corpo += String.format("%-15s", e.getCnpj());
                //Gravando corpo no arquivo:
                gravaRegistro(corpo, nomeArq);
                gravaRegistro("", nomeArq);
                contaRegDados++;
            }

            for (int i = 0; i < e.getMenu().size(); i++) {
                corpo = "03";
                corpo += String.format("%-40s", e.getMenu().get(i).getIdProduct());
                corpo += String.format("%-30s", e.getMenu().get(i).getName());
                corpo += String.format("%15s", e.getMenu().get(i).getPrice());
                corpo += String.format("%28s", e.getMenu().get(i).getCreatedAt());
                corpo += String.format("%28s", e.getMenu().get(i).getUpdatedAt());
                gravaRegistro(corpo, nomeArq);
                contaRegDados++;
            }
            count++;
        }
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

                if (registro.equals("")) {
                    registro = entrada.readLine();
                } else {
                    tipoRegistro = registro.substring(0, 2);

                    if (tipoRegistro.equals("00")) {
                        System.out.printf("Contexto: " + registro.substring(2, 16) + "\n");
                        System.out.printf("Data e Hora: " + registro.substring(16, 35) + "\n");
                        System.out.printf("Versão do Layout: " + registro.substring(35, 37) + "\n");
                    } else if (tipoRegistro.equals("01")) {
                        System.out.printf("Quantidade de registros: " + registro.substring(2, 12) + "\n");
                    } else if (tipoRegistro.equals("02")) {
                        String idUser = registro.substring(2, 42).trim();
                        String name = registro.substring(42, 82).trim();
                        String email = registro.substring(82, 112).trim();
                        String typeUser = registro.substring(112, 127).trim();
                        String cep = registro.substring(127, 137).trim();
                        String number = registro.substring(137, 145).trim();
                        String complement = registro.substring(145, 160).trim();
                        Double rate = Double.valueOf(registro.substring(160, 165).trim().replace(",", "."));
                        String cnpj = registro.substring(165, 180).trim();

                        System.out.printf("idUser: " + idUser + "\n");
                        System.out.printf("Nome: " + name + "\n");
                        System.out.printf("Email: " + email + "\n");
                        System.out.printf("Tipo de usuário: " + typeUser + "\n");
                        System.out.printf("CEP: " + cep + "\n");
                        System.out.printf("Número: " + number + "\n");
                        System.out.printf("Complemento: " + complement + "\n");
                        System.out.printf("Avaliação: " + rate + "\n");
                        System.out.printf("CNPJ: " + cnpj + "\n");

                    } else if (tipoRegistro.equals("03")) {

                        String idProduct = registro.substring(2, 42).trim();
                        String productName = registro.substring(42, 72).trim();
                        Double productPrice = Double.valueOf(registro.substring(72, 87).trim().replace(",", "."));
                        String createdAt = registro.substring(87, 115).trim();
                        String updatedAt = registro.substring(115, 142).trim();

                        System.out.printf("ID do produto: " + idProduct + "\n");
                        System.out.printf("Nome do produto: " + productName + "\n");
                        System.out.printf("Preço do produto: " + productPrice + "\n");
                        System.out.printf("Criado em: " + createdAt + "\n");
                        System.out.printf("Atualizado em: " + updatedAt + "\n");

                        contaRegDadosLidos++;

                    } else {
                        System.out.println("Registro inválido");
                    }
                    registro = entrada.readLine();
                }
            }
            entrada.close();
        } catch (IOException erro) {
            System.out.println("Erro ao ler o arquivo");
            erro.printStackTrace();
        }

        // Aqui tb seria possível salvar a lista no BD
        // repository.saveAll(lista);
    }
}
