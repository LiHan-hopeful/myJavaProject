package tool;

import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

public class DeleteByIdsElementGenerator extends AbstractXmlElementGenerator {
    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("delete"); //$NON-NLS-1$

        answer.addAttribute(new Attribute(
                "id", "deleteByIds")); //$NON-NLS-1$

        answer.addAttribute(new Attribute("parameterType", //$NON-NLS-1$
                "java.util.List"));

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("delete from "); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));

        sb.setLength(0);
        sb.append("  where ");
        sb.append(introspectedTable.getPrimaryKeyColumns().get(0).getActualColumnName());
        sb.append(" in");
        answer.addElement(new TextElement(sb.toString()));

        XmlElement foreachElement = new XmlElement("foreach"); //$NON-NLS-1$
        foreachElement.addAttribute(new Attribute("collection", "list")); //$NON-NLS-1$ //$NON-NLS-2$
//        insertTrimElement.addAttribute(new Attribute("suffix", ")")); //$NON-NLS-1$ //$NON-NLS-2$
        foreachElement.addAttribute(new Attribute("item", "item")); //$NON-NLS-1$ //$NON-NLS-2$
        foreachElement.addAttribute(new Attribute("open", "(")); //$NON-NLS-1$ //$NON-NLS-2$
        foreachElement.addAttribute(new Attribute("separator", ",")); //$NON-NLS-1$ //$NON-NLS-2$
        foreachElement.addAttribute(new Attribute("close", ")")); //$NON-NLS-1$ //$NON-NLS-2$
        foreachElement.addElement(new TextElement("#{item}"));
        answer.addElement(foreachElement);

        parentElement.addElement(answer);
    }
}
