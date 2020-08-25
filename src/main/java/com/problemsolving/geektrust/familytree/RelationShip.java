package com.problemsolving.geektrust.familytree;

import com.problemsolving.geektrust.familytree.FamilyTree.Entry;

import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Collections.emptySet;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toSet;

public enum RelationShip {
    PATERNAL_UNCLE(entry -> entry.getParent().isMale() ? getUncles(entry, Entry::getMember) : emptySet()),
    MATERNAL_UNCLE(entry -> entry.getParent().isFemale() ? getUncles(entry, Entry::getMember) : emptySet()),
    PATERNAL_AUNT(entry -> entry.getParent().isMale() ? getAunts(entry, Entry::getMember) : emptySet()),
    MATERNAL_AUNT(entry -> entry.getParent().isFemale() ? getAunts(entry, Entry::getMember) : emptySet()),
    SISTER_IN_LAW(RelationShip::getSisterInLaws),
    BROTHER_IN_LAW(RelationShip::getBrotherInLaws),
    SON(entry -> entry.getChildren().stream()
            .filter(Entry::isMale)
            .map(Entry::getMember)
            .collect(toSet())),
    DAUGHTER(entry -> entry.getChildren().stream()
            .filter(Entry::isFemale)
            .map(Entry::getMember)
            .collect(toSet())),
    SIBLINGS(entry -> getSiblings(entry, Entry::getMember));

    Function<Entry, Set<String>> finder;

    RelationShip(Function<Entry, Set<String>> finder) {
        this.finder = finder;
    }

    public Set<String> getAllOfThem(Entry entry) {
        return finder.apply(entry);
    }

    public static Set<String> getRelations(Set<Entry> entries, Predicate<Entry> relationMatcher, Function<Entry, String> nameExtractor) {
        return entries.stream()
                .filter(relationMatcher)
                .map(nameExtractor)
                .filter(Objects::nonNull)
                .collect(toSet());
    }

    public static Set<String> getAunts(Entry entry, Function<Entry, String> nameExtractor) {
        return getUnclesAndAunts(entry, Entry::isFemale, nameExtractor);
    }

    public static Set<String> getUncles(Entry entry, Function<Entry, String> nameExtractor) {
        return getUnclesAndAunts(entry, Entry::isMale, nameExtractor);
    }

    public static Set<String> getUnclesAndAunts(Entry entry, Predicate<Entry> predicate, Function<Entry, String> nameExtractor) {
        return getRelations(ofNullable(entry.getParent())
                        .map(Entry::getParent)
                        .map(Entry::getChildren)
                        .map(entries -> entries.stream().filter(e -> !Objects.equals(
                                e.getMember(), entry.getParent().getMember()))
                                .collect(toSet()))
                        .orElse(emptySet()),
                predicate,
                nameExtractor);
    }

    public static Set<String> getSisterInLaws(Entry entry) {
        return getBrotherOrSisterInLaws(entry, Entry::isMale, Entry::getSpouse);
    }

    public static Set<String> getBrotherInLaws(Entry entry) {
        return getBrotherOrSisterInLaws(entry, Entry::isFemale, Entry::getSpouse);
    }

    public static Set<String> getBrotherOrSisterInLaws(Entry entry, Predicate<Entry> predicate, Function<Entry, String> nameExtractor) {
        return getRelations(entry.getParent().getChildren().stream().filter(e -> !Objects.equals(e.getMember(), entry.getMember())).collect(toSet()),
                predicate, nameExtractor);
    }

    public static Set<String> getSiblings(Entry entry, Function<Entry, String> nameExtractor) {
        return getRelations(entry.getParent().getChildren(), e -> !Objects.equals(e.getMember(), entry.getMember()), nameExtractor);
    }
}
