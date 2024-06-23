package info.zpss.jdbc;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

// 品牌数据对应的实体类
class Brand {
    // id 主键
    private Integer id;
    // 品牌名称jdbc_test
    private String brandName;
    // 企业名称
    private String companyName;
    // 排序字段
    private Integer ordered;
    // 描述信息
    private String description;
    // 状态：0：禁用  1：启用
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getOrdered() {
        return ordered;
    }

    public void setOrdered(Integer ordered) {
        this.ordered = ordered;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "id=" + id +
                ", brandName='" + brandName + '\'' +
                ", companyName='" + companyName + '\'' +
                ", ordered=" + ordered +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}

// 实现品牌数据的增删改查
class BrandMapper {
    private static final BrandMapper INSTANCE;  // 单例模式：私有静态常量用于保存唯一实例
    private static final DataSource DATA_SOURCE; // 数据源

    static {
        INSTANCE = new BrandMapper();   // 单例模式：创建实例
        try {
            // 加载数据库和druid连接池驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            Class.forName("com.alibaba.druid.pool.DruidDataSource");
            // 加载druid连接池配置文件
            Properties druidProp = new Properties();
            druidProp.load(Files.newInputStream(Paths.get("src/druid.properties")));
            // 创建数据源
            DATA_SOURCE = DruidDataSourceFactory.createDataSource(druidProp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 单例模式：禁止外部通过构造方法创建实例
    private BrandMapper() {
    }

    // 单例模式：获取实例
    public static BrandMapper getInstance() {
        return INSTANCE;
    }

    // 查询所有品牌数据
    List<Brand> getBrands() {
        List<Brand> brands = new ArrayList<>();
        String sql = "SELECT * FROM `tb_brand`";
        try (Connection conn = DATA_SOURCE.getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(sql);
            ResultSet resultSet = preStmt.executeQuery();
            while (resultSet.next()) {
                Brand brand = new Brand();
                brand.setId(resultSet.getInt("id"));
                brand.setBrandName(resultSet.getString("brand_name"));
                brand.setCompanyName(resultSet.getString("company_name"));
                brand.setOrdered(resultSet.getInt("ordered"));
                brand.setDescription(resultSet.getString("description"));
                brand.setStatus(resultSet.getInt("status"));
                brands.add(brand);
            }
            resultSet.close();
            preStmt.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return brands;
    }

    // 添加品牌数据
    boolean addBrand(String brandName, String companyName, Integer ordered, String description, Integer status) {
        boolean result = false;
        String sql = "INSERT INTO `tb_brand`(`brand_name`, `company_name`, `ordered`, `description`, `status`)" +
                " VALUES(?, ?, ?, ?, ?)";
        try (Connection conn = DATA_SOURCE.getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setString(1, brandName);
            preStmt.setString(2, companyName);
            preStmt.setInt(3, ordered);
            preStmt.setString(4, description);
            preStmt.setInt(5, status);
            result = preStmt.executeUpdate() > 0;
            preStmt.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return result;
    }

    // 修改品牌数据
    boolean updateBrand(Integer id, String brandName, String companyName, Integer ordered,
                        String description, Integer status) {
        boolean result = false;
        String sql = "UPDATE `tb_brand` SET `brand_name`=?, `company_name`=?, `ordered`=?, " +
                "`description`=?, `status`=? WHERE `id`=?";
        try (Connection conn = DATA_SOURCE.getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setString(1, brandName);
            preStmt.setString(2, companyName);
            preStmt.setInt(3, ordered);
            preStmt.setString(4, description);
            preStmt.setInt(5, status);
            preStmt.setInt(6, id);
            result = preStmt.executeUpdate() > 0;
            preStmt.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return result;
    }

    // 删除品牌数据
    boolean deleteBrand(Integer id) {
        boolean result = false;
        String sql = "DELETE FROM `tb_brand` WHERE `id`=?";
        try (Connection conn = DATA_SOURCE.getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setInt(1, id);
            result = preStmt.executeUpdate() > 0;
            preStmt.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return result;
    }

    // 获取品牌数据的id
    Integer getIdByBrandName(String brandName) {
        Integer result = null;
        String sql = "SELECT `id` FROM `tb_brand` WHERE `brand_name`=?";
        try (Connection conn = DATA_SOURCE.getConnection()) {
            PreparedStatement preStmt = conn.prepareStatement(sql);
            preStmt.setString(1, brandName);
            ResultSet resultSet = preStmt.executeQuery();
            if (resultSet.next())
                result = resultSet.getInt("id");
            resultSet.close();
            preStmt.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return result;
    }
}

// 品牌数据增删改查的测试类
public class BrandTest {
    public static void main(String[] args) {
        testGetBrands();
        testAddBrand();
        testGetBrands();
        testUpdateBrand();
        testGetBrands();
        testDeleteBrand();
        testGetBrands();
    }

    private static void testGetBrands() {
        BrandMapper brandMapper = BrandMapper.getInstance();
        List<Brand> brands = brandMapper.getBrands();
        for (Brand brand : brands)
            System.out.println(brand);
    }

    private static void testAddBrand() {
        BrandMapper brandMapper = BrandMapper.getInstance();
        boolean result = brandMapper.addBrand("苹果", "Apple Inc.", 10,
                "Stay hungry, stay foolish.", 1);
        System.out.println(result ? "添加成功" : "添加失败");
    }

    private static void testUpdateBrand() {
        BrandMapper brandMapper = BrandMapper.getInstance();
        int id = brandMapper.getIdByBrandName("苹果");
        boolean result = brandMapper.updateBrand(id, "苹果", "苹果电子产品商贸有限公司", 10,
                "Stay hungry, stay foolish.", 1);
        System.out.println(result ? "修改成功" : "修改失败");
    }

    private static void testDeleteBrand() {
        BrandMapper brandMapper = BrandMapper.getInstance();
        int id = brandMapper.getIdByBrandName("苹果");
        boolean result = brandMapper.deleteBrand(id);
        System.out.println(result ? "删除成功" : "删除失败");
    }
}