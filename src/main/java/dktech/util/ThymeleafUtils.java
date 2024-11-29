package dktech.util;

import java.util.Set;

import dktech.entity.Sanction;

public class ThymeleafUtils {
	public static boolean containsSanctionID(Set<Sanction> sanctions, int sanctionID) {
		return sanctions.stream().anyMatch(sanction -> sanction.getSanctionID() == sanctionID);
	}

}
