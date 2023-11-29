package vn.edu.iuh.fit.controllers;

import com.neovisionaries.i18n.CountryCode;
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
import vn.edu.iuh.fit.backend.models.Company;
import vn.edu.iuh.fit.backend.repositories.AddressRepository;
import vn.edu.iuh.fit.backend.repositories.CandidateRepository;
import vn.edu.iuh.fit.backend.repositories.CompanyRepository;
import vn.edu.iuh.fit.backend.services.CandidateServices;
import vn.edu.iuh.fit.backend.services.CompanyServices;

import java.util.Optional;

@Controller
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyServices companyServices;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private AddressRepository addressRepository;


    @GetMapping("/delete/{id}")
    public String deleteCompany(@PathVariable("id") long id,Model model) {
        companyRepository.deleteById(id);
        return "redirect:/company";
    }

    @PostMapping("/update")
    @Transactional
    public String updateCompany(@ModelAttribute Company company,
                                  @ModelAttribute Address address,
                                  Model model) {
        addressRepository.save(address);
        company.setAddress(address);
        companyRepository.save(company);
        return "redirect:/company";
    }
    @GetMapping("/update/{id}")
    public ModelAndView update(@PathVariable("id") long id,Model model) {
        ModelAndView modelAndView = new ModelAndView();
        Company company = companyRepository.findById(id).get();
        modelAndView.addObject("company",company);
        modelAndView.addObject("address",company.getAddress());
        modelAndView.addObject("countries",CountryCode.values());
        modelAndView.setViewName("companies/update-company");
        return  modelAndView;
    }

    @GetMapping("/list")
    public String showCandidateList(Model model) {
        model.addAttribute("companies", companyRepository.findAll());
        return "company/companies";
    }

    @GetMapping
    public String showCandidateListPaging(Model model,
                                          @RequestParam("page") Optional<Integer> page,
                                          @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);

        currentPage = Math.max(currentPage, 1);
        pageSize = Math.max(pageSize, 10);

        long totalCandidates = companyRepository.count();
        int totalPages = (int) Math.ceil((double) totalCandidates / pageSize);
        currentPage = Math.min(currentPage, totalPages);

        Page<Company> companyPage = companyServices.findAll(currentPage - 1,
                pageSize, "id", "asc");

        model.addAttribute("companyPage", companyPage);
        return "companies/companies-paging";
    }

    @PostMapping("/add")
    @Transactional
    public String addCandidate(
            @ModelAttribute("company") Company company,
            @ModelAttribute("address") Address address,
            BindingResult result, Model model) {

        addressRepository.save(address);
        company.setAddress(address);
        companyRepository.save(company);

        long totalCandidates = companyRepository.count();

        int pageSize = 30;
        int lastPage = (int) Math.ceil((double) totalCandidates / pageSize);
        return "redirect:/company?page=" + lastPage + "&size=" + pageSize;
    }

    @GetMapping("/add-company")
    public ModelAndView add(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        Company company = new Company();
        company.setAddress(new Address());
        modelAndView.addObject("company", company);
        modelAndView.addObject("address", company.getAddress());
        modelAndView.addObject("countries", CountryCode.values());
        modelAndView.setViewName("companies/add-company");
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
