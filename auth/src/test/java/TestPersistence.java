import com.newsletter.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {User.class})
public class TestPersistence {

    User user = new User();
    @Test
    public void testSetter() {
        user.setEmail("ryan2gmail.com");
        System.out.println(printValues());
    }

    public String printValues() {
        return user.getEmail();
    }
}