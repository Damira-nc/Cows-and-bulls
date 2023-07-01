import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;


public class Main {
    public static void main(String[] args) {
        boolean gameContinue;
        ArrayList<String> userNumber = new ArrayList<>();
        ArrayList<String> hiddenNumber = new ArrayList<>();
        generateRandomNum(hiddenNumber);
        gameRecord("Game №" + numberOfGame() + " " + gameDataAndTime() + "Загаданная строка " + listToString(hiddenNumber) + "\n");
        int bullsNum = 0;
        int cowsNum = 0;
        int attemptsNumber = 0;
        do {
            if (attemptsNumber != 0)
                System.out.println(responseOutput(cowsNum, bullsNum));
            userNumber.clear();
            getUserNumber(userNumber);
            cowsNum = countCow(hiddenNumber, userNumber);
            bullsNum = countBull(hiddenNumber, userNumber);
            gameContinue = !isGameOver(hiddenNumber, userNumber);
            attemptsNumber++;
            gameRecord(" Запрос: " + listToString(userNumber) + " Ответ: " + responseOutput(cowsNum, bullsNum) + "\n");
        } while (gameContinue);
        System.out.println("Вы отгадали!");
        gameRecord(" Строка была угадана за " + attemptsNumber + " " + gameAttempt(attemptsNumber)+"." + "\n");
    }

    //Генерируем загаданное число
    static void generateRandomNum(ArrayList<String> newNum) {
        Random random = new Random();
        while (newNum.size() < 4) {
            String a = String.valueOf(random.nextInt(10));
            if (!newNum.contains(a))
                newNum.add(a);
        }
        //System.out.println(newNum);
    }
    //Обрабатываем число от пользователя
    static void getUserNumber(ArrayList<String> userNums) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Введите в консоль строку из четырех цифр: ");
            String User_num = reader.readLine();
            String[] s = User_num.split("");
            userNums.addAll(Arrays.asList(s));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Считаем количество коров
    static int countCow(ArrayList<String> randNums, ArrayList<String> userNums) {
        int cow = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (i != j && randNums.get(i).equals(userNums.get(j))) {
                    cow = cow + 1;
                }
            }
        }
        return cow;
    }
    //Считаем количество быков
    static int countBull(ArrayList<String> randNums, ArrayList<String> userNums) {
        int bull = 0;
        for (int i = 0; i < 4; i++) {
            if (randNums.get(i).equals(userNums.get(i))) {
                bull = bull + 1;
            }
        }
        return bull;
    }
    //Выводим количество коров и быков
    static String responseOutput(int cow, int bull) {
        String bulls, cows;

        if (cow == 1)
            cows = "корова";
        else if (cow > 1 && cow < 5)
            cows = "коровы";
        else cows = "коров";

        if (bull == 1)
            bulls = "бык";
        else if (bull > 1 && bull < 5)
            bulls = "быка";
        else bulls = "быков";

        return cow + " " + cows + " " + bull + " " + bulls;
    }
    //Проверка на конец игры
    static boolean isGameOver(ArrayList<String> randNums, ArrayList<String> userNums) {
        for (int i = 0; i < 4; i++) {
            if (randNums.equals(userNums))
                return true;
        }
        return false;
    }
    //Конвертация ArrayList в String
    static String listToString(ArrayList<String> listNums) {
        StringBuilder text = new StringBuilder();
        for (String con : listNums) {
            text.append(con);
        }
        return text.toString();
    }
    //Возвращает красивую дату
    static String gameDataAndTime() {
        Date currentTime = new Date();
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy hh:mm ");
        return df.format(currentTime.getTime());
    }
    static String gameAttempt(int attemptsNumber) {
        String attempt;
        if (attemptsNumber == 1)
            attempt = "попытка";
        else if (attemptsNumber > 1 && attemptsNumber < 5)
            attempt = "попытки";
        else attempt = "попыток";
        return attempt;
    }
    //Записывает отчет по игре
    static void gameRecord(String addText) {
        try {
            FileWriter writer = new FileWriter("gameResult.txt", true);
            BufferedWriter bufferWriter = new BufferedWriter(writer);
            bufferWriter.write(addText);
            bufferWriter.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    //Получет данные о количестве игр
    static int numberOfGame() {
        int countGame = 1;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("gameResult.txt"));
            String readLine;
            while ((readLine = reader.readLine())!= null) {
                String[] cols = readLine.split(" ");
                if (cols[0].equals("Game"))
                    countGame = countGame + 1;
            }
            reader.close();
            } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return countGame;
    }
}
