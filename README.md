# lab7-design-patterns

## * Singleton
  * Global Configuration Management: Design a system that ensures a single, globally accessible configuration object without access conflicts
We can solve this problem using singleton pattern design, or we can also create a Enum class, but in this case we will use Singleton as a solution problem.

Here is a code example in Java.
The class Configuration is applying the design patter of Singleton, then, later in the class Singleton we are using the configuration class, emulating environment variables.
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
This problem can be primarily solved using factory pattern, we need to create several types of objects based on an external input.

Here is an example code in java

In this case we will create different types of vehicles depending on the user choice
First of all we create the vehicle interface and their implementations, we can also add other types of vehicles.
```java
interface Vehicle{
    public void move();
}

class Car implements Vehicle{
    
    @Override
    public void move(){
        System.out.println("Car is moving across the land");
    }
}

class Boat implements Vehicle{
    @Override
    public void move(){
        System.out.println("Boat is moving across the sea");
    }
}
```

After that, we have our Factory.
```java
interface VehicleFactory {
    public Vehicle createVehicle();
}

class BoatFactory implements VehicleFactory{
    @Override
    public Vehicle createVehicle(){
        return new Boat();
    }
}

class CarFactory implements VehicleFactory{
    @Override
    public Vehicle createVehicle(){
        return new Car();
    }
}
```

And then, the POJO of the user, where the decision of the user is being stored.
```java
class User{
    private String typeOfVehicle;

    public User(String typeOfVehicle){
        this.typeOfVehicle = typeOfVehicle;
    }

    public String getTypeOfVehicle(){
        return typeOfVehicle;
    }
}
```

Then we implement the use of the factory based on the user choice 
```java
public class Factory{

    VehicleFactory factory;
    
    void configureVehicle(String vehicleInput) throws Exception{
    User user = new User(vehicleInput);
    String input = user.getTypeOfVehicle().toLowerCase();

    if(input.contains("boat")){
        factory = new BoatFactory();
    }
    else if(input.contains("car")){
        factory = new CarFactory();
    }
    else{
        throw new Exception("No vehicle found");
    }

    Vehicle vehicle = factory.createVehicle();
    vehicle.move();
    }
}
```

  * State Change Notification Across System Components: Ensure components are notified about changes in the state of other parts without creating tight coupling.
We can use the Observer pattern, there are a few libaries that can help us in this task, but the raw implementation is the next one, using the example of components that needs to be notified about changes in them.

First of all we have the event manager and the Editor.
Event Manager function is only to subscribe, unsubscribe or notify users. 
Otherwise, the Editor class is ensuring that the performed action is being notified.
```java
class EventManager {
    Map<String, List<EventListener>> listeners = new HashMap<>();

    public EventManager(String... operations) {
        for (String operation : operations) {
            this.listeners.put(operation, new ArrayList<>());
        }
    }

    public void subscribe(String eventType, EventListener listener) {
        List<EventListener> users = listeners.get(eventType);
        users.add(listener);
    }

    public void unsubscribe(String eventType, EventListener listener) {
        List<EventListener> users = listeners.get(eventType);
        users.remove(listener);
    }

    public void notify(String eventType, Component component) {
        List<EventListener> users = listeners.get(eventType);
        for (EventListener listener : users) {
            listener.update(eventType, component);
        }
    }
}

class Editor {
    public EventManager events;
    private Component component;

    public Editor() {
        this.events = new EventManager("resize", "delete");
    }

    public void resizeComponent(String componentName, int heigth, int length) {
        this.component = new Component(componentName);
        component.resize(heigth, length);
        events.notify("resize", component);
    }

    public void deleteComponent(String componentName) throws Exception {
        //this should look into a database to delete components.
        this.component = new Component(componentName);
        component.delete();
        events.notify("delete", component);
    }
}
```

We also need the EventListener, this will notify the user or in this case using logging.
This class ensures to be notified on every change of the components.
```java
nterface EventListener {
    void update(String eventType, Component component);
}

class PhoneNotificationListener implements EventListener {
    private String phone;

    public PhoneNotificationListener(String phone) {
        this.phone = phone;
    }

    @Override
    public void update(String eventType, Component component) {
        System.out.println("Msg to " + phone + ": Someone has performed " + eventType + " operation with the following component: " + component.getComponentName());
    }
}
```
Here is the component POJO
```java
class Component{

    private String componentName;
    private int heigth;
    private int length;

    Component(String componentName){
        this.componentName = componentName;
    }

    public String getComponentName(){
        return componentName;
    }
    public void resize(int heigth, int length){
        this.heigth = heigth;
        this.length = length;
    }

    public void delete(){
        this.delete();
    }
}
```

and thus the Observer class using the editor as the main object, every action performed must go through the editor class.
```java
public class Observer {
    public void observerExample(){
        Editor editor = new Editor();
        editor.events.subscribe("resize", new PhoneNotificationListener ("Phone Number here"));
        editor.events.subscribe("delete", new PhoneNotificationListener ("Phone Number here")); 
        try{
            editor.resizeComponent("Component Name", 10, 10);
            editor.deleteComponent("Component Name");
        }
        catch(Exception e ){
            System.out.println(e.getMessage());
        }
    }
}
```

  * Efficient Management of Asynchronous Operations: Manage multiple asynchronous operations like API calls which need to be coordinated without blocking the main application workflow.

To solve this problem we can use asynchronous methods, in java we can use different libaries, but in Java 8 we can use Threads, this is an example of how we can use them.

the output of the next code is:
```
Before started async method
Executed async method
3 + 9 = 12
```
Ensuring the asynchonous funcionality.
```java
public class Async {

    public static void main(String[] args) {
        asyncSum(3, 9);
    }

    private static void asyncSum(int number, int number2){
        Thread newThread = new Thread(() -> {
            try{
                Thread.sleep(1000);
                System.out.println(number + " + " + number2 + " = " + (number + number2));
            }
            catch(Exception e ){
                System.out.println(e.getMessage());
            }
        });
        System.out.println("Before started async method");
        newThread.start();
        System.out.println("Executed async method");
    }
}
```



