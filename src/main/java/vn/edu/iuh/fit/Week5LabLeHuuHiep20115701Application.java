package vn.edu.iuh.fit;

import com.neovisionaries.i18n.CountryCode;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import vn.edu.iuh.fit.backend.enums.SkillLevel;
import vn.edu.iuh.fit.backend.enums.SkillType;
import vn.edu.iuh.fit.backend.ids.JobSkillID;
import vn.edu.iuh.fit.backend.models.*;
import vn.edu.iuh.fit.backend.repositories.*;

import java.time.LocalDate;
import java.util.Random;

@SpringBootApplication
public class Week5LabLeHuuHiep20115701Application {

    public static void main(String[] args) {
        SpringApplication.run(Week5LabLeHuuHiep20115701Application.class, args);
    }

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CandidateSkillRepository candidateSkillRepository;

    @Autowired
    private JobSkillRepository jobSkillRepository;


//    @Bean
    CommandLineRunner initData() {
        return args -> {
            Random random = new Random();

            //Tạo Skill
            for (int i = 1; i <= 20; i++) {
                int iSkill = random.nextInt(0, 3);
                Skill skill = new Skill("Skill #" + i, SkillType.fromValue(iSkill), "");
                skillRepository.save(skill);
            }

//            //Tạo Address và Candidate
            for (int i = 1; i < 888; i++) {
                Address address = new Address(random.nextInt(1, 1000) + "", "Ng Van Bao", "Ho Chi Minh", random.nextInt(70000, 80000) + "", CountryCode.VN);
                addressRepository.save(address);

                Candidate candidate = new Candidate("Name #" + i,
                        LocalDate.of(1998, random.nextInt(1, 13), random.nextInt(1, 29)),
                        address,
                        random.nextLong(1111111111L, 9999999999L) + "",
                        "email_" + i + "@gmail.com");
                candidateRepository.save(candidate);
            }

            // tạo CandidateSkill
            for (int i = 1; i < 888; i++) {
                //tạo 3 CandidateSkill cho mỗi Candidate
                long iSkill = random.nextInt(1,18);
                for (int j = 1; j <= 3; j++) {
                    int iLevel = random.nextInt(1,6);
                    CandidateSkill candidateSkill = new CandidateSkill(new Skill(iSkill++),new Candidate((long) i),SkillLevel.fromValue(iLevel),"");
                    candidateSkillRepository.save(candidateSkill);
                }
            }

//            //Tạo company
            int jJob = 0;
            for (int i = 1; i <= 10; i++) {
                Address address = new Address(random.nextInt(1, 1000) + "", "Ng Van Bao", "Ho Chi Minh", random.nextInt(70000, 80000) + "", CountryCode.VN);
                addressRepository.save(address);

                Company company = new Company("Cty #" + i, address, "urlWeb #" + i, random.nextLong(1111111111L, 9999999999L) + "", "#" + i + "@gmail.com", "");
                companyRepository.save(company);

                //tạo 3 job cho mỗi company
                for (int j = 1; j <= 3; j++) {
                    Job job = new Job("Job #" + ++jJob, "", company);
                    jobRepository.save(job);

                    //tạo  3 JobSkill cho mỗi job
                    for (int k = 1; k <= 3; k++) {
                        long jSkill = random.nextLong(1, 21);
                        int jLevel = random.nextInt(1, 6);
                        JobSkill jobSkill = new JobSkill(new Skill(jSkill), job, SkillLevel.fromValue(jLevel), "");
                        jobSkillRepository.save(jobSkill);
                    }
                }
            }
        };
    }


}
