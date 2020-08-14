package org.ccit.com;

import java.util.ArrayList;
import java.util.Collections;

public class CollectionSynchronized {

    public static void main(String[] args) {
        Collections.synchronizedList(new ArrayList<>());
    }

}
