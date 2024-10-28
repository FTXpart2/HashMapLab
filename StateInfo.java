
public class StateInfo {
    private String capital;
    private long population;
    private double size; // in square miles
    private DLList<String> landmarkPictures;

    // Constructor
    public StateInfo(String capital, long population, double size) {
        this.capital = capital;
        this.population = population;
        this.size = size;
        this.landmarkPictures = new DLList<>();
    }

    // Getters
    public String getCapital() {
        return capital;
    }

    public long getPopulation() {
        return population;
    }

    public double getSize() {
        return size;
    }

    public DLList<String> getLandmarkPictures() {
        return landmarkPictures;
    }

    // Method to add a landmark picture URL
    public void addLandmarkPicture(String url) {
        landmarkPictures.add(url);
    }

    @Override
    public String toString() {
        return "State Capital: " + capital + "\n" +
               "Population: " + population + "\n" +
               "Size: " + size + " square miles\n" +
               "Landmark Pictures: ";
    }
}
