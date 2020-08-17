package com.akul.opperations;

import com.akul.model.Account;
import com.akul.opperations.util.TestDataGenerator;
import org.hibernate.jpa.QueryHints;

import static com.akul.opperations.util.JpaUtil.init;
import static com.akul.opperations.util.JpaUtil.performWithinPersistenceContext;
import static com.akul.opperations.util.JpaUtil.*;

/**
 * This code demonstrate the dirty checking mechanism using read-only Session
 */
public class ReadOnlyQueryExample {
    public static void main(String[] args) {
        init("BasicEntitiesH2");

        long accountId = saveRandomAccount();

        performWithinPersistenceContext(entityManager -> {
            Account managedAccount = entityManager.createQuery("from Account a where a.id = :id", Account.class)
                    .setParameter("id", accountId)
                    .setHint(QueryHints.HINT_READONLY, true)// turns off dirty checking for this particular entity
                    .getSingleResult();
            managedAccount.setFirstName("TEST"); // won't cause SQL UPDATE statement since dirty checking is disabled for this entity
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
