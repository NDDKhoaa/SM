package dktech.services.impl;

import dktech.entity.AuthorizeGroup;
import dktech.repository.AuthorizeGroupRepository;
import dktech.services.AuthorizeGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorizeGroupServiceImpl implements AuthorizeGroupService {

    @Autowired
    private AuthorizeGroupRepository authorizeGroupRepository;

    @Override
    public List<AuthorizeGroup> getAllAuthorizationGroups() {
        return authorizeGroupRepository.findAll();
    }

    @Override
    public AuthorizeGroup getAuthorizationGroupById(Long id) {
        return authorizeGroupRepository.findById(id).orElse(null);
    }

    @Override
    public AuthorizeGroup createAuthorizationGroup(AuthorizeGroup authorizeGroup) {
        return authorizeGroupRepository.save(authorizeGroup);
    }

    @Override
    public AuthorizeGroup updateAuthorizationGroup(Long id, AuthorizeGroup authorizeGroup) {
        Optional<AuthorizeGroup> existingAuthorizationGroup = authorizeGroupRepository.findById(id);
        if (existingAuthorizationGroup.isPresent()) {
            authorizeGroup.setAuthorizeGroupID(id);
            return authorizeGroupRepository.save(authorizeGroup);
        }
        return null;
    }

    @Override
    public void deleteAuthorizationGroup(Long id) {
        authorizeGroupRepository.deleteById(id);
    }
}
