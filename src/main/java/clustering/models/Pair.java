package clustering.models;

import java.util.Objects;

public class Pair<K, V> {
    private K key;
    private V value;

    public Pair() {
        key = null;
        value = null;
    }

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Pair) {
            return ((Pair<?, ?>) o).value.equals(this.value) && ((Pair<?, ?>) o).getKey().equals(this.key);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }
}