package dao;

import model.Page;
import model.Student;
import util.DBUtil;
import util.ThreadLocalHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentDAO {

    public static List<Student> query(Page p) {
        List<Student> list = new ArrayList<>();
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            c = DBUtil.getConnection();
            StringBuilder sql = new StringBuilder("SELECT s.id," +
                    "       s.student_name," +
                    "       s.student_graduate_year," +
                    "       s.student_major," +
                    "       s.student_email," +
                    "       s.dorm_id," +
                    "       s.create_time," +
                    "       d.dorm_no," +
                    "       b.id building_id," +
                    "       b.building_name" +
                    "   FROM student s" +
                    "         JOIN dorm d ON s.dorm_id = d.id" +
                    "         JOIN building b ON d.building_id = b.id");
            if(p.getSearchText() != null && p.getSearchText().trim().length() > 0){
                sql.append("    WHERE s.student_name like ?");
            }
            if(p.getSortOrder() != null && p.getSortOrder().trim().length() > 0){
                //占位符什么情况下可以使用：考虑一个因素：字符串替换占位符，带单引号
                sql.append("    ORDER BY s.create_time "+p.getSortOrder());
            }
            //获取总的查询数量
            StringBuilder countSQL = new StringBuilder("select count(0) count from (");
            countSQL.append(sql);
            countSQL.append(") tmp");
            ps = c.prepareStatement(countSQL.toString());
            if(p.getSearchText() != null && p.getSearchText().trim().length() > 0){
                ps.setString(1, "%"+p.getSearchText()+"%");
            }
            rs = ps.executeQuery();
            while(rs.next()){
                int count = rs.getInt("count");//设置到返回数据的total字段，当前方法是无法通过返回对象设置
                //使用ThreadLocal：变量绑定到线程Thread类的ThreadLocalMap类型的属性中
                ThreadLocalHolder.get().set(count);
            }

            //处理业务数据
            sql.append("    limit ?,?");
            ps = c.prepareStatement(sql.toString());
            //页码转换为索引：上一页的页码*每页的数量，注意索引是从0开始
            int idx = (p.getPageNumber()-1)*p.getPageSize();
            int i = 1;
            if(p.getSearchText() != null && p.getSearchText().trim().length() > 0){
                ps.setString(i++, "%"+p.getSearchText()+"%");
            }
            ps.setInt(i++, idx);
            ps.setInt(i++, p.getPageSize());
            rs = ps.executeQuery();
            while(rs.next()){
                Student s = new Student();
                s.setId(rs.getInt("id"));
                s.setStudentName(rs.getString("student_name"));
                s.setStudentGraduateYear(rs.getString("student_graduate_year"));
                s.setStudentMajor(rs.getString("student_major"));
                s.setStudentEmail(rs.getString("student_email"));
                s.setDormId(rs.getInt("dorm_id"));
                s.setCreateTime(new Date(rs.getTimestamp("create_time").getTime()));
                s.setDormNo(rs.getString("dorm_no"));
                s.setBuildingId(rs.getInt("building_id"));
                s.setBuildingName(rs.getString("building_name"));
                list.add(s);
            }
        } catch (Exception e) {
            throw new RuntimeException("查询学生列表出错", e);
        } finally {
            DBUtil.close(c, ps, rs);
        }
        return list;
    }

    public static Student queryById(int id) {
        Student s = new Student();
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            c = DBUtil.getConnection();
            String sql = "SELECT s.id," +
                    "       s.student_name," +
                    "       s.student_graduate_year," +
                    "       s.student_major," +
                    "       s.student_email," +
                    "       s.dorm_id," +
                    "       s.create_time," +
                    "       d.dorm_no," +
                    "       b.id building_id," +
                    "       b.building_name" +
                    "   FROM student s" +
                    "         JOIN dorm d ON s.dorm_id = d.id" +
                    "         JOIN building b ON d.building_id = b.id" +
                    "   WHERE s.id=?";
            ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while(rs.next()){
                s.setId(rs.getInt("id"));
                s.setStudentName(rs.getString("student_name"));
                s.setStudentGraduateYear(rs.getString("student_graduate_year"));
                s.setStudentMajor(rs.getString("student_major"));
                s.setStudentEmail(rs.getString("student_email"));
                s.setDormId(rs.getInt("dorm_id"));
                s.setCreateTime(new Date(rs.getTimestamp("create_time").getTime()));
                s.setDormNo(rs.getString("dorm_no"));
                s.setBuildingId(rs.getInt("building_id"));
                s.setBuildingName(rs.getString("building_name"));
            }
        } catch (Exception e) {
            throw new RuntimeException("查询学生详情出错", e);
        } finally {
            DBUtil.close(c, ps, rs);
        }
        return s;
    }

    public static int insert(Student s) {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = DBUtil.getConnection();
            String sql = "insert into student(student_name, student_graduate_year, student_major, student_email, dorm_id) values (?,?,?,?,?)";
            ps = c.prepareStatement(sql);
            ps.setString(1, s.getStudentName());
            ps.setString(2, s.getStudentGraduateYear());
            ps.setString(3, s.getStudentMajor());
            ps.setString(4, s.getStudentEmail());
            ps.setInt(5, s.getDormId());
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("插入学生信息出错", e);
        } finally {
            DBUtil.close(c, ps);
        }
    }

    public static int update(Student s) {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = DBUtil.getConnection();
            String sql = "update student set student_name=?, student_graduate_year=?, student_major=?, student_email=?, dorm_id=?" +
                    "   where id=?";
            ps = c.prepareStatement(sql);
            ps.setString(1, s.getStudentName());
            ps.setString(2, s.getStudentGraduateYear());
            ps.setString(3, s.getStudentMajor());
            ps.setString(4, s.getStudentEmail());
            ps.setInt(5, s.getDormId());
            ps.setInt(6, s.getId());
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("修改学生信息出错", e);
        } finally {
            DBUtil.close(c, ps);
        }
    }

    public static int delete(String[] ids) {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = DBUtil.getConnection();
            StringBuilder sql = new StringBuilder("delete from student where id in (");
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
            throw new RuntimeException("删除学生信息出错", e);
        } finally {
            DBUtil.close(c, ps);
        }
    }

    public static int apply(Student s) {
        List<Integer> ids = s.getIds();
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = DBUtil.getConnection();
            StringBuilder sql = new StringBuilder("update student set dorm_id=?" +
                    "   where id in (");
            for(int i=0; i<ids.size(); i++){
                if(i != 0)
                    sql.append(",");
                sql.append("?");
            }
            sql.append(")");
            ps = c.prepareStatement(sql.toString());
            ps.setInt(1, s.getDormId());
            for(int i=0; i<ids.size(); i++){
                ps.setInt(i+2, ids.get(i));
            }
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("分配学生宿舍出错", e);
        } finally {
            DBUtil.close(c, ps);
        }
    }
}
