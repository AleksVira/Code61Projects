import java.util.Date;

public class Cache {
    private long dataLifetime;              // Время жизни данных в милисекундах
    private long dataStartTime;             // Время получения новых данных от провайдера
    private DataProvider dataProvider;      // Провайдер данных
    private Object data;                    // Данные от провайдера

    public Cache(long dataLifetime, DataProvider dataProvider) {
        if (dataProvider == null) {
            throw new NullPointerException("DataProvider is not initialized");
        } else {
            this.dataProvider = dataProvider;
        }
        this.dataLifetime = dataLifetime;
    }


    public Object getData() {
        if (data == null) {
            data = updateData();
            System.out.println("Data received first time!");
        } else if (isCacheExpired()) {
            data = updateData();
            System.out.println("Cache is expired, data from the remote source");
        } else {
            System.out.println("Data from cache");
        }
        return data;
    }

    private boolean isCacheExpired() {
        long nowTime = new Date().getTime();
        return (nowTime - dataStartTime) > dataLifetime;
    }

    private Object updateData() {
        Object newData = dataProvider.provide();
        dataStartTime = new Date().getTime();
        return newData;
    }

    // Принудительное получение данных и обновление кэша
    public Object getDataImmediately() {
        System.out.println("New data from source, immediately");
        return updateData();
    }

}
