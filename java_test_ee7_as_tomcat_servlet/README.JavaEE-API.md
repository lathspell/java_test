Caveat: javaee-api Maven scope
------------------------------
Symptom:
    ```
    Caused by: java.lang.ClassNotFoundException: javax.faces.webapp.FacesServlet
        at org.apache.catalina.loader.WebappClassLoaderBase.loadClass(WebappClassLoaderBase.java:1333)
        at org.apache.catalina.loader.WebappClassLoaderBase.loadClass(WebappClassLoaderBase.java:1167)
        ... 54 more

    ```

Cause:
    The following Maven artefact must be set to scope=compile instead of
    the usual scope=provided as plain Servlet Containers do not provide
    the included classes. At least javax.faces.webapp.FacesServlet is required
    at runtime though.

Solution:
    ```
    <dependency>
        <!-- Java EE: API classes for all included standards -->
        <groupId>javax</groupId>
        <artifactId>javaee-api</artifactId>
        <version>7.0</version>
    </dependency>
    ```
