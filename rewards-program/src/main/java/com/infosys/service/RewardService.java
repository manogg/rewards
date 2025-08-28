package com.infosys.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.infosys.dto.RewardResponseDTO;
import com.infosys.dto.TransactionDTO;
import com.infosys.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RewardService {

	private final TransactionRepository repository;
	private final DynamicRewardCalculator rewardCalculator;

	@Async
	public CompletableFuture<RewardResponseDTO> calculateRewardsAsync(String customerId, LocalDate startDate,
			LocalDate endDate) {
		return CompletableFuture.completedFuture(calculateRewards(customerId, startDate, endDate));
	}

	public RewardResponseDTO calculateRewards(String customerId, LocalDate startDate, LocalDate endDate) {
		if (customerId == null || customerId.trim().isEmpty()) {
			throw new IllegalArgumentException("Customer ID must not be empty.");
		}

		List<TransactionDTO> transactions = repository.findByCustomerIdWithinPeriod(customerId, startDate, endDate);

		if (transactions.isEmpty()) {
			throw new IllegalArgumentException("No transactions found for the given customer and time frame.");
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

		Map<String, Integer> monthlyPoints = transactions.stream()
				.collect(Collectors.groupingBy(t -> t.getTransactionDate().format(formatter),
						Collectors.summingInt(t -> calculatePoints(t.getAmount()))));

		int totalPoints = monthlyPoints.values().stream().mapToInt(Integer::intValue).sum();

		RewardResponseDTO response = new RewardResponseDTO();
		response.setCustomerId(customerId);
		response.setMonthlyPoints(monthlyPoints);
		response.setTotalPoints(totalPoints);
		response.setTransactions(transactions);
		response.setMessage("Reward points calculated successfully.");

		return response;
	}

	public int calculatePoints(double amount) {
		return rewardCalculator.calculatePoints(amount);
	}
}
