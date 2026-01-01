package org.learning.interview.generalJavaQuestions.interfaceAbstractClassFunctionalInterface.functionalInterface;

import java.util.function.*;

public class BuiltInFunctionalInterfaces {
    public static void main(String[] args) {
          /*1. Predicate */
          Predicate<Integer> predicate = (num) -> num > 10;
          System.out.println("Predicate test for 15: " + predicate.test(15));
    
          /*2. Function */
          Function<String, String> function = (name) -> "Length of the name " + name + " is: " + name.length();
          System.out.println("Function apply for 'Hello': " + function.apply("Hello"));
    
          /*3. Consumer */
          Consumer<String> consumer = (str) -> System.out.println("Consumed: " + str);
          consumer.accept("Hello Consumer!");
    
          /*4. Supplier */
         Supplier<Double> supplier = () -> Math.random();
          System.out.println("Supplier provided value: " + supplier.get());

          /*5. BiFunction */
          BiFunction<Integer, Integer, Integer> biFunction = (a, b) -> a + b;
          System.out.println("BiFunction apply for 5 and 10: " + biFunction.apply(5, 10));

          /*6. UnaryOperator */
          UnaryOperator<Integer> unaryOperator = (num) -> num * num;
          System.out.println("UnaryOperator apply for 7: " + unaryOperator.apply(7));

          /*7. BinaryOperator */
          BinaryOperator<Integer> binaryOperator = (a, b) -> a * b;
          System.out.println("BinaryOperator apply for 4 and 5: " + binaryOperator.apply(4, 5));

    }
}