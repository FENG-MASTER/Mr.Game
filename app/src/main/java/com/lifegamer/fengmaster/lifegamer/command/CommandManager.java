package com.lifegamer.fengmaster.lifegamer.command;

import android.support.design.widget.Snackbar;

import com.lifegamer.fengmaster.lifegamer.command.command.ICommand;
import com.lifegamer.fengmaster.lifegamer.util.ViewUtil;

/**
 * Created by qianzise on 2017/10/15.
 */

public class CommandManager {

    /**
     * 命令集头部
     */
    private boolean isHead=true;

    /**
     * 命令集中间的事件,不会提示
     */
    private boolean hide=true;

    public void executeCommand(ICommand command){
        //如果是事件集的头部
        if (isHead){
            /**
             * 为了解决如下问题:
             *  如果一个命令调用了其他命令,可能会出现前一个命令的提示还未显示完全,就被子命令的提示覆盖
             *
             *          这里采用解决方案是:只显示命令集中第一个命令的提示
             *
             * */
            isHead=false;
            command.execute();
            isHead=true;
            hide=false;
        }else {
            hide=true;
            command.execute();
        }


        if (command.isShow()&&!hide){
            if (command.isUndoable()){
                //可取消
                ViewUtil.showSnack(command.getName(), Snackbar.LENGTH_LONG, command.getUndoActionName(), view -> command.undo());
            }else {
                //不可取消
                ViewUtil.showSnack(command.getName(),Snackbar.LENGTH_LONG);
            }
        }

    }

}
