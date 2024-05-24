import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Observer {
    public void observerExample(){
        Editor editor = new Editor();
        editor.events.subscribe("open", new PhoneNotificationListener ("Phone Number here"));
        editor.events.subscribe("save", new PhoneNotificationListener ("Phone Number here")); 
        try{
            editor.openFile("FilePath");
            editor.saveFile();
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

    public void notify(String eventType, File file) {
        List<EventListener> users = listeners.get(eventType);
        for (EventListener listener : users) {
            listener.update(eventType, file);
        }
    }
}

class Editor {
    public EventManager events;
    private File file;

    public Editor() {
        this.events = new EventManager("open", "save");
    }

    public void openFile(String filePath) {
        this.file = new File(filePath);
        events.notify("open", file);
    }

    public void saveFile() throws Exception {
        events.notify("save", file);
    }
}


interface EventListener {
    void update(String eventType, File file);
}

class PhoneNotificationListener implements EventListener {
    private String phone;

    public PhoneNotificationListener(String phone) {
        this.phone = phone;
    }

    @Override
    public void update(String eventType, File file) {
        System.out.println("Msg to " + phone + ": Someone has performed " + eventType + " operation with the following file: " + file.getName());
    }
}