package dktech.services.impl;

import dktech.entity.Authorize;
import dktech.repository.AuthorizeRepository;
import dktech.services.AuthorizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorizeServiceImpl implements AuthorizeService {

    @Autowired
    private AuthorizeRepository authorizeRepository;

    @Override
    public List<Authorize> getAllAuthorizations() {
        return authorizeRepository.findAll();
    }

    @Override
    public Authorize getAuthorizationById(Long id) {
        return authorizeRepository.findById(id).orElse(null);
    }

    @Override
    public Authorize createAuthorization(Authorize authorize) {
        return authorizeRepository.save(authorize);
    }

    @Override
    public Authorize updateAuthorization(Long id, Authorize authorize) {
        Optional<Authorize> existingAuthorization = authorizeRepository.findById(id);
        if (existingAuthorization.isPresent()) {
            authorize.setAuthorizeID(id);
            return authorizeRepository.save(authorize);
        }
        return null;
    }

    @Override
    public void deleteAuthorization(Long id) {
        authorizeRepository.deleteById(id);
    }
}
