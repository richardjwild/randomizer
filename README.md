# It creates random values for use in automated tests!

There are two situations I commonly encounter. To illustrate both, let's use the trivial example of multiplying two
numbers. I might write a test like this:

```java
@Test
public void productOfTwoNumbers() {
    int operandOne = 1;
    int operandTwo = 2;
    int actual = new Multiplier().multiply(operandOne, operandTwo);
    int expected = operandOne * operandTwo;
    Assert.assertEquals(expected, actual);
}
```

Now I could implement the `multiply` method in the logical way and the test will pass, but how do I know that it will
pass for other argument values? Especially if I want to keep my test ignorant of the implementation, which I always
prefer to do. Short of putting my test in a pair of loops that exhaustively tests all possible inputs - which is
already impractical - there is no way to verify this.

The second situation sometimes crops up when pair programming with someone who follows the rule "do the simplest thing
possible to make the tests pass" a little too literally. For the example of the multiplier, my partner could write some
code that would make the test pass like this:

```java
public class Multiplier {

    public int multiply(int arg1, int arg2) {
        return 2;
    }
}```

If your partner does that once, then fine. So you might write a second test to tease out some actual functionality:

```java
@Test
public void productOfTwoNumbers() {
    int operandOne = 1;
    int operandTwo = 2;
    int actual = new Multiplier().multiply(operandOne, operandTwo);
    int expected = operandOne * operandTwo;
    Assert.assertEquals(expected, actual);
}

@Test
public void productOfTwoOtherNumbers() {
    int operandOne = 3;
    int operandTwo = 4;
    int actual = new Multiplier().multiply(operandOne, operandTwo);
    int expected = operandOne * operandTwo;
    Assert.assertEquals(expected, actual);
}
```

Now if your partner is a smartass, they might amend the implementation like this:

```java
public class Multiplier {

    public int multiply(int arg1, int arg2) {
        if (arg1 == 3)
            return 12;
        return 2;
    }
```

The tests will pass but this is clearly getting us nowhere.