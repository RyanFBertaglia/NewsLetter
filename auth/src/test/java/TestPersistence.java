import com.newsletter.model.Role;
import com.newsletter.model.User;
import com.newsletter.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {User.class, UserRepository.class, Role.class})
public class TestPersistence {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void userPersistence() {
        User user = new User();
        user.setEmail("email@gmail.com");
        user.setNome("nome");
        user.setCompartilhados(0);
        user.setValidade(LocalDate.now().plusMonths(1));
        user.setRole(Role.USER);

        User savedUser = userRepository.save(user);
        assertNotNull(savedUser.getId());
    }
}