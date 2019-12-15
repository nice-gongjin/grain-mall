package com.grain.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.SpringVersion;

@SpringBootApplication
@MapperScan(basePackages = "com.grain.mall.mapper") // 扫描 Mapper 文件夹 【注：根据自己的项目结构配置】
public class MallApplication {

    public static void main(String[] args) {
        // 启动颜色格式化 这不是唯一启动颜色格式的方式，有兴趣的同学可以查看源码
        AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);
        new SpringApplicationBuilder(MallApplication.class)
                .main(SpringVersion.class)  // 这个是为了可以加载 Spring 版本
                .bannerMode(Banner.Mode.CONSOLE)    // 控制台打印
                .run(args);
    }

}
