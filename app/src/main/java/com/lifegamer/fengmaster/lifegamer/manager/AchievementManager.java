package com.lifegamer.fengmaster.lifegamer.manager;

import android.database.Cursor;

import com.annimon.stream.Collectors;
import com.annimon.stream.Optional;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Predicate;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.dao.DBHelper;
import com.lifegamer.fengmaster.lifegamer.event.achievement.DeleteAchievementEvent;
import com.lifegamer.fengmaster.lifegamer.event.achievement.NewAchievementEvent;
import com.lifegamer.fengmaster.lifegamer.log.LogPoint;
import com.lifegamer.fengmaster.lifegamer.manager.itf.IAchievementManager;
import com.lifegamer.fengmaster.lifegamer.model.Achievement;
import com.lifegamer.fengmaster.lifegamer.model.Log;
import com.lifegamer.fengmaster.lifegamer.model.randomreward.AchievementReward;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by qianzise on 2017/10/4.
 *
 * 本地成就管理器
 */

public class AchievementManager implements IAchievementManager{

    private List<Achievement> achievements =new ArrayList<>();

    private DBHelper helper=DBHelper.getInstance();

    public AchievementManager() {
        loadAchievementsFromSQL();
    }

    /**
     * 新增成就
     * @param achievement 成就
     * @return 是否成功
     */
    @Override
    public boolean addAchievement(Achievement achievement) {
        return _addAchievement(achievement);
    }

    @LogPoint(type = Log.TYPE.ACHIEVEMENT,action = Log.ACTION.CREATE,property = Log.PROPERTY.DEFAULT)
    private boolean _addAchievement(Achievement achievement){
        if (Stream.of(achievements).anyMatch(value -> value.getName().equals(achievement.getName()))){
            //已经存在相同名字的成就
            return updateAchievement(achievement);
        }else {

            long l = Game.insert(achievement);
            if (l!=0){
                achievement.setId((int) l);
                achievements.add(achievement);
                EventBus.getDefault().post(new NewAchievementEvent(achievement));
            }
            return l!=0;
        }
    }

    /**
     * 删除成就
     * @param name 成就名称
     * @return 是否成功
     */
    @Override
    public boolean removeAchievement(String name) {
        Achievement achievement = Stream.of(achievements).filter(value -> value.getName().equals(name)).findFirst().get();
        return _removeAchievement(achievement);
    }

    /**
     * 删除成就
     * @param id 成就id
     * @return 是否成功
     */
    @Override
    public boolean removeAchievement(long id) {
        Achievement achievement = Stream.of(achievements).filter(value -> value.getId() == id).findFirst().get();
        return _removeAchievement(achievement);
    }

    @LogPoint(type = Log.TYPE.ACHIEVEMENT,action = Log.ACTION.DELETE,property = Log.PROPERTY.DEFAULT)
    private boolean _removeAchievement(Achievement achievement){
        if (achievement!=null&&Game.delete(achievement)){
            achievements.remove(achievement);
            EventBus.getDefault().post(new DeleteAchievementEvent(achievement));
            return true;
        }else {
            return false;
        }
    }

    /**
     * 更新成就,如果id相同,会修改原来的成就
     * @param achievement 成就
     * @return 是否成功
     */
    @Override
    public boolean updateAchievement(Achievement achievement) {
       return _updateAchievement(achievement);
    }

    @LogPoint(type = Log.TYPE.ACHIEVEMENT,action = Log.ACTION.EDIT,property = Log.PROPERTY.DEFAULT)
    private boolean _updateAchievement(Achievement achievement){
        if(achievements.contains(achievement)){
            return Game.update(achievement);
        }else {
            Achievement t = Stream.of(achievements).
                    filter(value -> value.getName().equals(achievement.getName())).
                    findFirst().get();
            if (t!=null){
                //存在重复id,采用替换的方式
                t.copyFrom(achievement);
                return Game.update(t);
            }else {
                return false;
            }
        }
    }

    /**
     * 获得成就
     * @param id 成就ID
     * @return 是否成功
     */
    @Override
    public boolean gainAchievement(long id) {
        Achievement achievement = Stream.of(achievements).filter(value -> value.getId() == id).findFirst().get();
        return _gainAchievement(achievement);

    }

    /**
     * 获得成就
     * @param name 成就名称
     * @return 是否成功
     */
    @Override
    public boolean gainAchievement(String name) {
        Achievement achievement = Stream.of(achievements).filter(value -> value.getName().equals(name)).findFirst().get();
        return _gainAchievement(achievement);
    }

