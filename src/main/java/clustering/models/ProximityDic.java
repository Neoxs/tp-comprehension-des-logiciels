package clustering.models;

import java.util.*;


public class ProximityDic {

    private Map<String, Item> pairHash;

    private PriorityQueue<Item> data;

    private class Item implements Comparable<Item> {
        final ClusterEven pair;
        final String hash;
        boolean removed = false;

        Item(ClusterEven p) {
            pair = p;
            hash = hashCodePair(p);
        }

        @Override
        public int compareTo(Item o) {
            return pair.compareTo(o.pair);
        }

        @Override
        public String toString() {
            return hash;
        }
    }

    public ProximityDic() {
        data = new PriorityQueue<Item>();
        pairHash = new HashMap<String, Item>();
    }

    public List<ClusterEven> list() {
        List<ClusterEven> l = new ArrayList<ClusterEven>(data.size());
        for (Item clusterPair : data) {
            l.add(clusterPair.pair);
        }
        return l;
    }

    public ClusterEven findByCodePair(Cluster c1, Cluster c2) {
        String inCode = hashCodePair(c1, c2);
        System.err.println(pairHash.get(inCode).pair);
        return pairHash.get(inCode).pair;
    }

    public ClusterEven removeFirst() {
        Item poll = data.poll();
        while (poll != null && poll.removed) {
            poll = data.poll();
        }
        if (poll == null) {
            return null;
        }
        ClusterEven link = poll.pair;
        pairHash.remove(poll.hash);
        return link;
    }

    public boolean remove(ClusterEven link) {
        Item remove = pairHash.remove(hashCodePair(link));
        if (remove == null) {
            return false;
        }
        remove.removed = true;
        return true;
    }


    public boolean add(ClusterEven link) {
        Item e = new Item(link);
        Item existingItem = pairHash.get(e.hash);
        if (existingItem != null) {
            System.err.println("hashCode = " + existingItem.hash +
                    " adding redundant link:" + link + " (exist:" + existingItem + ")");
            return false;
        } else {
            pairHash.put(e.hash, e);
            data.add(e);
            return true;
        }
    }


    private String hashCodePair(ClusterEven link) {
        return hashCodePair(link.getLeftCluster(), link.getRightCluster());
    }

    private String hashCodePair(Cluster lCluster, Cluster rCluster) {
        return hashCodePairNames(lCluster.getName(), rCluster.getName());
    }


    private String hashCodePairNames(String leftName, String rightName) {
        if (leftName.compareTo(rightName) < 0) {
            return leftName + "~~~" + rightName;
        } else {
            return rightName + "~~~" + leftName;
        }
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
