package sorting;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Sort {
    private static String[] swap(String[] a, int i, int j) {
        String t = a[i];
        a[i] = a[j];
        a[j] = t;

        return a;
    }
    private static boolean compare(String[] strings, int i, int j, Character symbol) {
        Character ch1 = Character.toLowerCase(strings[i].toCharArray()[0]);
        Character ch2 = Character.toLowerCase(strings[j].toCharArray()[0]);

        if(ch1 == 'ё') {
            if(ch2 <= 'е') {
                ch1 = 'е' + 1;
            } else {
                ch1 = 'е';
            }
        }

        if(ch2 == 'ё') {
            if(ch1 <= 'е') {
                ch2 = 'е' + 1;
            } else {
                ch2 = 'е';
            }
        }

        switch (symbol) {
            case '>':
                return ch1 > ch2;
            case '<':
                return ch1 <ch2;
        }
        return false;
    }
    public static void main(String[] args) {

        System.out.println("Введите название сортировки");
        Scanner console = new Scanner(System.in);
        String s = console.nextLine();

        String words[];
        words = read("file.txt");

        switch (s) {
            case "shaker":
                words = shaker(words);
                break;
            case "bubble":
                words = bubble(words);
                break;
            case "quick":
                words = quick(words);
                break;
            case "oddEven":
                words = oddEven(words);
                break;
            case "gnome":
                words = gnome(words);
                break;
            default: System.out.println("Такой сортировки нет");
        }


        write("fileout.txt", words);
    }

    private static void write(String fileName, String[] strings) {
        try(FileWriter w = new FileWriter(fileName, false)) {
            for (String string : strings) {
                w.append(string);
                w.append("\r\n");
            }
            w.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static String[] read(String fileName) {
        String[] strings = new String[0];
        try {
            String s = new String(Files.readAllBytes(Paths.get(fileName)));
            strings = s.split("[^а-яА-ЯёЁa-zA-Z-]+");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return strings;
    }

    private static String[] shaker(String[] strings) {
        int left = 0;
        int right = strings.length - 1;

        while(left <= right) {
            for (int i = left; i < right; i++) {
                if(compare(strings, i, i + 1, '>')) {
                    swap(strings, i, i + 1);
                }
            }
            right--;

            for (int i = right; i > left; i--){
                if(compare(strings, i - 1, i, '>')) {
                    swap(strings, i - 1, i);
                }
            }
            left++;
        }
        return strings;
    }


    private static String[] bubble(String[] strings) {
        for (int i = 0; i < strings.length - 1; i++) {
            boolean isNotChange = true;
            for (int j = 0; j < strings.length - 1; j++) {
                if(compare(strings, j, j + 1, '>')) {
                    strings = swap(strings, j, j + 1);
                    isNotChange = false;
                }
            }
            if(isNotChange) break;
        }
        return strings;
    }

    private static String[] gnome(String[] strings) {
        int i = 1;
        int j = 2;
        int size = strings.length;
        while(i < size) {
            if(compare(strings, i - 1, i, '<')) {
                i = j;
                j++;
            } else {
                swap(strings, i - 1, i);
                i--;
                if(i == 0) {
                    i = j;
                    j++;
                }
            }
        }
        return strings;
    }

    private static String[] oddEven(String[] strings) {
        for (int i = 0; i < strings.length; i++) {
            for (int j = i % 2; j < strings.length - 1; j += 2) {
                if(compare(strings, j, j + 1, '>')) {
                    swap(strings, j, j + 1);
                }
            }
        }

        return strings;
    }

    private static String[] quick(String[] strings) {
        _quick(strings, 0, strings.length - 1);
        return strings;
    }

    private static void _quick(String[] strings, int left, int right) {
        if(left < right) {
            int center = _quickPartition(strings, left, right);
            _quick(strings, left, center - 1);
            _quick(strings, center + 1, right);
        }
    }

    private static int _quickPartition(String[] strings, int left, int right) {
        int i = left;
        int j = right;
        int p = (i + j) / 2;
        while (i < j) {
            while (compare(strings, i, p, '<')) {
                i++;
            }
            while (compare(strings, j, p, '>')) {
                j--;
            }
            if(i < j) {
                strings = swap(strings, i, j);
                if (i == p) {
                    p = j;
                    i++;
                }
                else if (j == p) {
                    p = i;
                    j--;
                } else {
                    i++;
                    j--;
                }
            }
        }
        return p;
    }
}