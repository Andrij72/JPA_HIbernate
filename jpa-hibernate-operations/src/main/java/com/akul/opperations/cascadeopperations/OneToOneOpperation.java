package com.akul.opperations.cascadeopperations;

import com.akul.model.basic.User;
import com.akul.model.basic.Address;
import com.akul.opperations.util.TestDataGenerator;

import static com.akul.opperations.util.JpaUtil.*;

public class OneToOneOpperation {

    public static void main(String[] args) {
        init("BasicEntitiesPostgres");

        User user = TestDataGenerator.generateUser();
        System.out.println("Generated user: " + user);

        Address address = TestDataGenerator.generateAddress();
        System.out.println("Generated address: " + address);

        saveUserWithAddress(user, address);
        System.out.println("User with address has been saved." );

        printUserById(user.getId());

        close();
    }

    private static void saveUserWithAddress(User user, Address address) {
        performWithinPersistenceContext(em -> {
            em.persist(user);
            address.setUser(user);
            em.persist(address);
        });
    }

    private static void printUserById(Long userId) {
        performWithinPersistenceContext(em -> {
            User persistedUser = em.find(User.class, userId);
            System.out.println("Persisted user: " + persistedUser);
        });
    }
}
