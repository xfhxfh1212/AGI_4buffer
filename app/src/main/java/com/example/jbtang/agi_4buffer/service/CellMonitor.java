package com.example.jbtang.agi_4buffer.service;

import com.example.jbtang.agi_4buffer.core.CellInfo;
import com.example.jbtang.agi_4buffer.device.MonitorDevice;

/**
 * Created by jbtang on 10/28/2015.
 */
public class CellMonitor {
    private static final String TAG = "Cell monitor";
    private static final CellMonitor instance = new CellMonitor();

    private CellMonitor() {

    }

    public static CellMonitor getInstance() {
        return instance;
    }

    public void prepareMonitor(MonitorDevice device, CellInfo cellInfo) {
        device.setCellInfo(cellInfo);
    }


}
