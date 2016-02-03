import static org.hamcrest.CoreMatchers.*;
import generated.*;

import org.junit.*;

import play.test.*;

public class BasicTest extends UnitTest {
    
    @Test
    public void testGenerate() {
        assertThat(new HelloWorld(), is(not(nullValue())));
    }
}
