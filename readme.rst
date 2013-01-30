Resthub Spring stack tutorial
=============================

This tutorial will help you to get an overview of resthub-spring-stack and its components.

If you want to use this tutorial in a training mode, `a version without answers is also available <spring-without-answer.html>`_.

**Code** : you can find the code of the sample application at `<https://github.com/resthub/resthub-spring-training>`_ (Have a look to rest branch for step 8 code).

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
    - Jackson 2.1: `reference <http://wiki.fasterxml.com/JacksonDocumentation>`_ and `Javadoc <http://wiki.fasterxml.com/JacksonJavaDocs>`_
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

       - When **archetype** prompt, choose `org.resthub:resthub-jpa-webservice-archetype`. or `org.resthub:resthub-jpa-backbonejs-archetype` if you want also that a basic resthub-backbone-stack project
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
- **configurers**: configurers are using Spring Java Config to allow you define you Spring beans and your Spring configuration. They contains the same information than your old applicationContext.xml files, but described with Java code in the ``WebAppConfigurer`` class.
- **initializers**: Initializers are special classes executed at application startup to setup your webapp. ``WebappInitializer`` load your spring application contexts,
  setup filters, etc. (all actions that you previously configured in your web.xml). The archetype provided you a ``SampleInitializer`` to setup sepcific domain model
  initializations such as data creation.
- ``src/main/resources`` contains all non java source files and, in particular, your spring application context, your database configuration file and you logging configuration.
- ``src/test/`` contains, obviously, all you test related files and has the same structure as src/main (i.e. *java* and *resources*).


Step 2: Customize Model
-----------------------

Let's start to customize the project generated by our archetype.

We are going to create Contoller, Repository and, obviously Model for our Task object. We'll also adapt our Initializer in order to provide
some sample data at application startup. 

Do:
+++

1. **Replace the generated `Sample` related objects with `Task`**

    - rename ``org.resthub.training.model.Sample`` class to ``org.resthub.training.model.Task``
    - replace ``name`` attribut by ``title``
    - add a ``description`` attribute and corresponding getter and setter

2. **Modify all others components considering this modification**

    - rename ``org.resthub.training.repository.SampleRepository`` class to ``org.resthub.training.repository.TaskRepository``
    - rename ``org.resthub.training.controller.SampleController`` class to ``org.resthub.training.controller.TaskController``
    - rename ``org.resthub.training.controller.SampleInitializer`` class to ``org.resthub.training.controller.TaskInitializer``
    - in ``TaskController`` and  ``TaskInitializer`` rename ``@RequestMapping`` & ``@Named`` annotation string values from sample to task  
    - check that all references to older Sample classes have been replaced

3. **Check that your new API works**

    re-run ``mvn jetty:run`` from your `training/jpa-webservice` directory. 

    Check on your browser that `<http://localhost:8080/api/task>`_ works and display XML representation for a sample object with id 1.

Answer:
+++++++

1. **How is wrapped the list of all existing tasks ?**
    
    A ``GET`` request on `<http://localhost:8080/api/task>`_ shows that the list of all existing tasks is **wrapped into a Pagination object** `PageImpl`.
    
2. **How to get a single task ?**
    
    A ``GET`` request on `<http://localhost:8080/api/task/1>`_ **returns a single Task** object with id 1, 
    
3. **How to update an existing task ? Update task 1 to add a description** ``new description``
    
    A ``PUT`` request on `<http://localhost:8080/api/task/1>`_ with ContentType ``application/json`` and body : 

    .. code-block:: javascript

       {
          "id": 1,
          "title": "testTask1",
          "description": "new description"
       }

4. **How to delete a task ?**       
  
    A ``DELETE`` request on `<http://localhost:8080/api/task/1>`_ **delete the Task** (check with a GET on `<http://localhost:8080/api/task>`_).
    
