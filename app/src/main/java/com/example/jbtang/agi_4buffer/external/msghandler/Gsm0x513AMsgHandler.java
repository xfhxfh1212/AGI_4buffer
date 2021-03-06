package com.example.jbtang.agi_4buffer.external.msghandler;

import com.example.jbtang.agi_4buffer.external.MonitorApplication;

import java.util.ArrayList;
import java.util.List;

import io.fmaster.GSMNCellInfo;
import io.fmaster.GSMServCellMessage;
import io.fmaster.Message;
import io.fmaster.MessageHandler;

import io.fmaster.Enumeration.BAND_TYPE;
import io.fmaster.GsmMessage.Gsm0x513AMessage;
import io.fmaster.GsmMessage.GsmNcellParam;


public class Gsm0x513AMsgHandler implements MessageHandler {
    private List<GSMNCellInfo> ncells = new ArrayList<>();

    @Override
    public void handle(Message message) {
        Gsm0x513AMessage msg = (Gsm0x513AMessage) message;
        MonitorApplication.data_gsm_ServCell.setEARFCN(msg.getservBcchArfcn());
        MonitorApplication.data_gsm_ServCell.setBand(BAND_TYPE.valueOf(msg.getBand()));
        if (MonitorApplication.data_gsm_ServCell.getDTX() == 2) {
            MonitorApplication.data_gsm_ServCell.setRXLEV(msg.getrxLevFullAvg());

            MonitorApplication.data_gsm_ServCell.setRXQUAL(msg.getrxQualFullAvg());
        } else {
            MonitorApplication.data_gsm_ServCell.setRXLEV(msg.getrxLevSubAvg());

            MonitorApplication.data_gsm_ServCell.setRXQUAL(msg.getrxQualSubAvg());
        }
        if (msg.getTime() - MonitorApplication.data_gsm_ServCell.getTime() > GSMServCellMessage.UPDATE_INTERVAL) {
            MonitorApplication.data_gsm_ServCell.setTime(msg.getTime());
            MonitorApplication.sendBroad(MonitorApplication.BROAD_TO_MAIN_ACTIVITY, MonitorApplication.GSM_SERVER_CELL_FLAG, "msg", MonitorApplication.data_gsm_ServCell);
        }
        ncells.clear();
        ncells.addAll(MonitorApplication.data_gsm_NcellList.getNcells());
        if (msg.getCellInfo().size() > 0) {
            MonitorApplication.data_gsm_NcellList.reset();
            //add servCell in Cell list
            GSMNCellInfo cell = new GSMNCellInfo();
            cell.setEARFCN(MonitorApplication.data_gsm_ServCell.getEARFCN());
            cell.setBSIC(MonitorApplication.data_gsm_ServCell.getBSIC());
            cell.setRXLEV(MonitorApplication.data_gsm_ServCell.getRXLEV());
            cell.setC1(MonitorApplication.data_gsm_ServCell.getC1());
            cell.setC2(MonitorApplication.data_gsm_ServCell.getC2());
            MonitorApplication.data_gsm_NcellList.AddNcell(cell);
        }

        for (GsmNcellParam ncell : msg.getCellInfo()) {
            GSMNCellInfo cell = new GSMNCellInfo();
            if (ncell.getbcc() != Byte.MAX_VALUE && ncell.getncc() != Byte.MAX_VALUE) {
                cell.setBSIC((byte) (ncell.getncc() * 10 + ncell.getbcc()));
            }
            cell.setEARFCN(ncell.getArfcn());
            for (GSMNCellInfo tempcell : ncells) {
                if (tempcell.getEARFCN() == ncell.getArfcn()) {
                    cell.setC1(tempcell.getC1());
                    cell.setC2(tempcell.getC2());

                    break;
                }
            }

            cell.setRXLEV(ncell.getrxLevAvg());

            MonitorApplication.data_gsm_NcellList.AddNcell(cell);
        }

        //send broad
        MonitorApplication.data_gsm_NcellList.setTime(msg.getTime());
        MonitorApplication.sendBroad(MonitorApplication.BROAD_TO_MAIN_ACTIVITY, MonitorApplication.GSM_N_CELL_FLAG, "msg", MonitorApplication.data_gsm_NcellList);

    }
}
