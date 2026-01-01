package org.learning.interview.generalJavaQuestions.ImmutableClass.classes;

public class ReferenceObject {
    private String refName;

    public String getRefName() {
        return refName;
    }

    public void setRefName(String refName) {
        this.refName = refName;
    }

    @Override
    public String toString() {
        return "ReferenceObject{" +
                "refName='" + refName + '\'' +
                '}';
    }
}
