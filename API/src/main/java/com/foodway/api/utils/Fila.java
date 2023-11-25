package com.foodway.api.utils;

import java.util.Arrays;

public class Fila<T> {
    // Atributos
    private int tamanho;
    private T[] fila;

    // Construtor
    public Fila(int capacidade) {
        tamanho = 0;
        fila = (T[]) new Object[capacidade];
    }

    // Métodos

    /* Método isEmpty() - retorna true se a fila está vazia e false caso contrário */
    public boolean isEmpty() {
        return fila[0] == null;
    }

    /* Método isFull() - retorna true se a fila está cheia e false caso contrário */
    public boolean isFull() {
        if (tamanho == fila.length) return true;
        return false;
    }

    /* Método insert - recebe um elemento e insere esse elemento na fila
                       no índice tamanho, e incrementa tamanho
                       Lançar IllegalStateException caso a fila esteja cheia
     */
    public void insert(T info) {
        if (isFull()) {
            throw new IllegalStateException("A fila está cheia!");
        }
        fila[tamanho] = info;
        tamanho++;
    }

    /* Método peek - retorna o primeiro elemento da fila, sem removê-lo */
    public T peek() {
        return fila[0];
    }

    /* Método poll - remove e retorna o primeiro elemento da fila, se a fila não estiver
       vazia. Quando um elemento é removido, a fila "anda", e tamanho é decrementado
       Depois que a fila andar, "limpar" o ex-último elemento da fila, atribuindo null
     */
    public T poll() {
        if (isEmpty()) {
            throw new IllegalStateException("A fila está vazia!");
        }
        T removed = fila[0];

        for (int i = 0; i < tamanho - 1; i++) {
            fila[i] = fila[i + 1];
        }

        fila[tamanho - 1] = null;
        tamanho--;
        return removed;
    }

    /* Método exibe() - exibe o conteúdo da fila */
    public void exibe() {
        for (int i = 0; i < fila.length; i++) {
            System.out.println(fila[i]);
        }
    }

    /* Usado nos testes  - complete para que fique certo */
    public int getTamanho() {
        return tamanho;
    }

    public T[] getFila() {
        return fila;
    }

    @Override
    public String toString() {
        return Arrays.toString(fila);
    }
}

