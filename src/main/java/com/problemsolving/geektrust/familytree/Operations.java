package com.problemsolving.geektrust.familytree;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;

import static com.problemsolving.geektrust.familytree.FamilyTree.PERSON_NOT_FOUND;
import static com.problemsolving.geektrust.familytree.PlanetLengaburu.INVALID_PARAMETERS;
import static com.problemsolving.geektrust.familytree.PlanetLengaburu.NONE;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public enum Operations {
    ADD_CHILD((inputList, kingShanFamilyTree) -> {
        FamilyTree.Gender gender;
        if (inputList.size() < 4 || isEmpty(inputList.get(1)) || isEmpty(inputList.get(2)) || isEmpty(inputList.get(3))
                || Objects.isNull(gender = FamilyTree.Gender.fromValue(inputList.get(3)))) {
            return INVALID_PARAMETERS;
        }
        return kingShanFamilyTree.addMember(inputList.get(1), inputList.get(2), gender, inputList.size() >= 5 ? inputList.get(4) : null);
    }),

    GET_RELATIONSHIP((inputList, kingShanFamilyTree) -> {
        FamilyTree.Entry entry = kingShanFamilyTree.getEntryWithMatchingName(inputList.get(1));
        return Objects.nonNull(entry) ? Optional.of(RelationShip.valueOf(ofNullable(inputList.get(2)).map(String::toUpperCase).map(s -> s.replaceAll("-", "_")).orElse(null)))
                .map(relationShip -> relationShip.getAllOfThem(entry,inputList.get(1)))
                .map(strings -> String.join(" ", strings))
                .filter(StringUtils::isNotEmpty)
                .orElse(NONE)
                : PERSON_NOT_FOUND;
    });

    BiFunction<List<String>, FamilyTree, String> function;

    Operations(BiFunction<List<String>, FamilyTree, String> function) {
        this.function = function;
    }

    public String apply(List<String> params, FamilyTree familyTree) {
        return function.apply(params, familyTree);
    }
}
