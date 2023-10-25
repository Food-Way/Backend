package com.foodway.api.utils;

import com.foodway.api.model.EEntity;
import com.foodway.api.model.Establishment;

import java.util.List;

public class ListaObj<T> {

    // 01) Declarar vetor de int:
    // É inicializado no construtor
    private T[] vetor;

    // 02) Criar atributo nroElem:
    // Tem dupla função: representa quantos elementos foram adicionado no vetor
    // Também o índice de onde será adicionado o próximo elemento
    private int nroElem;

    // 03) Criar Construtor:
    // Recebe como argumento o tamanho máximo do vetor
    // Cria vetor com tamanho máximo informado
    // Inicializa nroElem
    public ListaObj(int tamanho) {
        vetor = (T[]) new Object[tamanho];
        nroElem = 0;
    }

    public ListaObj(int tamanho, List<T> list) {
        vetor = (T[]) new Object[tamanho];
        adicionaLista(list);
        nroElem = list.size();
    }

    public void adicionaLista(List<T> list) {
        for (int i = 0; i < vetor.length; i++) {
            adiciona(list.get(i));
        }
    }

    // 04) Método adiciona:
    // Recebe o elemento a ser adicionado na lista
    // Se a lista estiver cheia usar IllegalStateException();
    public void adiciona(T elemento) {
        if (nroElem < vetor.length) {
            vetor[nroElem] = elemento;
            nroElem++;
        } else {
            throw new IllegalStateException("A lista está cheia.");
        }
    }

    public void adicionaNoIndice(int indice, T obj) {
        if (indice > 0 || indice < nroElem) {
            vetor[indice] = obj;
        }
    }


    // 05) Método busca:
    // Recebe o elemento a ser procurado na lista
    // Retorna o índice do elemento, se for encontrado
    // Retorna -1 se não encontrou
    public int busca(T elementoBuscado) {
        for (int i = 0; i < nroElem; i++) {
            if (vetor[i].equals(elementoBuscado)) {
                return i;
            }
        }
        return -1;
    }

    // 06) Método removePeloIndice:
    // Recebe o índice do elemento a ser removido
    // Se o índice for inválido, retorna false
    // Se removeu, retorna true
    public boolean removePeloIndice(int indice) {
        if (indice < 0 || indice >= nroElem) {
            return false;
        }
        for (int i = indice; i < nroElem - 1; i++) {
            vetor[i] = vetor[i + 1];
        }
        vetor[nroElem - 1] = null;
        nroElem--;
        return true;
    }

    // 07) Método removeElemento
    // Recebe um elemento a ser removido
    // Utiliza os métodos busca e removePeloIndice
    // Retorna false, se não encontrou o elemento
    // Retorna true, se encontrou e removeu o elemento
    public boolean removeElemento(T elementoARemover) {
        int indice = busca(elementoARemover);
        if (indice != -1) {
            return removePeloIndice(indice);
        }
        return false;
    }

    // 08) Método getTamanho
    // Retorna o tamanho da lista
    public int getTamanho() {
        return nroElem;
    }

    // 09) Método getElemento
    // Recebe um índice e retorna o elemento desse índice
    // Se o índice for inválido, retorna null
    public T getElemento(int indice) {
        if (indice < 0 || indice >= nroElem) {
            return null;
        }
        return vetor[indice];
    }

    // 10) Método limpa
    // Limpa a lista
    public void limpa() {
        for (int i = 0; i < vetor.length; i++) {
            vetor[i] = null;
        }
        nroElem = 0;
    }

    // 11) Método exibe:
    // Exibe os elementos da lista
    public void exibe() {
        for (int i = 0; i < nroElem; i++) {
            System.out.println(vetor[i]);
        }
    }

    public ListaObj<T> filterBySome(ListaObj<T> list, String filter, EEntity eEntity) {
        switch (filter.toUpperCase() + eEntity) {
            case "RATEESTABLISHMENT" :
                if (list.getTamanho() > 0) {
                    for (int i = 0; i < list.getTamanho() - 1; i++) {
                        for (int j = 1; j < list.getTamanho() - i; j++) {
                            Establishment first = (Establishment) list.getElemento(j - 1);
                            Establishment next = (Establishment) list.getElemento(j);

                            if(next.getRate() > first.getRate()){
                                list.adicionaNoIndice(j - 1, (T) next);
                                list.adicionaNoIndice(j, (T) first);
                            }
                        }
                    }
                    return list;
                }
                break;

            default:
                return list;
        }
        return null;
    }

    // pegar o indice de uma nota específica
    public int findByRate(Double rate){
        int inf, mid, sup;
        inf = 0;
        sup = vetor.length - 1;

        while(inf <= sup){
            mid = (inf + sup) / 2;
            Establishment current = (Establishment) vetor[mid];
            if(current.getRate().equals(rate)){
                return mid;
            } else if(rate > current.getRate()){
                sup = mid - 1;
            } else {
                inf = mid + 1;
            }
        }

        return -1;
    }

    // Get do vetor
    // Não retirar, é usado nos testes
    public T[] getVetor() {
        return vetor;
    }

    public void setVetor(T[] vetor) {
        this.vetor = vetor;
    }

    public int getNroElem() {
        return nroElem;
    }

    public void setNroElem(int nroElem) {
        this.nroElem = nroElem;
    }
}
