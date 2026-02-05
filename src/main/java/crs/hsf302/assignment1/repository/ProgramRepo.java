package crs.hsf302.assignment1.repository;

import crs.hsf302.assignment1.domain.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProgramRepo extends JpaRepository<Program, UUID> {
}
