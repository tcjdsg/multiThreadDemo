package GC;

import java.util.ArrayList;

public class TestGc {
    public static void main(String[] args) {
        byte[] allocation1, allocation2,allocation3,allocation4,allocation5;

        allocation5 = new byte[1* 1024];
        ArrayList<Object> objects = new ArrayList<>();
        while(true) {
            objects.add(allocation5);
        }
    }
}