    /**
     * 获得成就
     * @param achievement 成就
     * @return 是否成功
     */
    @LogPoint(type = Log.TYPE.ACHIEVEMENT,action = Log.ACTION.GET,property = Log.PROPERTY.DEFAULT)
    private boolean _gainAchievement(Achievement achievement){
        if (achievement!=null){
            achievement.setGot(true);
            achievement.setGainTime(new Date());
            return updateAchievement(achievement);
        }else {
            return false;
        }
    }

    /**
     * 从概率型成就对象中获得成就
     * @param achievementReward 成就奖励
     * @return 是否成功
     */
    @Override
    public boolean gainAchievement(AchievementReward achievementReward) {
        if (achievementReward.isHit()){
            //hit
            return gainAchievement(achievementReward.getAchievementID());
        }else {
            return false;
        }
    }

    /**
     * 获得成就详情
     * @param id 成就ID
     * @return 成就
     */
    @Override
    public Achievement getAchievement(long id) {
        Optional<Achievement> achievementOptional = Stream.of(achievements).filter(value -> value.getId() == id).findFirst();
        if (achievementOptional.isPresent()){
            return achievementOptional.get();
        }else {
            return null;
        }
    }

    /**
     * 获得所有成就
     * @return 成就列表
     */
    @Override
    public List<Achievement> getAllAchievement() {
        return achievements;
    }

    /**
     * 获得所有 尚未获得 的成就列表
     * @return 成就列表
     */
    @Override
    public List<Achievement> getAllNoGetAchievment() {
        return Stream.of(achievements).filterNot(Achievement::isGot).collect(Collectors.toList());
    }

    /**
     * 获得所有 已经获得 的成就列表
     * @return 成就列表
     */
    @Override
    public List<Achievement> getAllGotAchievement() {
        return Stream.of(achievements).filter(Achievement::isGot).collect(Collectors.toList());
    }

    /**
     * 获得所有 特定分类 的成就列表
     * @param type 成就分类
     * @return 列表
     */
    @Override
    public List<Achievement> getAllAchievement(String type) {
        return  Stream.of(getAllAchievement()).filter(value -> value.getType().equals(type)).collect(Collectors.toList());
    }

    /**
     * 获得所有 特定分类而且尚未获得 的成就列表
     * @param type 成就分类
     * @return 列表
     */
    @Override
    public List<Achievement> getAllNoGetAchievment(String type) {
        return Stream.of(getAllNoGetAchievment()).filter(value -> value.getType().equals(type)).collect(Collectors.toList());
    }

    /**
     * 获得所有 特定分类而且已经获得 的成就列表
     * @param type 成就分类
     * @return 列表
     */
    @Override
    public List<Achievement> getAllGotAchievement(String type) {
        return Stream.of(getAllGotAchievement()).filter(value -> value.getType().equals(type)).collect(Collectors.toList());
    }

    /**
     * 失去成就
     * @param id 成就id
     * @return 是否成功
     */
    @Override
    public boolean lostAchievement(long id) {
        Achievement achievement = getAchievement(id);
        return _lostAchievement(achievement);
    }

    @LogPoint(type = Log.TYPE.ACHIEVEMENT,action = Log.ACTION.LOSE,property = Log.PROPERTY.DEFAULT)
    private boolean _lostAchievement(Achievement achievement){
        if (achievement!=null){
            //有相应成就
            achievement.setGot(false);
            achievement.setGainTime(new Date());
            return updateAchievement(achievement);
        }else {
            return false;
        }
    }

    /**
     * 从随机成就中失去成就
     * @param achievementReward 成就
     * @return 是否成功
     */
    @Override
    public boolean lostAchievement(AchievementReward achievementReward) {
        if (achievementReward.isHit()){
            Achievement achievement=Stream.of(achievements).filter(value -> value.getName().equals(achievementReward.getAchievementID())).findFirst().get();
            if (achievement!=null){
                return lostAchievement(achievement.getId());
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

    /**
     * 获得所有分类
     * @return 分类列表
     */
    @Override
    public List<String> getAllType() {
        return Stream.of(achievements).map(Achievement::getType).distinct().collect(Collectors.toList());

    }

    /**
     * 载入数据库数据
     */
    private void loadAchievementsFromSQL(){
        Cursor cursor = helper.getReadableDatabase().query(DBHelper.TABLE_ACHIEVEMENT, null, null, null, null, null, null);
        while (cursor.moveToNext()){
            Achievement a = new Achievement();
            a.getFromCursor(cursor);
            achievements.add(a);
        }
        cursor.close();
    }


}
