package org.resthub.training.repository.integration;

import org.fest.assertions.api.Assertions;
import org.resthub.test.AbstractTest;
import org.resthub.training.model.Address;
import org.resthub.training.model.User;
import org.resthub.training.repository.UserRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.TransactionSystemException;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.inject.Named;

@ActiveProfiles({"resthub-jpa", "resthub-pool-bonecp", "test"})
public class UserRepositoryIntegrationTest extends AbstractTest {

    @Inject
    @Named("userRepository")
    private UserRepository repository;

    @Test
    public void testCreateNullAddress() {
        User user = new User("userName", "user.email@test.org");

        user = this.repository.save(user);

        user = this.repository.findOne(user.getId());
        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getId()).isNotNull();
        Assertions.assertThat(user.getAddress()).isNull();
    }

    @Test(expectedExceptions = {TransactionSystemException.class})
    public void testCreateInvalidAddress() {
        User user = new User("userName", "user.email@test.org");
        Address address = new Address();
        address.setCity("city1");
        user.setAddress(address);

        this.repository.save(user);
    }

    @Test
    public void testCreateValidAddress() {
        User user = new User("userName", "user.email@test.org");
        Address address = new Address();
        address.setCity("city1");
        address.setCountry("country1");
        user.setAddress(address);

        user = this.repository.save(user);
        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getId()).isNotNull();
        Assertions.assertThat(user.getAddress()).isNotNull();
        Assertions.assertThat(user.getAddress().getCity()).isEqualTo("city1");
    }
}
