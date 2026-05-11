package cat.urv.deim;

import java.util.ArrayList;

import cat.urv.deim.exceptions.ElementNoTrobat;
import cat.urv.deim.exceptions.LlistaBuida;
import cat.urv.deim.exceptions.LlistaPlena;

public class LlistaArrayList<E> implements TADLlista<E> {

    private ArrayList<E> llista;
    private int capacitat;

    // Constructor
    public LlistaArrayList(int capacitat) {
        this.capacitat = capacitat;
        llista = new ArrayList<>();
    }

    // Métodes a implementar de TADLlista
    @Override
    public void inserir(E elem) throws LlistaPlena {
        if (esPlena()) {
            throw new LlistaPlena();
        }
        llista.add(elem);
    }

    @Override
    public void inserir(E elem, int pos) throws LlistaPlena {
        if (esPlena()) {
            throw new LlistaPlena();
        }
        llista.add(pos, elem);
    }

    @Override
    public void esborrar(int pos) throws LlistaBuida {
        if (esBuida()) {
            throw new LlistaBuida();
        }
        llista.remove(pos);
    }

    @Override
    public E consultar(int pos) throws LlistaBuida {
        if (esBuida()) {
            throw new LlistaBuida();
        }
        return llista.get(pos);
    }

    @Override
    public int numElem() {
        return llista.size();
    }

    @Override
    public boolean esBuida() {
        return llista.isEmpty();
    }

    @Override
    public boolean esPlena() {
        return llista.size() == capacitat;
    }

    @Override
    public boolean existeix(E elem) {
        return llista.contains(elem);
    }

    @Override
    public int posicio(E elem) throws ElementNoTrobat {
        int pos = llista.indexOf(elem);

        if (pos == -1) {
            throw new ElementNoTrobat();
        }

        return pos;
    }

    @Override
    public E anterior(E elem) throws ElementNoTrobat {
        int pos = posicio(elem);

        if (pos == 0) {
            return null;
        }

        return llista.get(pos - 1);
    }

    @Override
    public E seguent(E elem) throws ElementNoTrobat {
        int pos = posicio(elem);

        if (pos == llista.size() - 1) {
            return null;
        }

        return llista.get(pos + 1);
    }
}
