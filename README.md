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