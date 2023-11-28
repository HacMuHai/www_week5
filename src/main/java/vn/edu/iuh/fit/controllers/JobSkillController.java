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
import vn.edu.iuh.fit.backend.repositories.JobRepository;
import vn.edu.iuh.fit.backend.repositories.JobSkillRepository;
import vn.edu.iuh.fit.backend.services.JobServices;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/jobSkill")
public class JobSkillController {


    @Autowired
    private JobSkillRepository jobSkillRepository;



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
