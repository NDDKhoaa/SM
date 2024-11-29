package dktech.services.impl;

import dktech.entity.Position;
import dktech.repository.PositionRepository;
import dktech.services.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionServiceImpl implements PositionService {

    @Autowired
    private PositionRepository PositionRepository;

    @Override
    public Position createPosition(Position Position) {
        return PositionRepository.save(Position);
    }

    @Override
    public List<Position> getAllPositions() {
        return PositionRepository.findAll();
    }

    @Override
    public Position getPositionById(long id) {
        return PositionRepository.findById(id).orElse(null);
    }

    @Override
    public void deletePosition(long id) {
        PositionRepository.deleteById(id);
    }
    
	public Position findPositionByName(String name) {
		return PositionRepository.findByPosition(name);
	}
}
