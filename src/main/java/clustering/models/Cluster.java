package clustering.models;

import java.util.ArrayList;
import java.util.List;

public class Cluster
{

    private String name;

    private Cluster root;

    private List<Cluster> children;

    private List<String> leafNames;

    private Proximity proximity;


    public Cluster(String name)
    {
        this.name = name;
        leafNames = new ArrayList<String>();
        proximity = new Proximity();
    }

    public Proximity getDistance()
    {
        return proximity;
    }

    public Double getWeightValue()
    {
        return proximity.getWeight();
    }

    public Double getDistanceValue()
    {
        return proximity.getProximityValue();
    }

    public void setProximity(Proximity proximity)
    {
        this.proximity = proximity;
    }

    public List<Cluster> getChildren()
    {
        if (children == null)
        {
            children = new ArrayList<Cluster>();
        }

        return children;
    }

    public void addLeafName(String lname)
    {
        leafNames.add(lname);
    }

    public void appendLeafNames(List<String> listnames)
    {
        leafNames.addAll(listnames);
    }

    public List<String> getLeafNames()
    {
        return leafNames;
    }

    public void setRoot(Cluster parent)
    {
        this.root = parent;
    }

    public String getName()
    {
        return name;
    }

    public void addChild(Cluster cluster)
    {
        getChildren().add(cluster);

    }


    @Override
    public String toString()
    {
        return "Cluster " + name;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        Cluster other = (Cluster) obj;
        if (name == null)
        {
            if (other.name != null)
            {
                return false;
            }
        } else if (!name.equals(other.name))
        {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        return (name == null) ? 0 : name.hashCode();
    }

    public boolean isLeaf()
    {
        return getChildren().size() == 0;
    }

    public int countLeafs()
    {
        return countLeafs(this, 0);
    }

    public int countLeafs(Cluster node, int count)
    {
        if (node.isLeaf()) count++;
        for (Cluster child : node.getChildren())
        {
            count += child.countLeafs();
        }
        return count;
    }

    public void toConsole(int indent)
    {
        for (int i = 0; i < indent; i++)
        {
            System.out.print("  ");

        }
        String name = getName() + (isLeaf() ? " (leaf)" : "") + (proximity != null ? "  distance: " + proximity : "");
        System.out.println(name);
        for (Cluster child : getChildren())
        {
            child.toConsole(indent + 1);
        }
    }

    public double getTotalDistance()
    {
        Double dist = getDistance() == null ? 0 : getDistance().getProximityValue();
        if (getChildren().size() > 0)
        {
            dist += children.get(0).getTotalDistance();
        }
        return dist;

    }

}
