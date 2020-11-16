package ru.vsu.cs.skofenko;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

public class CommonModule {

    public static List<Integer> createNewList(List<Integer> list) {
        int maxCount = 0;
        int[] counts = new int[list.size()];
        List<Integer> answer = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            int count = countOf(list, list.get(i));
            if (count > maxCount)
                maxCount = count;
            counts[i] = count;
        }
        for (int i = maxCount; i >= 0; i--) {
            int startSize = answer.size();
            for (int j = 0; j < counts.length; j++) {
                if (counts[j] == i)
                    answer.add(list.get(j));
            }
            int nowSize = answer.size();
            if (nowSize != startSize) {
                sort(answer, startSize, nowSize);
            }
        }
        return answer;
    }

    public static int countOf(List<Integer> list, int value) {
        int count = 0;
        for (Integer a : list) {
            if (a == value)
                count++;
        }
        return count;
    }

    //пузырёк
    public static void sort(List<Integer> list, int index1, int index2) {
        for (int i = index2 - 1; i >= index1; i--) {
            for (int j = index1; j < i; j++) {
                if (list.get(j) > list.get(j + 1)) {
                    int temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
    }

    public static List<Integer> readListFromFile(String path) throws FileNotFoundException, NullPointerException, InputMismatchException {
        try (Scanner scanner = new Scanner(new File(path))) {
            List<Integer> list = new ArrayList<>();
            scanner.useDelimiter("(\\s|[,;])+");
            while (scanner.hasNext()) {
                list.add(scanner.nextInt());
            }
            return list;
        }
    }

    public static void writeListToFile(String path, List<Integer> answer) throws FileNotFoundException {
        try (PrintWriter pw = new PrintWriter(path)) {
            for (Integer a : answer) {
                pw.print(a + " ");
            }
        }
    }

    public static List<Integer> intArrayToList(int[] arr) {
        return Arrays.stream(arr).boxed().collect(Collectors.toList());
    }

    public static int[] intListToArray(List<Integer> list) {
        return list.stream().mapToInt(i -> i).toArray();
    }
}
