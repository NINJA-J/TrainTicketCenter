package com.icss.dao;

import com.icss.entity.Station;
import oracle.jdbc.OracleResultSet;
import oracle.jdbc.OracleStatement;

public class StationDao extends BaseDao {
    public Station getStation(String tno, int area) throws Exception {
        OpenConncetion();
        OracleStatement st = (OracleStatement) connection.createStatement();
        OracleResultSet rs = (OracleResultSet) st.executeQuery(
                "select * from TRANSITION where TNO='"+tno+"' and area="+area);
        Station station = new Station();
        if(rs.next()) {
            AreaDao ad = new AreaDao();
            station.setaName(ad.getAreaNameById(area));
            station.setArea(area);
            station.setAreaIndex(rs.getInt("Ind"));
            station.setInterval(rs.getINTERVALDS("run_time"));
            station.setTno(tno);
            station.setName(rs.getString("name"));
        }

        return station;

    }
}
