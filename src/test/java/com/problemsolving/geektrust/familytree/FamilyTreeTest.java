package com.problemsolving.geektrust.familytree;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import static com.google.common.collect.ImmutableList.of;
import static com.problemsolving.geektrust.familytree.FamilyTree.CHILD_ADDITION_SUCCEEDED;
import static com.problemsolving.geektrust.familytree.PlanetLengaburu.INVALID_PARAMETERS;
import static org.junit.Assert.assertEquals;

@RunWith(BlockJUnit4ClassRunner.class)
public class FamilyTreeTest {

    private PlanetLengaburu planetLengaburu = new PlanetLengaburu();

    @Test
    public void testAddMemberValidScenario() throws Exception {
        assertEquals(of(CHILD_ADDITION_SUCCEEDED), planetLengaburu.startFromListOfArgs(of(
                "ADD_CHILD Chitra Aria Female"
        )));
    }

    @Test
    public void testAddMemberInValidScenarioMissingMother() throws Exception {
        assertEquals(of("Couldn't find the parent: Ramya"), planetLengaburu.startFromListOfArgs(of(
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
        assertEquals(of("Jnki Ahit"), planetLengaburu.startFromListOfArgs(of(
                "GET_RELATIONSHIP Lavanya Maternal-Aunt"
        )));
    }

    @Test
    public void testAddMemberAndRelationCombine() throws Exception {
        assertEquals(of(CHILD_ADDITION_SUCCEEDED, "Aria", "Jnki Ahit"), planetLengaburu.startFromListOfArgs(of(
                "ADD_CHILD Chitra Aria Female",
                "GET_RELATIONSHIP Lavanya Maternal-Aunt",
                "GET_RELATIONSHIP Aria Siblings"
        )));
    }
}
