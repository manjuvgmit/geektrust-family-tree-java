package com.problemsolving.geektrust.familytree;

import com.google.common.collect.Sets;

import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

public class FamilyTree {
    public static final String CHILD_ADDITION_SUCCEEDED = "CHILD_ADDITION_SUCCEEDED";
    private final FamilyTreeEntry rootMember;
    private transient int size = 0;

    public FamilyTree(String rootMember, String spouse, Gender gender) {
        this.rootMember = new FamilyTreeEntry(rootMember, gender, spouse, null);
    }

    public String addMember(String mother, String member, Gender gender) {
        return addMember(mother, member, gender, null);
    }

    public String addMember(String mother, String member, Gender gender, String spouse) {
        checkAndThrowIfMandatoryFieldsMissing(mother, member, gender);
        FamilyTreeEntry newEntry = new FamilyTreeEntry(member, gender, spouse);
        FamilyTreeEntry parent = getParent(mother);
        if (Objects.isNull(parent)) {
            return new StringBuilder().append("Couldn't find the parent: ").append(mother).toString();
        } else if (checkIfMemberExists(parent, member)) {
            return new StringBuilder()
                    .append("Duplicate member entry. member: ")
                    .append(member)
                    .append(", parent: ")
                    .append(mother).toString();
        } else {
            newEntry.setParent(parent);
            parent.addChildren(newEntry);
            size++;
            return CHILD_ADDITION_SUCCEEDED;
        }
    }

    private FamilyTreeEntry getParent(String mother) {
        return matchParent(rootMember, mother)
                ? rootMember
                : getAllEntries().stream()
                .filter(entry -> matchParent(entry, mother))
                .findFirst().orElse(null);
    }

    private boolean matchParent(FamilyTreeEntry entry, String mother) {
        return Objects.equals(mother, entry.getGender().isFemale() ? entry.getMember() : entry.getSpouse());
    }

    private void checkAndThrowIfMandatoryFieldsMissing(String mother, String member, Gender gender) {
        requireNonNull(mother);
        requireNonNull(member);
        requireNonNull(gender);
    }

    public Set<FamilyTreeEntry> getAllEntries() {
        return Stream.concat(Sets.newHashSet(rootMember).stream(), getAllChildren(rootMember).stream())
                .collect(Collectors.toSet());
    }

    public FamilyTreeEntry getEntryWithMatchingName(String name) {
        return Stream.concat(Sets.newHashSet(rootMember).stream(), getAllChildren(rootMember).stream())
                .filter(entry -> Objects.equals(name, entry.getMember()) || Objects.equals(name, entry.getSpouse()))
                .findFirst()
                .orElse(null);
    }

    private Set<FamilyTreeEntry> getAllChildren(FamilyTreeEntry entry) {
        return entry.getChildren().isEmpty() ? Sets.newHashSet(entry) : Stream.concat(Stream.of(entry), entry.getChildren().stream()
                .map(this::getAllChildren)
                .flatMap(Set::stream))
                .collect(Collectors.toSet());
    }

    private boolean checkIfMemberExists(FamilyTreeEntry entry, String childName) {
        return entry.getChildren().stream().anyMatch(familyTreeEntry -> Objects.equals(entry.getMember(), childName));
    }

    public FamilyTreeEntry getRootMember() {
        return rootMember;
    }

    private int size() {
        return size;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FamilyTree{");
        sb.append("rootMember=").append(rootMember);
        sb.append(", size=").append(size);
        sb.append('}');
        return sb.toString();
    }
}
