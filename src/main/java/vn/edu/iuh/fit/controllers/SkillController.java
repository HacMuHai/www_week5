package vn.edu.iuh.fit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import vn.edu.iuh.fit.backend.models.Company;
import vn.edu.iuh.fit.backend.models.Job;
import vn.edu.iuh.fit.backend.models.JobSkill;
import vn.edu.iuh.fit.backend.models.Skill;
import vn.edu.iuh.fit.backend.repositories.JobRepository;
import vn.edu.iuh.fit.backend.repositories.JobSkillRepository;
import vn.edu.iuh.fit.backend.repositories.SkillRepository;
import vn.edu.iuh.fit.backend.services.JobServices;
import vn.edu.iuh.fit.backend.services.SkillServices;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/skill")
public class SkillController {

    @Autowired
    private SkillServices skillServices;
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private JobSkillRepository jobSkillRepository;



    @GetMapping
    public String showSkillListPaging(Model model,
                                          @RequestParam("page") Optional<Integer> page,
                                          @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);

        currentPage = Math.max(currentPage, 1);
        pageSize = Math.max(pageSize, 0);

        long totalCandidates = skillRepository.count();
        int totalPages = (int) Math.ceil((double) totalCandidates / pageSize);
        currentPage = Math.min(currentPage, totalPages);

        Page<Skill> skillPage = skillServices.findAll(currentPage - 1,
                pageSize, "id", "asc");

        model.addAttribute("skillPage", skillPage);
        return "skills/skills-paging";
    }


    @GetMapping("/add")
    public ModelAndView add(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("skills/add-skill");
        return modelAndView;
    }

    @GetMapping("update")
    public ModelAndView update(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("skills/add-skill");
        return modelAndView;
    }

    @GetMapping("/delete")
    public ModelAndView delete(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("skills/add-skill");
        return modelAndView;
    }
}
