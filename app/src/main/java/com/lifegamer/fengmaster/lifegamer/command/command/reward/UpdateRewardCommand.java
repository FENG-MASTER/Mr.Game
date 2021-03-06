package com.lifegamer.fengmaster.lifegamer.command.command.reward;

import com.lifegamer.fengmaster.lifegamer.App;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.command.command.AbsNoCancelableCommand;
import com.lifegamer.fengmaster.lifegamer.model.RewardItem;

/**
 * Created by qianzise on 2017/10/22.
 *
 * 更新奖励命令
 */

public class UpdateRewardCommand extends AbsNoCancelableCommand {
    private RewardItem rewardItem;

    public UpdateRewardCommand(RewardItem rewardItem) {
        this.rewardItem = rewardItem;
    }

    @Override
    public boolean execute() {
        return Game.getInstance().getRewardManager().updateRewardItem(rewardItem);
    }

    @Override
    public String getName() {
        return App.getContext().getString(R.string.reward_update)+rewardItem.getName();
    }
}
