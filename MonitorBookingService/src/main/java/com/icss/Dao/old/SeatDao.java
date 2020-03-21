package com.icss.Dao.old;

import com.icss.Entity.old.Seat;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SeatDao extends BaseDao {
    /**
     *
     * @param bno
     * @param type
     * @param occupation
     * @return
     * @throws Exception
     */
    public Seat getValidSeat(int bno, String type, int occupation) throws Exception {
        String validSeatNo = null;
        String sql = "select seatno sno, occupation occu from seat where bno=? and stype=? ";
        OpenConncetion();

        System.out.println(type);

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1,bno);
        ps.setString(2,type);
        ResultSet rs = ps.executeQuery();

        Seat s = null;

        while( rs.next() ){
            if( ( rs.getInt("occu") & occupation) == 0 ){
                s = new Seat();
                s.setBno(bno);
                s.setOccupation(rs.getInt("occu"));
                s.setSeatNo(rs.getString("sno"));
                break;
            }
        }
        closeConnection();
        return s;
    }

    public int getCurrOccuBySeatNo(int bno, String seatNo) throws Exception {
        int currOccu = 0;
        String sql = "select occupation from ticket where bno=? and seatno = ?";

        OpenConncetion();
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1,bno);
        ps.setString(2, seatNo);
        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            currOccu = rs.getInt("occupation");
        }
        return currOccu;
    }
}
