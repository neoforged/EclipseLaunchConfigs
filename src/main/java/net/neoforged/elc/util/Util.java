package net.neoforged.elc.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Util {

    /**
     * Creates a shallow copy of the passed list.
     */
    public static <T> List<T> copyOf(List<T> list) {
        List<T> out = new ArrayList<>(list.size());
        out.addAll(list);
        return out;
    }

    /**
     * Creates a shallow copy of the passed map.
     */
    public static <K, V> Map<K, V> copyOf(Map<K, V> map) {
        Map<K, V> out = new HashMap<>(map.size());
        out.putAll(map);
        return out;
    }

}
