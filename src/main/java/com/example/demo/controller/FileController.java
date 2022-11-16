package com.example.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import common.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * @ClassName: FileController
 * @Author: sxl
 * @Description: 自定义规则文件控制类
 * @Date: 2022/9/24 10:33
 * @Version: 1.0
 */
@Api(tags = "自定义规则API")
@RestController
@RequestMapping("/file")
public class FileController {

    private final Logger logger = LoggerFactory.getLogger(FileController.class);

    private final String fileDir = (System.getProperty("user.dir") + "\\" ).replace("\\","/")
            +"src/main/resources/rule/";


    @PostMapping("/fileUpload")
    @ApiOperation("自定义规则文件上传")
    public CommonResult<String> fileUpload(@RequestParam("file") MultipartFile file) throws JsonProcessingException {
        // 获取文件名
        String fileName = file.getOriginalFilename();
        //判断是否为IE浏览器的文件名，IE浏览器下文件名会带有盘符信息

        // escaping dangerous characters to prevent XSS
        assert fileName != null;
        fileName = HtmlUtils.htmlEscape(fileName, StandardCharsets.UTF_8.name());

        // Check for Unix-style path
        int unixSep = fileName.lastIndexOf('/');
        // Check for Windows-style path
        int winSep = fileName.lastIndexOf('\\');
        // Cut off at latest possible point
        int pos = (Math.max(winSep, unixSep));
        if (pos != -1) {
            fileName = fileName.substring(pos + 1);
        }
        // 判断是否存在同名文件
        if (existsFile(fileName)) {
            return CommonResult.failed("存在同名文件，请先删除原有文件再次上传");
        }
        File outFile = new File(fileDir );
        if (!outFile.exists() && !outFile.mkdirs()) {
            logger.error("创建文件夹【{}】失败，请检查目录权限！", fileDir);
        }
        logger.info("上传文件：{}", fileDir  + fileName);
        try (InputStream in = file.getInputStream(); OutputStream out = new FileOutputStream(fileDir +  fileName)) {
            StreamUtils.copy(in, out);
            return CommonResult.success("上传成功");
        } catch (IOException e) {
            logger.error("文件上传失败", e);
            return CommonResult.failed("文件上传失败");
        }
    }

    @GetMapping("/deleteFile")
    @ApiOperation("自定义规则文件删除")
    public CommonResult<String> deleteFile(String fileName) throws JsonProcessingException {
        if (fileName.contains("/")) {
            fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
        }
        File file = new File(fileDir  + fileName);
        logger.info("删除文件：{}", file.getAbsolutePath());
        if (file.exists() && !file.delete()) {
            String msg = String.format("删除文件【%s】失败，请检查目录权限！", file.getPath());
            logger.error(msg);
            return CommonResult.failed(msg);
        }
        return CommonResult.success("文件删除成功");
    }

    @GetMapping("/listFiles")
    @ApiOperation("获取自定义规则列表")
    public List<Map<String, String>> getFiles() throws JsonProcessingException {
        List<Map<String, String>> list = new ArrayList<>();
        File file = new File(fileDir);
        if (file.exists()) {
            Arrays.stream(Objects.requireNonNull(file.listFiles())).forEach(file1 -> {
                Map<String, String> fileName = new HashMap<>();
                fileName.put("fileName",  "/" + file1.getName());
                list.add(fileName);
            });
        }
        return list;
    }

    private boolean existsFile(String fileName) {
        File file = new File(fileDir + fileName);
        return file.exists();
    }
}
