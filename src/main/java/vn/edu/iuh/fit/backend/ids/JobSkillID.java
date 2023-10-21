package vn.edu.iuh.fit.backend.ids;


import vn.edu.iuh.fit.backend.models.Job;
import vn.edu.iuh.fit.backend.models.Skill;

import java.io.Serializable;
import java.util.Objects;

public class JobSkillID implements Serializable {
    private long skill;
    private long job;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobSkillID that = (JobSkillID) o;
        return skill == that.skill && job == that.job;
    }

    @Override
    public int hashCode() {
        return Objects.hash(skill, job);
    }
}
