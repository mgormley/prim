

# Summary

Prim is a Java primitives library akin to GNU Trove. It
differs by focusing heavily on sparse representations of vectors and
matrices.

Code generation is used to enable easy creation of many different
classes specific to a primitive type or pair of primitive types. For
example, LongDoubleSortedVector is used as the "template" class for
automatic generation of IntDoubleSortedVector, IntIntSortedVector,
LongIntSortedVector, etc.

# Using Prim

The latest public version is deployed on Maven Central:

```xml
<dependency>
    <groupId>edu.jhu.prim</groupId>
    <artifactId>prim</artifactId>
    <version>3.1.3</version>
</dependency>
```

# Development

## Dependencies

Currently, Prim has no dependencies other than JUnit, in test scope
only.

## Build

* Compile the code from the command line:

        mvn compile

* To build a single jar with all the dependencies included:

        mvn compile assembly:single

## Code Generation

The generated main code lives in src/main/java. The generated code is
also checked in and lives in src/main/generated_java. To regenerate,
simply run the following from the root directory:

	python scripts/copy_primitive_classes.py

## Eclipse setup

* Create local versions of the .project and .classpath files for Eclipse:

        mvn eclipse:eclipse

* Add M2\_REPO environment variable to
  Eclipse. http://maven.apache.org/guides/mini/guide-ide-eclipse.html
  Open the Preferences and navigate to 'Java --> Build Path -->
  Classpath Variables'. Add a new classpath variable M2\_REPO with the
  path to your local repository (e.g. ~/.m2/repository).

* To make the project Git aware, right click on the project and select Team -> Git... 
