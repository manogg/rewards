package com.infosys.controller;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infosys.common.ApiResponse;
import com.infosys.dto.RewardResponseDTO;
import com.infosys.service.RewardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/rewards")
@RequiredArgsConstructor
@Tag(name = "Rewards", description = "Operations related to customer rewards")
public class RewardController {

	private final RewardService rewardService;

	/**
	 * Calculates reward points for a given customer within a time frame.
	 *
	 * @param customerId Customer ID (mandatory)
	 * @param startDate  Start date in YYYY-MM-DD
	 * @param endDate    End date in YYYY-MM-DD
	 * @return CompletableFuture with detailed reward response
	 */
	@GetMapping
	@Operation(summary = "Calculate rewards for a customer", description = "Calculates reward points for a given customer based on their transactions and timeframe.")
	public CompletableFuture<ResponseEntity<ApiResponse<RewardResponseDTO>>> calculateRewards(
			@RequestParam @NotBlank String customerId,
			@RequestParam @NotBlank @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam @NotBlank @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
			@RequestHeader(name = "X-API-VERSION", defaultValue = "1") String apiVersion) {

		log.info("Received request to calculate rewards for customer: {} from {} to {}", customerId, startDate,
				endDate);

		return rewardService.calculateRewardsAsync(customerId, startDate, endDate).thenApply(response -> {
			log.info("Successfully calculated rewards for customer: {}", customerId);
			ApiResponse<RewardResponseDTO> apiResponse = new ApiResponse<>(HttpStatus.OK.value(),
					"Reward points calculated successfully.", response);
			return ResponseEntity.ok(apiResponse);
		}).exceptionally(ex -> {
			log.error("Error calculating rewards for customer: {} - {}", customerId, ex.getMessage(), ex);
			ApiResponse<RewardResponseDTO> errorResponse = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					"Failed to calculate rewards: " + ex.getMessage(), null);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		});
	}

}
