RESThub Spring stack Tutorial
=============================

This tutorial will help you to get an overview of resthub-spring-stack.

Problem description
-------------------

During this tutorial we'll illustrate resthub usage with a sample and simple REST interface to manage tasks. Its components are:

.. image:: http://www.yuml.me/c57b8a61

Our REST interface will be mainly able to expose services to:

   - get all tasks
   - get all tasks & users paginated (with a page id and a page size)
   - get one task or user from its id
   - update one task or user from an updated object parameter
   - remove a task or user from its id
   
Each step of this tutorial is proposed as a git branch. You could checkout a step branch to get step instructions and solutions.
  
Step 1: Initialization
----------------------

**Prerequisites** :

   - Git installed : `<http://git-scm.com/downloads>`_
   - Maven installed : `<http://maven.apache.org/download.html>`_
   
**Solution** : you can find solution at `<spring/solution.html#step-1-initialization>`_

Find:
+++++

1. **Resthub2 getting started guide**
2. **Resthub2 documentation for Spring stack**
3. **Resthub2 javadoc site**
4. **List of Resthub2 underlying frameworks and corresponding documentation**

Do:
+++

1. **Generate a Resthub2 template project structure**

   You can choose which template to use : pure Java Spring server template or Server + Client template if you plan to provide a RIA client
   for your app based on `Resthub Spring Stack`
   
   Choose groupId `org.resthub.training`, artifactId `jpa-webservice`, package `org.resthub.training` and version `1.0-SNAPSHOT`.

2. **Run your project with mvn**

3. **Check on your browser that your project works and display XML representation for a sample object with id 1**.

Let's take a look at the generated project. Its structure is:

.. code-block:: text

   |--- src
   |   |--- main
   |   |    | --- java
   |   |    |     | --- org
   |   |    |           | --- resthub
   |   |    |                 | --- training
   |   |    |                       | --- controller
   |   |    |                       |     | --- SampleController.java
   |   |    |                       | --- model
   |   |    |                       |     | --- Sample.java
   |   |    |                       | --- repository
   |   |    |                       |     | --- SampleRepository.java
   |   |    |                       | --- SampleInitializer.java
   |   |    |                       | --- WebAppConfigurer.java
   |   |    |                       | --- WebAppInitializer.java
   |   |    | --- resources
   |   |          | --- applicationContext.xml
   |   |          | --- database.properties
   |   |          | --- logback.xml
   |   |--- test
   |        | --- java
   |              | --- org
   |                    | --- resthub
   |                          | --- training
   | --- pom.xml
   
``src/main/java`` contains all java sources under the package ``org.resthub.training`` as specified during archetype generation.
This package contains the following sub packages and files: 

- **controller**: This package contains all your application controllers, i.e. your web API. In the generated sample, the archetype provided
  you a SampleController that simply extend ``RepositoryBasedRestController`` and apply its behaviour to the *Sample* model and
  *SampleRepository*: ``SampleController extends RepositoryBasedRestController<Sample, Long, SampleRepository>``. This generic ``RepositoryBasedRestController``
  provides basic CRUD functionalities: see Resthub2 documentation for details.
- **model**: This package contains all you domain models.
- **repository**: This package contains your repositories, i.e. classes that provide methods to manipulate, persist and retrieve your objects from your JPA
  manager (and so your database). In the generated sample, the archetype provided you a SampleRepository that simply extend Spring-Data ``JpaRepository``.
  for behaviour, see Spring-Data JPA documentation for details.
- **initializers**: initializers are special classes executed at application startup to setup your webapp. ``WebappInitializer`` load your spring application contexts, setup filters, etc. (all actions that you previously configured in your web.xml). The archetype provided you a ``SampleInitializer`` to setup sepcific domain model initializations such as data creation.
- **configurers**: configurers are using Spring Java Config to allow you define you Spring beans and your Spring configuration. They contains the same information than your old applicationContext.xml files, but described with Java code in the ``WebAppConfigurer`` class.
- ``src/main/resources`` contains all non java source files and, in particular, your spring application context (kept for some parts that still need an applicationContext.xml file like Spring Security), your database configuration file and you logging configuration.
- ``src/test/`` contains, obviously, all you test related files and has the same structure as src/main (i.e. *java* and *resources*).

