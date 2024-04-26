package source;  // Pakiet, w którym znajduje się klasa

// Definicja klasy Resource
public class Resource {
    // Enumeracja reprezentująca typ zasobu
    public enum Type {
        Coal,  // Węgiel
        Wood,  // Drewno
        Fish   // Ryby
    }

    public Type type;  // Typ zasobu
    public Point localization;  // Lokalizacja zasobu

    // Konstruktor klasy Resource
    public Resource(Type type, Point localization) {
        this.type = type;  // Przypisanie wartości do pola type
        this.localization = localization;  // Przypisanie wartości do pola localization
    }
}
