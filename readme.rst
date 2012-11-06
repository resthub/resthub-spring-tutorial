Resthub Spring Tutorial
=======================

Content for resthub-spring-stack tutorial.

This tutorial will help you to get an overview of resthub-spring-stack and its components and to take advantage of this framework and its tools.

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
   
Find:
+++++

1. **Resthub2 getting started guide**

    see `<http://resthub.org/2/getting-started.html>`_

2. **Resthub2 documentation for Spring stack**

    see `<http://resthub.org/2/spring-stack.html>`_

3. **Resthub2 javadoc site**

    see `<http://jenkins.pullrequest.org/job/resthub-spring-stack-master/javadoc>`_
    
4. **List of Resthub2 underlying frameworks and corresponding documentation**

    - Maven: `complete reference <http://www.sonatype.com/books/mvnref-book/reference/public-book.html>`_
    - Spring 3.1: `reference manual <http://static.springsource.org/spring/docs/3.1.x/spring-framework-reference/html>`_ and `Javadoc <http://static.springsource.org/spring/docs/3.1.x/javadoc-api/>`_
    - Spring Data: `reference <http://www.springsource.org/spring-data>`_
        - Spring Data JPA: `reference <http://static.springsource.org/spring-data/data-jpa/docs/current/reference/html/>`_ and `Javadoc <http://static.springsource.org/spring-data/data-jpa/docs/current/api/>`_
        - Spring Data MongoDB: `reference <http://static.springsource.org/spring-data/data-mongodb/docs/current/reference/html/>`_ and `Javadoc <http://static.springsource.org/spring-data/data-mongodb/docs/current/api/>`_
    - Hibernate ORM and JPA : `reference <http://docs.jboss.org/hibernate/orm/4.1/manual/en-US/html_single/>`_ and `Javadoc <http://docs.jboss.org/hibernate/orm/4.1/javadocs/>`_
    - Spring MVC 3.1: `reference <http://static.springsource.org/spring-data/data-mongodb/docs/current/reference/html/>`_
    - Spring MVC Router: `reference <https://github.com/resthub/springmvc-router>`_
    - Jackson 2.0: `reference <http://wiki.fasterxml.com/JacksonDocumentation>`_ and `Javadoc <http://wiki.fasterxml.com/JacksonJavaDocs>`_
    - AsyncHttpClient: `reference <https://github.com/sonatype/async-http-client>`_ and `Javadoc <http://sonatype.github.com/async-http-client/apidocs/reference/packages.html>`_
    - SLF4J: `reference <http://www.slf4j.org/manual.html>`_
    - Logback: `reference <http://logback.qos.ch/manual/index.html>`_
    
Do:
+++

1. **Generate a Resthub2 template project structure**

   You can choose which template to use : pure Java Spring server template or Server + Client template if you plan to provide a RIA client
   for your app based on `Resthub Spring Stack`
   
   Choose groupId `org.resthub.training`, artifactId `jpa-webservice`, package `org.resthub.training` and version `1.0-SNAPSHOT`.
   
       As described in `Resthub documentation <http://resthub.org/2/getting-started.html>`_, create your local project by executing 
       ``mvn archetype:generate -DarchetypeCatalog=http://nexus.pullrequest.org/content/repositories/releases/`` in your `training` directory.

       - When **archetype** prompt, choose `1`: `org.resthub:resthub-jpa-webservice-archetype`. or 2 if you want also that a basic resthub-backbone-stack project
         will be generated. Enter
       - When **groupId** prompt, choose your `groupId`: `org.resthub.training`. Enter
       - When **artifactId** prompt, choose your `artifactId`: `jpa-webservice`. Enter
       - When **version** and **package** prompt, Enter.
       - Confirm by typing 'Y'. Enter

   You now have a `ready-to-code` sample resthub-spring project. Congrats !

2. **Run your project with mvn**

    Run ``mvn jetty:run`` from your `training/jpa-webservice` directory. Jetty should launch your application
    and says: 

    .. code-block:: script

       [INFO] Started Jetty Server

3. **Check on your browser that your project works that the response is an XML serialization of a Sample object with id 1**.

    Check `<http://localhost:8080/api/sample>`_

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
- **initializers**: Initializers are special classes executed at application startup to setup your webapp. ``WebappInitializer`` load your spring application contexts,
  setup filters, etc. (all actions that you previously configured in your web.xml). The archetype provided you a ``SampleInitializer`` to setup sepcific domain model
  initializations such as data creation.
  
``src/main/resources`` contains all non java source files and, in particular, your spring application context, your database configuration file and you logging configuration.

``src/test/`` contains, obviously, all you test related files and has the same structure as src/main (i.e. *java* and *resources*).
