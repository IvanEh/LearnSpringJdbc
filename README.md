# Goal
Set up a Spring driven project with basic usage of Spring's JDBC API

# Functionality
- set up Gradle project with dependencies
    see build.gradle
- set up a JDBC data source
    see class AppConfig; see methods dataSource(), jdbcTemplate(), main()
- design a general DAO interface with CRUD operations
    see interface Dao
- store and retrieve User objects from the data source
    see class User, see AppConfig.main()
- substitute '?' in the query with appropriate values
    see Dao interface implementation in UserDao class.