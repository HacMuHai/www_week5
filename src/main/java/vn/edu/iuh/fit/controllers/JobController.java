package vn.edu.iuh.fit.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.edu.iuh.fit.backend.models.Company;
import vn.edu.iuh.fit.backend.models.Job;
import vn.edu.iuh.fit.backend.models.JobSkill;
import vn.edu.iuh.fit.backend.repositories.JobRepository;
import vn.edu.iuh.fit.backend.repositories.JobSkillRepository;
import vn.edu.iuh.fit.backend.services.JobServices;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/job")
public class JobController {

    @Autowired
    private JobServices jobServices;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private JobSkillRepository jobSkillRepository;

    @GetMapping("/openJobs/{id}")
    public String showJobsInCompany(@PathVariable("id") long id,Model model) {

        List<Job> jobs = jobRepository.findByCompany_Id(id);
        model.addAttribute("jobs",jobs);
        model.addAttribute("company",jobs.get(0).getCompany());
        return "/jobs/jobs-Company";
    }

    @GetMapping("/detailJobs")
    public String showJobDetails( @RequestParam("jobId") long id,Model model) {

        List<JobSkill> jobSkills = jobSkillRepository.findByJob_Id(id);
        Company company = jobRepository.findById(id).get().getCompany();
        model.addAttribute("company",company);
        model.addAttribute("jobSkills",jobSkills);
        model.addAttribute("job",jobSkills.get(0).getJob());
        return "/jobs/job-Skills";
    }

    @GetMapping
    public String showJobListPaging(Model model,
                                          HttpServletRequest request,
                                          @RequestParam("page") Optional<Integer> page,
                                          @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(30);

        currentPage = Math.max(currentPage, 1);
        pageSize = Math.max(pageSize, 0);

        long totalCandidates = jobRepository.count();
        int totalPages = (int) Math.ceil((double) totalCandidates / pageSize);
        currentPage = Math.min(currentPage, totalPages);

        Page<Job> jobPage = jobServices.findAll(currentPage - 1,
                pageSize, "id", "asc");

        model.addAttribute("jobPage", jobPage);
        return "jobs/jobs-paging";
    }


    @GetMapping("/add")
    public ModelAndView add(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jobs/add-jobSkills");
        return modelAndView;
    }

    @GetMapping("update")
    public ModelAndView update(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jobs/add-jobSkills");
        return modelAndView;
    }

    @GetMapping("/delete")
    public ModelAndView delete(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jobs/add-jobSkills");
        return modelAndView;
    }
}
