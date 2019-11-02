package com.fh.shop.apiv4.util;

import redis.clients.jedis.Jedis;

public class RedisUtil {
    //对redis缓存服务器的操作方法

    //新增
    public static void set (String key, String value){
            Jedis resource = null;
        try {
            resource = RedisPool.getResource();
            resource.set(key,value);
        } catch (Exception e) {
            e.printStackTrace();
            //抛异常
            throw new RuntimeException(e.getMessage());
        } finally {
            if (resource != null){
                resource.close();
            }
        }
    }

    //删除
    public static void del(String key){
        Jedis resource =null;
        try {
             resource = RedisPool.getResource();
            resource.del(key);
        } catch (Exception e) {
            e.printStackTrace();
            //抛异常
            throw new RuntimeException(e.getMessage());
        } finally {
            if (resource != null){
                resource.close();
            }
        }
    }

    //查询
    public static String get(String key){
        Jedis resource =null;
        String result = null;
        try {
             resource = RedisPool.getResource();
             result = resource.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            //抛异常
            throw new RuntimeException(e.getMessage());
        } finally {
            if (resource != null){
                resource.close();
            }
        }
        return result;
    }

    //为已经存在的key设置生命周期
    public static void expire(String key ,int secondes){
            Jedis resource =null;
        try {
             resource = RedisPool.getResource();
            resource.expire(key , secondes);
        } catch (Exception e) {
            e.printStackTrace();
            //抛异常
            throw new RuntimeException(e.getMessage());
        } finally {
            if (resource != null){
                resource.close();
            }
        }
    }

    //创建一个新的key  并给他设置一个生命周期
    public static void setex(String key , int secondes, String value){
        Jedis resource =null;
        try {
             resource = RedisPool.getResource();
            resource.setex(key,secondes ,value);
        } catch (Exception e) {
            e.printStackTrace();
            //抛异常
            throw new RuntimeException(e.getMessage());
        } finally {
            if (resource != null){
                resource.close();
            }
        }
    }

    //判断一个key是否存在
    public static boolean exist(String key ){
        Jedis resource =null;
        boolean exist = false;
        try {
            resource = RedisPool.getResource();
            exist = resource.exists(key);
        } catch (Exception e) {
            e.printStackTrace();
            //抛异常
            throw new RuntimeException(e.getMessage());
        } finally {
            if (resource != null){
                resource.close();
            }
        }
        return exist;
    }

    //==================================hash============================================================


    //新增
    public static void hset(String key, String field, String value){
        Jedis resource =null;
        try {
            resource = RedisPool.getResource();
            resource.hset(key,field ,value);
        } catch (Exception e) {
            e.printStackTrace();
            //抛异常
            throw new RuntimeException(e.getMessage());
        } finally {
            if (resource != null){
                resource.close();
            }
        }
    }


    //获取
    public static String hget(String key, String field){
        Jedis resource = null;
        String result = "";
        try {
            resource = RedisPool.getResource();
            result = resource.hget(key, field);
        } catch (Exception e) {
            e.printStackTrace();
            //抛异常
            throw new RuntimeException(e.getMessage());
        } finally {
            if (resource != null){
                resource.close();
            }
        }
        return result;
    }

    public static void hdel(String key,String field){
        Jedis jedis = null;
        try {
            jedis = RedisPool.getResource();
            jedis.hdel(key,field);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }
    }


    //解决重复登录问题
    //这是删除
    public static long delCount(String key) {
        Jedis recource = null;
        Long delCount =0L;
        try {
            recource = RedisPool.getResource();
            delCount = recource.del(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            if (null != recource) {
                recource.close();
            }
        }
        return delCount;
    }


}
