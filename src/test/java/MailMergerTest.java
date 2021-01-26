import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


class MailMergerTest {
    @Test
    public void noData() {
        try {
            MailMerger merger = new MailMerger(new File("src/test/resources/noData.txt"));
            assertEquals(new HashMap<>(), merger);
        } catch (IOException err) {
            fail(err.getMessage());
        }
    }

    @Test
    public void negativeJustGarbage() {
        try {
            new MailMerger(new File("src/test/resources/justGarbage.txt"));
            fail("expected StringIndexOutOfBoundsException");
        } catch (StringIndexOutOfBoundsException err) {
            assertEquals("delimiter[->], not found in[qweasdxczxc]", err.getMessage());
        } catch (IOException err) {
            fail("no expected exception: " + err.getMessage());
        }
    }

    @Test
    public void noDuplicateUsers() {
        try {
            MailMerger merger = new MailMerger(new File("src/test/resources/noDuplicateUsers.txt"));
            HashMap<String, Set<String>> expected = new HashMap<>() {{
                put("user1", Set.of("xxx@ya.ru", "foo4@gmail.com", "lol@mail.ru"));
                put("user2", Set.of("foo@gmail.com", "ups@pisem.net"));
            }};
            assertEquals(expected, merger);
        } catch (IOException err) {
            fail(err.getMessage());
        }
    }

    @Test
    public void threeDuplicateUsers() {
        try {
            MailMerger merger = new MailMerger(new File("src/test/resources/threeDuplicateUsers.txt"));
            HashMap<String, Set<String>> expected = new HashMap<>() {{
                put("user1", Set.of("xxx@ya.ru", "foo@gmail.com", "lol@mail.ru", "ups@pisem.net", "aaa@bbb.ru"));
                put("user3", Set.of("xyz@pisem.net", "vasya@pupkin.com"));
            }};
            assertEquals(expected, merger);
        } catch (IOException err) {
            fail(err.getMessage());
        }
    }

    @Test
    public void garbageAfterNewLine() {
        try {
            MailMerger merger = new MailMerger(new File("src/test/resources/garbageAfterNewLine.txt"));
            HashMap<String, Set<String>> expected = new HashMap<>() {{
                put("user1", Set.of("xxx@ya.ru", "foo4@gmail.com", "lol@mail.ru"));
                put("user2", Set.of("foo@gmail.com", "ups@pisem.net"));
            }};
            assertEquals(expected, merger);
        } catch (IOException err) {
            fail(err.getMessage());
        }
    }

    @Test
    public void oneUserOnlyWithDupMails() {
        try {
            MailMerger merger = new MailMerger(new File("src/test/resources/oneUserOnlyWithDupMails.txt"));
            HashMap<String, Set<String>> expected = new HashMap<>() {{
                put("user1", Set.of("xxx@ya.ru", "foo4@gmail.com", "lol@mail.ru", "asdasd@aaqwe"));
            }};
            assertEquals(expected, merger);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}