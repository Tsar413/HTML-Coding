package com.study.htmlCoding.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.htmlCoding.dto.*;
import com.study.htmlCoding.entity.Problem;
import com.study.htmlCoding.entity.SubmissionRecords;
import com.study.htmlCoding.entity.TestCase;
import com.study.htmlCoding.mapper.ProblemMapper;
import com.study.htmlCoding.mapper.SubmissionRecordsMapper;
import com.study.htmlCoding.mapper.TestCaseMapper;
import com.study.htmlCoding.service.IStudentService;
import com.study.htmlCoding.util.GenerateMainFunction;
import org.springframework.stereotype.Service;
import org.springframework.core.io.ClassPathResource;

import java.nio.file.StandardCopyOption;
import javax.annotation.Resource;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class StudentServiceImpl implements IStudentService {

    @Resource
    private ProblemMapper problemMapper;

    @Resource
    private TestCaseMapper testCaseMapper;

    @Resource
    private SubmissionRecordsMapper submissionRecordsMapper;

    // 编译和运行 Java 代码的临时目录
    private static final String WORK_DIR_PREFIX = System.getProperty("java.io.tmpdir") + File.separator + "judge_work_";
    private static final long TIME_LIMIT_MS = 2000; // 单个测试用例执行时间限制（毫秒）
    private static final String MAIN_CLASS_NAME = "Main"; // 假设学生提交的代码主类名为 Main
//    private static final String HUTOOL_ALL_JAR_PATH = "F:\\JavaProgram\\java_workspace\\study-htmlCoding\\src\\main\\java\\com\\study\\htmlCoding\\lib\\hutool-all-5.7.17.jar";
    private static final String HUTOOL_JAR_CLASSPATH = "lib/hutool-all-5.7.17.jar";

    /**
     * 对学生提交的 Java 代码进行编译和判题
     *
     * @param codeRequestDTO 用户提交的所有内容
     * @return 判题结果 JudgeResult
     */
    @Override
    public CodeResultDTO submitCode(CodeRequestDTO codeRequestDTO) {
        // TODO token处理

        // 创建一个独立的工作目录，避免不同提交之间的干扰
        String workDirPath = WORK_DIR_PREFIX + UUID.randomUUID().toString();
        Path workDir = Paths.get(workDirPath);
        CodeResultDTO codeResultDTO = new CodeResultDTO();

        Long maxSubmissionId = submissionRecordsMapper.getMaxSubmissionRecordId();
//        Long maxSubmissionId = submissionRecordsDao.getMaxSubmissionRecordId();
        if(maxSubmissionId == null){
            maxSubmissionId = 1L;
        } else {
            maxSubmissionId++;
        }

        /**
         * 核心代码，编译执行Java代码
         * 将执行结果TestCaseResultDTO存入List
         * 根据List进行判断
         */

        try {

            Files.createDirectories(workDir); // 创建工作目录

            // 1. 动态生成完整的Java代码
            QueryWrapper<Problem> wrapper = new QueryWrapper<Problem>();
            wrapper.eq("problem_id", codeRequestDTO.getProblemId());
            String fullCode = GenerateMainFunction.generateMainFunction(codeRequestDTO.getCode(), problemMapper.selectOne(wrapper));
//            String fullCode = GenerateMainFunction.generateMainFunction(codeRequestDTO.getCode(), problemDao.getProblemWithId(codeRequestDTO.getProblemId()));
            System.out.println(fullCode);
            Path sourceFile = workDir.resolve(MAIN_CLASS_NAME + ".java");
            Files.write(sourceFile, fullCode.getBytes());
            System.out.println("代码已保存到: " + sourceFile.toAbsolutePath());

            // 2. 编译代码
//            String classpath = HUTOOL_ALL_JAR_PATH + File.pathSeparator + workDir.toAbsolutePath();
            String hutoolJarPath = prepareHutoolJar(workDir);
            String classpath = hutoolJarPath + File.pathSeparator + workDir.toAbsolutePath();
            Process compileProcess = Runtime.getRuntime().exec(new String[]{"javac", "-encoding", "UTF-8", "-classpath", classpath, sourceFile.toAbsolutePath().toString()}, null, workDir.toFile());
            String compileOutput = readProcessOutput(compileProcess.getErrorStream());
            int compileExitCode = compileProcess.waitFor();

            if (compileExitCode != 0) {
                // 编译失败
                System.err.println("编译失败: " + compileOutput);
                codeResultDTO.setStatus(400);
                codeResultDTO.setMessage("编译错误");
                codeResultDTO.setCompileOutput(compileOutput);
                // 保存历史记录
                submissionRecordsMapper.saveNewSubmissionRecordFailed(maxSubmissionId, codeRequestDTO.getUserId(),
                        codeRequestDTO.getProblemId(), codeRequestDTO.getCode(), null, null,
                        codeResultDTO.getMessage(), null, null, null,
                        codeResultDTO.getCompileOutput(), null);// 保存编译错误的代码
//                submissionRecordsDao.saveNewSubmissionRecordFailed(maxSubmissionId, codeRequestDTO.getUserId(),
//                        codeRequestDTO.getProblemId(), codeRequestDTO.getCode(), null, null,
//                        codeResultDTO.getMessage(), null, null, null,
//                        codeResultDTO.getCompileOutput(), null); // 保存编译错误的代码
                return codeResultDTO;
            }
            System.out.println("代码编译成功！");

            // 3. 执行代码并进行参数化测试
            boolean allPassed = true;
            // 获取对应问题的全部测试用例
            QueryWrapper<TestCase> wrapper1 = new QueryWrapper<TestCase>();
            wrapper1.eq("problem_id", codeRequestDTO.getProblemId()).eq("deleted", "1");
            List<TestCase> testCases = testCaseMapper.selectList(wrapper1);
//            List<TestCase> testCases = testCaseDao.getTestCasesWithId(codeRequestDTO.getProblemId());
            // 保存全部的测试结果
            List<TestCaseResultDTO> testCaseResults = new ArrayList<TestCaseResultDTO>();
            // 如果出现执行错误，则只保存第一次出现错误的输入与输出
            AtomicBoolean recordFlag = new AtomicBoolean(true);

            for (int i = 0; i < testCases.size(); i++) {
                TestCase testCase = testCases.get(i);
                ExecutorService executor = Executors.newSingleThreadExecutor();
                TestCaseResultDTO caseResult = new TestCaseResultDTO();

                Future<String> future = executor.submit(() -> {
                    try {
                        // 在 classpath 中添加 Hutool JAR 包
                        Process runProcess = Runtime.getRuntime().exec(new String[]{"java", "-classpath", classpath, MAIN_CLASS_NAME}, null, workDir.toFile());

                        // 将测试用例的 JSON 输入写入到程序的标准输入
                        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(runProcess.getOutputStream()))) {
                            writer.write(testCase.getInput());
                            writer.flush();
                        }

                        // 读取程序标准输出和错误输出
                        String actualOutput = readProcessOutput(runProcess.getInputStream());
                        String runErrorOutput = readProcessOutput(runProcess.getErrorStream());

                        int runExitCode = runProcess.waitFor();

                        if (runErrorOutput.contains("Runtime Error Occurred:")) {
                            if(recordFlag.get()){
                                caseResult.setInput(testCase.getInput());
                                recordFlag.set(false);
                            }
                            caseResult.setPassed(false);
                            caseResult.setErrorInfo("运行时错误:\n" + runErrorOutput);
                            System.err.println("运行时错误: " + runErrorOutput);
                            return "Runtime Error";
                        }
                        else if (runExitCode != 0) {
                            if(recordFlag.get()){
                                caseResult.setInput(testCase.getInput());
                                recordFlag.set(false);
                            }
                            caseResult.setPassed(false);
                            caseResult.setErrorInfo("运行时错误 (非零退出码):\n" + runErrorOutput);
                            System.err.println("运行时错误 (非零退出码): " + runErrorOutput);
                            return "Runtime Error";
                        } else {
                            // 比较输出，现在输入和输出都是 JSON，需要进行规范化比较
                            // Hutool 的 toJsonStr 默认会格式化JSON，可能包含多余空格和换行。
                            // 建议将实际输出和预期输出都通过 Hutool 的 JSONUtil.parseObj().toStringPretty() 或类似方法规范化后再比较。
                            // 这里简化为直接字符串比较，但去除了空白。
                            String trimmedActualOutput = actualOutput.trim();
                            String trimmedExpectedOutput = testCase.getExpectedOutput().trim();

                            if (trimmedActualOutput.equals(trimmedExpectedOutput)) {
                                caseResult.setPassed(true);
                            } else {
                                if(recordFlag.get()){
                                    caseResult.setInput(testCase.getInput());
                                    caseResult.setExpectedOutput(testCase.getExpectedOutput());
                                    caseResult.setActualOutput(actualOutput);
                                    recordFlag.set(false);
                                }
                                caseResult.setPassed(false);
                                caseResult.setErrorInfo("答案错误");
                            }
                            return actualOutput;
                        }
                    } catch (IOException e) {
                        System.err.println("执行 Java 程序时发生 IO 错误: " + e.getMessage());
                        return "Internal IO Error";
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.err.println("Java 程序执行被中断 (可能是超时): " + e.getMessage());
                        return "Interrupted";
                    } finally {
                        // 在子线程任务中关闭进程的输入流和错误流，虽然父线程的readProcessOutput会关闭
                        // 这里主要是确保即使超时，子进程的IO流也能被处理
                    }
                });

                long startTime = System.currentTimeMillis();
                try {
                    String executionResult = future.get(TIME_LIMIT_MS, TimeUnit.MILLISECONDS);
                    caseResult.setTimeUsedMs(System.currentTimeMillis() - startTime);

                    if ("Runtime Error".equals(executionResult) || "Internal IO Error".equals(executionResult) || "Interrupted".equals(executionResult)) {
                        allPassed = false;
                    } else if (caseResult.getPassed() != null && !caseResult.getPassed()) {
                        allPassed = false;
                    }
                } catch (TimeoutException e) {
                    future.cancel(true); // 尝试中断任务
                    if(recordFlag.get()){
                        caseResult.setInput(testCase.getInput());
                        recordFlag.set(false);
                    }
                    caseResult.setPassed(false);
                    caseResult.setErrorInfo("执行超时");
                    caseResult.setTimeUsedMs(TIME_LIMIT_MS);
                    System.err.println("程序执行超时 for test case " + testCase.getTestCaseId());
                    allPassed = false;
                } catch (InterruptedException | ExecutionException e) {
                    if(recordFlag.get()){
                        caseResult.setInput(testCase.getInput());
                        recordFlag.set(false);
                    }
                    caseResult.setPassed(false);
                    caseResult.setErrorInfo("系统执行错误: " + e.getMessage());
                    System.err.println("执行或获取结果时发生错误: " + e.getMessage());
                    allPassed = false;
                } finally {
                    // 确保在每个测试用例的 Future 完成后，都关闭线程池
                    executor.shutdownNow(); // 立即关闭线程池，中断所有正在执行的任务
                    try {
                        // 等待所有任务终止，最多等待一个短时间
                        if (!executor.awaitTermination(100, TimeUnit.MILLISECONDS)) {
                            System.err.println("Executor did not terminate in time for test case " + testCase.getTestCaseId());
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt(); // 重新设置中断状态
                    }
                }
                testCaseResults.add(caseResult);
            }

            System.out.println(testCaseResults);
            boolean isPassed = true;
            // 判断结果是否匹配
            for (int i = 0; i < testCaseResults.size(); i++) {
                TestCaseResultDTO testCaseResultDTO = testCaseResults.get(i);
                if(testCaseResultDTO.getErrorInfo() != null && testCaseResultDTO.getErrorInfo().contains("超时")){
                    codeResultDTO.setStatus(402);
                    codeResultDTO.setMessage("部分测试用例执行超时");
                    codeResultDTO.setPassedNumber(i);
                    codeResultDTO.setAllNumber(testCaseResults.size());
                    codeResultDTO.setPassed(testCaseResultDTO.getPassed());
                    codeResultDTO.setInput(testCaseResultDTO.getInput());
                    codeResultDTO.setExpectedOutput(testCaseResultDTO.getExpectedOutput());
                    codeResultDTO.setActualOutput(testCaseResultDTO.getActualOutput());
                    codeResultDTO.setErrorInfo(testCaseResultDTO.getErrorInfo());
                    codeResultDTO.setTimeUsedMs(testCaseResultDTO.getTimeUsedMs());
                    isPassed = false;
                    break;
                } else if (testCaseResultDTO.getErrorInfo() != null && testCaseResultDTO.getErrorInfo().contains("运行时错误")) {
                    codeResultDTO.setStatus(401);
                    codeResultDTO.setMessage("部分测试用例运行时错误");
                    codeResultDTO.setPassedNumber(i);
                    codeResultDTO.setAllNumber(testCaseResults.size());
                    codeResultDTO.setPassed(testCaseResultDTO.getPassed());
                    codeResultDTO.setInput(testCaseResultDTO.getInput());
                    codeResultDTO.setExpectedOutput(testCaseResultDTO.getExpectedOutput());
                    codeResultDTO.setActualOutput(testCaseResultDTO.getActualOutput());
                    codeResultDTO.setErrorInfo(testCaseResultDTO.getErrorInfo());
                    codeResultDTO.setTimeUsedMs(testCaseResultDTO.getTimeUsedMs());
                    isPassed = false;
                    break;
                } else if (testCaseResultDTO.getErrorInfo() != null && testCaseResultDTO.getErrorInfo().contains("答案错误")) {
                    codeResultDTO.setStatus(403);
                    codeResultDTO.setMessage("部分测试用例答案错误");
                    codeResultDTO.setPassedNumber(i);
                    codeResultDTO.setAllNumber(testCaseResults.size());
                    codeResultDTO.setPassed(testCaseResultDTO.getPassed());
                    codeResultDTO.setInput(testCaseResultDTO.getInput());
                    codeResultDTO.setExpectedOutput(testCaseResultDTO.getExpectedOutput());
                    codeResultDTO.setActualOutput(testCaseResultDTO.getActualOutput());
                    testCaseResultDTO.setErrorInfo("部分测试用例答案错误");
                    codeResultDTO.setErrorInfo(testCaseResultDTO.getErrorInfo());
                    codeResultDTO.setTimeUsedMs(testCaseResultDTO.getTimeUsedMs());
                    isPassed = false;
                    break;
                } else {
                    codeResultDTO.setStatus(200);
                    codeResultDTO.setMessage("所有测试用例通过！");
                    codeResultDTO.setPassedNumber(i + 1);
                    codeResultDTO.setAllNumber(testCaseResults.size());
                }
            }

            if(!isPassed){
                // 保存测试失败的代码
//                submissionRecordsDao.saveNewSubmissionRecordFailed(maxSubmissionId, codeRequestDTO.getUserId(),
//                        codeRequestDTO.getProblemId(), codeRequestDTO.getCode(), codeResultDTO.getPassedNumber(), codeResultDTO.getAllNumber(),
//                        codeResultDTO.getMessage(), codeResultDTO.getInput(), codeResultDTO.getExpectedOutput(), codeResultDTO.getActualOutput(),
//                        codeResultDTO.getCompileOutput(), codeResultDTO.getTimeUsedMs());
                submissionRecordsMapper.saveNewSubmissionRecordFailed(maxSubmissionId, codeRequestDTO.getUserId(),
                        codeRequestDTO.getProblemId(), codeRequestDTO.getCode(), codeResultDTO.getPassedNumber(), codeResultDTO.getAllNumber(),
                        codeResultDTO.getMessage(), codeResultDTO.getInput(), codeResultDTO.getExpectedOutput(), codeResultDTO.getActualOutput(),
                        codeResultDTO.getCompileOutput(), codeResultDTO.getTimeUsedMs());
            } else {
                // 保存测试通过代码
//                submissionRecordsDao.saveNewSubmissionRecordPassed(maxSubmissionId, codeRequestDTO.getUserId(),
//                        codeRequestDTO.getProblemId(), codeRequestDTO.getCode(), codeResultDTO.getPassedNumber(),
//                        codeResultDTO.getAllNumber(), codeResultDTO.getTimeUsedMs());
                submissionRecordsMapper.saveNewSubmissionRecordPassed(maxSubmissionId, codeRequestDTO.getUserId(),
                        codeRequestDTO.getProblemId(), codeRequestDTO.getCode(), codeResultDTO.getPassedNumber(),
                        codeResultDTO.getAllNumber(), codeResultDTO.getTimeUsedMs());
            }

        } catch (IOException | InterruptedException e) {
            System.err.println("判题过程中发生 IO 或中断错误: " + e.getMessage());
            codeResultDTO.setStatus(500);
            codeResultDTO.setMessage("判题系统内部错误: " + e.getMessage());
        } finally {
            // 清理临时工作目录
            try {
                if (workDir != null && Files.exists(workDir)) {
                    // Files.walk 用于遍历目录下的所有文件和子目录，并按逆序删除
                    Files.walk(workDir)
                            .sorted(java.util.Comparator.reverseOrder())
                            .map(Path::toFile)
                            .forEach(File::delete);
                    System.out.println("已清理工作目录: " + workDir.toAbsolutePath());
                }
            } catch (IOException e) {
                System.err.println("清理工作目录失败: " + e.getMessage());
            }
        }
        System.out.println(codeResultDTO);
        return codeResultDTO;
    }

    /**
     * 从输入流读取所有内容并转换为字符串
     *
     * @param inputStream 输入流
     * @return 读取到的字符串
     * @throws IOException IO 异常
     */
    private String readProcessOutput(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        }
        return output.toString();
    }

    /**
     * 页面提交请求获取问题集
     *
     * @param userDTO 用户信息
     * @return 查询结果 由Problem组成的list
     */
    @Override
    public ProblemConsultDTO getAllProblems(UserDTO userDTO) {
        // TODO token处理

        // 获取所有问题
        QueryWrapper<Problem> wrapper = new QueryWrapper<Problem>();
        wrapper.eq("deleted", "1");
        List<Problem> allProblems = problemMapper.selectList(wrapper);
//        List<Problem> allProblems = problemDao.getAllProblems();
        // 把参数换成正常格式
        for (Problem problem : allProblems) {
            String[] methodSignatureParams = problem.getMethodSignatureParams().split(";");
            StringBuilder stringBuilder = new StringBuilder();
            for (int j = 0; j < methodSignatureParams.length; j++) {
                String[] strings = methodSignatureParams[j].split(",");
                if (strings[2].equals("one") || strings[2].equals("other")) {
                    stringBuilder.append(strings[1]).append(" ").append(strings[0]);
                } else if (strings[2].equals("array")) {
                    stringBuilder.append(strings[1]).append("[]").append(" ").append(strings[0]);
                } else if (strings[2].equals("list")) {
                    stringBuilder.append("List<").append(strings[1]).append("> ").append(strings[0]);
                }
                if (j < methodSignatureParams.length - 1) {
                    stringBuilder.append(", ");
                }
            }
            problem.setMethodSignatureParams(stringBuilder.toString());
        }

        ProblemConsultDTO problemConsultDTO = new ProblemConsultDTO();
        problemConsultDTO.setStatus(200);
        problemConsultDTO.setProblems(allProblems);
        return problemConsultDTO;
    }

    /**
     * 根据提交用户与题目编号请求所有提交记录
     *
     * @param codeRequestDTO 代码请求 使用其中的problemId与userId
     * @return 查询结果 由submissionRecords组成的list
     */
    @Override
    public SubmissionHistoryDTO getSubmissions(CodeRequestDTO codeRequestDTO) {
        // TODO token处理

        // 获取全部查询记录
        SubmissionHistoryDTO submissionHistoryDTO = new SubmissionHistoryDTO();
        submissionHistoryDTO.setStatus(200);
        QueryWrapper<SubmissionRecords> wrapper = new QueryWrapper<SubmissionRecords>();
        wrapper.eq("school_id", codeRequestDTO.getUserId()).eq("problem_id", codeRequestDTO.getProblemId());
        submissionHistoryDTO.setSubmissionRecords(submissionRecordsMapper.selectList(wrapper));
//        submissionHistoryDTO.setSubmissionRecords(submissionRecordsDao.getSubmissionRecordByProblemIdSchoolId(codeRequestDTO.getUserId(), codeRequestDTO.getProblemId()));
        System.out.println(submissionHistoryDTO);
        return submissionHistoryDTO;
    }

    /**
     * 根据用户的ID获取所有做题记录
     *
     * @param userDTO userDTO包含用户的ID和token
     * @return 查询结果 由submissionRecords组成的list
     */
    @Override
    public SubmissionHistoryDTO getAllSubmissions(UserDTO userDTO) {
        // TODO token处理

        // 获取全部查询记录
        SubmissionHistoryDTO submissionHistoryDTO = new SubmissionHistoryDTO();
        submissionHistoryDTO.setStatus(200);
        QueryWrapper<SubmissionRecords> wrapper = new QueryWrapper<SubmissionRecords>();
        wrapper.eq("school_id", userDTO.getId()).orderByDesc("submission_record_id");
        List<SubmissionRecords> submissions = submissionRecordsMapper.selectList(wrapper);
//        List<SubmissionRecords> submissions = submissionRecordsDao.getSubmissionRecordBySchoolId(userDTO.getId());
        submissionHistoryDTO.setSubmissionRecords(submissions);
        System.out.println(submissionHistoryDTO);
        return submissionHistoryDTO;
    }

    /**
     * 从 classpath 中提取 Hutool jar 到临时目录，返回可供 javac/java 使用的真实文件路径
     */
    private String prepareHutoolJar(Path workDir) throws IOException {
        ClassPathResource resource = new ClassPathResource(HUTOOL_JAR_CLASSPATH);

        if (!resource.exists()) {
            throw new FileNotFoundException("未找到 Hutool 依赖文件: " + HUTOOL_JAR_CLASSPATH);
        }

        Path jarTarget = workDir.resolve("hutool-all-5.7.17.jar");

        try (InputStream inputStream = resource.getInputStream()) {
            Files.copy(inputStream, jarTarget, StandardCopyOption.REPLACE_EXISTING);
        }

        return jarTarget.toAbsolutePath().toString();
    }
}
