package cat.urv.deim;

import cat.urv.deim.exceptions.ElementNoTrobat;
import cat.urv.deim.exceptions.LlistaPlena;
import java.util.ArrayList;
import java.util.Iterator;

public class HashingIndirecte<K extends Comparable<K>, V> implements TADHashMap<K, V> {

    private ArrayList<Node> taula;
    private int mida;
    private int numElements;

    // Node per a gestio manual de les llistes de colisions
    private class Node {
        K key;
        V value;
        Node next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    // Constructor
    public HashingIndirecte(int mida) {
        this.mida = mida;
        this.numElements = 0;
        taula = new ArrayList<>();

        for (int i = 0; i < mida; i++) {
            taula.add(null);
        }
    }

    // hash
    private int hash(K key) {
        return Math.abs(key.hashCode()) % mida;
    }

    @Override
    public void inserir(K key, V value) {
        int i = hash(key);
        Node actual = taula.get(i);

        // substituir si existeix
        while (actual != null) {
            if (actual.key.equals(key)) {
                actual.value = value;
                return; // surt
            }
            actual = actual.next;
        }

        // inserir al principi
        Node nou = new Node(key, value);
        nou.next = taula.get(i);
        taula.set(i, nou);

        numElements++;

        // redimensionar si factor de carrega es > 0.75
        if (factorCarrega() > 0.75) {
            redimensionar();
        }
    }

    // funcio auxiliar per a redimensionar taula (quan factCarreg > 0.75)
    private void redimensionar() {
        ArrayList<Node> antiga = taula;
        mida = mida * 2; // duplicar mida
        taula = new ArrayList<>();

        for (int i = 0; i < mida; i++) {
            taula.add(null);
        }

        numElements = 0;

        for (Node n : antiga) {
            while (n != null) {
                inserir(n.key, n.value);
                n = n.next;
            }
        }
    }

    @Override
    public V consultar(K key) throws ElementNoTrobat {
        int i = hash(key);
        Node actual = taula.get(i);

        while (actual != null) {
            if (actual.key.equals(key)) {
                return actual.value;
            }
            actual = actual.next;
        }

        throw new ElementNoTrobat();
    }

    @Override
    public void esborrar(K key) throws ElementNoTrobat {
        int i = hash(key);
        Node actual = taula.get(i);
        Node anterior = null;

        while (actual != null) {
            if (actual.key.equals(key)) {
                if (anterior == null) {
                    taula.set(i, actual.next);
                } else {
                    anterior.next = actual.next;
                }
                numElements--;
                return;
            }
            anterior = actual;
            actual = actual.next;
        }

        throw new ElementNoTrobat();
    }

    @Override
    public boolean buscar(K key) {
        int i = hash(key);
        Node actual = taula.get(i);

        while (actual != null) {
            if (actual.key.equals(key)) {
                return true;
            }
            actual = actual.next;
        }

        return false;
    }

    @Override
    public boolean esBuida() {
        return numElements == 0;
    }

    @Override
    public int numElements() {
        return numElements;
    }

    @Override
    public TADLlista<K> obtenirClaus() {
        TADLlista<K> claus = new LlistaArrayList<>(numElements);

        for (Node n : taula) {
            while (n != null) {
                try {
                    claus.inserir(n.key);
                } catch (LlistaPlena e) {
                    // no passara ja que la capacitat es exacta (pero s'ha de tractar igualment)
                }
                n = n.next;
            }
        }
        return claus;
    }

    @Override
    public float factorCarrega() {
        return (float) numElements / mida;
    }

    @Override
    public int midaTaula() {
        return mida;
    }

    @Override
    public Iterator<V> iterator() {
        ArrayList<V> valors = new ArrayList<>();

        for (Node n : taula) {
            while (n != null) {
                valors.add(n.value);
                n = n.next;
            }
        }

        return valors.iterator();
    }

}
