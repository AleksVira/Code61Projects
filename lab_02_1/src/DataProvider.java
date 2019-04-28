import java.io.Serializable;

public interface DataProvider<T1> extends Serializable {
    T1 provide();
}