package HelperClass;

import java.util.*;
import java.util.stream.Collectors;

public class HyGraph {
    Map<Integer,String[]> nodes;
    Map<Integer,List<Integer>> lines;

    public  HyGraph() {
        nodes = new TreeMap<Integer,String[]>();
        lines = new TreeMap<Integer,List<Integer>>();
    }

    public void addNode(int n,String[] s) {
        nodes.put(n,s);
    }

    public void addLine(int i, int n) {
        if(!lines.containsKey(i)){
            List<Integer> p = new ArrayList<>();
            p.add(n);
            lines.put(i,p);
        }else{
            lines.get(i).add(n);
        }
    }

    public List<Integer> getNeihbors(int n) {
        // 确保永不返回null - 没有邻居时返回空列表
        return lines.containsKey(n) ? lines.get(n) : Collections.emptyList();
    }

    public List<Integer> getAll(int n) {
        // 使用LinkedHashSet保持插入顺序同时去重
        Set<Integer> visited = new LinkedHashSet<>();
        // 用栈实现迭代DFS避免递归栈溢出
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(n);

        while (!stack.isEmpty()) {
            int current = stack.pop();

            // 如果已访问过则跳过
            if (visited.contains(current)) continue;

            // 添加当前节点到结果集
            visited.add(current);

            // 获取邻居（现在保证不返回null）
            List<Integer> neighbors = getNeihbors(current);

            // 将未访问的邻居加入栈
            for (int neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    stack.push(neighbor);
                }
            }
        }

        return new ArrayList<>(visited);
    }

    private List<String> getHyponymsHelper(int n){
        List<String> hyponyms = new ArrayList<>();
        List<Integer> sum = getAll(n);
        if(sum == null){
            return null;
        }else{
            for(int i: sum){
                String[] p = nodes.get(i);
                for(String s: p) {
                    if (!hyponyms.contains(s)) {
                        hyponyms.add(s);
                    }
                }
            }
        }
        return hyponyms;
    }

    private List<String> orderHelper(List<String> hyponyms) {
        return hyponyms.stream()
                .distinct()  // 去重
                .sorted(String.CASE_INSENSITIVE_ORDER)  // 不区分大小写的字母顺序
                .collect(Collectors.toList());
    }

    private List<Integer> getIndex(String s){
        List<Integer> index = new ArrayList<>();
        for(int i=0;i<nodes.size();i++){
            for(String p: nodes.get(i)){
                if(p.equals(s)){
                    index.add(i);
                }
            }
        }
        return index;
    }

    public List<String> getAll(String s){
        List<String> hyponyms = new ArrayList<>();
        List<Integer> index = getIndex(s);
        if(index == null){
            return null;
        }
        for(int i: index){
            hyponyms.addAll(getHyponymsHelper(i));
        }
        return orderHelper(hyponyms);
    }
}
