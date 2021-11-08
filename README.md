### To open swagger-ui:

1) Run application
2) Go to http://localhost:8080/swagger-ui/

### To deploy application

1) Install MySQL database on PC
2) Open "MySQL 8.0 Command Line Client" and execute command "user module3" to switch application database
3) Run scripts in terminal from step 2:
    - appendix/module4/create_database_schema_module4.sql
    - insert scripts from appendix/module4/dump_after_inserting_a_lot_of_rows.sql
4) Select needed repository submodule (see below)
5) Run application

### To change repository type (Hibernate/Spring JPA) comment unwanted repository in:

1) build.gradle(module3) dependencies

Example:

```
   dependencies {
      implementation('org.springframework.boot:spring-boot-starter-web')
      implementation project(":api")
      // implementation project(":spring-data-repository")
      implementation project(":hibernate-repository")
   }
```

2) build.gradle(submodule "api") dependencies

Example:

```
   dependencies {
      // implementation project(":spring-data-repository")
      implementation project(":hibernate-repository")
      
      // other dependencies ...
   }
```