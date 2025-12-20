package org.learning.streams.terminaloperations;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
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
        Integer sumWithIdentity = integers.stream().reduce(1, (x, y) -> x + y);
        log.info("sumWithIdentity: {}", sumWithIdentity);

        log.info("4. collect");
        log.info("converts stream to another form like list, set, map etc...");
        log.info("there are many other ways to use this collect we will see later");
        List<Integer> doubledList = integers.stream().map(x -> x * 2).collect(Collectors.toList());//doubled list [2, 4, 6, 8, 10]
        log.info("doubled list {}", doubledList);
        Map<Integer, Long> collect = integers.stream().collect(Collectors.groupingBy(x -> x, Collectors.counting()));// {1=1, 2=1, 3=1, 4=1, 5=1}
        log.info("the count of each element in the list is {}", collect);

        log.info("5. matching short circuits");
        log.info("a. anyMatch: if atleast one of the element satisfy the condition");
        boolean anyMatch = integers.stream().anyMatch(x -> x > 3);
        log.info("any match {}", anyMatch);//ture

        log.info("b. allMatch: all the elements in the stream satisfy the condition");
        boolean allMatch = integers.stream().allMatch(x -> x > 3);//false
        log.info("all match {}", allMatch);

        log.info("c. noneMatch: all the elements in the stream not satisfy the condition");
        boolean noneMatch = integers.stream().noneMatch(x -> x > 3);
        log.info("none match {}", noneMatch); //false

        log.info("6. finding elements (short circuits");
        log.info("a. findFirst: gets the first element if the collection is ordered(like arraylist linked list ect..) and stream is sequential/parallel but if the collection is" +
                "not ordered like (hasMap set etc)");
        Optional<Integer> findFirst = integers.stream().findFirst();//1
        log.info("findFirst: {}", findFirst);

        log.info("b. findAny: gets any element order is not present best for parallel stream");
        Optional<Integer> findAny = integers.stream().findAny();// any element
        log.info("findAny: {}", findAny);

        log.info("7. count: gets the count of elements in that stream");
        long count = integers.stream().count();//5
        log.info("count: {}", count);

        log.info("8. min and max");
        log.info("a. min: which takes a comparator based on that returns the result");
        Optional<Integer> min = integers.stream().min(Integer::compareTo); //1 u can give custom comparator like min ==> (x,y)-> x-y and max ==> (x,y)-> y-x
        log.info("min {}", min);

        log.info("b. max: which takes a comparator based on that returns the result");
        Optional<Integer> max = integers.stream().max(Integer::compareTo); //5
        log.info("max {}", max);

        log.info("9. toArray");
        log.info("a. toArray: by default it produces the Object array");
        Object[] objectArray = integers.stream().toArray();
        log.info("objectArray: {}", Arrays.toString(objectArray));
        log.info("b. toArray with constructor for typecasting");
        Integer[] integerArray = integers.stream().toArray(Integer[]::new);
        log.info("integerArray: {}", Arrays.toString(integerArray));

        log.info("10. special primitive stream(IntStream, LongStream, DoubleStream) terminal operations");
        log.info("we can perform sum,min,max,count,average");
        IntStream intStream = integers.stream().mapToInt(num -> num);
        int intSum = intStream.sum();//15
        log.info("intSum: {}", intSum);
        log.info("we can also produce the required statistics on these primitive streams like");
        IntSummaryStatistics intSummaryStatistics = integers.stream().mapToInt(num -> num).summaryStatistics();
        log.info("summary: {}", intSummaryStatistics);//summary: IntSummaryStatistics{count=5, sum=15, min=1, average=3.000000, max=5}

    }
}
