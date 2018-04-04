package org.nix.util;


import org.nix.entity.City;

import java.util.*;

public class Graph {

    /**
     * 顶点
     */
    private List<City> citys;

    /**
     * 边
     */
    private double[][] edges;

    String way;

    /**
     * 没有访问的顶点
     */
    private Queue<City> unCityed;

    public Graph(List<City> citys, double[][] edges) {
        this.citys = citys;
        this.edges = edges;
        initUnVisited();
    }
    
    /**
     * 搜索各顶点最短路径
     */
    public void search(){
        while(!unCityed.isEmpty()){
            City city = unCityed.element();
            //顶点已经计算出最短路径，设置为"已访问"
            city.setMarked(true);
            //获取所有"未访问"的邻居
            List<City> neighbors = getNeighbors(city);    
            //更新邻居的最短路径
            updatesDistance(city, neighbors);
            pop();
        }
    }
    
    /**
     * 更新所有邻居的最短路径
     */
    private void updatesDistance(City city, List<City> neighbors){
        for(City neighbor: neighbors){
            updateDistance(city, neighbor);
        }
    }
    
    /**
     * 更新邻居的最短路径
     */
    private void updateDistance(City city, City neighbor){
        double distance = getDistance(city, neighbor) + city.getPath();
        if(distance < neighbor.getPath()){
            neighbor.setPath(distance);
            neighbor.setWay(city);
        }
    }

    /**
     * 初始化未访问顶点集合
     */
    private void initUnVisited() {
        unCityed = new PriorityQueue<City>();
        for (City v : citys) {
            unCityed.add(v);
        }
    }

    /**
     * 从未访问顶点集合中删除已找到最短路径的节点
     */
    private void pop() {
        unCityed.poll();
    }

    /**
     * 获取顶点到目标顶点的距离
     */
    private double getDistance(City source, City destination) {
        int sourceIndex = citys.indexOf(source);
        int destIndex = citys.indexOf(destination);
        return edges[sourceIndex][destIndex];
    }

    /***
     * 获取顶点所有(未访问的)邻居
     */
    private List<City> getNeighbors(City v) {
        List<City> neighbors = new ArrayList<City>();
        int position = citys.indexOf(v);
        City neighbor = null;
        double distance;
        for (int i = 0; i < citys.size(); i++) {
            if (i == position) {
                //顶点本身，跳过
                continue;
            }
            //到所有顶点的距离
            distance = edges[position][i];
            if (distance < Double.MAX_VALUE) {
                //是邻居(有路径可达)
                neighbor = getCity(i);
                if (!neighbor.isMarked()) {
                    //如果邻居没有访问过，则加入list;
                    neighbors.add(neighbor);
                }
            }
        }
        return neighbors;
    }

    /***
     * 根据顶点位置获取顶点
     */
    private City getCity(int index) {
        return citys.get(index);
    }

    /**
     * 打印图
     */
    public void printGraph() {
        int verNums = citys.size();
        for (int row = 0; row < verNums; row++) {
            for (int col = 0; col < verNums; col++) {
                if(Double.MAX_VALUE == edges[row][col]){
                    System.out.printf("%9s","X");
                    System.out.printf(" ");
                    continue;
                }
                System.out.printf("%9f",edges[row][col]);
                System.out.printf(" ");
            }
            System.out.println();
        }
    }

    public static Map<String,Object> getMinWay(City srcCity, City dstModel, List<City> cities) {
        double[][] doubles = new double[cities.size()][cities.size()];
        for (int i = 0;i < doubles.length;i ++) {
            for (int j = 0;j < doubles[i].length;j ++) {
                doubles[i][j] = Double.MAX_VALUE;
            }
        }
        for (int i = 0;i < cities.size();i ++ ) {
            if (cities.get(i).equals(srcCity)) {
                cities.get(i).setPath(0);
            }
            if (cities.get(i).equals(dstModel)) {
                dstModel = cities.get(i);
            }
            for (int j = 0;j < cities.get(i).getDisList().size() ;j ++) {
                int k = -1;
                for (int c = 0;c < cities.size();c ++) {
                    if (cities.get(c).getCityName().equals(cities.get(i).getDisList().get(j).getDstCity().getCityName())) {
                        k = c;
                    }
                }
                if (k != -1) {
                    doubles[i][k] = cities.get(i).getDisList().get(j).getDistance();
                    doubles[k][i] = doubles[i][k];
                }
            }
        }
        Graph graph = new Graph(cities, doubles);
        graph.printGraph();
        graph.search();
        Map<String,Object> map = new HashMap<>();
        map.put("distance",dstModel.getPath());
        map.put("way",dstModel.getWays());
        return map;
    }
}