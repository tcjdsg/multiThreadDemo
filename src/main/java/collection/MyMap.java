package collection;

public interface MyMap <K,V>{
    V get(K k);

    V put(K k, V v);

    int size();

    V remove(K k);

    boolean isEmpty();

}
