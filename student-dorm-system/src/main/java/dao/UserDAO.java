package dao;

import model.User;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDAO {

    public static User query(User user) {
        User queryUser = null;
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            c = DBUtil.getConnection();
            String sql = "select id,nickname,email from user where username=? and password=?";
            ps = c.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            rs = ps.executeQuery();
            while(rs.next()){
                queryUser = user;
                queryUser.setId(rs.getInt("id"));
                queryUser.setNickname(rs.getString("nickname"));
                queryUser.setEmail(rs.getString("email"));
            }
        } catch (Exception e) {
            throw new RuntimeException("登录校验用户名密码出错", e);
        } finally {
            DBUtil.close(c, ps, rs);
        }
        return queryUser;
    }

    public static List<User> querys() {
        List<User> list = new ArrayList<>();
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            c = DBUtil.getConnection();
            String sql = "select u.id," +
                    "       u.username," +
                    "       u.password," +
                    "       u.nickname," +
                    "       u.email," +
                    "       u.create_time" +
                    "   from user u";
            ps = c.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setNickname(rs.getString("nickname"));
                u.setEmail(rs.getString("email"));
                u.setCreateTime(new Date(rs.getTimestamp("create_time").getTime()));
               list.add(u) ;
            }
        } catch (Exception e) {
            throw new RuntimeException("查询用户列表出错", e);
        } finally {
            DBUtil.close(c, ps, rs);
        }
        return list;
    }

    public static User queryById(int id) {
        User u = new User();
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            c = DBUtil.getConnection();
            String sql = "select u.id," +
                    "       u.username," +
                    "       u.password," +
                    "       u.nickname," +
                    "       u.email," +
                    "       u.create_time" +
                    "   from user u" +
                    "   where u.id = ?";
            ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while(rs.next()){
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setNickname(rs.getString("nickname"));
                u.setEmail(rs.getString("email"));
                u.setCreateTime(new Date(rs.getTimestamp("create_time").getTime()));
            }
        } catch (Exception e) {
            throw new RuntimeException("查询用户详情出错", e);
        } finally {
            DBUtil.close(c, ps, rs);
        }
        return u;
    }

    public static int update(User u) {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = DBUtil.getConnection();
            String sql = "update user set username=?, password=?, nickname=?, email=?" +
                    "   where id=?";
            ps = c.prepareStatement(sql);
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getNickname());
            ps.setString(4, u.getEmail());
            ps.setInt(5,u.getId());
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("修改用户信息出错", e);
        } finally {
            DBUtil.close(c, ps);
        }
    }

    public static int delete(String[] ids) {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = DBUtil.getConnection();
            StringBuilder sql = new StringBuilder("delete from user where id in (");
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
            throw new RuntimeException("删除用户信息出错", e);
        } finally {
            DBUtil.close(c, ps);
        }
    }

    public static int insert(User u) {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = DBUtil.getConnection();
            String sql = "insert into user(username, password, nickname, email, create_time) values (?,?,?,?,?)";
            ps = c.prepareStatement(sql);
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getNickname());
            ps.setString(4, u.getEmail());
            ps.setTimestamp(5,new Timestamp(new Date().getTime()));
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("插入用户信息出错", e);
        } finally {
            DBUtil.close(c, ps);
        }
    }
}
