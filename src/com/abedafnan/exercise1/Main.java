package com.abedafnan.exercise1;

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class Main {

    public static void main(String[] args) {
        /**
         * a) Write a lambda that can be used in place of the following anonymous inner class:
         * new IntConsumer() {
         *   public void accept(int value) {
         *     System.out.printf("%d ", value);
         *   }
         * }
         */
        Consumer<Integer> consumer = (value) -> System.out.printf("%d ", value);
        consumer.accept(5);

        /**
         * b) Write a method reference that can be used in place of the following lambda:
         * (String s) -> {return s.toUpperCase();}
         */
        UnaryOperator<String> upperCase = String::toUpperCase;
        System.out.println(upperCase.apply("a"));

        /**
         * c) Write a no-argument lambda that implicitly returns the String "Welcome to lambdas!"
         */
        Supplier<String> supplier = () -> "Welcome to lambdas!";
        System.out.println(supplier.get());

        /**
         * d) Write a method reference for Math method sqrt
         */
        UnaryOperator<Double> sqrt = Math::sqrt;
        System.out.println(sqrt.apply(100.0));

        /**
         * e) Create a one-parameter lambda that returns the cube of its argument
         */
        UnaryOperator<Integer> cube = (num) -> num * num * num;
        System.out.println(cube.apply(5));
    }

}
