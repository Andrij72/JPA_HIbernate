package com.akul.opperations;

import com.akul.model.Account;
import com.akul.opperations.util.TestDataGenerator;
import org.hibernate.Session;

import static com.akul.opperations.util.JpaUtil.init;
import static com.akul.opperations.util.JpaUtil.*;

/**
 * This code demonstrate the dirty checking mechanism for a particular entity
 */
public class ReadOnlySessionExample {
    public static void main(String[] args) {
        init("BasicEntitiesH2");

        long accountId = saveRandomAccount();

        performWithinPersistenceContext(entityManager -> {
            Session session = entityManager.unwrap(Session.class);
            session.setDefaultReadOnly(true);//turns off dirty checking
            Account managedAccount = entityManager.find(Account.class, accountId);
            managedAccount.setFirstName("YYYY"); // won't cause SQL UPDATE statement since dirty checking is disabled
        });

        printAccountById(accountId);

        close();
    }

    private static long saveRandomAccount() {
        Account account = TestDataGenerator.generateAccount();
        performWithinPersistenceContext(entityManager -> entityManager.persist(account));
        return account.getId();
    }

    private static void printAccountById(long accountId) {
        performWithinPersistenceContext(entityManager -> {
            Account storedAccount = entityManager.find(Account.class, accountId);
            System.out.println(storedAccount);
        });
    }
}

