import com.sun.istack.internal.NotNull;

import java.util.Date;

public class Cache<T extends String> {
    private final long dataLifetime;              // Время жизни данных в милисекундах
    private long dataStartTime;                   // Время получения новых данных от провайдера
    private final DataProvider dataProvider;      // Провайдер данных
    private T data;                               // Данные от провайдера

    public Cache(long dataLifetime, @NotNull DataProvider dataProvider) {
        if (dataProvider == null) {
            throw new NullPointerException("DataProvider is not initialized");
        } else {
            this.dataProvider = dataProvider;
        }
        this.dataLifetime = dataLifetime;
    }


    public T getData(@NotNull boolean rightNow) {
        String message;
        if ((getNowTime() - dataStartTime) > dataLifetime || rightNow) {
            data = updateData();
            message = rightNow ? "New data from source, immediately" : "Cache is expired, data from the remote source";
        } else {
            message = "Data from cache";
        }
        System.out.println(message);
        return data;
    }

    private T updateData() {
        T newData = (T) dataProvider.provide();
        dataStartTime = getNowTime();
        return newData;
    }

    private long getNowTime() {
        return new Date().getTime();
    }
}