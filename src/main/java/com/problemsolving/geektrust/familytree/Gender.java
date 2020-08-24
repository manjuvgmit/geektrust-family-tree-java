package com.problemsolving.geektrust.familytree;

import java.util.Arrays;
import java.util.Objects;

public enum Gender {
    Male, Female;

    public boolean isMale() {
        return this == Male;
    }

    public boolean isFemale() {
        return this == Female;
    }

    public static Gender fromValue(String name) {
        return Arrays.stream(values()).filter(gender -> Objects.equals(gender.name(), name)).findFirst().orElse(null);
    }
}
