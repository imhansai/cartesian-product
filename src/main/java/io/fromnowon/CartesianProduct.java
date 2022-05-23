package io.fromnowon;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 笛卡尔积
 *
 * @author hansai
 */
public class CartesianProduct {

    private static LinkedList<Function<? super TestSubject, ? extends String>> originFunctionLinkedList;

    public static void main(String[] args) {
        TestSubject testSubject = new TestSubject();
        testSubject.setBrandName("品牌-波司登");
        testSubject.setCountry("国家-中国");
        testSubject.setState("状态-在售");
        testSubject.setLevel("级别-高级");

        LinkedList<Function<? super TestSubject, ? extends String>> functionLinkedList = new LinkedList<>();
        functionLinkedList.offer(TestSubject::getBrandName);
        functionLinkedList.offer(TestSubject::getCountry);
        functionLinkedList.offer(TestSubject::getState);
        functionLinkedList.offer(TestSubject::getLevel);

        originFunctionLinkedList = new LinkedList<>(functionLinkedList);

        List<TestSubject> testSubjectList = List.of(testSubject);

        // 为了区分结果
        Type tempType = new Type();
        // 控制遍历
        Boolean insideLoop = false;
        extracted(tempType, functionLinkedList, testSubjectList, insideLoop);
    }

    private static void extracted(Type type,
                                  LinkedList<Function<? super TestSubject, ? extends String>> functionLinkedList,
                                  List<TestSubject> testSubjectList,
                                  Boolean insideLoop) {
        Function<? super TestSubject, ? extends String> function = functionLinkedList.poll();
        if (Objects.isNull(function)) {
            return;
        }

        // 为了换个维度重头遍历
        LinkedList<Function<? super TestSubject, ? extends String>> tempFunctionLinkedList = null;
        if (!insideLoop) {
            tempFunctionLinkedList = new LinkedList<>(functionLinkedList);
        }

        // 内部横向遍历
        List<LinkedList<Function<? super TestSubject, ? extends String>>> tempFunctionLinkedListList = new ArrayList<>();
        while (!functionLinkedList.isEmpty()) {
            LinkedList<Function<? super TestSubject, ? extends String>> list = new LinkedList<>(functionLinkedList);
            tempFunctionLinkedListList.add(list);
            functionLinkedList.poll();
        }

        Map<? extends String, List<TestSubject>> listMap = testSubjectList.stream().collect(Collectors.groupingBy(function));
        for (Map.Entry<? extends String, List<TestSubject>> entry : listMap.entrySet()) {
            String group = entry.getKey();
            List<TestSubject> testSubjects = entry.getValue();
            handlerType(type, function, group);

            System.out.println("type = " + type);

            for (LinkedList<Function<? super TestSubject, ? extends String>> functions : tempFunctionLinkedListList) {
                // 结果重置
                Type tempType = type.clone();
                extracted(type, functions, testSubjects, true);
                type = tempType;
            }

        }

        if (insideLoop) {
            return;
        }

        Type tempType = new Type();
        extracted(tempType, tempFunctionLinkedList, testSubjectList, false);
    }

    private static void handlerType(Type type,
                                    Function<? super TestSubject, ? extends String> function,
                                    String group) {
        Integer index = null;
        for (int i = originFunctionLinkedList.size() - 1; i >= 0; i--) {
            Function<? super TestSubject, ? extends String> tempFunction = originFunctionLinkedList.get(i);
            if (Objects.equals(tempFunction, function)) {
                index = i;
                break;
            }
        }

        if (Objects.isNull(index)) {
            throw new RuntimeException("没有找到对应的类型");
        }

        switch (index) {
            case 0:
                type.setBrandName(group);
                break;
            case 1:
                type.setCountry(group);
                break;
            case 2:
                type.setState(group);
                break;
            case 3:
                type.setLevel(group);
                break;
            default:

        }
    }

}
