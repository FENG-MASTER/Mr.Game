package com.lifegamer.fengmaster.lifegamer.manager.itf;

import com.lifegamer.fengmaster.lifegamer.model.Item;

import java.util.List;

/**
 * Created by qianzise on 2017/10/4.
 *
 * 物品管理器
 */

public interface IItemManager {

    /**
     * 添加新的物品
     * @param item 物品
     * @return 是否成功
     */
    boolean addItem(Item item);

    /**
     * 删除物品
     * @param name 物品名称
     * @return 是否成功
     */
    boolean removeItem(String name);

    /**
     * 删除物品
     * @param id 物品id
     * @return 是否成功
     */
    boolean removeItem(long id);

    /**
     * 使用物品
     * @param item 物品名称
     * @return 是否成功
     */
    boolean useItem(String item);

    /**
     * 使用物品
     * @param id 物品id
     * @return 是否成功
     */
    boolean useItem(long id);

    /**
     * 获取物品信息
     * @param item 物品名称
     * @return 物品
     */
    Item getItem(String item);

    /**
     * 根据ID获取物品
     * @param id 物品id
     * @return 物品
     */
    Item getItem(long id);

    /**
     * 获得所有物品列表
     * @return 物品列表
     */
    List<Item> getAllItem();


    /**
     * 获得特定分类的物品列表
     * @param type 分类
     * @return 列表
     */
    List<Item> getAllItem(String type);


    /**
     * 获得分类列表
     * @return 分类列表
     */
    List<String> getAllType();


    /**
     * 获得所有拥有的物品
     * @return
     */
    List<Item> getAllOwnItem();

    /**
     * 获得所有指定类型的拥有的物品
     * @param type 指定类型
     * @return
     */
    List<Item> getAllOwnItem(String type);


    /**
     * 更新物品信息
     * @param item 物品
     * @return 是否成功
     */
    boolean updateItem(Item item);
}
