# Builder Design Patterns in Java

![Builder Pattern](https://github.com/harshitafk/Design-Patterns-Java/blob/master/design-pattern-demo/src/com/designpattern/builder/notes/Builder%20pattern.png)

## Introduction:
Each design pattern comes to solve a well-known problem.

## Why Builder Design Pattern? The Problem is with :

### Complex constructor.
> Multiple constructor having combinations of `multiple parameter` with nested objects
### Large number of parameters.
> having `large number of field parameter` is also the key point to consider.
### Immutability.
> You can force the immutability to the object once you are done with creation of object.

Builder pattern is a `creational design pattern` it means its solves problem related 
to `object creation`.
Best example would be `StringBuilder, DocumentBuilder` best to see how the compex object can be created.
 
It typically solve problem in object oriented programming i.e determining what `constructor` to use. 
Often we write many constructor and it is really hard to manage them. 
The multiple constructor with combination of multiple parameters variation is called the `telescoping constructor`. 

> Builder pattern is used to create instance of very complex object having telescoping constructor in easiest way.

## Where to use the builder pattern?
When you have a simple object, this pattern is not very useful, 
but when you begin to have a more complex object and want to have a clear code 
you can use it without hesitation
For example :

```java
package main.builder;

public class Human {

    String name;
    String lastName;
    int age;

    public Human(String name, String lastName, int age) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }
}
```

A constructor with a limited number of parameters won’t be something that will make your code unreadable.
But what if now you have something like this :

```java
package main.builder;

import java.util.Date;

public class Human {

    String name;
    String lastName;
    int age;
    String height;
    String weight;
    String eyesColor;
    String hairColor;
    String birthPlace;
    Date birthDate;
    int nomberOfSibling;
    boolean married;

    public Human(String name, String lastName, int age, String height, String weight, String eyesColor, String hairColor, String birthPlace, Date birthDate, int nomberOfSibling, boolean married) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.eyesColor = eyesColor;
        this.hairColor = hairColor;
        this.birthPlace = birthPlace;
        this.birthDate = birthDate;
        this.nomberOfSibling = nomberOfSibling;
        this.married = married;
    }
}
```
The number of parameters to pass to the `constructor` will make the line of code `unreadable`.
Yes, you can still use the setter to change the value of a property after you have initialized your object. 
But what if all your fields are final because you want to render them immutable?  
Or What if some fields are mandatory and others don’t?


## Implementation of the builder pattern

## Structure
![Builder Structure](https://github.com/harshitafk/Design-Patterns-Java/blob/master/design-pattern-demo/src/com/designpattern/builder/notes/Builder%20Pattern%20Structure.png)

### Simple Example :
We want to apply the Builder Design Pattern to the example of Student.

```java
public class Student {

    private int id;
    private String name;
    private String major;
    private String mobileNumber;
    private double salary;

    // Optional Private Constructor
    pu Student(int id, String name, String major, String mobileNumber) {
        this.id = id;
        this.name = name;
        this.major = major;
        this.mobileNumber = mobileNumber;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}

```

To Apply Builder Design Patter we need a New Class to `Collect Data Variables` and 
after the user fills all parameter data we `Create Object` from Student Class so we need 
to Declare ‘SimpleStudentBuilder’ class.

## Notes :
- We need to Declare this class ‘SimpleStudentBuilder’ inner in Student Class because we need to change Student Constructor to `private` and an `inner class`, 
  we can init the new object form Student.
- We Need also Declare ‘SimpleStudentBuilder’ `Static` because we can use it from outer class and not init object from student :)


## SimpleStudentBuilder Class:

```java
// This Class inner in Student class 
public static class SimpleStudentBuilder {

    private int id;
    private String name;
    private String major;
    private String mobileNumber;
    private double salary;


    public SimpleStudentBuilder() {
    }

    public SimpleStudentBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public SimpleStudentBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public SimpleStudentBuilder setMajor(String major) {
        this.major = major;
        return this;
    }

    public SimpleStudentBuilder setMobileNumber(String mobileNumber)     {
        this.mobileNumber = mobileNumber;
        return this;
    }

    public SimpleStudentBuilder setSalary(double salary) {
        this.salary = salary;
        return this;
    }

    public Student build() {
        // TODO : Check All Require Parameters is not null
        if (id == 0)
            throw new NullPointerException("Id Can't be with out value");

        if (name == null)
            throw new NullPointerException("Name Can't be Null");

        if (major == null)
            throw new NullPointerException("Major Can't be Null");

        if (mobileNumber == null)
            throw new NullPointerException("Number Can't be Null");

        Student student = new Student(id, name, major, mobileNumber);
        student.salary = salary;

        return student;
    }
}
```
## Usage SimpleStudentBuilder :

```java
public static void main(String[] args) {
    Student student = new Student.SimpleStudentBuilder()
            .setMajor("IT")
            .setName("Geeky Bull")
            .setMobileNumber("0597190510")
            .setSalary(1000)
            .setId(32)
            .build();
}
```

## Deep In Builder Design Patter :)
Suppose in Student `Constructor` we have priority in passing parameter values for example in order [ id, name, major, mobile number ] 
If this arrangement disappears you will not be able to create an `Object` from Class Student.
so we need to answer to this situation the answer is Step Builder, 
but how we can implement this answer. keep reading :)

