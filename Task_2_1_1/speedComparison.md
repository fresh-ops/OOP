# Методология замеров
## Формирование тестовых данных
Тестовые данные формируются динамически для каждого теста следующим методом:
```java
private static final NUMBERS_IN_TEST = 100;
// ...
private static int[] setUpTestData() {
    PrimeChecker checker = (numbers) -> false;
    var random = new Random();
    var testData = new ArrayList<Integer>();

    for (var i = 2_000_000_000; i < Integer.MAX_VALUE; i++) {
        if (checker.checkIfPrime(i) && random.nextBoolean()) {
            testData.add(i);
            if (testData.size() == NUMBERS_IN_TEST) {
                break;
            }
        }
    }
}
```

Для проверки ускорения используем такие тестовые данные, которые потребуют максимально долгого исполнения, то есть в 
данных не должно быть раннего выхода(все числа простые) и содержат числа достаточно большие, чтобы увеличить время 
исполнения. Для этого выбираем простые числа близкие к границе целочисленного типа
## Способ замера
Чтобы данные были более релевантными, каждый тест будем делать несколько замеров и брать среднее:
```java
private static final int TEST_COUNT = 10;
// ...

private static double averageTime(Runnable target, int numberOfTests) {
    double totalTime = 0;

    for (var i = 0; i < numberOfTests; i++) {
        totalTime += measureExecutionTime(target);
    }

    return totalTime / numberOfTests;
}

private static long measureExecutionTime(Runnable target) {
    var startTime = System.nanoTime();
    target.run();
    var endTime = System.nanoTime();

    return endTime - startTime;
}
```
Для старта используем следующую конструкцию:
```java
var checker = ParallelStreamPrimeChecker();
var duration = averageTime(
    () -> checker.checkIfContainsNonPrime(numbers),
    TEST_COUNT
);
```