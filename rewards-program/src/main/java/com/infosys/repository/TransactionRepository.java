package com.infosys.repository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.infosys.dto.TransactionDTO;

@Repository
public class TransactionRepository {

	private static final List<TransactionDTO> DATA = Arrays.asList(
			new TransactionDTO("CUST1001", 120, LocalDate.of(2025, 6, 10)),
			new TransactionDTO("CUST1001", 75, LocalDate.of(2025, 7, 15)),
			new TransactionDTO("CUST1001", 200, LocalDate.of(2025, 8, 5)),
			new TransactionDTO("CUST1002", 300, LocalDate.of(2025, 6, 20)),
			new TransactionDTO("CUST1002", 60, LocalDate.of(2025, 8, 10)),
			new TransactionDTO("CUST1003", 40, LocalDate.of(2025, 7, 12)));

	public List<TransactionDTO> findByCustomerIdWithinPeriod(String customerId, LocalDate start, LocalDate end) {
		return DATA.stream().filter(t -> t.getCustomerId().equalsIgnoreCase(customerId))
				.filter(t -> !t.getTransactionDate().isBefore(start) && !t.getTransactionDate().isAfter(end))
				.collect(Collectors.toList());
	}

}
