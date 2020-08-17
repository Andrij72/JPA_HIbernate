package com.akul.opperations;

import com.akul.model.Account;
import com.akul.opperations.dto.AccountProjection;
import com.akul.opperations.util.JpaUtil;
import com.akul.opperations.util.TestDataGenerator;

import static com.akul.opperations.util.JpaUtil.performReturningWithinPersistenceContext;
import static com.akul.opperations.util.JpaUtil.performWithinPersistenceContext;

public class FetchingProjection {

        public static void main(String[] args) {
            JpaUtil.init("BasicEntitiesPostgres");
            Account account = TestDataGenerator.generateAccount();
            performWithinPersistenceContext(entityManager -> entityManager.persist(account));

            AccountProjection accountProjection = fetchAccountProjectionById(account.getId());
            System.out.println(accountProjection);
            JpaUtil.close();
        }

        /**
         * Fetches {@link AccountProjection} by account id. An {@link AccountProjection} is a data transfer object (DTO) for
         * {@link Account}. (It's a short version that stores some account data, but is not manged by Hibernate)
         *
         * @param id account id
         * @return account projection
         */
        private static AccountProjection fetchAccountProjectionById(Long id) {
            return performReturningWithinPersistenceContext(entityManager ->
                    entityManager.createQuery("select new com.akul.opperations.dto.AccountProjection(a.id, a.email) " +
                            "from Account a where a.id = :id", AccountProjection.class)
                            .setParameter("id", id)
                            .getSingleResult());
        }
    }
