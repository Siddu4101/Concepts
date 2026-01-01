package org.learning.interview.generalJavaQuestions.ImmutableClass.testClasses;

import org.learning.interview.generalJavaQuestions.ImmutableClass.classes.CreateImmutableClassInJava;
import org.learning.interview.generalJavaQuestions.ImmutableClass.classes.ReferenceObject;

import java.util.ArrayList;
import java.util.List;

public class TestImmutableClass {
    public static void main(String[] args) {
        ReferenceObject referenceObject = new ReferenceObject();
        referenceObject.setRefName("Sid");
        ArrayList<String> friends = new ArrayList<>();
        friends.add("Raj");
        friends.add("Viju");
        CreateImmutableClassInJava createImmutableClassInJava = new CreateImmutableClassInJava("Siddu",referenceObject,friends);
        System.out.println(createImmutableClassInJava);

        /*we will try to set the different value for the ref object and see is it changes (constructor test)*/
        /*if i have used the direct assignment in the constructor it will mutate the original fields of my immutable class */
       referenceObject.setRefName("Manjunath");
       friends.add("Nagaraj");
       /*but i have used defensive copy so it didn't affected the fields*/
        System.out.println(createImmutableClassInJava);

        /*now we will test for the getter*/
        /*This will also affect my ref object if i don't use the defensive copy */
        List<String> friends1 = createImmutableClassInJava.getFriends();
        ReferenceObject referenceObject1 = createImmutableClassInJava.getReferenceObject();
        friends1.add("Dyamanna");
        referenceObject1.setRefName("Basanna");
        System.out.println(createImmutableClassInJava);
    }
}
