package cat.urv.deim;

import cat.urv.deim.exceptions.ElementNoTrobat;
import cat.urv.deim.exceptions.LlistaPlena;
import java.util.HashMap;
import java.util.Iterator;

public class HashingHashmap<K extends Comparable<K>, V> implements TADHashMap<K, V> {

    private HashMap<K, V> mapa;
    private int midaInici; // per a calcul de factorCarrega i midaTaula

    public HashingHashmap(int mida) {
        mapa = new HashMap<>();
        this.midaInici = mida;
    }

    @Override
    public void inserir(K key, V value) {
        mapa.put(key, value); // es sobreescriu si existeix
    }

    @Override
    public V consultar(K key) throws ElementNoTrobat {
        if (!mapa.containsKey(key)) {
            throw new ElementNoTrobat();
        }
        return mapa.get(key);
    }

    @Override
    public void esborrar(K key) throws ElementNoTrobat {
        if (!mapa.containsKey(key)) {
            throw new ElementNoTrobat();
        }
        mapa.remove(key);
    }

    @Override
    public boolean buscar(K key) {
        return mapa.containsKey(key);
    }

    @Override
    public boolean esBuida() {
        return mapa.isEmpty();
    }

    @Override
    public int numElements() {
        return mapa.size();
    }

    @Override
    public TADLlista<K> obtenirClaus() {
        TADLlista<K> claus = new LlistaArrayList<>(mapa.size());

        for (K key : mapa.keySet()) {
            try {
                claus.inserir(key);
            } catch (LlistaPlena e) {
                // no passara ja que la capacitat es exacta (pero s'ha de tractar igualment)
            }
        }
        return claus;
    }

    @Override
    public float factorCarrega() {
        return (float) mapa.size() / midaInici;
    }

    @Override
    public int midaTaula() {
        return midaInici;
    }

    @Override
    public Iterator<V> iterator() {
        return mapa.values().iterator();
    }
}
