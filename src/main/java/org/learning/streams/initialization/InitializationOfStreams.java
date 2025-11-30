package org.learning.streams.initialization;


import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InitializationOfStreams {

    /*
    * Initialization of stream
    * */

    public static void main(String[] args) {
        log.info("1. Stream creation via collection");
        /*
        * 1. via collection
        * */
        log.info("stream from list collection");
        /*for list*/
        List<Integer> listNumbers = List.of(1, 2, 3, 4);
        listNumbers.stream().forEach(System.out::println);

        log.info("stream from map collection");
        /*for map*/
        Map<String, String> mapValues = Map.of("k1", "v1", "k2", "v2");
        mapValues.entrySet().stream()
                .forEach(entry-> log.info("Key:{} and Value:{} ",entry.getKey(), entry.getValue()));


        log.info("2. Stream creation via Array");
        /*
        * 2. From Array
        * */

        log.info("Stream from non-primitive array");
        /*a. from non-primitive array*/
        String[] names = {"Sid", "Raj", "Viju"};
        Stream<String> namesStream = Arrays.stream(names);
        namesStream.forEach(System.out::println);

        log.info("Stream from primitive array");
        /*b. from primitive array*/
        int[] numbers = new int[]{1,2,3,4};
        IntStream numberIntStream = Arrays.stream(numbers);
        numberIntStream.forEach(System.out::println);


        log.info("3. From sequence of values");
        /*
         * 3. From sequence of values
         * */

        log.info("from non-primitive values");
        /*a. from non-primitive values*/
        Stream<String> namesStreamFromValues = Stream.of("Sid", "Raj", "Viju");
        namesStreamFromValues.forEach(System.out::println);

        /*Note: for non-primitive we have a another method as ofNullable() which can process null values also safely*/

        Stream<Object> nullbaleStream = Stream.ofNullable(null); // if we use the Stream.of(null)(if it is single value then only bcz it treats it as null object array) we get NPE in the stream usage
        nullbaleStream.forEach(System.out::println);

        log.info("from primitive values");
        /*b. from primitive values*/
        IntStream intStreamFromValues = IntStream.of(1, 2, 3, 4);
        intStreamFromValues.forEach(System.out::println);

        log.info("4. Empty stream");
        /*
        * 4. Empty stream
        * */
        Stream<String> empty = Stream.empty(); //This internally handles the null so usage won't give any NPE
        empty.forEach(System.out::println);

        log.info("5. infinite stream");
        /*
        * 5. infinite streams
        * */
        log.info("infinite stream via generate");
        /*via generate*/
        Stream<Double> generateRandomDoubles = Stream.generate(Math::random).limit(10); //limit to avoid infinite run
        generateRandomDoubles.forEach(System.out::println);

        log.info("infinite stream via iterate");
        /*via iterate*/
        Stream<Integer> iterateEvenNumbers = Stream.iterate(0, x -> x + 2).limit(11); // limit to avoid infinite run
        iterateEvenNumbers.forEach(System.out::println);

        log.info("infinite stream via iterate with predicate condition to stop the generation");
        /*another version stop condition as a predicate parameter*/
        Stream<Integer> iterateWithPredicate = Stream.iterate(0, x -> x < 5, x -> x + 1); // here middle parameter is predicate for stop condition
        iterateWithPredicate.forEach(System.out::println);

        log.info("range and rangeClosed for IntStream");
        /*
        * 6. Range and RangeClosed for primitive stream generation
        * */
        log.info("range to exclude second val");
        /*a. range to exclude the second val*/
        IntStream rangeExcludingSecondVal = IntStream.range(0, 11);
        rangeExcludingSecondVal.forEach(System.out::println);

        log.info("rangeClosed to include the second val");
        /*b. rangeClosed to include the second val*/
        IntStream rangeClosedToIncludeSecondVal = IntStream.rangeClosed(1, 11);
        rangeClosedToIncludeSecondVal.forEach(System.out::println);
    }

}
