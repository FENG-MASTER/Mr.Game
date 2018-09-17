package com.lifegamer.fengmaster.lifegamer.model;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.lifegamer.fengmaster.lifegamer.BR;
import com.lifegamer.fengmaster.lifegamer.base.ICopy;
import com.lifegamer.fengmaster.lifegamer.dao.DBHelper;
import com.lifegamer.fengmaster.lifegamer.dao.itf.Deleteable;
import com.lifegamer.fengmaster.lifegamer.dao.itf.Insertable;
import com.lifegamer.fengmaster.lifegamer.dao.itf.Updateable;
import com.lifegamer.fengmaster.lifegamer.util.FormatUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by qianzise on 2017/10/4.
 *
 * 奖励实体类
 *
 * 奖励的定位是:玩家<b>购买</b>得到的相应奖励
 *
 * 没有 是否拥有 状态
 */

public class RewardItem extends BaseObservable implements Insertable,Deleteable,Updateable,ICopy<RewardItem>{

    /**
     * 奖励ID
     */
    private long id;

    /**
     * 奖励名称
     */
    private String name;

    /**
     * 奖励所属种类
     */
    private String type="未分类";

    /**
     * 奖励详细描述信息
     */
    private String desc;

    /**
     * 获得奖励所需LP点数
     * -1 表示不可购买(只能通过任务获得)
     */
    private int costLP;

    /**
     * 剩余奖励数目
     * -1表示无限
     */
    private int quantityAvailable;

    /**
     * 已经获得的次数
     */
    private int gainTimes;

    /**
     * 创建时间
     */
    private Date createTime=new Date();

    /**
     * 修改时间
     */
    private Date updateTime=new Date();

    /**
     * 奖励图标
     */
    private String icon;

    /**
     * 每次获得奖励后,奖励增加的LP价格
     */
    private int costLPIncrement;

    /**
     * 得到后是否添加到物品
     */
    private boolean addToItem;
    /**
     * 添加到物品后是否属于消耗品
     */
    private boolean expendable;

    /**
     * 笔记ID
     */
    private List<Integer> notes;

    public Item getItem(){
        if (!addToItem){
            return null;
        }
        //转换成物品
        Item item=new Item();
        item.setName(getName());
        item.setId(getId());
        item.setCreateTime(new Date());
        item.setExpendable(isExpendable());
        item.setDesc(getDesc());
        item.setIcon(getIcon());
        item.setType(getType());
        item.setQuantity(0);
        return item;

    }
    @Bindable
    public boolean isExpendable() {
        return expendable;
    }

    public void setExpendable(boolean expendable) {
        this.expendable = expendable;
        notifyPropertyChanged(BR.expendable);
    }
    @Bindable
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }
    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }
    @Bindable
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        notifyPropertyChanged(BR.type);
    }
    @Bindable
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
        notifyPropertyChanged(BR.desc);
    }
    @Bindable
    public int getCostLP() {
        return costLP;
    }

    public void setCostLP(int costLP) {
        this.costLP = costLP;
        notifyPropertyChanged(BR.costLP);
    }
    @Bindable
    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
        notifyPropertyChanged(BR.quantityAvailable);
    }
    @Bindable
    public int getGainTimes() {
        return gainTimes;
    }

    public void setGainTimes(int gainTimes) {
        this.gainTimes = gainTimes;
        notifyPropertyChanged(BR.gainTimes);
    }
    @Bindable
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
        notifyPropertyChanged(BR.createTime);
    }
    @Bindable
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        notifyPropertyChanged(BR.updateTime);
    }
    @Bindable
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
        notifyPropertyChanged(BR.icon);
    }
    @Bindable
    public int getCostLPIncrement() {
        return costLPIncrement;
    }

    public void setCostLPIncrement(int costLPIncrement) {
        this.costLPIncrement = costLPIncrement;
        notifyPropertyChanged(BR.costLPIncrement);
    }
    @Bindable
    public boolean isAddToItem() {
        return addToItem;
    }

    public void setAddToItem(boolean addToItem) {
        this.addToItem = addToItem;
        notifyPropertyChanged(BR.addToItem);
    }
    @Bindable
    public List<Integer> getNotes() {
        return notes;
    }

    public void setNotes(List<Integer> notes) {
        this.notes = notes;
        notifyPropertyChanged(BR.notes);
    }




    @Override
    public int delete(SQLiteDatabase sqLiteDatabase) {
        return sqLiteDatabase.delete(DBHelper.TABLE_REWARD,"_id = ? ",new String[]{String.valueOf(getId())});
    }

    @Override
    public int update(SQLiteDatabase sqLiteDatabase) {
        ContentValues cv=new ContentValues();
        cv.put("name",getName());
        cv.put("type",getType());
        cv.put("desc",getDesc());
        cv.put("costLP",getCostLP());
        cv.put("quantityAvailable",getQuantityAvailable());
        cv.put("gainTimes", getGainTimes());
        cv.put("icon",getIcon());
        cv.put("costLPIncrement",getCostLPIncrement());
        cv.put("addToItem",isAddToItem());
        cv.put("notes", FormatUtil.list2Str(notes));
        cv.put("createTime", SimpleDateFormat.getInstance().format(getCreateTime()));
        cv.put("updateTime",SimpleDateFormat.getInstance().format(getUpdateTime()));
        return sqLiteDatabase.update(DBHelper.TABLE_REWARD,cv," _id = ?",new String[]{String.valueOf(getId())});
    }

    @Override
    public long insert(SQLiteDatabase sqLiteDatabase) {
        ContentValues cv=new ContentValues();
        cv.put("name",getName());
        cv.put("type",getType());
        cv.put("desc",getDesc());
        cv.put("costLP",getCostLP());
        cv.put("quantityAvailable",getQuantityAvailable());
        cv.put("gainTimes", getGainTimes());
        cv.put("icon",getIcon());
        cv.put("costLPIncrement",getCostLPIncrement());
        cv.put("addToItem",isAddToItem());
        cv.put("notes", FormatUtil.list2Str(notes));
        cv.put("createTime", SimpleDateFormat.getInstance().format(getCreateTime()));
        cv.put("updateTime",SimpleDateFormat.getInstance().format(getUpdateTime()));


        return sqLiteDatabase.insert(DBHelper.TABLE_REWARD,null,cv);
    }

    @Override
    public void copyFrom(RewardItem rewardItem) {
        this.setId(rewardItem.getId());
        this.setName(rewardItem.getName());
        this.setType(rewardItem.getType());
        this.setDesc(rewardItem.getDesc());
        this.setCostLP(rewardItem.getCostLP());
        this.setQuantityAvailable(rewardItem.getQuantityAvailable());
        this.setGainTimes(rewardItem.getGainTimes());
        this.setIcon(rewardItem.getIcon());
        this.setCostLPIncrement(rewardItem.getCostLPIncrement());
        this.setAddToItem(rewardItem.isAddToItem());
        this.setNotes(rewardItem.getNotes());
        this.setCreateTime(rewardItem.getCreateTime());
        this.setUpdateTime(rewardItem.getUpdateTime());

    }
}
