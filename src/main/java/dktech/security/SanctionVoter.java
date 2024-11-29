
package dktech.security;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.function.Supplier;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;

import dktech.entity.Account;
import dktech.entity.Authorize;
import dktech.entity.Sanction;
import jakarta.servlet.http.HttpServletRequest;

public class SanctionVoter implements AuthorizationManager<Object> {

	@Override
	public AuthorizationDecision check(Supplier<Authentication> authentication, Object object) {
		Authentication auth = authentication.get();
		if (auth == null) {
			return new AuthorizationDecision(false);
		}

		AccountDetails accountDetails = (AccountDetails) auth.getPrincipal();
		Account account = accountDetails.getAccount();
		Set<Authorize> authorizations = account.getAuthorizations();

		for (ConfigAttribute attribute : getAttributes(object)) {
			if (supports(attribute)) {
				String requiredSanction = attribute.getAttribute();
				for (Authorize authorization : authorizations) {
					Sanction sanction = authorization.getSanction();
					if (sanction != null && requiredSanction.equals(String.valueOf(sanction.getSanctionID()))) {
						return new AuthorizationDecision(true);
					}
				}
				return new AuthorizationDecision(false);
			}
		}
		return new AuthorizationDecision(false);
	}

	private Collection<ConfigAttribute> getAttributes(Object object) {
		// Extract the required sanction ID from the URL or other context
		String requiredSanctionID = extractSanctionIDFromObject(object);
		return Collections.singletonList(new SecurityConfig(requiredSanctionID));
	}


private String extractSanctionIDFromObject(Object object) {
    if (object instanceof HttpServletRequest) {
        HttpServletRequest request = (HttpServletRequest) object;
        String sanctionID = request.getParameter("s");
        if (sanctionID != null && sanctionID.matches("\\d+")) {
            return sanctionID;
        }
    }
    return null; // Return null if sanction ID is not found or invalid
}


	public boolean supports(ConfigAttribute attribute) {
		return attribute.getAttribute() != null && attribute.getAttribute().matches("\\d+");
	}

	public boolean supports(Class<?> clazz) {
		return true; // Support any type of secured object
	}

	@Override
	public String toString() {
		return "SanctionVoter{}";
	}
}
