# Java Singleton Pattern and Synchronization

![Singleton Pattern](https://github.com/harshitafk/Design-Patterns-Java/blob/3177b563d0c05f9ff83d5069507f17738dbb9aba/design-pattern-demo/src/com/designpattern/singleton/notes/singleton.jpeg)

## What is Singleton pattern

> The singleton pattern is a software design pattern that is used to instantiate class to one object.
> This pattern is useful when we want to have a single instance of class across the application. For example to keep API token after successful login.

## Simple singleton pattern implementation
The following implementation is the simplest singleton pattern. 
I have declared a private static class member called `instance` and I hid the constructor of the class by declaring private constructor to prevent class
being initiated from outside. To obtain a class instance I implemented the `getInstance()` method which creates a new instance if it is not yet created, 
otherwise the method returns an existing instance.


```
public static class Singleton {
   private static Singleton instance = null;

   private Singleton() {}

   public static Singleton getInstance() {
       if (instance == null) {
           instance = new Singleton();
       }

       return instance;
   }
}
```
## What will happen if two threads call `getInstance()` method at the same time?
In that case the second thread is going to create a new instance of `Singleton` class and override the existing instance 
which has been created by the first thread. In order to improve that we are going to implement a synchronization block.

## Implement Synchronization
> Synchronization prevents a block of code to be executed by more than one thread at the same time.

To improve our Singleton pattern I have just added `synchronized` keyword in method declaration. In the following example only one 
thread can enter the `getInstance()` method and execute code at the time. 

More specifically, the first thread is going to obtain
a lock from `Singleton` class, execute the method, create an instance of Singleton class and return the monitor. 
Once the key monitor for `Singleton` class is free to use, the second thread is going to take lock from Singleton class, 
execute the method, obtain already created instance and return the key monitor.

```
public static class Singleton {
   private static Singleton instance = null;

   private Singleton() {}

   public static synchronized Singleton getInstance() {
       if (instance == null) {
           instance = new Singleton();
       }

       return instance;
   }
}
```

This implementation of singleton pattern is widely used and can be found almost in any GitHub project. However If synchronization of method `getInstance()` is not a performance critical for your application, this is simple and clean solution.

## Why `getInstance()` method is performance critical
Because we acquire a lock on the method for every read and write. Synchronization is useful just once when instance is null, after that every call returns a constant result and it is no longer useful.

## Double check singleton pattern
In the following implementation we will acquire lock on `getInstance()` method once, to create instance and after that we will read instance without acquiring the lock.

```
public static class Singleton {
   private static final Object object = new Object();
   private static Singleton instance = null;

   private Singleton() {}

   public static Singleton getInstance() {
       if (instance != null) {
           return instance;
       }

       synchronized (object) {
           if (instance == null) {
               instance = new Singleton();
           }

           return instance;
       }
   }
}
```

However this implementation is buggy if we forget to declare the variable instance as `volatile.` Without `volatile` we don't have happens before link between synchronize write and read. Volatile ensure that multiple threads read the correct instance value. When a variable is declared `volatile` we suggest the compiler to never store the value of the variable in cache memory.
So we are going to fix it by adding happens before link between synchronize write and volatile read by declaring variable instance `volatile`.

```
public static class Singleton {
   private static final Object object = new Object();
   private static volatile Singleton instance = null;

   private Singleton() {}

   public static Singleton getInstance() {
       if (instance != null) {
           return instance;
       }

       synchronized (object) {
           if (instance == null) {
               instance = new Singleton();
           }

           return instance;
       }
   }
}
```

With `volatile` we solve the problem but we still have the similar performance issue since `volatile` tells the compiler that optimisations must be avoided.

## The right way to implement Singleton pattern in java
The best method of making singleton in Java is by using `enum`. The following implementation is simple, easy to understand and clean.

```
public enum Singleton {
   INSTANCE;

   private String value;

   public void setValue(String value) {
       this.value = value;
   }

   public String getValue() {
       return value;
   }
}
```
JVM guarantees that only one instance is present and in fact is used in several places in JDK.
