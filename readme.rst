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

**Solution** : solution can be retrived in `<http://github.com/resthub/resthub-spring-training/tree/step2-solution>`_

Let's start to customize the project generated by our archetype.

We are going to create Contoller, Repository and, obviously Model for our Task object. We'll also adapt our Initializer in order to provide
some sample data at application startup. So, let's replace the generated `Sample` related objects with `Task`: 

- rename ``org.resthub.training.model.Sample`` class to ``org.resthub.training.model.Task``
- replace ``name`` attribut by ``title``
- add a ``description`` attribute and corresponding getter and setter
- rename ``org.resthub.training.repository.SampleRepository`` class to ``org.resthub.training.repository.TaskRepository``
- rename ``org.resthub.training.controller.SampleController`` class to ``org.resthub.training.controller.TaskController``
- rename ``org.resthub.training.controller.SampleInitializer`` class to ``org.resthub.training.controller.TaskInitializer``
- check that all references to older Sample classes have been replaced
- in ``TaskController`` and  ``TaskInitializer`` rename ``@RequestMapping`` & ``@Named`` annotation string values from sample to task  

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
- a ``POST`` request on `<http://localhost:8080/api/task>`_ with ContentType ``application/json`` and body **creates a new Task** : 

.. code-block:: javascript

   {
      "name": "new test Task",
      "description": "new description"
   }

Step 3: Customize Controller
----------------------------

**Solution** : solution can be retrieved in `<http://github.com/resthub/resthub-spring-training/tree/step3-solution>`_

We now have a basic REST interface uppon our Task model object providing default methods and behaviour implemented by resthub.

Let's suppose that the current findall : `<http://localhost:8080/api/task?page=all>`_ does not match our needs: the current implementation
returns a paginated list containing all elements in order to provide a consistent API between a *find all* and a *find paginated*.

In our case, we want a ``findAll`` implementation that returns a simple non paginated list of tasks: 

Open your ``TaskController.java`` and create a new method called ``findAllNonPaginated`` and mapped to ``/api/task?page=no``. Implement this
using repository findAll method (see `documentation <http://static.springsource.org/spring-data/data-jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html#findAll()>`_).

Check on your browser that `<http://localhost:8080/api/task?page=no>`_ works and display a simple list of tasks, without pagination:

.. code-block:: javascript

   [{
       "id": 1,
       "name": "testTask1",
       "description": null
   }, {
       "id": 2,
       "name": "testTask2",
       "description": null
   }, {
       "id": 3,
       "name": "testTask3",
       "description": null
   }]

**Note**: We cannot simply override ``/api/task?page=all`` method because mappings are currently defined in interface ``RestController`` 
(see `documentation <http://jenkins.pullrequest.org/job/resthub-spring-stack-master/javadoc/org/resthub/web/controller/RestController.html>`_)
and *Spring MVC* does not accept that a path appears twice.

Test your controller
++++++++++++++++++++

Resthub provide some testing tooling : `<http://resthub.org/2/spring-stack.html#testing>`_

We are going to test our new controller ``findAllNonPaginated`` method: 

Add resthub-test dependency in your pom.xml:

.. code-block:: xml

   <dependency>
      <groupId>org.resthub</groupId>
      <artifactId>resthub-test</artifactId>
      <version>${resthub.spring.stack.version}</version>
      <scope>test</scope>
   </dependency>
   
In ``src/test/org/resthub/training``, add a ``controller`` directory and create a ``TaskControllerTest`` inside. 

We first want to make an **integration test** of our controller:

Make your ``TaskControllerTest`` extend resthub ``AbstractWebTest`` 
(see `documentation <http://jenkins.pullrequest.org/job/resthub-spring-stack-master/javadoc/org/resthub/test/common/AbstractWebTest.html>`_)
and implement a new ``findAllNonPaginated`` test method that creates some tasks and call controller. 

Verify that the new controller: 

- returns a response that is not empty, 
- does not contain pagination
- contains the created tasks.

Step 4: Users own tasks
-----------------------

**Prerequisites** : you can find some prerequisites and reference implementation of ``NotificationService`` and ``MockConfiguration`` in
`<http://github.com/resthub/resthub-spring-training/tree/step4-prerequisites>`_

**Solution** : solution can be retrieved in `<http://github.com/resthub/resthub-spring-training/tree/step4-solution>`_

Implement a new domain model ``User`` containing a name and an email and owning tasks:

- User owns 0 or n tasks
- Task is owned by 0 or 1 user

