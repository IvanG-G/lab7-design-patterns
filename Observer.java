import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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


interface EventListener {
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