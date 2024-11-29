package dktech.services;

import dktech.entity.Authorize;
import java.util.List;

public interface AuthorizeService {
    List<Authorize> getAllAuthorizations();
    Authorize getAuthorizationById(long id);
    Authorize createAuthorization(Authorize authorize);
    Authorize updateAuthorization(long id, Authorize authorize);
    void deleteAuthorization(long id);
}
