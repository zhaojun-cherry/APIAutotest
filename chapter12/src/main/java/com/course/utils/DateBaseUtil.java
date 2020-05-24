package com.course.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.*;
import org.apache.tomcat.util.file.ConfigurationSource;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;

public class DateBaseUtil {
    public static SqlSession getSqlSession() throws IOException {

        //获取配置的资源文件
        Reader reader = Resources.getResourceAsReader("dataBaseConfig.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);

       //sqlsession能执行配置文件中的sql 语句
        SqlSession sqlSession=factory.openSession();
        return sqlSession;
    }
}
