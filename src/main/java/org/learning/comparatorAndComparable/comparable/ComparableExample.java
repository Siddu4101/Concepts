package org.learning.comparatorAndComparable.comparable;

import lombok.extern.slf4j.Slf4j;
import org.learning.comparatorAndComparable.utils.Student;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ComparableExample {
    public static void main(String[] args) {
        log.info("If it is a normal simple class like Integer we don't need to provide the implementation it has by default");
        List<Integer> integers = List.of(12, 3, 4, 53, 57);
        List<Integer> sortedIntegers = integers.stream().sorted().toList();
        log.info("Sorted integers {}", sortedIntegers);

        log.info("This is me v/s you which takes one input and compares it with itself and provides the result");
        log.info("This is the method which provides the NaturalOrder sorting for the class");
        log.info("This helps to provide the default sorting key when we are using the sort on this");
        log.info("This gives only one possible way of providing the implementation for the sorting on one key");
        log.info("This is within the class implementation");

        List<Student> students = new ArrayList<>();

        students.add(Student.builder().id(1).name("Sid").age(24).build());
        students.add(Student.builder().id(2).name("Raj").age(22).build());
        students.add(Student.builder().id(3).name("Viju").age(25).build());

        log.info("students before sort {}", students);

        log.info("By default it sorts the student based on there age ASC as we have implemented the comparable on Student");
        List<Student> sortedStudentBasedOnAgeAsc = students.stream().sorted().toList();
        log.info("students sorted based on age ASC {}", sortedStudentBasedOnAgeAsc);
    }
}
