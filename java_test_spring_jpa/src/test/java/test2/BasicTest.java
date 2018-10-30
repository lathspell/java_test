package test2;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import test2.config.Test2Config;
import test2.model.Pet;
import test2.model.User;
import test2.service.PetService;
import test2.service.UserService;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static test2.model.UserType.OWNER;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Test2Config.class)
@Slf4j
public class BasicTest {

    @Autowired
    private UserService userService;

    @Autowired
    private PetService petService;

    @Test
    public void testCrud() {
        Pet timmy = new Pet();
        timmy.setName("Timmy");
        timmy.setCreatedAt(new Date()); // h2 has no triggers

        User tim = new User();
        tim.setUsername("ttaler");
        tim.setFirstName("Tim");
        tim.setLastName("Taler");
        tim.setUserType(OWNER);
        tim.setCreatedAt(new Date()); // h2 has no triggers
        tim.getPets().add(timmy); // saves Timmy as well due to CascadeType.ALL

        log.info("Storing tim");
        userService.storeUser(tim);

        log.info("Checking user count");
        List<User> actualUsers = userService.getAllUsers();
        assertEquals(1, actualUsers.size());
        User actualUser = userService.getAllUsers().get(0);

        log.info("Checking user: " + actualUser);
        assertEquals("ttaler", actualUser.getUsername());
        assertNotNull(actualUser.getId());
        assertNotNull(actualUser.getPets());
        assertEquals(1, actualUser.getPets().size());

        log.info("Checking pets");
        List<Pet> actualPets = petService.getAllPets();
        assertEquals(1, actualPets.size());
        Pet actualPet = actualPets.get(0);

        log.info("Checking pet: " + actualPet);
        assertEquals("Timmy", actualPet.getName());
    }
}
