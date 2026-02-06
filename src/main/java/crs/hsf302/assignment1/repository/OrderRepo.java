package crs.hsf302.assignment1.repository;

import crs.hsf302.assignment1.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepo extends JpaRepository<Order, Integer> {
    Boolean existsByEmailAndProgram_Id(String email, UUID programId);
    List<Order> findAllOrdersByProgramId(UUID programId);
}
