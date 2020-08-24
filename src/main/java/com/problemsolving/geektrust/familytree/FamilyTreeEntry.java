package com.problemsolving.geektrust.familytree;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class FamilyTreeEntry {
    private String member;
    private Gender gender;
    private String spouse;
    private FamilyTreeEntry parent;
    private Set<FamilyTreeEntry> children;

    public FamilyTreeEntry(String member, Gender gender, String spouse, FamilyTreeEntry parent) {
        this.member = member;
        this.gender = gender;
        this.spouse = spouse;
        this.parent = parent;
        this.children = new LinkedHashSet<>();
    }

    public FamilyTreeEntry(String member, Gender gender, FamilyTreeEntry parent) {
        this(member, gender, null, parent);
    }

    public FamilyTreeEntry(String member, Gender gender, String spouse) {
        this(member, gender, spouse, null);
    }

    public FamilyTreeEntry(String member, Gender gender) {
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

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public boolean isMale() {
        return getGender().isMale();
    }

    public boolean isFemale() {
        return getGender().isFemale();
    }

    public FamilyTreeEntry getParent() {
        return parent;
    }

    public void setParent(FamilyTreeEntry parent) {
        this.parent = parent;
    }

    public Set<FamilyTreeEntry> getChildren() {
        return children;
    }

    public void addChildren(FamilyTreeEntry child) {
        this.children.add(child);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FamilyTreeEntry that = (FamilyTreeEntry) o;
        return member.equals(that.member);
    }

    @Override
    public int hashCode() {
        return Objects.hash(member);
    }

    @Override
    public String toString() {
        return "FamilyTreeEntry{" +
                "member='" + member + '\'' +
                ", spouse='" + spouse + '\'' +
                ", gender=" + gender +
                ", parent=" + (parent != null ? parent.getMember() : null) +
                ", children=" + children +
                "}";
    }
}
