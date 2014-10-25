package de.lathspell.java_test_ee6_jpa.model;

import java.sql.SQLException;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.postgresql.util.PGobject;

import de.lathspell.java_test_ee6_jpa.model.EnumExample.MyEnum;

/**
 * Converter for Java "MyEnum" to PostgreSQL "my_enum".
 *
 * For whatever reason this converter has to be registered in orm.xml!
 */
@Converter(autoApply = false)
public class MyEnumPostgresConverter implements AttributeConverter<MyEnum, PGobject> {

    @Override
    public PGobject convertToDatabaseColumn(MyEnum attribute) {
        PGobject o = new PGobject();
        o.setType("my_enum");
        try {
            o.setValue(attribute.name());
        } catch (SQLException e) {
            throw new RuntimeException("Cannot PGobject#setValue(" + attribute + ")!", e);
        }
        return o;
    }

    @Override
    public MyEnum convertToEntityAttribute(PGobject dbData) {
        return MyEnum.valueOf(dbData.getValue());
    }
}
