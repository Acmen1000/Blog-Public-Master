package com.blog.Utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtils {
    private BeanCopyUtils(){

    }

    public static <V> V copyBean(Object source,Class<V> clazz){
        //创建目标对象
        //实现属性copy
        //返回结果
        V result=null;
        try{
            result=clazz.newInstance();
            BeanUtils.copyProperties(source,result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static <O,V>List<V> copyBeanList(List<O> list, Class<V> clazz){
            //转换stream流
           return list.stream()
                    .map(o->copyBean(o,clazz))
                    .collect(Collectors.toList());

    }
}
