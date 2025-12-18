package org.learning.streams.terminaloperations;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Slf4j
public class TerminalOperations {

    public static void main(String[] args) {
        log.info("===============TERMINAL OPERATIONS===============");
        List<Integer> integers = List.of(1, 2, 3, 4, 5);
        log.info("1. Consumption:(forEach and forEachOrdered):");
        log.info("it will consume each element and u can perform operation on each element");
        log.info("forEach: doesn't guarantee the order in the parallel stream");
        integers.stream().parallel().forEach(System.out::println); //3 2 1 4 5
        log.info("forEachOrdered: still holds the order in parallel streams");
        integers.stream().parallel().forEachOrdered(System.out::println); //1 2 3 4 5

        log.info("2. toArray()");
        log.info("converts the stream to array");
        int[] array = "Siddu".chars().toArray();
        log.info("toArray with specific datatype conversion");
        String[] array1 = "Siddu".chars().mapToObj(num -> String.valueOf((char) num)).toArray(String[]::new);


        log.info("3. reduce");
        log.info("produces a single value based on the operation");
        Optional<Integer> sum = integers.stream().reduce((x, y) -> x + y);
        log.info("Sum: {}", sum.get());
        log.info("with identity (seed value to be used for the reduce operation)");
        Integer sumWithIdentity = integers.stream().reduce(1, (x,y)->x+y);
        log.info("sumWithIdentity: {}", sumWithIdentity);
        log.info("Build a large list to force parallel partitioning");
        log.info("reduce with parallel stream");


        log.info("4. collect");
        log.info("converts stream to another form like list, set, map etc...");
        log.info("there are many other ways to use this collect we will see later");
        Map<Integer, Long> collect = integers.stream().collect(Collectors.groupingBy(x -> x, Collectors.counting()));// {1=1, 2=1, 3=1, 4=1, 5=1}
        log.info("the count of each element in the list is {}", collect);

    }
}
