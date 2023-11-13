package com.ccm.document.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.InputStream;

/**
 * @description：文件信息
 * @author： kekai.huang
 * @create： 2023/10/26 9:45
 */
@Data
@AllArgsConstructor
public class FileInfoDTO {
    private String filename;
    private String originalFilename;
    private String contentType;
    private InputStream contentStream;
}
