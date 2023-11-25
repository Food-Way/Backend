package com.foodway.api.utils;

public class Pilha<T> {

    // 01) Atributos
    private T[] pilha;
    private int topo;

    // 02) Construtor
    public Pilha(int capacidade) {
        pilha = (T[]) new Object[capacidade];
        topo = -1;
    }

    // 03) MÃ©todo isEmpty
    public Boolean isEmpty() {
        if(topo == -1){
            return true;
        }

        return false;
    }

    // 04) MÃ©todo isFull
    public Boolean isFull() {
        if (topo == pilha.length - 1){
            return true;
        }

        return false;
    }

    // 05) MÃ©todo push
    public void push(T info) {
        if(isFull()){
            throw new IllegalStateException("Pilha cheia!");
        }
        if(topo == -1){
            pilha[0] = info;
            topo++;
        } else {
            pilha[topo + 1] = info;
            topo++;
        }
    }

    // 06) MÃ©todo pop
    public T pop() {
        T deleted = pilha[topo];
        pilha[topo] = null;
        topo--;
        return deleted;
    }

    // 07) MÃ©todo peek
    public T peek() {
        if (isEmpty()){
            throw new IllegalStateException("A listá está vazia!");
        }

        return pilha[topo];
    }

    // 08) MÃ©todo exibe
    public void exibe() {
        for (int i = 0; i < pilha.length; i++) {
            System.out.println(pilha[i]);
        }
    }

    //Getters & Setters (manter)
    public int getTopo() {
        return topo;
    }

    public T[] getPilha() {
        return pilha;
    }
}