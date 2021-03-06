import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class DjikstraBDO {
  
 


 public static void main(String[] args) {
  Graph g = new Graph();
  g.addVertex("Calpheon", Arrays.asList( new Vertex("AbandonedLand", 1),new Vertex("ContaminatedFarm", 2), 
                                        new Vertex("DiasFarm", 2),new Vertex("FalresDirtFarm", 2),
                                        new Vertex("OberenFarm", 2),new Vertex("GabinoFarm", 2)));
  g.addVertex("AbandonedLand", Arrays.asList(new Vertex("Calpheon", 1),new Vertex("CohenFarm", 3)
                                            ,new Vertex("IsolatedSentryPost", 4)));
  g.addVertex("CohenFarm", Arrays.asList(new Vertex("AbandonedLand", 3),new Vertex("IsolatedSentryPost", 5))); 
  g.addVertex("IsolatedSentryPost", Arrays.asList(new Vertex("AbandonedLand", 4),new Vertex("CohenFarm",5 )));  
  
  g.addVertex("ContaminatedFarm", Arrays.asList(new Vertex("Calpheon", 2),new Vertex("AntiTrollFortification",5 )));
  g.addVertex("AntiTrollFortification", Arrays.asList(new Vertex("ContaminatedFarm", 5),new Vertex("IsolatedSentryPost",6 ),new Vertex("BerniantoFarm",4 )
                                       )); 
  g.addVertex("BerniantoFarm", Arrays.asList(new Vertex("AntiTrollFortification", 4),new Vertex("NorthernWheatPlantation",3 )));
  
  g.addVertex("DiasFarm", Arrays.asList(new Vertex("Calpheon", 2),new Vertex("NorthernWheatPlantation",4 ),new Vertex("FalresDirtFarm",4 )));
  g.addVertex("NorthernWheatPlantation", Arrays.asList(new Vertex("DiasFarm", 4),new Vertex("BerniantoFarm",3 )));
  
  g.addVertex("FalresDirtFarm", Arrays.asList(new Vertex("Calpheon", 2),new Vertex("DiasFarm",4 ),new Vertex("MarniFarmRuins",3 ),new Vertex("MarniCavePath",5 )));
  g.addVertex("MarniFarmRuins", Arrays.asList(new Vertex("FalresDirtFarm", 3)));
  g.addVertex("MarniCavePath", Arrays.asList(new Vertex("FalresDirtFarm", 5),new Vertex("MarniFarmRuins",4 ),new Vertex("BeaconEntrancePost",6 )));
  
  g.addVertex("OberenFarm", Arrays.asList(new Vertex("Calpheon", 2),new Vertex("BeaconEntrancePost",5 ),new Vertex("BainFarmland",4 )));
  g.addVertex("BeaconEntrancePost", Arrays.asList(new Vertex("OberenFarm", 5),new Vertex("MarniCavePath",6 )));
  g.addVertex("BainFarmland", Arrays.asList(new Vertex("OberenFarm", 4)));
  
  g.addVertex("GabinoFarm", Arrays.asList(new Vertex("Calpheon", 2),new Vertex("NorthKaiaMountaintop",3 ),new Vertex("NorthKaiaPier",5 )));
  g.addVertex("NorthKaiaMountaintop", Arrays.asList(new Vertex("GabinoFarm", 3)));
  g.addVertex("NorthKaiaPier", Arrays.asList(new Vertex("GabinoFarm", 5)));
  
  
  System.out.println(g.getShortestPath("Calpheon", "NorthKaiaPier"));
 }
 
}

class Vertex implements Comparable<Vertex> {
 
 private String id;
 private Integer distance;
 
 public Vertex(String id, Integer distance) {
  super();
  this.id = id;
  this.distance = distance;
 }

 public String getId() {
  return id;
 }

 public Integer getDistance() {
  return distance;
 }

 public void setId(String id) {
  this.id = id;
 }

 public void setDistance(Integer distance) {
  this.distance = distance;
 }

 @Override
 public int hashCode() {
  final int prime = 31;
  int result = 1;
  result = prime * result
    + ((distance == null) ? 0 : distance.hashCode());
  result = prime * result + ((id == null) ? 0 : id.hashCode());
  return result;
 }

 @Override
 public boolean equals(Object obj) {
  if (this == obj)
   return true;
  if (obj == null)
   return false;
  if (getClass() != obj.getClass())
   return false;
  Vertex other = (Vertex) obj;
  if (distance == null) {
   if (other.distance != null)
    return false;
  } else if (!distance.equals(other.distance))
   return false;
  if (id == null) {
   if (other.id != null)
    return false;
  } else if (!id.equals(other.id))
   return false;
  return true;
 }

 @Override
 public String toString() {
  return "Vertex [id=" + id + ", distance=" + distance + "]";
 }

 @Override
 public int compareTo(Vertex o) {
  if (this.distance < o.distance)
   return -1;
  else if (this.distance > o.distance)
   return 1;
  else
   return this.getId().compareTo(o.getId());
 }
 
}

class Graph {
 
 private final Map<String, List<Vertex>> vertices;
 
 public Graph() {
  this.vertices = new HashMap<String, List<Vertex>>();
 }
 
 public void addVertex(String String, List<Vertex> vertex) {
  this.vertices.put(String, vertex);
 }
 
 public List<String> getShortestPath(String start, String finish) {
  final Map<String, Integer> distances = new HashMap<String, Integer>();
  final Map<String, Vertex> previous = new HashMap<String, Vertex>();
  PriorityQueue<Vertex> nodes = new PriorityQueue<Vertex>();
  
  for(String vertex : vertices.keySet()) {
   if (vertex == start) {
    distances.put(vertex, 0);
    nodes.add(new Vertex(vertex, 0));
   } else {
    distances.put(vertex, Integer.MAX_VALUE);
    nodes.add(new Vertex(vertex, Integer.MAX_VALUE));
   }
   previous.put(vertex, null);
  }
  
  while (!nodes.isEmpty()) {
   Vertex smallest = nodes.poll();
   if (smallest.getId() == finish) {
    final List<String> path = new ArrayList<String>();
    while (previous.get(smallest.getId()) != null) {
     path.add(smallest.getId());
     smallest = previous.get(smallest.getId());
    }
    return path;
   }

   if (distances.get(smallest.getId()) == Integer.MAX_VALUE) {
    break;
   }
      
   for (Vertex neighbor : vertices.get(smallest.getId())) {
    Integer alt = distances.get(smallest.getId()) + neighbor.getDistance();
    if (alt < distances.get(neighbor.getId())) {
     distances.put(neighbor.getId(), alt);
     previous.put(neighbor.getId(), smallest);
     
     forloop:
     for(Vertex n : nodes) {
      if (n.getId() == neighbor.getId()) {
       nodes.remove(n);
       n.setDistance(alt);
       nodes.add(n);
       break forloop;
      }
     }
    }
   }
  }
  
  return new ArrayList<String>(distances.keySet());
 }
 
}