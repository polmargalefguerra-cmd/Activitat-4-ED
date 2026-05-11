package cat.urv.deim;

import cat.urv.deim.exceptions.ElementNoTrobat;
import cat.urv.deim.exceptions.LlistaBuida;
import cat.urv.deim.exceptions.LlistaPlena;

public class LlistaDinamica<E> implements TADLlista<E> {

    // Classe node per a la gestió dinàmica de la llista
    private class Node {
        E info;
        Node next;

        Node(E info) {
            this.info = info;
            this.next = null;
        }
    }

    private Node inici; // cap de la llista
    private Node fi; // cua de la llista
    private int numElem;
    private int capacitat;

    // Constructor
    public LlistaDinamica(int capacitat) {
        inici = null;
        fi = null;
        numElem = 0;
        this.capacitat = capacitat;
    }

    // Métodes de TADLlista
    @Override
    public void inserir(E elem) throws LlistaPlena {
        if (esPlena()) {
            throw new LlistaPlena();
        }

        Node nou = new Node(elem);

        if (esBuida()) {
            inici = nou;
            fi = nou;
        } else {
            fi.next = nou;
            fi = nou;
        }
        numElem++;
    }

    @Override
    public void inserir(E elem, int pos) throws LlistaPlena {
        if (esPlena()) {
            throw new LlistaPlena();
        }

        Node nou = new Node(elem);

        if (pos == 0) {
            nou.next = inici;
            inici = nou;

            if (numElem == 0) {
                fi = nou;
            }

        } else {

            Node actual = inici;

            for (int i = 0; i < pos - 1; i++) {
                actual = actual.next;
            }
            nou.next = actual.next;
            actual.next = nou;

            if (nou.next == null) {
                fi = nou; // si s'insereix a la última posició es fi
            }
        }
        numElem++;
    }

    @Override
    public void esborrar(int pos) throws LlistaBuida {
        if (esBuida()) {
            throw new LlistaBuida();
        }

        if (pos == 0) {
            inici = inici.next;

            if (inici == null) {
                fi = null;
            }

        } else {

            Node actual = inici;

            for (int i = 0; i < pos - 1; i++) {
                actual = actual.next;
            }

            if (actual.next == fi) {
                fi = actual;
            }

            actual.next = actual.next.next;
        }
        numElem--;
    }

    @Override
    public E consultar(int pos) throws LlistaBuida {
        if (esBuida()) {
            throw new LlistaBuida();
        }

        Node actual = inici;

        for (int i = 0; i < pos; i++) {
            actual = actual.next;
        }
        return actual.info;
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
        Node actual = inici;

        while (actual != null) {
            if (actual.info.equals(elem)) {
                return true;
            }
            actual = actual.next;
        }
        return false;
    }

    @Override
    public int posicio(E elem) throws ElementNoTrobat {
        Node actual = inici;
        int pos = 0;

        while (actual != null) {
            if (actual.info.equals(elem)) {
                return pos;
            }
            actual = actual.next;
            pos++;
        }
        throw new ElementNoTrobat();
    }

    @Override
    public E anterior(E elem) throws ElementNoTrobat {

        int pos = posicio(elem);

        if (pos == 0) {
            return null;
        }

        Node actual = inici;

        for (int i = 0; i < pos - 1; i++) {
            actual = actual.next;
        }

        return actual.info;
    }

    @Override
    public E seguent(E elem) throws ElementNoTrobat {

        int pos = posicio(elem);

        if (pos == numElem - 1) {
            return null;
        }

        Node actual = inici;

        for (int i = 0; i < pos + 1; i++) {
            actual = actual.next;
        }

        return actual.info;
    }
}
