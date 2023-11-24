package vn.edu.iuh.fit.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.backend.models.Candidate;
import vn.edu.iuh.fit.backend.models.Company;
import vn.edu.iuh.fit.backend.repositories.CandidateRepository;
import vn.edu.iuh.fit.backend.repositories.CompanyRepository;

import java.util.Collections;
import java.util.List;

@Service
public class CompanyServices {
    @Autowired
    private CompanyRepository companyRepository;

    public Page<Company> findAll(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        return companyRepository.findAll(pageable);
    }

    public Page<Company> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Company> list;
        List<Company> companies = companyRepository.findAll();

        if (companies.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, companies.size());
            list = companies.subList(startItem, toIndex);
        }

        Page<Company> companyPage
                = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), companies.size());

        return companyPage;
    }
}
