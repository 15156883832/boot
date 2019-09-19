package com.spring.boot.base.redisConfig.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RedisServiceImpl{

    @Autowired
    private RedisTemplate redisTemplate;

    /*********************************** String数据结构 **********************************/
    /**
     *
     * 根据key存储value
     *
     **/
    public boolean set(String key, Object value) {
        ValueOperations vo = redisTemplate.opsForValue();
        vo.set(key,value);
        return true;
    }

    /**
     *
     * 根据key存储value,并且设置过期时间和单位,默认为分钟
     *
     **/
    public boolean set(String key, Object value, Long expireTime,TimeUnit timeUnit) {
        if(timeUnit==null){
            timeUnit = TimeUnit.MINUTES;
        }
        ValueOperations vo = redisTemplate.opsForValue();
        vo.set(key,value,expireTime,timeUnit);
        return true;
    }

    /**
     * 根据key批量删除
     *
     **/
    public void remove(Collection<String> collection) {
        redisTemplate.delete(collection);
    }

    /**
     * 删除多个key
     *
     * @param keys
     */
    public void deleteKey(String... keys) {
        Set<String> kSet = Stream.of(keys).map(k -> k).collect(Collectors.toSet());
        redisTemplate.delete(kSet);
    }

    /**
     *根据key删除
     *
     */
    public void remove(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 判断key是否存在
     *
     */
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 设置key的生命周期
     *
     * @param key
     * @param time
     * @param timeUnit
     */
    public void expireKey(String key, long time, TimeUnit timeUnit) {
        redisTemplate.expire(key, time, timeUnit);
    }

    /**
     * 指定key在指定的日期过期
     *
     * @param key
     * @param date
     */
    public void expireKeyAt(String key, Date date) {
        redisTemplate.expireAt(key, date);
    }

    /**
     * 查询key的生命周期
     *
     * @param key
     * @param timeUnit
     * @return
     */
    public long getKeyExpire(String key, TimeUnit timeUnit) {
        return redisTemplate.getExpire(key, timeUnit);
    }

    /**
     * 将key设置为永久有效
     *
     * @param key
     */
    public void persistKey(String key) {
        redisTemplate.persist(key);
    }

    /**
     * 根据key获取value
     *
     **/
    public Object get(String key) {
        ValueOperations vo = redisTemplate.opsForValue();
        return vo.get(key);
    }



    /**************************************** Hash数据结构 *****************************************/
    /**
     * Hash存值
     *
     */
    public void hmSet(String key, Map<String,Object> map) {
        HashOperations ho = redisTemplate.opsForHash();
        ho.putAll(key,map);
    }

    /**
     * Hash 取值
     * key  不能为null
     * item 不能为null
     *
     */
    public Object hmGet(String key,String item) {
        HashOperations ho = redisTemplate.opsForHash();
        return ho.get(key,item);
    }


    /**
     *
     * 获取hashkey对应的所有键值对
     *
     */
    public Map<Object,Object> hmget(String key){
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     *  Hash存值 并且设定 生命周期(多长时间之后失效)
     *
     *
     */
    public void hmSet(String key,Map<String,Object> map,long time,TimeUnit timeUnit){
        HashOperations ho = redisTemplate.opsForHash();
        ho.putAll(key,map);
        if(time > 0){
            expireKey(key,time,timeUnit);
        }
    }

    /**
     *  Hash存值 并且设定 生命周期(多少日期之后失效)
     *
     *
     */
    public void hmSet(String key,Map<String,Object> map,Date date){
        HashOperations ho = redisTemplate.opsForHash();
        ho.putAll(key,map);
        if(date!=null){
            expireKeyAt(key,date);
        }
    }

    /**
     *向一张hash表中放入数据
     *如果不存在将创建(过期时间设置的话可以直接调用公用里面的方法即可)
     *
     */
     public void hmSet(String key,String item,Object value){
        HashOperations ho = redisTemplate.opsForHash();
        ho.put(key,item,value);
     }

    /**
     *
     *根据key获取Hash集合的长度
     *
     */
     public long hmSize(String key){
         HashOperations ho = redisTemplate.opsForHash();
         return ho.size(key);
     }

     /**
      *
      * 删除hash表中的值
      *
      */
      public void hmDelete(String key,String item){
          HashOperations ho = redisTemplate.opsForHash();
          ho.delete(key,item);
      }

    /**
     * 判断hash表中是否有该项的值
     * @param key 键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hHasKey(String key, String item){
        return redisTemplate.opsForHash().hasKey(key, item);
    }


    /*************************************** list 集合先相关操作 ********************************/

    /**
     * 获取list所有集合
     *
     */
    public List<?> lGet(String key){
        ListOperations lo = redisTemplate.opsForList();
        try{
            return lo.range(key,0,-1); //0到-1代表获取所有值
        }catch(Exception e ){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取list的某些集合
     *
     * */
    public List<?> lGet(String key,long start,long end){
        ListOperations lo = redisTemplate.opsForList();
        try{
            return lo.range(key,start,end); //0到-1代表获取所有值
        }catch(Exception e ){
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 获取list集合的长度
     *
     */
    public long lSize(String key){
        ListOperations lo = redisTemplate.opsForList();
        try{
            return lo.size(key);
        }catch(Exception e ){
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 通过索引 获取list中的值
     * @param key 键
     * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public Object lGetIndex(String key,long index){
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将list放入缓存 ---有超时时间的直接调用公用的expire 获取expireAt方法
     * @param key 键
     * @param value 值
     * @return
     */
    public boolean lSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存  ---有超时时间的直接调用公用的expire 获取expireAt方法
     * @param key 键
     * @param value 值
     * @return
     */
    public boolean lSet(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     * @param key 键
     * @param index 索引
     * @param value 值
     * @return
     */
    public boolean lUpdateIndex(String key, long index,Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 移除N个值为value
     * @param key 键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public long lRemove(String key,long count,Object value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    /************************************* Set集合操作  *****************************************/

    /**
     * 根据key获取Set中的所有值
     * @param key 键
     * @return
     */
    public Set<Object> sGet(String key){
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 根据value从一个set中查询,是否存在
     * @param key 键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean sHasKey(String key,Object value){
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 将数据放入set缓存  ---有需要设置生命周期的直接调用公用的expire 获取expireAt方法
     * @param key 键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSet(String key, Object...values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 获取set缓存的长度
     * @param key 键
     * @return
     */
    public long sGetSetSize(String key){
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 移除值为value的
     * @param key 键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public long setRemove(String key, Object ...values) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 有序集合添加
     * @param key
     * @param value
     * @param scoure
     */
    public void zAdd(String key,Object value,double scoure){
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        zset.add(key,value,scoure);
    }

    public Set<Object> rangeByScore(String key, double scoure, double scoure1) {
        return null;
    }
}
