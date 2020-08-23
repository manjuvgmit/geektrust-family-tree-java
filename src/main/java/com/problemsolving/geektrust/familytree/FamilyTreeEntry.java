package com.problemsolving.geektrust.familytree;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class FamilyTreeEntry {
    private String member;
    private String spouse;
    private Gender gender;
    private FamilyTreeEntry parent;
    private Set<FamilyTreeEntry> children;

    public FamilyTreeEntry(String member, String spouse, Gender gender, FamilyTreeEntry parent) {
        this.member = member;
        this.spouse = spouse;
        this.gender = gender;
        this.children = new LinkedHashSet<>();
    }

    public FamilyTreeEntry(String member, Gender gender, FamilyTreeEntry parent) {
        this(member, null, gender, parent);
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

    public Gender getSex() {
        return gender;
    }

    public void setSex(Gender gender) {
        this.gender = gender;
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
        return "\n--FamilyTreeEntry{" +
                "member='" + member + '\'' +
                ", spouse='" + spouse + '\'' +
                ", sex=" + gender +
                ", parent=" + parent +
                ", children=" + children +
                '}';
    }
}
