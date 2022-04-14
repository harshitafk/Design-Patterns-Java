# Prototype design pattern — Java

![Prototype Pattern](https://github.com/harshitafk/Design-Patterns-Java/blob/bce0e08e54f0464af416735439dc6083f8827501/design-pattern-demo/src/com/designpattern/prototype/notes/prototype-design-pattern.jpg | width=500)

In some situations, creating a new object is very expensive. 
Because the application may be facing performance issues. Instead, the prototype design pattern encourages you to clone the existing object.

## Definition of Prototype pattern
> The Prototype pattern comes under the creational patterns. 
> The specialty of creational patterns provides a way to create a new instance without using the typical new keyword. 
> So, the mechanism inside the Prototype pattern is cloning objects from an existing object. Actually, 
this is not a very often used design pattern. But sometimes it is very useful.

### In short, Where to use the Prototype pattern?
If the cost for creating a new object is expensive and costs resources.

For example, assume you create an application to an online shop. 
So, you may need to create thousands of customer objects at a time. 
Normally you have to create an instance of Customer class for each customer. 
If you use the prototype pattern, you can create a single instance of customer class and clone it. 
You may improve the application preference by saving the object creation time.

## Before getting to implementation, let’s see two types of copying an object.

- Shallow Copy - When we use the default clone method, 
                it will create a shallow copy of the object. 
                It means the cloned object will have the same state as the source object. 
                In shallow cloning only memory addresses are copied, 
                if any changes are made in the field of the object, it will reflect to the cloned object as well. 
                It works similar to deep cloning in case of primitives.
                
![Shallow Copy](https://github.com/harshitafk/Design-Patterns-Java/blob/bce0e08e54f0464af416735439dc6083f8827501/design-pattern-demo/src/com/designpattern/prototype/notes/Shallow%20Cloning.png)

- Deep Copy - A deep cloning object will have the same state as shallow cloning but the original and cloned object will be fully disjoint. 
             They will be independent of each other. 
             If there is any change made to the clone object it will not be reflected in the original object or vice-versa.
             
![Deep Copy](https://github.com/harshitafk/Design-Patterns-Java/blob/bce0e08e54f0464af416735439dc6083f8827501/design-pattern-demo/src/com/designpattern/prototype/notes/Deep%20Cloning.png)             
             
## UML diagram for the previous example :
> Here you can see The Customer class has two different sub-classes called RegularCustomer and LoyalCustomer. 
> If we don’t use any design pattern, we have to create instances by new keyword.
> ex:- `RegularCustomer rc = new RegularCustomer();`
But here we can clone the existing object. Let’s see the code now.

![UML Diagram](https://github.com/harshitafk/Design-Patterns-Java/blob/bce0e08e54f0464af416735439dc6083f8827501/design-pattern-demo/src/com/designpattern/prototype/notes/UML%20Diagram.jpeg)

### Customer Class

```java
package prototype;

public abstract class Customer implements Cloneable {
    private String name;
    private String contactNo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contact) {
        this.contactNo = contact;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
```
  
The `clone()` method is a protected method. 
Therefore, we need to implement the Customer class by the Cloneable interface. 
This clone method is copying objects as shallow copies. 
If you need to make it as a deep copy, you have to override the `clone()` method.

### LoyalCustomer class

```java
package prototype;

public class LoyalCustomer extends Customer {
    private double discountRate;

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }
    
    @Override
    public String toString() {
        return "LoyalCustomer{" +
                "name=" + getName() + ", " +
                "Contact Number=" + getContactNo() + ", " +
                "discountRate=" + discountRate + "%" +
                "}";
    }
}
```

### RegularCustomer class

```java
package prototype;

public class RegularCustomer extends Customer {

    @Override
    public String toString() {
        return "LoyalCustomer{" +
                "name=" + getName() + ", " +
                "Contact Number=" + getContactNo() + ", " +
                "}";
    }
}
```

### CustomerType enum

```java
package prototype;

public enum CustomerType {
    REGULAR, LOYAL
}
```
I just implemented LoyalCustomer and RegularCustomer classes for demo purposes.
LoyolCustomer class has three fields such as name, contactNo, discountRate. 
Let’s see how to create clone objects by Registry class.

### Registry class

```java
package prototype;

import java.util.HashMap;
import java.util.Map;

public class Registry {
    private Map<CustomerType, Customer> customers = new HashMap();

    public Registry() {
        this.initialize();
    }

    public Customer getCustomer(CustomerType customerType) {
        Customer customer = null;
        try {
            customer = (Customer) customers.get(customerType).clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return customer;
    }

    private void initialize() {
        LoyalCustomer localCustomer = new LoyalCustomer();
        localCustomer.setName("Default Loyal Name");
        localCustomer.setContactNo("070 000 0000");
        localCustomer.setDiscountRate(5.0);

        RegularCustomer regularCustomer = new RegularCustomer();
        regularCustomer.setName("default Regular Customer");
        regularCustomer.setContactNo("070 000 0000");

        customers.put(CustomerType.LOYAL, localCustomer);
        customers.put(CustomerType.REGULAR, regularCustomer);

    }
}
```

This is the most important consideration in the Prototype pattern. 
Because we usually create the Customer class and its sub-classes to implement objects. 
There are four steps to implement this class.
1. First, we need to create instances of all types of customers. For that, I implemented initialize() method.
2. Then we need to implement the Registry class constructor. This initialize() method is called inside the constructor. 
   So, when we create a Registry instance, it will immediately call the initialize() method.
3. Then, we need to implement a collection of customers. 
4. It is used to map customerType and its default object. Finally, we need to implement getCustomer() method to return a clone of the particular object.

### Application class

```java
package prototype;

public class Application {
    public static void main(String[] args) {
        Registry registry = new Registry();
        LoyalCustomer c1 = (LoyalCustomer) registry.getCustomer(CustomerType.LOYAL);

        System.out.println(c1);

        LoyalCustomer c2 = (LoyalCustomer) registry.getCustomer(CustomerType.LOYAL);
        c2.setName("Tom");
        c2.setContactNo("071 123 4567");
        c2.setDiscountRate(7.0);
        System.out.println(c2);

        LoyalCustomer c3 = (LoyalCustomer) registry.getCustomer(CustomerType.LOYAL);
        System.out.println(c3);
    }
}
```

## Where to use
- When a system needs to be independent of how its objects are created, composed, and represented.
- When adding and removing objects at runtime.
- When specifying new objects by changing an existing objects structure.
- When configuring an application with classes dynamically.
- When trying to keep the number of classes in a system to a minimum.
- When state population is an expensive or exclusive process.

## Benefits
- Speeds up instantiation of large, dynamically loaded classes.
- Reduced subclassing.

## Drawbacks/consequences
- Each subclass of Prototype must implement the Clone operation.
- Could be difficult with existing classes which do not support copying.
        


             
