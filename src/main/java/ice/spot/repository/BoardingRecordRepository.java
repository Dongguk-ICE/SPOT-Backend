package ice.spot.repository;

import ice.spot.domain.BoardingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardingRecordRepository extends JpaRepository<BoardingRecord, Long> {
}
