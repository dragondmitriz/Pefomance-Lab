import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class task1 {

    public static void main(String[] args) throws IOException {
        //считывание входных данных
        List<String> lines = Files.readAllLines(Paths.get(args[0]));
        List<Short> input = new ArrayList<>();
        for (String line : lines)
            input.add(Short.parseShort(line)); //заполнение массива
        lines.clear();

        //сортировка массива чисел по возрастанию
        Collections.sort(input);

        //переменные
        int N; //90 перцентиль, максимальное значение, среднее значение: количество чисел в массиве
        double X; //90 перцентиль: дробный порядковый номер Х
        short n; //90 перцентиль: порядковый номер n
        double x; //90 перцентиль: дробная часть порядкого номера
        //

        //инициализация переменных для решения задачи
        N = input.size();
        X = 0.9 * (N - 1) + 1;
        n = (short) X;
        x = X - n;

        //вывод решения задачи
        //90 перцентиль
        double percentl = input.get(n - 1) + x * (input.get(n) - input.get(n - 1));
        System.out.print(String.format("%.2f", percentl).replace(',', '.') + System.lineSeparator());
        //медиана
        double median = input.get((N + 1) / 2);
        if (N % 2 != 0) {
            median = ((double) (input.get((N + 1) / 2) + input.get((N - 1) / 2))) / 2;
        }
        System.out.print(String.format("%.2f", median).replace(',', '.') + System.lineSeparator());
        //максимальное значение
        System.out.print(String.format("%.2f", (double) input.get(N - 1)).replace(',', '.') + System.lineSeparator());
        //минимальное значение
        System.out.print(String.format("%.2f", (double) input.get(0)).replace(',', '.') + System.lineSeparator());
        //среднее значение
        int sum = 0;
        for (Short a : input)
            sum += a;
        System.out.print(String.format("%.2f", ((double) sum) / ((double) N)).replace(',', '.'));
    }
}
