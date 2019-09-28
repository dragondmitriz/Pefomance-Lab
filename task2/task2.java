import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class task2 {

    public static void main(String args[]) throws IOException {

        //==считывание координат вершин прямоугольника==
        float[] rectangleX = new float[4];
        float[] rectangleY = new float[4];
        List<String> input_str = Files.readAllLines(Paths.get(args[0]));
        for (int i = 0; i < 4; i++) {

            String[] pointXY_str = input_str.get(i).split(" ");
            rectangleX[i] = Float.parseFloat(pointXY_str[0]);
            rectangleY[i] = Float.parseFloat(pointXY_str[1]);
        }

        //==считывание координат точек==
        float[] pointX;
        float[] pointY;
        input_str = Files.readAllLines(Paths.get(args[1]));
        pointX = new float[input_str.size()];
        pointY = new float[input_str.size()];
        for (int i = 0; i < input_str.size(); i++) {

            String[] pointXY_str = input_str.get(i).split(" ");
            pointX[i] = Float.parseFloat(pointXY_str[0]);
            pointY[i] = Float.parseFloat(pointXY_str[1]);
        }

        //==решение задачи==
        byte[] result = new byte[pointX.length];
        //перебор точек
        for (int ip = 0; ip < pointX.length; ip++) {

            float[] deviations = new float[4]; //массив "отклонений" от прямой стороны прямоугольника (косое умножение векторов)

            for (int ipr = 0; ipr < 4; ipr++) {

                //перебор вершин
                if ((pointX[ip] == rectangleX[ipr]) && (pointY[ip] == rectangleY[ipr])) {
                    result[ip] = 0;
                    break;
                }

                //определение местороположения точки относительно прямой
                //косое умножение векторов
                if (ipr < 3) {
                    deviations[ipr] = (rectangleX[ipr + 1] - rectangleX[ipr]) * (pointY[ip] - rectangleY[ipr]) - (rectangleY[ipr + 1] - rectangleY[ipr]) * (pointX[ip] - rectangleX[ipr]);
                } else {
                    deviations[ipr] = (rectangleX[0] - rectangleX[ipr]) * (pointY[ip] - rectangleY[ipr]) - (rectangleY[0] - rectangleY[ipr]) * (pointX[ip] - rectangleX[ipr]);
                }
                if (deviations[ipr] == 0) {
                    //точка лежит на прямой, образованной стороной прямоугольника
                    //определение принадлежности точки стороне прямоугольника скалярным умножением векторов
                    float skal;
                    if (ipr < 3) {
                        skal = (rectangleX[ipr + 1] - pointX[ip]) * (rectangleX[ipr] - pointX[ip]) + (rectangleY[ipr + 1] - pointY[ip]) * (rectangleY[ipr] - pointY[ip]);
                    } else {
                        skal = (rectangleX[0] - pointX[ip]) * (rectangleX[ipr] - pointX[ip]) + (rectangleY[0] - pointY[ip]) * (rectangleY[ipr] - pointY[ip]);
                    }
                    if (skal <= 0) {
                        //точка на стороне прямоугольника
                        result[ip] = 1;
                    } else {
                        //точка вне прямоугольника
                        result[ip] = 3;
                    }
                }

                //анализ "отклонений" (косого умножения векторов)
                byte negatyve_deviations = 0;
                for (float deviation : deviations)
                    if (deviation < 0) negatyve_deviations++;
                if ((negatyve_deviations == 0) || (negatyve_deviations == 4)) {
                    //точка внутри прямоугольника
                    result[ip] = 2;
                } else {
                    //точка вне прямоугольника
                    result[ip] = 3;
                }
            }
        }

        //==вывод результатов решения==
        for (short i = 0; i < result.length - 1; i++) {
            System.out.println(result[i]);
        }
        System.out.print(result[result.length - 1]);
    }
}
