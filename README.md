### To open swagger-ui:

1) Run application
2) Go to http://localhost:8080/swagger-ui/

### To change repository type (Hibernate/Spring JPA) comment unwanted repository in:

1) settings.gradle(module3)

Example:

```
   include 'hibernate-repository'
   // include 'spring-data-repository'
```

2) build.gradle(module3) dependencies

Example:

```
   dependencies {
      implementation('org.springframework.boot:spring-boot-starter-web')
      implementation project(":api")
      // implementation project(":spring-data-repository")
      implementation project(":hibernate-repository")
   }
```

3) build.gradle(submodule "api") dependencies

Example:

```
   dependencies {
      // implementation project(":spring-data-repository")
      implementation project(":hibernate-repository")
      
      // other dependencies ...
   }
```