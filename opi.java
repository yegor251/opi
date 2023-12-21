import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.Random;
import java.util.Scanner;

public class opi {

    static final Set<Character> LETTERS = Set.of('А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я');
    static Scanner scanner = new Scanner(System.in);
    public static String ReadFile(String path, int randomNum) throws FileNotFoundException {
        Scanner fileReader = new Scanner(new File(path));
        for (int i = 0; i < randomNum; i++) { //это функция которая считывает рандомную строку из файла
            fileReader.nextLine();  //скипаем ненужные строчки
        }
        return fileReader.nextLine(); //осталось вернуть нужную строку
    }

    public static int findRandom(){  //в этой функции генерируются числа в диапазоне [5,18)
        Random random = new Random(); //то есть от 5 до 17 , где [5,15] это очки на барабане, 16 - пропуск хода, 17 - сектор +
        int value = random.nextInt(5, 18); //дальше в коде эти числа просто умножим на 20 и получим числа с шагом 20
        return value;
    }

    public static String findFullWord(String str){
        String Word="";
        int i;
        for (i = 0; i < str.length(); i++) {
            if (str.charAt(i)==':'){ //тут ищем символ :
                i+=2; //когда нашли прибавляем к i 2 так как после ':' стоит пробел
                break;
            }
        }
        while (i<str.length()){ //оставшиеся символы записываем в Word
            Word+=str.charAt(i);
            i++;
        }
        return Word.toUpperCase(); //слово будет написано большими буквами
    }

    public static String findСondition(String str){ // это функция для нахождения вопроса(пример: река в америке) к слову
        String usl="";
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i)==':'){ //тут аналогично с прошлой функцией только записываем пока не встретим :
                return usl+':';
            }
            usl+=str.charAt(i);
        }
        return "";
    }

    public static String openLettersInWord(String fullWord, String currWord, char letter) {
        String newWord = "";
        for (int i = 0; i < fullWord.length(); i++) { //пробегаемся по исходному слову
            if (letter == fullWord.charAt(i)) { // если буква которую нужно открыть стоит в слове записываем ее в новое
                newWord += fullWord.charAt (i);
            }
            else {
                newWord += currWord.charAt(i); //если нет то записываем символ из текущего слова в котором есть _
            }
        }
        return newWord;
    }

    private static int CountLetter(String word, char letter) {
        int counter=0;
        for (int i = 0; i < word.length(); i++) { //подсчет определенной буквы в слове
            if (word.charAt(i) == letter) {
                counter++;
            }
        }
        return counter;
    }

    public static int findRandomLine(int max) { // функция по генерации рандомного числа
        Random random = new Random();           // в функцию нужно передавать количество строк в файле
        return random.nextInt(0, max);
    }

    public static String sectorPlus(String currWord, String fullWord) {
        String newWord = "";
        int num = 0;
        boolean isIncorrect;
        System.out.print("Введите номер буквы для открытия: ");
        do {
            isIncorrect = false;
            try {
                num = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                isIncorrect = true;
                System.out.print("Номер буквы - это число! Введите еще раз: ");
            }
            if (!isIncorrect && ((num < 1) || (num > fullWord.length()))) {
                isIncorrect = true;
                System.out.print("Вы вышли за границу слова! Введите еще раз: ");
            }
            if (!isIncorrect && (currWord.charAt(num - 1) != '_')) {
                isIncorrect = true;
                System.out.print("Буква уже открыта! Введите еще раз: ");
            }
        } while (isIncorrect);
        char letter = fullWord.charAt(num-1);
        for (int i = 0; i < fullWord.length(); i++) {
            if (letter == fullWord.charAt(i)) {
                newWord += fullWord.charAt(i);
            }
            else {
                newWord += currWord.charAt(i);
            }
        }
        return newWord;
    }

    public static char readLetter(){ //функ для считывания именно 1 символа
        String str;
        boolean isInCorrect;
        do {
            isInCorrect=false;
            str = scanner.nextLine();
            if (str.length()!=1) {
                System.out.println("Нужно вводить букву! Попробуйте снова!");
                isInCorrect=true;
            }
            if (!(isInCorrect) &&!(LETTERS.contains(str.toUpperCase().charAt(0)))) {
                System.out.println("Это не русская буква! Попробуйте снова!");
                isInCorrect=true;
            }
        } while (isInCorrect);
        str=str.toUpperCase(); //тут я делаю букву заглавной чтобы не было проблем
        return str.charAt(0);
    }

    public static void main (String[]args) throws FileNotFoundException {
        String  uslovie,fullWord, currWord = "",str,path;
        int[] playerScore = new int[3];
        for (int i = 0; i < 3; i++) {
            playerScore[i]=0;
        }
        path="q.txt";//тут нужно указывать путь к файлу
        str=ReadFile(path,findRandomLine(84)); //считываем строку из файла 50 это количество строк в базе
        uslovie=findСondition(str); //считываем условие(вопрос)
        fullWord=findFullWord(str); //считываем слово которое нужно угадать
        for (int i = 0; i < fullWord.length(); i++) {
            currWord+='_';
        }
        int tempScore;
        int round=1; //это переменная отмечает за игрока который сейчас называет букву
        int randNum;
        while (CountLetter(currWord,'_')>0){ //цикл продолжается пока есть неоткрытые буквы
            round=0;
            do{
                round++;
                System.out.println(uslovie+' '+currWord);
                System.out.println("Баланс игроков:");
                for (int i = 0; i < 3; i++) {
                    System.out.print(i+1);
                    System.out.print(" Игрок: ");
                    System.out.println(playerScore[i]);
                }
                System.out.print("Ход ");
                System.out.print(round);
                System.out.println("-го игрока");
                System.out.print("НА БАРАБАНЕ: ");
                randNum=findRandom(); //генерация рандомного числа от 5 до 17
                if (randNum==16){ // сгенерировало 16 - переход хода
                    System.out.println("переход хода");
                    continue; // эта штука скипает всё что идёт ниже и возращает к циклу на 144 строке
                }
                if (randNum==17){ //аналогично только тут вызывается функция сектор +
                    System.out.println("Сектор плюс");
                    currWord = sectorPlus(currWord,fullWord);
                    if (CountLetter(currWord,'_')==0)
                        round--;
                    continue;
                }
                tempScore=randNum*20; //очки нам нужны от 100 до 300
                System.out.println(tempScore);
                char letter;
                System.out.print("Введите букву: ");
                letter=readLetter(); //ввод буквы
                if (CountLetter(currWord,letter)>0){
                    System.out.println("Это буква уже открыта! Вы ошиблись."); //человек ошибся и назвал букву которая уже открыта (его проблемы)
                    continue;
                }
                tempScore*=CountLetter(fullWord,letter); //на количество открытых букв умножаем выбитое количество очков на барабане
                if (tempScore==0){ //если буквы нет, то и счётчик вернет 0, после умножения на 0 tempScore будет 0
                    System.out.println("Такой буквы нет");
                } else {
                    System.out.println("Есть такая буква");
                    playerScore[round-1]+=tempScore; //прибавляем к счету игрока
                    currWord = openLettersInWord(fullWord,currWord,letter);
                    round--;
                }
            }while (round<3 && CountLetter(currWord,'_')>0);
        }
        System.out.println("Слово: "+fullWord);
        System.out.print("Выиграл ");
        System.out.print(round+1);
        System.out.println("-й Игрок! Ура!");
        System.out.print("Баланс: ");
        System.out.println(playerScore[round]);
    }
}