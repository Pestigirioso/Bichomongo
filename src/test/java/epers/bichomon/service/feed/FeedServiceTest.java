package epers.bichomon.service.feed;

import epers.bichomon.AbstractServiceTest;
import epers.bichomon.service.ServiceFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class FeedServiceTest extends AbstractServiceTest {

    private FeedService service = ServiceFactory.INSTANCE.getFeedService();

    @BeforeAll
    static void prepare() {
    }

    @Test
    void test1() {
        fail();
    }
}
