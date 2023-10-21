package vn.edu.iuh.fit.backend.ids;

import vn.edu.iuh.fit.backend.models.Candidate;
import vn.edu.iuh.fit.backend.models.Skill;

import java.io.Serializable;
import java.util.Objects;

public class CandidateSkillID implements Serializable {
    private long skill;
    private long candidate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CandidateSkillID that = (CandidateSkillID) o;
        return skill == that.skill && candidate == that.candidate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(skill, candidate);
    }
}
