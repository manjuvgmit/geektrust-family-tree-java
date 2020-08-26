package com.problemsolving.geektrust.familytree;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import static com.google.common.collect.ImmutableList.of;
import static com.problemsolving.geektrust.familytree.FamilyTree.*;
import static com.problemsolving.geektrust.familytree.PlanetLengaburu.INVALID_PARAMETERS;
import static org.junit.Assert.assertEquals;

@RunWith(BlockJUnit4ClassRunner.class)
public class FamilyTreeTest {

    private PlanetLengaburu planetLengaburu = new PlanetLengaburu();

    public FamilyTreeTest() throws Exception {}

    @Test
    public void testAddMemberValidScenario() throws Exception {
        assertEquals(of(CHILD_ADDITION_SUCCEEDED), planetLengaburu.startFromListOfArgs(of(
                "ADD_CHILD Chitra Aria Female"
        )));
    }

    @Test
    public void testAddMemberInValidScenarioMissingMother() throws Exception {
        assertEquals(of(PERSON_NOT_FOUND), planetLengaburu.startFromListOfArgs(of(
                "ADD_CHILD Ramya Soumya Female"
        )));
    }

    @Test
    public void testAddMemberInValidScenarioWrongGender() throws Exception {
        assertEquals(of(INVALID_PARAMETERS), planetLengaburu.startFromListOfArgs(of(
                "ADD_CHILD Ramya Soumya BlaBla"
        )));
    }

    @Test
    public void testAddMemberInValidScenarioEmptyMother() throws Exception {
        assertEquals(of(INVALID_PARAMETERS), planetLengaburu.startFromListOfArgs(of(
                "ADD_CHILD  Soumya Female"
        )));
    }

    @Test
    public void testAddMemberInValidScenarioEmptyName() throws Exception {
        assertEquals(of(INVALID_PARAMETERS), planetLengaburu.startFromListOfArgs(of(
                "ADD_CHILD Soumya  Female"
        )));
    }

    @Test
    public void testAddMemberInValidScenarioEmptyGender() throws Exception {
        assertEquals(of(INVALID_PARAMETERS), planetLengaburu.startFromListOfArgs(of(
                "ADD_CHILD Ramya Soumya"
        )));
    }

    @Test
    public void testRelation() throws Exception {
        assertEquals(of("NONE"), planetLengaburu.startFromListOfArgs(of(
                "GET_RELATIONSHIP Lavnya Maternal-Aunt"
        )));
    }

    @Test
    public void testAddMemberAndRelationCombine() throws Exception {
        assertEquals(of(CHILD_ADDITION_SUCCEEDED, "Aria", "Jnki Ahit"), planetLengaburu.startFromListOfArgs(of(
                "ADD_CHILD Chitra Aria Female",
                "GET_RELATIONSHIP Lavnya Maternal-Aunt",
                "GET_RELATIONSHIP Aria Siblings"
        )));
    }

    @Test
    public void testAddMemberWithFatherName() throws Exception {
        assertEquals(of(CHILD_ADDITION_FAILED), planetLengaburu.startFromListOfArgs(of(
                "ADD_CHILD Asva Vani Female"
        )));
    }

    @Test
    public void testAddMemberAndRelationWithWrongNames() throws Exception {
        assertEquals(of(PERSON_NOT_FOUND, PERSON_NOT_FOUND), planetLengaburu.startFromListOfArgs(of(
                "ADD_CHILD Pjali Srutak Male",
                "GET_RELATIONSHIP Pjali Son"
        )));
    }

    @Test
    public void testCase01() throws Exception {
        assertEquals(of(CHILD_ADDITION_SUCCEEDED, "Satya", "Ahit"), planetLengaburu.startFromListOfArgs(of(
                "ADD_CHILD Chitra Aria Female",
                "GET_RELATIONSHIP Aria Paternal-Aunt",
                "GET_RELATIONSHIP Lavnya Maternal-Uncle"
        )));
    }

    @Test
    public void testCase02() throws Exception {
        assertEquals(of(CHILD_ADDITION_SUCCEEDED, "Atya Yaya"), planetLengaburu.startFromListOfArgs(of(
                "ADD_CHILD Satya Yaya Female",
                "GET_RELATIONSHIP Satvy Sister-In-Law"
        )));
    }

    @Test
    public void testCase03() throws Exception {
        assertEquals(of("Satvy Krpi"), planetLengaburu.startFromListOfArgs(of(
                "GET_RELATIONSHIP Atya Sister-In-Law"
        )));
    }

}
