package dktech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dktech.entity.Position;


public interface PositionRepository extends JpaRepository<Position, Long> {
    Position findByPosition(String position);
}
