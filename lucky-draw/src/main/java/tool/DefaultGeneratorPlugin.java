package tool;

import lombok.SneakyThrows;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.SimpleSelectAllElementGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

public class DefaultGeneratorPlugin extends PluginAdapter {

    private HashSet<String> rootMappers = new HashSet<>();

    //是否需要生成Data注解
    private boolean needsData = false;
    //是否需要生成Getter注解
    private boolean needsGetter = false;
    //是否需要生成Setter注解
    private boolean needsSetter = false;
    //是否需要生成ToString注解
    private boolean needsToString = false;
    //是否需要生成Accessors(chain = true)注解
    private boolean needsAccessors = false;
    //是否需要生成EqualsAndHashCode注解
    private boolean needsEqualsAndHashCode = false;

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
        String rootMappers = properties.getProperty("rootMappers");
        if (StringUtility.stringHasValue(rootMappers)) {
            for (String mapper : rootMappers.split(",")) {
                this.rootMappers.add(mapper);
            }
        }
        //lombok扩展
        String lombok = properties.getProperty("lombok");
        if (lombok != null && !"".equals(lombok)) {
            this.needsData = lombok.contains("Data");
            //@Data 优先级高于 @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
            this.needsGetter = !this.needsData && lombok.contains("Getter");
            this.needsSetter = !this.needsData && lombok.contains("Setter");
            this.needsToString = !this.needsData && lombok.contains("ToString");
            this.needsEqualsAndHashCode = !this.needsData && lombok.contains("EqualsAndHashCode");
            this.needsAccessors = lombok.contains("Accessors");
        }
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        //获取实体类
        FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        //import接口
        for (String mapper : rootMappers) {
            interfaze.addImportedType(new FullyQualifiedJavaType(mapper));
            interfaze.addSuperInterface(new FullyQualifiedJavaType(mapper + "<" + entityType.getShortName() + ">"));
        }
        //import实体类
        interfaze.addImportedType(entityType);
        interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Mapper"));
        interfaze.addAnnotation("@Mapper");
        return true;
    }

    /**
     * 生成基础实体类
     *
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        //lombok扩展开始
        //如果需要Data，引入包，代码增加注解
        if (this.needsData) {
            topLevelClass.addImportedType("lombok.Data");
            topLevelClass.addAnnotation("@Data");
        }
        //如果需要Getter，引入包，代码增加注解
        if (this.needsGetter) {
            topLevelClass.addImportedType("lombok.Getter");
            topLevelClass.addAnnotation("@Getter");
        }
        //如果需要Setter，引入包，代码增加注解
        if (this.needsSetter) {
            topLevelClass.addImportedType("lombok.Setter");
            topLevelClass.addAnnotation("@Setter");
        }
        //如果需要ToString，引入包，代码增加注解
        if (this.needsToString) {
            topLevelClass.addImportedType("lombok.ToString");
            topLevelClass.addAnnotation("@ToString");
        }
        //如果需要Getter，引入包，代码增加注解
        if (this.needsAccessors) {
            topLevelClass.addImportedType("lombok.experimental.Accessors");
            topLevelClass.addAnnotation("@Accessors(chain = true)");
        }
        //如果需要Getter，引入包，代码增加注解
        if (this.needsEqualsAndHashCode) {
            topLevelClass.addImportedType("lombok.EqualsAndHashCode");
            topLevelClass.addAnnotation("@EqualsAndHashCode");
        }
        return true;
    }

    /**
     * 如果需要生成Getter注解，就不需要生成get相关代码了
     */
    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return !(this.needsData || this.needsGetter);
    }

    /**
     * 如果需要生成Setter注解，就不需要生成set相关代码了
     */
    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return !(this.needsData || this.needsSetter);
    }


    @Override
    public boolean clientInsertMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientInsertMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientInsertSelectiveMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientInsertSelectiveMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectByPrimaryKeyMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles() {
        return super.contextGenerateAdditionalXmlFiles();
    }

    @SneakyThrows
    @Override
    public boolean sqlMapGenerated(GeneratedXmlFile sqlMap, IntrospectedTable introspectedTable) {
        Field field = sqlMap.getClass().getDeclaredField("document");
        field.setAccessible(true);
        Document document = (Document) field.get(sqlMap);
        XmlElement rootElement = document.getRootElement();

        SelectByConditionElementGenerator selectOneElementGenerator = new SelectByConditionElementGenerator();
        selectOneElementGenerator.setId("selectOne");
        selectOneElementGenerator.setContext(context);
        selectOneElementGenerator.setIntrospectedTable(introspectedTable);
//                elementGenerator.setProgressCallback(progressCallback);
//                elementGenerator.setWarnings(warnings);
        selectOneElementGenerator.addElements(rootElement);

        AbstractXmlElementGenerator simpleSelectAllElementGenerator = new SimpleSelectAllElementGenerator();
        simpleSelectAllElementGenerator.setContext(context);
        simpleSelectAllElementGenerator.setIntrospectedTable(introspectedTable);
//                elementGenerator.setProgressCallback(progressCallback);
//                elementGenerator.setWarnings(warnings);
        simpleSelectAllElementGenerator.addElements(rootElement);

        SelectByConditionElementGenerator selectByConditionElementGenerator = new SelectByConditionElementGenerator();
        selectByConditionElementGenerator.setId("selectByCondition");
        selectByConditionElementGenerator.setContext(context);
        selectByConditionElementGenerator.setIntrospectedTable(introspectedTable);
//                elementGenerator.setProgressCallback(progressCallback);
//                elementGenerator.setWarnings(warnings);
        selectByConditionElementGenerator.addElements(rootElement);

        AbstractXmlElementGenerator deleteByIdsElementGenerator = new DeleteByIdsElementGenerator();
        deleteByIdsElementGenerator.setContext(context);
        deleteByIdsElementGenerator.setIntrospectedTable(introspectedTable);
//                elementGenerator.setProgressCallback(progressCallback);
//                elementGenerator.setWarnings(warnings);
        deleteByIdsElementGenerator.addElements(rootElement);

        field.setAccessible(false);
        return super.sqlMapGenerated(sqlMap, introspectedTable);
    }

}
