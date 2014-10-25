Example of Java EE6 web filters
===============================

# URI Filter for EWU_TREE

REST resources can be accessed with test,devel,beta and release as URI components
and a system property is set accordingly. E.g. the following request would set
"EWU_TREE" to "release":

    $ curl -i --get 'http://localhost:8080/java_test_ee6_filter/release/rest/application.wadl'

Using this, the code already deployed by NetBeans (or manually with Maven) can
be used with different databases or other configurations.

