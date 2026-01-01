package org.learning.interview.generalJavaQuestions.comparatorAndComparable.utils;

import lombok.Builder;

@Builder
public record Student(int id, String name, int age) implements Comparable<Student> {
    @Override
    public int compareTo(Student o) {
        return Integer.compare(this.age, o.age);
    }
}
