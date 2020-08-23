package com.problemsolving.geektrust.familytree;

import com.google.common.collect.Sets;
import com.google.common.collect.Streams;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class FamilyTree {
    private final FamilyTreeEntry rootMember;
    private transient int size = 0;
    private transient int modCount = 0;

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
        return Streams.concat(Sets.newHashSet(this.rootMember).stream(), getAllChildren(this.rootMember).stream())
                .collect(Collectors.toSet());
    }

    private Set<FamilyTreeEntry> getAllChildren(FamilyTreeEntry entry) {
        if (entry.getChildren().isEmpty()) {
            return Sets.newHashSet(entry);
        } else {
            return entry.getChildren().stream()
                    .map(this::getAllChildren)
                    .map(stream -> stream.stream())
                    .map(Set::stream)
                    .map(Streams::concat)
                    .map(stream -> Streams.concat(Sets.newHashSet(entry).stream(), stream))
                    .collect(Collectors.toSet());
//            Set<FamilyTreeEntry> allChildren = entry.getChildren().stream()
//                    .map(this::getAllChildren)
//                    .reduce((entries, entries2) -> {
//                        entries.addAll(entries2);
//                        return entries;
//                    }).get();
//            allChildren.add(entry);
//            return allChildren;
        }
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
