package com.study.htmlCoding.util;

import com.study.htmlCoding.entity.Problem;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import cn.hutool.json.JSONException;

public class GenerateMainFunction {
    private static final String SOLUTION_CLASS_NAME = "Solution"; // 学生提交的核心代码将作为这个类的方法
    private static final String MAIN_CLASS_NAME = "Main"; // 包含 main 方法的包装类

    /**
     * 该方法被用来生成完整的可编译代码
     *
     * @param studentCode 学生代码
     * @param problem 问题的所有参数
     * @return fullCode 完整可编译代码
     */
    public static String generateMainFunction(String studentCode, Problem problem){
        StringBuilder fullCode = new StringBuilder();
        // 导入 Hutool JSON 库
        fullCode.append("import cn.hutool.json.JSONObject;\n");
        fullCode.append("import cn.hutool.json.JSONArray;\n");
        fullCode.append("import cn.hutool.json.JSONUtil;\n");
        fullCode.append("import cn.hutool.json.JSONException;\n"); // 导入可能的异常

        fullCode.append("import java.util.*;\n"); // 添加util库
        fullCode.append("import java.util.stream.*;\n"); // 添加stream库
        fullCode.append(studentCode); // 添加学生代码
        fullCode.append("public class ").append(MAIN_CLASS_NAME).append(" {\n");
        fullCode.append("    public static void main(String[] args) {\n");
        fullCode.append("        Scanner scanner = new Scanner(System.in);\n");
        fullCode.append("        ").append(SOLUTION_CLASS_NAME).append(" solution = new ").append(SOLUTION_CLASS_NAME).append("();\n\n");

        // 解析 problem.methodSignatureParams 字符串
        // 每个参数设定按照 参数名,数据类型,one/array/list 进行设置
        String[] methodSignatureParams = problem.getMethodSignatureParams().split(";");

        fullCode.append("        // 逐个测试用例执行\n");
        fullCode.append("        while (scanner.hasNextLine()) {\n");
        fullCode.append("            try {\n");
        fullCode.append("                String jsonInputStr = scanner.nextLine();\n"); // 读取整个 JSON 字符串
        fullCode.append("                JSONObject jsonInput = JSONUtil.parseObj(jsonInputStr);\n"); // 使用 Hutool 解析 JSON
        StringBuilder methodCallArgs = new StringBuilder(); // 用于构建方法调用时的参数列表
        for (int i = 0; i < methodSignatureParams.length; i++) {
            String[] param = methodSignatureParams[i].split(",");
            String paramName = param[0]; // 获取参数名
            String paramType1 = param[1]; // 获取数据类型
            String paramType2 = param[2]; // 获取数据类型在one/array/list/other中的一种
            if(paramType2.equals("one")){
                if(paramType1.equals("int")){
                    fullCode.append("                int ").append(paramName).append(" = jsonInput.getInt(\"").append(paramName).append("\");\n");
                } else if (paramType1.equals("long")) {
                    fullCode.append("                long ").append(paramName).append(" = jsonInput.getLong(\"").append(paramName).append("\");\n");
                } else if (paramType1.equals("double")) {
                    fullCode.append("                double ").append(paramName).append(" = jsonInput.getDouble(\"").append(paramName).append("\");\n");
                } else if (paramType1.equals("boolean")) {
                    fullCode.append("                boolean ").append(paramName).append(" = jsonInput.getBool(\"").append(paramName).append("\");\n");
                } else if (paramType1.equals("String")) {
                    fullCode.append("                String ").append(paramName).append(" = jsonInput.getStr(\"").append(paramName).append("\");\n");
                }
            } else if(paramType2.equals("array")){
                // int[] nums = Arrays.stream(Arrays.stream(word.trim().split(" ")).mapToInt(Integer::parseInt).toArray()).toArray();
                // String[] strings = word.trim().split(" ");
                fullCode.append("                JSONArray ").append(paramName).append("JsonArray = jsonInput.getJSONArray(\"").append(paramName).append("\");\n");
                // Hutool toArray 转换为 Object[]，需要根据实际类型强转或遍历
                if (paramType1.equals("int")) { // 转int数组
                    fullCode.append("                int[] ").append(paramName).append(" = ").append(paramName).append("JsonArray.stream().mapToInt(o -> ((Number)o).intValue()).toArray();\n");
                } else if (paramType1.equals("double")) { // 转double数组
                    fullCode.append("                double[] ").append(paramName).append(" = ").append(paramName).append("JsonArray.stream().mapToDouble(o -> ((Number)o).doubleValue()).toArray();\n");
                } else if (paramType1.equals("long")) { // 转long数组
                    fullCode.append("                long[] ").append(paramName).append(" = ").append(paramName).append("JsonArray.stream().mapToLong(o -> ((Number)o).longValue()).toArray();\n");
                } else if (paramType1.equals("String")) { // 转String数组
                    fullCode.append("                String[] ").append(paramName).append(" = ").append(paramName).append("JsonArray.stream().map(o -> o.toString()).toArray(String[]::new);\n");
                }
                // TODO 自定义对象
            } else if(paramType2.equals("list")){
                fullCode.append("                JSONArray ").append(paramName).append("JsonArray = jsonInput.getJSONArray(\"").append(paramName).append("\");\n");
                fullCode.append("                List<").append(paramType1).append("> ").append(paramName).append(" = JSONUtil.toList(").append(paramName).append("JsonArray, ").append(paramType1).append(".class);\n");
            } else if(paramType2.equals("other")) {
                // 假设是自定义对象类型（如 Student）
                fullCode.append("                ").append(paramType1).append(" ").append(paramName).append(" = jsonInput.getJSONObject(\"").append(paramName).append("\").toBean(").append(paramType1).append(".class);\n");
            }


            // 拼接方法中的参数
            methodCallArgs.append(paramName);
            if (i < methodSignatureParams.length - 1) {
                methodCallArgs.append(", ");
            }
        }

        // 调用学生的 Solution 方法
        fullCode.append("                ").append(problem.getReturnType()).append(" result = solution.").append(problem.getMethodName()).append("(").append(methodCallArgs).append(");\n");

        // 打印结果到标准输出，根据返回值类型格式化输出
        // 例如，数组和List需要特殊处理以便正确比较
        if (problem.getReturnType().endsWith("[]")) { // 数组类型
            fullCode.append("                System.out.println(Arrays.toString(result));\n");
        } else if (problem.getReturnType().startsWith("List<")) { // List 类型
            fullCode.append("                System.out.println(result);\n"); // 逗号分隔
        } else if (problem.getReturnType().equals("void")) {
            fullCode.append("                System.out.println(\"VOID_RETURN\");\n"); // 对于 void 方法，可能打印一个特殊标记
        } else {
            fullCode.append("                System.out.println(result);\n");
        }
        fullCode.append("            } catch (Exception e) {\n");
        fullCode.append("                // 捕获运行时错误并输出，前端可以捕获这个特殊的输出\n");
        fullCode.append("                System.err.println(\"Runtime Error Occurred: \" + e.getClass().getName() + \": \" + e.getMessage());\n");
        fullCode.append("                // e.printStackTrace(System.err);\n"); // 调试时可以打印堆栈
        fullCode.append("                break;\n"); // 如果输入格式错误或运行时错误，则终止后续测试用例
        fullCode.append("            }\n");
        fullCode.append("        }\n");
        fullCode.append("        scanner.close();\n");
        fullCode.append("    }\n");
        fullCode.append("}\n");

        return fullCode.toString();
    }
}