Each domain object should contain relation to the other. Relations should be **mapped with JPA** 
(see `documentation <http://docs.jboss.org/hibernate/orm/4.1/manual/en-US/html_single/#mapping-declaration>`_) in order to be saved and retrieved from database.

**Provide dedicated Repository and Controller for user**.

Modify ``TaskInitializer`` in order to provide some sample users associated to tasks at startup. You should provide a new constructor for Tasks with a user parameter.
Don't forget to add a userRepository if needed and declare your initializer as Transactional 
`documentation <http://static.springsource.org/spring/docs/3.1.x/spring-framework-reference/html/transaction.html#transaction-declarative-annotations>`_ !

Be caution with potential infinite JSON serialization. Ignore tasks lists serialization in JSON user serialization (``@JsonIgnore``).

Check on your browser that User API `<http://localhost:8080/api/user>`_ works and provides simple CRUD and that `<http://localhost:8080/api/task>`_ still works.

You can thus add domain models and provide for each one a simple CRUD API whithout doing nothing but defining empty repositories and controllers.
But if you have more than simple CRUD needs, resthub provides also a generic **Service layer** that could be extended to fit your business needs: 

**Create a new dedicated service for business user management**: 

- create a new ``TaskService`` interface in package ``org.resthub.training.service`` extending ``CrudService`` 
  (see `documentation <http://jenkins.pullrequest.org/job/resthub-spring-stack-master/javadoc/org/resthub/common/service/CrudService.html>`_).
- create a new ``TaskServiceImpl`` class in package ``org.resthub.training.service.impl`` extending
  ``CrudServiceImpl`` (see `documentation <http://jenkins.pullrequest.org/job/resthub-spring-stack-master/javadoc/org/resthub/common/service/impl/CrudServiceImpl.html>`_)
  and implementing ``TaskService``.
- modify ``TaskController`` to switch from ``taskRepository`` to ``taskService`` (don't forget to switch from ``RepositoryBasedRestController`` extension class to
  ``ServiceBasedRestController``.
  
Check that your REST interface is still working :-)

The idea is now to **add a method that affects a user to a task** based on user and task ids. During affectation, the user should be notified that a new task 
has been affected and, if exists, the old affected user should be notified that his affectation was removed. 
These business operations should be implemented in service layer: 

- create method ``affectTask`` in ``TaskService`` interface and implement it in ``TaskServiceImpl``. Notification simulation should be performed by implementing a custom ``NotificationService`` that simply
  logs the event (you can also get the implementation from our repo in step4 solution). It is important to have an independant service (for mocking - see below - purposes)
  and you should not simply log in your new method.

.. code-block:: java

   // NotificationService
   void send(String email, String message);
   
   // TaskService
   Task affectTask(Long taskId, Long userId);
   
- In ``affectTask`` implementation, validate parameters to ensure that both userId and taskId are not null and correspond to existing objects 
  (see `documentation <http://static.springsource.org/spring/docs/3.0.x/javadoc-api/org/springframework/util/Assert.html>`_).
- Tip : You will need to manipulate userRepository in TaskService ...
- Tip 2 : You don't even have to call ``repository.save()`` due to Transactional behaviour of your service
  (see `documentation <http://static.springsource.org/spring/docs/3.1.x/spring-framework-reference/html/transaction.html#transaction-declarative-annotations>`_).
- Tip 3 : Maybe you should consider to implement ``equals()`` and ``hashCode()`` methods for User & Task
   
**Test your new service**
   
We will now write an integration test for our new service:

Create a new ``TaskServiceIntegrationTest`` integration test in ``src/test/org/resthub/training/service/integration``. This test should extend ``org.resthub.test.common.AbstractTest``.
This makes this test **aware of spring context but non transactional** because testing a service should be done in a non transactional way. This is indeed the
way in which the service will be called (e.g. by controller). The repository test should extend ``org.resthub.test.common.AbstractTransactionalTest`` to be run
in a transactional context, as done by service.

This test should perform an unique operation:

- Create user and task and affect task to user.
- Refresh the task by calling service.findById and check the retrived task contains the affected user

Try until test passes :-)


If you didn't do anything else, you can see that we didn't manage notification service calls. In our case, this is not a real problem because
our implementation simply perform a log. But in a real sample, this will lead our unit tests to send a mail to a user (and thus will need for us to
be able to send a mail in tests, etc.). So **we need to mock**.

**Mock notification service**

- Add in ``src/test/java/org/resthub/training`` a new ``MockConfiguration`` class: 

