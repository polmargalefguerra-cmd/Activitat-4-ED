package cat.urv.deim;

import cat.urv.deim.exceptions.ElementNoTrobat;
import cat.urv.deim.exceptions.LlistaPlena;
import java.util.ArrayList;
import java.util.Iterator;

public class HashingNoEncadenat<K extends Comparable<K>, V> implements TADHashMap<K, V> {

    private ArrayList<Entry> taula;
    private int mida;
    private int numElements;

    // node d'entrada de cada posicio de la taula
    private class Entry {
        K key;
        V value;
        boolean esborrat;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.esborrat = false;
        }
    }

    // Constructor
    public HashingNoEncadenat(int mida) {
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

        while (taula.get(i) != null && !taula.get(i).esborrat) {
            if (taula.get(i).key.equals(key)) {
                taula.get(i).value = value; // sobrescriu
                return;
            }
            i = (i + 1) % mida; // avançament lineal
        }

        taula.set(i, new Entry(key, value));
        numElements++;

        // redimensionar si factor de carrega es > 0.75
        if (factorCarrega() > 0.75) {
            redimensionar();
        }
    }

    // funcio auxiliar per a redimensionar taula (quan factCarreg > 0.75)
    private void redimensionar() {
        ArrayList<Entry> antiga = taula;
        mida = mida * 2; // duplicar mida
        taula = new ArrayList<>();

        for (int i = 0; i < mida; i++) {
            taula.add(null);
        }

        numElements = 0;

        for (Entry e : antiga) {
            if (e != null && !e.esborrat) {
                inserir(e.key, e.value);
            }
        }
    }

    @Override
    public V consultar(K key) throws ElementNoTrobat {
        int i = hash(key);
        int inici = i;

        while (taula.get(i) != null) {
            if (!taula.get(i).esborrat && taula.get(i).key.equals(key)) {
                return taula.get(i).value;
            }
            i = (i + 1) % mida;
            if (i == inici) {
                break; // no trobat
            }
        }

        throw new ElementNoTrobat();
    }

    @Override
    public void esborrar(K key) throws ElementNoTrobat {
        int i = hash(key);
        int inici = i;

        while (taula.get(i) != null) {
            if (!taula.get(i).esborrat && taula.get(i).key.equals(key)) {
                taula.get(i).esborrat = true; // marca d'esborrat
                numElements--;
                return;
            }
            i = (i + 1) % mida;
            if (i == inici) {
                break; // no trobat per esborrar mitjançant key
            }
        }

        throw new ElementNoTrobat();
    }

    @Override
    public boolean buscar(K key) {
        int i = hash(key);
        int inici = i;

        while (taula.get(i) != null) {
            if (!taula.get(i).esborrat && taula.get(i).key.equals(key)) {
                return true;
            }
            i = (i + 1) % mida;
            if (i == inici) {
                break; // no trobat
            }
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

        for (Entry ent : taula) {
            if (ent != null && !ent.esborrat) {
                try {
                    claus.inserir(ent.key);
                } catch (LlistaPlena e) {
                    // no passara ja que la capacitat es exacta (pero s'ha de tractar igualment)
                }
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

        for (Entry e : taula) {
            if (e != null && !e.esborrat) {
                valors.add(e.value);
            }
        }

        return valors.iterator();
    }
}
