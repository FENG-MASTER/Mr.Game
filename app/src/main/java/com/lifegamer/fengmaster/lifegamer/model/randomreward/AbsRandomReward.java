package com.lifegamer.fengmaster.lifegamer.model.randomreward;

import java.util.Random;

/**
 * Created by qianzise on 2017/10/5.
 *
 * 概率型奖励抽象类
 */

public abstract class AbsRandomReward {

    private static Random random=new Random();


    public abstract int getProbability();

    public boolean isHit(){


        boolean hitFlag=random.nextInt(1000)<=Math.abs(getProbability());
        return hitFlag;

    }

}
