package vn.edu.iuh.fit.controllers;

import com.neovisionaries.i18n.CountryCode;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.edu.iuh.fit.backend.enums.SkillLevel;
import vn.edu.iuh.fit.backend.models.*;
import vn.edu.iuh.fit.backend.repositories.*;
import vn.edu.iuh.fit.backend.services.CandidateServices;
import vn.edu.iuh.fit.backend.services.JobServices;

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
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private JobServices jobServices;
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private CandidateSkillRepository candidateSkillRepository;


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

    @GetMapping("/login")
    public ModelAndView login(@RequestParam("page") Optional<Integer> page,
                              @RequestParam("size") Optional<Integer> size,
                              @RequestParam("id") Optional<Long> candidateId,
                              HttpServletRequest request,
                              Model model) {
        ModelAndView modelAndView = new ModelAndView();
        int pageCur = page.orElse(1);
        int sizeCur = size.orElse(10);

        pageCur = Math.max(pageCur, 1);
        sizeCur = Math.max(sizeCur, 10);

        PageRequest pageRequest = PageRequest.of(pageCur-1, sizeCur, Sort.by("id"));
        Page<Job> jobPage = jobRepository.findJobsByCanId(candidateId.get(),pageRequest);

        if (pageCur > jobPage.getTotalPages()){
            pageRequest = PageRequest.of(jobPage.getTotalPages()-1, sizeCur, Sort.by("id"));
            jobPage = jobRepository.findJobsByCanId(candidateId.get(),pageRequest);
        }
        Candidate candidate = candidateRepository.findById(candidateId.get()).get();

        model.addAttribute("jobPage",jobPage);
        model.addAttribute("candidate",candidate);
        request.getSession().setAttribute("candidateId",candidate.getId());
        modelAndView.setViewName("candidates/find-job");
        return modelAndView;
    }

    @GetMapping("/logout")
    public String logout( HttpServletRequest request,
                          Model model) {
        request.getSession().removeAttribute("candidateId");
        return "redirect:/candidates";
    }

    @GetMapping("/findJob")
    public String findJob( @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size,
                           @RequestParam("id") Optional<Long> candidateId,
                           HttpServletRequest request,
                           Model model) {
        int pageCur = page.orElse(1);
        int sizeCur = size.orElse(10);
        long id = candidateId.get();
        return String.format("redirect:/candidates/login?id=%s&page=%s&size=%s",id,pageCur,sizeCur);
    }

    @GetMapping("/suggest-skill")
    public String skill( @RequestParam("page") Optional<Integer> page,
                         @RequestParam("size") Optional<Integer> size,
                         HttpServletRequest request,
                         Model model)  {
        int pageCur = page.orElse(1);
        int sizeCur = size.orElse(10);

        pageCur = Math.max(pageCur, 1);
        sizeCur = Math.max(sizeCur, 10);

        long candidateId = (long) request.getSession().getAttribute("candidateId");

        PageRequest pageRequest = PageRequest.of(pageCur-1, sizeCur,Sort.by("id"));
        Page<Skill> skillPage = skillRepository.findByCandidateIdNot(candidateId,pageRequest);

        if (pageCur > skillPage.getTotalPages() && skillPage.getTotalPages() >= 1){
            pageRequest = PageRequest.of(skillPage.getTotalPages()-1, sizeCur,Sort.by("id"));
            skillPage = skillRepository.findByCandidateIdNot(candidateId,pageRequest);
        }
        request.getSession().getAttribute("candidateId");
        model.addAttribute("skillPage",skillPage);
        model.addAttribute("isPageSuggest",true);
        return "candidates/candidate-suggest-skill";
    }

    @GetMapping("/learn-skill")
    public String learnSkill( @RequestParam("page") Optional<Integer> page,
                         @RequestParam("size") Optional<Integer> size,
                         HttpServletRequest request,
                         Model model)  {
        int pageCur = page.orElse(1);
        int sizeCur = size.orElse(10);

        pageCur = Math.max(pageCur, 1);
        sizeCur = Math.max(sizeCur, 10);

        long candidateId = (long) request.getSession().getAttribute("candidateId");

        PageRequest pageRequest = PageRequest.of(pageCur-1, sizeCur,Sort.by("id"));
        Page<Skill> skillPage = skillRepository.findByCandidateId(candidateId,pageRequest);

        if (pageCur > skillPage.getTotalPages() && skillPage.getTotalPages() >= 1){
            pageRequest = PageRequest.of(skillPage.getTotalPages()-1, sizeCur,Sort.by("id"));
            skillPage = skillRepository.findByCandidateId(candidateId,pageRequest);
        }
        request.getSession().getAttribute("candidateId");
        model.addAttribute("skillPage",skillPage);
        model.addAttribute("isPageSuggest",false);
        return "candidates/candidate-suggest-skill";
    }

    @GetMapping("/add-skill")
    public String addSkill(@RequestParam("skillId") Optional<Long> skillId,
                             HttpServletRequest request,
                             Model model){
        long candidateId = (long) request.getSession().getAttribute("candidateId");
        Skill skill = new Skill(skillId.get());
        Candidate candidate = new Candidate(candidateId);
        candidateSkillRepository.save(new CandidateSkill(skill,candidate,SkillLevel.BEGINER,""));
        return "redirect:/candidates/suggest-skill";
    }

//    @GetMapping("/delete-skill")
//    public String deleteSkill(@RequestParam("skillId") Optional<Long> skillId,
//                             HttpServletRequest request,
//                             Model model){
//        long candidateId = (long) request.getSession().getAttribute("candidateId");
//        Skill skill = new Skill(skillId.get());
//        Candidate candidate = new Candidate(candidateId);
//        candidateSkillRepository.save(new CandidateSkill(skill,candidate,SkillLevel.BEGINER,""));
//        return "redirect:/candidates/suggest-skill";
//    }
}
