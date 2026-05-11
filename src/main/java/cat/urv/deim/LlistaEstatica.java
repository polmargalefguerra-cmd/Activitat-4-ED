package cat.urv.deim;

import cat.urv.deim.exceptions.ElementNoTrobat;
import cat.urv.deim.exceptions.LlistaBuida;
import cat.urv.deim.exceptions.LlistaPlena;

public class LlistaEstatica<E> implements TADLlista<E> {

    private E[] elements;
    private int numElem;
    private int capacitat;

    // Constructor
    @SuppressWarnings("unchecked") // Per ignorar warning de cast Object[]
    public LlistaEstatica(int capacitat) {
        this.capacitat = capacitat;
        this.numElem = 0;
        elements = (E[]) new Object[capacitat];
    }

    // Implementació métodes de TADLlista
    @Override
    public void inserir(E elem) throws LlistaPlena {
        if (esPlena()) {
            throw new LlistaPlena();
        }

        elements[numElem] = elem;
        numElem++;
    }

    @Override
    public void inserir(E elem, int pos) throws LlistaPlena {
        if (esPlena()) {
            throw new LlistaPlena();
        }

        for (int i = numElem; i > pos; i--) {
            elements[i] = elements[i - 1];
        }

        elements[pos] = elem;
        numElem++;
    }

    @Override
    public void esborrar(int pos) throws LlistaBuida {
        if (esBuida()) {
            throw new LlistaBuida();
        }

        for (int i = pos; i < numElem - 1; i++) {
            elements[i] = elements[i + 1];
        }

        numElem--;
    }

    @Override
    public E consultar(int pos) throws LlistaBuida {
        if (esBuida()) {
            throw new LlistaBuida();
        }

        return elements[pos];
    }

    @Override
    public int numElem() {
        return numElem;
    }

    @Override
    public boolean esBuida() {
        return numElem == 0;
    }

    @Override
    public boolean esPlena() {
        return numElem == capacitat;
    }

    @Override
    public boolean existeix(E elem) {
        for (int i = 0; i < numElem; i++) {
            if (elements[i].equals(elem)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int posicio(E elem) throws ElementNoTrobat {
        for (int i = 0; i < numElem; i++) {
            if (elements[i].equals(elem)) {
                return i;
            }
        }
        throw new ElementNoTrobat();
    }

    @Override
    public E anterior(E elem) throws ElementNoTrobat {
        int pos = posicio(elem);

        if (pos == 0) {
            return null;
        }

        return elements[pos - 1];
    }

    @Override
    public E seguent(E elem) throws ElementNoTrobat {
        int pos = posicio(elem);

        if (pos == numElem - 1) {
            return null;
        }

        return elements[pos + 1];
    }
}
