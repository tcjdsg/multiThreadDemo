package collection;

public class MyHashMap <K,V> implements MyMap<K,V>{

    class Entry<K,V>{
        K k;
        V v;
        Entry<K,V> next ;

        public Entry(K k, V v, Entry<K, V> next) {
            this.k = k;
            this.v = v;
            this.next = next;
        }
    }
    final static int DEFUALT_CAPACITY = 16;
    final static float DEFUALT_LOAD_FACTOR = 0.775f;
    private int capacity;
    private float factor;
    private int size=0;
    Entry<K,V>[] tables;

    public MyHashMap() {
        this.capacity=DEFUALT_CAPACITY;
        this.factor = DEFUALT_LOAD_FACTOR;
    }

    public MyHashMap(int capacity, float factor, Entry<K, V>[] tables) {
        this.capacity = capacity;
        this.factor = factor;
        this.tables = tables;
    }

    @Override
    public V put(K k,V v){
        int index = k.hashCode()%tables.length;
        Entry<K,V> cur = tables[index];
        if(cur!=null){
            while(cur!=null){
                if(cur.k==k){
                    V old =cur.v;
                    cur.v = v;
                    return old;
                }
                cur = cur.next;
            }
            tables[index] = new Entry<K,V>(k,v,tables[index]);
            size++;
            return null;
        }
        tables[index] = new Entry<K,V>(k,v,null);
        size++;
        return null;
    }
    @Override
    public V get(K k) {
        int index = k.hashCode() % tables.length;
        Entry<K, V> current = tables[index];
        //遍历链表
        while (current!=null){
            if(current.k == k)
                return current.v;
            current = current.next;
        }
        //不存在则返回空
        return null;
    }
    @Override
    public V remove(K k) {
        int index = k.hashCode() % tables.length;
        V result = null;
        Entry<K, V> current = tables[index];
        //遍历链表
        Entry<K, V> pre = null;

        while(current!=null){
            if(current.k == k){
                result = current.v;
                size--;
                if (pre!=null){
                    pre.next = current.next;
                }else {
                    tables[index] = current.next;
                }

                return result;
            }
            //向下遍历
            pre = current;
            current = current.next;
        }

        return null;
    }
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }
}
