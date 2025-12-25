package org.learning.streams.questions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UdemyStreamsExample {
    public static void main(String[] args) {

        List<String> names = List.of("Siddu", "Raju", "Viju");

        /*1. concat the each word with comma*/
        /*via reduce it add the one extra "," at the end u can remove it if u want*/
        String combinedStringWithComma = names.stream().reduce("", (a, b) -> a + b + ",");
        System.out.println("concatenated via reduce = "+ combinedStringWithComma.substring(0, combinedStringWithComma.length()-1 ));

        /*via collect joining method*/
        String joinedViaCollect = names.stream().collect(Collectors.joining(","));
        System.out.println("concatenated via joining od collect = "+ joinedViaCollect);

        /*2. split each word into char and get the distinct letters */
        List<String> characterisedWords = names.stream().map(word -> word.split("")).flatMap(words -> Arrays.stream(words)).distinct().collect(Collectors.toList());
        System.out.println("characterized words concat = "+ characterisedWords);

        /*3. make a group of 2 where the length of the words matches*/
        /*expected combined words are = [[ABC, KLM], [DEF, KLM], [KLM, ABC], [DEF, ABC], [KLM, DEF], [ABC, DEF], [SDBM, AMDB], [AMDB, SDBM]] */
        List<String> words = List.of("KLM", "ABC", "DEF", "AMDB", "SDBM");
        List<List<String>> combinedWords = new ArrayList<>();
        words.stream().forEach(word -> words.stream().filter(incomingWord-> incomingWord.length()==word.length()).filter(incomingWord-> !incomingWord.equals(word)).forEach(newWord-> combinedWords.add(List.of(newWord, word))));
        System.out.println("combined words are = "+ combinedWords);

    }
}
