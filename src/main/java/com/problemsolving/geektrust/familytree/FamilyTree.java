package com.problemsolving.geektrust.familytree;

import com.google.common.collect.Sets;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FamilyTree {
    public static final String CHILD_ADDITION_SUCCEEDED = "CHILD_ADDITION_SUCCEEDED";
    public static final String PERSON_NOT_FOUND = "PERSON_NOT_FOUND";
    public static final String PERSON_ALREADY_EXISTS = "PERSON_ALREADY_EXISTS";
    public static final String CHILD_ADDITION_FAILED = "CHILD_ADDITION_FAILED";
    private final Entry rootMember;
    private transient int size = 0;

    public FamilyTree(String rootMember, String spouse, Gender gender) {
        this.rootMember = new Entry(rootMember, gender, spouse, null);
    }

    public String addMember(String mother, String member, Gender gender) {
        return addMember(mother, member, gender, null);
    }

    public String addMember(String mother, String member, Gender gender, String spouse) {
        MiscUtils.requiresNonNull(mother, member, gender);
        Entry newEntry = new Entry(member, gender, spouse);
        Entry parent = getEntryWithMatchingName(mother);
        if (Objects.isNull(parent)) {
            return PERSON_NOT_FOUND;
        } else if (checkIfMemberExists(parent, member)) {
            return PERSON_ALREADY_EXISTS;
        } else if (parent.isMale() && Objects.equals(parent.member, mother)) {
            return CHILD_ADDITION_FAILED;
        } else {
            newEntry.setParent(parent);
            parent.getChildren().add(newEntry);
            size++;
            return CHILD_ADDITION_SUCCEEDED;
        }
    }

    public Set<Entry> getAllEntries() {
        return Stream.concat(Sets.newHashSet(rootMember).stream(), getAllChildren(rootMember).stream())
                .collect(Collectors.toSet());
    }

    public Entry getEntryWithMatchingName(String name) {
        return Stream.concat(Sets.newHashSet(rootMember).stream(), getAllChildren(rootMember).stream())
                .filter(entry -> Objects.equals(name, entry.getMember()) || Objects.equals(name, entry.getSpouse()))
                .findFirst()
                .orElse(null);
    }

    private Set<Entry> getAllChildren(Entry entry) {
        return entry.getChildren().isEmpty() ? Sets.newHashSet(entry) : Stream.concat(Stream.of(entry), entry.getChildren().stream()
                .map(this::getAllChildren)
                .flatMap(Set::stream))
                .collect(Collectors.toSet());
    }

    private boolean checkIfMemberExists(Entry entry, String childName) {
        return entry.getChildren().stream().anyMatch(familyTreeEntry -> Objects.equals(entry.getMember(), childName));
    }

    public Entry getRootMember() {
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

    static class Entry {
        private String member;
        private Gender gender;
        private String spouse;
        private Entry parent;
        private Set<Entry> children;

        public Entry(String member, Gender gender, String spouse, Entry parent) {
            this.member = member;
            this.gender = gender;
            this.spouse = spouse;
            this.parent = parent;
            this.children = new LinkedHashSet<>();
        }

        public Entry(String member, Gender gender, String spouse) {
            this(member, gender, spouse, null);
        }

        public Entry(String member, Gender gender) {
            this(member, gender, null, null);
        }

        public String getMember() {
            return member;
        }

        public void setMember(String member) {
            this.member = member;
        }

        public String getSpouse() {
            return spouse;
        }

        public void setSpouse(String spouse) {
            this.spouse = spouse;
        }

        public Gender getGender() {
            return gender;
        }

        public boolean isMale() {
            return getGender().isMale();
        }

        public boolean isFemale() {
            return getGender().isFemale();
        }

        public Entry getParent() {
            return parent;
        }

        public void setParent(Entry parent) {
            this.parent = parent;
        }

        public Set<Entry> getChildren() {
            return children;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Entry that = (Entry) o;
            return member.equals(that.member);
        }

        @Override
        public int hashCode() {
            return Objects.hash(member);
        }

        @Override
        public String toString() {
            return "Entry{" +
                    "member='" + member + '\'' +
                    ", spouse='" + spouse + '\'' +
                    ", gender=" + gender +
                    ", parent=" + (parent != null ? parent.getMember() : null) +
                    ", children=" + children +
                    "}";
        }
    }

    enum Gender {
        Male, Female;

        public boolean isMale() {
            return this == Male;
        }

        public boolean isFemale() {
            return this == Female;
        }

        public static Gender fromValue(String name) {
            return Arrays.stream(values())
                    .filter(gender -> Objects.equals(gender.name(), name))
                    .findFirst()
                    .orElse(null);
        }
    }
}
