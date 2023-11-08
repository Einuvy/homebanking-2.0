package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Purchase;
import com.mindhub.homebanking.models.Summary;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.subModels.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.models.ENUM.CardColor.PLATINUM;
import static com.mindhub.homebanking.models.ENUM.TransactionType.DEPOSIT;
import static com.mindhub.homebanking.models.ENUM.TransactionType.WITHDRAWAL;

//Hacer que el client solo traiga el client y no sus relaciones, porque si hacemos que el client traiga todo tenemos que modificar las query para que no traigan las relaciones con
//la propiedad active en false

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository,
									  EmployeeRepository employeeRepository,
									  SavingAccountRepository savingAccountRepository,
									  CheckingAccountRepository checkingAccountRepository,
									  TransactionRepository transactionRepository,
									  DebitCardRepository debitCardRepository,
									  CreditCardRepository creditCardRepository,
									  PurchaseRepository purchaseRepository,
									  SummaryRepository summaryRepository,
									  PasswordEncoder passwordEncoder){
		return args -> {

			Client client = new Client("John", "Doe", "jhon@gmail.com", passwordEncoder.encode("Jhon!123"), LocalDate.of(2000, 3, 23),"12345678");
			Client client2 = new Client("Jane", "Doe", "jhane@gmail.com", passwordEncoder.encode("Jhane!1234"), LocalDate.of(2000, 3, 23),"87654321");
			Employee client3 = new Employee("Elias", "Medina", "eliasnikolasmv@gmail.com", passwordEncoder.encode("Elias!123"), LocalDate.of(2000, 5, 15),"42644301");
			clientRepository.save(client);
			clientRepository.save(client2);
			employeeRepository.save(client3);

			SavingAccount savingAccount = new SavingAccount("MHBS - 810523801", "jhon.mhb", "CBU - 947219880");
			savingAccount.setAmount(1000000d);
			SavingAccount savingAccount2 = new SavingAccount("MHBS - 691890376", "jhane.mhb", "CBU - 577515352");
			savingAccount2.setAmount(1000000d);
			client.addAccount(savingAccount);
			client2.addAccount(savingAccount2);
			savingAccountRepository.save(savingAccount);
			savingAccountRepository.save(savingAccount2);

			CheckingAccount checkingAccount = new CheckingAccount("MHBC - 387752324", "jhon.corriente", "CBU - 760522233");
			checkingAccount.setAmount(1000000d);
			CheckingAccount checkingAccount2 = new CheckingAccount("MHBC - 942263695", "jhane.corriente", "CBU - 391465499");
			checkingAccount2.setAmount(1000000d);
			client.addAccount(checkingAccount);
			client2.addAccount(checkingAccount2);
			checkingAccountRepository.save(checkingAccount);
			checkingAccountRepository.save(checkingAccount2);


			DebitCard debitCard = new DebitCard(PLATINUM, "4636 - 9629 - 3620 - 4824", "1245");
			DebitCard debitCard2 = new DebitCard(PLATINUM, "5555 - 9664 - 8914 - 9945", "1246");
			checkingAccount.addDebitCard(debitCard);
			checkingAccount2.addDebitCard(debitCard2);
			debitCardRepository.save(debitCard);
			debitCardRepository.save(debitCard2);

			Transaction transaction = new Transaction(-1000.0, "Retiro", WITHDRAWAL);
			Transaction transaction2 = new Transaction(1000.0, "Deposito", DEPOSIT);
			savingAccount.addTransaction(transaction);
			checkingAccount.addTransaction(transaction2);
			transactionRepository.save(transaction);
			transactionRepository.save(transaction2);

			CreditCard creditCard = new CreditCard(PLATINUM, "3376 - 1252 - 3329 - 1424", "1247", 5000000d);
			CreditCard creditCard2 = new CreditCard(PLATINUM, "5555 - 3620 - 9664 - 9629", "1248", 5000000d);
			client.addCreditCard(creditCard);
			client2.addCreditCard(creditCard2);
			creditCardRepository.save(creditCard);
			creditCardRepository.save(creditCard2);

			Purchase purchase = new Purchase(1000.0, 3, "Compra de celular", "123456789");
			Purchase purchase2 = new Purchase(1000.0, 6, "Compra de celular", "123456799");
			creditCard.addPurchase(purchase);
			creditCard2.addPurchase(purchase2);
			purchaseRepository.save(purchase);
			purchaseRepository.save(purchase2);

			Summary summary = new Summary(1000.0, "Resumen de tarjeta", "123456789");
			Summary summary2 = new Summary(1000.0, "Resumen de tarjeta", "123456799");
			summaryRepository.save(summary);
			summaryRepository.save(summary2);
			creditCard.addSummary(summary);
			creditCard2.addSummary(summary2);


			Set<Purchase> purchases= new HashSet<>(purchaseRepository.findByCreditCardNumberAndCreationDateBeforeAndCurrentPaymentLessThan(purchase.getCreditCard().getNumber(), LocalDateTime.now().plusDays(1), purchase.getMaxPayments()));
			summary.setPurchases(purchases);
			purchases.forEach(purchaseF -> {
				purchaseF.getSummaries().add(summary);
			});
			purchaseRepository.saveAll(purchases);
			summaryRepository.save(summary);

			Set<Purchase> purchases2= new HashSet<>(purchaseRepository.findByCreditCardNumberAndCreationDateBeforeAndCurrentPaymentLessThan(purchase2.getCreditCard().getNumber(), LocalDateTime.now().plusDays(1), purchase2.getMaxPayments()));
			summary2.setPurchases(purchases2);
			purchases2.forEach(purchaseF -> {
				purchaseF.getSummaries().add(summary2);
			});
			purchaseRepository.saveAll(purchases2);
			summaryRepository.save(summary2);

		};
	}

}
