package lesson2;

class Start {

    // Указываем какой размер массива считается допустимым
    static int arrLenght = 4;

    public static void main(String[] args) {
        System.out.println(); // Для красоты

        // Обрабатываем создание массива
        try {
            // Указываем какого размера хотим создать массив !!!
            arrCreate(4);
        } catch (MyArraySizeException e) {
            e.printStackTrace();
        }

        System.out.println(); // Для красоты
        System.out.println("Конец работы программы");
    }

    /* МЕТОД ДЛЯ СОЗДАНИЯ МАССИВА */
    private static void arrCreate(int size) throws MyArraySizeException {
        // Проверяем размер массива
        if (size != arrLenght) {
            throw new MyArraySizeException(size);
        } else {
            String[][] arr = new String[size][size];
            int arrInt[][] = new int[size][size];
            arrAdvancedInit(arr);
            arrPrint(arr);

            // Обрабатываем валидность конвертирования
            try {
                arrConvertSum(arr, arrInt);
            } catch (MyArrayDataException e) {
                e.printStackTrace();
            }

            System.out.println(); // Для красоты

        }
    }

    /* МЕТОД ДЛЯ РУЧНОГО ВВОДА МАССИВА */
    private static void arrAdvancedInit(String[][] arr) {
        arr[0][0] = "0";
        arr[0][1] = "1";
        arr[0][2] = "2";
        arr[0][3] = "3";

        arr[1][0] = "4";
        arr[1][1] = "5";
        arr[1][2] = "6";
        arr[1][3] = "7";

        arr[2][0] = "8";
        arr[2][1] = "9";
        arr[2][2] = "10";
        arr[2][3] = "11";

        arr[3][0] = "12";
        arr[3][1] = "13r";
        arr[3][2] = "14";
        arr[3][3] = "15";
    }

    /* МЕТОД ДЛЯ АВТО ЗАПОЛНЕНИЯ МАССИВА */
    private static void arrAutoInit(String[][] arr) {
        // Переменная для заполнения ячеек текстом (ЧИСЛА)
        int num = 0;

        // Переменная для заполнения ячеек текстом вызывающим ошибку (ТЕКСТ)
        String text = "text";

        // Перебираем ячейки массива и заполняем их текстом
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                arr[i][j] = String.valueOf(num++);
            }
        }

        // Заполняем отдельные ячейки текстом для вызова исключения
        arr[3][3] = text;
    }

    /* МЕТОД ДЛЯ ПЕЧАТИ МАССИВА */
    private static void arrPrint(String[][] arr) {
        System.out.println("Массив: ");

        // Перебираем массив для его матричной печати
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }

    /* МЕТОД ДЛЯ ПРЕОБРАЗОВАНИЯ ЯЧЕЕК В МАССИВЕ + СУММА */
    private static void arrConvertSum(String[][] arr, int[][] arrInt) throws MyArrayDataException {
        // Перменная для подсчёта суммы
        int sum = 0;

        System.out.println(); // Для красоты
        System.out.println("Сумма: ");

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                // Пробуем преобразовать ячейку и считать сумму
                try {
                    arrInt[i][j] = Integer.parseInt(arr[i][j]);
                    sum += arrInt[i][j];
                    System.out.print(sum + " -> ");
                } catch (Exception e) {
                    System.err.println("Ошибка преобразования");
                    throw new MyArrayDataException(i, j);
                }
            }
        }
    }
}