## Step Builder Design Patter :
to apply this idea you need
- A number of interfaces with the same number of Require variables so in 
  Our example Student class we need 4 interfaces and in addition, 
  we need one more interface for Build Method and optional variables.
- Class is implemented all the above interfaces we need captions this ‘Steps’

## The explanation for Usage StudentStepBuilder:
When we need to use this approach of builder you need to show in first time only id set method and if the user fil it, 
you can after that set Name variable value, etc.

## Time Of coding:

```java
public class StudentStepBuilder {

    public static IdFiled newBuilder() {
        return new Steps();
    }

    public static interface IdFiled {
        NameFiled setId(int id);
    }

    public static interface NameFiled {
        MajorFiled setNmae(String name);
    }

    public static interface MajorFiled {
        MobileNumber setMajor(String major);
    }

    public static interface MobileNumber {
        BuildStep setMobile(String mobile);
    }

    public static interface BuildStep {

        BuildStep setSalary(double salary);

        Student build();
    }

    private static class Steps implements IdFiled, NameFiled, MajorFiled, MobileNumber, BuildStep {

        private int id;
        private String name;
        private String major;
        private String mobileNumber;
        private double salary;


        @Override
        public NameFiled setId(int id) {
            this.id = id;
            return this;
        }

        @Override
        public MajorFiled setNmae(String name) {
            this.name = name;
            return this;
        }

        @Override
        public MobileNumber setMajor(String major) {
            this.major = major;
            return this;
        }

        @Override
        public BuildStep setSalary(double salary) {
            this.salary = salary;
            return this;
        }

        @Override
        public BuildStep setMobile(String mobile) {
            this.mobileNumber = mobile;
            return this;
        }

        @Override
        public Student build() {
            if (id == 0) {
                throw new NullPointerException("ID Can't be 0");
            }
            if (name == null) {
                throw new NullPointerException("Name can't be null");
            }


            if (major == null) {
                throw new NullPointerException("Major can't be null");
            }

            if (mobileNumber == null) {
                throw new NullPointerException("Mbile Number can't be null");
            }

            Student student = new Student(id, name, major, mobileNumber);
            student.setSalary(salary);

            return student;
        }


    }
```
## Usage StudentStepBuilder:

![Builder Pattern Structure](https://github.com/harshitafk/Design-Patterns-Java/blob/master/design-pattern-demo/src/com/designpattern/builder/notes/Builder%20Pattern%20Code.gif)

## Pros
1. Code is more `maintainable` if number of fields required to create object is more than 4 or 5.
2. Object Creation code `less error-prone` as user will know what they are passing because of explicit method call.
3. Builder pattern `increase robustness`, as only fully constructed object will be available to client.
4. You can force `immutability` to the object once its created.
## Cons
1. Builder pattern is verbose and requires code duplication as Builder needs to copy all fields from Original or Item class.


## When should you use this pattern?
1. Use this pattern when a `complex configuration process` is required to create an object and you don’t want the default configuration values to be disseminated throughout the application.
2. This pattern is useful when you have `lots of optional parameters`
3. When you have to `modify values later at any point of time`

## When should you avoid this pattern?
1. Don’t use this pattern when every data value required to create an object will be different for each instance.

## Any related patterns:
1. This pattern can be combined with the `factory method` or `abstract factory patterns`
