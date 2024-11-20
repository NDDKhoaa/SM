package dktech.repository;

import dktech.entity.BillInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillInfoRepository extends JpaRepository<BillInfo, Long> {
}