5. **How to create a task ?**  
    
    A ``POST`` request on `<http://localhost:8080/api/task>`_ with ContentType ``application/json`` and body: 

    .. code-block:: javascript

       {
          "title": "new test Task",
          "description": "new description"
       }
       
Step 3: Customize Controller
----------------------------

We now have a basic REST interface uppon our Task model object providing default methods and behaviour implemented by resthub.

Let's try to implement a ``findByName`` implementation that returns a Task based on it name: 

Do:
+++

1. **Modify** ``TaskController.java`` **to add a new method called** ``findByTitle``  **with a name parameter mapped to** ``/api/task/name/{name}`` returning a single task element if exists.

  Implementation is done by adding a new repository findByName() method (see `<http://static.springsource.org/spring-data/data-jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html#findAll()>`_) in interface.
  
  .. code-block:: Java
    
    Task findByTitle(String title);
  

  And in controller: 
  
  .. code-block:: Java
    
    @RequestMapping(value = "title/{title}", method = RequestMethod.GET) @ResponseBody
    public Task searchByTitle(@PathVariable String title) {
      return this.repository.findByTitle(title);
    }

  Check on your browser that `<http://localhost:8080/api/task/title/testSample1>`_ works and display a simple list of tasks, without pagination.

  .. code-block:: javascript

    {
      "id": 1,
      "name": "testTask1",
      "description": "bla bla"
    }

    
see `<https://github.com/resthub/resthub-spring-training/tree/step3-solution>`_ for complete solution.

Test your controller
++++++++++++++++++++

1. **Add dependency to use Resthub2 testing tools**

    .. code-block:: xml

       <dependency>
          <groupId>org.resthub</groupId>
          <artifactId>resthub-test</artifactId>
          <version>${resthub.spring.stack.version}</version>
          <scope>test</scope>
       </dependency>
   
2. In ``src/test/org/resthub/training``, add a ``controller`` directory and create a ``TaskControllerTest`` inside. 
   We first want to make an **integration test** of our controller. i.e. a test that need to run and embedded servlet container.
   **Implement a new** ``testFindByName`` **test method that creates some tasks and call controller.** 
   
   Verify that the new controller returns a response that is not null, with the right name.

    Our test ``TaskControllerTest`` should extend resthub ``AbstractWebTest`` 
    (see `documentation <http://jenkins.pullrequest.org/job/resthub-spring-stack-master/javadoc/org/resthub/test/common/AbstractWebTest.html>`_)
    
    .. code-block:: Java
    
        public class TaskControllerTest extends AbstractWebTest {
            public TaskControllerTest() {
                // Activate resthub-web-server and resthub-jpa Spring profiles
                super("resthub-web-server,resthub-jpa");
            }


            @Test
            public void testFindByTitle() {
                this.request("api/task").xmlPost(new Task("task1"));
                this.request("api/task").xmlPost(new Task("task2"));
                Task task1 = this.request("api/task/title/task1").jsonGet().resource(Task.class);
                Assertions.assertThat(task1).isNotNull();
                Assertions.assertThat(task1.getTitle()).isEqualTo("task1");
            }
        }
       
    see `<https://github.com/resthub/resthub-spring-training/tree/step3-solution>`_ for complete solution.

3. **Run test and check it passes**

   .. code-block:: script

        mvn -Dtest=TaskControllerTest#testCreateResource test
        
        -------------------------------------------------------
         T E S T S
        -------------------------------------------------------
        Running org.resthub.training.controller.TaskControllerTest
        
        ....
        
        Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 15.046 sec

        Results :

        Tests run: 1, Failures: 0, Errors: 0, Skipped: 0

        [INFO] ------------------------------------------------------------------------
        [INFO] BUILD SUCCESS
        [INFO] ------------------------------------------------------------------------
        [INFO] Total time: 24.281s
        [INFO] Finished at: Thu Sep 13 14:27:44 CEST 2012
        [INFO] Final Memory: 13M/31M
        [INFO] ------------------------------------------------------------------------