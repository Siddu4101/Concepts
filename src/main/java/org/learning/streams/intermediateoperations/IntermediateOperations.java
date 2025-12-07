package org.learning.streams.intermediateoperations;

import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

@Slf4j
public class IntermediateOperations {
    /*Intermediate Operations*/

    public static void main(String[] args) {
        log.info("which produces Stream as output and evaluates lazily");

        log.info("1. filter:");
        log.info("filters out the inputs based on the predicate passed");
        IntStream.range(1, 11).filter(num -> num % 2 == 0).forEach(System.out::println);

        log.info("2. map");
        log.info("transforms input data to another form for each element");
        log.info("squares the each number");
        IntStream.rangeClosed(1,10).map(num-> num * num).forEach(System.out::println);
        log.info("Note there is another function as mapToObj which does the typecasting too if u need different stream in between");
        log.info("with map it fails for convertion");
//        IntStream.rangeClosed(1,10).map(num-> String.valueOf(num)).forEach(System.out::println);//this failes bcz u have the intstream and trying to convert to string stream but map on int stream product intstream only to covert use the mapToObj
        log.info("with mapToObj it works fine");
        IntStream.rangeClosed(1,10).mapToObj(num-> String.valueOf(num)).forEach(System.out::println);

        log.info("3. flatmap");
        log.info("converts nested lists to single stream to process each item");
        List.of(List.of(1,2,3), List.of(4,5,6)).stream().flatMap(Collection::stream).forEach(System.out::println);

        log.info("4. distinct");
        log.info("gets the distinct items from the stream if it has custom items then it will use .equals() method to identify the distinct");
        IntStream.of(1,2,3,4,2,1).distinct().forEach(System.out::println);

        log.info("5. sorted");
        log.info("sorts the incoming data with natural ordering if it is non custom object for custom object we need to define the compare method in class or comparator in the sort method");
        List<Integer> integers = List.of(12, 43, 34, 23, 45, 26);
        log.info("Ascending");
        integers.stream().sorted().forEach(System.out::println);//ASC
        log.info("Descending");
        integers.stream().sorted(Comparator.reverseOrder()).forEach(System.out::println);//DESC

        log.info("for custom objects");
        log.info("Ascending based on userId");
        List<User> users = List.of(new User(23, "Sid"), new User(38, "Mohan"), new User(12, "Ram"));
        users.stream().sorted(Comparator.comparingInt(User::id)).forEach(System.out::println);
        log.info("Descending based on userId");
        users.stream().sorted(Comparator.comparingInt(User::id).reversed()).forEach(System.out::println);


        log.info("6. limit");
        log.info("it's short circuit intermediate operation to stop stream processing once the limit reached");
        integers.stream().limit(2).forEach(System.out::println); // 12 34

        log.info("7. skip");
        log.info("it is used to skip the starting few elements flow through the stream pipeline");
        integers.stream().skip(2).forEach(System.out::println); // 34 23 45 26

        log.info("8. peek");
        log.info("used for debugging purpose it don't modify the each element until it's a ref like object");
        integers.stream().filter(num->num%2==0).peek(System.out::println).count(); //12 34 26 prints the intermediate result after the filter apply
        integers.stream().filter(num->num%2==0).peek(element-> element *= 10).forEach(System.out::println); //12 34 26 still it prints same because it is not apply those change it is just for debugging

        log.info("9. mapToInt(), mapToLong(), mapToDouble()");
        log.info("used when u want to perform the operation on primitive level and product primitive stream and want to use the primitive stat functions like avg, sum etc..");
        log.info("reduces the over head of auto and unboxing");
        log.info("from IntStream");
        IntStream intStreamPrimitive = integers.stream().mapToInt(num -> num * 10);//on for each print 120 430 340 230 450 260
        intStreamPrimitive.forEach(System.out::println);
        log.info("from LongStream");
        LongStream longStreamPrimitive = integers.stream().mapToLong(num -> num * 10);//on for each print 120 430 340 230 450 260
        longStreamPrimitive.forEach(System.out::println);
        log.info("from DoubleStream");
        DoubleStream doubleStreamPrimitive = integers.stream().mapToDouble(num -> num * 10);//on for each print 120.0 430.0 340.0 230.0 450.0 260.0
        doubleStreamPrimitive.forEach(System.out::println);

        log.info("10.boxed");
        log.info("used to convert primitive to wrapper");
        Stream<Integer> boxedIntegersFromPrimitove = IntStream.of(1, 2, 3, 4).boxed();//on sout 1 2 3 4
        boxedIntegersFromPrimitove.forEach(System.out::println);

        log.info("11. takeWhile");
        log.info("This will stops the process once condition fails won't process further");
        log.info("useful for early stops and better to use only with ordered list/sequence");
        List.of(1,2,3,4,1,2).stream().takeWhile(num->num<4).forEach(System.out::println); // 1 2 3 if we use filer it will print 1 2 3 1 2

        log.info("12. dropWhile");
        log.info("This will skips the elements till the condition met once it is false it won't affect further");
        List.of(1,2,3,4,1,2).stream().dropWhile(num->num<4).forEach(System.out::println); // 4 1 2

        log.info("13. unordered");
        log.info("this is mainly useful when u r doing parallel processing to explicitly tell the process no need of order which improves the performance");
        log.info("There are some operations which respects the order but if we know order is not imp we can improve the performance by declaring it");
        log.info("this can be used in places like parallel, distinct, limit, takeWhile etc..");
        integers.stream().unordered().parallel().forEach(System.out::println);//23 26 45 12 43 34 here it not actually useful but just a usage example but u can see the result is unorderd

        log.info("14. parallel and sequential");
        log.info("parallel is used mainly for unordered result multi threaded process for time efficient and cpu bound task");
        log.info("sequential used for single threaded order maintained task");
        log.info("nut u can take advantage of the both like some operation run parallel and some in sequential mode as per requirement via using both");
        log.info("even u use parallel and the collector/terminal and stream is ordered then it still produce the ordered result like below but it is better to use forEachOrdered (gor guarantee)");
        integers.stream().parallel().map(x-> x+1).sequential().map(x->x*10).forEach(System.out::println); // the things before sequential runs parallel and after that it runs in sequential mode
    }

}
