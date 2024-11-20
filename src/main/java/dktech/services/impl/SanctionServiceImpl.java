package dktech.services.impl;

import dktech.entity.Sanction;
import dktech.repository.SanctionRepository;
import dktech.services.SanctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SanctionServiceImpl implements SanctionService {

    @Autowired
    private SanctionRepository SanctionRepository;

    @Override
    public Sanction saveSanction(Sanction Sanction) {
        return SanctionRepository.save(Sanction);
    }

    @Override
    public List<Sanction> getAllSanctions() {
        return SanctionRepository.findAll();
    }

    @Override
    public Sanction getSanctionById(long id) {
        return SanctionRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteSanction(long id) {
        SanctionRepository.deleteById(id);
    }
}
