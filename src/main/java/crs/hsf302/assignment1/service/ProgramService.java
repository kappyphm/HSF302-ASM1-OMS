package crs.hsf302.assignment1.service;

import crs.hsf302.assignment1.domain.CreateProgramRequest;
import crs.hsf302.assignment1.domain.entity.Program;
import crs.hsf302.assignment1.exception.ProgramNotFoundException;
import crs.hsf302.assignment1.repository.ProgramRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProgramService {
    private final ProgramRepo repo;

    public void addProgram(CreateProgramRequest req){
        Program program = new Program();
        program.setName(req.name());
        program.setDescription(req.description());
        repo.save(program);
    }

    public void removeProgram(UUID programId){
        if(!repo.existsById(programId)){
            throw new ProgramNotFoundException("Program with id:"+programId+" not found");
        }
        repo.deleteById(programId);
    }

    public Program getProgram(UUID programId){
        return repo.findById(programId).orElseThrow(() -> new ProgramNotFoundException("Program with id:"+programId+" not found"));
    }

    public List<Program> getAllPrograms(){
        return repo.findAll();
    }



}
