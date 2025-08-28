package com.infosys.dto;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RewardResponseDTO {

	private String customerId;
	private Map<String, Integer> monthlyPoints;
	private int totalPoints;
	private List<TransactionDTO> transactions;
	private String message;

}
