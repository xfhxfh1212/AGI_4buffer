package com.example.jbtang.agi_4buffer.messages;

import android.os.Handler;
import android.os.Message;

import com.example.jbtang.agi_4buffer.core.Global;
import com.example.jbtang.agi_4buffer.messages.base.MsgTypes;

/**
 * Created by jbtang on 11/1/2015.
 */
public class MessageDispatcher {
    private static final MessageDispatcher instance = new MessageDispatcher();
    private Handler registeredHandler;

    private MessageDispatcher() {
    }

    public static MessageDispatcher getInstance() {
        return instance;
    }

    public void RegisterHandler(Handler handler) {
        if (handler == null) {
            return;
        }
        registeredHandler = handler;
    }

    public void Dispatch(Global.GlobalMsg globalMsg) {
        Message msg = new Message();
        msg.what = globalMsg.getMsgType();
        switch (globalMsg.getMsgType()) {
            case MsgTypes.L2P_AG_CELL_CAPTURE_IND_MSG_TYPE:
            case MsgTypes.L2P_AG_UE_CAPTURE_IND_MSG_TYPE:
            case MsgTypes.L1_PHY_COMMEAS_IND_MSG_TYPE:
            case MsgTypes.L1_AG_PROTOCOL_DATA_MSG_TYPE:
            case MsgTypes.L2P_AG_UE_RELEASE_IND_MSG_TYPE:
                msg.obj = globalMsg;
                if (registeredHandler != null) {
                    registeredHandler.sendMessage(msg);
                }
                break;
            default:
                break;
        }
    }
}
