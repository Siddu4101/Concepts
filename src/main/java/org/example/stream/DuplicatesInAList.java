package org.example.stream;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class DuplicatesInAList {
    public static void main(String[] args) {
        List<Employee> employees = List.of(new Employee("Sid", 1), new Employee("Vij", 10), new Employee("Raj", 2), new Employee("Sid", 4));

        HashSet<String> uniqueNames = new HashSet<>();
        /*find the duplicate and unique users based on name*/
        /*1. Using hash set*/
        /*HasSet add method gives false if the element already present in the hasSet using this functionality*/
        List<String> duplicateNames = employees.stream().map(Employee::getName).toList().stream().filter(name -> !uniqueNames.add(name)).collect(Collectors.toList());
        log.info("duplicate names:{}" , duplicateNames);
        log.info("unique names:{}" , uniqueNames);

        /*2. Using grouping by collectors method*/

        Set<String> duplicateStrings = employees.stream().collect(Collectors.groupingBy(Employee::getName, Collectors.counting())).entrySet().stream().filter(entry -> entry.getValue() > 1).map(Map.Entry::getKey).collect(Collectors.toSet());
        Set<String> uniqueEntries = new HashSet<>(employees.stream().collect(Collectors.groupingBy(Employee::getName, Collectors.counting())).keySet());
        log.info("duplicate names:{}" , duplicateStrings);
        log.info("unique names:{}" , uniqueEntries);


        /*3. Using frequency method of Collections*/
        List<String> employeesNames = employees.stream().map(Employee::getName).toList();
        Set<String> uniqueNamesFromList = employeesNames.stream().filter(name -> Collections.frequency(employeesNames, name) >= 1).collect(Collectors.toSet());
        Set<String> duplicateNameFromList = employeesNames.stream().filter(name -> Collections.frequency(employeesNames, name) > 1).collect(Collectors.toSet());
        log.info("duplicate names:{}" , duplicateNameFromList);
        log.info("unique names:{}" , uniqueNamesFromList);
    }

    public static class Employee {
        @Getter
        private final String name;
        private final int age;

        public Employee(String name, int age) {
            this.name = name;
            this.age = age;
        }

    }
}
