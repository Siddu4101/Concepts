package org.example.stream.groupby;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class GroupByTestsInCollectors {
    public static void main(String[] args) {
        List<User> users = List.of(new User.Builder()
                        .setId(10)
                        .setAge("28")
                        .setName("name")
                        .build(), new User.Builder()
                        .setId(1)
                        .setName("John Doe")
                        .setAge("30")
                        .build(),
                new User.Builder()
                        .setId(2)
                        .setName("Jane Smith")
                        .setAge("25")
                        .build(),
                new User.Builder()
                        .setId(3)
                        .setName("Alice Johnson")
                        .setAge("25")
                        .build(),
                new User.Builder()
                        .setId(4)
                        .setName("Alice Johnson")
                        .setAge("25")
                        .build()
        );

        Map<Integer, Set<User>> collect = users.stream().collect(Collectors.groupingBy(User::getId, TreeMap::new, Collectors.toSet()));
        System.out.println(collect);

        System.out.println(users.stream().collect(Collectors.groupingBy(User::getAge, Collectors.counting())));
    }


}
