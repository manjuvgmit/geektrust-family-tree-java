# Langaburu King Shan Family

Solution for the Geektrust *King Shan Family* problem.

## Problem Statement

Please refer to the pdf document with problem statement. Same has been copied below.
[Geektrust_in_family_java.pdf](/docs/Geektrust_in_family_java.pdf)


## Sample input & output
Your program should take the location to the test file as parameter. Input needs to be read from a text file,
and output should be printed to the console.

### Input format to add a child

**Input Format**
```
ADD_CHILD ”Mother’s-Name" "Child's-Name" "Gender"
```

**Output Format**
```
CHILD_ADDITION_SUCCEEDED
```

### Input format to find the people belonging to a relationship:

**Input Format**
```
GET_RELATIONSHIP ”Name” “Relationship”
```

**Output Format**
```
”Name 1” “Name 2”... “Name N”
```

**Sample Input**
```
AIR ROZO
GET_RELATIONSHIP Lavnya Maternal-Aunt
GET_RELATIONSHIP Aria Siblings
```

**Sample Output**
```
CHILD_ADDITION_SUCCEEDED
Aria
Jnki Ahit
```

### Error Scenario

**Sample Input**
```
ADD_CHILD Pjali Srutak Male
GET_RELATIONSHIP Pjali Son
ADD_CHILD Asva Vani Female
GET_RELATIONSHIP Vasa Siblings
GET_RELATIONSHIP Atya Sister-In-Law
```

**Sample Output**
```
PERSON_NOT_FOUND
PERSON_NOT_FOUND
CHILD_ADDITION_FAILED
NONE
Satvy Krpi
```
<sub><sup>*Pjali does not exist in the family tree*</sup></sub><br/>
<sub><sup>*Asva is male, hence child addition failed*</sup></sub>

## How to run

### With a file from repo
```console
$ gradle clean build run --args="docs/input-01.txt" --info
```

### With a file of your own
```console
$ gradle clean build run --args="home/Downloads/sample.txt" --info
```