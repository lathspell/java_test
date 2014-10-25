= Approaches =

1. Database first

The database tables are manually created and the Java classes then auto-created.
The latter can happen manually via IDE, on every build using Maven or only on
certain builds using Maven profiles.

 + database schema is very good
 - Java classes have problems with ENUM and DEFAULT values
 - difficult to maintain manually applied changes to the Java classes like
   @PrePersist

2. Model first

The Java classes are created and annotated manually and the SQL DDL then
generated. Changes are applied either automatically or stored in a file.

 + model classes are very good annotated and can be documented
 o SQL schema changes should probably better applied manually after review
 - database schema probably sub-optimal and thus not suited for use with
   different applications

= Implementations =

* EclipseLink (RI)
** http://www.eclipse.org/eclipselink/documentation/2.5/jpa/extensions/annotations_ref.htm#CACFGAIC

* Apache OpenJPA
** http://ci.apache.org/projects/openjpa/2.2.x/docbook/manual.html

* JBoss Hibernate
** http://docs.jboss.org/hibernate/orm/4.2/devguide/en-US/html_single/

= Issues =

== DDL mapping ==

g = can generate SQL DDL from Java Model
r = can reverse engineer SQL DDL to Java Model

                    JPA             EclipseLink     OpenJPA     Hibernate

MySQL enum          @Enumerated+
                    @Column

DEFAULT values      @PrePersist+
                    @Column+
                    private setter

INDEXES             -               x               x           x


== DELETE ON CASCADE ==

cascede=CascadeType.ALL and orphanRemoval=true did not change the DDL.
The org.eclipse.persistence.annotations.CascadeOnDelete did the trick but
beware that it has to come after the @OneToMany not the @ManyToOne!

Maybe: https://bugs.eclipse.org/bugs/show_bug.cgi?id=413541

== table default charset ==

Charsets are outside the scope of JPA. They can be provided in the JDBC
connection string and by the DBA when doing the "CREATE DATABASE". This works
for MySQL and PostgreSQL.

