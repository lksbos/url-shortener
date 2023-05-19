# URL Shortener

A very simple URL Shortener

### Libraries
Main Third Party Libraries
 - [Gradle](https://gradle.org/) - Gradle helps teams build, automate and deliver better software, faster.
 - [Spring Boot](http://projects.spring.io/spring-boot/) - Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications that you can "just run".
 - [Lombok](https://projectlombok.org/) - Lombok is a java library that automatically plugs into your editor and build tools, spicing up your java.
 - [Thymeleaf](https://www.thymeleaf.org/) - Thymeleaf is a modern server-side Java template engine for both web and standalone environments.
 - [Apache Commons Validator](http://commons.apache.org/proper/commons-validator/) - This package addresses some of these issues to speed development and maintenance of validation rules.

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/gradle-plugin/reference/html/)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/reference/htmlsingle/#using-boot-devtools)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)
* [Thymeleaf](https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/reference/htmlsingle/#boot-features-spring-mvc-template-engines)

### Decisions
* It was decided to use Thymeleaf due to simplicity and time constraint.
* The same can be said about the decision to use an in memory database manually implemented.
* Although the ui has some bootstrap on it, it has lot's of opportunities for improvement both visually and regarding usability
* It was used Apache Commons Validator library with UrlValidator class to validate the url entered. For any further information please check the official site mentioned in libraries section
* Spring boot was used due to its out of the box features that helps both development and deploy.
* Url hash algorithm is converting a Long id (base10) to a base60 id before presenting the url to the user to make it less predictable, but no guarantees.

### Assumptions
* It was assumed that the user can only enter either http or https protocols, and in case the protocol is missing http is the default one to be appended on the url.
  * eg.
  * valid: www.url.domain, url.domain, http://url.com, https://url.domain
  * invalid: url, http://url, anythingDifferentThanHttpOrHttps://url.domain

### Limitations
* This project was built as a prototype and it is NOT ready to production by any means.
* The data is in memory and once the server is restarted the data will be lost.
* It uses two maps to simulate a key value pair database, and an index to filter by the original url (you can check that on the UrlRepository class).
* The short urls are generated in a sequence base using AtomicLong from java and in case we convert it to use a real database 
some strategies to generate this sequence can be adopted.
* Although we have the createUrl method synchronized to avoid concurrency issues no performance tests of any kind were done, so concurrency scenarios may still be a problem.
* The urls can be predictable and that may represent some security issues.
* The generated urls don't have a time to live, which is a "Good to Have" feature to save space with a periodic purge routine.
* In case we convert this to use a real database, some cache mechanism it's something to be considered to speed up repetitive calls and avoid unnecessary calls to the database.
  * A no sql database like mongoDB would be a good option due to its scalability and indexing capacity making the transition from in memory to mongoDB easy to do
  * For caching layer Redis is also a good option since it's also scalable.
  * We could apply many strategies to generate the ids sequence leaving that to the database or having a separate microservice to manage that having even less predictability.
* The system only allows one url at a time and doesn't have any protection against bots like rate limiting and controlled access
* There's no user controlled access which in a real product would be advised to both monetize and protect the product of bad actors limiting urls per user on both paid and free versions.

### Building
Open a terminal on the project root folder and execute:

```./gradlew clean build```

### Testing
Open a terminal on the project root folder and execute:

```./gradlew test```

### Running
Open a terminal on the project root folder and execute:

```./gradlew bootRun```