.. code-block:: java

   @Configuration
   @ImportResource("classpath*:applicationContext.xml")
   @Profile("test")
   public class MocksConfiguration {

       @Bean(name = "notificationService")
       public NotificationService mockedNotificationService() {
           return mock(NotificationService.class);
       }

   }
   
This class allows to define a mocked alias bean to notificationService bean for test purposes. Its is scoped as **test profile** 
(see `documentation <http://blog.springsource.com/2011/02/14/spring-3-1-m1-introducing-profile/>`_).

- Modify your ``TaskServiceIntegrationTest`` to load our configuration:

.. code-block:: java

   @ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = MocksConfiguration.class)
   @ActiveProfiles("test")
   public class TaskServiceIntegrationTest extends AbstractTest {
      ...
   }
   
- Modify your test to check that ``NotificationService.send()`` method is called once when a user is affected to a task and twice if there was
  already a user affected to this task. Check the values of parameters passed to send method.
- Tip : You'll need to inject your mocked service and use ``Mockito`` API and ``verify`` method 
  (see `documentation <http://docs.mockito.googlecode.com/hg/latest/org/mockito/Mockito.html>`_.
  
This mock allows us to verify integration with others services and API whitout testing all these external tools.

This integration test is really usefull to validate your the complete chain i.e. service -> repository -> database (and, thus, your JPA mapping)
but, it is not necessary to write integration tests to test only your business and the logic of a given method.

It is really more performant and efficient to write *real unit tests* by using mocks.

**Unit test with mocks**

- Create a new ``TaskServiceTest`` class in ``src/test/java/org/resthub/training/service``. This test should not extend any other class.
- Declare and mock (cf. `documentation <http://docs.mockito.googlecode.com/hg/latest/org/mockito/Mockito.html#verification>`_) ``userRepository``,
  ``taskRepository`` and ``notificationService``.
- Define that when call in ``userRepository.findOne()`` with parameter equal to 1L, the mock will return a valid user instance, null otherwise.
- Define that when call in ``taskRepository.findOne()`` with parameter equal to 1L, the mock will return a valid task instance, null otherwise.
- Provide these mocks to a new TaskServiceImpl instance (note that this test is a real unit test so we fon't use spring at all).
- Tip : These declarations could be implemented in a ``@BeforeClass`` method.
- Tip 2: It will be maybe necessary that you provide setters in TaskServiceImpl for userRepository and notificationService.
- Implement tests:
   - Check that the expected exception is thrown when userId or taskId are null   
   - Check that the expected exception is thrown when userId or taskId does not match any object.
   - Check that the returned task contains the affected user.
   
Working mainly with unit tests (whithout launching spring context, etc.) is really more efficient to write and run and should be preffered to
systematic complete integration tests. Note that you still have to provide, at least, one integration test in order to verify mappings and complete
chain.
  
**Create correponding method in controller to call this new service layer**.

- Implement a new method API to affect a task to a user that call ``taskService`` method. This API could be reached at ``/api/task/1/user/1`` on a 
  ``PUT`` request in order to affect user 1 to task 1.

You can test in your browser (or, better, add a test in ``TaskControllerTest``) that the new API is operational.


Step 5: Validate your beans and embed entities
----------------------------------------------

**Solution** : solution can be retrieved in `<http://github.com/resthub/resthub-spring-training/tree/step5-solution>`_

Finally, we want to add validation constraints to our model. This could be done by using BeanValidation (JSR303 Spec) and its reference
implementation: Hibernate Validator. see `documentation <http://docs.jboss.org/hibernate/validator/4.1/reference/en-US/html_single/>`_

**Modify User and Task to add validation**: 

- User name and email are mandatory and not empty
- User email should match regexp ``.+@.+\\.[a-z]+``
- Task title is mandatory and not empty

Verify that your integration tests (and initializer) fail. Make it pass.

**Add embedded address to users**:

- Modify User model to add an embedded entity address to store user address (city, country).
  see `documentation <http://docs.jboss.org/hibernate/orm/4.1/manual/en-US/html_single/#mapping-declaration-component>`_
- Add a ``UserRepositoryIntegrationTest`` class in ``src/test/java/org/resthub/training/repository/integration`` and implement
  a test that try to create a user with an embedded address. Check that you can then call a findOne of this user and that
  the return object contains address object. Your test should extend ``AbstractTest``
  
**Add nested validation**:

- Add validation constraints to your Address : city and country must be not null and not empty
- Add annotation on User to guarantee that address object is null or valid (see ``@Valid`` annotation).
- Modify ``UserRepositoryIntegrationTest`` to test that a user can be created with a null address but exception is thrown when 
  address is incomplete (e.g. country is null or empty).
