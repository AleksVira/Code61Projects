import java.util.Random;

public class DataMachine {

    private final static int LIFE_TIME_MILLISECONDS = 10000;           // Время жизни данных в миллисекундах
    private final static int MINIMAL_PERIOD = 100;                     // Минимальный допустимый интервал между запросами

    public static void main(String[] args) {

        DataProvider provider = new SimpleRandomDataProvider();
        Cache dataCache = new Cache(LIFE_TIME_MILLISECONDS, provider);
        Random rnd = new Random();
        // Организую 8 запросов данных со случайным временем между ними, от 0 до 2 * LIFE_TIME_MILLISECONDS
        for (int i = 0; i < 6; i++) {
            long period = rnd.nextInt(2 * LIFE_TIME_MILLISECONDS) + MINIMAL_PERIOD;
            System.out.println("Pause: " + period / 1000 + " seconds");

            // Делаю паузу
            try {
                Thread.sleep(period);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Запрашиваю данные
            System.out.println(">>>   " + dataCache.getData());
        }
        // Принудительно получаю данные
        System.out.println(">>>   " + dataCache.getDataImmediately());
    }
}
