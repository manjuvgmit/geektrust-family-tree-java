package com.problemsolving.geektrust.familytree;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(BlockJUnit4ClassRunner.class)
public class FamilyTreeTest {

    @Test
    public void testFamilyTree() {
        FamilyTree tree = new FamilyTree("Root", "RootSpouse", Gender.MALE);

        System.out.println("Tree -->> " + tree);
        assertFalse(tree.getAllEntries().isEmpty());
        assertTrue(tree.getAllEntries().size() == 1);

        tree.addMember("Root", "Child-1", "Child-1-Spouse", Gender.MALE);
        System.out.println("Tree -->> " + tree);
        assertTrue(tree.getAllEntries().size() == 2);
        assertTrue(tree.getAllEntries().size() == 2);

        tree.addMember("Root", "Child-2", "Child-2-Spouse", Gender.FEMALE);
        System.out.println("Tree -->> " + tree);
        assertTrue(tree.getAllEntries().size() == 3);
        assertTrue(tree.getAllEntries().size() == 3);

        tree.addMember("Child-1", "Child-1-Child-1", "Child-1-Child-1-Spouse", Gender.FEMALE);
        System.out.println("Tree -->> " + tree);
        assertTrue(tree.getAllEntries().size() == 4);
        assertTrue(tree.getAllEntries().size() == 4);
    }
}
