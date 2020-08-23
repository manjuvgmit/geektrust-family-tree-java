package com.problemsolving.geektrust.familytree;

import com.google.common.collect.Sets;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

public class FamilyTree {
    private final FamilyTreeEntry rootMember;
    private transient int size = 0;

    public FamilyTree(String rootMember, String spouse, Gender gender) {
        this.rootMember = new FamilyTreeEntry(rootMember, spouse, gender, null);
    }

    public FamilyTreeEntry addMember(String parent, String member, Gender gender) {
        return addMember(parent, member, null, gender);
    }

    public FamilyTreeEntry addMember(String parent, String member, String spouse, Gender gender) {
        requireNonNull(parent);
        requireNonNull(member);
        requireNonNull(gender);
        FamilyTreeEntry newEntry = new FamilyTreeEntry(member, spouse, gender, this.rootMember);
        if (Objects.equals(this.rootMember.getMember(), parent)) {
            if (this.rootMember.getChildren().stream().anyMatch(entry -> Objects.equals(entry.getMember(), member))) {
                new Exception("Duplicate Child.");
            } else {
                this.rootMember.addChildren(newEntry);
                size++;
            }
        } else {
            Optional<FamilyTreeEntry> parentFamilyTreeEntry = getAllEntries().stream()
                    .filter(entry -> Objects.equals(entry.getMember(), parent))
                    .findFirst();
            if (parentFamilyTreeEntry.isPresent()) {
                if (parentFamilyTreeEntry.get().getChildren().stream()
                        .anyMatch(entry -> Objects.equals(entry.getMember(), member))) {
                    new Exception("Duplicate Child.");
                } else {
                    parentFamilyTreeEntry.get().addChildren(newEntry);
                    size++;
                }
            } else {
                new Exception("Parent not found.");
            }
        }
        return newEntry;
    }

    public Set<FamilyTreeEntry> getAllEntries() {
        return Stream.concat(Sets.newHashSet(this.rootMember).stream(), getAllChildren(this.rootMember).stream())
                .collect(Collectors.toSet());
    }

    private Set<FamilyTreeEntry> getAllChildren(FamilyTreeEntry entry) {
        return entry.getChildren().isEmpty() ? Sets.newHashSet(entry) : Stream.concat(Stream.of(entry), entry.getChildren().stream()
                .map(this::getAllChildren)
                .flatMap(Set::stream))
                .collect(Collectors.toSet());
    }

    public FamilyTreeEntry getRootMember() {
        return this.rootMember;
    }

    private int size() {
        return this.size;
    }

    @Override
    public String toString() {
        return "FamilyTree{" +
                "root=" + rootMember +
                '}';
    }
}
