/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.problemsolving.geektrust.familytree;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.google.common.collect.ImmutableList.of;
import static com.problemsolving.geektrust.familytree.FamilyTree.Gender.Male;
import static com.problemsolving.geektrust.familytree.MiscUtils.getInput;
import static java.util.Objects.requireNonNull;

public class PlanetLengaburu {
    static Logger LOGGER = LoggerFactory.getLogger(PlanetLengaburu.class.getSimpleName());

    public static final String NONE = "NONE";
    public static final String INVALID_OPERATION = "INVALID_OPERATION";
    public static final String INVALID_PARAMETERS = "INVALID_PARAMETERS";
    public static final String FILE_PATH_AS_FIRST_ARGUMENT = "Pass in the file path as first argument.";
    public static final String MISSING_COMMANDS = "MISSING_COMMANDS";
    public static final String shanFamilyInitialStructure = "ADD_CHILD Queen_Anga Chit Male Amba,ADD_CHILD Queen_Anga Ish Male ,ADD_CHILD Queen_Anga Vich Male Lika,ADD_CHILD Queen_Anga Aras Male Chitra,ADD_CHILD Queen_Anga Satya Female Vyan,ADD_CHILD Amba Dritha Female Jaya,ADD_CHILD Amba Thritha Male ,ADD_CHILD Amba Vritha Female ,ADD_CHILD Lika Vila Female ,ADD_CHILD Lika Chika Female ,ADD_CHILD Chitra Jnki Female Arit,ADD_CHILD Chitra Ahit Male ,ADD_CHILD Satya Asva Male Satvy,ADD_CHILD Satya Vyas Male Krpi,ADD_CHILD Satya Atya Female ,ADD_CHILD Dritha Yodhan Male ,ADD_CHILD Jnki Laki Male ,ADD_CHILD Jnki Lavnya Female ,ADD_CHILD Satvy Vasa Female ,ADD_CHILD Krpi Kriya Male ,ADD_CHILD Krpi Krithi Female";

    private FamilyTree kingShanFamilyTree;

    public PlanetLengaburu() throws Exception {
        kingShanFamilyTree = new FamilyTree("King_Shan", "Queen_Anga", Male);
        Arrays.stream(shanFamilyInitialStructure.split(",")).forEach(this::processCommand);
    }

    public static void main(String[] args) throws Exception {
        requireNonNull(args, FILE_PATH_AS_FIRST_ARGUMENT);
        requireNonNull(args[0], FILE_PATH_AS_FIRST_ARGUMENT);
        System.out.println(String.join("\n", new PlanetLengaburu().startFromFilePath(args[0])));
    }

    public List<String> startFromFilePath(String inputFilePath) throws Exception {
        return startFromListOfArgs(getInput(inputFilePath));
    }

    public List<String> startFromListOfArgs(List<String> inputs) throws Exception {
        requireNonNull(inputs, MISSING_COMMANDS);
        return inputs.size() != 0 ? processCommands(inputs) : of(MISSING_COMMANDS);
    }

    private List<String> processCommands(List<String> inputs) throws Exception {
        return inputs.stream().map(this::processCommand).collect(Collectors.toList());
    }

    private String processCommand(String input) {
        List<String> params = Arrays.stream(input.split(" ")).collect(Collectors.toList());
        Operations operation;
        return params.isEmpty()
                ? INVALID_PARAMETERS
                : Objects.isNull(operation = Operations.valueOf(params.get(0)))
                    ? INVALID_OPERATION
                    : operation.apply(params, kingShanFamilyTree);
    }

}
