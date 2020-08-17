package com.akul.opperations.cascadeopperations;

import com.akul.model.basic.Role;
import com.akul.model.basic.RoleType;
import com.akul.model.basic.User;
import com.akul.opperations.util.JpaUtil;
import com.akul.opperations.util.TestDataGenerator;

import static com.akul.opperations.util.JpaUtil.performWithinPersistenceContext;

public class StoringNewRelation {
    /**
     * This example shows different ways of storing new relationship between {@link User} and {@link Role}
     */
    public static void main(String[] args) {
        JpaUtil.init("BasicEntitiesH2");
        User user = TestDataGenerator.generateUser();

        storeNewUserWithRole(user, RoleType.USER);
        printUserById(user.getId());

        addNewRole(user, RoleType.OPERATOR);
        printUserById(user.getId());

        addNewRole(user.getId(), RoleType.ADMIN);
        printUserById(user.getId());

        JpaUtil.close();
    }

    /**
     * Stores new {@link User} with a specific role. This method uses cascade persist and a helper method
     * {@link User#addRole(Role)}, which links {@link User} object with new {@link Role} object
     *
     * @param user     NEW user
     * @param roleType a role type of NEW role
     */
    private static void storeNewUserWithRole(User user, RoleType roleType) {
        performWithinPersistenceContext(entityManager -> {
            Role role = Role.valueOf(roleType);
            user.addRole(role);
            entityManager.persist(user); // cascade operation will store also new role records
        });
    }

    /**
     * Loads and prints a {@link User} by id
     *
     * @param userId
     */
    private static void printUserById(long userId) {
        performWithinPersistenceContext(entityManager -> {
            User mangedUser = entityManager.find(User.class, userId);
            System.out.println(mangedUser);
        });
    }

    /**
     * Adds a new role to an existing user. This method receives existing user object and uses it helper method
     * {@link User#addRole(Role)} to add a new role
     *
     * @param user     DETACHED user
     * @param roleType role type of new role
     */
    private static void addNewRole(User user, RoleType roleType) {
        Role role = Role.valueOf(roleType);
        user.addRole(role);
        performWithinPersistenceContext(entityManager -> entityManager.merge(user));
    }

    /**
     * Adds a new role to an existing user by its id. This method does not require a full {@link User} object to add
     * a new role.
     *
     * @param userId   stored user id
     * @param roleType role type of new role
     */
    private static void addNewRole(long userId, RoleType roleType) {
        performWithinPersistenceContext(entityManager -> {
            Role role = Role.valueOf(roleType);
            User userProxy = entityManager.getReference(User.class, userId); // does not call db
            role.setUser(userProxy);
            entityManager.persist(role);
        });
    }

}
