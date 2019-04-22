import java.util.Date;
import java.util.Random;

public class SimpleRandomDataProvider implements DataProvider {

    private final Random rndProvider;
    private final String[] dataStorage;

    public SimpleRandomDataProvider() {
        rndProvider = new Random(new Date().getTime());
        dataStorage = new String[10];
        dataStorage[0] = "0-04a21069-2b7d-48eb-aec9-9a85afdc2cfb";
        dataStorage[1] = "1-74fcf250-b4a5-4609-93d3-01e48010d2c7";
        dataStorage[2] = "2-4d58c8bb-f2d7-4e9b-9872-98c62fde76f9";
        dataStorage[3] = "3-87e0a26e-f3a6-4308-a74a-de860afd3da0";
        dataStorage[4] = "4-7dc691ba-7b73-41a8-883b-fdc7ae0638d9";
        dataStorage[5] = "5-dd8ea970-5a64-4053-9689-bdbf377d2820";
        dataStorage[6] = "6-dd56f561-61d5-4dbb-bf88-8543f0f9ea2d";
        dataStorage[7] = "7-a071eb99-b009-4a07-9b58-8d1f1d7276db";
        dataStorage[8] = "8-e91d18ea-7446-43b9-8450-8f296cd65f45";
        dataStorage[9] = "9-01257ffe-4a54-48ca-8e7e-ac183179a44c";
    }

    @Override
    public Object provide() {
        int index = rndProvider.nextInt(10);
        return dataStorage[index];
    }
}
