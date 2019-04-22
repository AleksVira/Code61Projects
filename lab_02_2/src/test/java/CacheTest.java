import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class CacheTest {
    @Mock
    DataProvider dataProvider;
    @Mock
    Object data;
    @InjectMocks
    Cache cache;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetData() {
        when(dataProvider.provide()).thenReturn("provideResponse");

        Object result = cache.getData();
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testGetDataImmediately() {
        when(dataProvider.provide()).thenReturn("provideResponse");

        Object result = cache.getDataImmediately();
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme