public class Factory{

    VehicleFactory factory;
    
    void confiugureVehicle(String vehicleInput) throws Exception{
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

class User{
    private String typeOfVehicle;

    public User(String typeOfVehicle){
        this.typeOfVehicle = typeOfVehicle;
    }

    public String getTypeOfVehicle(){
        return typeOfVehicle;
    }
}