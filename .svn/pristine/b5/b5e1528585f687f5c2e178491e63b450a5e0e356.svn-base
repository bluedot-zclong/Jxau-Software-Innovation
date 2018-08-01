package cn.edu.jxau.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Json数据处理工具(fastjson实现)
 *
 */
public final class JsonUtil {

    public static SerializerFeature[] defaultFeatures = {SerializerFeature.WriteEnumUsingToString,
            SerializerFeature.WriteDateUseDateFormat};

    public static SerializerFeature[] prettyFeatures = {SerializerFeature.WriteEnumUsingToString,
            SerializerFeature.WriteDateUseDateFormat, SerializerFeature.PrettyFormat};

    public static synchronized void setDefaultFeatures(SerializerFeature[] defaultFeatures) {
        JsonUtil.defaultFeatures = defaultFeatures;
    }

    public static synchronized void setPrettyFeatures(SerializerFeature[] prettyFeatures) {
        JsonUtil.prettyFeatures = prettyFeatures;
    }

    public static String toJson(Object object) {
        return JSON.toJSONString(object, defaultFeatures);
    }

    public static String toJson(Object object, boolean pretty) {
        if (pretty) {
            return JSON.toJSONString(object, prettyFeatures);
        }
        return toJson(object);
    }

    public static int toJson(OutputStream out, Object object) throws IOException {
        return JSON.writeJSONString(out, object, defaultFeatures);
    }

    public static int toJson(OutputStream out, Object object, boolean pretty) throws IOException {
        if (pretty) {
            return JSON.writeJSONString(out, object, prettyFeatures);
        }
        return toJson(out, object);
    }

    public static int toJson(File dest, Object object) throws IOException {
        return toJson(new FileOutputStream(dest), object);
    }

    public static int toJson(File dest, Object object, boolean pretty) throws IOException {
        return toJson(new FileOutputStream(dest), object, pretty);
    }

    // ************************************************* //

    public Object parse(String text) {
        return JSON.parse(text);
    }

    public static <T> T parse(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    public static <T> T parse(InputStream in, Class<T> clazz) throws IOException {
        return JSON.parseObject(in, clazz);
    }

    public static <T> T parse(File src, Class<T> clazz) throws IOException {
        return parse(new FileInputStream(src), clazz);
    }

}