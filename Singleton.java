public class Singleton {
    Configuration configuration = Configuration.getInstance();
    String envVar = configuration.getConfigurationVariables();
    //Use of configuration variables
}


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