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
   
**Solution** : solution can be retrived in `<http://github.com/resthub/resthub-spring-training/tree/step1-solution>`_

- **Get resthub archetype:**

As described in `Resthub documentation <http://resthub.org/2/getting-started.html>`_, create your local project by executing 
``mvn archetype:generate -DarchetypeCatalog=http://nexus.pullrequest.org/content/repositories/releases/`` in your `training` directory.

   - When **archetype** prompt, choose `1`: `org.resthub:resthub-jpa-webservice-archetype`. or 2 if you want also that a basic resthub-backbone-stack project
     will be generated. Enter
   - When **groupId** prompt, choose your `groupId`: `org.resthub.training` or whatever. Enter
   - When **artifactId** prompt, choose your `artifactId`: `jpa-webservice` or whatever. Enter
   - When **version** and **package** prompt, Enter.
   - Confirm by typing 'Y'. Enter

You now have a `ready-to-code` sample resthub-spring project. Congrats !

To finish step 1, run ``mvn jetty:run`` from your `training/jpa-webservice` directory. Jetty should launch your application
and says: 

.. code-block:: script

   [INFO] Started Jetty Server

Check on your browser that `<http://localhost:8080/api/sample>`_ works and display XML representation for a sample object with id 1.

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
  provides basic CRUD functionalities: see `documentation <http://jenkins.pullrequest.org/job/resthub-spring-stack-master/javadoc/org/resthub/web/controller/ServiceBasedRestController.html>`_
- **model**: This package contains all you domain models.
- **repository**: This package contains your repositories, i.e. classes that provide methods to manipulate, persist and retrieve your objects from your JPA
  manager (and so your database). In the generated sample, the archetype provided you a SampleRepository that simply extend Spring-Data ``JpaRepository``.
  for behaviour, see `documentation <http://static.springsource.org/spring-data/data-jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html>`_
- **initializers**: Initializers are special classes executed at application startup to setup your webapp. ``WebappInitializer`` load your spring application contexts,
  setup filters, etc. (all actions that you previously configured in your web.xml). The archetype provided you a ``SampleInitializer`` to setup sepcific domain model
  initializations such as data creation.
  
``src/main/resources`` contains all non java source files and, in particular, your spring application context, your database configuration file and you logging configuration.

``src/test/`` contains, obviously, all you test related files and has the same structure as src/main (i.e. *java* and *resources*).


Step 2: Customize Model
-----------------------

We'll now start to customize the project generated by our archetype.

We are going to create Contoller, Repository and, obviously Model for our Task object. We'll also adapt our Initializer in order to provide
some data samples at application startup. So, let's replace the generated `Sample` related objects with `Task`: 

- rename ``org.resthub.training.model.Sample`` class to ``org.resthub.training.model.Task``
- add a ``description`` attribute and corresponding getter and setter
- rename ``org.resthub.training.repository.SampleRepository`` class to ``org.resthub.training.repository.TaskRepository``
- rename ``org.resthub.training.controller.SampleController`` class to ``org.resthub.training.controller.TaskController``
- rename ``org.resthub.training.controller.SampleInitializer`` class to ``org.resthub.training.controller.TaskInitializer``
- check that all references to older Sample classes have been replaced
- in ``TaskController`` and  ``TaskInitializer`` rename @RequestMapping & @Named annotation string values from sample to task  

re-run ``mvn jetty:run`` from your `training/jpa-webservice` directory. 

Check on your browser that `<http://localhost:8080/api/task>`_ works and display XML representation for a sample object with id 1.
Note that this service returns the list of all existing tasks wrapped into a Pagination object `PageImpl`.

We can also test that:

- a ``GET`` request on `<http://localhost:8080/api/task/1>`_ **returns a single Task** object with id 1, 
- a ``PUT`` request on the same URL with ContentType ``application/json`` and body : 

.. code-block:: javascript

   {
    "id": 1,
    "name": "testTask1",
    "description": "new description"
   }
   
**update the Task** 1 with the new description
  
- a ``DELETE`` request on the same URL **delete the Task** (check with a GET on `<http://localhost:8080/api/task>`_).
- a ``POST`` request on `<http://localhost:8080/api/task>`_ with ContentType ``application/json`` and body : 

.. code-block:: javascript

   {
    "name": "new test Task",
    "description": "new description"
   }
   
**creates a new Task**.

