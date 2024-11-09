package clustering.models;

public class Proximity implements Comparable<Proximity>, Cloneable {

    private Double distance;

    private Double weight;

    public Proximity() {
		this(0.0);
    }

    public Proximity(Double distance) {
		this(distance, 1.0);
    }

    public Proximity(Double distance, Double weight) {
        this.distance = distance;
        this.weight = weight;
    }

    public Double getProximityValue() {
        return distance;
    }

    /*public void setProximityValue(Double distance) {
        this.distance = distance;
    }*/

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public boolean isNaN() {
        return distance == null || distance.isNaN();
    }

    @Override
    public int compareTo(Proximity proximity) {
        return proximity == null ? 1 : getProximityValue().compareTo(proximity.getProximityValue());
    }

    @Override
    public String toString() {
		return String.format("distance : %.2f, weight : %.2f", distance, weight);
	}
}
