package Grazie.com.Grazie_Backend.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
class UserJoinServiceTest  {

    @Autowired
    UserJoinService userJoinService;
    @Autowired
    UserRepository userRepository;

    @Test
    void TestSaveUser() {
        // given
        User user = new User();
        user.setUserId("testQQQ");
        user.setPassword("1234");
        user.setEmail("teada123r@example.com");
        user.setName("김말똥a213sd");
        user.setPhone("1231237123890");

        // when
        userRepository.save(user);

        // then
        Assertions.assertThat(user).isNotNull();
    }


}