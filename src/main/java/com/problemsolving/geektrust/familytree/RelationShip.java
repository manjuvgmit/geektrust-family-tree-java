package com.problemsolving.geektrust.familytree;

import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Collections.emptySet;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toSet;

public enum RelationShip {
    PATERNAL_UNCLE(entry -> entry.getParent().isMale() ? RelationShip.getUncles(entry, FamilyTreeEntry::getMember) : emptySet()),
    MATERNAL_UNCLE(entry -> entry.getParent().isFemale() ? RelationShip.getUncles(entry, FamilyTreeEntry::getMember) : emptySet()),
    PATERNAL_AUNT(entry -> entry.getParent().isMale() ? RelationShip.getAunts(entry, FamilyTreeEntry::getMember) : emptySet()),
    MATERNAL_AUNT(entry -> entry.getParent().isFemale() ? RelationShip.getAunts(entry, FamilyTreeEntry::getMember) : emptySet()),
    SISTER_IN_LAW(RelationShip::getSisterInLaws),
    BROTHER_IN_LAW(RelationShip::getBrotherInLaws),
    SON(entry -> entry.getChildren().stream()
            .filter(FamilyTreeEntry::isMale)
            .map(FamilyTreeEntry::getMember)
            .collect(toSet())),
    DAUGHTER(entry -> entry.getChildren().stream()
            .filter(FamilyTreeEntry::isFemale)
            .map(FamilyTreeEntry::getMember)
            .collect(toSet())),
    SIBLINGS(entry -> RelationShip.getSiblings(entry, FamilyTreeEntry::getMember));

    Function<FamilyTreeEntry, Set<String>> finder;

    RelationShip(Function<FamilyTreeEntry, Set<String>> finder) {
        this.finder = finder;
    }

    public Set<String> getAllOfThem(FamilyTreeEntry entry) {
        return finder.apply(entry);
    }

    public static Set<String> getRelations(Set<FamilyTreeEntry> entries, Predicate<FamilyTreeEntry> relationMatcher, Function<FamilyTreeEntry, String> nameExtractor) {
        return entries.stream()
                .filter(relationMatcher)
                .map(nameExtractor)
                .filter(Objects::nonNull)
                .collect(toSet());
    }

    public static Set<String> getAunts(FamilyTreeEntry entry, Function<FamilyTreeEntry, String> nameExtractor) {
        return getUnclesAndAunts(entry, FamilyTreeEntry::isFemale, nameExtractor);
    }

    public static Set<String> getUncles(FamilyTreeEntry entry, Function<FamilyTreeEntry, String> nameExtractor) {
        return getUnclesAndAunts(entry, FamilyTreeEntry::isMale, nameExtractor);
    }

    public static Set<String> getUnclesAndAunts(FamilyTreeEntry entry, Predicate<FamilyTreeEntry> predicate, Function<FamilyTreeEntry, String> nameExtractor) {
        return getRelations(ofNullable(entry.getParent())
                        .map(FamilyTreeEntry::getParent)
                        .map(FamilyTreeEntry::getChildren)
                        .map(entries -> entries.stream().filter(e -> !Objects.equals(
                                e.getMember(), entry.getParent().getMember()))
                                .collect(toSet()))
                        .orElse(emptySet()),
                predicate,
                nameExtractor);
    }

    public static Set<String> getSisterInLaws(FamilyTreeEntry entry) {
        return getBrotherOrSisterInLaws(entry, FamilyTreeEntry::isMale, FamilyTreeEntry::getSpouse);
    }

    public static Set<String> getBrotherInLaws(FamilyTreeEntry entry) {
        return getBrotherOrSisterInLaws(entry, FamilyTreeEntry::isFemale, FamilyTreeEntry::getSpouse);
    }

    public static Set<String> getBrotherOrSisterInLaws(FamilyTreeEntry entry, Predicate<FamilyTreeEntry> predicate, Function<FamilyTreeEntry, String> nameExtractor) {
        return getRelations(entry.getParent().getChildren().stream().filter(e -> !Objects.equals(e.getMember(), entry.getMember())).collect(toSet()),
                predicate, nameExtractor);
    }

    public static Set<String> getSiblings(FamilyTreeEntry entry, Function<FamilyTreeEntry, String> nameExtractor) {
        return getRelations(entry.getParent().getChildren(), e -> !Objects.equals(e.getMember(), entry.getMember()), nameExtractor);
    }
}
