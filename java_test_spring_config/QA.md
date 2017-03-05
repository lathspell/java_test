### What is SpEL?
Spring Expression Language, a superset of the Unified Expression Language that is also used by JSP.

### Main features of SpEL that are not in the Unified EL
* Method invocation
* Access to properties and collections/arrays
* collection filtering
* operators

### What means "name" in an XML bean declaration?
It is or was used for web controllers before Spring 3.1 started allowing `/` in the `id` attribute.

### Can a bean have multiple "id" or "name"
Every bean has exactly one `id` that is either specified or auto-generated.
A bean can have multiple `name` that act as alias names and may even be equal to the id.

### How can a bean be initialized further than by simple dependency injection?
* Use @PostConstruct from the Common Annotations JSR
* Using the `init-method` XML attribute
* Using `@Bean(initMethod="...")` with Java Configuration
* Implementing InitializatingBean and implement afterPropertiesSet (not recommended due to coupling to Spring)


