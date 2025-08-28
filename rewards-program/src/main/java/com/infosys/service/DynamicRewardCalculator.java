package com.infosys.service;

import org.springframework.stereotype.Component;
import org.togglz.core.manager.FeatureManager;

import com.infosys.enums.RewardFeatures;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DynamicRewardCalculator {

	private final FeatureManager featureManager;

	public int calculatePoints(double amount) {
		int points = 0;

		if (featureManager.isActive(RewardFeatures.BASIC_REWARD)) {
			if (amount > 100) {
				points += (int) ((amount - 100) * 2) + 50;
			} else if (amount > 50) {
				points += (int) (amount - 50);
			}
		}

		if (featureManager.isActive(RewardFeatures.FLAT_PER_DOLLAR)) {
			points += (int) amount;
		}

		if (featureManager.isActive(RewardFeatures.HOLIDAY_BONUS)) {
			points += 50;
		}

		return Math.max(points, 0);
	}

}
