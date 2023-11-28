package vn.edu.iuh.fit.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.backend.models.Skill;
import vn.edu.iuh.fit.backend.repositories.SkillRepository;

import java.util.Collections;
import java.util.List;

@Service
public class SkillServices {
    @Autowired
    private SkillRepository skillRepository;

    public Page<Skill> findAll(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        return skillRepository.findAll(pageable);
    }

    public Page<Skill> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Skill> list;
        List<Skill> skills = skillRepository.findAll();

        if (skills.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, skills.size());
            list = skills.subList(startItem, toIndex);
        }

        Page<Skill> skillPage
                = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), skills.size());

        return skillPage;
    }

}
