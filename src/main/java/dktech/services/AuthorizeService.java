package dktech.services;

import dktech.entity.Authorize;
import java.util.List;

public interface AuthorizeService {
    List<Authorize> getAllAuthorizations();
    Authorize getAuthorizationById(Long id);
    Authorize createAuthorization(Authorize authorize);
    Authorize updateAuthorization(Long id, Authorize authorize);
    void deleteAuthorization(Long id);
}
