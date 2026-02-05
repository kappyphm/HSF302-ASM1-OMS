package crs.hsf302.assignment1.mapper;

import crs.hsf302.assignment1.domain.CreateProgramRequest;
import crs.hsf302.assignment1.domain.dto.CreateProgramDto;
import crs.hsf302.assignment1.domain.dto.ProgramDto;
import crs.hsf302.assignment1.domain.entity.Program;
import crs.hsf302.assignment1.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
@RequiredArgsConstructor
public class ProgramMapper {

    private final OrderMapper orderMapper;

    public CreateProgramRequest fromDto(CreateProgramDto dto){
        return  new CreateProgramRequest(
                dto.name(),
                dto.descrition()
        );
    }

    public ProgramDto toDto(Program program){
        return new ProgramDto(
                program.getId(),
                program.getDescription(),
                program.getName(),

                program.getOrders().stream().map(orderMapper::toSummary).toList()
        );
    }

}
