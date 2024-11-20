package dktech.services;

import java.util.List;
import dktech.entity.Sanction;

public interface SanctionService {
    Sanction saveSanction(Sanction Sanction);
    List<Sanction> getAllSanctions();
    Sanction getSanctionById(long id);
    void deleteSanction(long id);
}
