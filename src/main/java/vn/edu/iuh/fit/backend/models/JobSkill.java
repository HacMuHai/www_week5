package vn.edu.iuh.fit.backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import vn.edu.iuh.fit.backend.enums.SkillLevel;
import vn.edu.iuh.fit.backend.ids.JobSkillID;

@Entity
@Table(name = "job_skill")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(JobSkillID.class)
@Getter
public class JobSkill {
    @Id
    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skill skill;

    @Id
    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    @Column(name = "skill_level", nullable = false)
    private SkillLevel skillLevel;

    @Column(name = "more_infos", length = 1000)
    private String moreInfo;

    public JobSkill(Skill skill, Job job, SkillLevel skillLevel) {
        this.skill = skill;
        this.job = job;
        this.skillLevel = skillLevel;
    }
}
