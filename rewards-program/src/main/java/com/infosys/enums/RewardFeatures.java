package com.infosys.enums;

import org.togglz.core.Feature;
import org.togglz.core.annotation.Label;

public enum RewardFeatures implements Feature {

	@Label("Standard tiered rule ($50/$100)")
	BASIC_REWARD,

	@Label("Flat: $1 = 1 point on top")
	FLAT_PER_DOLLAR,

	@Label("Holiday bonus: +50 per transaction")
	HOLIDAY_BONUS
}
