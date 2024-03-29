package code.Hash;

import java.util.TreeMap;

public class HashTable<K, V> {

    //添加素数表，避免扩容产生和数
    /*private final int[] capacity = {
            53, 97,  ......
    };
    private int capacityIndex;
    */

    private static final int upperTol = 10;
    private static final int lowerTol = 2;
    private static final int initCapacity = 7;

    private TreeMap<K, V>[] hashtable;
    private int M;//取模元素，合适的素数
    private int size;

    public HashTable(int M){
        this.M = M;
        size = 0;
        hashtable = new TreeMap[M];
        for (int i = 0; i < M; i++){
            hashtable[i] = new TreeMap<>();
        }
    }

    public HashTable(){
        this(initCapacity);
    }

    private int hash(K key){
        return (key.hashCode() & 0x7fffffff) % M;
    }

    public int getSize() {
        return size;
    }

    public void add(K key, V value){
        TreeMap<K, V> map = hashtable[hash(key)];

        if (map.containsKey(key)){
            map.put(key, value);
        }else {
            map.put(key, value);
            size++;

            if (size >= upperTol * M){
                resize(2 * M);
            }
        }
    }

    public V remove(K key){
        TreeMap<K, V> map = hashtable[hash(key)];
        V ret = null;
        if (map.containsKey(key)){
            ret = map.remove(key);
            size--;

            if (size < lowerTol * M && (M / 2) >= initCapacity){
                resize(M / 2);
            }
        }

        return ret;
    }

    public void set(K key, V value){
        TreeMap<K, V> map = hashtable[hash(key)];
        if (!map.containsKey(key)){
            throw new IllegalArgumentException("sss");
        }
        map.put(key, value);
    }

    public boolean contains(K key){
        return hashtable[hash(key)].containsKey(key);
    }

    public V get(K key){
        return hashtable[hash(key)].get(key);
    }

    public void resize(int newM){
        TreeMap<K, V>[] newHashTable = new TreeMap[newM];
        for (int i = 0; i < newM; i++){
            newHashTable[i] = new TreeMap<>();
        }

        int oldM = M;
        this.M = newM;

        for (int i = 0;  i < oldM; i++){
            TreeMap<K, V> map = hashtable[i];
            for (K key : map.keySet()){
                newHashTable[hash(key)].put(key, map.get(key));
            }
        }

        this.hashtable = newHashTable;
    }
}
