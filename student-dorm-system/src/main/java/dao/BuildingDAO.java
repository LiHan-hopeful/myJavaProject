package dao;

import model.Building;
import model.DictionaryTag;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BuildingDAO {

    public static List<DictionaryTag> query() {
        List<DictionaryTag> list = new ArrayList<>();
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            c = DBUtil.getConnection();
            String sql = "select id, building_name from building";
            ps = c.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                DictionaryTag tag = new DictionaryTag();
                tag.setDictionaryTagKey(rs.getString("id"));
                tag.setDictionaryTagValue(rs.getString("building_name"));
                list.add(tag);
            }
        } catch (Exception e) {
            throw new RuntimeException("查询宿舍楼数据字典出错", e);
        } finally {
            DBUtil.close(c, ps, rs);
        }
        return list;
    }

    public static List<Building> querys() {
        List<Building> list = new ArrayList<>();
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            c = DBUtil.getConnection();
            String sql = "SELECT b.building_name," +
                    "       b.building_desc," +
                    "       COUNT(d.building_id) AS dormCount," +
                    "       b.create_time" +
                    "   FROM building b" +
                    "         LEFT JOIN dorm d ON b.id = d.building_id" +
                    "   GROUP BY b.id";
            ps = c.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
               Building b = new Building();
               b.setBuildingName(rs.getString("building_name"));
               b.setBuildingDesc(rs.getString("building_desc"));
               b.setDormCount(rs.getInt("dormCount"));
               b.setCreateTime(new Date(rs.getTimestamp("create_time").getTime()));
                list.add(b);
            }
        } catch (Exception e) {
            throw new RuntimeException("查询宿舍列表出错", e);
        } finally {
            DBUtil.close(c, ps, rs);
        }
        return list;
    }

    public static int insert(Building b) {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = DBUtil.getConnection();
            String sql = "insert into building(building_name, building_desc) values (?,?)";
            ps = c.prepareStatement(sql);
            ps.setString(1,b.getBuildingName());
            ps.setString(2,b.getBuildingDesc());
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("插入宿舍楼信息出错", e);
        } finally {
            DBUtil.close(c, ps);
        }
    }

    public static Building queryById(int id) {
        Building b = new Building();
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            c = DBUtil.getConnection();
            String sql = "SELECT b.id," +
                    "       b.building_name," +
                    "       b.building_desc," +
                    "       b.create_time" +
                    "   FROM building b" +
                    "   WHERE b.id = ?";
            ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while(rs.next()){
               b.setId(rs.getInt("id"));
                b.setBuildingName(rs.getString("building_name"));
                b.setBuildingDesc(rs.getString("building_desc"));
                b.setCreateTime(new Date(rs.getTimestamp("create_time").getTime()));
            }
        } catch (Exception e) {
            throw new RuntimeException("查询宿舍详情出错", e);
        } finally {
            DBUtil.close(c, ps, rs);
        }
        return b;
    }


    public static int delete(String[] ids) {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = DBUtil.getConnection();
            StringBuilder sql = new StringBuilder("delete from building where id in (");
            for(int i=0; i<ids.length; i++){
                if(i != 0)
                    sql.append(",");
                sql.append("?");
            }
            sql.append(")");
            ps = c.prepareStatement(sql.toString());
            for(int i=0; i<ids.length; i++){
                ps.setInt(i+1, Integer.parseInt(ids[i]));
            }
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("删除宿舍楼信息出错", e);
        } finally {
            DBUtil.close(c, ps);
        }
    }

    public static int update(Building b) {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = DBUtil.getConnection();
            String sql = "update building set building_name=?,building_desc=?" +
                    "   where id=?";
            ps = c.prepareStatement(sql);
            ps.setString(1, b.getBuildingName());
            ps.setString(2, b.getBuildingDesc());
            ps.setInt(3,b.getId());
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("修改宿舍信息出错", e);
        } finally {
            DBUtil.close(c, ps);
        }
    }
}
