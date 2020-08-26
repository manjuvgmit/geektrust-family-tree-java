package com.problemsolving.geektrust.familytree;

import com.problemsolving.geektrust.familytree.FamilyTree.Entry;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Collections.emptySet;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toCollection;

public enum RelationShip {
    PATERNAL_UNCLE((entry, name) -> entry.getParent().isMale() ? getUncles(entry, Entry::getMember) : emptySet()),
    MATERNAL_UNCLE((entry, name) -> entry.getParent().isFemale() ? getUncles(entry, Entry::getMember) : emptySet()),
    PATERNAL_AUNT((entry, name) -> entry.getParent().isMale() ? getAunts(entry, Entry::getMember) : emptySet()),
    MATERNAL_AUNT((entry, name) -> entry.getParent().isFemale() ? getAunts(entry, Entry::getMember) : emptySet()),
    SISTER_IN_LAW((entry, name) -> getSisterInLaws(entry, Objects.equals(name, entry.getMember()))),
    BROTHER_IN_LAW((entry, name) -> getBrotherInLaws(entry, Objects.equals(name, entry.getMember()))),
    SON((entry, name) -> entry.getChildren().stream()
            .filter(Entry::isMale)
            .map(Entry::getMember)
            .collect(toCollection(LinkedHashSet::new))),
    DAUGHTER((entry, name) -> entry.getChildren().stream()
            .filter(Entry::isFemale)
            .map(Entry::getMember)
            .collect(toCollection(LinkedHashSet::new))),
    SIBLINGS((entry, name) -> getSiblings(entry, Entry::getMember));

    BiFunction<Entry, String, Set<String>> finder;

    RelationShip(BiFunction<Entry, String, Set<String>> finder) {
        this.finder = finder;
    }

    public Set<String> getAllOfThem(Entry entry, String name) {
        return finder.apply(entry, name);
    }

    public static Set<String> getRelations(Set<Entry> entries, Predicate<Entry> relationMatcher, Function<Entry, String> nameExtractor) {
        return entries.stream()
                .filter(relationMatcher)
                .map(nameExtractor)
                .filter(Objects::nonNull)
                .collect(toCollection(LinkedHashSet::new));
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
                        .map(entries -> (Set<Entry>) entries.stream()
                                .filter(e -> !Objects.equals(e.getMember(), entry.getParent().getMember()))
                                .collect(toCollection(LinkedHashSet::new)))
                        .orElse(emptySet()),
                predicate,
                nameExtractor);
    }

    public static Set<String> getSisterInLaws(Entry entry, boolean isPrimaryMember) {
        return getBrotherOrSisterInLaws(
                entry,
                isPrimaryMember ? Entry::isMale : Entry::isFemale,
                isPrimaryMember ? Entry::getSpouse : Entry::getMember
        );
    }

    public static Set<String> getBrotherInLaws(Entry entry, boolean isPrimaryMember) {
//        return getBrotherOrSisterInLaws(entry, Entry::isFemale, Entry::getSpouse);
        return getBrotherOrSisterInLaws(
                entry,
                isPrimaryMember ? Entry::isFemale : Entry::isMale,
                isPrimaryMember ? Entry::getSpouse : Entry::getMember
        );
    }

    public static Set<String> getBrotherOrSisterInLaws(Entry entry, Predicate<Entry> predicate, Function<Entry, String> nameExtractor) {
        return getRelations(entry.getParent().getChildren().stream()
                        .filter(e -> !Objects.equals(e.getMember(), entry.getMember())).collect(toCollection(LinkedHashSet::new)),
                predicate,
                nameExtractor);
    }

    public static Set<String> getSiblings(Entry entry, Function<Entry, String> nameExtractor) {
        return getRelations(entry.getParent().getChildren(), e -> !Objects.equals(e.getMember(), entry.getMember()), nameExtractor);
    }
}
