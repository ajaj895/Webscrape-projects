import static org.junit.Assert.*;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedList;

public class RocketTest {

    @Test
    public void test_rocketSched() throws IOException {
        LinkedList<String> rs = RocketSoup.getSchedule(RocketSoup.getSfn());

    }
}
