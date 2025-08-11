package HelperClass;

import edu.princeton.cs.algs4.In;

import java.util.*;
import java.util.stream.Collectors;

public class DealWithTxt {
    HyGraph graph;

    public DealWithTxt(String synsetsfilename , String hyponymsfilename){
        graph = new HyGraph();
        if(synsetsfilename==null || hyponymsfilename==null){
            throw new Error("synsetsfilename or hyponymsfilename is null");
        }
        In in = new In(synsetsfilename);
        while (in.hasNextLine()) {
            String[] splitLine = in.readLine().split(",");
            int index = Integer.parseInt(splitLine[0]);
            String[] synset = splitLine[1].split(" ");
            for (String s : synset) {
                graph.addNode(index,synset);
            }
        }

        In in2 = new In(hyponymsfilename);
        while (in2.hasNextLine()) {
            String[] splitLine2 = in2.readLine().split(",");
            int index = Integer.parseInt(splitLine2[0]);
            for (int i = 1; i < splitLine2.length; i++) {
                int index2 = Integer.parseInt(splitLine2[i]);
                graph.addLine(index,index2);
            }
        }
    }

    public List<String> orderBothhyponyms(String[] words){
        List<String> hyponyms = graph.getAll(words[0]);
        for (int i = 1; i < words.length; i++) {
            hyponyms = intersectionHelper(hyponyms, graph.getAll(words[i]));
        }

        return hyponyms;
    }

    public List<String> intersectionHelper(List<String> list1, List<String> list2) {
        // 处理空列表特殊情况
        if (list1 == null || list2 == null || list1.isEmpty() || list2.isEmpty()) {
            return Collections.emptyList();
        }

        // 使用HashSet实现O(1)查找复杂度
        Set<String> set = new HashSet<>(list1);
        List<String> result = new ArrayList<>();

        // 遍历第二个列表，保留交集元素
        for (String s : list2) {
            // 当元素存在于第一个列表且尚未添加时
            if (set.contains(s)) {
                result.add(s);
                // 避免结果中出现重复元素
                set.remove(s);
            }
        }

        return result;
    }
    private List<String> orderHelper(List<String> hyponyms) {
        return hyponyms.stream()
                .distinct()  // 去重
                .sorted(String.CASE_INSENSITIVE_ORDER)  // 不区分大小写的字母顺序
                .collect(Collectors.toList());
    }
}

