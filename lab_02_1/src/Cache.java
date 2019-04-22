import com.sun.istack.internal.NotNull;

import java.util.Date;

public class Cache<T> {
    private final long dataLifetime;              // Время жизни данных в милисекундах
    private long dataStartTime;                   // Время получения новых данных от провайдера
    private final DataProvider dataProvider;      // Провайдер данных
    private T data;                               // Данные от провайдера

    public Cache(long dataLifetime, @NotNull DataProvider dataProvider) {
        this.dataProvider = dataProvider;
        this.dataLifetime = dataLifetime;
    }


    public T getData(boolean rightNow) {
        if (rightNow) {
            // Принудительное получение данных и обновление кэша
            System.out.println("New data from source, immediately");
            return updateData();
        } else {
            long nowTime = getNowTime();
            if (data == null) {
                data = updateData();
                System.out.println("Data received first time!");
            } else if ((nowTime - dataStartTime) > dataLifetime) {
                data = updateData();
                System.out.println("Cache is expired, data from the remote source");
            } else {
                System.out.println("Data from cache");
            }
            return data;
        }
    }

    private T updateData() {
        T newData = (T) dataProvider.provide();
        dataStartTime = getNowTime();
        return newData;
    }

    private long getNowTime() {
        return new Date().getTime();
    }

    // Принудительное получение данных и обновление кэша
    public T getDataImmediately() {
        System.out.println("New data from source, immediately");
        return updateData();
    }

}
