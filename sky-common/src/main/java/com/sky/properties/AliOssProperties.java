package com.sky.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 阿里云OSS配置属性类
 * 注意：accessKeyId和accessKeySecret是敏感信息，请确保通过环境变量或安全的配置管理方式提供，
 * 避免在代码或配置文件中硬编码这些凭证
 */
@Component
@ConfigurationProperties(prefix = "sky.alioss")
@Data
public class AliOssProperties {

    /**
     * OSS服务端点
     */
    private String endpoint;
    
    /**
     * 访问密钥ID - 敏感信息，请安全存储
     */
    private String accessKeyId;
    
    /**
     * 访问密钥密码 - 敏感信息，请安全存储
     */
    private String accessKeySecret;
    
    /**
     * OSS存储桶名称
     */
    private String bucketName;

}
