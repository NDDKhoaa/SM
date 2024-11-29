package dktech.services;

import dktech.entity.Position;
import java.util.List;

public interface PositionService {
    Position createPosition(Position Position);
    List<Position> getAllPositions();
    Position getPositionById(long id);
    void deletePosition(long id);
    public Position findPositionByName(String name);
}
