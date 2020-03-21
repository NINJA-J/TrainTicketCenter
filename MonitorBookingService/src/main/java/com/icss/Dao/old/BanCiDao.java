package com.icss.Dao.old;

import com.icss.Entity.old.BanCi;
import com.icss.Entity.old.Station;
import com.icss.Entity.old.Train;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BanCiDao extends BaseDao {
    /**
     *
     * @param bno
     * @param depStat
     * @param desStat
     * @return
     * @throws Exception
     */
    public BanCi getBanCiByBno(int bno, Station depStat, Station desStat) throws Exception {
        TicketDao td = new TicketDao();

        Connection conn = (new BanCiDao()).getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(
                "select TBANCI.BNO bno,TBANCI.STARTDATE + TRANSITION.RUN_TIME aTime" +
                "  from TBANCI, TRANSITION " +
                "where TBANCI.BNO = " + bno +
                "and TBANCI.TNO = TRANSITION.TNO and TRANSITION.AREA = " + depStat.getArea());

        BanCi bc = new BanCi();
        if(rs.next()) {
            bc.setBno(rs.getInt("bno"));
            bc.setSeatInfo(td.getTicketsByBanCi(bno, generateStationCover(
                    depStat.getAreaIndex(), desStat.getAreaIndex())));
            bc.setArrCusDepTime(rs.getDate("aTime"));
        }

        return bc;
    }

    /**
     * 获取startDate发车，经过startArea的火车train的车次列表
     * @param train
     * @param startDate
     * @param sArea
     * @return
     * @throws Exception
     */
    public List<BanCi> getBanCiByDate(Train train, Date startDate, int sArea) throws Exception {
        List<BanCi> bans = new ArrayList<BanCi>();

        String sql="select TBANCI.BNO bno,TBANCI.STARTDATE + TRANSITION.RUN_TIME aTime" +
                "  from TBANCI, TRANSITION " +
                "where TBANCI.TNO = ? " +
                "and to_char(TBANCI.STARTDATE+TRANSITION.RUN_TIME,'yyyy-mm-dd') = " +
                "    to_char(?,                                     'yyyy-mm-dd') " +
                "and TBANCI.TNO = TRANSITION.TNO and TRANSITION.AREA = ?";
        OpenConncetion();
        //
        OraclePreparedStatement ps = (OraclePreparedStatement)connection.prepareStatement(sql);
        ps.setString(1,train.getTno());
        ps.setDate(2,new java.sql.Date(startDate.getTime()));
        ps.setInt(3, sArea);
        OracleResultSet rs = (OracleResultSet)ps.executeQuery();

        while(rs.next()){

            BanCi ban = new BanCi();
            ban.setBno(rs.getInt("bno"));
            ban.setArrCusDepTime(rs.getDate("aTime"));

            TicketDao td = new TicketDao();
            ban.setSeatInfo(td.getTicketsByBanCi(Integer.valueOf(ban.getBno()) ,train.getCusOccupation()));

            bans.add(ban);
        }
        closeConnection();

        return bans;
    }
}
