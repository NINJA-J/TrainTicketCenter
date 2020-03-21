package com.icss.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AreaDao extends BaseDao {
    public String getAreaNameById( int id ) throws Exception {
        OpenConncetion();
        PreparedStatement ps = connection.prepareStatement("select ANAME from TAREA where ANO = ?");
        ps.setInt(1,id);
        ResultSet rs = ps.executeQuery();

        String name = "-";
        if(rs.next())
            name = rs.getString("ANAME");
        closeConnection();
        return name;
    }

    public int getIdByAreaName( String name ) throws Exception {
        OpenConncetion();
        PreparedStatement ps = connection.prepareStatement("select ANO from TAREA where ANAME = ?");
        ps.setString(1,name);
        ResultSet rs = ps.executeQuery();

        int t = -1;
        if(rs.next())
            t = rs.getInt("ANO");
        closeConnection();
        return t;
    }
}
