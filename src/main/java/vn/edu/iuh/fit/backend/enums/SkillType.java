package vn.edu.iuh.fit.backend.enums;

public enum SkillType {
    UNSPECIFIC(0),

    TECHNICAL_SKILL(1),

    SOFT_SKILL(2);

    private int value;

    SkillType(int value) {
      this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SkillType fromValue(int value) {
        for (SkillType type : SkillType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Không tìm thấy SkillType với giá trị " + value);
    }
}
