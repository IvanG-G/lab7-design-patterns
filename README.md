# lab7-design-patterns

## * Singleton
  * Global Configuration Management: Design a system that ensures a single, globally accessible configuration object without access conflicts
```java
class Configuration{

    private static Configuration instance;
    private String configurationVariables;

    public Configuration(){
        this.configurationVariables = "${variable.Ambiente.Configuracion}";
    }

    public static Configuration getInstance(){
        if(instance == null){
            instance = new Configuration();
        }
        return instance;
    }

    public String getConfigurationVariables(){
        return configurationVariables;
    }
}
```

```java
public class Singleton {
    Configuration configuration = Configuration.getInstance();
    String envVar = configuration.getConfigurationVariables();
    //Use of configuration variables
}
```

  * Dynamic Object Creation Based on User Input: Implement a system to dynamically create various types of user interface elements based on user actions.
```java
```
  * State Change Notification Across System Components: Ensure components are notified about changes in the state of other parts without creating tight coupling.
```java
```
  * Efficient Management of Asynchronous Operations: Manage multiple asynchronous operations like API calls which need to be coordinated without blocking the main application workflow.
```java
```

