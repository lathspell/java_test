Exploring the mysteries of...

Spring Aspect Oriented Programming
==================================

Package de.lathspell.test2
--------------------------

Situation:

The Test2 class uses an instance of MyService to do some stuff.

Task:

Now we want to log all input parameters to any method of MyService without modifying the class.

Solution:

The Aspect `DebugAspect` is created and advised to intercept any calls to public methods of MyService.
During interception it stores all parameters in a list of the `DebugLog` component. That list can then
be verified in the test.
  
Additonally:

* MyService does not implement an Interface so a CGLIB Proxy has to be used
* MyOtherService is an Interface that so a JDK-Proxy can be used and is used

Package de.lathspell.test3
---------------------------

Like test2 but with pure XML.

Package de.lathspell.test4
--------------------------

Advice with combined pointcut.

Package de.lathspell.test5
--------------------------

@Around throwing and preventing exceptions and fiddling with the result. 