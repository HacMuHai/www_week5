package vn.edu.iuh.fit.controllers;

import com.neovisionaries.i18n.CountryCode;
import jakarta.servlet.annotation.WebServlet;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.edu.iuh.fit.backend.models.Address;
import vn.edu.iuh.fit.backend.models.Candidate;
import vn.edu.iuh.fit.backend.repositories.AddressRepository;
import vn.edu.iuh.fit.backend.repositories.CandidateRepository;
import vn.edu.iuh.fit.backend.services.CandidateServices;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/candidates")
public class CandidateController {
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private CandidateServices candidateServices;
    @Autowired
    private AddressRepository addressRepository;


    @GetMapping("/delete/{id}")
    public String deleteCandidate(@PathVariable("id") long id,Model model) {
        candidateRepository.deleteById(id);
        return "redirect:/candidates";
    }

    @PostMapping("/update")
    @Transactional
    public String updateCandidate(@ModelAttribute Candidate candidate,
                                  @ModelAttribute Address address,
                                  Model model) {
        addressRepository.save(address);
        candidate.setAddress(address);
        candidateRepository.save(candidate);
        return "redirect:/candidates";
    }
    @GetMapping("/update/{id}")
    public ModelAndView update(@PathVariable("id") long id,Model model) {
        ModelAndView modelAndView = new ModelAndView();
        Candidate candidate = candidateRepository.findById(id).get();
        modelAndView.addObject("candidate",candidate);
        modelAndView.addObject("address",candidate.getAddress());
        modelAndView.addObject("countries",CountryCode.values());
        modelAndView.setViewName("candidates/update-candidate");
        return  modelAndView;
    }

    @GetMapping("/list")
    public String showCandidateList(Model model) {
        model.addAttribute("candidates", candidateRepository.findAll());
        return "candidates/candidates";
    }

    @GetMapping
    public String showCandidateListPaging(Model model,
                                          @RequestParam("page") Optional<Integer> page,
                                          @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(30);

        currentPage = Math.max(currentPage, 1);
        pageSize = Math.max(pageSize, 0);

        long totalCandidates = candidateRepository.count();
        int totalPages = (int) Math.ceil((double) totalCandidates / pageSize);
        currentPage = Math.min(currentPage, totalPages);

        Page<Candidate> candidatePage = candidateServices.findAll(currentPage - 1,
                pageSize, "id", "asc");

        model.addAttribute("candidatePage", candidatePage);
        return "candidates/candidates-paging";
    }

    @PostMapping("/add")
    @Transactional
    public String addCandidate(
            @ModelAttribute("candidate") Candidate candidate,
            @ModelAttribute("address") Address address,
            BindingResult result, Model model) {

        addressRepository.save(address);
        candidate.setAddress(address);
        candidateRepository.save(candidate);

        long totalCandidates = candidateRepository.count();

        int pageSize = 30; // Kích thước trang
        int lastPage = (int) Math.ceil((double) totalCandidates / pageSize);
        return "redirect:/candidates?page=" + lastPage + "&size=" + pageSize;
    }

    @GetMapping("/add-candidate")
    public ModelAndView add(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        Candidate candidate = new Candidate();
        candidate.setAddress(new Address());
        modelAndView.addObject("candidate", candidate);
        modelAndView.addObject("address", candidate.getAddress());
        modelAndView.addObject("countries", CountryCode.values());
        modelAndView.setViewName("candidates/add-candidate");
        return modelAndView;
    }

//    @GetMapping("/update-candidate")
//    public ModelAndView update(Model model) {
//        ModelAndView modelAndView = new ModelAndView();
//        Candidate candidate = new Candidate();
//        candidate.setAddress(new Address());
//        modelAndView.addObject("candidate", candidate);
//        modelAndView.addObject("address", candidate.getAddress());
//        modelAndView.addObject("countries", CountryCode.values());
//        modelAndView.setViewName("candidates/add-candidate");
//        return modelAndView;
//    }
}
