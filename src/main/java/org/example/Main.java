package org.example;

import java.util.*;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    public static void main(String[] args) throws InterruptedException {

        int maxVal = 0;
        int maxKey = 0;

        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            Thread thread = new Thread(() -> {
                Integer numberOfR = 0;
                String route = generateRoute("RLRFR", 100);
                for (int j = 0; j < route.length(); j++) {
                    if (route.charAt(j) == 'R') {
                        numberOfR++;
                    }
                }

                synchronized (numberOfR) {
                    if (sizeToFreq.containsKey(numberOfR)) {
                        sizeToFreq.put(numberOfR, sizeToFreq.get(numberOfR) + 1);
                    } else {
                        sizeToFreq.put(numberOfR, 1);
                    }
                }
            });
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        for (int key : sizeToFreq.keySet()) {
            if (sizeToFreq.get(key) > maxVal) {
                maxVal = sizeToFreq.get(key);
                maxKey = key;
            }
        }

        System.out.println("Самое частое количество повторений " + maxKey + " (встретилось " + maxVal + " раз(а))");

        System.out.println("Другие размеры:");
        for (int val : sizeToFreq.keySet()) {
            System.out.println("- " + val + " (" + sizeToFreq.get(val) + " раз(а))");
        }
    }
}