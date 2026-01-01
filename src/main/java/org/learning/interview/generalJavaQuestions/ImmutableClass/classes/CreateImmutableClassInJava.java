package org.learning.interview.generalJavaQuestions.ImmutableClass.classes;

import java.util.ArrayList;
import java.util.List;

/*making class as final for avoiding inheritance*/
public final class CreateImmutableClassInJava {

    /*all the fields in the class will be private and final to avoid internal reinitialization of the field and external accessibility*/
    private final String name;

    /*Reference object*/
    private final ReferenceObject referenceObject;
    private final List<String> friends;

    /*initialize all fields inside the constructor*/
     public CreateImmutableClassInJava(String name, ReferenceObject referenceObject, List<String> friends){
        this.name = name;
        ReferenceObject referenceObject1 = new ReferenceObject();
        referenceObject1.setRefName(referenceObject.getRefName());
        this.referenceObject = referenceObject1; /*copied things from passed object and created new one and assigned that to original*/
        this.friends = new ArrayList<>(friends); /*defencive copy if anybody changed the passed ref object later it won't change this this class field ref*/
    }

    /*for all the fields i have only the getters*/
    public String getName() {
        return name;
    }

    public ReferenceObject getReferenceObject() {
        ReferenceObject referenceObject1 = new ReferenceObject();
        referenceObject1.setRefName(referenceObject.getRefName());
        return referenceObject1;/*defensive copy if any body get it and try to change it won't change this class ref field*/
    }

    public List<String> getFriends() {
        return new ArrayList<>(friends);
    }

    @Override
    public String toString() {
        return "CreateImmutableClassInJava{" +
                "name='" + name + '\'' +
                ", referenceObject=" + referenceObject +
                ", friends=" + friends +
                '}';
    }
}
