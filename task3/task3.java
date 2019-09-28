import java.io.FileReader;
import java.io.IOException;

public class task3 {

    public static void main(String[] args) throws IOException {

        //Инициализируем потоки считывания данных
        FileReader[] readers = new FileReader[5];
        for (byte i = 0; i < 5; i++) {
            readers[i] = new FileReader(args[0] + "/Cash" + (i + 1) + ".txt");
        }

        //==Считываем данные со всех потоков одновременно находя максимальное суммарное количество посетителей==
        double max = 0;
        int ind_max = 0;
        //перебор всех интервалов
        for (int i = 0; i < 16; i++) {

            double sum = 0;
            //перебор потоков: считываем по одному числу и суммируем их
            for (FileReader reader : readers) {

                String str = "";
                int a;
                while ((a = reader.read()) != -1) {
                    if ((char) a == '\n') break;
                    str += (char)a;
                }
                sum += Double.parseDouble(str);
            }

            //определяем максимум
            if (sum > max) {
                max = sum;
                ind_max = i+1;
            }
        }

        //Выводим результат
        System.out.print(ind_max);
    }
}
