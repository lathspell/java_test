
## Spring Aspect Oriented Programming

### What is AOP?
Aspect Oriented Programming is a type of programming that helps with
cross-cutting concerns. It does not change the business logic but amends it by
usually executing small pieces of code just before or after certain points.

### What is a cross-cutting concern?
An aspect of software design that concerns functions/classes/modules regardless
of their purpose. Examples are:
* performance monitoring
* logging
* auditing
* internationalization
* security
* error detection and correction
* memory management
* transaction management
* caching
* synchronization
(A data access object needs access to a logger just like a web controller)

### What is an "advice"?
It's an action that takes place before/after a certain piece of code.

### Name the five advice types
* @Around
* @Before
* @AfterReturning       (like in try with no Exception)
* @AfterThrowing        (like in try..catch)
* @After                (like in try..finally)

### What is a "joint point"?
Join Points are all possible places at which an Advice can be applied.

### What is a "pointcut"?
A predicate that is used to identify those joint points where a specific
Advice should be applied. Can be a complex expression to execute the 
advice at all code occurances that matches it.

### What is an "introduction"?
When new fields or methods are declared using AOP.

### AOP Proxy
The object created by AOP that is returned instead of the originally requested
object to implement the additional functionality. Every other method call is
passed to the originally requested object that is kept in the background.

### What are the two implementation types of AOP Proxies?
* JDK dynamic proxies - they implement the same interface as the original class
* CGLIB proxies - they subclass the original class

### How is Springs @Aspect functionallity enabled in an @Configuration class?
Using @EnableAspectJAutoProxy

### Which Spring module contains AOP?
spring-aop

### How to switch from JDK dynamic proxies to CGLIB subclasses?
Using @EnableAspectJAutoProxy(proxyTargetClass = true) or whenever the
targeted class does not implement an Interface so that CGLIB must be used.

### What annotations are needed to define an aspect?
* @Aspect for the class, which must be a Spring Bean as well
* At least one method with @Around, @Before or @After

### How are Aspects declared in Spring XML?
```
    <aop:aspectj-autoproxy>
        <aop:include name="targetBean"> 
    </aop:aspectj-autoproxy>

    <aop:config>
        <aop:aspect ref="targetBean">
            <aop:before pointcut="execution(public String foo())" method="beforeTargetMethod" />
        </aop:aspect>
    </aop:config>
```

### Which expressions are understood in Pointcut definitions?
* `+` for at least one char (does not have to be the preceding one like in RegEx)
* `+` after a class name means that also subclasses are matched
* `*` for any amount of chars
* `&&`, `|| as boolean operators
