package dktech.services;

import dktech.entity.AuthorizeGroup;
import java.util.List;

public interface AuthorizeGroupService {
    List<AuthorizeGroup> getAllAuthorizationGroups();
    AuthorizeGroup getAuthorizationGroupById(Long id);
    AuthorizeGroup createAuthorizationGroup(AuthorizeGroup authorizeGroup);
    AuthorizeGroup updateAuthorizationGroup(Long id, AuthorizeGroup authorizeGroup);
    void deleteAuthorizationGroup(Long id);
}
