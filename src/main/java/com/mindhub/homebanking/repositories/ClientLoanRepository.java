package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.models.subModels.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientLoanRepository extends JpaRepository<ClientLoan, Long> {
    List<ClientLoan> findByPaidFalse();

    Optional<ClientLoan> findByCode(String code);

    List<ClientLoan> findByClientAndPaidFalse(Client client);

    List<ClientLoan> findByLoan(Loan loan);

    List<ClientLoan> findByClient_EmailAndPaidFalse(String email);

    List<ClientLoan> findByLoan_Code(String code);

    boolean existsByClient_EmailAndClient_Accounts_NumberAndActualPaymentGreaterThanEqual(String email, String number, Integer actualPayment, Integer payments);
}