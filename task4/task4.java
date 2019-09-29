import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class task4 {

    //класс для удобной работы с временной шкалой
    private static class Timelist {

        final int OPEN_TIME = 480;
        final int CLOSE_TIME = 1200;

        private List<Integer> timeline;

        public Timelist() {
            timeline = new ArrayList<>();
        }

        public void addArrivalTime(int time) {
            for (int i = 0; i < timeline.size(); i++) {
                if (Math.abs(timeline.get(i)) > time) {
                    timeline.add(i, time);
                    return;
                }
            }
            timeline.add(time);
        }

        public void addExitTime(int time) {
            for (int i = 0; i < timeline.size(); i++) {

                if (Math.abs(timeline.get(i)) > time) {
                    timeline.add(i, -time);
                    return;
                }
            }
            timeline.add(-time);
        }

        public int[][] getQuestTable() {
            int[][] questTable = new int[timeline.size() + 2][2];
            int i = 0;//указатель на текущий элемент в таблице посещений
            questTable[i][0] = OPEN_TIME;
            questTable[i][1] = 0;
            for (int item : timeline) {

                if (Math.abs(item) >= CLOSE_TIME) {
                    questTable[++i][0] = CLOSE_TIME;
                    questTable[i][1] = 0;
                    break;
                }

                if (Math.abs(item) > questTable[i][0]) {
                    questTable[++i][0] = Math.abs(item);
                    questTable[i][1] = questTable[i - 1][1] + (item / Math.abs(item));
                    continue;
                }
                questTable[i][1] += (item / Math.abs(item));
            }
            if (questTable[i][0] != CLOSE_TIME) {
                questTable[++i][0] = CLOSE_TIME;
                questTable[i][1] = 0;
            }
            questTable = Arrays.copyOf(questTable, i + 1);
            return questTable;
        }
    }

    private static String getTimeString(int count_min) {
        int hour = count_min / 60;
        int min = count_min % 60;
        if (min < 10) {
            return hour + ":0" + min;
        }
        return hour + ":" + min;
    }

    public static void main(String[] args) throws IOException {

        //==считывание входных данных==
        Timelist timelist = new Timelist();
        List<String> input_str = Files.readAllLines(Paths.get(args[0]));
        for (int i = 0; i < input_str.size(); i++) {

            String[] pair = input_str.get(i).split(" "); //пара вход-выход
            String[] timeHM = pair[0].split(":"); //часы и минуты
            timelist.addArrivalTime(Integer.parseInt(timeHM[0]) * 60 + Integer.parseInt(timeHM[1]));//общее количество минут от начала дня (не рабочего)
            timeHM = pair[1].split(":");
            timelist.addExitTime(Integer.parseInt(timeHM[0]) * 60 + Integer.parseInt(timeHM[1]));//общее количество минут от начала дня (не рабочего)
        }

        //==решение задачи==
        int[][] questTable = timelist.getQuestTable(); //составление таблицы посещений
        //==ищем максимумальное количество посетителей за день и записываем интервалы с зафиксированным максимумом посетителей
        int max = 0;
        List<String> result = new ArrayList<>();
        String result_str = "";
        for (int[] quest : questTable) {

            if (quest[1] > max) {
                max = quest[1];
                result.clear();
                result_str = getTimeString(quest[0]);
            }
            if ((quest[1] == max) && (result_str.equals(""))) {
                result_str = getTimeString(quest[0]);
            }
            if ((quest[1] < max) && (!result_str.equals(""))) {
                result.add(result_str + " " + getTimeString(quest[0]));
                result_str = "";
            }
        }
        //вывод результата
        for (String item : result) {
            System.out.print(item);
            if (result.indexOf(item) < result.size() - 1) {
                System.out.print(System.lineSeparator());
            }
        }
    }
}
