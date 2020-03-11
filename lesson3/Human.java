package lesson3;

public class Human {
    private String name, phone;

    public String getName(){
        return name;
    }

    public String getPhone(){
        return phone;
    }

    public Human(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Фамилия: " + name + "; Телефон: " + phone + "\n";
    }
}