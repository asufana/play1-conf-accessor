import org.junit.*;

import generated.*;
import play.*;
import play.test.*;

public class BasicTest extends UnitTest {
    
    @Test
    public void testGenerate() {
        Play.id = "prod";
        System.out.println(Play.id);
        System.out.println(PlayConf.db_url());
    }
}
