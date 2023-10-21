package vn.edu.iuh.fit.backend.models;

import jakarta.persistence.*;
import lombok.*;
import vn.edu.iuh.fit.backend.enums.SkillType;

import java.util.List;

//Read skill list at: https://www.yourdictionary.com/articles/examples-skills-list
//API: https://github.com/workforce-data-initiative/skills-api/wiki/API-Overview#swagger-ui-test-client
@Entity
@Table(name = "skill")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id")
    private long id;
    @Column(name = "skill_name", nullable = false, length = 150)
    private String skillName;
    @Column(name = "skill_type", nullable = false)
    private SkillType type;
    @Column(name = "skill_desc", nullable = false, length = 300)
    private String skillDescription;

    @OneToMany(mappedBy = "skill", fetch = FetchType.LAZY)
    private List<JobSkill>jobSkills;

    public Skill(long id) {
        this.id = id;
    }

    public Skill(String skillName, SkillType type, String skillDescription) {
        this.skillName = skillName;
        this.type = type;
        this.skillDescription = skillDescription;
    }

    @Override
    public String toString() {
        return "Skill{" +
                "id=" + id +
                ", skillName='" + skillName + '\'' +
                ", type=" + type +
                ", skillDescription='" + skillDescription + '\'' +
                '}';
    }

}
