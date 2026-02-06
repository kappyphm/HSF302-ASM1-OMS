package crs.hsf302.assignment1.controller;

import crs.hsf302.assignment1.domain.dto.CountryDto;
import crs.hsf302.assignment1.domain.dto.CreateOrderDto;
import crs.hsf302.assignment1.domain.dto.CreateProgramDto;
import crs.hsf302.assignment1.domain.dto.ProgramDto;
import crs.hsf302.assignment1.domain.entity.Program;
import crs.hsf302.assignment1.exception.DuplicateEmailException;
import crs.hsf302.assignment1.exception.DuplicateResourcceException;
import crs.hsf302.assignment1.exception.ProgramNotFoundException;
import crs.hsf302.assignment1.mapper.CountryMapper;
import crs.hsf302.assignment1.mapper.OrderMapper;
import crs.hsf302.assignment1.mapper.ProgramMapper;
import crs.hsf302.assignment1.service.CountryService;
import crs.hsf302.assignment1.service.OrderService;
import crs.hsf302.assignment1.service.ProgramService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/programs")
public class ProgramController {

    private final ProgramService programService;
    private final ProgramMapper programMapper;
    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final CountryService countryService;
    private final CountryMapper countryMapper;

    @GetMapping(path = "/add")
    public String programForm(Model model) {
        model.addAttribute("program", new ProgramDto());
        return "program-form";
    }

    @PostMapping(path = "/save")
    public String saveProgram(
            @ModelAttribute("program")
            @Valid
            CreateProgramDto dto,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            return "program-form";
        }

        programService.addProgram(programMapper.fromDto(dto));

        return "redirect:/programs";

    }

    @GetMapping
    public String programListView(Model model) {

        model.addAttribute("programs", programService.getAllPrograms().stream().map(programMapper::toDto).toList());
        return "program-list";
    }

    @GetMapping(path = "/{programId}/order")
    public String programFormView(Model model, @PathVariable UUID programId) {
        try {
            Program program = programService.getProgram(programId);

            model.addAttribute("program_name", program.getName());
            model.addAttribute("program_description", program.getDescription());
            model.addAttribute("program_id", programId);
            model.addAttribute("order", CreateOrderDto.builder().build());
            model.addAttribute("countries", countryService.findAll().stream().map(countryMapper::toDto).toList());

            return "program-order-detail";
        } catch (ProgramNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "error/404";
        }
    }

    @PostMapping(path = "{programId}/order")
    public String programOrder(
            @PathVariable UUID programId,
            @ModelAttribute("order") @Valid CreateOrderDto dto,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        try {
            if (!result.hasErrors()) {
                orderService.saveOrder(orderMapper.fromDto(dto), programId);
                return "redirect:/programs";
            }
        } catch (DuplicateEmailException e) {
            model.addAttribute("error", e.getMessage());
            result.rejectValue("email","error.order",e.getMessage());
        } catch (ProgramNotFoundException e) {
            return "error/404";
        }


        try {
            Program program = programService.getProgram(programId);
            model.addAttribute("program_name", program.getName());
            model.addAttribute("program_description", program.getDescription());
            model.addAttribute("program_id", programId);
            model.addAttribute("countries", countryService.findAll().stream().map(countryMapper::toDto).toList());

            return "program-order-detail";

        } catch (ProgramNotFoundException e) {
            return "error/404";
        }
    }

    @GetMapping(path = "/{programId}")
    public String programDetailView(
            Model model,
            @PathVariable UUID programId
    ) {
        try {
            Program program = programService.getProgram(programId);
            model.addAttribute("orders", program.getOrders().stream().map(orderMapper::toSummary).toList());
            return "program-detail";
        } catch (ProgramNotFoundException e) {
            return "error/404";
        }


    }

}
