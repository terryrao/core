package com.raowei.util;

import java.util.*;

/**
 * 多值的treeMap
 */
public class MultiValueTreeMap<K, V> {

    private TreeMap<K, List<V>> map;

    public MultiValueTreeMap(Comparator<K> comparator) {

        this.map = new TreeMap<>(comparator);
    }

    public MultiValueTreeMap() {
    }


    public int size() {
        return this.map.size();
    }

    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    public boolean containsKey(K key) {
        return this.containsValue(key);
    }

    public boolean containsValue(K value) {
        Set<Map.Entry<K, List<V>>> entries = this.map.entrySet();
        for (Map.Entry<K, List<V>> entry : entries) {
            for (V v : entry.getValue()) {
                if (v.equals(value))
                    return true;
            }
        }
        return false;
    }

    public List<V> get(K key) {
        return this.map.get(key);
    }

    public List<V> put(K key, V value) {
        List<V> vs = this.map.getOrDefault(key, new ArrayList<>());
        vs.add(value);
        return vs;
    }

    public List<V> remove(K key) {
        return this.remove(key);
    }


    public void clear() {
        this.map.clear();
    }

    public Set<K> keySet() {
        return this.map.keySet();
    }

    public Collection<List<V>> values() {
        return this.map.values();
    }

    public Set<Map.Entry<K, List<V>>> entrySet() {
        return this.map.entrySet();
    }

    public K higherKey(K k) {
        return this.map.higherKey(k);
    }

    public Map.Entry<K, List<V>> higherEntry(K k) {
        return this.map.higherEntry(k);
    }


    public K lowerKey(K k) {
        return this.map.lowerKey(k);
    }

    public Map.Entry<K, List<V>> lowerEntry(K k) {
        return this.map.lowerEntry(k);
    }

    public K floorKey(K k) {
        return this.map.floorKey(k);
    }

    public K ceilingKey(K k) {
        return this.map.ceilingKey(k);
    }


    public Map.Entry<K, List<V>> floorKeyEntry(K k) {
        return this.map.floorEntry(k);
    }

    public Map.Entry<K, List<V>> ceilingKeyEntry(K k) {
        return this.map.ceilingEntry(k);
    }


}
