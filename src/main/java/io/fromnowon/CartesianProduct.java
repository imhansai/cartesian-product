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

    /**
     * 原始的分组维度
     * <p>
     * 主要为了处理 Type 属性赋值
     */
    private static final LinkedList<Function<? super TestSubject, ? extends String>> ORIGIN_FUNCTION_LINKED_LIST = getFunctionLinkedList();

    public static void main(String[] args) {
        TestSubject testSubject = new TestSubject();
        testSubject.setBrandName("品牌-波司登");
        testSubject.setCountry("国家-中国");
        testSubject.setState("状态-停售");
        testSubject.setData(1);

        TestSubject testSubject1 = new TestSubject();
        testSubject1.setBrandName("品牌-七匹狼");
        testSubject1.setCountry("国家-中国");
        testSubject1.setState("状态-在售");
        testSubject1.setData(7);

        TestSubject testSubject2 = new TestSubject();
        testSubject2.setBrandName("品牌-七匹狼");
        testSubject2.setCountry("国家-埃及");
        testSubject2.setState("状态-在售");
        testSubject2.setData(12);

        List<TestSubject> testSubjectList = List.of(testSubject, testSubject1, testSubject2);

        // 构建分组维度
        LinkedList<Function<? super TestSubject, ? extends String>> functionLinkedList = new LinkedList<>(ORIGIN_FUNCTION_LINKED_LIST);
        // 处理目标列表数据
        handleTargetList(testSubjectList, functionLinkedList);
    }

    /**
     * 构建分组维度
     *
     * @return 分组维度列表
     */
    private static LinkedList<Function<? super TestSubject, ? extends String>> getFunctionLinkedList() {
        LinkedList<Function<? super TestSubject, ? extends String>> functionLinkedList = new LinkedList<>();
        functionLinkedList.offer(TestSubject::getBrandName);
        functionLinkedList.offer(TestSubject::getCountry);
        functionLinkedList.offer(TestSubject::getState);

        return functionLinkedList;
    }

    /**
     * 处理目标列表数据
     *
     * @param testSubjectList    待统计整理的数据
     * @param functionLinkedList 类型列表.即分组维度
     */
    private static void handleTargetList(List<TestSubject> testSubjectList,
                                         LinkedList<Function<? super TestSubject, ? extends String>> functionLinkedList) {
        // 为了区分结果
        Type tempType = new Type();
        // 控制遍历,针对内部循环逻辑做限制
        Boolean insideLoop = false;
        // 循环处理数据
        cyclicProcessingData(tempType, functionLinkedList, testSubjectList, insideLoop);
    }

    /**
     * 循环处理数据
     *
     * @param type               区分分组维度，设置id
     * @param functionLinkedList 分组维度
     * @param testSubjectList    待统计整理的数据
     * @param insideLoop         是否为内部循环
     */
    private static void cyclicProcessingData(Type type,
                                             LinkedList<Function<? super TestSubject, ? extends String>> functionLinkedList,
                                             List<TestSubject> testSubjectList,
                                             Boolean insideLoop) {
        // 取分组维度进行处理
        Function<? super TestSubject, ? extends String> function = functionLinkedList.poll();
        if (Objects.isNull(function)) {
            return;
        }

        // 为了换个分组维度从头遍历,比如从品牌维度之后，需要从状态从头遍历
        LinkedList<Function<? super TestSubject, ? extends String>> tempFunctionLinkedList = null;
        if (!insideLoop) {
            tempFunctionLinkedList = new LinkedList<>(functionLinkedList);
        }

        // 为了内部横向遍历,不换分组维度，继续区分下一个分组维度,例如品牌分组之后，需要根据品牌、状态分组
        List<LinkedList<Function<? super TestSubject, ? extends String>>> tempFunctionLinkedListList = new ArrayList<>();
        while (!functionLinkedList.isEmpty()) {
            LinkedList<Function<? super TestSubject, ? extends String>> list = new LinkedList<>(functionLinkedList);
            tempFunctionLinkedListList.add(list);
            functionLinkedList.poll();
        }

        // 按分组维度进行分组
        Map<? extends String, List<TestSubject>> listMap = testSubjectList.stream().collect(Collectors.groupingBy(function));
        for (Map.Entry<? extends String, List<TestSubject>> entry : listMap.entrySet()) {
            String group = entry.getKey();
            List<TestSubject> testSubjects = entry.getValue();
            handlerType(type, function, group);

            TestSubject testSubject = summaryData(testSubjects);
            handleId(testSubject, type);
            System.out.println("testSubject = " + testSubject);

            // 内部横向遍历
            for (LinkedList<Function<? super TestSubject, ? extends String>> functions : tempFunctionLinkedListList) {
                // 为了不影响下一轮循环,比如两个品牌,都需要各自分别遍历处理
                LinkedList<Function<? super TestSubject, ? extends String>> linkedList = new LinkedList<>(functions);
                //  区分分组维度，设置id 重置
                Type tempType = type.clone();
                cyclicProcessingData(type, linkedList, testSubjects, true);
                type = tempType;
            }

        }

        // 如果是内部循环处理，直接返回，不需要换维度从头统计
        if (insideLoop) {
            return;
        }

        Type tempType = new Type();
        cyclicProcessingData(tempType, tempFunctionLinkedList, testSubjectList, false);
    }

    /**
     * 区分分组维度，设置id
     *
     * @param testSubject 统计整理之后的数据
     * @param type        区分分组维度，设置id
     */
    private static void handleId(TestSubject testSubject, Type type) {
        testSubject.setBrandName(type.getBrandName());
        testSubject.setCountry(type.getCountry());
        testSubject.setState(type.getState());

        String id = String.join("-", type.getBrandName(), type.getCountry(), type.getState());
        testSubject.setId(id);
    }

    /**
     * 统计整理数据
     *
     * @param testSubjects 待统计整理的数据
     * @return 统计整理后的数据
     */
    private static TestSubject summaryData(List<TestSubject> testSubjects) {
        TestSubject tempTestSubject = new TestSubject();
        Integer data = testSubjects.stream().map(TestSubject::getData).filter(Objects::nonNull).reduce(Integer::sum).orElse(0);
        tempTestSubject.setData(data);
        return tempTestSubject;
    }

    /**
     * 处理 Type 属性赋值,区分分组维度
     * <p>
     * 特别的，由于头部会poll,因此需要从后往前遍历
     *
     * @param type     区分分组维度，设置id
     * @param function 分组维度
     * @param group    分组后具体的值
     */
    private static void handlerType(Type type,
                                    Function<? super TestSubject, ? extends String> function,
                                    String group) {
        Integer index = null;
        for (int i = ORIGIN_FUNCTION_LINKED_LIST.size() - 1; i >= 0; i--) {
            Function<? super TestSubject, ? extends String> tempFunction = ORIGIN_FUNCTION_LINKED_LIST.get(i);
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
            default:

        }
    }

}
