package ca.jrvs.apps.practice;

import java.lang.reflect.Array;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LambdaStreamExcImp implements LambdaStreamExc {

    //Creates a string stream
    @Override
    public Stream<String> createStrStream(String... strings) {
        return Stream.of(strings);
    }

    //Converts all strings to uppercase
    @Override
    public Stream<String> toUpperCase(String... strings) {
        return createStrStream(strings)
                .map(string -> string.toUpperCase());
    }

    //Filter strings that contain the given pattern, so return all strings that dont have that pattern
    @Override
    public Stream<String> filter(Stream<String> stringStream, String pattern) {
        return stringStream
                .filter(string -> !string.contains(pattern));
    }

    //Create an IntStream
    @Override
    public IntStream createIntStream(int[] arr) {
        return IntStream.of(arr);
    }

    //Convert a stream to list
    @Override
    public <E> List<E> toList(Stream<E> stream) {
        return stream.collect(Collectors.toList());
    }

    //Convert an IntStream to list
    @Override
    public List<Integer> toList(IntStream intStream) {
        return intStream
                .boxed().collect(Collectors.toList());
        //.boxed returns a Stream consisting of the elements of this stream, each boxed to an Integer.
        //once we have that, we can then use collect to convert to list
    }

    //Create an IntStream ranged from start to end inclusive
    @Override
    public IntStream createIntStream(int start, int end) {
        return IntStream.rangeClosed(start, end);
    }

    //Convert an intStream to a doubleStream and compute square root of each element
    @Override
    public DoubleStream squareRootIntStream(IntStream intStream) {
        return intStream
                .mapToDouble(x -> Math.sqrt(x));
    }

    //Filter all even number and return odd numbers from a intStream
    @Override
    public IntStream getOdd(IntStream intStream) {
        return intStream
                .filter(x -> x % 2 == 1);
    }

    /**
     * Return a lambda function that print a message with a prefix and suffix
     * This lambda can be useful to format logs
     * <p>
     * You will learn:
     * - functional interface http://bit.ly/2pTXRwM & http://bit.ly/33onFig
     * - lambda syntax
     * <p>
     * e.g.
     * LambdaStreamExc lse = new LambdaStreamImp();
     * Consumer<String> printer = lse.getLambdaPrinter("start>", "<end");
     * printer.accept("Message body");
     * <p>
     * sout:
     * start>Message body<end
     */
    @Override
    public Consumer<String> getLambdaPrinter(String prefix, String suffix) {
        return x -> System.out.println(prefix + x + suffix + " ");
    }

    /**
     * Print each message with a given printer
     * <p>
     * e.g.
     * String[] messages = {"a","b", "c"};
     * lse.printMessages(messages, lse.getLambdaPrinter("msg:", "!") );
     * <p>
     * sout:
     * msg:a!
     * msg:b!
     * msg:c!
     */
    @Override
    public void printMessages(String[] messages, Consumer<String> printer) {
        Stream.of(messages)
                .forEach(x -> printer.accept(x));
    }

    /**
     * Print all odd number from a intStream.
     * Please use `createIntStream` and `getLambdaPrinter` methods
     * <p>
     * e.g.
     * lse.printOdd(lse.createIntStream(0, 5), lse.getLambdaPrinter("odd number:", "!"));
     * <p>
     * sout:
     * odd number:1!
     * odd number:3!
     * odd number:5!
     */
    @Override
    public void printOdd(IntStream intStream, Consumer<String> printer) {
        getOdd(intStream)
                //First map the integers to strings, this returns a Stream of strings
                .mapToObj(x -> ((Integer) x).toString())
                //now we can accept each in the printer
                .forEach(x -> printer.accept(x));
    }

    /**
     * Square each number from the input.
     * Please write two solutions and compare difference
     * - using map and flat
     * - using flatMap
     */
    @Override
    public Stream<Integer> flatNestedInt(Stream<List<Integer>> ints) {
        return ints
                //flatMap returns a stream which is what we need to do first in order to use map
                .flatMap(x -> x.stream())
                .map(x -> x * x);
    }
}
