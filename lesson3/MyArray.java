package lesson3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class MyArray {
    private ArrayList<String> words = new ArrayList<>();

    public void arrayCreate() {
        for (int i = 0; i < 15; i++) {
            words.add("Слово" + String.valueOf(i));
        }
        words.add("Одинаковое");
        words.add("Одинаковое");
        words.add("Одинаковое");
        words.add("Одинаковое");
        words.add("Одинаковое");

        System.out.println();
        System.out.println("Мой массив: " + words);
        System.out.println();
    }

    public void arrayCheck() {
        Set<String> uniq = new LinkedHashSet<>(words);
        HashMap<String, Integer> repeatMap = new HashMap<>();
        Set<String> repeat = new HashSet<>(words);

        System.out.println("Уникальные слова в массиве: " + uniq);
        System.out.println();

        for (String key : repeat) {
            int repeatCount = Collections.frequency(words, key);
            if(repeatCount > 1){
                repeatMap.put(key, repeatCount);
            }
        }
        System.out.println("Количество одинаковых слов: " + repeatMap);
        System.out.println();
    }
}