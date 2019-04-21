import java.util.Date;

public class Cache {
    private long dataLifetime;              // Время жизни данных в милисекундах
    private long dataStartTime;             // Время получения новых данных от провайдера
    private DataProvider dataProvider;      // Провайдер данных
    private AnyData data;                   // Данные от провайдера

    public Cache(long dataLifetime, DataProvider dataProvider) throws InstantiationException {
        if (dataProvider == null) {
            throw new InstantiationException("DataProvider is not initialized");
        } else {
            this.dataProvider = dataProvider;
        }
        this.dataLifetime = dataLifetime;
    }


    public AnyData getData() {
        long nowTime = new Date().getTime();
        if (((nowTime - dataStartTime) > dataLifetime) || (data == null)) {
            data = updateData();
        }
        return data;
    }

    public AnyData updateData() {
        AnyData newData = dataProvider.provide();
        dataStartTime = new Date().getTime();
        return newData;
    }


}
