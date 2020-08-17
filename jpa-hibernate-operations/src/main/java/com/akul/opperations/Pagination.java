package com.akul.opperations;

import com.akul.model.Account;
import com.akul.opperations.util.JpaUtil;
import com.akul.opperations.util.TestDataGenerator;

import java.util.List;
import java.util.stream.Stream;

import static com.akul.opperations.util.JpaUtil.performReturningWithinPersistenceContext;
import static com.akul.opperations.util.JpaUtil.performWithinPersistenceContext;
import static java.util.stream.Collectors.toList;

public class Pagination {
    public static void main(String[] args) {
        JpaUtil.init("BasicEntitiesPostgres");
        List<Account> accounts = Stream.generate(TestDataGenerator::generateAccount).limit(100).collect(toList());
        performWithinPersistenceContext(entityManager -> accounts.forEach(entityManager::persist));

        List<Account> pageAccounts = loadAccounts(10, 20);
        pageAccounts.forEach(System.out::println);
        JpaUtil.close();
    }

    /**
     * Loads all account starting from {@code offset} position. The number of loaded accounts is limited by
     * {@code limit} number
     *
     * @param offset starting position
     * @param limit  number of account to load
     * @return list of accounts
     */
    private static List<Account> loadAccounts(int offset, int limit) {
        return performReturningWithinPersistenceContext(entityManager -> entityManager
                .createQuery("from Account a", Account.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList());
    }
}
