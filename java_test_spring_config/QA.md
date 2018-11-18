
## Spring Bean Lifecycle And Configuration

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

### What is the name/id of a bean if none is explicitly given?
The auto-generated id of a bean is its fully qualified class name with a number appended.
It's thus unpredictable.

### How can a bean be initialized further than by simple dependency injection?
* Use @PostConstruct from the Common Annotations JSR
* Using the `init-method` XML attribute
* Using `@Bean(initMethod="...")` with Java Configuration
* Implementing InitializatingBean and implement afterPropertiesSet (not recommended due to coupling to Spring)

### What are the Bean scopes?
* Singleton (default; like @ApplicationScoped) - only one instance
* Prototype (like @Depends) - a new instance for every injection
* Request (like @RequestScoped) - one instance per HTTP Request (in SpringMVC)
* Session (like @SessionScoped) - one instance per HTTP Session (in SpringMVC)
* global-session - one instance for each global HTTP Session (in SpringMVC)
* custom - for implementing own scopes

### Are beans lazy or eager initialized?
* Singletons are eager initialized (to quickly spot configuration failures)
* Other scopes are lazy initialized
* Prototype scoped beans that are part of a Singleton are eager initialized
* @Lazy at the @Bean/@Component can force Lazy Initialization
* @Lazy at @Autowired creates a lazy-loading proxy bean

### Can a session scoped bean be injected into a singleton scoped bean?
Only if the session scoped bean is being proxied by adding `<aop:scoped-proxy>`.

### What types of Dependency Injection does Spring support?
* Constructor Injection
* Setter Injection
* Field-based Injection

### Name the three most important ApplicationContext subclasses
* AnnotationConfig ApplicationContext
* FileSystem Xml ApplicationContext
* ClassPath Xml ApplicationContext

### Name the five "stereotype" annotations
* Component     (general candidate for auto-detection for Spring DI)
* Controller    (specialization of Component, mainly for Web controllers)
* Repository    (specialization of Component, mainly for Data Access Objects and the like)
* Service       (specialization of Component, mainly for "Business Service Facades" and the like)
* Indexed       (marker that can be applied to make any Interface eligable for Spring DI?)

### Important implementations of the ApplicationContext interface
* AnnotationConfigApplicationContext
* AnnotationConfigWebApplicationContext
* ClassPathXmlApplicationContext
* FileSystemXmlApplicationConext
* XmlWebApplicationContext

### What are the abbreviations to the XML <property> tag using Namespaces?
* p:foo="bar" for setFoo("bar");
* p:foo-ref="bar" for setFoo(bar);

### What are the abbreviations to the XML <constructor-arg> tag using Namespaces?
* c:0="bar" to set the first argument to "bar"
* c:0-ref="bar" to set the first argument to the Bean called "bar"
* c:foo="bar" to set the argument named "foo" to "bar"
* c:foo-ref="bar" to set the argument named "foo" to the Bean called "bar"

### Which annotation is used to add futher classes or XML to a @Configuration class?
* @Import to add a JavaConfig annotated class
* @ImportResource to add an XML file
* (@Resource is an annotation used by Common Annotations similar to @Inject/@Autowire)

### What is the default XML filename for @ContextConfiguration?
The name of the test class including package suffixed with "-context.xml",
i.e. "target/test-classes/de/lathspell/test/FooTest-context.xml".

### Which variants exist to create a Spring *Configuration* (not Beans)
* XML file
* Annotation based with classes tagged with @Configuration
* (Groovy file)

### Which variants exist to create a Spring *Bean* (not the wiring Configuration or Context) 
* XML `<bean>` tag
* `@Bean` on a method to create and configure an object and return it as Bean
* `@Component` or similar on a class to mark it as Bean class

### Which variants exist to mix Spring Bean definition and wiring configuration
* XML configuration 
    * can contain `<bean>` tags
    * can use `<context:component-scan base-package="..."/>` to find @Bean definitions
* Java configuration
    * uses @Bean inside of @Configuration classes
    * uses @Component
    * uses @Import to find XML files with `<bean>` tags
* (so in effect, several XML files and several @Configuration classes can be present simultaneously)
