package com.pim.develize.enumerate;

public enum EmploymentStatusEnum {
    FULL_TIME("full-time"),
    PART_TIME("part-time"),
    OUTSOURCE("outsource"),
    INTERN("intern"),
    ETC("etc.");

    private final String text;
    EmploymentStatusEnum(final String text) {
        this.text = text;
    }
    @Override
    public String toString() {
        return text;
    }

}
