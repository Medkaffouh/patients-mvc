package com.med.patientsmvc.web;

import com.med.patientsmvc.entities.Patient;
import com.med.patientsmvc.repositories.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;

    @GetMapping(path = "/index")
    public String patients(Model model){
        List<Patient> patients=patientRepository.findAll();
        model.addAttribute("listPatients",patients);
        return "patients";
    }
}
