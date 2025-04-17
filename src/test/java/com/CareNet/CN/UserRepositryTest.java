package com.CareNet.CN;

// TEST LANG TO WALANG MAGRURUN
import static org.assertj.core.api.Assertions.assertThat;
import com.CareNet.CN.model.User;
import com.CareNet.CN.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(value = false)
@ActiveProfiles("test")
public class UserRepositryTest {

    @Autowired
    private UserRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setEmail("testemail2@yahoo.com");
        user.setPassword("testpass2");
        user.setFirstName("testfirst2");
        user.setLastName("testlast2");
        user.setMiddleName("testmid2");
        user.setGender("gayy");
        user.setBirthday("2000-06-13");
        user.setUsername("testuser2");
        user.setPhoneNumber("09991234322");

        User savedUser = repo.save(user);

        User existUser = entityManager.find(User.class, savedUser.getId());

        assertThat(existUser.getEmail()).isEqualTo(user.getEmail());
    }
}

